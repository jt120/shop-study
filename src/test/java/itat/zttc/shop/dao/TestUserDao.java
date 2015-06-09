package itat.zttc.shop.dao;


import com.sun.org.apache.bcel.internal.generic.IADD;
import itat.zttc.shop.test.BaseTest;
import itat.zttc.shop.model.*;
import itat.zttc.shop.util.DaoUtil;
import itat.zttc.shop.util.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TestUserDao extends BaseTest {
	private IUserDao ud;

    private IAddressDao ad;
	
	public IUserDao getUd() {
		return ud;
	}

	@ShopDi("userDao")
	public void setUd(IUserDao ud) {
		this.ud = ud;
	}

    public IAddressDao getAd() {
        return ad;
    }

    @ShopDi("addressDao")
    public void setAd(IAddressDao ad) {
        this.ad = ad;
    }

    @Before
    public void init() {
        SqlSession session = MyBatisUtil.createSession();
        Connection connection = null;
        try {
            connection = session.getConnection();
            ScriptRunner runner = new ScriptRunner(connection);
            Reader reader = new StringReader("truncate t_user;\ntruncate t_address;");
            runner.runScript(reader);
            session.commit();
        } finally {
            session.close();
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

	@Test
	public void testAdd(){
		User u = new User();
		u.setNickname("曹操");
		u.setPassword("123");
		u.setType(1);
		u.setUsername("cc");
		ud.add(u);
        Assert.assertTrue(u.getId() > 0);
	}
	
	@Test
	public void testUpdate() {
        User u = new User();
        u.setNickname("曹操");
        u.setPassword("123");
        u.setType(1);
        u.setUsername("cc");
        ud.add(u);

		u = ud.loadByUsername("cc");
		u.setPassword("2222");
		ud.update(u);
        u = ud.loadByUsername("cc");
        Assert.assertTrue(u.getPassword().equals("2222"));
    }
	
	@Test
	public void testDelete() {
        User u = new User();
        u.setNickname("曹操");
        u.setPassword("123");
        u.setType(1);
        u.setUsername("cc");
        ud.add(u);

        ud.delete(u.getId());

        User load = ud.load(u.getId());
        Assert.assertNull(load);
    }
	
	@Test
	public void testLogin() {
        User u = new User();
        u.setNickname("曹操");
        u.setPassword("123");
        u.setType(1);
        u.setUsername("cc");
        ud.add(u);
		u = ud.login("cc", "123");
        Assert.assertNotNull(u);

	}
	
	@Test
	public void testFind() {
		SystemContext.setPageOffset(0);
		SystemContext.setPageSize(15);
		SystemContext.setOrder("desc");
		SystemContext.setSort("nickname");

        User u1 = new User();
        u1.setNickname("曹操");
        u1.setPassword("123");
        u1.setType(1);
        u1.setUsername("cc");
        ud.add(u1);

        User u2 = new User();
        u2.setNickname("曹操2");
        u2.setPassword("123");
        u2.setType(1);
        u2.setUsername("cc2");
        ud.add(u2);

		Pager<User> ps = ud.find("曹操%");
		System.out.println(ps.getTotalRecord());
		for(User u:ps.getDatas()) {
			System.out.println(u);
		}
        Assert.assertTrue(ps.getDatas().size()==2);
		
	}
	
	@Test
	public void testLoad() {
        User u2 = new User();
        u2.setNickname("曹操");
        u2.setPassword("123");
        u2.setType(1);
        u2.setUsername("cc");
        ud.add(u2);

        Address address = new Address();
        address.setName("洛阳");
        address.setPhone("1234");
        address.setPostcode("1000");
        address.setUser(u2);

        ad.add(address, u2.getId());
        User u = ud.load(u2.getId());
        List<Address> addresses = u.getAddresses();
        Assert.assertTrue(addresses.size()==1);
    }
	
	@Test
	public void testSingle() {
		IUserDao ud1 = (IUserDao) DaoUtil.createDaoFactory().getDao("userDao");
		IUserDao ud2 = (IUserDao) DaoUtil.createDaoFactory().getDao("userDao");
		Assert.assertEquals(ud1, ud2);
		IAddressDao ad1 = (IAddressDao) DaoUtil.createDaoFactory().getDao("addressDao");
		IAddressDao ad2 = (IAddressDao) DaoUtil.createDaoFactory().getDao("addressDao");
        Assert.assertEquals(ad1, ad2);
    }

}
