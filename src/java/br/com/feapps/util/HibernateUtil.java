    /*

        ver 3

    */
/*package br.com.feapps.util;

import org.hibernate.*;  
import org.hibernate.cfg.*;  
  
public class HibernateUtil {  
  
   private static SessionFactory sessionFactory;  
  
   static {  
      try {  
         sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();  
      } catch (Throwable ex) {        
         System.err.println("Initial SessionFactory creation failed." + ex);  
         throw new ExceptionInInitializerError(ex);  
      }  
   }  
  
   public static SessionFactory getSessionFactory() {  
      if(sessionFactory == null){  
         try {  
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();  
         } catch (Throwable ex) {     
            System.err.println("Initial SessionFactory creation failed." + ex);  
            throw new ExceptionInInitializerError(ex);  
         }  
      }  
      return sessionFactory;  
   }  
}*¹


    /*

        ver 2

    */

/*package br.com.feapps.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 
public class HibernateUtil {
 
    private static SessionFactory sessionFactory;
 
    static {
        try {           
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration().configure("/hibernate.cfg.xml");     
            sessionFactory = configuration.buildSessionFactory( new StandardServiceRegistryBuilder().applySettings( configuration.getProperties() ).build());
            
            //Configuration configuration = new Configuration().configure();
            //StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
            //                                    applySettings(configuration.getProperties());
            //sessionFactory = configuration.buildSessionFactory(builder.build());
            
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
}*/

    /*

        ver 1

    */

package br.com.feapps.util;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);           
        }

        return sessionFactory;
    }
}