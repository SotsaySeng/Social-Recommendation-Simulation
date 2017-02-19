/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;
import java.awt.Color;
import javax.swing.JFrame;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.*;
import sim.portrayal.grid.*;
import sim.util.Bag;
/**
 *
 * @author DV6
 */
public class GraphicalUI extends GUIState{
    
    public Display2D display; // variable for the graphic display
        public JFrame displayFrame; // for the display frame
        SparseGridPortrayal2D agentsPortrayal = new
            SparseGridPortrayal2D();
          // Graphic portrayal of the agents in 2D space           

         protected GraphicalUI() { // Constructor for this class
         super(new AgentSimulation(System.currentTimeMillis()));
                         // Create a simulation in it
        }
    public GraphicalUI(SimState state) {
        super(state);
    }
     public static String getName() {
            return "Agents Simulation";
                   // return a name for what this simulation is about
             }
 
        public void quit() {
             super.quit(); // Use the already defined quit method
               
             if (displayFrame!=null) displayFrame.dispose();
                displayFrame = null; 
                // when quiting get rid of the display
                display = null;      
        }
 
        public void start() {
              super.start(); // use the predefined start method
               setupPortrayals(); // add setupPortrayals method below
        }

           
        public void load(SimState state) {
            super.load(state);
            // load the simulation into the interface
            setupPortrayals(); // call setuuPortrayals
        }


          /*
           * This is the one method we will change the most for each
.          *  simulation.  Specifically, we determine what agents
           * look like. 
           */
              
        public void setupPortrayals() {
            agentsPortrayal.setField(((AgentSimulation)state).agentsSpace);
     // Here we give the agentsProtrayal, the agentsSpace so that it
             // can graphically represent it.
Bag agents = ((AgentSimulation)state).agentsSpace.getAllObjects();              //get all the agents
       for(int i=0; i<agents.numObjs;i++){// for all of the agents
               Agent a = (Agent)agents.get(i);
                    //get each agent from 0 to n

        /*
         * Now we are going to use RGB colors to set the color
.        * of our agents.  The alpha level determines how 
         * transparent the agent is. 
         */

               float red = (float)1.0;                             
               float green = (float)0.0;
               float blue = (float)0.0;
               float alpha = (float)1.0;
               agentsPortrayal.setPortrayalForObject (a, new sim.portrayal.simple.OvalPortrayal2D (new Color(red,green,blue,alpha)));      
            }
    // This complicated statement makes an oval portrayal of
             // a given color
            display.reset();
            // call the predefined reset method for the display
            display.repaint(); // call the repaint method
         }
 
         public void init(Controller c){
         super.init(c); 
            // use the predefined method to initialize the
               // controller, c
             display = new Display2D(400,400,this,1);
            // make the display
         // that is 400 x 400 pixels.  You can set it to other values
             displayFrame = display.createFrame();
                // create the frame
             c.registerFrame(displayFrame);  
                // let the controller control the
              //frame
             displayFrame.setVisible(true);  // make it visible
             display.setBackdrop(Color.black);
                // make the background black
             display.attach(agentsPortrayal,"Agents Simulation");
                // attach the display
                }
           
        public Object getSimulationInspectedObject() {
              return state; // This returns the simulation
         }

       
         public static void main(String[] args) {
               GraphicalUI ex = new GraphicalUI();
            // make the user interface
                // and call it ex
                Console c = new Console(ex);
            // make the console and give it
                 // the user interface to display
                c.setVisible(true); // make the console visible
                System.out.println("Start Simulation");
            // Print that the
                  // simulation started
                } 
    
}
