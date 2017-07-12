package com.anthony.dubbo.test.dao;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * @author anthony
 * @date Jul 7, 2017
 * @updateTime 5:02:32 PM
 */
public class DaoUtils {
	public static enum DataSourceEnvironment {
		development, developmentPay;
	}

	private static Reader reader;
	private static final Map<DataSourceEnvironment, SqlSessionFactory> sqlSessionFactoryMap = new HashMap<DataSourceEnvironment, SqlSessionFactory>();

	static {
		try {
			reader = Resources.getResourceAsReader("mybatis/Configuration.xml");
			new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static SqlSessionFactory getSession(DataSourceEnvironment environment) {
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryMap.get(environment);

		if (!Objects.isNull(sqlSessionFactory))
			return sqlSessionFactory;
		else {
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream("mybatis/Configuration.xml");
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, environment.name());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
			sqlSessionFactoryMap.put(environment, sqlSessionFactory);
			return sqlSessionFactory;
		}
	}

	// 默认的session
	public static SqlSessionFactory getSession() {
		return DaoUtils.getSession(DataSourceEnvironment.development);
	}

}
