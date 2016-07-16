package agentMap;

import java.awt.Point;

public class ManhattanDist implements IHeuristic {

	@Override
	public int calcHeuristic(Point currLoc, Point goalLoc) {
		return Math.abs(currLoc.x - goalLoc.x) + Math.abs(currLoc.y - goalLoc.y);
	}

}
