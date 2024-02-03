package project.model;

import java.util.ArrayList;

/**
 * 
 * @author csd3755 K.D
 * @category Model
 *
 */
public interface Character {
	String getId();
	boolean isEnabled();
	void setEnable(boolean bool);
	int hasPlayed();
	void setHasPlayed();
}

class Assistant implements Character{
	boolean enabled;
	String name;
	Assistant(String name) {
		this.enabled = true;
		this.name = name;
	}
	public String getId() {
		return this.name;
	}
	public void setEnable(boolean bool) {
		this.enabled = bool;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	public int hasPlayed() {return 0;}
	public void setHasPlayed() {};
}

class Archaeologist implements Character{
	char hasPlayed = 0;
	boolean enabled;
	String name;
	Archaeologist(String name) {
		this.enabled = true;
		this.name = name;
	}
	
	public String getId() {
		return this.name;
	}
	
	public void setEnable(boolean bool) {
		this.enabled = bool;
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setHasPlayed() {
		this.hasPlayed++;
	}
	
	public int hasPlayed() {
		return this.hasPlayed;
	}
	
}
class Digger implements Character{
	boolean enabled;
	String name;
	char hasPlayed = 0;
	
	Digger(String name) {
		this.enabled = true;
		this.name = name;
	}
	public String getId() {
		return this.name;
	}
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnable(boolean bool) {
		this.enabled = bool;
	}
	public void setHasPlayed() {
		this.hasPlayed++;
	}
	
	public int hasPlayed() {
		return this.hasPlayed;
	}
}
class Professor implements Character{
	char hasPlayed = 0;
	boolean enabled;
	String name;
	Professor(String name) {
		this.enabled = true;
		this.name = name;
	}
	
	public String getId() {
		return this.name;
	}
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnable(boolean bool) {
		this.enabled = bool;
	}
	public void setHasPlayed() {
		this.hasPlayed++;
	}
	
	public int hasPlayed() {
		return this.hasPlayed;
	}
}
