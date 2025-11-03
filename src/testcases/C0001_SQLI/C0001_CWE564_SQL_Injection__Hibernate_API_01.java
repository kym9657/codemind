package testcases.C0001_SQLI;
/*

https://www.tutorialspoint.com/hibernate/hibernate_examples.htm
by juho
*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//annotation과는 factory 생성 차이
//factory = new AnnotationConfiguration().configure().addAnnotatedClass(Employee.class).buildSessionFactory();
public class C0001_CWE564_SQL_Injection__Hibernate_API_01 {
	private static final SessionFactory factory;
	private static Session session = null;

	static {
		try{
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Exception ex){
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		session = factory.openSession();
	}
	public void bad(HttpServletRequest req){
		//#1
    //FLAW:
		String currentOrder = req.getParameter("order");
		List results = session.createQuery("from Orders as orders where orders.id = " + currentOrder).list();
		//#2
    //FLAW:
		String author = java.lang.System.getenv("USER");
		List results2 = session.createSQLQuery("Select * from Books where author = " + author).list();  // SQLASTER
	}
	public void good(){
		//#1  Positional parameter in HQL
		Query hqlQuery = session.createQuery("from Orders as orders where orders.id = ?");
		List results = hqlQuery.setString(0, "123-ADB-567-QTWYTFDL").list();
		//#2  named parameter in HQL
		Query hqlQuery2 = session.createQuery("from Employees as emp where emp.incentive > :incentive");
		List results2 = hqlQuery.setLong("incentive", new Long(10000)).list();
		//#3  named parameter list in HQL
		List items = new ArrayList();
		items.add("book"); items.add("clock"); items.add("ink");
		List results3 = session.createQuery("from Cart as cart where cart.item in (:itemList)").setParameterList("itemList", items).list();
		//#4  JavaBean in HQL
		Query hqlQuery4 = session.createQuery("from Books as books where book.name = :name and book.author = :author");
		List results4 = hqlQuery.setProperties(this).list();  //this == javaBean
		//#5  Native-SQL
		Query sqlQuery5 = session.createSQLQuery("Select * from Books where author = ?");  //SQLASTER
		List results5 = sqlQuery5.setString(0, "Charles Dickens").list();
	}
}
