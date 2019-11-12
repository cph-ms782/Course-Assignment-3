package facades;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class ApiFacade {

    private static ApiFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private ApiFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static ApiFacade getApiFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ApiFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public String getSwappiPerson(int id) throws MalformedURLException, IOException {
//        URL url = new URL("https://swapi.co/api/people/" + id);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
//        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
//        Scanner scan = new Scanner(con.getInputStream());
//        String jsonStr = null;
//        if (scan.hasNext()) {
//            jsonStr = scan.nextLine();
//        }
//        scan.close();
//        return jsonStr;
//    }

}
