package agentMap;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class aStarSearch implements ISearch {
	private IHeuristic heuristic;
	HashMap<Point, Boolean> visited;
	
	public aStarSearch() {
		heuristic = new ManhattanDist();
		visited = new HashMap<Point, Boolean>();
	}
	
	@Override
	public LinkedList<Point> getPath(Point currLoc, Point destLoc, Tile map[][]) {
		// Take note of the map's dimensions
		int mapHeight = map.length;
		int mapWidth = map[0].length;
		// Initialise the first state in the priority queue
		int hCost = heuristic.calcHeuristic(currLoc, destLoc);
		State startState = new State(null, currLoc, 0, hCost);
		PriorityQueue<State> pq = new PriorityQueue<State>();
		pq.add(startState);
		// Start the search
		while (pq.size() != 0) {
			State currState = pq.poll();
			Point currStatePos = currState.getCurrLoc();
			// Check if the state has been visited
			if (visited.containsKey(currStatePos) && visited.get(currStatePos) == true) {
				continue;
			}
			// Check if the state has reached the destination point
			if (currStatePos.x == destLoc.x && currStatePos.y == destLoc.y) {
				return currState.getPath();
			}
			// Continue the search
			// Create a state for the tile to the left
			if (currStatePos.x - 1 >= 0) {
				addNewState(pq, currState, destLoc, "West", map);
			}
			// Create a state for the tile to the Right
			if (currStatePos.x + 1 < mapWidth) {
				addNewState(pq, currState, destLoc, "East", map);
			}
			// Create a state for the tile above
			if (currStatePos.y - 1 >= 0) {
				addNewState(pq, currState, destLoc, "North", map);
			}
			// Create a state for the tile below
			if (currStatePos.y + 1 < mapHeight) {
				addNewState(pq, currState, destLoc, "South", map);
			}
			visited.put(currStatePos, true);
		}
		return null;
	}

	/**
	 * Using the parameters given, generate a new state to add to the priority queue
	 * to be used in the A* search
	 * @param pq existing priority queue
	 * @param currState State currently being looked at in the A* algorithm
	 * @param destLoc Point for the A* to reach
	 * @param direction Direction to create the new state
	 * @param map Map containing tiles (Used to get passCost for a given tile)
	 * @param heuristic Heuristic function
	 */
	public void addNewState(PriorityQueue<State> pq, State currState, Point destLoc, 
			String direction, Tile[][] map) {
		Point nextPoint = null;
		Point currStatePos = currState.getCurrLoc();
		/* Determine the point of the new state using the current state's position
		and the direction given*/ 
		switch(direction) {
		case "West":
			nextPoint = new Point(currStatePos.x - 1, currStatePos.y);
			break;
		case "East":
			nextPoint = new Point(currStatePos.x + 1, currStatePos.y);
			break;
		case "North":
			nextPoint = new Point(currStatePos.x, currStatePos.y - 1);
			break;
		case "South":
			nextPoint = new Point(currStatePos.x, currStatePos.y + 1);
			break;
		}
		// Check if the point has already been visited previously
		if (visited.containsKey(nextPoint) && visited.get(nextPoint) == true) {
			return;
		}
		// Check if the point is passable
		if (map[nextPoint.y][nextPoint.x].getPassable() == false) return;
		// Create the new state and add it to the priority queue
		int hCost = heuristic.calcHeuristic(nextPoint, destLoc);
		int gCost = currState.getGCost() + map[nextPoint.y][nextPoint.x].getPassCost();
		State newState = new State(currState, nextPoint, gCost, hCost);
		pq.add(newState);
	}
}
