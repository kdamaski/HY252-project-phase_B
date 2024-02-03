package project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import project.controller.MyController;

/**
 * 
 * @author csd3755 K.D
 * @category Model
 *
 */
public class Board{
	public Player[] playerList;
	public Map<String,Integer> activeTiles = new HashMap<String,Integer>();
	public ArrayList<Tile> bag = new ArrayList<Tile>();
	public int index = 0; //indexes the playerList to determine who is playing
	public Player thief;
	
	/**
	 * 
	 * @param numOfPlayers
	 * @param names
	 */
	public void initChars(int numOfPlayers, ArrayList<String> names) {
		playerList = new Player[numOfPlayers];
		for(int i =0; i< numOfPlayers;++i) {
			playerList[i] = new Player(names.get(i));
			playerList[i].playerCards[3] = new Assistant("assistant");
			playerList[i].playerCards[2] = new Archaeologist("archaeologist");
			playerList[i].playerCards[1] = new Digger("digger");
			playerList[i].playerCards[0] = new Professor("professor");
			playerList[i].playerArea.put("mosaic_green", 0);
			playerList[i].playerArea.put("mosaic_red", 0);
			playerList[i].playerArea.put("mosaic_yellow", 0);
			playerList[i].playerArea.put("amphora_green", 0);
			playerList[i].playerArea.put("amphora_blue", 0);
			playerList[i].playerArea.put("amphora_yellow", 0);
			playerList[i].playerArea.put("amphora_red", 0);
			playerList[i].playerArea.put("amphora_brown", 0);
			playerList[i].playerArea.put("amphora_purple", 0);
			playerList[i].playerArea.put("statue_caryatid", 0);
			playerList[i].playerArea.put("statue_sphinx", 0);
			playerList[i].playerArea.put("skeleton_big_top", 0);
			playerList[i].playerArea.put("skeleton_big_bottom", 0);
			playerList[i].playerArea.put("skeleton_small_top", 0);
			playerList[i].playerArea.put("skeleton_small_bottom", 0);
			playerList[i].setAreaVisited(4);//just an invalid index
		}
		playerList[0].setIsPlaying(true);
	}
	
