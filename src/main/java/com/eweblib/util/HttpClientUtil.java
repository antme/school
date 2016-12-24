package com.eweblib.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eweblib.cfg.ConfigManager;
import com.eweblib.exception.ResponseException;

public class HttpClientUtil {

	private static Logger logger = LogManager.getLogger(HttpClientUtil.class);

	public static String[] userAgents = new String[] {
			"Mozilla/5.0 (Windows NT 5.2) AppleWebKit/534.30 (KHTML, like Gecko) Chrome/12.0.742.122 Safari/534.30",
			"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET4.0E; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C)",
			"Opera/9.80 (Windows NT 5.1; U; zh-cn) Presto/2.9.168 Version/11.50",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:37.0) Gecko/20100101 Firefox/37.0",
			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36" };

	static HttpClientBuilder builder = null;
	static CloseableHttpClient httpClient = null;
	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(5000);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(5000);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 5000);

		httpClient = HttpClients.custom().setConnectionManager(cm).build();

	}



	private static final String DEFAULT_CHARSET = "UTF-8";



	public static String doGet(String url, Map<String, Object> parameters, String urlEncoding, boolean redirect) {

		//

		HttpGet httpget = null;
		String result = null;
		//
		try {

			if (url.contains("?")) {
				String[] urls = url.split("\\?");

				if (EweblibUtil.isValid(urls[1])) {
					// if (EweblibUtil.isValid(encoding)) {
					// url = urls[0] + "?" + URLEncoder.encode(urls[1],
					// encoding);
					// } else {
					// url = urls[0] + "?" + URLEncoder.encode(urls[1]);
					// }
				}
			}
			URIBuilder builder = new URIBuilder(url);
			if (parameters != null) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {

					if (parameters.get(key) != null) {
						builder.setParameter(key, parameters.get(key).toString());
					}
				}
				URI uri = builder.build();
				httpget = new HttpGet(uri);
			} else {
				// builder.
				httpget = new HttpGet(url);
			}

			// int index = EweblibUtil.generateRandom(0, userAgents.length);
			//
			// HttpParams params = new BasicHttpParams();
			// params.setParameter("http.protocol.handle-redirects", redirect);
			//
			// if (urlEncoding != null) {
			// params.setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET,
			// urlEncoding);
			// }
			//
			// // HttpMethodRetryHandle retryHandler = new
			// // DefaultHttpMethodRetryHandler(0, false);
			// // params.setParameter(HttpProtocolParams.RETRY_HANDLER,
			// // retryHandler);
			// params.setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
			// HttpConnectionParams.setConnectionTimeout(params, 5000);
			// HttpConnectionParams.setSoKeepalive(params, false);
			//
			// httpget.setParams(params);

			// // httpget.setHeader("Accept-Encoding", "gzip, deflate");
			// httpget.setHeader("User-Agent", userAgents[index]);
			// httpget.setHeader("Accept",
			// "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			// // httpget.setHeader("Referer", "https://www.baidu.com/");

			ResponseHandler<String> rh = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws IOException {
					StatusLine statusLine = response.getStatusLine();
					HttpEntity entity = response.getEntity();
					if (statusLine.getStatusCode() >= 300) {
						throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
					}
					if (entity == null) {
						return "";
					}

					return EntityUtils.toString(entity);

				}
			};

			result = httpClient.execute(httpget, rh);
			httpget.releaseConnection();
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to get data from ".concat(url) + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException when try to get data from ".concat(url) + " " + e.getMessage());
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException when try to get data from ".concat(url) + e.getMessage());
		}

		return result;

	}
	//
	// public static void postFile(String url, String filePath, String fileName)
	// {
	//
	// HttpClient httpclient = new DefaultHttpClient();
	//
	// try {
	//
	// HttpPost httppost = new HttpPost(url);
	//
	// FileBody bin = new FileBody(new File(filePath));
	//
	// StringBody comment = new StringBody(fileName);
	//
	// MultipartEntity reqEntity = new MultipartEntity();
	//
	// reqEntity.addPart("file", bin);// file1为请求后台的File upload;属性
	// reqEntity.addPart("filename", comment);// filename1为请求后台的普通参数;属性
	// httppost.setEntity(reqEntity);
	//
	// HttpResponse response = httpclient.execute(httppost);
	//
	// int statusCode = response.getStatusLine().getStatusCode();
	//
	// if (statusCode == HttpStatus.SC_OK) {
	//
	// System.out.println("服务器正常响应.....");
	//
	// HttpEntity resEntity = response.getEntity();
	//
	// System.out.println(EntityUtils.toString(resEntity));//
	// httpclient自带的工具类读取返回数据
	//
	// System.out.println(resEntity.getContent());
	//
	// EntityUtils.consume(resEntity);
	// }
	//
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// try {
	// httpclient.getConnectionManager().shutdown();
	// } catch (Exception ignore) {
	//
	// }
	// }
	//
	// }

	public static String getResponseContentType(String url) {
		HttpResponse response = doGetResponse(url, null, null, false);

		if (response == null || response.getFirstHeader("Content-Type") == null) {

			return parserContentEncoding(null);
		}
		String contentType = response.getFirstHeader("Content-Type").getValue();

		return parserContentEncoding(contentType);
	}

	public static String parserContentEncoding(String contentType) {
		// String encoding = "GBK";
		String encoding = "UTF-8";
		if (EweblibUtil.isValid(contentType)) {

			if (contentType.contains("=")) {
				String resEncoding = contentType.split("=")[1];

				if (EweblibUtil.isValid(resEncoding)) {
					encoding = resEncoding;
				}

			}
		}

		return encoding;
	}

	public static HttpResponse doGetResponse(String url, Map<String, Object> parameters, String encoding,
			boolean redirect) {
		HttpClient httpClient = new DefaultHttpClient();

		HttpResponse response = null;
		//
		try {

			if (url.contains("?")) {
				String[] urls = url.split("\\?");

				if (EweblibUtil.isValid(urls[1])) {
					// if (EweblibUtil.isValid(encoding)) {
					// url = urls[0] + "?" + URLEncoder.encode(urls[1],
					// encoding);
					// } else {
					// url = urls[0] + "?" + URLEncoder.encode(urls[1]);
					// }
				}
			}
			HttpGet httpget = null;
			URIBuilder builder = new URIBuilder(url);
			if (parameters != null) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {

					if (parameters.get(key) != null) {
						builder.setParameter(key, parameters.get(key).toString());
					}
				}
				URI uri = builder.build();
				httpget = new HttpGet(uri);
			} else {
				// builder.
				httpget = new HttpGet(url);
			}

			int index = EweblibUtil.generateRandom(0, userAgents.length);

			HttpParams params = new BasicHttpParams();
			params.setParameter("http.protocol.handle-redirects", redirect);

			if (encoding != null) {
				params.setParameter(HttpProtocolParams.HTTP_CONTENT_CHARSET, encoding);
			}

			// HttpMethodRetryHandle retryHandler = new
			// DefaultHttpMethodRetryHandler(0, false);
			// params.setParameter(HttpProtocolParams.RETRY_HANDLER,
			// retryHandler);
			params.setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
			HttpConnectionParams.setConnectionTimeout(params, 5000);

			httpget.setParams(params);

			// httpget.setHeader("Accept-Encoding", "gzip, deflate");
			httpget.setHeader("User-Agent", userAgents[index]);
			httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			// httpget.setHeader("Referer", "https://www.baidu.com/");

			response = httpClient.execute(httpget);

			httpget.releaseConnection();
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to get data from ".concat(url) + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException when try to get data from ".concat(url) + " " + e.getMessage());
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException when try to get data from ".concat(url) + e.getMessage());
		}
		return response;
	}

	/**
	 * 
	 * Request a get request with data paramter
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String doGet(String url, Map<String, Object> parameters) {
		return doGet(url, parameters, null, true);

	}

	/**
	 * 
	 * Request a get request with data paramter
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String doGet(String url, Map<String, Object> parameters, boolean redirect) {
		return doGet(url, parameters, null, redirect);

	}

	public static String doPost(String url, Map<String, Object> parameters, String urlEncoding) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpPost method = new HttpPost(url);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		if (parameters != null) {
			Set<String> keys = parameters.keySet();
			for (String key : keys) {

				if (parameters.get(key) != null) {
					nameValuePairs.add(new BasicNameValuePair(key, parameters.get(key).toString()));
				}

			}
		}
		UrlEncodedFormEntity rentity = null;
		try {
			rentity = new UrlEncodedFormEntity(nameValuePairs, urlEncoding);
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException when try to encode data for ".concat(url), e);
		}
		try {
			method.setEntity(rentity);
			response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to post data to ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to post data to ".concat(url), e);
		}
		return null;
	}

	public static String doPost(String url, Map<String, Object> parameters) {
		return doPost(url, parameters, "UTF-8");
	}

	public static String doBodyPost(String url, String data) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpPost method = new HttpPost(url);

		method.addHeader("Content-Type", "application/json");
		if (data == null) {
			data = "";
		}

		try {
			StringEntity stringEntity = new StringEntity(data, "UTF-8");

			method.setEntity(stringEntity);
			response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to post data to ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to post data to ".concat(url), e);
		}
		return null;
	}

	public static byte[] doBodyBytePost(String url, byte[] data) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = null;
		HttpPost method = new HttpPost(url);

		try {
			ByteArrayEntity bae = new ByteArrayEntity(data);
			method.setEntity(bae);
			response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toByteArray(entity);
			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to post data to ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to post data to ".concat(url), e);
		}
		return null;
	}

	public static void downloadFile(String url, Map<String, Object> parameters, String savePath) {

		downloadFile(url, parameters, savePath, "UTF-8");
	}

	public static void downloadFile(String url, Map<String, Object> parameters, String savePath, String urlEncoding) {

		//
		new File(savePath).getParentFile().mkdirs();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = null;
			URIBuilder builder = new URIBuilder(url);
			if (parameters != null) {
				Set<String> keys = parameters.keySet();
				for (String key : keys) {

					if (parameters.get(key) != null) {
						builder.setParameter(key, parameters.get(key).toString());
					}
				}
			}
			URI uri = builder.build();

			if (EweblibUtil.isValid(urlEncoding)) {
				url = URLEncoder.encode(url, urlEncoding);
			}
			// builder.
			HttpGet httpget = new HttpGet(uri);

			response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				entity.writeTo(new FileOutputStream(new String(savePath.getBytes(), "gbk")));

			}

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException when try to get data from ".concat(url), e);
		} catch (IOException e) {
			logger.error("IOException when try to get data from ".concat(url), e);
		} catch (URISyntaxException e) {
			logger.error("URISyntaxException when try to get data from ".concat(url), e);
		}

	}


	public static Map<String, Object> convertLngAndLatToBaidu(Double lng, Double lat, String key, String from) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("from", from);
		params.put("output", "json");
		if (key == null) {
			params.put("ak", ConfigManager.getProperty("baidu_map_key"));
		} else {
			params.put("ak", key);
		}

		params.put("coords", lng + "," + lat);

		Map<String, Object> location = new HashMap<String, Object>();
		Map<String, Object> result = null;
		try {
			String res = HttpClientUtil.doPost("http://api.map.baidu.com/geoconv/v1/", params);
			result = EweblibUtil.toMap(res);

			if (result != null && !EweblibUtil.isEmpty(result.get("result"))) {
				List<Map<String, Object>> locationResult = (List<Map<String, Object>>) result.get("result");
				if (!EweblibUtil.isEmpty(locationResult)) {
					location = (Map<String, Object>) locationResult.get(0);

				}

			}
		} catch (Exception e) {
			logger.error(e);
			throw new ResponseException(e.getMessage());
		}

		if (result != null) {
			Object status = result.get("status");
			if (status == null
					|| !(status.toString().equalsIgnoreCase("0.0") || status.toString().equalsIgnoreCase("0"))) {
				throw new ResponseException("百度地图异常，返回状态码: " + status
						+ " , 请查阅http://developer.baidu.com/map/webservice-geocoding.htm, 8.返回码状态表");
			}
		}
		return location;
	}

	public static Map<String, Object> getAddressByLngAndLat(String lng, String lat, String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", lng + "," + lat);
		params.put("output", "json");
		params.put("coord_type", "bd09ll");
		if (key == null) {
			params.put("ak", ConfigManager.getProperty("baidu_map_key"));
		} else {
			params.put("ak", key);
		}
		String res = null;
		try {
			res = HttpClientUtil.doGet("http://api.map.baidu.com/telematics/v3/reverseGeocoding", params);
		} catch (Exception e) {
			logger.error(e);
		}

		Map<String, Object> result = EweblibUtil.toMap(res);

		return result;
	}

	public static Map<String, Object> getAddressByLngAndLatV2(String lng, String lat, String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("location", lat + "," + lng);
		params.put("output", "json");
		params.put("coord_type", "bd09ll");
		params.put("pois", "0");
		if (key == null) {
			params.put("ak", ConfigManager.getProperty("baidu_map_key"));
		} else {
			params.put("ak", key);
		}
		String res = null;
		try {
			res = HttpClientUtil.doGet("http://api.map.baidu.com/geocoder/v2/", params);
		} catch (Exception e) {
			logger.error(e);
		}

		Map<String, Object> result = EweblibUtil.toMap(res);
		System.out.println(result.get("result"));

		return result;
	}

}
