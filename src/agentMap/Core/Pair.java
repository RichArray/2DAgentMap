package agentMap.Core;

/**
 * 
 * @author Richard Luong
 * Generic tuple of size 2
 * @param <T> Generic type
 * @param <S> Generic type
 */
public class Pair<T, S> {
	public T first;
	public S second;
	
	Pair (T first, S second) {
		this.first = first;
		this.second = second;
	}
};