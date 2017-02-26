package agentMap.Core;

import java.awt.Point;

/**
 * 
 * @author Richard Luong
 * Returns a heuristic value for a state in an A* search
 */
public interface IHeuristic {
	public int calcHeuristic(Point currLoc, Point goalLoc);
}
