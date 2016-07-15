package agentMap;
import java.awt.Point;

/**
 * 
 * @author Richard Luong
 * A Tile contains information about a given tile on the map
 */

public class Tile {
	private char tileChar;
	private Point pos;
	private boolean passable;
	private int passCost;
	
	/**
	 * Constructor for the Tile class
	 * @param tileChar character on the map representing the tile
	 * @param pos x and y position of the tile on the map
	 * @param passable if the tile can be bypassed by the agent or not
	 * @param passCost the g cost of passing the tile in an A* search
	 */
	public Tile(char tileChar, Point pos, boolean passable, int passCost) {
		this.tileChar = tileChar;
		this.pos = pos;
		this.passable = passable;
		this.passCost = passCost;
	}
	
	/**
	 * A special clone that uses a blueprint tile and gives it a position on the map
	 * @param newPos position that the tile has on the map
	 * @return A cloned blueprint tile that has a position on the InternalMap
	 */
	public Tile clone(Point newPos) {
		return new Tile(tileChar, newPos, passable, passCost);
	}
	
	/**
	 * Getter: gets the tile position
	 * @return tile position
	 */
	public Point getTilePos() {
		return pos;
	}
	
	/**
	 * Getter: gets the character representing the tile
	 * @return the character representing the tile
	 */
	public char getTileChar() {
		return tileChar;
	}
	
	/**
	 * Getter: gets a boolean on whether the tile is passable
	 * @return a boolean identifying whether the tile is passable
	 */
	public boolean getPassable() {
		return passable;
	}
	
	/**
	 * Getter: gets cost to pass the tile on the map
	 * @return the cost to pass the tile on the map (aka gCost in A* search)
	 */
	public int getPassCost() {
		return passCost;
	}
}
