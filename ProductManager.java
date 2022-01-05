package com.crud;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class ProductManager {
	private static SessionFactory factory;

	static {
		try {
			factory = getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		Configuration con = new Configuration().configure().addAnnotatedClass(Product.class);

		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(con.getProperties())
				.build();

		SessionFactory factoryObj = con.buildSessionFactory(serviceRegistryObj);
		return factoryObj;
	}

	public static void main(String[] args) {

		
		Integer productId1 = addProduct("oil","fortune");
		Integer productId2 = addProduct("rice","bashmati");
		Integer productId3 = addProduct("mobile","samsung");

		
		listPoduct();
		

		updateEmployee(productId3,"washing machine");

		
		deleteEmployee(productId2);

		
		listPoduct();
	}

	
	public static Integer addProduct(String pname, String cname) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer productId = null;

		try {
			tx = session.beginTransaction();
			Product product=new Product();
			product.setpName(pname);
			product.setcName(cname);
			productId = (Integer) session.save(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return productId;
	}

	
	public static void listPoduct() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List employees = session.createQuery("FROM Product").list();
			for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
				Product product=  (Product) iterator.next();
				System.out.println("product name"+product.getpName());
				System.out.println("company name"+product.getcName());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	
	public static void updateEmployee(Integer productId, String pname) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			product.setpName(pname);
			session.update(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	
	public static void deleteEmployee(Integer  productId) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			session.delete(product);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
