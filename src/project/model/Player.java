package project.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author csd3755 K.D
 * @category Model
 */

public class Player{
	String name;
	boolean isPlaying;
	char lastAreaVisited; /*Can be 0->mosaic, 1->statue, 2->amphora, 3->skeleton*/
	public Map<String,Integer> playerArea;
	public Character[] playerCards;/*can be 0->professor, 1->digger, 2->archaeologist, 3->assistant*/
	int points = 0;
	Player(String id){
		this.name = id;
		playerArea = new HashMap<String,Integer>();
		playerCards = new Character[4];
		lastAreaVisited = 4;
	}
	
	public void setAreaVisited(int area) {
		this.lastAreaVisited = (char)area;
	}
	public int getAreaVisited() {
		return (int)this.lastAreaVisited;
	}
	public void addTile(String tile) {
		int temp = playerArea.get(tile)+1;
		playerArea.put(tile, temp);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public void setIsPlaying(boolean bool) {
		isPlaying = bool;
	}
	public boolean isPlaying() {
		return this.isPlaying;
	}
	
	public void addPoints(int points) {
		this.points += points;
	}
	
	public int getPoints() {
		return this.points;
	}
}