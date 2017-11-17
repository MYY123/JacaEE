package cn.itcast.crm.service.impl;

import java.util.List;

import org.hibernate.Session;

import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;
import cn.itcast.crm.utils.HibernateUtils;

public class CustomerServiceImpl implements CustomerService {

	public List<Customer> findnoassociationCustomers() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id is null";
		List<Customer> customers = session.createQuery(hql).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public List<Customer> findhasassociationCustomers(String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, decidedZoneId).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public void assignCustomersToDecidedZone(String[] customerIds, String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		// 鍙栨秷瀹氬尯鎵�湁鍏宠仈瀹㈡埛
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// 杩涜鍏宠仈
		String hql = "update Customer set decidedzone_id=? where id =?";
		if (customerIds != null) {
			for (String id : customerIds) {
				//int id=Integer.parseInt(id);
				session.createQuery(hql).setParameter(0, decidedZoneId).setParameter(1, Integer.parseInt(id)).executeUpdate();
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	public Customer findCustomerByPhonenumber(String phonenumber) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where telephone = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, phonenumber).list();

		session.getTransaction().commit();
		session.close();

		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	public String findDecidedzoneIdByPickaddress(String address) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "select decidedzone_id from Customer where address = ?";
		List<String> customers = session.createQuery(hql).setParameter(0, address).list();

		session.getTransaction().commit();
		session.close();

		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

}
