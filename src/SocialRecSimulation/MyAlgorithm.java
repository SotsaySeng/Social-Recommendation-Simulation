/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
/**
 *
 * @author SocialRec
 */
public class MyAlgorithm {

//    @Override
//    public Map<UserAgent,List<Item>> run() {
//     
//          return null;
//    }
 
    public static void main(String[] args) throws  Exception{
        List<UserDetail> users = InputReader.readUserDetailFromCSV();
        Map<Integer,Double> mineSocialGoodValueList = new HashMap<>();
        Map<Integer,Double> traSocialGoodValueList = new HashMap<>();
        Map<Integer,List> traRecommendedList = new HashMap<>();
        Map<Integer,List> mineRecommendedList = new HashMap<>();
        double traTotalSocialFriendly = 0;
        double mineTotalSocialFriendly = 0;
        for( int i =0;i<users.size();++i){
           double calSocialMine = socialRec(i);
           double calSocialTra = traditionalRec(i);
           Map recommendedList = traditionalRecList(i);
           Map recommendedListNew = mineRecList(i);
           mineSocialGoodValueList.put(Integer.parseInt(users.get(i).getUserId()), calSocialMine);
           traSocialGoodValueList.put(Integer.parseInt(users.get(i).getUserId()), calSocialTra);
           //return tra recommend
           List topN = topN(recommendedList, 13);
           Collections.reverse(topN);
           traRecommendedList.put(Integer.parseInt(users.get(i).getUserId()),topN);
           //return mine recommend
           List topNMine = topN(recommendedListNew, 13);
           Collections.reverse(topNMine);
           mineRecommendedList.put(Integer.parseInt(users.get(i).getUserId()),topNMine);
           //Loop social score
           traTotalSocialFriendly+=calSocialTra;
           mineTotalSocialFriendly+=calSocialMine;
//                        
        }
        System.out.println("Traditional method"); 
        for(UserDetail userIds : users ){
            int userId = Integer.parseInt(userIds.getId());
            System.out.println("User "+ userId);
            System.out.println("Social score "+traSocialGoodValueList.get(userId));
            System.out.println(traRecommendedList.get(userId));
            System.out.println("............................................");
        }
        System.out.println("Total "+traTotalSocialFriendly);
        System.out.println("New proposed method");
        for(UserDetail userIds : users ){
            int userId = Integer.parseInt(userIds.getId());
            System.out.println("User "+ userId);
            System.out.println("Social score "+mineRecommendedList.get(userId));
            System.out.println(mineSocialGoodValueList.get(userId));
            System.out.println("............................................");
        }
        System.out.println(mineTotalSocialFriendly);
        System.out.println(users.get(0));

//        return userSocialGoodValueList;
//        PrintWriter pw = new PrintWriter(new FileWriter("abc.txt"));
//        for(TravelRoute r : items){
//            pw.println(r.toString());
//            pw.flush();
//        }
//        pw.close();
                //rank
//              System.out.println(items);
//                System.out.println();
//                for(int i=0;i<items.size();++i){
//                    System.out.print(items.get(i));
//                    if((i+1) % 4==0){
//                        System.out.println();
//                    } else{
//                        System.out.print(",");
//                    }
//                }
                /*
                // spearman
		double n = totalUtilityList.size();
                Double e = new SpearmansCorrelation().correlation(toDoubleArray(utilityValues),toDoubleArray(totalUtilityList));
                List<Double> rewards = rewardCalculate(utilityValues, totalUtilityList);
                System.out.println(rewards);
                //double[] rank = RankingAlgorithm.rank(toDoubleArray(utilityValues));
		double corr = Spearman.getCorrelation(utilityValues, totalUtilityList);
		//double pvalue = Spearman.getPvalue(e, n);
		//System.out.println("corr   = "+ corr);
		//System.out.println("pvalue = "+ pvalue);
                //end spearman
                */
    }
    public static double traditionalRec(int userId){
        
        List<TravelRoute> items = Item.getRoute();
        List<Double>  utilityValues = new ArrayList<>();
        List<String>  utilitySocials = new ArrayList<>();
        Map idUtilityValue = new HashMap();
        Map idUtilitySocial = new HashMap();
        Double wCongestion = 0.25;
        Double wAccident = 0.25;
        Double wPollution = 0.25;
        Double wTrafficCon = 0.25;
        for(int i=0 ;i< items.size();i++)
                {
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    //calculate personal 
                    Double utilityValue = vTravelTime;
                    utilityValues.add(utilityValue);
                    idUtilityValue.put(vId, utilityValue);
                    //
                    //social
                    Double vCongestion = Double.parseDouble(items.get(i).getCongestion());
                    Double vAccident = Double.parseDouble(items.get(i).getAccident());
                    Double vPollution = Double.parseDouble(items.get(i).getPollution());
                    Double vTrafficCon = Double.parseDouble(items.get(i).getTrafficCon());
  
                    //calculate social
                    Double utilitySocial= (wCongestion*vCongestion)+(wAccident*vAccident)+
                                            (wPollution*vPollution)+(wTrafficCon*vTrafficCon);
                    //add to social list and map with id
                    utilitySocials.add(String.valueOf(utilitySocial));
                    idUtilitySocial.put(vId, utilitySocial);
                }
                List<String> topNRecommendation = topNKeys((HashMap<String, Double>) idUtilityValue, 5);
                
                Collections.reverse(topNRecommendation);
                        double totalSocialFriendly = 0;
                        for (int i=0; i < topNRecommendation.size(); i++) {
                        totalSocialFriendly+=(Double)idUtilitySocial.get(topNRecommendation.get(i));
                        }
        return totalSocialFriendly;
    }
    public static Map traditionalRecList(int userId){
        
        List<TravelRoute> items = Item.getRoute();
        List<Double>  utilityValues = new ArrayList<>();
        Map idUtilityValue = new HashMap();
        for(int i=0 ;i< items.size();i++)
                {
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    Double utilityValue = vTravelTime;
                    utilityValues.add(utilityValue);
                    idUtilityValue.put(vId, utilityValue);
                }
        
        return idUtilityValue;
    }
    public static double socialRec(int userId){
        //user preference
        Double [] userWeight = AgentPref.weigthExtract(userId);
        Double wTime = userWeight[0];
        Double wCost = userWeight[1];
        Double wDelay = userWeight[2];
        Double wWalk = userWeight[3];
        
        //social friendly
        Double socialGoodWeight = 0.25;
        Double wCongestion = 0.25;
        Double wAccident = 0.25;
        Double wPollution = 0.25;
        Double wTrafficCon = 0.25;
        List<TravelRoute> items = Item.getRoute();
        List<Double>  utilityValues = new ArrayList<>();
        List<String>  utilitySocials = new ArrayList<>();
        List<Double>  totalUtilityList = new ArrayList<>();
        Map idUtilityValue = new HashMap();
        Map idUtilitySocial = new HashMap();
        Map idTotalUtility = new HashMap();
        Map itemDetail = new HashMap();
      
        for(int i=0 ;i< items.size();i++)
                {
                    //personal
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    Double vTravelCost = Double.parseDouble(items.get(i).getTravelCost());
                    Double vDelay = Double.parseDouble(items.get(i).getDelay());
                    Double vWalk = Double.parseDouble(items.get(i).getWalk());
                    //social
                    Double vCongestion = Double.parseDouble(items.get(i).getCongestion());
                    Double vAccident = Double.parseDouble(items.get(i).getAccident());
                    Double vPollution = Double.parseDouble(items.get(i).getPollution());
                    Double vTrafficCon = Double.parseDouble(items.get(i).getTrafficCon());
                    //calculate personal 
                    Double utilityValue = (wTime*vTravelTime)+(wCost*vTravelCost)+
                                            (wDelay*vDelay)+(wWalk*vWalk);
                    //calculate social
                    Double utilitySocial= (wCongestion*vCongestion)+(wAccident*vAccident)+
                                            (wPollution*vPollution)+(wTrafficCon*vTrafficCon);
                    //add to persoanl list and map with id
                    utilityValues.add(utilityValue);
                    idUtilityValue.put(vId, utilityValue);
                    //add to social list and map with id
                    utilitySocials.add(String.valueOf(utilitySocial));
                    idUtilitySocial.put(vId, utilitySocial);
                    //combine
                    
                    Double totalUtility = (utilityValue*(1-socialGoodWeight))+(utilitySocial*socialGoodWeight);
                    totalUtilityList.add(totalUtility);
                    idTotalUtility.put(vId, totalUtility);
                    // item detail
                    itemDetail.put(vId, items.get(i)); 
                }
                //reward
                List<Double> rewards = rewardCalculate(utilityValues, totalUtilityList);
                //create new list
                //List<String> calValueList = createCalculateValue(utilityValues,utilitySocials, totalUtilityList, rewards);
                //
                List<String> topNRecommendation = topNKeys((HashMap<String, Double>) idTotalUtility, 5);
                
                Collections.reverse(topNRecommendation);
                        double totalSocialFriendly = 0;
                        for (int i=0; i < topNRecommendation.size(); i++) {
                        double value = (double) idUtilitySocial.get(topNRecommendation.get(i));
                        totalSocialFriendly+=value;
                        }
                        //topNRecommendation.add(String.valueOf(totalSocialFriendly));
        return totalSocialFriendly;
    }
    public static Map mineRecList(int userId){
        
        Double [] userWeight = AgentPref.weigthExtract(userId);
        Double wTime = userWeight[0];
        Double wCost = userWeight[1];
        Double wDelay = userWeight[2];
        Double wWalk = userWeight[3];
        
        //social friendly
        Double socialGoodWeight = 0.25;
        Double wCongestion = 0.25;
        Double wAccident = 0.25;
        Double wPollution = 0.25;
        Double wTrafficCon = 0.25;
        List<TravelRoute> items = Item.getRoute();
        List<Double>  utilityValues = new ArrayList<>();
        List<String>  utilitySocials = new ArrayList<>();
        List<Double>  totalUtilityList = new ArrayList<>();
        Map idUtilityValue = new HashMap();
        Map idUtilitySocial = new HashMap();
        Map idTotalUtility = new HashMap();
        Map itemDetail = new HashMap();
      
        for(int i=0 ;i< items.size();i++)
                {
                    //personal
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    Double vTravelCost = Double.parseDouble(items.get(i).getTravelCost());
                    Double vDelay = Double.parseDouble(items.get(i).getDelay());
                    Double vWalk = Double.parseDouble(items.get(i).getWalk());
                    //social
                    Double vCongestion = Double.parseDouble(items.get(i).getCongestion());
                    Double vAccident = Double.parseDouble(items.get(i).getAccident());
                    Double vPollution = Double.parseDouble(items.get(i).getPollution());
                    Double vTrafficCon = Double.parseDouble(items.get(i).getTrafficCon());
                    //calculate personal 
                    Double utilityValue = (wTime*vTravelTime)+(wCost*vTravelCost)+
                                            (wDelay*vDelay)+(wWalk*vWalk);
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
               
        return idTotalUtility;
    }
    public static List<String> createCalculateValue(List utilityValues, List utilitySocials,List totalUtilityList,List rewards) {
        
        List<String>  calValueList = new ArrayList<>();
            for(int i =  0; i < utilityValues.size(); i++){
                calValueList.add(String.valueOf(utilityValues.get(i)));
                calValueList.add(String.valueOf(utilitySocials.get(i)));
                calValueList.add(String.valueOf(totalUtilityList.get(i)));
                calValueList.add(String.valueOf(rewards.get(i)));
            }

        return calValueList;
   
    }
    //Reward caluculate
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
    //sort
    public static <K,V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {

            List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());

            Collections.sort(sortedEntries, 
                new Comparator<Entry<K,V>>() {
                @Override
                public int compare(Entry<K,V> e1, Entry<K,V> e2) {
                    return e2.getValue().compareTo(e1.getValue());
                }
            }
         );

        return sortedEntries;
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
    //top n
    //
    private static <K, V extends Comparable<? super V>> List<Entry<K, V>> 
        topN(Map<K, V> map, int n)
    {
        Comparator<? super Entry<K, V>> comparator = 
            new Comparator<Entry<K, V>>()
        {
            @Override
            public int compare(Entry<K, V> e0, Entry<K, V> e1)
            {
                V v0 = e0.getValue();
                V v1 = e1.getValue();
                return v0.compareTo(v1);
            }
        };
        PriorityQueue<Entry<K, V>> highest = 
            new PriorityQueue<Entry<K,V>>(n, comparator);
        for (Entry<K, V> entry : map.entrySet())
        {
            highest.offer(entry);
            while (highest.size() > n)
            {
                highest.poll();
            }
        }

        List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
        while (highest.size() > 0)
        {
            result.add(highest.poll());
        }
        return result;
    }
    public static List<String> topNKeys(final HashMap<String, Double> map, int n) {
        PriorityQueue<String> topN = new PriorityQueue<String>(n, new Comparator<String>() {
        public int compare(String s1, String s2) {
            return Double.compare(map.get(s1), map.get(s2));
        }
        });

        for(String key:map.keySet()){
            if (topN.size() < n)
                topN.add(key);
            else if (map.get(topN.peek()) < map.get(key)) {
            topN.poll();
            topN.add(key);
        }
    }
    return (List) Arrays.asList(topN.toArray());
    }
} 

