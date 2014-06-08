package org.denevell.natch.web.jerseymvc.uitests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class TestUtils {
    
    public static void deleteTestDb() throws Exception {
        
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "denevell");
        connectionProps.put("password", "user");

            conn = DriverManager.getConnection(
                       "jdbc:postgresql://" +
                       "localhost" +
                       ":5432" + "/testnatch",
                       connectionProps);
            
        Statement statment = conn.createStatement();
        statment.execute("delete from users_loggedin");
        statment.execute("delete from thread_posts");
        statment.execute("delete from post_tags");
        statment.execute("delete from ThreadEntity");
        statment.execute("delete from PostEntity");
        statment.execute("delete from UserEntity");
        statment.execute("delete from PushIds");
        
//        EntityManager mEntityManager = null;
//        EntityManagerFactory fact = null;
//        try {
//            fact = Persistence.createEntityManagerFactory(PersistenceInfo.TestEntityManagerFactoryName);
//            mEntityManager = fact.createEntityManager();
//            EntityTransaction trans = mEntityManager.getTransaction();
//            trans.begin();
//            List<ThreadEntity> resultT = mEntityManager.createQuery("select a from ThreadEntity a", ThreadEntity.class).getResultList();
//            for (ThreadEntity postEntity : resultT) {
//                mEntityManager.remove(postEntity);
//            }
//            List<PostEntity> result = mEntityManager.createQuery("select a from PostEntity a", PostEntity.class).getResultList();
//            for (PostEntity postEntity : result) {
//                mEntityManager.remove(postEntity);
//            }
//            List<UserEntity> resultU = mEntityManager.createQuery("select a from UserEntity a", UserEntity.class).getResultList();
//            for (UserEntity postEntity : resultU) {
//                mEntityManager.remove(postEntity);
//            }
//            trans.commit();
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            EntityUtils.closeEntityConnection(mEntityManager);
//        }
    }   
}
