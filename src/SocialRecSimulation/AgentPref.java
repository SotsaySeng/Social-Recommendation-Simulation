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
public class AgentPref {
    //function extract weight from user preference
    public static Double[] weigthExtract (int userId){
        List<UserDetail> users = InputReader.readUserDetailFromCSV(); //read from CSV
        String rTime = users.get(userId).getTimeWeight();
        String rCost = users.get(userId).getCostWeight();
        String rDelay = users.get(userId).getDelayWeight();
        String rWalk = users.get(userId).getWalkWeight();
        
        double wTime;
        double wCost;
        double wDelay;
        double wWalk;
        double total = Double.parseDouble(rTime)+Double.parseDouble(rCost)+Double.parseDouble(rDelay)+Double.parseDouble(rWalk);
        
        wTime = Integer.parseInt(rTime)/total;
        wCost=Integer.parseInt(rCost)/total;
        wDelay = Integer.parseInt(rDelay)/total;
        wWalk = Integer.parseInt(rWalk)/total;
        return new Double[] {wTime,wCost,wDelay,wWalk};
    }
}
