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
public class UserDetail {
        private String id;
	private String user_id;
	private String time_weight;
        private String cost_weight;
        private String delay_weight;
	private String walk_weight;
        
        public UserDetail(String id, String user_id,String time_weight, String cost_weight,String delay_weight,String walk_weight) {
        this.id = id;
        this.user_id = user_id;
        this.time_weight = time_weight;
        this.cost_weight = cost_weight;
        this.delay_weight = delay_weight;
        this.walk_weight = walk_weight;
        }

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return user_id;
	}
	public void setUserId(String user_id) {
		this.user_id = user_id;
	}
	
	public String getTimeWeight() {
		return time_weight;
	}
	public void setTimeWeight(String time_weight) {
		this.time_weight = time_weight;
	}
        public String getCostWeight() {
		return cost_weight;
	}
	public void setCostWeight(String cost_weight) {
		this.cost_weight = cost_weight;
	}
        public String getDelayWeight() {
		return delay_weight;
	}
	public void setDelayWeight(String delay_weight) {
		this.delay_weight = delay_weight;
	}
        public String getWalkWeight() {
		return walk_weight;
	}
	public void setWalkWeight(String walk_weight) {
		this.walk_weight = walk_weight;
	}
	
	@Override
	public String toString(){
		return "\n"+getId()+","+getUserId()+","+getTimeWeight()+","+getCostWeight()+","+getDelayWeight()+","+getWalkWeight()+"";
	}
   
}
