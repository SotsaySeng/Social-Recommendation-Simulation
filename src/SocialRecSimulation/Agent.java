/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;
import static SocialRecSimulation.RSAgent.weigthExtract;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sim.engine.Steppable;
import sim.engine.SimState;
/**
 *
 * @author SocialRec
 */
public class Agent implements Steppable{
    //agent variable
    public int x;     // x coordinate of agent
    public int y;  
    public int id;
    public int vTime;
    public int vCost;
    public int vDelay;
    public int vWalk;
    public boolean agentStatus;

    
    public void step(SimState state){
             
       }
  
    public Agent(int x, int y ,int id, int vTime, int vCost, int vDelay, int vWalk, boolean agentStatus) {
           this.x = x;
           this.y = y;
           this.id = id;
           this.vTime = vTime;
           this.vCost = vCost;
           this.vDelay = vDelay;
           this.vWalk = vWalk;
           this.agentStatus = agentStatus;
    }
    
    public List agentSelection (List recommendedItems) {
        List<TravelRoute> items = Item.getRoute();
        List<String>  utilityValues = new ArrayList<>();
        List<Double>  totalUtilityList = new ArrayList<>();
        Map idPersoanlUtilityValue = new HashMap();
        Double [] userWeight = weigthExtract(vTime,vCost,vDelay,vWalk);
        Double wTime = userWeight[0];
        Double wCost = userWeight[1];
        Double wDelay = userWeight[2];
        Double wWalk = userWeight[3];
        
        for(int i=0 ;i< recommendedItems.size();i++)
                {
                    //personal
                    String vId = items.get(i).getId();
                    Double vTravelTime = Double.parseDouble(items.get(i).getTravelTime());
                    Double vTravelCost = Double.parseDouble(items.get(i).getTravelCost());
                    Double vTimeDelay = Double.parseDouble(items.get(i).getDelay());
                    Double vTimeWalk = Double.parseDouble(items.get(i).getWalk());
                    
                    //calculate personal 
                    Double utilityValue = (wTime*vTravelTime)+(wCost*vTravelCost)+
                                            (wDelay*vTimeDelay)+(wWalk*vTimeWalk);
                     totalUtilityList.add(utilityValue);
                }
        return totalUtilityList;
                
    }  
}
