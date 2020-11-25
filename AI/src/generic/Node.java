package generic;


import code.mission.Operator;

public class Node {

String state; 
Node parent;
Operator operator;
Integer depth;
public Integer pathCost;
Integer totalCost;
@Override
public String toString() {
//	return "Node [state=" + state + ", parent=" + parent + ", operator=" + operator + ", depth=" + depth + ", pathCost="
//			+ pathCost + "]";
	return "Node [state=" + state + ", operator=" + operator + ", depth=" + getDepth() + ", pathCost="
	+ this.getPathCost() + ", Path=" + this.getPath()+"]";
}
public Node(String state, Node parent, Operator operator, Integer depth, Integer pathCost) {
	super();
	this.state = state;
	this.parent = parent;
	this.operator = operator;
	if(this.getParent()!=null)
	this.pathCost=this.getParent().getPathCost();
//	this.depth = (int) this.getPath().chars().filter(ch -> ch == ',').count()-1;
this.depth=0;
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
public Operator getOperator() {
	return operator;
}
public Integer getDepth() {
	return (int) this.getPath().chars().filter(ch -> ch == ',').count()-1;

}

public Integer getPathCost() {
	return this.pathCost;
}
public void setPathCost(Integer pathCost) {
this.totalCost = pathCost+this.getParent().getPathCost();
this.pathCost = pathCost;

}

public int totalCost() {
	return totalCost;
}
public String getPath() {
	if (operator==null)
		return "";
	return parent.getPath() +operator+",";
}

}