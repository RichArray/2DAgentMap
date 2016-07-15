package agentMap;

import java.awt.Point;
import java.util.LinkedList;

/**
 * 
 * @author Richard Luong
 * Interface for a search algorithm that will return a path from
 * the current point to the destination point
 */

public interface ISearch {
	public LinkedList<Point> getPath(Point currLoc, Point destLoc);
}
