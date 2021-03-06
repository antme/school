package com.eweblib.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.eweblib.annotation.column.BigDecimalColum;
import com.eweblib.annotation.column.BooleanColumn;
import com.eweblib.annotation.column.CopyColum;
import com.eweblib.annotation.column.DateColumn;
import com.eweblib.annotation.column.DoubleColumn;
import com.eweblib.annotation.column.FloatColumn;
import com.eweblib.annotation.column.IntegerColumn;
import com.eweblib.annotation.column.LongColumn;
import com.eweblib.annotation.column.ObjectColumn;
import com.eweblib.bean.BaseEntity;
import com.eweblib.cfg.ConfigManager;
import com.eweblib.exception.ResponseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class EweblibUtil {

	private static Logger logger = LogManager.getLogger(EweblibUtil.class);

	public static Map<String, VelocityEngine> engineMap = new HashMap<String, VelocityEngine>();

	static JsonSerializer<Date> ser = new JsonSerializer<Date>() {
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? new JsonPrimitive("") : new JsonPrimitive(DateUtil.getDateStringByLong(src.getTime()));
		}
	};

	static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return DateUtil.getDateTime(json.getAsString());
		}
	};

	private static final Gson GSON = new Gson();

	private static final Gson G_CREATOR = new GsonBuilder().registerTypeAdapter(Date.class, ser)
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	private static final Gson D_CREATER = new GsonBuilder().registerTypeAdapter(Date.class, deser).create();

	public static Gson C_CREATOR = new GsonBuilder().registerTypeAdapter(Date.class, ser).create();

	public static String concat(String symbole, String[] concats) {

		StringBuffer sb = new StringBuffer();

		int i = 0;
		for (String concat : concats) {
			if (i == concats.length - 1) {
				sb.append(concat);
			} else {
				sb.append(concat).append(symbole);
			}
		}
		return sb.toString();
	}

	public static boolean isEmpty(Object param) {

		if (param == null) {
			return true;
		}

		if (param instanceof Collection) {
			return ((Collection<?>) param).isEmpty();
		}

		if (param instanceof Map) {
			return ((Map<?, ?>) param).isEmpty();
		}

		if (param.getClass().isArray()) {
			return Array.getLength(param) == 0;
		}
		String parameter = param.toString().trim();
		if ("null".equalsIgnoreCase(parameter) || "undefined".equalsIgnoreCase(parameter)) {
			return true;
		}
		return StringUtils.isBlank(parameter);
	}

	public static void requireNonNull(Object ob, String msg) {
		if (isEmpty(ob))
			throw new RuntimeException(msg);
	}

	public static Integer getInteger(Object value, Integer defaultValue) {
		Integer result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = (int) Float.parseFloat(String.valueOf(value));
			} catch (NumberFormatException e) {
				try {
					result = Integer.parseInt(String.valueOf(value));
				} catch (NumberFormatException e1) {
					logger.error(String.format("Integer parameter illegal [%s]", value));

				}
			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static Float getFloat(Object value, Float defaultValue) {
		Float result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = Float.parseFloat(String.valueOf(value));
			} catch (NumberFormatException e) {

				logger.error(String.format("Double parameter illegal [%s]", value), e);
				throw new ResponseException("ILEGAL_PARAMTERS");

			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static Long getLong(Object value, Long defaultValue) {
		Long result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = Long.parseLong(String.valueOf(value));
			} catch (NumberFormatException e) {

				logger.error(String.format("Long parameter illegal [%s]", value), e);
				throw new ResponseException("ILEGAL_PARAMTERS");

			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static Double getDouble(Object value, Double defaultValue) {
		Double result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = Double.parseDouble(String.valueOf(value));

			} catch (NumberFormatException e) {

				logger.error(String.format("Integer parameter illegal [%s]", value), e);

			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static BigDecimal getBigDecimal(Object value, BigDecimal defaultValue) {
		BigDecimal result = null;

		if (isEmpty(value)) {
			result = defaultValue;
		} else {
			try {
				result = new BigDecimal(value.toString());

			} catch (NumberFormatException e) {

				logger.error(String.format("BigDecimal parameter illegal [%s]", value), e);

			}

		}
		if (result == null)
			result = defaultValue;
		return result;
	}

	public static String getRandomNum(int num) {
		return RandomStringUtils.randomNumeric(num);
	}

	public static String join(String[] array) {
		return join(array, ",");
	}

	public static String join(List<String> array) {
		return join((String[]) array.toArray(), ",");
	}

	public static String join(Set<String> array) {
		return join((String[]) array.toArray(), ",");
	}

	/**
	 * Join all the elements of a string array into a single String.
	 * 
	 * If the given array empty an empty string will be returned. Null elements
	 * of the array are allowed and will be treated like empty Strings.
	 * 
	 * @param array
	 *            Array to be joined into a string.
	 * @param delimiter
	 *            String to place between array elements.
	 * @return Concatenation of all the elements of the given array with the the
	 *         delimiter in between.
	 * @throws NullPointerException
	 *             if array or delimiter is null.
	 * 
	 * @since ostermillerutils 1.05.00
	 */
	public static String join(String[] array, String delimiter) {
		// Cache the length of the delimiter
		// has the side effect of throwing a NullPointerException if
		// the delimiter is null.
		int delimiterLength = delimiter.length();
		// Nothing in the array return empty string
		// has the side effect of throwing a NullPointerException if
		// the array is null.
		if (array.length == 0)
			return "";
		// Only one thing in the array, return it.
		if (array.length == 1) {
			if (array[0] == null)
				return "";
			return array[0];
		}
		// Make a pass through and determine the size
		// of the resulting string.
		int length = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null)
				length += array[i].length();
			if (i < array.length - 1)
				length += delimiterLength;
		}
		// Make a second pass through and concatenate everything
		// into a string buffer.
		StringBuffer result = new StringBuffer(length);
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null)
				result.append(array[i]);
			if (i < array.length - 1)
				result.append(delimiter);
		}
		return result.toString();
	}

	public static <T extends BaseEntity> T toEntity(Map<String, Object> data, Class<T> classzz) {
		EweblibUtil.updateJsonFieldWithType(data, classzz);
		String json = G_CREATOR.toJson(data);
		return D_CREATER.fromJson(json, classzz);

	}

	public static <T extends BaseEntity> T toEntityNoCheck(String json, Class<T> classzz) {
		return D_CREATER.fromJson(json, classzz);
	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity> BaseEntity toEntity(String data, Class<T> classzz) {
		return toEntity(D_CREATER.fromJson(data, Map.class), classzz);

	}

	public static <T extends BaseEntity> List<T> toJsonList(Map<String, Object> params, Class<T> clz, String key) {

		Object data = params.get(key);
		return toJsonList(clz, data);

	}

	public static <T> T toJSonT(String s, Class<T> clazz) {

		return GSON.fromJson(s, clazz);
	}

	public static <T extends BaseEntity> List<T> toJsonList(Class<T> clz, Object data) {
		List<T> results = new ArrayList<T>();

		if (!EweblibUtil.isEmpty(data)) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if (data instanceof String) {

				list = (List<Map<String, Object>>) GSON.fromJson((String) data, List.class);
			} else if (data instanceof List) {
				list = (List<Map<String, Object>>) data;
			}

			for (Map<String, Object> obj : list) {
				updateJsonFieldWithType(obj, clz);
				results.add((T) EweblibUtil.toEntity(obj, clz));
			}

		}
		return results;
	}

	public static <T extends BaseEntity> List<T> toJsonList(Map<String, Object> params, Class<T> clz) {

		return toJsonList(params, clz, "rows");

	}

	public static <T extends BaseEntity> List<T> toJsonList(String data, Class<T> clz) {

		return toJsonList(clz, data);

	}

	public static String toJson(BaseEntity entity) {
		return C_CREATOR.toJson(entity);
	}

	public static String toJson(Object data) {
		return C_CREATOR.toJson(data);
	}

	public static Map<String, Object> toMap(BaseEntity entity) {
		return GSON.fromJson(entity.toString(), HashMap.class);
	}

	public static Map<String, Object> toMap(String jsonStr) {
		return GSON.fromJson(jsonStr, HashMap.class);
	}

	public static String toString(Map<String, Object> data) {
		return GSON.toJson(data);
	}

	public static String toString(Object data) {
		return GSON.toJson(data);
	}


	public static <T extends BaseEntity> void updateJsonFieldWithType(Map<String, Object> params, Class<?> clz) {

		if (params != null) {
			Field[] fields = clz.getFields();

			Set<String> set = new HashSet<String>();

			set.add("class java.lang.Integer");
			set.add("class java.lang.Boolean");
			set.add("class java.util.Date");
			set.add("class java.lang.Double");
			set.add("class java.lang.Float");
			set.add("class java.lang.Long");
			set.add("class java.math.BigDecimal");

			List<Field> flist = new ArrayList<Field>();
			for (Field f : fields) {

				String gtype = f.getGenericType().toString();
				if (set.contains(gtype)) {

					flist.add(f);
				}
			}

			for (Field field : flist) {
				String name = field.getName();
				Object object = params.get(name);

				if (object != null) {
					if (field.isAnnotationPresent(IntegerColumn.class)) {
						if (object != null) {
							params.put(name, getInteger(object, null));
						}
					}

					else if (field.isAnnotationPresent(LongColumn.class)) {
						if (object != null) {
							params.put(name, getLong(object, null));
						}
					}

					else if (field.isAnnotationPresent(DateColumn.class)) {
						if (object != null) {
							params.put(name, DateUtil.getDateTime(object.toString()));
						}
					}

					else if (field.isAnnotationPresent(FloatColumn.class)) {
						if (object != null) {
							params.put(name, getFloat(object, null));
						}
					}

					else if (field.isAnnotationPresent(DoubleColumn.class)) {
						if (object != null) {
							params.put(name, getDouble(object, null));
						}
					}

					else if (field.isAnnotationPresent(BigDecimalColum.class)) {
						if (object != null) {
							params.put(name, getBigDecimal(object, null));
						}
					}

					else if (field.isAnnotationPresent(BooleanColumn.class)) {

						if (params != null && field != null) {
							if (EweblibUtil.isValid(object)) {
								String text = object.toString();
								if (text.length() > 0) {
									if (text.equalsIgnoreCase("1") || text.equalsIgnoreCase("true")) {
										params.put(name, true);
									} else {
										params.put(name, false);
									}
								} else {
									params.put(name, null);
								}

							} else {
								params.put(name, null);
							}
						}
					}

					else if (field.isAnnotationPresent(ObjectColumn.class)) {
						Object v = object;

						if (EweblibUtil.isEmpty(v)) {
							params.remove(name);
						} else {
							if (v instanceof Map) {

								try {
									updateJsonFieldWithType((Map<String, Object>) v, Class
											.forName(field.getGenericType().toString().replaceAll("class", "").trim()));
								} catch (ClassNotFoundException e) {
									// do nothing
								}
							} else if (v instanceof List) {
								List<Map<String, Object>> list = (List<Map<String, Object>>) v;
								String className = field.getGenericType().toString().replaceAll("class", "").trim();
								className = className.replaceAll("java.util.List", "");

								className = className.replaceAll("<", "");
								className = className.replaceAll(">", "");
								for (Map<String, Object> data : list) {

									try {
										updateJsonFieldWithType(data, Class.forName(className.trim()));
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
										// do nothing
									}
								}

							}

						}
					}

					else if (field.isAnnotationPresent(CopyColum.class)) {
						if (object != null) {
							CopyColum cc = field.getAnnotation(CopyColum.class);
							params.put(cc.name(), object);
						}
					}

				}

			}
		}

	}

	public static Long getLongParam(Map<String, Object> params, String key) {
		if (params == null) {
			return null;
		}
		return getLong(params.get(key));
	}

	public static Long getLong(Object value) {
		Long result = null;
		if (isValid(value)) {
			try {
				if (value instanceof Number) {
					result = ((Number) value).longValue();
				} else {
					result = Long.parseLong(String.valueOf(value));
				}
			} catch (NumberFormatException e) {
				// throw new
				// ApiResponseCodeException(String.format("Long parameter
				// illegal [%s]",
				// value),
				// ResponseCodeConstants.NUMBER_PARAMETER_ILLEGAL);
			}
		}
		return result;
	}

	public static boolean isValid(Object param) {
		return !isEmpty(param);
	}

	private static final double EARTH_RADIUS = 6378137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;

	}

	/** 判断字符串是否为数字和字母组成 */
	public static boolean isCharNum(String str) {
		if (str != null) {
			return str.matches("^[A-Za-z0-9]+$");
		}
		return false;
	}

	public static String getUrl(String path) {

		return "http://".concat(ConfigManager.getProperty("WEI_XIN_HOST").toString()).concat("/").concat(path);
	}

	public static int generateRandom(int a, int b) {
		int temp = 0;

		if (a > b) {
			temp = new Random().nextInt(a - b);
			return temp + b;
		} else {
			temp = new Random().nextInt(b - a);
			return temp + a;
		}

	}

	/**
	 * @return 邮件主体
	 * @param model
	 *            向模版中传递的对象变量
	 * @param tempate
	 *            模版名
	 */
	public static String getContent(Map<String, Object> model, String template, String resourcePath) {

		if (!template.endsWith(".vm") && !template.contains(".")) {
			template = template.concat(".vm");
		}
		String key = resourcePath;
		VelocityEngine ve = null;
		if (engineMap.get(key) == null) {
			ve = new VelocityEngine(); // 配置模板引擎
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, resourcePath);// 模板文件所在的路径
			ve.setProperty(Velocity.INPUT_ENCODING, "UTF-8");// 处理中文问题
			ve.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");// 处理中文问题
			ve.init();// 初始化模板
			engineMap.put(key, ve);
		} else {
			ve = engineMap.get(key);
		}

		String result = "";
		try {

			result = VelocityEngineUtils.mergeTemplateIntoString(ve, template, "UTF-8", model);
		} catch (Exception e) {
		}
		return result;
	}

	public static Float floatToFixed(float value) {

		BigDecimal b = new BigDecimal(value);
		float f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		return f1;
	}

	public static Float floatToFixed(float value, int scale) {

		BigDecimal b = new BigDecimal(value);
		float f1 = b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();

		return f1;
	}

	public static boolean isUrlMatchIgnoreProtocol(String url1, String url2) {
		if (isEmpty(url1) || isEmpty(url2))
			return false;
		boolean ret = false;
		if (url1.length() > url2.length()) {
			ret = StringUtils.containsIgnoreCase(url1, removeUrlProtocol(url2));
		} else {
			ret = StringUtils.containsIgnoreCase(url2, removeUrlProtocol(url1));
		}
		return ret;
	}

	public static boolean isUrlMatchIgnoreProtocol(String[] urls1, String[] urls2) {
		Arrays.sort(urls1, LENGTH_ASC_COMPARATOR);
		Arrays.sort(urls2, LENGTH_DESC_COMPARATOR);
		boolean isMatch = false;
		loop: for (int i = 0; i < urls1.length; i++) {
			for (int j = 0; j < urls2.length; j++) {
				isMatch = isUrlMatchIgnoreProtocol(urls1[i], urls2[j]);
				if (isMatch)
					break loop;
			}
		}
		return isMatch;
	}

	private static final Comparator<String> LENGTH_ASC_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return StringUtils.length(o1) - StringUtils.length(o2);
		}
	};
	private static final Comparator<String> LENGTH_DESC_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			return StringUtils.length(o2) - StringUtils.length(o1);
		}
	};

	enum Protocol {
		HTTP, HTTPS, FTP, MAILTO, LDAP, FILE
	}

	private static final String PROTOCOL_SPLIT = "://";

	private static String removeUrlProtocol(String url, Protocol... protocols) {
		for (Protocol protocol : protocols) {
			url = StringUtils.removeStartIgnoreCase(url, protocol.name().concat(PROTOCOL_SPLIT));
		}
		return url;
	}

	private static String removeUrlProtocol(String url) {
		return removeUrlProtocol(url, Protocol.HTTP, Protocol.HTTPS);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double distance = GetDistance(121.38254370023, 31.239157976918, 121.41725835734, 31.248301067351);
		System.out.println(distance);
	}

}
