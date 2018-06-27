package de.fh_dortmund.swt.doppelkopf;
import javax.persistence.*;

import org.hibernate.HibernateException;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class Manager {
	private static SessionFactory factory;
	private static ServiceRegistry registry;
	public static void start(Object obj)
	{
		try
		{
			System.out.println( "------------------------------------------------------" );
			System.out.println( "Initializing Hibernate" );
			Configuration cfg=new Configuration().configure("hibernate.cfg.xml");
			cfg.addAnnotatedClass(Player.class);
			registry =  new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
			factory=cfg.buildSessionFactory(registry);
			Session session=factory.openSession();
			Transaction t= session.beginTransaction();
				if(session.contains(obj))
					session.merge(obj);
				else
					session.persist(obj);
			t.commit();
			session.close();
			System.out.println( "Finished Initializing Hibernate" );
			System.out.println( "------------------------------------------------------" );
			/*Configuration configuration = new Configuration();
	        configuration.configure("hibernate.cfg.xml");
	        configuration.addAnnotatedClass(Player.class);
	        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        factory = configuration.buildSessionFactory(srvcReg);*/
		}
		catch(PersistentObjectException p)
		{
			p.printStackTrace();
		}
		catch(HibernateException he)
		{
			System.out.println("##############################################");
			System.out.println("Exception Factory");
			System.out.println("##############################################");
			he.printStackTrace();
		}
	}

}
