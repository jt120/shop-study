package itat.zttc.shop.dao;

import itat.zttc.shop.test.BaseTest;
import itat.zttc.shop.model.Address;
import itat.zttc.shop.model.ShopDi;
import org.junit.Test;

import java.util.List;

public class TestAddressDao extends BaseTest {
	private IAddressDao addressDao;
	private IUserDao userDao;
	
	public IAddressDao getAddressDao() {
		return addressDao;
	}

	@ShopDi
	public void setAddressDao(IAddressDao addressDao) {
		this.addressDao = addressDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}
	@ShopDi
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	@Test
	public void testUserDao() {
		new AddressDao();
	}

	@Test
	public void testLoad() {
		Address address = addressDao.load(1);
		System.out.println(address.getName()+","+address.getUser()+","+address.getPostcode());
	}
	
	@Test
	public void testAdd() {
		Address address = new Address();
		address.setName("云南省东川！！");
		address.setPhone("114");
		address.setPostcode("652000");
		addressDao.add(address, 2);
	}
	
	@Test
	public void testList() {
		List<Address> list = addressDao.list(1);
		for(Address a:list) {
			System.out.println(a.getName()+","+a.getUser());
		}
	}
}