	public void initTiles(int numOfPlayers) {
			activeTiles.put("mosaic_green", 0);/*init active tiles(0)*/
			activeTiles.put("mosaic_red", 0);
			activeTiles.put("mosaic_yellow", 0);
			activeTiles.put("amphora_green", 0);
			activeTiles.put("amphora_blue", 0);
			activeTiles.put("amphora_yellow", 0);
			activeTiles.put("amphora_red", 0);
			activeTiles.put("amphora_brown", 0);
			activeTiles.put("amphora_purple", 0);
			activeTiles.put("statue_caryatid", 0);
			activeTiles.put("statue_sphinx", 0);
			activeTiles.put("skeleton_big_top", 0);
			activeTiles.put("skeleton_big_bottom", 0);
			activeTiles.put("skeleton_small_top", 0);
			activeTiles.put("skeleton_small_bottom", 0);

		for(int i = 0;i < 3; i++) {// mosaics
			for(int j = 0;j <9; j++) {
				if(i==0)
					bag.add(new mosaic("mosaic_green"));
				else if(i==1)
					bag.add(new mosaic("mosaic_red"));
				else
					bag.add(new mosaic("mosaic_yellow"));
			}
		}
		for(int i =0; i<15 ; ++i) {//skeletons
				if(i<10) {
					bag.add(new skeleton("skeleton_big_top"));
					bag.add(new skeleton("skeleton_big_bottom"));
				}
				else {
					bag.add(new skeleton("skeleton_small_top"));
					bag.add(new skeleton("skeleton_small_bottom"));
				}		
		}
		for(int i =0; i<6;i++) {// amphoras
			for(int j=0; j<5; j++) {
				if(i==0)
					bag.add(new amphora("amphora_blue"));
				else if(i == 1)
					bag.add(new amphora("amphora_brown"));
				else if(i == 2)
					bag.add(new amphora("amphora_green"));
				else if(i == 3)
					bag.add(new amphora("amphora_purple"));
				else if(i == 4)
					bag.add(new amphora("amphora_red"));
				else
					bag.add(new amphora("amphora_yellow"));
			}
		}
		if(numOfPlayers == 4) {//init 24 landslides
			for(int i =0; i<24 ; i++) {//statues and landslides
					bag.add(new landSlide("landslides"));
				if(i<12)
					bag.add(new caryatid("statue_caryatid"));
				else
					bag.add(new sphinx("statue_sphinx"));
			}
		}
		else { // solo game : init 20 landslides but 8 of them must be put on board before game start 		
			thief = new Player("Thief");
			thief.playerArea.put("mosaic_green", 0);
			thief.playerArea.put("mosaic_red", 0);
			thief.playerArea.put("mosaic_yellow", 0);
			thief.playerArea.put("amphora_green", 0);
			thief.playerArea.put("amphora_blue", 0);
			thief.playerArea.put("amphora_yellow", 0);
			thief.playerArea.put("amphora_red", 0);
			thief.playerArea.put("amphora_brown", 0);
			thief.playerArea.put("amphora_purple", 0);
			thief.playerArea.put("statue_caryatid", 0);
			thief.playerArea.put("statue_sphinx", 0);
			thief.playerArea.put("skeleton_big_top", 0);
			thief.playerArea.put("skeleton_big_bottom", 0);
			thief.playerArea.put("skeleton_small_top", 0);
			thief.playerArea.put("skeleton_small_bottom", 0);
			for(int i =0; i<12 ; i++) {//statues and landslides
				bag.add(new landSlide("landslides"));
				bag.add(new caryatid("statue_caryatid"));
				bag.add(new sphinx("statue_sphinx"));	
			}
		}
		Collections.shuffle(bag);
	}
	
	public void initBag(int playerNum,ArrayList<String> names) {
		initChars(playerNum, names);
		initTiles(playerNum);
	}
	
