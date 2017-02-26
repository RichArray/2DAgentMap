package agentMap.Core;

import java.awt.Point;
import java.util.LinkedList;

/**
 * 
 * @author Richard Luong
 * A state will be used in an A* search to keep track of a path to
 * a given point on the InternalMap
 */
public class State implements Comparable<State> {
	private State prevState;
	private Point currLoc;
	private int gCost;
	private int hCost;
	private Direction currDir;

	/**
	 * Constructor for state
	 * @param prevState The previous state
	 * @param currLoc The point the state is at on the map
	 * @param gCost The cost of the path the state has been on
	 * @param hCost The estimated cost to the destination
	 */
	public State(State prevState, Point currLoc, int gCost, int hCost, Direction direction) {
		this.prevState = prevState;
		this.currLoc = currLoc;
		this.gCost = gCost;
		this.hCost = hCost;
		this.currDir = direction;
	}
	
	/**
	 * Generates a list of points that represent the path from one point
	 * to the destination point.
	 * @return List of points representing the path from one point to another
	 */
	public LinkedList<Pair<Direction, Point>> getPath() {
		// Stopping condition for recursion
		LinkedList<Pair<Direction, Point>> currPath = null;
		if (prevState == null) {
			currPath = new LinkedList<Pair<Direction, Point>>();
		} else {
			currPath = prevState.getPath();
		}
		currPath.add(new Pair<Direction, Point>(currDir, currLoc));
		return currPath;
	}
	
	/**
	 * Getter: Gets the current point on the map that the state is at
	 * @return Current point of the state on the map
	 */
	public Point getCurrLoc() {
		return currLoc;
	}
	
	/**
	 * Gets the total cost of the evaluation function for A*
	 * @return  f(n) = g(n) + h(n)
	 */
	public int getFCost() {
		return gCost + hCost;
	}
	
	/**
	 * Getter: Gets the current cost of the path so far
	 * @return Cost of the path so far
	 */
	public int getGCost() {
		return gCost;
	}
	
	/**
	 * Getter: Gets the estimated number of steps to the goal from this state
	 * @return Estimated steps to goal
	 */
	public int getHCost() {
		return hCost;
	}
	
	@Override
	public int compareTo(State otherState) {
		return getFCost() - otherState.getFCost();
	}
}
