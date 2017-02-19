/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;
import java.util.List;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import sim.engine.SimState;
import sim.field.grid.SparseGrid2D;
/**
 *
 * @author DV6
 */
public class AgentSimulation extends SimState {
    public SparseGrid2D agentsSpace; // 2D space for agents
        public int gridWidth = 100; // width of the space
        public int gridHeight = 100; // Height of the space
        
    private final double statusThreshold = 1;
    private int countStatusTrue = 0;
    private int countStatusFalse = 0;
    private double socialWeight = 0.25;
    private double wCongestion = 0.25;
    private double wAccident = 0.25;
    private double wPollution = 0.25;
    private double wTrafficCon = 0.25;

    public AgentSimulation(long seed) {
        super(seed);
    }
    
     public void start(){
           super.start(); // reuse the SimState start method
                         // Now add our own code
                         
           agentsSpace = new SparseGrid2D(gridWidth, gridHeight);
                                // create a 2-D space
           //set RsAgent
           RSAgent rAgent = new RSAgent(socialWeight,wCongestion,wAccident,wPollution,wTrafficCon);
           List<UserDetail> users = InputReader.readUserDetailFromCSV();
           
                /*
                 * Now create the agents and put them in the space.
                 */

            for(int i=0;i<users.size();i++){ // 0 to the number of agents - 1
                int x = random.nextInt(gridWidth);
                // a random number < gridWidth
                int y = random.nextInt(gridHeight); 
                int id = Integer.parseInt(users.get(i).getUserId());
                // a random number < gridWidth
                int vTime = Integer.parseInt(users.get(i).getTimeWeight());
                    //a random number < gridHeight
                int vCost = Integer.parseInt(users.get(i).getCostWeight());
                    //in the range -1 to 1
                int vDelay = Integer.parseInt(users.get(i).getDelayWeight());
                int vWalk = Integer.parseInt(users.get(i).getWalkWeight());
                boolean agentStatus= true;
                    //in the range -1 to 1
               Agent agent = new Agent(x,y,id,vTime,vCost,vDelay,vWalk,agentStatus);
               agentsSpace.setObjectLocation(agent, x,y);
                    //put it at location x, y
               //start agent request
               System.out.println("User " + id);
               List recommendedItems = rAgent.mineRecList(id, vTime, vCost, vDelay, vWalk);
               System.out.println("Recommended route " + id);
               System.out.println(recommendedItems);
               //agent calculate its utility
               System.out.println("Personal route " + id);
               List agentList = agent.agentSelection(recommendedItems);
               System.out.println(agentList);
               //compare
               Double spearCo = calculateRankCo(agentList, recommendedItems);
               //check spear coefficient resutl
               System.out.println(spearCo);
               //change status
               System.out.println(agentStatus);
               //change status
               if (spearCo<statusThreshold){
                   agentStatus = false;
                   countStatusFalse++;
               }
               else {
                   agentStatus = true;
                   countStatusTrue++;
               }
               
               System.out.println(agentStatus);
               //schedule.scheduleRepeating(agent                   // schedule it to repeat in the simulation
                }
                
            System.out.println("Total active user "+countStatusTrue);  
            System.out.println("Total passive user "+countStatusFalse);
              
                
            
            //RAgent read file and calulate alternative
        }
//     public void changeStatus (Double cor) {
//        if (statusThreshold>cor){
//            = false;
//        }    
//    }
    public Double calculateRankCo(List<Double> X, List<Double> Y) {
        SpearmansCorrelation SC = new SpearmansCorrelation();
        double[] xArray = toDoubleArray(X);
        double[] yArray = toDoubleArray(Y);
        double corr = SC.correlation(xArray, yArray);
       
        return corr;
    }
    public static double[] toDoubleArray(List<Double> list) {
       double[] arr = new double[list.size()];
       for (int i=0; i < list.size(); i++) {
          arr[i] = list.get(i);
       }
       return arr;
    }
     public static void main(String[] args) {
            doLoop(AgentSimulation.class, args);
            //doLoop is a static method
             //already defined in SimState
            System.exit(0);//Stop the program when finished.
        }
}
