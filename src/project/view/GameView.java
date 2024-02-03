package project.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import project.controller.MyController;
import project.model.Board;
import project.model.Player;
import project.model.Tile;


/**
 * 
 * @author csd3755 K.D
 *
 */
public class GameView extends JFrame implements ActionListener{
	URL imgURL;
	Image img;
	JMenuBar menubar = new JMenuBar();
	JMenu menu = new JMenu("Options");
	JMenuItem item = new JMenuItem("Save");
	public JLabel[] landslideArea = new JLabel[16];

	ClassLoader cldr;
	JLayeredPaneExtension basicPane;
	
	public Map<String, JLabel> bagLabelHash = new HashMap<String, JLabel>();/*hashing of players "bag" labels and the rest labels*/
	public Map<String, MyJButton> buttonHash = new HashMap<String, MyJButton>();
	
	
	

	
	/**
	 * @brief Constructor makes the board and everything basic needed to start GUI
	 * @param numOfPlayers
	 */
	public GameView(int numOfPlayers, ArrayList<String> Names) {
		JLabel[] playerAreaLabel = new JLabel[15];
		JPanel actionPanel = new JPanel(new GridLayout(2,0,0,10));
		JPanel cardsPanel = null;
		MyJButton bchar[] = new MyJButton[4];
		JLabel label;
		menu.setIconTextGap(27);
		item.setIconTextGap(16);
		menu.add(item);
		menubar.add(menu);
		putSaveListener();
		GridBagConstraints c = new GridBagConstraints();
		getContentPane().setLayout(new GridBagLayout());	
		add(menubar,new GridBagConstraints(0,0,1,1,0.1,0.1,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.NONE,new Insets(0,0,40,40),0,0 ));
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 1;	
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.insets = new Insets(5,40,5,45);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		getContentPane().setBackground(new Color(153,214,53));
		cldr = this.getClass().getClassLoader();
		imgURL = cldr.getResource("images_2020/background.png");
		img = new ImageIcon(imgURL).getImage();
		img = img.getScaledInstance(970, 550, java.awt.Image.SCALE_SMOOTH);
		basicPane = new JLayeredPaneExtension(img);
		basicPane.setPreferredSize(new Dimension(970,550));
		basicPane.setMinimumSize(basicPane.getPreferredSize());
		basicPane.setBorder(BorderFactory.createLineBorder(new Color(235,167,81),2));
		add(basicPane,c);
		MyJButton drawTiles = new MyJButton("Draw Tiles");
		MyJButton endTurn = new MyJButton("End Turn");
		drawTiles.setId("drawTiles");
		endTurn.setId("endTurn");
		buttonHash.put("drawTiles", drawTiles);
		buttonHash.put("endTurn", endTurn);
		actionPanel.add(drawTiles);
		actionPanel.add(endTurn);
		Border border;		
		c.weightx = 0.5;
		c.weighty = 0.5;
		GridBagConstraints tempC = new GridBagConstraints(0,0,1,6,1.0,1.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.NONE,new Insets(4,20,4,10),80,15);
		for(int j =0; j<numOfPlayers;j++) {
			if(j == 0) {
				cardsPanel = new JPanel(new GridBagLayout());
				/*Start of Players Bag Area*/
					JPanel playersBag = new JPanel(new GridLayout(3,5,2,2));
					playersBag.setBackground(new Color(161,115,84));
					playersBag.setBorder(BorderFactory.createLineBorder(new Color(102,65,103),2));
					for(int i=0;i<15;i++) {
						if(i==0) {
							imgURL = cldr.getResource("images_2020/mosaic_green.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("mosaic_green", playerAreaLabel[i]);
						}
						else if(i==1) {
							imgURL = cldr.getResource("images_2020/amphora_blue.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_blue", playerAreaLabel[i]);
						}
						else if(i==2) {
							imgURL = cldr.getResource("images_2020/amphora_green.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_green", playerAreaLabel[i]);
						}
						else if(i==3) {
							imgURL = cldr.getResource("images_2020/caryatid.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("statue_caryatid", playerAreaLabel[i]);
						}
						else if(i==4) {
							imgURL = cldr.getResource("images_2020/sphinx.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("statue_sphinx", playerAreaLabel[i]);
						}
						else if(i==5) {
							imgURL = cldr.getResource("images_2020/mosaic_red.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("mosaic_red", playerAreaLabel[i]);
						}
						else if(i==6) {
							imgURL = cldr.getResource("images_2020/amphora_brown.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_brown", playerAreaLabel[i]);
						}
						else if(i==7) {
							imgURL = cldr.getResource("images_2020/amphora_yellow.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_yellow", playerAreaLabel[i]);
						}
						else if(i==8) {
							imgURL = cldr.getResource("images_2020/skeleton_big_top.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("skeleton_big_top", playerAreaLabel[i]);
						}
						else if(i==9) {
							imgURL = cldr.getResource("images_2020/skeleton_small_top.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("skeleton_small_top", playerAreaLabel[i]);
						}
						else if(i==10) {
							imgURL = cldr.getResource("images_2020/mosaic_yellow.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("mosaic_yellow", playerAreaLabel[i]);
						}
						else if(i==11) {
							imgURL = cldr.getResource("images_2020/amphora_red.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_red", playerAreaLabel[i]);
						}
						else if(i==12) {
							imgURL = cldr.getResource("images_2020/amphora_purple.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("amphora_purple", playerAreaLabel[i]);
						}
						else if(i==13) {
							imgURL = cldr.getResource("images_2020/skeleton_big_bottom.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("skeleton_big_bottom", playerAreaLabel[i]);
						}
						else if(i==14) {
							imgURL = cldr.getResource("images_2020/skeleton_small_bottom.png");
							playerAreaLabel[i] = new JLabel("0x");
							bagLabelHash.put("skeleton_small_bottom", playerAreaLabel[i]);
						}
						img = new ImageIcon(imgURL).getImage();
						img = img.getScaledInstance(30, 35, java.awt.Image.SCALE_SMOOTH);
						playerAreaLabel[i].setIcon(new ImageIcon(img));
						playersBag.add(playerAreaLabel[i]);
					}
					cardsPanel.add(playersBag,tempC);
				/*end of Players Bag Area*/
				
				tempC.insets = new Insets(0,2,2,3);
				tempC.ipadx = 10;
				tempC.ipady = 5;
				tempC.gridx = 1;
				label = new JLabel(Names.get(0));
				bagLabelHash.put(Names.get(0),label);
				border = BorderFactory.createLineBorder(Color.black);
				c.gridx = 1;
				c.gridy = 2;
				c.anchor = GridBagConstraints.SOUTH;
				c.fill = GridBagConstraints.NONE;
				c.insets = new Insets(5,5,15,10);
				
				cardsPanel.setBackground(new Color(153,214,53));
				cardsPanel.add(label,tempC);
			}
			else if(j==1) {
				label = new JLabel(Names.get(1));
				bagLabelHash.put(Names.get(1),label);
				border = BorderFactory.createLineBorder(Color.red);
				c.gridx = 0;
				c.gridy = 1;
				c.anchor = GridBagConstraints.WEST;
				c.insets = new Insets(10,65,10,25);
				cardsPanel = new JPanel(new GridLayout(5,0,3,3));
				cardsPanel.setBackground(new Color(153,214,53));
				cardsPanel.add(label);
			}
			else if(j==2) {
				label = new JLabel(Names.get(2));
				bagLabelHash.put(Names.get(2),label);
				border = BorderFactory.createLineBorder(Color.blue);
				c.gridx = 1;
				c.gridy = 0;
				c.anchor = GridBagConstraints.NORTH;
				c.insets = new Insets(10,15,0,15);
				cardsPanel = new JPanel(new GridLayout(0,5,3,3));
				cardsPanel.setBackground(new Color(153,214,53));
				cardsPanel.add(label);
			}
			else {
				label = new JLabel(Names.get(3));
				bagLabelHash.put(Names.get(3),label);
				border = BorderFactory.createLineBorder(Color.yellow);
				c.gridx = 2;
				c.gridy = 1;
				c.anchor = GridBagConstraints.EAST;
				c.weightx = 1.5;
				c.insets = new Insets(10,25,10,25);
				cardsPanel = new JPanel(new GridLayout(5,0,3,3));
				cardsPanel.setBackground(new Color(153,214,53));
				cardsPanel.add(label);
			}
			for(int i =0; i< 4;++i) {
				if(j==0) {
					bchar[i] = new MyJButton();	
				}
				label = new JLabel();
				if(i==0) {
					if(j == 0 || j == 2) {
						imgURL = cldr.getResource("images_2020/professor.png");
						if(j == 0) {
							bchar[0].setId("professor");
							buttonHash.put("professor", bchar[0]);
							img = new ImageIcon(imgURL).getImage();
							img = img.getScaledInstance(85, 105, java.awt.Image.SCALE_SMOOTH);
							bchar[i].setToolTipText("Take one Tile from each sorting area that you did not visit");
							bchar[i].setIcon(new ImageIcon(img));	
							bchar[i].setBorder(border);
							tempC.gridx=2;
							tempC.anchor = GridBagConstraints.LAST_LINE_END;
							cardsPanel.add(bchar[i],tempC);
							continue;
						}
						else {
							bagLabelHash.put("professorUP",label);
						}
					}
					else if(j==1) {
						imgURL = cldr.getResource("images_2020/professor_90_right.png");
						bagLabelHash.put("professorLEFT",label);
					}
					else {
						imgURL = cldr.getResource("images_2020/professor_90_left.png");
						bagLabelHash.put("professorRIGHT",label);
					}
					label.setBorder(border);
				}
				else if(i==1) {
					if(j == 0 || j == 2) {
						imgURL = cldr.getResource("images_2020/digger.png");
						if(j==0)	{
							bchar[1].setId("digger");
							buttonHash.put("digger", bchar[1]);
							img = new ImageIcon(imgURL).getImage();
							img = img.getScaledInstance(85, 105, java.awt.Image.SCALE_SMOOTH);
							bchar[i].setToolTipText("Take two Tiles from the same sorting area you visited");
							bchar[i].setIcon(new ImageIcon(img));		
							bchar[i].setBorder(border);
							tempC.gridx=3;
							cardsPanel.add(bchar[i],tempC);
							continue;
						}
						else {
							bagLabelHash.put("diggerUP",label);
						}
					}
					else if(j==1) {
						imgURL = cldr.getResource("images_2020/digger_90_right.png");
						bagLabelHash.put("diggerLEFT",label);
					}
					else {
						imgURL = cldr.getResource("images_2020/digger_90_left.png");
						bagLabelHash.put("diggerRIGHT",label);
					}
					label.setBorder(border);
				}
				else if(i==2) {
					if(j == 0 || j == 2) {
						imgURL = cldr.getResource("images_2020/archaeologist.png");
						if(j==0) {
							bchar[2].setId("archaeologist");
							buttonHash.put("archaeologist", bchar[2]);
							img = new ImageIcon(imgURL).getImage();
							img = img.getScaledInstance(85, 105, java.awt.Image.SCALE_SMOOTH);
							bchar[i].setToolTipText("Take two Tiles from a sorting area other than the one you visited");
							bchar[i].setIcon(new ImageIcon(img));
							bchar[i].setBorder(border);
							tempC.gridx=4;
							cardsPanel.add(bchar[i],tempC);
							continue;
						}
						else{
							bagLabelHash.put("archaeologistUP",label);
						}
					}
					else if(j==1) {
						imgURL = cldr.getResource("images_2020/archaeologist_90_right.png");
						bagLabelHash.put("archaeologistLEFT",label);
					}
					else {
						imgURL = cldr.getResource("images_2020/archaeologist_90_left.png");	
						bagLabelHash.put("archaeologistRIGHT",label);
					}
					label.setBorder(border);
				}
				else {
					if(j == 0 || j == 2) {
						imgURL = cldr.getResource("images_2020/assistant.png");
						if(j==0) {
							bchar[3].setId("assistant");
							buttonHash.put("assistant", bchar[3]);
							img = new ImageIcon(imgURL).getImage();
							img = img.getScaledInstance(85, 105, java.awt.Image.SCALE_SMOOTH);
							bchar[i].setToolTipText("Take one Tile from any sorting area");
							bchar[i].setIcon(new ImageIcon(img));	
							bchar[i].setBorder(border);
							tempC.gridx=5;
							cardsPanel.add(bchar[i],tempC);
							continue;
						}
						else {
							bagLabelHash.put("assistantUP",label);
						}
					}
					else if(j==1) {
						imgURL = cldr.getResource("images_2020/assistant_90_right.png");
						bagLabelHash.put("assistantLEFT",label);
					}
					else {
						imgURL = cldr.getResource("images_2020/assistant_90_left.png");	
						bagLabelHash.put("assistantRIGHT",label);
					}
					label.setBorder(border);
				}
				//fixing the scaled instances due to rotating images
				if(j == 0 || j == 2) {
					img = new ImageIcon(imgURL).getImage();
					img = img.getScaledInstance(95, 105, java.awt.Image.SCALE_SMOOTH);
				}
				else {
					img = new ImageIcon(imgURL).getImage();
					img = img.getScaledInstance(120, 90, java.awt.Image.SCALE_SMOOTH);
				}
				label.setIcon(new ImageIcon(img));			
				cardsPanel.add(label,i+1);
			}
			add(cardsPanel,c);
		}
		
		if(numOfPlayers==4) {	
			c.gridy = 2;
			c.gridx = 2;
			c.ipadx = 80;
			c.ipady = 40;
			c.insets = new Insets(10,5,5,5);
			c.fill = GridBagConstraints.BOTH;
			actionPanel.setBackground(new Color(153,214,53));
			add(actionPanel,c);
		}
		else{
			getContentPane().setBackground(new Color(235,167,81));
			c.gridy = 1;
			c.gridx = 2;
			c.ipadx = 125;
			c.ipady = 130;
			cardsPanel.setBackground(new Color(235,167,81));
			actionPanel.setBackground(new Color(235,167,81));
			imgURL = cldr.getResource("images_2020/thief.jpg");
			img = new ImageIcon(imgURL).getImage();
			img = img.getScaledInstance(195, 305, java.awt.Image.SCALE_SMOOTH);
			label = new JLabel();
			label.setIcon(new ImageIcon(img));
			c.anchor = GridBagConstraints.LINE_END;
			add(label,c);
			c.anchor = GridBagConstraints.CENTER;
			c.gridy = 2;
			c.ipadx = 100;
			c.ipady = 80;
			add(actionPanel,c);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		setPreferredSize(new Dimension(1550,890));
		setMinimumSize(new Dimension(1380,800));
		pack();
		setResizable(false);
	}
	//End of constructor GameView
	
	/**
	 * @brief method that initializes the areas and buttons of basic pane
	 * @postcondition all buttons are disabled
	 */
	public void makeTileAreas() {
		int i = 0;
		basicPane.setLayout(new GridBagLayout());
		JPanel tempPan;
		Insets myInset = new Insets(12,24,30,40);	
		MyJButton[] boardButton = new MyJButton[15];
		GridBagConstraints tmpCon = 
				new GridBagConstraints(0,0,3,3,1.0,1.0,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.NONE,myInset,130,79);
		
		//mosaic
					
			tempPan = new JPanel(new GridBagLayout());
			tempPan.setBackground(new Color(102,51,51));
			tempPan.setBorder(BorderFactory.createLineBorder(new Color(200,51,51),2));
			for(; i<3 ;i++) {
				boardButton[i] = new MyJButton("0x");

				boardButton[i].setBackground(new Color(50,200,145));
				if(i==0) {
					boardButton[i].setId("mosaic_green");
					buttonHash.put("mosaic_green", boardButton[i]);
					imgURL = cldr.getResource("images_2020/mosaic_green.png");		
					tempPan.add(boardButton[i], 
							new GridBagConstraints(0,0,2,2,0.5,0.5,GridBagConstraints.FIRST_LINE_START,GridBagConstraints.NONE,new Insets(8,15,5,5),26,23));
				}
				else if(i==1) {
					boardButton[i].setId("mosaic_red");
					buttonHash.put("mosaic_red", boardButton[i]);
					imgURL = cldr.getResource("images_2020/mosaic_red.png");
					tempPan.add(boardButton[i],
							new GridBagConstraints(1,0,2,2,0.5,0.5,GridBagConstraints.FIRST_LINE_END,GridBagConstraints.NONE,new Insets(8,5,5,15),26,23));
				}
				else {
					boardButton[i].setId("mosaic_yellow");
					buttonHash.put("mosaic_yellow", boardButton[i]);
					imgURL = cldr.getResource("images_2020/mosaic_yellow.png");
					tempPan.add(boardButton[i], 
							new GridBagConstraints(1,1,2,2,0.5,0.5,GridBagConstraints.SOUTH,GridBagConstraints.NONE,new Insets(5,18,8,18),26,23));
				}
				img = new ImageIcon(imgURL).getImage();
				img = img.getScaledInstance(40, 33, java.awt.Image.SCALE_SMOOTH);
				boardButton[i].setIcon(new ImageIcon(img));
				boardButton[i].setEnabled(false);
			}
			basicPane.add(tempPan,tmpCon);
				
		//statue
		
		tempPan = new JPanel(new GridBagLayout());
		myInset.set(11, 120, 50, 24);
		tmpCon.anchor = GridBagConstraints.FIRST_LINE_END;
		tmpCon.gridy = 0;
		tmpCon.gridx = 2;
		tmpCon.ipady = 73;
		tmpCon.ipadx = 99;
		tempPan.setBackground(new Color(100,77,201));
		tempPan.setBorder(BorderFactory.createLineBorder(new Color(14,73,43), 4));
		
		for(;i<5; ++i) {
			boardButton[i] = new MyJButton("0x");
			boardButton[i].setBackground(new Color(61,137,212));

			if(i==3) {
				boardButton[i].setId("statue_caryatid");
				buttonHash.put("statue_caryatid", boardButton[i]);
				imgURL = cldr.getResource("images_2020/caryatid.png");
				tempPan.add(boardButton[i], new GridBagConstraints(0,0,1,2,1.0,1.0,GridBagConstraints.NORTH,
						GridBagConstraints.NONE,new Insets(5,15,9,15),23,16));
			}
			else {
				boardButton[i].setId("statue_sphinx");
				buttonHash.put("statue_sphinx", boardButton[i]);
				imgURL = cldr.getResource("images_2020/sphinx.png");
				tempPan.add(boardButton[i], new GridBagConstraints(0,1,1,2,1.0,1.0,GridBagConstraints.SOUTH,
						GridBagConstraints.NONE,new Insets(9,15,5,15),23,16));
			}
			img = new ImageIcon(imgURL).getImage();
			img = img.getScaledInstance(74, 42, java.awt.Image.SCALE_SMOOTH);
			boardButton[i].setIcon(new ImageIcon(img));
			boardButton[i].setEnabled(false);
		}
		basicPane.add(tempPan,tmpCon); 
		
		//landslides
		
		imgURL = cldr.getResource("images_2020/landslide.png");
		img = new ImageIcon(imgURL).getImage();
		img = img.getScaledInstance(54, 42, java.awt.Image.SCALE_SMOOTH);
		tempPan = new JPanel(new GridLayout(4,4));
		tmpCon.anchor = GridBagConstraints.CENTER;
		tmpCon.gridy = 1;
		tmpCon.gridx = 1;
		myInset.set(85,20,65,20);
		tmpCon.ipadx = 52;
		tmpCon.ipady = 44;
		int tmp = 15 - MyController.landslideCounter;
		Border B = BorderFactory.createEtchedBorder(5, new Color(102,51,51), new Color(255,128,0));
		for(int j = 15; j>-1; j--) {
			if(tmp>0) {
				landslideArea[j] = new JLabel(new ImageIcon(img));
				landslideArea[j].setBackground(new Color(102,51,51));
				landslideArea[j].setOpaque(true);
				landslideArea[j].setBorder(B);
				tempPan.add(landslideArea[j]);
				tmp--;
			}
			else {
				imgURL = cldr.getResource("images_2020/tile_back.png");
				img = new ImageIcon(imgURL).getImage();
				img = img.getScaledInstance(54, 42, java.awt.Image.SCALE_SMOOTH);
				landslideArea[j] = new JLabel(new ImageIcon(img));
				landslideArea[j].setBackground(new Color(102,51,51));
				landslideArea[j].setOpaque(true);
				landslideArea[j].setBorder(B);
				tempPan.add(landslideArea[j]);
			}
		}
		basicPane.add(tempPan,tmpCon);
		
		//amphora
		
		myInset.set(100,24,12,20);
		tmpCon.gridx = 0;
		tmpCon.gridy = 2;
		tmpCon.anchor = GridBagConstraints.LAST_LINE_START;
		tmpCon.ipadx = 58;
		tmpCon.ipady = 50;
		tempPan = new JPanel(new GridLayout(2,3));
		for(;i<11;i++) {
			boardButton[i] = new MyJButton("0x");
			if(i==5) {
				boardButton[i].setId("amphora_blue");
				buttonHash.put("amphora_blue", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_blue.png");
			}
			else if(i==6) {
				boardButton[i].setId("amphora_brown");
				buttonHash.put("amphora_brown", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_brown.png");
			}
			else if(i==7) {
				boardButton[i].setId("amphora_green");
				buttonHash.put("amphora_green", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_green.png");
			}
			else if(i==8) {
				boardButton[i].setId("amphora_purple");
				buttonHash.put("amphora_purple", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_purple.png");
			}
			else if(i==9) {
				boardButton[i].setId("amphora_red");
				buttonHash.put("amphora_red", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_red.png");
			}
			else {
				boardButton[i].setId("amphora_yellow");
				buttonHash.put("amphora_yellow", boardButton[i]);
				imgURL = cldr.getResource("images_2020/amphora_yellow.png");
			}
			img = new ImageIcon(imgURL).getImage();
			img = img.getScaledInstance(55, 52, java.awt.Image.SCALE_SMOOTH);
			boardButton[i].setIcon(new ImageIcon(img));
			boardButton[i].setBackground(new Color(220,149,22));
			boardButton[i].setBorder(BorderFactory.createEtchedBorder(1, new Color(0,102,51), new Color(102,255,102)));
			boardButton[i].setEnabled(false);
			tempPan.add(boardButton[i]);
		}
		basicPane.add(tempPan,tmpCon);
		
		//skeletons
		
		myInset.set(100,90,12,24);
		tempPan = new JPanel(new GridLayout(2,2,5,5));
		tmpCon.gridy = 2;
		tmpCon.anchor = GridBagConstraints.LAST_LINE_END;
		tmpCon.gridx = 2;
		tmpCon.ipadx = 83;
		tmpCon.ipady = 34;
		tempPan.setBorder(BorderFactory.createBevelBorder(3, Color.DARK_GRAY, Color.red));
		tempPan.setBackground(new Color(221,242,116));
		for(;i<15;i++) {
			boardButton[i] = new MyJButton("0x");
			if(i==11) {
				boardButton[i].setId("skeleton_big_top");
				buttonHash.put("skeleton_big_top", boardButton[i]);
				imgURL = cldr.getResource("images_2020/skeleton_big_top.png");
			}
			else if(i==12) {
				boardButton[i].setId("skeleton_small_top");
				buttonHash.put("skeleton_small_top", boardButton[i]);
				imgURL = cldr.getResource("images_2020/skeleton_small_top.png");
			}
			else if(i==13) {
				boardButton[i].setId("skeleton_big_bottom");
				buttonHash.put("skeleton_big_bottom", boardButton[i]);
				imgURL = cldr.getResource("images_2020/skeleton_big_bottom.png");
			}
			else {
				boardButton[i].setId("skeleton_small_bottom");
				buttonHash.put("skeleton_small_bottom", boardButton[i]);
				imgURL = cldr.getResource("images_2020/skeleton_small_bottom.png");
			}
			
			img = new ImageIcon(imgURL).getImage();
			img = img.getScaledInstance(75, 56, java.awt.Image.SCALE_SMOOTH);
			boardButton[i].setIcon(new ImageIcon(img));
			boardButton[i].setBackground(new Color(180,206,118));
			boardButton[i].setBorder(BorderFactory.createEtchedBorder(8, new Color(117,65,50), Color.red));
			boardButton[i].setEnabled(false);
			tempPan.add(boardButton[i]);
		}
		basicPane.add(tempPan,tmpCon);
		this.show();
		for(Map.Entry<String, MyJButton> m: buttonHash.entrySet()) {
			m.getValue().addActionListener(this);
		}
	}
	
	
	/**
	 * @brief will insert the selected Tile in player's area
	 * @param P is the player currently playing, that i want to update
	 * @precondition must be player's turn
	 * @postcondition if move is valid the board loses this icon image</br>and it goes to the player's area.
	 * @invariant the button's position on board
	 */
	public void refreshPlayerArea(Player P){
		for(Map.Entry<String,Integer> m : P.playerArea.entrySet()) {
			bagLabelHash.get(m.getKey()).setText(m.getValue()+"x");
		}
		for(int i=0;i<4;i++) {
			buttonHash.get(P.playerCards[i].getId()).setEnabled(P.playerCards[i].isEnabled());
		}
	}
	
	/**
	 * @brief refreshes the texts of buttons and can also disable or enable them
	 */
	public int refreshBoard(String tile,int num,int landslideCounter) {
		if(tile.equals("landslides")) {
			imgURL = cldr.getResource("images_2020/landslide.png");
			img = new ImageIcon(imgURL).getImage();
			img = img.getScaledInstance(54, 42, java.awt.Image.SCALE_SMOOTH);
			landslideArea[landslideCounter].setIcon(new ImageIcon(img));
			return --landslideCounter;
		}
		else {
			buttonHash.get(tile).setText(num+"x");
			if(MyController.board.activeTiles.get(tile)!=0 && MyController.board.playerList[MyController.board.index%4].isPlaying())
				buttonHash.get(tile).setEnabled(true);
			return landslideCounter;
		}
	}
	/**
	 * @brief Changes the images of board's players cards and names
	 * @param P is the Player that was playing
	 */
	public void rotateView(Board b) {
		int temp = b.index;
		int toFix = temp+3;
		Player p2Fix = b.playerList[toFix%4];
		Border B2Fix = bagLabelHash.get("diggerRIGHT").getBorder();
		String name2Fix = p2Fix.getName();
		for(int i=0;i<3;++i,++temp) {
			Player P = b.playerList[temp%4];
			String curName = P.getName();		
			String nextName = b.playerList[(temp+1)%4].getName();
			bagLabelHash.get(curName).setText(nextName);		
			for(int j=0;j<4;j++) {
				if(i==0) {
					bagLabelHash.get(P.playerCards[j].getId()+"RIGHT").setVisible(P.playerCards[j].isEnabled());
					bagLabelHash.get(P.playerCards[j].getId()+"RIGHT").setBorder(buttonHash.get("digger").getBorder());
				}
				else if(i==1) {
					refreshPlayerArea(P);//active player's character buttons are being handled in refresh_player_area
					buttonHash.get(P.playerCards[j].getId()).setBorder(bagLabelHash.get("diggerLEFT").getBorder());
				}
				else if(i==2) {
					bagLabelHash.get(P.playerCards[j].getId()+"LEFT").setVisible(P.playerCards[j].isEnabled());
					bagLabelHash.get(P.playerCards[j].getId()+"LEFT").setBorder(bagLabelHash.get("diggerUP").getBorder());
				}
			}
		}
		//o temp exei ginei index+3 ara "deixnei" sta deksia
		bagLabelHash.get(name2Fix).setText(b.playerList[b.index%4].getName());
		JLabel tempL = bagLabelHash.get(b.playerList[b.index%4].getName());
		for(int i=0; i<3; i++,temp--) {//fixing label hashing
			bagLabelHash.put(b.playerList[(temp+1)%4].getName(), bagLabelHash.get(b.playerList[(temp)%4].getName()));
		}
		bagLabelHash.put(b.playerList[(temp+1)%4].getName(), tempL);
		for(int j=0;j<4;j++) {
			bagLabelHash.get(b.playerList[(toFix-1)%4].playerCards[j].getId()+"UP").setVisible(p2Fix.playerCards[j].isEnabled());
			bagLabelHash.get(b.playerList[(toFix-1)%4].playerCards[j].getId()+"UP").setBorder(B2Fix);
		}
	}
	
	
	
	public void actionPerformed(ActionEvent ev) {
		Border B = buttonHash.get("digger").getBorder();
		if(MyController.charChosen == 1)
			B = buttonHash.get("assistant").getBorder();
		MyJButton buttonPressed = (MyJButton)ev.getSource();
		if(buttonPressed.getId().equals("drawTiles")) {
			MyController.drawTilesAndUpdate(MyController.board.bag);
		}
		else if(buttonPressed.getId().equals("endTurn")){
			if(MyController.charChosen!=4) {
				if(MyController.charChosen == 0) 
					buttonHash.get("professor").setBorder(B);
				else if(MyController.charChosen == 1)
					buttonHash.get("digger").setBorder(B);
				else if(MyController.charChosen == 2)
					buttonHash.get("archaeologist").setBorder(B);
				else if(MyController.charChosen == 3)
					buttonHash.get("assistant").setBorder(B);
				MyController.charChosen = 4;
			}
			MyController.endTurn();
		}
		else if(buttonPressed.getId().equals("assistant")) {
			if(MyController.board.playerList[MyController.board.index%4].isPlaying()) {
				JOptionPane.showMessageDialog(null, "You cannot use a Character card before drawing from a sorting area first"
						, "InfoBox: Game_Rule", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(MyController.charChosen == 0) {
					buttonHash.get("assistant").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("professor").setBorder(B);
					MyController.enableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());					
					MyController.charChosen = 3;
				}
				else if(MyController.charChosen == 1) {
					buttonHash.get("assistant").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("digger").setBorder(B);
					MyController.enableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 3;
				}
				else if(MyController.charChosen == 2) {
					buttonHash.get("assistant").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("archaeologist").setBorder(B);
					MyController.enableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 3;
				}
				else if(MyController.charChosen == 3) {
					buttonHash.get("assistant").setBorder(B);
					MyController.disableAllTileButtons();
					MyController.charChosen = 4;
					}
				else {
					buttonHash.get("assistant").setBorder(BorderFactory.createRaisedBevelBorder());
					MyController.enableTileButtons();
					MyController.charChosen = 3;
				}
			}
		}
		else if(buttonPressed.getId().equals("archaeologist")) {
			if(MyController.board.playerList[MyController.board.index%4].isPlaying()) {
				JOptionPane.showMessageDialog(null, "You cannot use a Character card before drawing from a sorting area first"
						, "InfoBox: " + "Game_Rule", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(MyController.charChosen == 0) {
					buttonHash.get("archaeologist").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("professor").setBorder(B);
					MyController.charChosen = 2;
				}
				else if(MyController.charChosen == 1) {
					buttonHash.get("archaeologist").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("digger").setBorder(B);
					MyController.enableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.disableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 2;
				}
				else if(MyController.charChosen == 2) {
					buttonHash.get("archaeologist").setBorder(B);
					MyController.disableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 4;
				}
				else if(MyController.charChosen == 3) {
					buttonHash.get("archaeologist").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("assistant").setBorder(B);
					MyController.disableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 2;
					}
				else {
					buttonHash.get("archaeologist").setBorder(BorderFactory.createRaisedBevelBorder());
					MyController.enableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 2;
				}
			}
		}
		else if(buttonPressed.getId().equals("digger")) {
			if(MyController.board.playerList[MyController.board.index%4].isPlaying()) {
				JOptionPane.showMessageDialog(null, "You cannot use a Character card before drawing from a sorting area first"
						, "InfoBox: Game_Rule", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(MyController.charChosen == 0) {
					buttonHash.get("digger").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("professor").setBorder(B);
					MyController.disableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.enableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 1;
				}
				else if(MyController.charChosen == 1) {
					buttonHash.get("digger").setBorder(B);
					MyController.disableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 4;
				}
				else if(MyController.charChosen == 2) {
					buttonHash.get("digger").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("archaeologist").setBorder(B);
					MyController.enableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.disableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 1;
				}
				else if(MyController.charChosen == 3) {
					buttonHash.get("digger").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("assistant").setBorder(B);
					MyController.disableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 1;
					}
				else {
					buttonHash.get("digger").setBorder(BorderFactory.createRaisedBevelBorder());
					MyController.charChosen = 1;
					MyController.enableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
				}
			}
		}
		else if(buttonPressed.getId().equals("professor")) {
			if(MyController.board.playerList[MyController.board.index%4].isPlaying()) {
				JOptionPane.showMessageDialog(null, "You cannot use a Character card before drawing from a sorting area first"
						, "InfoBox: " + "Game_Rule", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				if(MyController.charChosen == 0) {
					buttonHash.get("professor").setBorder(B);
					MyController.charChosen = 4;
					MyController.disableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
				}
				else if(MyController.charChosen == 1) {
					buttonHash.get("professor").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("digger").setBorder(B);
					MyController.disableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.enableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 0;
				}
				else if(MyController.charChosen == 2) {
					buttonHash.get("professor").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("archaeologist").setBorder(B);
					MyController.charChosen = 0;
				}
				else if(MyController.charChosen == 3) {
					buttonHash.get("professor").setBorder(BorderFactory.createRaisedBevelBorder());
					buttonHash.get("assistant").setBorder(B);
					MyController.disableSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 0;
					}
				else {
					buttonHash.get("professor").setBorder(BorderFactory.createRaisedBevelBorder());
					MyController.enableNonSortingButtons((char)MyController.board.playerList[MyController.board.index%4].getAreaVisited());
					MyController.charChosen = 0;
				}
			}
		}
		else {
			MyController.playerMove(buttonPressed.getId(),MyController.charChosen,B);
		}
	}
	
	/**
	 * @brief save button listener
	 */
	public void putSaveListener() {
		item.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent ev) {
				int reply = JOptionPane.showConfirmDialog(null, "Do you want to save your progress and exit?","Saving", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_OPTION) {
					String fName = JOptionPane.showInputDialog("Enter file name", "Amphipolis_save#");
					try {
						byte[] buffer = new byte[8192];
						int index = 0;
						if(MyController.board.playerList.length == 1) {
							fName += "_solo.txt";
							String header = "Solo\n"+MyController.board.playerList[0].getName() +" "
							+ (MyController.board.playerList[0].isPlaying() && MyController.board.playerList[0].getAreaVisited() == 4)+"\n";
							String body = "";
							for(int i=0;i<4;i++) {
								body += MyController.board.playerList[0].playerCards[i].getId() +
									" " + MyController.board.playerList[0].playerCards[i].isEnabled()+"\n";
							}
							for(Map.Entry<String,Integer> M : MyController.board.playerList[0].playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "thief\n";
							body = "";
							for(Map.Entry<String,Integer> M : MyController.board.thief.playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,++index) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "activetiles\n"+"landslides "+ MyController.landslideCounter+"\n";
							body = "";
							for(Map.Entry<String,Integer> M : MyController.board.activeTiles.entrySet()){
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							
						}
						else {
							fName += "_4Player.txt";
							String header = MyController.board.playerList[MyController.board.index%4].getName()+" "
									+MyController.board.playerList[(MyController.board.index+1)%4].getName() + " "
											+MyController.board.playerList[(MyController.board.index+2)%4].getName() + " "
													+MyController.board.playerList[(MyController.board.index+3)%4].getName()+"\n";//names for init gameView
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							header = MyController.board.playerList[MyController.board.index%4].getName()+" "
									+ (MyController.board.playerList[MyController.board.index%4].isPlaying() && 
											MyController.board.playerList[0].getAreaVisited() == 4)+"\n";
							String body = "";
							for(int i=0;i<4;i++) {
								body += MyController.board.playerList[MyController.board.index%4].playerCards[i].getId() + 
										" " + MyController.board.playerList[MyController.board.index%4].playerCards[i].isEnabled()+"\n";
							}
							for(Map.Entry<String,Integer> M : MyController.board.playerList[MyController.board.index%4].playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "End\n"+MyController.board.playerList[(MyController.board.index+1)%4].getName()+" "
									+ (MyController.board.playerList[(MyController.board.index+1)%4].isPlaying() 
									&& MyController.board.playerList[0].getAreaVisited() == 4)+"\n";
							body = "";
							for(int i=0;i<4;i++) {
								body += MyController.board.playerList[(MyController.board.index+1)%4].playerCards[i].getId() + 
										" " + MyController.board.playerList[(MyController.board.index+1)%4].playerCards[i].isEnabled()+"\n";
							}
							for(Map.Entry<String,Integer> M : MyController.board.playerList[(MyController.board.index+1)%4].playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "End\n"+MyController.board.playerList[(MyController.board.index+2)%4].getName()+" "
									+ (MyController.board.playerList[(MyController.board.index+2)%4].isPlaying() 
											&& MyController.board.playerList[0].getAreaVisited() == 4)+"\n";
							body = "";
							for(int i=0;i<4;i++) {
								body += MyController.board.playerList[(MyController.board.index+2)%4].playerCards[i].getId() + 
										" " + MyController.board.playerList[(MyController.board.index+2)%4].playerCards[i].isEnabled()+"\n";
							}
							for(Map.Entry<String,Integer> M : MyController.board.playerList[(MyController.board.index+2)%4].playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "End\n"+MyController.board.playerList[(MyController.board.index+3)%4].getName()+" "
									+ (MyController.board.playerList[(MyController.board.index+3)%4].isPlaying() 
											&& MyController.board.playerList[0].getAreaVisited() == 4)+"\n";
							body = "";
							for(int i=0;i<4;i++) {
								body += MyController.board.playerList[(MyController.board.index+3)%4].playerCards[i].getId() + 
										" " + MyController.board.playerList[(MyController.board.index+3)%4].playerCards[i].isEnabled()+"\n";
							}
							for(Map.Entry<String,Integer> M : MyController.board.playerList[(MyController.board.index+3)%4].playerArea.entrySet()) {
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
							header = "End\nindex "+MyController.board.index%4+"\n"
							+"landslides "+ MyController.landslideCounter+"\n"+"activetiles\n";
							body = "";
							for(Map.Entry<String,Integer> M : MyController.board.activeTiles.entrySet()){
								body += M.getKey() + " " + M.getValue() + "\n";
							}
							for(int i=0;i<header.length();++i,index++) {
								buffer[index] = (byte)header.charAt(i);
							}
							for(int i=0;i<body.length();++i,index++) {
								buffer[index] = (byte)body.charAt(i);
							}
						}
						String Bag = "Bag\n";
						while(!MyController.board.bag.isEmpty()) {
							Bag += MyController.board.bag.remove(0).getId() +"\n";
						}
						for(int i=0;i<Bag.length();i++) {
							buffer[index++] = (byte)Bag.charAt(i);
						}
						File outFileName = new File("Amphipolis_saves\\"+fName);
						FileOutputStream outStream = new FileOutputStream(outFileName);
						outStream.write(buffer,0,index-1);
						outStream.close();
					}
					catch(Exception e) {
						JOptionPane.showMessageDialog(null, e.toString(), "Exception FileNotFound", JOptionPane.ERROR_MESSAGE);
					}
					System.exit(0);
				}
			}
		});
	}
}