/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;

import java.util.List;
/**
 *
 * @author SocialRec
 */
public class Item {
    public static List<TravelRoute> getRoute (){
    
        List<TravelRoute> routes = InputReader.readTravelRouteFromCSV();
        itemNormalization(routes);
        return routes;
    }

    public static void itemNormalization (List<TravelRoute> travelRoutes){
    
        double[] minMaxTravelTime = getMinMaxTravelTime(travelRoutes);
        double[] minMaxTravelCost = getMinMaxTravelCost(travelRoutes);
        double[] minMaxTravelDelay = getMinMaxDelay(travelRoutes);
        double[] minMaxTravelWalk = getMinMaxWalk(travelRoutes);
        travelRoutes.stream().map((route) -> {
            double normlTravelTime = itemNormalization(Double.parseDouble(route.getTravelTime()), minMaxTravelTime[0], minMaxTravelTime[1]);
            route.setTravelTime(String.valueOf(normlTravelTime));
            return route;
        }).map((route) -> {
            double normlTravelCost = itemNormalization(Double.parseDouble(route.getTravelCost()), minMaxTravelCost[0], minMaxTravelCost[1]);
            route.setTravelCost(String.valueOf(normlTravelCost));
            return route;
        }).map((route) -> {
            double normalDelay = itemNormalization(Double.parseDouble(route.getDelay()), minMaxTravelDelay[0], minMaxTravelDelay[1]);
            route.setDelay(String.valueOf(normalDelay));
            return route;
        }).forEachOrdered((route) -> {
            double normalWalk = itemNormalization(Double.parseDouble(route.getWalk()), minMaxTravelWalk[0], minMaxTravelWalk[1]);
            route.setWalk(String.valueOf(normalWalk));
        });
    }

    private static double[] getMinMaxTravelTime(List<TravelRoute> travelRoutes){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(TravelRoute route : travelRoutes){
            double travelTime = Double.parseDouble(route.getTravelTime());
            if(travelTime < min){
                min = travelTime;
            }
            if(travelTime > max){
                max = travelTime;
            }
        }
        return new double[]{min,max};
    }

    private static double[] getMinMaxTravelCost(List<TravelRoute> travelRoutes){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(TravelRoute route : travelRoutes){
            double travelCost = Double.parseDouble(route.getTravelCost());
            if(travelCost < min){
                min = travelCost;
            }
            if(travelCost > max){
                max = travelCost;
            }
        }
        return new double[]{min,max};
    }

    private static double[] getMinMaxDelay(List<TravelRoute> travelRoutes){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(TravelRoute route : travelRoutes){
            double delay = Double.parseDouble(route.getDelay());
            if(delay < min){
                min = delay;
            }
            if(delay > max){
                max = delay;
            }
        }
        return new double[]{min,max};
    }

    private static double[] getMinMaxWalk(List<TravelRoute> travelRoutes){
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for(TravelRoute route : travelRoutes){
            double walk = Double.parseDouble(route.getWalk());
            if(walk < min){
                min = walk;
            }
            if(walk > max){
                max = walk;
            }
        }
        return new double[]{min,max};
    }

    private static double itemNormalization (double oriVale,double min,double max){
            double normvalue= (5+1)-(1+(oriVale-min)*(5-1)/(max-min));
            return normvalue;

    }
}
