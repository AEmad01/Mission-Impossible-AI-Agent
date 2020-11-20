import java.util.Collection;
public interface SearchProblem {
	public Node getInitialState();

	public boolean goalTest(Node node);

	public Collection<Operator> getAllowedOperators(Node state);

	public Node apply(Node state, Operator action);

	public int getCost(Node start, Operator action, Node dest);
}