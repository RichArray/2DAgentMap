package agentMap;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;

/**
 * 
 * @author Richard Luong
 * Interface for a search algorithm that will return a path from
 * the current point to the destination point
 */

public interface ISearch {
	/**
	 * Returns a path from a starting point to another point using a search algorithm
	 * @param currLoc Position to start from
	 * @param destLoc Position to end at
	 * @param map 2d array of tiles representing the map
	 * @return
	 */
	public LinkedList<Point> getPath(Point currLoc, Point destLoc, Tile[][] map);
}
