package de.fh_dortmund.swt.doppelkopf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Manager {
	private static SessionFactory factory;
	private static ServiceRegistry registry;
	
	public static void main(String[] args) {
		//Manager.start(null);
		System.out.println(Manager.askLeaderboard());
	}
	
	/**
	 *  Configures and builds a Sessionfactory, uses it to open a session to persist the given object
	 */
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
	
	public static String askLeaderboard() {
		String url = "jdbc:postgresql://localhost:5432/Doppelkopf_Table";
        String user = "postgres";
        String password = "0000";
        String leaderboardString = "";

        try (Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM public.player")) {

            while (rs.next()) {
                String name = rs.getString("name");
                int points = rs.getInt("victorypoints");
                leaderboardString += "Name: " + name + "|| Punkte: " + points + "\n"; 
            }
            
            return leaderboardString;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		return leaderboardString;
	}

}
