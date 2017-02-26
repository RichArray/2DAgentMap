package agentMap.Core;

/**
 * 
 * @author Richard Luong
 * Direction representing the position the agent can move to
 * on the map
 */

public enum Direction {
	/**
	 * The Directions act as a doubly linked list, where the first argument is its direction
	 * in character form and the 2 arguments after are directions that are before and after the 
	 * current direction in character form (Like pointers).
	 */
	N("n", "nw", "ne"), S("s", "se", "sw"), 
	E("e", "ne", "se"), W("w", "sw", "nw"), 
	NE("ne", "n", "e"), NW("nw", "w", "n"),
	SE("ne", "e", "s"), SW("se", "s", "w"),
	NONE(" ", " ", " ");
	
	public String strVal;
	public String prev;
	public String next;
	
	/**
	 * Constructor
	 * @param strVal String representing the direction
	 * @param prev direction on the left
	 * @param next direction on the right
	 */
	Direction (String charVal, String prev, String next) {
		this.strVal = charVal;
		this.prev = prev;
		this.next = next;
	}
	
	/**
	 * Iterates through directions and finds the one whose character form corresponds
	 * to the parameter/
	 * @param c character form of direction to match
	 * @return corresponding direction to the character passed
	 */
	private Direction charToDirection(String str) {
		for (Direction d: Direction.values()) {
			if (d.strVal.equals(str)) return d;
		}
		return Direction.NONE;
	}
	
	/**
	 * Change the direction based on whether the agent moved left or right
	 * @param move right or left move
	 * @return the new direction that the agent is facing
	 */
	public Direction changeDirection (char move) {
		if (move == 'l') {
			return charToDirection(this.prev);
		} else if (move == 'r') {
			return charToDirection(this.next);
		}
		return null;
	}
}