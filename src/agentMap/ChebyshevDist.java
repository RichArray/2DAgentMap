package agentMap;

import java.awt.Point;

/**
 * @author Richard Luong
 * The Chebyshev distance heuristic is used for 2d grids which allow
 * for diagonal movement (i.e movement in 8 directions(
 */

public class ChebyshevDist implements IHeuristic {

	@Override
	public int calcHeuristic(Point currLoc, Point goalLoc) {
		int xDist = Math.abs(currLoc.x - goalLoc.x);
		int yDist = Math.abs(currLoc.y - goalLoc.y);
		return (xDist > yDist) ? xDist : yDist;
	}

}