	/**
	 * @brief initialization of bag according to a previous progress.Solo
	 */
	public void loadSoloBag(String str) {
		playerList = new Player[1];
		thief = new Player("Thief");
		Scanner scan = new Scanner(str);
		String[] ss = new String[2];
		scan.nextLine();//Getting rid of "Solo"
		ss = scan.nextLine().split("[ ]+");
		playerList[0] = new Player(ss[0]);
		if(ss[1].equals("true"))
			playerList[0].setIsPlaying(true);
		else
			playerList[0].setIsPlaying(false);
		playerList[0].playerCards[0] = new Professor("professor");
		playerList[0].playerCards[1] = new Digger("digger");
		playerList[0].playerCards[2] = new Archaeologist("archaeologist");
		playerList[0].playerCards[3] = new Assistant("assistant");
		for(int i=0;i<4;i++) {
			ss = scan.nextLine().split("[ ]+");
			if(ss[1].equals("true"))
				playerList[0].playerCards[i].setEnable(true);
			else
				playerList[0].playerCards[i].setEnable(false);
		}
		while(true) {//playerArea
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			playerList[0].playerArea.put(ss[0],Integer.parseInt(ss[1]));
		}
		while(true) {//thiefArea
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			thief.playerArea.put(ss[0],Integer.parseInt(ss[1]));
		}
		ss = scan.nextLine().split("[ ]+");//"landslides"
		MyController.landslideCounter = Integer.parseInt(ss[1]);
		while(true) {//activeTiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1) 
				break;
			activeTiles.put(ss[0],Integer.parseInt(ss[1]));
		}
		while(scan.hasNext()) {//bag
			String s = scan.nextLine();
			if(s.equals("landslides"))
				bag.add(new landSlide(s));
			else if(s.equals("statue_sphinx"))
				bag.add(new sphinx(s));
			else if(s.equals("statue_caryatid"))
				bag.add(new caryatid(s));
			else if(s.substring(0,7).equals("amphora"))
				bag.add(new amphora(s));
			else if(s.substring(0,6).equals("mosaic"))
				bag.add(new mosaic(s));
			else
				bag.add(new skeleton(s));
		}
	}
	/**
	 * @brief loads a 4player session
	 * @param str
	 */
	public void loadFourBag(String str){
		playerList = new Player[4];
		Scanner scan = new Scanner(str);
		String[] ss = new String[4];
		ss = scan.nextLine().split("[ ]+");
		for(int i=0;i<4;i++) {
			playerList[i] = new Player(ss[i]);
			playerList[i].playerCards[3] = new Assistant("assistant");
			playerList[i].playerCards[2] = new Archaeologist("archaeologist");
			playerList[i].playerCards[1] = new Digger("digger");
			playerList[i].playerCards[0] = new Professor("professor");
		}
		ss = new String[2];
		ss = scan.nextLine().split("[ ]+");
		if(ss[1].equals("true"))
			playerList[0].setIsPlaying(true);
		else
			playerList[0].setIsPlaying(false);
		for(int i=0;i<4;i++) {//1st player's cards
			ss = scan.nextLine().split("[ ]+");
			if(ss[1].equals("true"))
				playerList[0].playerCards[i].setEnable(true);
			else
				playerList[0].playerCards[i].setEnable(false);
		}
		while(true) {//1st player's tiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			playerList[0].playerArea.put(ss[0], Integer.parseInt(ss[1]));	
		}
		scan.nextLine();//getting rid of headers.......................
		
		for(int i=0;i<4;i++) {//2nd player's cards
			ss = scan.nextLine().split("[ ]+");
			if(ss[1].equals("true"))
				playerList[1].playerCards[i].setEnable(true);
			else
				playerList[1].playerCards[i].setEnable(false);
		}
		while(true) {//2nd player's tiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			playerList[1].playerArea.put(ss[0], Integer.parseInt(ss[1]));	
		}
		scan.nextLine();//getting rid of headers.......................
		
		for(int i=0;i<4;i++) {//3rd player's cards
			ss = scan.nextLine().split("[ ]+");
			if(ss[1].equals("true"))
				playerList[2].playerCards[i].setEnable(true);
			else
				playerList[2].playerCards[i].setEnable(false);
		}
		while(true) {//3rd player's tiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			playerList[2].playerArea.put(ss[0], Integer.parseInt(ss[1]));	
		}
		scan.nextLine();//getting rid of headers.......................
		
		for(int i=0;i<4;i++) {//4th player's cards
			ss = scan.nextLine().split("[ ]+");
			if(ss[1].equals("true"))
				playerList[3].playerCards[i].setEnable(true);
			else
				playerList[3].playerCards[i].setEnable(false);
		}
		while(true) {//4th player's tiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			playerList[3].playerArea.put(ss[0], Integer.parseInt(ss[1]));
		}
			//init bag is left
		ss = scan.nextLine().split("[ ]+");
		index = Integer.parseInt(ss[1]);
		ss = scan.nextLine().split("[ ]+");
		MyController.landslideCounter = Integer.parseInt(ss[1]);
		ss = scan.nextLine().split("[ ]+");//getting rid of header "activeTiles"
		while(true) {//	active tiles
			ss = scan.nextLine().split("[ ]+");
			if(ss.length == 1)
				break;
			activeTiles.put(ss[0], Integer.parseInt(ss[1]));
		}
		
		while(scan.hasNext()) {// Bag
			String s = scan.nextLine();
			if(s.equals("landslides"))
				bag.add(new landSlide(s));
			else if(s.equals("statue_sphinx"))
				bag.add(new sphinx(s));
			else if(s.equals("statue_caryatid"))
				bag.add(new caryatid(s));
			else if(s.substring(0,7).equals("amphora"))
				bag.add(new amphora(s));
			else if(s.substring(0,6).equals("mosaic"))
				bag.add(new mosaic(s));
			else
				bag.add(new skeleton(s));
		}
	}
}
