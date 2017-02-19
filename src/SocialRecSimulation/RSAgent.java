/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sim.engine.SimState;

/**
 *
 * @author SocialRec
 */
public class RSAgent {
    
        public double socialGoodWeight;
        public double wCongestion;
        public double wAccident;
        public double wPollution;
        public double wTrafficCon;
    
    public void step(SimState state){
             
       }
    public RSAgent(Double socialGoodWeight, Double wCongestion,Double wAccident,Double wPollution,Double wTrafficCon) {
           this.socialGoodWeight = socialGoodWeight;
           this.wCongestion = wCongestion;
           this.wAccident = wAccident;
           this.wPollution = wPollution;
           this.wTrafficCon = wTrafficCon;
    }
    
    public List mineRecList(int agentId,int vTime, int vCost, int vDelay, int vWalk){
        
        Double [] userWeight = weigthExtract(vTime,vCost,vDelay,vWalk);
        Double wTime = userWeight[0];
        Double wCost = userWeight[1];
        Double wDelay = userWeight[2];
        Double wWalk = userWeight[3];
        
        //social friendly
        List<TravelRoute> items = Item.getRoute();
        List<Double>  utilityValues = new ArrayList<>();
        List<String>  utilitySocials = new ArrayList<>();
        List<Double>  totalUtilityList = new ArrayList<>();
        Map idUtilityValue = new HashMap();
        Map idUtilitySocial = new HashMap();
        Map idTotalUtility = new HashMap();
        for(int i=0 ;i< items.size();i++)
                {
                    //personal
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    Double vTravelCost = Double.parseDouble(items.get(i).getTravelCost());
                    Double vTimeDelay = Double.parseDouble(items.get(i).getDelay());
                    Double vTimeWalk = Double.parseDouble(items.get(i).getWalk());
                    //social
                    Double vCongestion = Double.parseDouble(items.get(i).getCongestion());
                    Double vAccident = Double.parseDouble(items.get(i).getAccident());
                    Double vPollution = Double.parseDouble(items.get(i).getPollution());
                    Double vTrafficCon = Double.parseDouble(items.get(i).getTrafficCon());
                    //calculate personal 
                    Double utilityValue = (wTime*vTravelTime)+(wCost*vTravelCost)+
                                            (wDelay*vTimeDelay)+(wWalk*vTimeWalk);
                    //calculate social
                    Double utilitySocial= (wCongestion*vCongestion)+(wAccident*vAccident)+
                                            (wPollution*vPollution)+(wTrafficCon*vTrafficCon);
//                    //add to persoanl list and map with id
//                    utilityValues.add(utilityValue);
//                    idUtilityValue.put(vId, utilityValue);
//                    //add to social list and map with id
//                    utilitySocials.add(String.valueOf(utilitySocial));
//                    idUtilitySocial.put(vId, utilitySocial);
                    //combine
                    
                    Double totalUtility = (utilityValue*(1-socialGoodWeight))+(utilitySocial*socialGoodWeight);
                    totalUtilityList.add(totalUtility);
                    idTotalUtility.put(vId, totalUtility);
  
                }
               
        return totalUtilityList;
    }
    
   public static Double[] weigthExtract (int vTime, int vCost, int vDelay, int vWalk){
        double wTime;
        double wCost;
        double wDelay;
        double wWalk;
        double total = vTime+vCost+vDelay+vWalk;
        
        wTime = vTime/total;
        wCost=vCost/total;
        wDelay = vDelay/total;
        wWalk =vWalk/total;
        return new Double[] {wTime,wCost,wDelay,wWalk};
    }
   
   public static List<Double> rewardCalculate(List<Double> listX,List<Double> listY) {
                int[] rankPersonal = getRanksArray(toDoubleArray(listX));
                int[] rankSocial = getRanksArray(toDoubleArray(listY));
                List<Double>  rewards = new ArrayList<>();
                 for (int i = 0; i < rankPersonal.length; i++) {
                    double balancedValue;
                    double reward;
                    if(rankPersonal[i]>rankSocial[i]){
                        balancedValue = listY.get(i)- listX.get(i);
                        reward = Math.abs(balancedValue);
                                }
                    else{
                        reward=0;
                    }
                    rewards.add(reward);
                 }
                 return rewards;
    }
   // ranking list
    public static int[] getRanksArray(double[] array) {
        int[] result = new int[array.length];

            for (int i = 0; i < array.length; i++) {
                int count = 0;
                for (int j = 0; j < array.length; j++) {
                    if (array[j] > array[i]) {
                     count++;
                }
             }
            result[i] = count + 1;
        }
        return result;
    }
    //convert to double
    public static double[] toDoubleArray(List<Double> list) {
       double[] arr = new double[list.size()];
       for (int i=0; i < list.size(); i++) {
          arr[i] = list.get(i);
       }
       return arr;
    } 
}

