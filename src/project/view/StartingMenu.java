package project.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class StartingMenu extends JFrame implements ActionListener {
	JPanel panel = new JPanel(new GridBagLayout());
	JMenu menu = new JMenu("Choose Playstyle!");
	JMenuBar menuBar = new JMenuBar();
	JButton confirm = new JButton("Confirm");
	JButton close = new JButton("Cancel");
	JMenuItem i1,i2,i3;
	public static int tmp = 0;
	public static File file = null;
	/**
	 * @brief constructor of StartingMenu where it happens
	 */
	public StartingMenu() {
		super("WelcomeToAmphipolis");
		confirm.addActionListener(this);
		close.addActionListener(this);
		menuBar.setBackground(new Color(255,215,0));
		menuBar.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
		i1 = new JMenuItem("Solo");
		i2 = new JMenuItem("4 players");
		i3 = new JMenuItem("Load a game");
		i1.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		i2.setBorder(BorderFactory.createLineBorder(new Color(100,190,40), 3));
		i3.setBorder(BorderFactory.createLineBorder(Color.blue, 3));
		i1.setIconTextGap(78);
		i2.setIconTextGap(78);
		i3.setIconTextGap(70);
		menu.setIconTextGap(94);
		i1.setBackground(new Color(235,167,81));
		i2.setBackground(new Color(177,187,42));
		i3.setBackground(new Color(42,125,195));
		i1.addActionListener(this);
		i2.addActionListener(this);
		i3.addActionListener(this);
		menu.add(i1);
		menu.add(i2);
		menu.add(i3);
		menuBar.add(menu);	
		setJMenuBar(menuBar);
		setBounds(600,350,311,105);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		confirm.setBorder(BorderFactory.createLineBorder(Color.black,2));
		close.setBorder(BorderFactory.createLineBorder(Color.black,2));
		panel.setBackground(Color.DARK_GRAY);
		Insets myInset = new Insets(0,0,0,0);
		GridBagConstraints constr = new GridBagConstraints(0,0,2,1,1.0,1.0,GridBagConstraints.LAST_LINE_START,GridBagConstraints.NONE,myInset,90,30);
		panel.add(confirm,constr);
		constr.anchor = GridBagConstraints.LAST_LINE_END;
		constr.gridx = 1;
		panel.add(close,constr);
		add(panel);
	}
	public synchronized void actionPerformed(ActionEvent e) { 
		String s = e.getActionCommand();
		if(s.equals("4 players"))	{
			menuBar.setBackground(new Color(177,187,42));
			menu.setText(s);
			menu.setIconTextGap(119);
		}
		else if(s.equals("Solo")) {
			menuBar.setBackground(new Color(235,167,81));
			menu.setText(s);
			menu.setIconTextGap(131);
		}
		else if(s.equals("Confirm") && ( menu.getText().equals("4 players") || menu.getText().equals("Solo"))){
			if(menu.getText().equals("Solo")) {
				tmp = 1;
				dispose();
			}
			else {
				tmp = 4;
				dispose();
			}
		}
		else if(s.equals("Cancel"))
			System.exit(0);
		else { // if equals load a game
			menuBar.setBackground(new Color(42,125,195));
			menu.setText(s);
			menu.setIconTextGap(109);
			JFileChooser fileChooser = new JFileChooser("Amphipolis_saves\\");
			fileChooser.setDialogTitle("Select a file");
			int userSelection = fileChooser.showOpenDialog(null);
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();
				if( file!= null && file.getName().endsWith(".txt")) {
					tmp = 5;
					dispose();
				}
			}
		}
	}
	/*public static void main(String[] args) {
		new StartingMenu();
		while(StartingMenu.tmp == 0) {
			try {
				Thread.sleep(600);
			}
			catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}*/
}