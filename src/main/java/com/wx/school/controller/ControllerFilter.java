package com.wx.school.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.eweblib.bean.BaseEntity;
import com.eweblib.controller.AbstractController;
import com.eweblib.exception.LoginException;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;
import com.wx.school.service.InitialService;

public class ControllerFilter extends AbstractController implements Filter {

	private PathMatcher pathMatcher = new AntPathMatcher();

	// private String[] excludePatterns = { "/delivery/**", "/cloud/**",
	// "/api/**", "/v/**", "/*.jsp", "/user/*.do" };

	private static Logger logger = LogManager.getLogger(ControllerFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest srequest = (HttpServletRequest) request;
		HttpServletResponse sresponse = (HttpServletResponse) response;
		srequest.setCharacterEncoding("UTF-8");

		Object uid = srequest.getSession().getAttribute(BaseEntity.ID);

		if (uid == null) {
			Cookie[] cookies = srequest.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equalsIgnoreCase("baiHuaId")) {
						uid = cookie.getValue();
						break;
					}
				}
			}

			// String skey = srequest.getParameter("s_key");

			// if (EweblibUtil.isValid(skey)) {
			// uid = UserController.safariLoginData.get(skey);
			// }
		}
	
		if (EweblibUtil.isValid(uid)) {
			EWeblibThreadLocal.set(BaseEntity.ID, uid);
			HttpServletRequest r = (HttpServletRequest)request;
			r.getSession().setAttribute(BaseEntity.ID, uid);
		}

		try {
			if (!isLogin(srequest) && needLogin(srequest)) {
				// throw new LoginException();
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			logger.catching(e);
			if (e instanceof LoginException) {
				forceLogin((HttpServletRequest) request, (HttpServletResponse) response);
			} else {
				// impossible!
				responseServerError(e, (HttpServletRequest) request, (HttpServletResponse) response);
			}

		}
		EWeblibThreadLocal.removeAll();
	}

	private void forceLogin(HttpServletRequest request, HttpServletResponse response) {
		// clearLoginSession(request, response);

		// try {
		// response.sendRedirect("/login.jsp");
		// } catch (IOException e) {
		// logger.fatal("Write response data to client failed!", e);
		// }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	private boolean isLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(BaseEntity.ID) != null;
	}

	private boolean needLogin(HttpServletRequest request) {
		String path = request.getServletPath();
		// if (excludePatterns != null) {
		// for (String pattern : excludePatterns) {
		// if (pathMatcher.match(pattern, path)) {
		// return false;
		// }
		// }
		// }
		return true;
	}

	private void cookieCheck(HttpServletRequest request, HttpServletResponse response) {
		// if (request.getSession().getAttribute(BaseEntity.ID) == null &&
		// request.getCookies() != null) {
		// String account = null;
		// String ssid = null;
		// for (Cookie cookie : request.getCookies()) {
		// if (cookie.getName().equals("account")) {
		// try {
		// account = URLDecoder.decode(cookie.getValue(), "UTF-8");
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// } else if (cookie.getName().equals("ssid")) {
		// ssid = cookie.getValue();
		// }
		// }
		// if (EweblibUtil.isValid(account) && EweblibUtil.isValid(ssid)) {
		// User user = (User) queryDao.findByKeyValue(User.USER_NAME, account,
		// User.TABLE_NAME, User.class);
		// if (user != null && DataEncrypt.generatePassword(user.getUserName() +
		// user.getPassword()).equals(ssid)) {
		// request.setAttribute("remember", "on");
		// setLoginSessionInfo(request, response, user);
		// }
		// }
		// }
	}

	public void roleCheck(HttpServletRequest request) {
		loginCheck(request);

		if (InitialService.rolesPathValidationMap.get(request.getServletPath()) != null) {
			boolean find = false;

			if (EWeblibThreadLocal.getCurrentUserId() != null) {

				// DataBaseQueryBuilder builder = new
				// DataBaseQueryBuilder(User.TABLE_NAME);
				// builder.and(User.ID, EWeblibThreadLocal.getCurrentUserId());
				//
				// User user = (User) queryDao.findOneByQuery(builder,
				// User.class);
				//
				// if (user != null) {
				//
				// }

			}

			if (!find) {
				throw new ResponseException("无权限操作");
			}

		}

	}

	public void loginCheck(HttpServletRequest request) {

		if (InitialService.loginPath.contains(request.getServletPath())) {
			if (request.getSession().getAttribute(BaseEntity.ID) != null) {
				EWeblibThreadLocal.set(BaseEntity.ID, request.getSession().getAttribute(BaseEntity.ID));
			}
			if (request.getSession().getAttribute(BaseEntity.ID) == null) {
				logger.debug("Login requried for path : " + request.getPathInfo());
				throw new LoginException();
			}
		}
	}

}
