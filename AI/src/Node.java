import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {

String state; 
Node parent;
Operator operator;
Integer depth;
Integer pathCost;

@Override
public String toString() {
//	return "Node [state=" + state + ", parent=" + parent + ", operator=" + operator + ", depth=" + depth + ", pathCost="
//			+ pathCost + "]";
	return "Node [state=" + state + ", operator=" + operator + ", depth=" + depth + ", pathCost="
	+ pathCost + ", Path=" + this.getPath()+"]";
}
public Node(String state, Node parent, Operator operator, Integer depth, Integer pathCost) {
	super();
	this.state = state;
	this.parent = parent;
	this.operator = operator;
	this.depth = depth;
	this.pathCost = pathCost;
}

public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public Node getParent() {
	return parent;
}
public void setParent(Node parent) {
	this.parent = parent;
}
public Operator getOperator() {
	return operator;
}
public void setOperator(Operator operator) {
	this.operator = operator;
}
public Integer getDepth() {
	return depth;
}
public void setDepth(Integer depth) {
	this.depth = depth;
}
public Integer getPathCost() {
	if (pathCost==null)
		return 0;
	if (parent==null)
		return 0;
	return parent.getPathCost()+pathCost;
}
public void setPathCost(Integer pathCost) {
	this.pathCost = pathCost;
}
public String getPath() {
	if (operator==null)
		return "";
	
	return parent.getPath() +operator+",";
}

}