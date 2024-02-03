package project.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

import project.model.Board;
import project.model.Player;
import project.model.Tile;
import project.view.GameView;
import project.view.MyJButton;
import project.view.StartingMenu;

/**
 * 
 * @author csd3755 K.D
 *
 */
public class MyController {
	public static GameView view;
	public static Board board = new Board();
	boolean isFinished;
	public static int landslideCounter = 15;
	public static char charChosen = 4;
	public static char thisRoundArea;
	public static boolean[] profConstr = new boolean[4];
	
	/**
	 * @precondition numOfPlayers is either 1 or 4
	 * @postcondition Game starts
	 * @param numOfPlayers , names
	 */
	static void initializeGame(int numOfPlayers,ArrayList<String> names)  {
		if(numOfPlayers==1)
			landslideCounter=7;
		board.initBag(numOfPlayers, names); 		//initialization of Tiles,Players and player's characters all in the "bag"		
		view = new GameView(numOfPlayers,names);
		view.makeTileAreas();
	}
	
	/**
	 * 
	 * @param file
	 */
	static void loadGame(File file) {
		String fName = file.getName();
		FileInputStream iStream = null;
		ArrayList<String> names = new ArrayList<String>();
		try {
			iStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		byte[] buffer = new byte[(int)file.length()];
		try {
			iStream.read(buffer);
			iStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String str = new String(buffer);
		if(str.substring(0, 4).equals("Solo")) {//then it is a 4player mode
			String[] tempS = new String[3];
			tempS = str.split("[ \n]+");
			names.add(tempS[1]);
			board.loadSoloBag(str);
			view = new GameView(1,names);
		}
		else {
			String[] tempS = new String[4];
			tempS = str.split("[ \n]+");
			for(int i=0;i<4;i++) 
				names.add(tempS[i]);
			board.loadFourBag(str);
			view = new GameView(4,names);
		}
		view.makeTileAreas();
		if(!board.playerList[board.index%4].isPlaying()) {
			view.buttonHash.get("drawTiles").setEnabled(false);
			disableAllTileButtons();
		}
		else {
			enableTileButtons();
		}
		for(Map.Entry<String,Integer> m : board.activeTiles.entrySet()) {
			view.buttonHash.get(m.getKey()).setText(m.getValue()+"x");
		}
		view.refreshPlayerArea(board.playerList[board.index%4]);
	}
	/**
	 * @brief finds next player and sets the previous as not playing
	 * @return the next player
	 */
	static void nextPlayer() {
		board.playerList[board.index%4].setAreaVisited(4);
		if(board.playerList.length == 1) {
			board.playerList[0].setIsPlaying(true);
			return;	
		}
		board.index++;
		board.playerList[board.index%4].setIsPlaying(true);
	}
	
	/**
	 * 
	 */
	public static void endTurn() {
		if(charChosen != 4) {
			enableChars();
			charChosen = 4;
		}
		view.buttonHash.get("drawTiles").setEnabled(true);
		if(board.playerList.length == 4) {
			enableTileButtons();
			view.rotateView(board);
		}
		else {//solo game rules
			for(Map.Entry<String, Integer> brdTiles : board.activeTiles.entrySet()) {
				board.thief.playerArea.put(brdTiles.getKey(), board.thief.playerArea.get(brdTiles.getKey())+brdTiles.getValue());
				board.activeTiles.put(brdTiles.getKey(), 0);
				view.buttonHash.get(brdTiles.getKey()).setText("0x");
				view.buttonHash.get(brdTiles.getKey()).setEnabled(false);
			}
			
		}
		nextPlayer();
		if(view.buttonHash.get("professor").isEnabled()) {
			for(int i=0;i<4;++i)
				profConstr[i]=false;
		}
	}
	
	/**
	 * @postcondition updates the board with (max)4 new Buttons on it
	 * @param bag
	 */
	public static void drawTilesAndUpdate(ArrayList<Tile> bag) {
		Tile[] tilesDrawn = new Tile[4];
		view.buttonHash.get("drawTiles").setEnabled(false);
		for(int i =0; i<4; ++i) {
			if(!bag.isEmpty()) {
				tilesDrawn[i] = bag.remove(0);
			}
		}
		for(int i=0;i<tilesDrawn.length;i++) {
			if(!tilesDrawn[i].getId().equals("landslides")) {
				int temp = board.activeTiles.get(tilesDrawn[i].getId());
				board.activeTiles.put(tilesDrawn[i].getId(),++temp);
				view.refreshBoard(tilesDrawn[i].getId(),temp,0);
			}
			else
				landslideCounter = view.refreshBoard(tilesDrawn[i].getId(),0,landslideCounter);
			if(landslideCounter == -1) {
				Player p;
				if(board.playerList.length == 4) 
				  p = findWinner(board);
				else
					p = findSoloWinner(board.playerList[0]);
				String winnerName = p.getName();
				int pts = p.getPoints();
				JOptionPane.showMessageDialog(null, "Winner : " + winnerName + "!!!\nPoints : " + pts +"!\n"
						+ "      ---___(*_*)---___ ___---(*_*)___--- ---___(*_*)---___ ___---(*_*)___---", "CONGRATULATIONS", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	/**
	 * @brief subroutine that refreshes a button when it is collected.</br>Also updates the player's "tile" collection and can set button disabled
	 * @param tile is a String that refers to the button clicked
	 */
	public static void refreshCollected(String tile) {
		int temp = board.activeTiles.get(tile) - 1;
		board.activeTiles.put(tile, temp);
		view.buttonHash.get(tile).setText(temp+"x");
		if(temp == 0){
			view.buttonHash.get(tile).setEnabled(false);
		}
		board.playerList[board.index%4].addTile(tile);
		view.refreshPlayerArea(board.playerList[board.index%4]);
	}
	/**
	 * @param tile is a String who refers to the tile (GUI button) drawn by the player
	 */
	public static void playerMove(String tile,char charChosen,Border B) {
		if(charChosen != 4) {
			if(charChosen == 3) {//assistant
				refreshCollected(tile);
				board.playerList[board.index%4].playerCards[3].setEnable(false);
				view.buttonHash.get("assistant").setBorder(B);
				view.buttonHash.get("assistant").setEnabled(false);
				disableAllTileButtons();
				MyController.charChosen = 4;
				return;
			}
			else if(charChosen == 2) {//archaeologist
				refreshCollected(tile);
				if(board.playerList[board.index%4].playerCards[2].hasPlayed() == 0) {
					view.buttonHash.get("archaeologist").setBorder(B);
					board.playerList[board.index%4].playerCards[2].setEnable(false);
					thisRoundArea = (char)board.playerList[MyController.board.index%4].getAreaVisited();
				}
				board.playerList[board.index%4].playerCards[2].setHasPlayed();
				if(board.playerList[board.index%4].playerCards[2].hasPlayed() == 2 || isNonSortingEmpty((char)board.playerList[board.index%4].getAreaVisited())) {
					MyController.charChosen = 4;
					board.playerList[board.index%4].setAreaVisited(thisRoundArea);
					disableNonSortingButtons((char)board.playerList[MyController.board.index%4].getAreaVisited());
					enableChars();
				}
				else
					disableChars();
				return;
			}
			else if(charChosen == 1) {//digger
				refreshCollected(tile);
				if(board.playerList[board.index%4].playerCards[1].hasPlayed() == 0) {
					view.buttonHash.get("digger").setBorder(B);
					board.playerList[board.index%4].playerCards[1].setEnable(false);
				}
				board.playerList[board.index%4].playerCards[1].setHasPlayed();
				if(board.playerList[board.index%4].playerCards[1].hasPlayed() == 2 || isSortingEmpty((char)board.playerList[board.index%4].getAreaVisited())) {
					MyController.charChosen = 4;
					disableSortingButtons((char)board.playerList[MyController.board.index%4].getAreaVisited());
					enableChars();
				}
				else
					disableChars();
				return;
			}
			else {//professor
				refreshCollected(tile);
				if(board.playerList[board.index%4].playerCards[0].hasPlayed() == 0) {
					thisRoundArea = (char)board.playerList[MyController.board.index%4].getAreaVisited();
					profConstr[thisRoundArea] = true;
					view.buttonHash.get("professor").setBorder(B);
					board.playerList[board.index%4].playerCards[0].setEnable(false);
				}
				board.playerList[board.index%4].playerCards[0].setHasPlayed();
				if(tile.substring(0, 6).equals("mosaic")) { 
					board.playerList[board.index%4].setAreaVisited(0);
					profConstr[0] = true;
				}
				else if(tile.substring(0, 6).equals("statue")) {
					board.playerList[board.index%4].setAreaVisited(1);
					profConstr[1] = true;
				}
				else if(tile.substring(0, 7).equals("amphora")) {
					board.playerList[board.index%4].setAreaVisited(2);
					profConstr[2] = true;
				}
				else if(tile.substring(0, 8).equals("skeleton")) {
					board.playerList[board.index%4].setAreaVisited(3);
					profConstr[3] = true;
				}
				disableSortingButtons((char)board.playerList[MyController.board.index%4].getAreaVisited());
				board.playerList[board.index%4].playerCards[0].setHasPlayed();
				if(board.playerList[board.index%4].playerCards[0].hasPlayed() == 3 || profIsDone(profConstr)) {
					MyController.charChosen = 4;
					disableAllTileButtons();
					board.playerList[board.index%4].setAreaVisited(thisRoundArea);
					enableChars();
				}
				else
					disableChars();
				return;
			}
		}
		if(board.playerList[board.index%4].getAreaVisited()==4) {
			refreshCollected(tile);//does all the refreshing
			if(tile.substring(0, 6).equals("mosaic")) { 
				board.playerList[board.index%4].setAreaVisited(0);
				if(isSortingEmpty((char)0)) {
					board.playerList[board.index%4].setIsPlaying(false);
					disableNonSortingButtons((char)0);
				}
			}
			else if(tile.substring(0, 6).equals("statue")) {
				board.playerList[board.index%4].setAreaVisited(1);
				if(isSortingEmpty((char)1)) {
					board.playerList[board.index%4].setIsPlaying(false);
					disableNonSortingButtons((char)1);
				}
			}
			else if(tile.substring(0, 7).equals("amphora")) {
				board.playerList[board.index%4].setAreaVisited(2);
				if(isSortingEmpty((char)2)) 
				{
					board.playerList[board.index%4].setIsPlaying(false);
					disableNonSortingButtons((char)2);
				}	
			}
			else if(tile.substring(0, 8).equals("skeleton")) {
				board.playerList[board.index%4].setAreaVisited(3);
				if(isSortingEmpty((char)3)) 
				{
					board.playerList[board.index%4].setIsPlaying(false);
					disableNonSortingButtons((char)3);
				}
			}

		}
		else {
			switch(board.playerList[board.index%4].getAreaVisited()) {
			case 0:
				if(!tile.substring(0, 6).equals("mosaic")) {
					JOptionPane.showMessageDialog(null, "You might only choose tiles from the same sorting area each round!\n"
							+ "          --__(*_*)__--  --__(*_*)__--  --__(*_*)__--", "InfoBox: " + "Game_Rule", JOptionPane.WARNING_MESSAGE);
					break;
				}
				else {
					board.playerList[board.index%4].setIsPlaying(false);
					refreshCollected(tile);
					disableAllTileButtons();
				}
				break;
			case 1:
				if(!tile.substring(0, 6).equals("statue")) {
					JOptionPane.showMessageDialog(null, "You might only choose tiles from the same sorting area each round!\n"
							+ "           --__(*_*)__--  --__(*_*)__--  --__(*_*)__--", "InfoBox: " + "Game_Rule", JOptionPane.WARNING_MESSAGE);
					break;
				}
				else {
					board.playerList[board.index%4].setIsPlaying(false);
					refreshCollected(tile);
					disableAllTileButtons();
				}
				break;
			case 2:
				if(!tile.substring(0, 7).equals("amphora")) {
					JOptionPane.showMessageDialog(null, "You might only choose tiles from the same sorting area each round!\n"
							+ "          --__(*_*)__--  --__(*_*)__--  --__(*_*)__--", "InfoBox: " + "Game_Rule", JOptionPane.WARNING_MESSAGE);
					break;
				}
				else {
					board.playerList[board.index%4].setIsPlaying(false);
					refreshCollected(tile);
					disableAllTileButtons();
				}
				break;
			case 3:
				if(!tile.substring(0, 8).equals("skeleton")) {
					JOptionPane.showMessageDialog(null, "You might only choose tiles from the same sorting area each round!\n"
							+ "          --__(*_*)__--  --__(*_*)__--  --__(*_*)__--", "InfoBox: " + "Game_Rule", JOptionPane.WARNING_MESSAGE);
					break;
				}
				else {
					board.playerList[board.index%4].setIsPlaying(false);
					refreshCollected(tile);
					disableAllTileButtons();
				}
				break;
			default:
			}
			
		}
	}
	
	/**
	 * @brief does what the name say
	 */
	public static void disableAllTileButtons() {
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("archaeologist") && !B.getKey().equals("digger") && !B.getKey().equals("professor") 
					&& !B.getKey().equals("drawTiles") && !B.getKey().equals("endTurn"))
				B.getValue().setEnabled(false);
		}
	}
	/**
	 * @brief enables those tile buttons who are not 0
	 */
	public static void enableTileButtons() {
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("archaeologist") && !B.getKey().equals("digger") && !B.getKey().equals("professor") 
					&& !B.getKey().equals("drawTiles") && !B.getKey().equals("endTurn")) {
				B.getValue().setEnabled(board.activeTiles.get(B.getKey()) != 0);
			}
		}
	}
	/**
	 * @brief enables those buttons that belong in the sorting area chosen
	 * @param sorting->sorting area
	 */
	public static void enableSortingButtons(char sorting) {
		String area = null;
		if(sorting ==0) {
			area = "mosaic";
		}
		else if(sorting == 1) {
			area = "statue";
		}
		else if(sorting == 2) {
			area = "amphora";
		}
		else {
			area = "skeleton";
		}
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("archaeologist") && !B.getKey().equals("digger") && !B.getKey().equals("professor") 
					&& !B.getKey().equals("drawTiles") && !B.getKey().equals("endTurn") && B.getKey().substring(0,area.length()).equals(area)) {
				B.getValue().setEnabled(board.activeTiles.get(B.getKey()) != 0);
			}
		}
	}
	
