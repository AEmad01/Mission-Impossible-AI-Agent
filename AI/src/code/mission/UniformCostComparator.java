package code.mission;
import java.util.Comparator;
import generic.Node;
public class UniformCostComparator implements Comparator<Node> {
	public int compare(Node s1, Node s2) {
			return (s1.totalCost()) -  (s2.totalCost());
	}
}
