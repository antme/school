package com.wx.school.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.eweblib.cfg.ConfigManager;
import com.eweblib.util.EweblibUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

@Service
public class RedisSingleService {
	public static Logger logger = LogManager.getLogger(RedisSingleService.class);

	private Pool<Jedis> jedisPool;// 非切片连接池

	public RedisSingleService() {
		initialPool();
	}

	private void initialPool() {

		if (jedisPool == null) {
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(-1);
			config.setMaxIdle(200);
			config.setMinIdle(50);
			config.setMaxWaitMillis(200);
			config.setTestOnBorrow(false);
			config.setTestOnReturn(false);
			config.setTestWhileIdle(true);
			config.setMinEvictableIdleTimeMillis(60000);
			config.setTimeBetweenEvictionRunsMillis(60000);
			config.setNumTestsPerEvictionRun(50);

			jedisPool = new JedisPool(config, ConfigManager.getProperty("redis_server"), 6379, 100);

		}
	}

	public String getApMac(String key) {
		long start = new Date().getTime();
		String upKey = key.toUpperCase();
		String field = "advertise";
		String value = null;
		try {
			value = get(upKey, field);
		} catch (Exception e) {

		}
		logger.debug("query ap mac info [{}] from redis server for user mac[{}] and cost {}", value, key,
				new Date().getTime() - start);
		return value;
	}

	public String getSubUsers(String userName) {
		String key = "kid_account_" + userName;
		String value = null;
		try {
			value = get(key, null);
		} catch (Exception e) {

		}
		return value;
	}

	public Map<String, String> getAll(String key) {
		Map<String, String> value = new HashMap<String, String>();
		Jedis jedis = null;
		try {
			long start = new Date().getTime();

			jedis = jedisPool.getResource();

			value = jedis.hgetAll(key);
			long end = new Date().getTime();

			if (end - start > 30) {
				logger.fatal("getAllApMacInfo: query data from redis cost: {}  with key: {}", end - start, key);
			}
		} catch (Exception e) {
			logger.fatal("query from cloud redis server failed with  key {} ", key, e);
		} finally {
			if (jedis != null) {
				try {
					jedis.close();
				} catch (Exception e) {
					logger.fatal("release  redis connect resource failed", e);
				}
			}
		}

		return value;
	}

	public String get(String key, String field) throws Exception {
		Jedis jedis = null;
		String value = null;
		try {
			long start = new Date().getTime();
			jedis = jedisPool.getResource();
			if (field != null) {
				value = jedis.hget(key, field);
			} else {
				value = jedis.get(key);
			}

			long end = new Date().getTime();

			if (end - start > 30) {
				logger.fatal("query data from redis cost: {}  with key: {}", end - start, key);
			}
		} catch (Exception e) {
			logger.fatal("get value from redis failed with key: " + key, e);
			throw new Exception(e);
		} finally {
			close(jedis);
		}
		return value;
	}

	public Integer getInt(String key, String field) {

		String value = null;
		try {
			value = get(key, field);
		} catch (Exception e) {
			// 返回NULL的时候代表redis 异常， 调用方需要自行处理异常情况
			return null;
		}

		return EweblibUtil.getInteger(value, 0);

	}

	public Long getLong(String key, String field) {
		String value = null;
		try {
			value = get(key, field);
		} catch (Exception e) {
			// 返回NULL的时候代表redis 异常， 调用方需要自行处理异常情况
			return null;
		}

		Long result = EweblibUtil.getLong(value);
		if (result == null) {
			result = 0l;
		}

		return result;
	}

	public void remove(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			logger.fatal("remove redis with key: " + key + " failed", e);
		} finally {
			close(jedis);
		}

	}

	public void setAll(String key, Map<String, String> values) {
		Jedis jedis = null;

		try {
			long start = new Date().getTime();
			jedis = jedisPool.getResource();
			jedis.hmset(key, values);
			long end = new Date().getTime();
			if (end - start > 30) {
				logger.fatal("set data to redis cost: {} of key {}", end - start, key);
			}

		} catch (Exception e) {
			logger.fatal("save data to  redis  failed", e);
		} finally {
			close(jedis);
		}
	}

	public void set(String key, Object value, String field) {
		Jedis jedis = null;

		try {
			long start = new Date().getTime();
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value.toString());
			long end = new Date().getTime();
			if (end - start > 30) {
				logger.fatal("set data to redis cost: {} ", end - start);
			}

		} catch (Exception e) {
			logger.fatal("save data to  redis  failed", e);
		} finally {
			close(jedis);
		}
	}

	public void expire(String key, int seconds) {
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			jedis.expire(key, seconds);

		} catch (Exception e) {
			logger.fatal("save data to  redis  failed", e);
		} finally {
			close(jedis);
		}
	}

	private void close(Jedis jedis) {
		try {
			if (jedis != null) {
				jedis.close();
			}
		} catch (Exception e) {
			logger.fatal("close  redis connect failed", e);
		}
	}

}
