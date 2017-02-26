package agentMap.Core;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

import agentMap.Heuristics.ChebyshevDist;
import agentMap.Heuristics.ManhattanDist;
import agentMap.Modules.aStarSearch;

/**
 * @author Richard Luong
 * Used by an agent to keep an internal map of a given environment. Currently, the map
 * only supports a fully observable environment.
 */
public class InternalMap {
	private Tile map[][];
	// Note: Tiles in this hashtable can be referred to as "Tile blueprints"
	private HashMap<Character, Tile> tileTypes; 
	private HashMap<Character, ArrayList<Tile>> tileCollection;
	private boolean fullyObservable;
	private ISearch searchAlgo;
	
	/**
	 * Base constructor which assumes the map is fully observable
	 * @param inputFile file to read the map from
	 * @param tileTypes different tiles that are on the map 
	 * @param player the tile representing the player (or agent)
	 * @param mapHeight height of the map
	 * @param mapWidth width of the map
	 * @precondition the map has to have a size of n x m where n, m > 1, additionally
	 * all rows and columns of the input file have to be filled with some character
	 * @postcondition an internal map of the input file will be generated and stored 
	 */
	public InternalMap(String inputFile, HashMap<Character, Tile> tileTypes, Tile player, 
			Dimension mapDim, boolean eightDirections) {
		this.tileTypes = tileTypes;
		this.fullyObservable = true;
		map = new Tile[mapDim.width][mapDim.height];
		IHeuristic heuristic = (eightDirections == true) ? new ChebyshevDist() : new ManhattanDist();
		// tileCollection stores the location of all the known tiles
		tileCollection = new HashMap<Character, ArrayList<Tile>>();
		for (char curr: tileTypes.keySet()) {
			tileCollection.put(curr, new ArrayList<Tile>());
		}
		// By default an A* search algorithm is used for searching
		searchAlgo = new aStarSearch(heuristic, eightDirections);
		// By default the heuristic used for A* is the manhatten distance
		// Read the file and store each tile in the 2d array 
		Scanner sc = null;
		try {
			sc = new Scanner(new File(inputFile));
			int currY = 0;
			while (sc.hasNextLine()) {
				String mapRow = sc.nextLine();
				char[] splitMapRow = mapRow.toCharArray();
				/* Use the tiles from tileTypes to determine which tile the current
				character being looked at it corresponds to*/
				for (int currX = 0; currX < splitMapRow.length; currX++) {
					Tile tempTile = tileTypes.get(splitMapRow[currX]);
					// Create a deep clone of the tile blueprint with the new position
					Tile newTile = tempTile.clone(new Point(currX, currY));
					map[currY][currX] = newTile;
					tileCollection.get(splitMapRow[currX]).add(newTile);
				}
				currY++;
			}
		// Account for the case where the file is specified but does not exist in directory
		} catch (FileNotFoundException e) {
			System.out.printf("File: %s not found", inputFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.printf("I/O Error when attempting to read file: %s\n", inputFile);
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}
	
	/**
	 * Puts a new tile blueprint into the tileTypes hashtable
	 * @param newTileType the tile type to put in the tileTypes hashtable
	 */
	public void addTileType(Tile newTileType) {
		tileTypes.put(newTileType.getTileChar(), newTileType);
	}
	
	/**
	 * Remove a tile from tileTypes using its character key
	 * @param tileChar the key of the tile to remove in tileTypes
	 */
	public void removeTileType(char tileChar) {
		tileTypes.remove(tileChar);
	}
	
	/**
	 * Displays the map as a 2d grid with the different characters to represent
	 * each different tile
	 */
	public void displayMap() {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				System.out.print(map[y][x].getTileChar());
			}
			System.out.println();
		}
	}
	
	/**
	 * Displays the count of each tile that is a key in the tileTypes hashtable
	 * known on the map
	 */
	public void displayTileCount() {
		System.out.println("Count of all known tiles:");
		for (char curr: tileTypes.keySet()) {
			System.out.printf("%c: %d\n", curr, tileCollection.get(curr).size());
		}
	}
	