	/**
	 * @brief enables the buttons that belong to the sorting areas but not those that belong to the area the player had used
	 * @param sorting->sorting area
	 */
	public static void enableNonSortingButtons(char sorting) {
		String area = null;
		if(sorting ==0) {
			area = "mosaic";
		}
		else if(sorting == 1) {
			area = "statue";
		}
		else if(sorting == 2) {
			area = "amphora";
		}
		else {
			area = "skeleton";
		}
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("digger") && !B.getKey().equals("archaeologist") && !B.getKey().equals("professor") &&
					!B.getKey().equals("endTurn") && !B.getKey().equals("drawTiles") && !B.getKey().substring(0,area.length()).equals(area)) {
				B.getValue().setEnabled(board.activeTiles.get(B.getKey()) != 0);
			}
		}
	}
	
	/**
	 * @brief disables those buttons that belong in the sorting area chosen
	 * @param sorting->sorting area
	 */
	public static void disableSortingButtons(char sorting) {
		String area = null;
		if(sorting ==0) {
			area = "mosaic";
		}
		else if(sorting == 1) {
			area = "statue";
		}
		else if(sorting == 2) {
			area = "amphora";
		}
		else {
			area = "skeleton";
		}
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("archaeologist") && !B.getKey().equals("digger") && !B.getKey().equals("professor") 
					&& !B.getKey().equals("drawTiles") && !B.getKey().equals("endTurn") && B.getKey().substring(0,area.length()).equals(area)) {
				B.getValue().setEnabled(false);
			}
		}
	}
	
	/**
	 * @brief disables the buttons that belong to the sorting areas but not those that belong to the area the player had used
	 * @param sorting->sorting area
	 */
	public static void disableNonSortingButtons(char sorting) {
		String area = null;
		if(sorting ==0) {
			area = "mosaic";
		}
		else if(sorting == 1) {
			area = "statue";
		}
		else if(sorting == 2) {
			area = "amphora";
		}
		else {
			area = "skeleton";
		}
		for(Map.Entry<String, MyJButton> B : view.buttonHash.entrySet()) {
			if(!B.getKey().equals("assistant") && !B.getKey().equals("digger") && !B.getKey().equals("archaeologist") && !B.getKey().equals("professor") &&
					!B.getKey().equals("endTurn") && !B.getKey().equals("drawTiles") && !B.getKey().substring(0,area.length()).equals(area)) {
				B.getValue().setEnabled(false);
			}
		}
	}
	/**
	 * @brief does what the name say.It is used after playing a character
	 */
	public static void enableChars() {
		view.buttonHash.get("professor").setEnabled(board.playerList[board.index%4].playerCards[0].isEnabled());
		view.buttonHash.get("assistant").setEnabled(board.playerList[board.index%4].playerCards[3].isEnabled());
		view.buttonHash.get("digger").setEnabled(board.playerList[board.index%4].playerCards[1].isEnabled());
		view.buttonHash.get("archaeologist").setEnabled(board.playerList[board.index%4].playerCards[2].isEnabled());
	}
	/**
	 * @brief does what the name say.It is used when playing a character
	 */
	public static void disableChars() {
		view.buttonHash.get("professor").setEnabled(false);
		view.buttonHash.get("assistant").setEnabled(false);
		view.buttonHash.get("digger").setEnabled(false);
		view.buttonHash.get("archaeologist").setEnabled(false);
	}
	
	public static boolean isSortingEmpty(char sorting) {
		if(sorting ==0) {
			return board.activeTiles.get("mosaic_green") == 0 && board.activeTiles.get("mosaic_red") == 0 && board.activeTiles.get("mosaic_yellow") == 0;
		}
		else if(sorting == 1) {
			return board.activeTiles.get("statue_caryatid") == 0 && board.activeTiles.get("statue_sphinx") == 0;
		}
		else if(sorting == 2) {
			return board.activeTiles.get("amphora_green") == 0 && board.activeTiles.get("amphora_red") == 0 &&
					board.activeTiles.get("amphora_yellow") == 0 
					&& 
					board.activeTiles.get("amphora_brown") == 0 && board.activeTiles.get("amphora_purple") == 0 &&
					board.activeTiles.get("amphora_blue") == 0;
		}
		else {
			return board.activeTiles.get("skeleton_big_top") == 0 && board.activeTiles.get("skeleton_big_bottom") == 0
					&& board.activeTiles.get("skeleton_small_top") == 0 && board.activeTiles.get("skeleton_small_bottom") == 0;
		}		
	}
	
	public static boolean isNonSortingEmpty(char sorting) {
		if(sorting ==0) {
			return board.activeTiles.get("statue_caryatid") == 0 && board.activeTiles.get("statue_sphinx") == 0 &&
					board.activeTiles.get("skeleton_big_top") == 0 && board.activeTiles.get("skeleton_big_bottom") == 0
					&& board.activeTiles.get("skeleton_small_top") == 0 && board.activeTiles.get("skeleton_small_bottom") == 0
					&& board.activeTiles.get("amphora_green") == 0 && board.activeTiles.get("amphora_red") == 0 &&
					board.activeTiles.get("amphora_yellow") == 0 
							&& 
							board.activeTiles.get("amphora_brown") == 0 && board.activeTiles.get("amphora_purple") == 0 &&
							board.activeTiles.get("amphora_blue") == 0;
		}
		else if(sorting == 1) {
			return board.activeTiles.get("mosaic_green") == 0 && board.activeTiles.get("mosaic_red") == 0 && board.activeTiles.get("mosaic_yellow") == 0
					&& board.activeTiles.get("skeleton_big_top") == 0 && board.activeTiles.get("skeleton_big_bottom") == 0
							&& board.activeTiles.get("skeleton_small_top") == 0 && board.activeTiles.get("skeleton_small_bottom") == 0
							&& board.activeTiles.get("amphora_green") == 0 && board.activeTiles.get("amphora_red") == 0 &&
							board.activeTiles.get("amphora_yellow") == 0 
									&& 
									board.activeTiles.get("amphora_brown") == 0 && board.activeTiles.get("amphora_purple") == 0 &&
									board.activeTiles.get("amphora_blue") == 0;
		}
		else if(sorting == 2) {
			return board.activeTiles.get("mosaic_green") == 0 && board.activeTiles.get("mosaic_red") == 0 && board.activeTiles.get("mosaic_yellow") == 0 &&
					board.activeTiles.get("statue_caryatid") == 0 && board.activeTiles.get("statue_sphinx") == 0 &&
					board.activeTiles.get("skeleton_big_top") == 0 && board.activeTiles.get("skeleton_big_bottom") == 0
					&& board.activeTiles.get("skeleton_small_top") == 0 && board.activeTiles.get("skeleton_small_bottom") == 0;
		}
		else {
			return board.activeTiles.get("mosaic_green") == 0 && board.activeTiles.get("mosaic_red") == 0 && board.activeTiles.get("mosaic_yellow") == 0 &&
					board.activeTiles.get("statue_caryatid") == 0 && board.activeTiles.get("statue_sphinx") == 0 &&
					board.activeTiles.get("amphora_green") == 0 && board.activeTiles.get("amphora_red") == 0 &&
					board.activeTiles.get("amphora_yellow") == 0 
							&& 
							board.activeTiles.get("amphora_brown") == 0 && board.activeTiles.get("amphora_purple") == 0 &&
							board.activeTiles.get("amphora_blue") == 0;
		}
	}
	
	
	/**
	 * @brief method for professor card only
	 */
	public static boolean profIsDone(boolean[] bool) {
		char ind1=4,ind2=4;
		for(char i=0;i<4;i++) {
			if(!bool[i] && ind1==4) {
				ind1 = i;
			}
			else if(!bool[i])
				ind2=i;
		}
		if(ind2!=4)
			return isSortingEmpty(ind1) && isSortingEmpty(ind2);
		else
			return isSortingEmpty(ind1);
	}
	
	/**
	 * 
	 * @param P player
	 * @brief counts what can be counted (statues must be compared to find points)
	 */
	public static void countPoints(Player P) {
		boolean[] amphoras = new boolean [6];//by default boolean is 0->false
		int restMosaics = 0;
		int difAmphoras = 0;
		int completeBig = P.playerArea.get("skeleton_big_top") > P.playerArea.get("skeleton_big_bottom")?
				P.playerArea.get("skeleton_big_bottom"):
					P.playerArea.get("skeleton_big_top");
		int completeSmall = P.playerArea.get("skeleton_small_top") > P.playerArea.get("skeleton_small_bottom")?
				P.playerArea.get("skeleton_small_bottom"):
					P.playerArea.get("skeleton_small_top");
		 
		while(completeSmall>0 && completeBig>1) {//count families
			completeBig-=2;
			completeSmall--;
			P.addPoints(6);
		}
		while(completeSmall>0) {
			completeSmall--;
			P.addPoints(1);
		}
		while(completeBig>0) {
			completeBig--;
			P.addPoints(1);
		}
		for(Map.Entry<String, Integer> tile: P.playerArea.entrySet()) {
			if(tile.getKey().equals("mosaic_green")) {
				int count = tile.getValue()/4;
				P.addPoints(count*4);
				restMosaics += tile.getValue()-4*count;
				
			}
			else if(tile.getKey().equals("mosaic_red")) {
				int count = tile.getValue()/4;
				P.addPoints(count*4);
				restMosaics += tile.getValue()-4*count;
				
			}
			else if(tile.getKey().equals("mosaic_yellow")) {
				int count = tile.getValue()/4;
				P.addPoints(count*4);
				restMosaics += tile.getValue()-4*count;
				
			}
			else if(tile.getKey().equals("amphora_green")) {
				if(tile.getValue()!=0)
				amphoras[0] = true;
				
			}
			else if(tile.getKey().equals("amphora_blue")) {
				if(tile.getValue()!=0)
					amphoras[1] = true;
				
			}
			else if(tile.getKey().equals("amphora_red")) {
				if(tile.getValue()!=0)
					amphoras[2] = true;
				
			}
			else if(tile.getKey().equals("amphora_yellow")) {
				if(tile.getValue()!=0)
					amphoras[3] = true;
				
			}
			else if(tile.getKey().equals("amphora_brown")) {
				if(tile.getValue()!=0)
					amphoras[4] = true;
				
			}
			else if(tile.getKey().equals("amphora_purple")) {
				if(tile.getValue()!=0)
					amphoras[5] = true;
				
			}
		}
		P.addPoints((restMosaics/4)*2);
		for(int i=0;i<6;i++) {
			if(amphoras[i])
				difAmphoras++;
		}
		switch(difAmphoras) {
		case 6:
			P.addPoints(6);
			break;
		case 5:
			P.addPoints(4);
			break;
		case 4:
			P.addPoints(2);
			break;
		case 3:
			P.addPoints(1);
			break;
			default:
		}
	}
	
	/**
	 * @brief this is for 4 player game only
	 * @param B board
	 * @return the player with the most points
	 */
	public static Player findWinner(Board B) {
		for(int i=0;i<4;i++)
			countPoints(B.playerList[i]);		
		int winIndex = 0;
		int maxC =-1;
		int maxS =-1;
		int i_maxC = 0;
		int i_maxS = 0;
		int leastC = 13;
		int leastS = 13;
		int i_leastC = 0;
		int i_leastS = 0;
		for(int i=3;i > -1;--i) {
			int sph = B.playerList[i].playerArea.get("statue_sphinx");
			int car = B.playerList[i].playerArea.get("statue_caryatid");
			if(sph < leastS) {
				i_leastS = i;
				leastS = sph;
			}
			if(car < leastC) {
				i_leastC = i;
				leastC = car;
			}
			if(sph > maxS) {
				i_maxS = i;
				maxS = sph;
			}
			if(car > maxC) {
				i_maxC = i;
				maxC = car;
			}
		}
		for(int i=0;i<4;i++) {
			if(i==i_maxC)
				B.playerList[i].addPoints(6);
			else if(i==i_leastC)
				;
			else
				B.playerList[i].addPoints(3);
			if(i==i_maxS)
				B.playerList[i].addPoints(6);
			else if(i==i_leastS)
				;
			else
				B.playerList[i].addPoints(3);
		}
		maxC = 0;
		for(int i=3;i>-1;i--) {
			int pts = B.playerList[i].getPoints();
			if(pts > maxC) {
				winIndex = i;
				maxC = pts;
			}
		}
		return B.playerList[winIndex];
	}
	
	public static Player findSoloWinner(Player P) {
		countPoints(P);
		countPoints(board.thief);
		if(P.playerArea.get("statue_caryatid") > board.thief.playerArea.get("statue_caryatid")) {
			P.addPoints(6);
			board.thief.addPoints(3);
		}
		else {
			board.thief.addPoints(6);
			P.addPoints(3);
		}
		if(P.playerArea.get("statue_sphinx") > board.thief.playerArea.get("statue_sphinx")) {
			P.addPoints(6);
			board.thief.addPoints(3);
		}
		else {
			board.thief.addPoints(6);
			P.addPoints(3);
		}
		if(P.getPoints()>board.thief.getPoints())
			return P;
		else
			return board.thief;
	}
	
	public static void main(String[] a) {
		new StartingMenu();
		while(StartingMenu.tmp == 0) {
			try {
				Thread.sleep(600);
			}
			catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		if(StartingMenu.tmp == 5) {
			loadGame(StartingMenu.file);
		}
		else{
			ArrayList<String> names = new ArrayList<String>(StartingMenu.tmp);
			for(int i =0; i<StartingMenu.tmp;++i) {
				names.add(JOptionPane.showInputDialog("Enter your Name", "Player"+(i+1)));
			}
			Collections.shuffle(names);
			if(StartingMenu.tmp==1)
				initializeGame(1,names);
			else if(StartingMenu.tmp == 4)
				initializeGame(4,names);
		}
	}
}