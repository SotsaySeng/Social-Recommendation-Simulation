/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SocialRec
 */
public class InputReader {
    
    public static List<TravelRoute> readTravelRouteFromCSV() {
        String csvFileRoute = "data/route.csv";
        String csvFileRoutePeak = "data/routePeak.csv";
        List<TravelRoute> routes = new ArrayList<>();
        Path pathToFile = Paths.get(csvFileRoute);
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");

                TravelRoute route = createTravelRoute(attributes);

                routes.add(route);

                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        return routes;
    }

    public static TravelRoute createTravelRoute(String[] metadata) {
        
        String id = metadata[0];
	String travel_name = metadata[1];
        String travel_time= metadata[2];
        String travel_cost=metadata[3];
        String delay= metadata[4];
	String walk= metadata[5];
        String congestion = metadata[6];
        String accident = metadata[7];
        String pollution = metadata[8];
        String traffic_con = metadata[9];

        // create and return travel route of this metadata
        return new TravelRoute(id, travel_name, travel_time,travel_cost,delay,walk,congestion,accident,
        pollution,traffic_con);
    }
    
    //user detail
    public static List<UserDetail> readUserDetailFromCSV() {
        String csvFile = "data/user.csv";
        List<UserDetail> users = new ArrayList<>();
        Path pathToFile = Paths.get(csvFile);

        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            String line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(",");

                UserDetail user = createUserDetail(attributes);

                users.add(user);
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return users;
    }

    public static UserDetail createUserDetail(String[] metadata) {
        
        String id = metadata[0];
	String user_id = metadata[1];
        String time_weight=metadata[2];
        String cost_weight= metadata[3];
        String delay_weight= metadata[4];
	String walk_weight= metadata[5];

        // create and return user of this metadata
        return new UserDetail(id, user_id,time_weight,cost_weight,delay_weight,walk_weight);
    }
}