	public void updateMap(Tile tile, Point point) {
		map[point.y][point.x] = tile;
	}
	
	/**
	 * Gets a path from one point to another point using a search algorithm 
	 * @param startLoc Starting point
	 * @param destLoc Destination point
	 * @return Queue of points representing the path between the two points
	 */
	public Queue<Pair<Direction, Point>> getPath(Point startLoc, Point destLoc) {
		Queue<Pair<Direction, Point>> path = searchAlgo.getPath(startLoc, destLoc, map);
		return path;
	}
	
	public ArrayList<Point> getLocOfTile(char tileChar) {
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Tile> tileList = tileCollection.get(tileChar);
		Iterator<Tile> tileIt = tileList.iterator();
		while (tileIt.hasNext()) {
			Tile currTile = tileIt.next();
			Point currPos = currTile.getTilePos();
			points.add(new Point (currPos.x, currPos.y));
		}
		return points;
	}
	
	public static void main(String args[]) {
		Scanner sc = null;
		String inputFile = "maps/map1.txt";
		/* Attempt to read the contents of the map and determine the
		height and width of the map to initialise a 2d array in the Internal Map*/
		int mapWidth = -1;
		int mapHeight = 0;
		try {
			sc = new Scanner(new File(inputFile));
			while (sc.hasNextLine()) {
				String nextLine = sc.nextLine();
				if (mapWidth == -1) {
					mapWidth = nextLine.length();
				}
				mapHeight += 1;
			}
			// Exit the program if map with invalid dimensions is given
			if (mapHeight < 1 || mapWidth < 1) {
				return;
			}
		// Account for the case where the file is specified but does not exist in directory
		} catch (FileNotFoundException e) {
			System.out.printf("File: %s not found\n", inputFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.printf("I/O Error when attempting to read file: %s\n", inputFile);
			e.printStackTrace();
		// Always remember to close the file
		} finally {
			sc.close();
		}
		System.out.printf("(Debug) Map: %s read successfully\n", inputFile);
		// TODO: Allow for a configuration file to load in the tile types on the map
		// Hardcode all the tile types for now
		Tile wall1 = new Tile('-', null, false, -99);
		Tile wall2 = new Tile('|', null, false, -99);
		Tile wall3 = new Tile('+', null, false, -99);
		Tile empty = new Tile(' ', null, true, 1);
		Tile gold = new Tile('g', null, true, 1);
		Tile player = new Tile('o', null, true, 1);
		HashMap<Character, Tile> tileTypes = new HashMap<Character, Tile>();
		tileTypes.put('-', wall1);
		tileTypes.put('|', wall2);
		tileTypes.put('+', wall3);
		tileTypes.put('g', gold);
		tileTypes.put('o', player);
		tileTypes.put(' ', empty);
		Dimension mapDim = new Dimension(mapHeight, mapWidth);
		boolean eightDirections = false;
		// Create an internal map using dimensions and blueprint tiles  
		InternalMap iMap = new InternalMap(inputFile, tileTypes, player, mapDim, eightDirections);
		// TODO: Make JUnit tests
		System.out.printf("(Debug) Internal map created successfully\n");
		iMap.displayMap();
		System.out.printf("(Debug) Internal map displayed successfully\n");
		iMap.displayTileCount();
		System.out.printf("(Debug) Tile count displayed successfully\n");
		ArrayList<Point> playerLoc = iMap.getLocOfTile('o');
		ArrayList<Point> goldLoc = iMap.getLocOfTile('g');
		Queue<Pair<Direction, Point>> path = iMap.getPath(playerLoc.get(0), goldLoc.get(0));
		Tile marker = new Tile('X', null, false, -99);
		while (path.size() > 0) {
			Pair<Direction, Point> nextPair = path.poll();
			Direction nextDirection = nextPair.first;
			Point nextPoint = nextPair.second;
			iMap.updateMap(marker, nextPoint);
			System.out.println("Direction: " + nextDirection.toString() + ", " + nextPoint.toString());
		}
		iMap.displayMap();
		System.out.printf("(Debug) Path from the player to the gold displayed sucessfully\n");
	}
}
