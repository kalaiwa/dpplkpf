package de.fh_dortmund.swt.doppelkopf;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class Manager {
	private static SessionFactory factory;
	private static ServiceRegistry registry;

	/**
	 *  Configures and builds a Sessionfactory
	 */
	public static void start() {
		System.out.println( "------------------------------------------------------" );
		System.out.println( "Initializing Hibernate" );
		Configuration cfg=new Configuration().configure("hibernate.cfg.xml");
		cfg.addAnnotatedClass(Player.class);
		registry =  new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		factory=cfg.buildSessionFactory(registry);
		System.out.println( "Finished Initializing Hibernate" );
		System.out.println( "------------------------------------------------------" );
		/*Configuration configuration = new Configuration();
	        configuration.configure("hibernate.cfg.xml");
	        configuration.addAnnotatedClass(Player.class);
	        ServiceRegistry srvcReg = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        factory = configuration.buildSessionFactory(srvcReg);*/
	}

	/**
	 * persists the given object
	 */
	public static void persist(Object obj)
	{
		try
		{
			if(factory==null) start();
			Session session=factory.openSession();
			Transaction t= session.beginTransaction();
			/*if(session.contains(obj))
					session.merge(obj);
				else
					session.merge(obj);*/
			session.merge(obj);
			t.commit();
			session.close();
			System.out.println( "Finished Initializing Hibernate" );
			System.out.println( "------------------------------------------------------" );
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

	public static String askLeaderboard() {
		String leaderboardString = "";

		if(factory==null) start();
		Session session = factory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
		Root<Player> root = criteria.from(Player.class);
		criteria.select(root);
		Query<Player> query = session.createQuery(criteria);
		List<Player> players = query.getResultList();
		session.close();
		players.sort((p1, p2) -> p1.getVictoryPoints() - p2.getVictoryPoints());
		for (Player player : players) {
			String name = player.getName();
			int points = player.getVictoryPoints();
			leaderboardString += name + " || " + points + "\n"; 
		}
		return leaderboardString;
	}

	public static Player askPlayer(String name, String pw) {
		if(factory==null) start();
		Session session = factory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
		Root<Player> root = criteria.from(Player.class);
		criteria.where(builder.and(builder.equal(root.get("name"), name), builder.equal(root.get("password"), pw)));
		Query<Player> query = session.createQuery(criteria);
		Player player = query.getSingleResult();
		session.close();
		return player;
	}
	
	public static Boolean isPlayerExisting(String name) {
		if(factory==null) start();
		Session session = factory.openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
		Root<Player> root = criteria.from(Player.class);
		criteria.where(builder.equal(root.get("name"), name));
		Query<Player> query = session.createQuery(criteria);
		Player player = null;
		try {
			player = query.getSingleResult();
		} catch (NoResultException e) {}
		session.close();
		return player!=null;
	}

}
