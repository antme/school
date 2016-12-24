package com.wx.school.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eweblib.annotation.role.LoginRequired;
@Service
public class InitialService implements InitializingBean {

	public static final Set<String> loginPath = new HashSet<String>();
	public static final Map<String, String> rolesPathValidationMap = new HashMap<String, String>();

	private static final Logger logger = LogManager.getLogger(InitialService.class);
	

	/**
	 * 初始化那些path需要登录验证，数据放到内存中
	 * 
	 * 
	 * @throws ClassNotFoundException
	 */
	private static void setLoginPathValidation(String packageName) throws ClassNotFoundException {
//		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
//		scanner.resetFilters(true);
//		scanner.addIncludeFilter(new AnnotationTypeFilter(LoginRequired.class));
//		for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
//			Class<?> classzz = Class.forName(bd.getBeanClassName());
//			Method metods[] = classzz.getMethods();
//
//			RequestMapping parent = classzz.getAnnotation(RequestMapping.class);
//			String path = "";
//			if (parent != null) {
//				path = parent.value()[0];
//			}
//
//			for (Method m : metods) {
//				LoginRequired rv = m.getAnnotation(LoginRequired.class);
//				RequestMapping mapping = m.getAnnotation(RequestMapping.class);
//
//				if (rv == null || rv.required()) {
//					if (mapping != null && mapping.value() != null && mapping.value().length > 0) {
//						loginPath.add(path + mapping.value()[0]);
//					}
//				}
//			}
//
//		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			setLoginPathValidation("com.wx.school.controller");
		} catch (ClassNotFoundException e) {
			logger.catching(e);
		}
	}

	// /**
	// *
	// * 出事化权限表，权限来至于 @RoleValidate
	// *
	// * @param dao
	// * @throws ClassNotFoundException
	// */
	// private static void initRoleItems(IQueryDao dao, String packageName)
	// throws ClassNotFoundException {
	//
	// Map<String, RolePath> pathSet = new HashMap<String, RolePath>();
	//
	// DataBaseQueryBuilder pathQuery = new
	// DataBaseQueryBuilder(RolePath.TABLE_NAME);
	// pathQuery.limitColumns(new String[] { RolePath.PATH, RolePath.ID });
	//
	// List<RolePath> pathList = dao.listByQuery(pathQuery, RolePath.class);
	// Map<String, String> pathIdMap = new HashMap<String, String>();
	//
	// for (RolePath rp : pathList) {
	//
	// pathIdMap.put(rp.getPath(), rp.getId());
	// }
	//
	// ClassPathScanningCandidateComponentProvider scanner = new
	// ClassPathScanningCandidateComponentProvider(false);
	// scanner.addIncludeFilter(new AnnotationTypeFilter(Permission.class));
	//
	// for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
	// Class<?> classzz = Class.forName(bd.getBeanClassName());
	// Method metods[] = classzz.getMethods();
	//
	// String groupName = null;
	// RequestMapping parent = classzz.getAnnotation(RequestMapping.class);
	// String path = "";
	// if (parent != null) {
	// path = parent.value()[0];
	// }
	//
	// Permission permission = classzz.getAnnotation(Permission.class);
	// if (permission != null) {
	// groupName = permission.groupName();
	// }
	//
	// for (Method m : metods) {
	//
	// RequestMapping mapping = m.getAnnotation(RequestMapping.class);
	//
	// RoleDescription rd = m.getAnnotation(RoleDescription.class);
	//
	// if (mapping != null) {
	//
	// String validPath = path + mapping.value()[0];
	// RolePath tmp = new RolePath();
	// tmp.setGroupName(groupName);
	// tmp.setPath(validPath);
	//
	// if (rd != null) {
	//
	// tmp.setDescription(rd.description());
	//
	// pathSet.put(validPath, tmp);
	// } else {
	// pathSet.put(validPath, tmp);
	// }
	// }
	//
	// }
	// }
	//
	// for (String newPath : pathSet.keySet()) {
	//
	// if (pathIdMap.get(newPath) == null) {
	// RolePath rp = new RolePath();
	// rp.setPath(newPath);
	// rp.setDescription(pathSet.get(newPath).getDescription());
	// rp.setGroupName(pathSet.get(newPath).getGroupName());
	//
	// dao.insert(rp);
	// }
	// }
	//
	// for (String oldPath : pathIdMap.keySet()) {
	//
	// if (!pathSet.keySet().contains(oldPath)) {
	// RolePath rp = new RolePath();
	// rp.setId(pathIdMap.get(oldPath));
	// dao.deleteById(rp);
	// } else {
	// RolePath rp = new RolePath();
	// rp.setPath(oldPath);
	// rp.setId(pathIdMap.get(oldPath));
	// rp.setDescription(pathSet.get(oldPath).getDescription());
	// rp.setGroupName(pathSet.get(oldPath).getGroupName());
	// dao.updateById(rp);
	// }
	// }
	//
	// }
}
