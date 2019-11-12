package rest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("sw")
public class StarWarsResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

//    ApiFacade facade = ApiFacade.getApiFacade(EMF);
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello star wars\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("data")
    public String getPerson1() throws IOException, ProtocolException, ExecutionException, InterruptedException {
//        String person = getSwappi("person/1");
        String data = getAllDataInParallelWithQueue();
        return data;
    }

    public String getSwappi(String urlApi) throws MalformedURLException, IOException {
        URL url = new URL(urlApi);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server"); //remember if you are using SWAPI
        String jsonStr;
        try (Scanner scan = new Scanner(con.getInputStream())) {
            jsonStr = null;
            if (scan.hasNext()) {
                jsonStr = scan.nextLine();
            }
        }
        return jsonStr;
    }

    public String getAllDataInParallelWithQueue() throws ProtocolException, IOException, InterruptedException, ExecutionException {
        List<String> results = new ArrayList();
        List<String> URLS = new ArrayList();
        URLS.add("https://swapi.co/api/people/1/");
        URLS.add("https://swapi.co/api/planets/1/");
        URLS.add("https://swapi.co/api/vehicles/7/");
        URLS.add("https://swapi.co/api/films/1/");
        URLS.add("https://swapi.co/api/starships/2/");
//        System.out.println("URLS used: " + URLS.toString());

        Queue<Future<String>> queue = new ArrayBlockingQueue(URLS.size());

        ExecutorService workingJack = Executors.newCachedThreadPool();
        for (String url : URLS) {
            Future<String> future;
            future = workingJack.submit(() -> {
                String cp = getSwappi(url);
                return cp;
            });
            queue.add(future);
        }
        while (!queue.isEmpty()) {
            Future<String> cpo = queue.poll();
            if (cpo.isDone()) {
                try {
                    String hj = null;
                    hj = cpo.get();
                    results.add(hj);
                } catch (InterruptedException interruptedException) {
                    System.out.println("interruptedException: " + interruptedException);
                } catch (ExecutionException executionException) {
                    System.out.println("executionException: " + executionException);
                }
            } else {
                queue.add(cpo);
            }
        }
        workingJack.shutdown();
        return String.join(",", results);
    }

}
