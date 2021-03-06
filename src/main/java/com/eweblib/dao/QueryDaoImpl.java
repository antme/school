package com.eweblib.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Table;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.eweblib.bean.BaseEntity;
import com.eweblib.bean.vo.EntityResults;
import com.eweblib.bean.vo.OrderBy;
import com.eweblib.bean.vo.Pagination;
import com.eweblib.constants.WebConstants;
import com.eweblib.dbhelper.DataBaseQueryBuilder;
import com.eweblib.exception.ResponseException;
import com.eweblib.util.EWeblibThreadLocal;
import com.eweblib.util.EweblibUtil;

@Repository(value = "commonDao")
public class QueryDaoImpl implements IQueryDao {
	private static Logger logger = LogManager.getLogger(QueryDaoImpl.class);

	@Autowired
	public IMyBatisDao dao;

	public IMyBatisDao getDao() {
		return dao;
	}

	public void setDao(IMyBatisDao dao) {
		this.dao = dao;
	}

	@Override
	public BaseEntity insert(BaseEntity entity) {

		meregeEntityValue(entity);

		int result = dao.insert(entity);

		if (result == 1) {

		} else {
			throw new ResponseException("保存失败");
		}


		return entity;

	}

	public void meregeEntityValue(BaseEntity entity) {
		if (EweblibUtil.isEmpty(entity.getId())) {
			entity.setId(UUID.randomUUID().toString());
		}

		if (EweblibUtil.isEmpty(entity.getCreatedOn())) {
			entity.setCreatedOn(new Date());
		}

		if (EweblibUtil.isEmpty(entity.getUpdatedOn())) {
			entity.setUpdatedOn(new Date());
		}
		entity.setCreatorId(EWeblibThreadLocal.getCurrentUserId());
	}

