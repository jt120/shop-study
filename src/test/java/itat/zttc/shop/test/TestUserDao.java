package itat.zttc.shop.test;

import itat.zttc.shop.dao.UserDao;
import itat.zttc.shop.model.User;
import org.junit.Test;

/**
 * @author he
 * @since 2015/6/9
 */
public class TestUserDao {

    @Test
    public void testAdd() throws Exception {
        UserDao userDao = new UserDao();
        userDao.add(new User());

    }
}
