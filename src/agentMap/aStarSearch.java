package agentMap;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class aStarSearch implements ISearch {
	private IHeuristic heuristic;
	private boolean eightDirections;
	HashMap<Point, Boolean> visited;
	
	public aStarSearch(IHeuristic heuristic, boolean eightDirections) {
		this.heuristic = heuristic;
		this.eightDirections = eightDirections;
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
				addNewState(pq, currState, destLoc, Direction.W, map);
			}
			// Create a state for the tile to the Right
			if (currStatePos.x + 1 < mapWidth) {
				addNewState(pq, currState, destLoc, Direction.E, map);
			}
			// Create a state for the tile above
			if (currStatePos.y - 1 >= 0) {
				addNewState(pq, currState, destLoc, Direction.N, map);
			}
			// Create a state for the tile below
			if (currStatePos.y + 1 < mapHeight) {
				addNewState(pq, currState, destLoc, Direction.S, map);
			}
			// Additional states if the agent is able to move diagonally
			if (eightDirections) {
				// Create a state for the tile north west
				if (currStatePos.x - 1 >= 0 && currStatePos.y - 1 >= 0) {
					addNewState(pq, currState, destLoc, Direction.NW, map);
				}
				// Create a state for the tile north east
				if (currStatePos.x + 1 < mapWidth && currStatePos.y - 1 >= 0) {
					addNewState(pq, currState, destLoc, Direction.NE, map);
				}
				// Create a state for the tile north west
				if (currStatePos.x - 1 >= 0 && currStatePos.y + 1 < mapHeight) {
					addNewState(pq, currState, destLoc, Direction.SW, map);
				}
				// Create a state for the tile north west
				if (currStatePos.x + 1 < mapWidth && currStatePos.y + 1 < mapHeight) {
					addNewState(pq, currState, destLoc, Direction.SE, map);
				}
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
			Direction direction, Tile[][] map) {
		Point nextPoint = null;
		Point currStatePos = currState.getCurrLoc();
		/* Determine the point of the new state using the current state's position
		and the direction given*/ 
		switch(direction) {
		case W:
			nextPoint = new Point(currStatePos.x - 1, currStatePos.y);
			break;
		case E:
			nextPoint = new Point(currStatePos.x + 1, currStatePos.y);
			break;
		case N:
			nextPoint = new Point(currStatePos.x, currStatePos.y - 1);
			break;
		case S:
			nextPoint = new Point(currStatePos.x, currStatePos.y + 1);
			break;
		case NW:
			nextPoint = new Point(currStatePos.x - 1, currStatePos.y - 1);
			break;
		case NE:
			nextPoint = new Point(currStatePos.x + 1, currStatePos.y - 1);
			break;
		case SW:
			nextPoint = new Point(currStatePos.x - 1, currStatePos.y + 1);
			break;
		case SE:
			nextPoint = new Point(currStatePos.x + 1, currStatePos.y + 1);
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
