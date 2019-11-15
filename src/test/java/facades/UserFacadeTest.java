package facades;

import entities.Role;
import static entities.Role_.userList;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author martin
 */
public class UserFacadeTest {

    private static User user = new User("user", "test");
    private static User user_admin = new User("user_admin", "test");
    private static User admin = new User("admin", "test");
    private static User both = new User("user_admin", "test");
    private static Role userRole = new Role("user");
    private static Role adminRole = new Role("admin");
    private static List<User> userlist= new ArrayList();

    public UserFacadeTest() {
    }

    private static final String SERVER_URL = "http://localhost/api";
    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.CREATE);
    private static UserFacade facade = UserFacade.getUserFacade(emf);

    ;
    
    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
//        EMF_Creator.endREST_TestWithDB();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

//            Role userRole = new Role("user");
//            Role adminRole = new Role("admin");
//            User user = new User("user", "test");
//            User admin = new User("admin", "test");
//            User both = new User("user_admin", "test");
            System.out.println("BeforeEach setUp?+++++++++++++++++");
            user.addRole(userRole);
            admin.addRole(adminRole);
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Test of getVerifiedUser method, of class UserFacade.
     */
    @Test
    public void testGetVerifiedUser() throws Exception {
        System.out.println("getVerifiedUser");
        String username = "user";
        String password = "test";
        
//        user.addRole(userRole);
//        admin.addRole(adminRole);
//        both.addRole(userRole);
//        both.addRole(adminRole);
//        userlist.add(user);
//        userlist.add(user_admin);
//        userRole.setUserList(userlist);
        String expResult = user.getUserName();
        String result = null;
        try {
            if (facade != null) {
                result = facade.getVerifiedUser(username, password).getUserName();
            }
        } catch (Exception ex) {
            System.out.println("Exception caught: " + ex);
        }
        assertEquals(expResult, result);
    }

}
