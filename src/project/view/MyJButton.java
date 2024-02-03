package project.view;

import javax.swing.JButton;

public class MyJButton extends JButton{
	String id;
	public MyJButton(String text) {
		super(text);
	}
	public MyJButton() {
		super();
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
}
