/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocialRecSimulation;

/**
 *
 * @author SocialRec
 */
public class TravelRoute {
        public String id;
	public String travel_name;
        public String travel_time;
        public String travel_cost;
        public String delay;
	public String walk;
        public String congestion;
        public String accident;
        public String pollution;
        public String traffic_con;
        
        public TravelRoute(String id, String travel_name,String travel_time,String travel_cost,
                String delay,String walk,String congestion,String accident,String pollution,
                String traffic_con) {
        this.id = id;
        this.travel_name = travel_name;
        this.travel_time = travel_time;
        this.travel_cost = travel_cost;
        this.delay = delay;
        this.walk = walk;
        this.congestion = congestion;
        this.accident = accident;
        this.pollution = pollution;
        this.traffic_con = traffic_con;
        }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTravelName() {
		return travel_name;
	}
	public void setTravelName(String travel_name) {
		this.travel_name = travel_name;
	}
	public String getTravelTime() {
		return travel_time;
	}
	public void setTravelTime(String travel_time) {
		this.travel_time = travel_time;
	}
	public String getTravelCost() {
		return travel_cost;
	}
	public void setTravelCost(String travel_cost) {
		this.travel_cost = travel_cost;
	}
        public String getDelay() {
		return delay;
	}
	public void setDelay(String delay) {
		this.delay = delay;
	}
        public String getWalk() {
		return walk;
	}
	public void setWalk(String walk) {
		this.walk = walk;
	}
        public String getCongestion() {
		return congestion;
	}
	public void setCongestion(String congestion) {
		this.congestion = congestion;
	}
        public String getAccident() {
		return accident;
	}
	public void setAccident(String accident) {
		this.accident = accident;
	}
        public String getPollution() {
		return pollution;
	}
	public void setPollution(String pollution) {
		this.pollution = pollution;
	}
        public String getTrafficCon() {
		return traffic_con;
	}
	public void setTrafficCon(String traffic_con) {
		this.traffic_con = traffic_con;
	}
	
	@Override
	public String toString(){
		return "\n"+getId()+","+getTravelName()+","+getTravelTime()+","+getTravelCost()+
                        ","+getDelay()+","+getWalk()+","+getCongestion()+","+getAccident()+
                        ","+getPollution()+","+getTrafficCon()+"";
	}
   
}
