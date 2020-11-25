package generic;

import code.mission.Operator;
import java.util.*;
public abstract  class SearchProblem {
	

	public abstract Node getInitialState();

	public abstract boolean goalTest(Node node);

	public abstract ArrayList<Operator> getAllowedOperators(Node state);

	public abstract int getCost(Node start, Operator action, Node dest);
	
	public abstract String getOptimalRepresentation(Node state, String Strategy);

	public abstract Node getNextState(Node previousNode, Operator action);
		


}