	public <T extends BaseEntity> void batchInsert(List<T> entityList, String table) {
		if (entityList.size() > 0) {
			String insertColumnsExp = null;
			String insertColumns = null;
			String batchInsertColumnsDefine = null;
			BaseEntity entity = entityList.get(0);

			insertColumns = entity.getInsertColumns();

			insertColumnsExp = entity.getInsertColumnsExp();

			if (table == null) {
				table = entity.getTable();
			}

			batchInsertColumnsDefine = entity.getBatchInsertColumnsExp();

			for (BaseEntity en : entityList) {
				meregeEntityValue(en);

			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("table", table);
			data.put("insertColumns", insertColumns);
			data.put("batchInsertColumnsExp", batchInsertColumnsDefine);
			data.put("insertColumnsExp", insertColumnsExp);
			data.put("list", entityList);

			dao.batchInsert(data);
		}
	}

	public <T extends BaseEntity> void batchInsert(List<T> entityList) {
		if (entityList.size() > 0) {
		    int size = 100000;
            int threads = entityList.size() / size;
            for (int i = 0; i < threads; i++) {
                List<T> tlist = entityList.subList(i * size, (i + 1) * size);
                batchInsert(tlist, entityList.get(0).getTable());
            }
            List<T> tlist = entityList.subList(threads * size, entityList.size());
            if (tlist.size() > 0) {
                batchInsert(tlist, entityList.get(0).getTable());
            }
			//batchInsert(entityList, entityList.get(0).getTable());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> List<T> listByQuery(DataBaseQueryBuilder builder, Class<T> classzz) {

		builder = mergeQueryBuilder(builder, classzz);

		List<Map<String, Object>> results = dao.listByQuery(builder);

		return mergeListValue(classzz, results, builder.getLimitColumnNames());

	}

	public <T extends BaseEntity> List<T> listBySql(String sql, Class<T> classzz) {

		if (EWeblibThreadLocal.get(WebConstants.PAGENATION) != null) {
			Pagination page = (Pagination) EWeblibThreadLocal.get(WebConstants.PAGENATION);

		}

		List<Map<String, Object>> results = dao.listBySql(sql);

		List<T> entityList = mergeListValue(classzz, results, null);
		return entityList;
	}

	public <T extends BaseEntity> List<T> mergeListValue(Class<T> classzz, List<Map<String, Object>> results, Set<String> keys) {
		List<T> entityList = new ArrayList<T>();

		for (Map<String, Object> result : results) {
			if (result == null) {
				result = new HashMap<String, Object>();
			}

			mergeEntityValue(classzz, keys, result);
			entityList.add((T) EweblibUtil.toEntity(result, classzz));
		}
		return entityList;
	}

	public <T extends BaseEntity> void mergeEntityValue(Class<T> classzz, Set<String> keys, Map<String, Object> result) {

		if (keys == null || keys.isEmpty()) {

			if (result != null) {
				keys = result.keySet();

			}
		}
		if (keys != null) {
			for (String key : keys) {

				try {
					String className = classzz.getField(key).getType().getName();
					if (className.equalsIgnoreCase("java.lang.String") || className.equalsIgnoreCase("java.util.Date")) {
						if (result != null && result.get(key) == null) {
							result.put(key, "");
						}
					}
				} catch (NoSuchFieldException e) {
					// do nothing
				} catch (SecurityException e) {
					// do nothing
				}

				if (result != null && result.get(key) != null && result.get(key) instanceof byte[]) {
					byte[] b = (byte[]) result.get(key);
					try {
						result.put(key, new String(b, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						throw new RuntimeException("Blob Encoding Error!");
					}
				}

			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseEntity> EntityResults<T> listByQueryWithPagnation(DataBaseQueryBuilder builder, Class<T> classzz) {

		builder = mergeQueryBuilder(builder, classzz);

		List<Map<String, Object>> results = dao.listByQueryWithPagination(builder);

		List<T> entityList = mergeListValue(classzz, results, builder.getLimitColumnNames());

		return toListBean(builder, entityList);

	}

	private <T extends BaseEntity> DataBaseQueryBuilder mergeQueryBuilder(DataBaseQueryBuilder builder, Class<T> classzz) {

		if (builder == null) {

			Table table = classzz.getAnnotation(Table.class);
			builder = new DataBaseQueryBuilder(table.name());
		}
		if (EWeblibThreadLocal.get(WebConstants.PAGENATION) != null) {
			builder.pagination((Pagination) EWeblibThreadLocal.get(WebConstants.PAGENATION));
		}
		if (EWeblibThreadLocal.get(WebConstants.DB_QUERY_ORDER_BY) != null) {
			OrderBy order = (OrderBy) EWeblibThreadLocal.get(WebConstants.DB_QUERY_ORDER_BY);
			
			builder.clearOderBy();
			
			if (order.getOrder().equalsIgnoreCase("asc")) {
				builder.orderBy(order.getSort(), true);
			} else {
				builder.orderBy(order.getSort(), false);
			}

		}
		if (EweblibUtil.isEmpty(builder.getOrderBy()) && !builder.isDisableOrder()) {
			builder.orderBy(BaseEntity.CREATED_ON, false);
		}
		return builder;
	}


	
	public <T extends BaseEntity> void updateById(BaseEntity entity, String[] columns) {

		if (EweblibUtil.isEmpty(entity.getId())) {
			throw new IllegalArgumentException("Must have id value when call updateById method");
		}

		entity.setUpdatedOn(new Date());

		if (columns == null || columns.length == 0) {
			throw new IllegalArgumentException("Must set update column names");

		}
		Map<String, Object> data = entity.toMap();
		DataBaseQueryBuilder query = new DataBaseQueryBuilder(entity.getTable());
		query.and(BaseEntity.ID, entity.getId());

		for (String column : columns) {

			query.update(column, data.get(column));

		}
		this.dao.updateByQuery(query);

	}
	
	
	public void executeSql(String sql) {

		dao.executeSql(sql);
	}

	@Override
	public void updateByQuery(DataBaseQueryBuilder builder) {

		if (EweblibUtil.isValid(builder.getQueryStr()) && EweblibUtil.isValid(builder.getUpdateColumns())) {
			dao.updateByQuery(builder);
		}

	}

	@Override
	public void deleteById(BaseEntity entity) {
		dao.deleteById(entity);
	}

	public void deleteAllByTableName(String table) {
		dao.deleteAllByTableName(table);
	}

	public void deleteByQuery(DataBaseQueryBuilder builder) {
		dao.deleteByQuery(builder);
	}

	@Override
	public <T extends BaseEntity> T findOneByQuery(DataBaseQueryBuilder builder, Class<T> classzz) {
		Map<String, Object> result = dao.findOneByQuery(builder);

		mergeEntityValue(classzz, builder.getLimitColumnNames(), result);

		return EweblibUtil.toEntity(result, classzz);
	}

	public <T extends BaseEntity> T findById(String id, String table, Class<T> classzz) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(BaseEntity.ID, id);

		return this.findOneByQuery(builder, classzz);

	}

	public <T extends BaseEntity> T findByKeyValue(String key, Object value, String table, Class<T> classzz) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(key, value);
		return this.findOneByQuery(builder, classzz);
	}

	public int count(DataBaseQueryBuilder builder) {
		return dao.count(builder);
	}

	private <T extends BaseEntity> EntityResults<T> toListBean(DataBaseQueryBuilder builder, List<T> entityList) {

		EntityResults<T> listBean = new EntityResults<T>();
		listBean.setEntityList(entityList);

		int total = dao.count(builder);

		Pagination pagnation = new Pagination();
		pagnation.setTotal(total);
		listBean.setPagnation(pagnation);
		return listBean;

	}

	public boolean exists(DataBaseQueryBuilder builder) {

		if (this.count(builder) > 0) {
			return true;
		}
		return false;
	}

	public boolean exists(String key, String value, String table) {
		DataBaseQueryBuilder builder = new DataBaseQueryBuilder(table);
		builder.and(key, value);
		return this.exists(builder);
	}

	public <T extends BaseEntity> List<T> distinctQuery(DataBaseQueryBuilder builder, Class<T> classzz) {
		return listByQuery(builder, classzz);
	}

	public <T extends BaseEntity, T1 extends BaseEntity> List<T> callListProcedure(BaseEntity queryEntity, Class<T> targetClasszz, Class<T1> tempClasszz, String procedure) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (queryEntity != null) {
			parameters = queryEntity.toMap();
			EweblibUtil.updateJsonFieldWithType(parameters, queryEntity.getClass());
		}

		// parameters.put("P_STARTDATE", DateUtil.getDateTime("2014-08-12"));
		// parameters.put("P_ENDDATE", DateUtil.getDateTime("2014-08-13"));
		parameters.put("procedure", procedure);

		Map<String, Object> data = new HashMap<String, Object>();

		parameters.put("P_CURSOR", data);

		// System.out.println("procedure query start");
		long start = new Date().getTime();

		this.dao.callListProcedure(parameters);

		System.out.println("call procedue " + procedure + " cost: " + (new Date().getTime() - start));
		// System.out.println(parameters);

		if (tempClasszz != null) {
			EweblibUtil.toJsonList(parameters, tempClasszz, "P_CURSOR");
		}

		return EweblibUtil.toJsonList(parameters, targetClasszz, "P_CURSOR");

	}

	public <T extends BaseEntity, T1 extends BaseEntity> BaseEntity callEntityProcedure(BaseEntity queryEntity, Class<T> targetClasszz, Class<T1> tempClasszz, String procedure) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (queryEntity != null) {
			parameters = queryEntity.toMap();
		}

		parameters.put("procedure", procedure);

		this.dao.callEntityProcedure(parameters);
		EweblibUtil.toEntity(parameters, tempClasszz);

		return EweblibUtil.toEntity(parameters, targetClasszz);
	}
//
//	public void createUpdateLog(String userId, BaseEntity entity, BaseEntity old) {
//		if (ConfigManager.enableLog()) {
//			if (EWeblibThreadLocal.get(ControllerFilter.URL_PATH) != null && !(entity instanceof Log) && !(entity instanceof LogItem)) {
//				if (EweblibUtil.isEmpty(userId)) {
//					userId = EWeblibThreadLocal.getCurrentUserId();
//				}
//
//				if (EweblibUtil.isValid(userId)) {
//
//					String path = EWeblibThreadLocal.get(ControllerFilter.URL_PATH).toString();
//
//					Log log = new Log();
//					log.setUserId(userId);
//					log.setUrlPath(path);
//					log.setLogType("update");
//					log.setTableName(entity.getTable());
//					log.setDataId(old.getId());
//
//					List<LogItem> list = new ArrayList<LogItem>();
//
//					Map<String, Object> map = entity.toMap();
//					map.remove("updatedOn");
//
//					Map<String, Object> oldMap = old.toMap();
//
//					for (String key : map.keySet()) {
//
//						if (entity.containsDbColumn(key)) {
//
//							Object ovalue = oldMap.get(key);
//							if (ovalue == null) {
//								ovalue = "";
//							}
//							if (ovalue == null || !ovalue.toString().equalsIgnoreCase(map.get(key).toString())) {
//								LogItem item = new LogItem();
//								item.setField(key);
//								item.setOldValue(ovalue.toString());
//								item.setNewValue(map.get(key).toString());
//								item.setTableName(log.getTableName());
//								list.add(item);
//							}
//
//						}
//					}
//
//					if (list.size() > 0) {
//						insert(log);
//						for (LogItem item : list) {
//							item.setLogId(log.getId());
//							insert(item);
//						}
//					}
//
//				}
//			}
//		}
//
//	}
//
//	public void createAddLog(String userId, BaseEntity entity) {
//
//		if (ConfigManager.enableLog()) {
//			if (EWeblibThreadLocal.get(ControllerFilter.URL_PATH) != null && !(entity instanceof Log) && !(entity instanceof LogItem)) {
//				if (EweblibUtil.isEmpty(userId)) {
//					userId = EWeblibThreadLocal.getCurrentUserId();
//				}
//
//				if (EweblibUtil.isValid(userId)) {
//					Log log = new Log();
//					log.setUserId(userId);
//					log.setUrlPath(EWeblibThreadLocal.get(ControllerFilter.URL_PATH).toString());
//					log.setLogType("add");
//					log.setTableName(entity.getTable());
//					log.setDataId(entity.getId());
//					insert(log);
//
//					Map<String, Object> map = entity.toMap();
//					map.remove("updatedOn");
//					map.remove("createdOn");
//					for (String key : map.keySet()) {
//
//						if (entity.containsDbColumn(key)) {
//							LogItem item = new LogItem();
//							item.setLogId(log.getId());
//							item.setField(key);
//							item.setOldValue(null);
//							item.setNewValue(map.get(key).toString());
//							item.setTableName(log.getTableName());
//							insert(item);
//						}
//					}
//				}
//			}
//		}
//
//	}

}
