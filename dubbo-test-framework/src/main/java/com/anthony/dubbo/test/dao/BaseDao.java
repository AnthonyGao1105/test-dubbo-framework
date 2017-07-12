package com.anthony.dubbo.test.dao;


import org.apache.ibatis.session.SqlSession;

/**
 * 数据库的增删改查，给每个模块的DAO调用
 * @author anthony
 * @date Jul 7, 2017
 * @updateTime 2:49:40 PM
 */
public class BaseDao {
	SqlSession session = DaoUtils.getSession().openSession();

	/**
	 * add statement
	 * 
	 * @param statement
	 * @param key
	 * @return
	 */
	public int insert(String statement, Object key) {
		SqlSession session = DaoUtils.getSession().openSession();
		try {
			int result = session.insert(statement, key);
			// Insert的时候,必须session.commit();
			session.commit();
			return result;
		} finally {
			session.close();
		}
	}

	/**
	 * delete statement
	 * 
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int delete(String statement, Object key) {
		SqlSession session = DaoUtils.getSession().openSession();
		try {
			int result = session.delete(statement, key);
			// Delete的时候,必须session.commit();
			session.commit();
			return result;
		} finally {
			session.close();
		}
	}

	/**
	 * update statement
	 * 
	 * @param statement
	 * @param key
	 * @return
	 */
	public int update(String statement, Object key) {
		SqlSession session = DaoUtils.getSession().openSession();
		try {
			int result = session.delete(statement, key);
			// Update的时候,必须session.commit();
			session.commit();
			return result;
		} finally {
			session.close();
		}
	}

	/**
	 * search statement
	 * 
	 * @param statement
	 * @param key
	 * @return
	 */
	public Object query(String statement, Object key) {
		SqlSession session = DaoUtils.getSession().openSession();
		try {
			Object result = session.selectOne(statement, key);
			return (Object) result;
		} finally {
			session.close();
		}
	}

}
