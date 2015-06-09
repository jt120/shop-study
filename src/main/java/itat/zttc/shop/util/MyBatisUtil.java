package itat.zttc.shop.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
	private static SqlSessionFactory factory;
	static {
		try {
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			factory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession createSession() {
		return factory.openSession();
	}
	
	public static void closeSession(SqlSession session) {
		if(session!=null) session.close();
	}

    public static void main(String[] args) throws Exception {
        SqlSession session = createSession();
        closeSession(session);
    }
}
