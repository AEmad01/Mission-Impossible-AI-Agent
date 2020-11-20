import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.*;
import javax.swing.*;

public class MissionImpossibleProblem implements SearchProblem, genericSearch {
	
	Node initialNode;
	Integer m;
	Integer n;
	Integer SubX;
	Integer SubY;
	Integer debug = 0;
	Integer id = 0;
	boolean bottomReached=false;
	// Integer Capacity;
	TreeSet<String> tree_map = new TreeSet<String>();
	// HashSet<Ally> previousAllies = new HashSet<Ally>();

	Boolean goal = false;
	// Integer EthanX;
	// Integer EthanY;
	// Integer depth = 0;
	Integer initCapacity;

	@Override
	public Node getInitialState() {
		return initialNode;
	}

	public MissionImpossibleProblem(Node initialNode) {
		super();
		this.initialNode = initialNode;
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(initialNode.getState().split(";")));
		initCapacity=Integer.parseInt(Inputs.get(5));
		System.out.println("init" + initCapacity);
	}

	public Integer getCost (Node node, ArrayList<Ally> Allies) {
		int damage =0;
		int dead =0;
		int dropped=0;
		int carried =0;
		for (Ally ally: Allies) {
			damage=damage+ally.getDamage();
			if (ally.getDamage()==100)
				dead++;
			if (ally.isDropped()&&ally.getDamage()<98)
				dropped++;
			if (ally.isCarried()&&ally.getDamage()<98)
				carried++;
		}
		

		
		return damage;
	}

	public void solve(String strat) {
		
		if (strat=="BFS")
		{Queue<Node> queue = new ArrayDeque<>();

		queue.add(initialNode);
		String state = initialNode.getState();
		
		
		
		getAllies(initialNode);
	     while (!queue.isEmpty()) {
	    	 debug++;
	         // popping the Node from the queue
	             Node poppedNode = queue.remove();
	             // printing the value
	             //System.out.println(poppedNode.toString());
	             
	             //each node that is popped from the queue has a list of neighbours
	             //so we get those neighbours
            	 System.out.println(poppedNode.toString());

	             ArrayList<Node> elementsOfPoppedNode = getChildren(poppedNode);
	             if (goalTest(poppedNode))
	             {
	 				System.out.print("**********GOAL**********");
	            	 System.out.println(poppedNode.toString());

	            	 return;

	             }
	             //accessing each node from that list of neighbours that we just got
	             for (int i = 0; i < elementsOfPoppedNode.size(); i++) {
	                 Node n = elementsOfPoppedNode.get(i);
	                 if (n != null && !tree_map.contains(n.getState())) {
	                     queue.add(n);     //adding to the queue
	                     tree_map.add(n.getState()); //making it visited
	                 }
	             }
	             
	         }}//BFS END
		
		if (strat=="DFS")
		{Stack<Node> queue = new Stack<>();

		queue.add(initialNode);

		getAllies(initialNode);
	     while (!queue.isEmpty()) {
	    	 debug++;
	         // popping the Node from the queue
	             Node poppedNode = queue.pop();
	             // printing the value
	             //System.out.println(poppedNode.toString());
	             
	             //each node that is popped from the queue has a list of neighbours
	             //so we get those neighbours
	             ArrayList<Node> elementsOfPoppedNode = getChildren(poppedNode);
	             if (goalTest(poppedNode))
	             {
	 				System.out.print("**********GOAL**********");
	            	 System.out.println(poppedNode.toString());

	            	 return;

	             }
	             //accessing each node from that list of neighbours that we just got
	             for (int i = 0; i < elementsOfPoppedNode.size(); i++) {
	                 Node n = elementsOfPoppedNode.get(i);
	                 if (n != null && !tree_map.contains(n.getState())) {
	                     queue.add(n);     //adding to the queue
	                     tree_map.add(n.getState()); //making it visited
	                 }
	             }
	             
	         }}//BFS END
		
		if (strat=="IDS") {
			System.out.print(strat);
	        int depth = 1;
	        while (!bottomReached) {
	            bottomReached = true; // One of the "end nodes" of the search with this depth has to still have children and set this to false again
	            Node result = iterativeDeepeningDFS(initialNode,0, depth);

	            if (result != null) {
	                // We've found the goal node while doing DFS with this max depth
		            System.out.println(result.toString());
		            return;

	            }

	            // We haven't found the goal node, but there are still deeper nodes to search through\
	            System.out.println(tree_map.size());
	            tree_map.clear();
	            depth=depth+1;
	            
	         //  System.out.println("Increasing depth to " + depth);
	        }


		}

		
		

	}

	public Node searchProblem(Node initialState, String strategy) {
		// TODO Auto-generated method stub
		return null;
	}
	
    private Node iterativeDeepeningDFS(Node node, int currentDepth, int maxDepth) {
     // System.out.println(node.toString());
        if (goalTest(node)) {
            // We have found the goal node we we're searching for
            System.out.println(node.toString());
            return node;
        }
     

	     ArrayList<Node> children = new ArrayList<Node>(getChildren(node));
// if (node.getOperator()==Operator.DROP)
// {
//	 System.out.println(node.toString());
// }

        if (currentDepth == maxDepth) {
          //  System.out.println(maxDepth);
            // We have reached the end for this depth...
            if (children.size() > 0) {
                //...but we have not yet reached the bottom of the tree
                bottomReached = false;
            }
            return null;
        }
        for (int i = 0; i < children.size(); i++) {
            Node n = children.get(i);
            if (n != null && !tree_map.contains(n.getState())) {
                tree_map.add(n.getState()); //making it visited
                Node result = iterativeDeepeningDFS(n, currentDepth + 1, maxDepth);
                if (result != null) {
                    // We've found the goal node while going down that child
                    return result;
                }
            }
        }
//        for (int i = 0; i < children.size(); i++) {
//            Node n = children.get(i);
//            if (n != null && !tree_map.contains(n.getState())) {
//                Node result = iterativeDeepeningDFS(children.get(i), currentDepth + 1, maxDepth);
//                tree_map.add(n.getState()); //making it visited
//                if (result != null) {
//                    // We've found the goal node while going down that child
//                    return result;
//                }
//            }
//        }
       // System.out.println(tree_map.toString());

        // We've gone through all children and not found the goal node
        return null;
    }
	public boolean contains(Node node) {
		

		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(node.getState().split(";")));
		String wihtoutId;
		if (Inputs.size() > 6 && Inputs.size() < 7)
			wihtoutId = Inputs.get(0) + ";" + Inputs.get(1) + ";" + Inputs.get(2) + ";" + Inputs.get(3) + ";"
					+ Inputs.get(4) + ";" + Inputs.get(5) + ";" + Inputs.get(6) + ";"+ ";";
		else if (Inputs.size() > 7)
			wihtoutId = Inputs.get(0) + ";" + Inputs.get(1) + ";" + Inputs.get(2) + ";" + Inputs.get(3) + ";"
					+ Inputs.get(4) + ";" + Inputs.get(5) + ";" + Inputs.get(6) + ";"+Inputs.get(7)+";";
		else
			wihtoutId = Inputs.get(0) + ";" + Inputs.get(1) + ";" + Inputs.get(2) + ";" + Inputs.get(3) + ";"
					+ Inputs.get(4) + ";" + Inputs.get(5) + ";" + ";"+";" + ";";
		if (tree_map.contains(wihtoutId)) {
			//System.out.println("repeated");
			return true;
		}

		return false;

	}

	public ArrayList<Node> getChildren(Node input) {
		HashSet<Operator> validOperators = getAllowedOperators(input);
		// System.out.println(validOperators.toString());
		ArrayList<Node> children = new ArrayList<Node>();
//		if (goalTest(input))
//			return children;
		for (Operator op : validOperators) {
			Node node = apply(input, op);
				children.add(node);

		}

		return children;
	}

	public boolean goalTest(Node node) {
//		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(node.getState().split(";")));
//		ArrayList<String> Dimensions = new ArrayList<>(Arrays.asList(Inputs.get(0).split(",")));
//		Integer m = Integer.parseInt(Dimensions.get(0));
//		Integer n = Integer.parseInt(Dimensions.get(1));
//		ArrayList<String> Ethan = new ArrayList<>(Arrays.asList(Inputs.get(1).split(",")));
//		Integer EthanX = Integer.parseInt(Ethan.get(0));
//		Integer EthanY = Integer.parseInt(Ethan.get(1));
//		ArrayList<String> Sub = new ArrayList<>(Arrays.asList(Inputs.get(2).split(",")));
//		Integer SubX = Integer.parseInt(Sub.get(0));
//		Integer SubY = Integer.parseInt(Sub.get(1));
//		initCapacity = Integer.parseInt(Inputs.get(5));
//		ArrayList<String> visitedInput;
//		try {
//			visitedInput = new ArrayList<>(Arrays.asList(Inputs.get(6).split(",")));
//		} catch (Exception e) {
//			visitedInput = new ArrayList<String>();
//
//		}

		ArrayList<Ally> Allies = getAllies(node);

		boolean goal = true;
		for (Ally ally : Allies) {
			if (!ally.isDropped()) {
				goal = false;
			}
		}
		return goal;
	}

	public ArrayList<Ally> getAllies(Node inputNode) {
		String state = inputNode.getState();
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(state.split(";")));
		ArrayList<String> AlliesString = new ArrayList<>(Arrays.asList(Inputs.get(3).split(",")));
		ArrayList<Ally> Allies = new ArrayList<Ally>();
		ArrayList<ArrayList<Integer>> AlliesPositions = new ArrayList<ArrayList<Integer>>();
		ArrayList<String> AlliesHealthString = new ArrayList<>(Arrays.asList(Inputs.get(4).split(",")));
		ArrayList<String> carriedInput;

		try {
			carriedInput = new ArrayList<>(Arrays.asList(Inputs.get(6).split(",")));
		} catch (Exception e) {
			carriedInput = new ArrayList<String>();

		}
		ArrayList<String> droppedInput;
		try {
			droppedInput = new ArrayList<>(Arrays.asList(Inputs.get(7).split(",")));
		} catch (Exception e) {
			droppedInput = new ArrayList<String>();

		}
		Integer Capacity = Integer.parseInt(Inputs.get(5));
		for (int i = 0; i < AlliesString.size(); i += 2) {

			int x = Integer.parseInt(AlliesString.get(i));
			int y = Integer.parseInt(AlliesString.get(i + 1));
			ArrayList<Integer> pos = new ArrayList<Integer>();
			pos.add(x);
			pos.add(y);
			AlliesPositions.add(pos);
		}

		for (int i = 0; i < AlliesHealthString.size(); i++) {
			Allies.add(new Ally(Integer.parseInt(AlliesHealthString.get(i)), AlliesPositions.get(i).get(0),
					AlliesPositions.get(i).get(1)));
		}
		if (carriedInput.size() > 0 && carriedInput.get(0).length() > 0) {
			for (String s : carriedInput) {
				Allies.get(Integer.parseInt(s)).setCarried(true);
			}
		}
		if (droppedInput.size() > 0 && droppedInput.get(0).length() > 0) {
			for (String s : droppedInput) {
				Allies.get(Integer.parseInt(s)).setDropped(true);
				Allies.get(Integer.parseInt(s)).setCarried(false);

			}
		}
		// System.out.println(Allies.toString());
		// System.out.println(visitedInput +"visited history");
		return Allies;
	}

	@Override
	public HashSet<Operator> getAllowedOperators(Node inputNode) {
		String state = inputNode.getState();
		// TODO Auto-generated method stub
		HashSet<Ally> AlliesCopy;
		// process input
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(state.split(";")));
		// first element is dimensions m,n
		ArrayList<String> Dimensions = new ArrayList<>(Arrays.asList(Inputs.get(0).split(",")));
		Integer m = Integer.parseInt(Dimensions.get(0));
		Integer n = Integer.parseInt(Dimensions.get(1));
		// second element = ethan pos x,y
		ArrayList<String> Ethan = new ArrayList<>(Arrays.asList(Inputs.get(1).split(",")));
		Integer EthanX = Integer.parseInt(Ethan.get(0));
		Integer EthanY = Integer.parseInt(Ethan.get(1));
		// submarine

		ArrayList<String> Sub = new ArrayList<>(Arrays.asList(Inputs.get(2).split(",")));
		Integer SubX = Integer.parseInt(Sub.get(0));
		Integer SubY = Integer.parseInt(Sub.get(1));
		// Capacity
		// Integer Capacity = Integer.parseInt(Inputs.get(5));
		// prepare list of valid moves
		HashSet<Operator> validMoves = new HashSet<Operator>();
		ArrayList<Ally> Allies = getAllies(inputNode);
		Integer Capacity = Integer.parseInt(Inputs.get(5));

		// LEFT Check
		if (EthanY - 1 >= 0) {
			validMoves.add(Operator.LEFT);
		}
		// UP Check
		if (EthanX - 1 >= 0) {
			validMoves.add(Operator.UP);
		}
		// Right Check
		if (EthanY + 1 < m) {
			validMoves.add(Operator.RIGHT);
		}
		// Down Check
		if (EthanX + 1 < n) {
			validMoves.add(Operator.DOWN);
		}
		// Left Check

		// Carry Check
		for (Ally ally : Allies) {
			// if ally is not dead and ally is not already carried and ethan
			// TODO Add Capacity Check
			// System.out.println("Ethan: X:" + EthanX + "Ethan Y:" + EthanY);
			if (EthanX == ally.getX() && EthanY == ally.getY() && !ally.isCarried() && Capacity > 0) {
				validMoves.add(Operator.CARRY);

			}

		}
		// Drop Check
		for (Ally ally : Allies) {
			// if ethan is standing on the submarine and has an ally that is not dropped and
			// is carried
			if (EthanX == SubX && EthanY == SubY && ally.isCarried()) {
				validMoves.add(Operator.DROP);
				break;

			}

		}
		
//		for (Operator op : validMoves) {
//			Node node = apply(inputNode, op);
//			if (contains(node))
//				validMoves.remove(op);
//		}

		return validMoves;
	}

//	public String getStateHistory(Node previousNode) {
//		return previousNode.getState();
//		// HashSet<Ally> AlliesCopy = AlliesMap.get(previousNode.getPath());
//		// ArrayList<String> Inputs = new
//		// ArrayList<>(Arrays.asList(previousNode.getState().split(";")));
//		// String newState = Inputs.get(0) + ";" + Inputs.get(1)+ ";" +Inputs.get(2)+
//		// ";" + Inputs.get(5);
//		// return newState;
//
//	}

	@Override
	public Node apply(Node previousNode, Operator action) {
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(previousNode.getState().split(";")));
		ArrayList<String> Dimensions = new ArrayList<>(Arrays.asList(Inputs.get(0).split(",")));
		Integer m = Integer.parseInt(Dimensions.get(0));
		Integer n = Integer.parseInt(Dimensions.get(1));
		ArrayList<String> Ethan = new ArrayList<>(Arrays.asList(Inputs.get(1).split(",")));
		Integer EthanX = Integer.parseInt(Ethan.get(0));
		Integer EthanY = Integer.parseInt(Ethan.get(1));
		ArrayList<String> Sub = new ArrayList<>(Arrays.asList(Inputs.get(2).split(",")));
		Integer SubX = Integer.parseInt(Sub.get(0));
		Integer SubY = Integer.parseInt(Sub.get(1));
		Integer Capacity = Integer.parseInt(Inputs.get(5));
		String carriedInput;
		try {
			carriedInput = Inputs.get(6);
		} catch (Exception e) {
			carriedInput = "";

		}
		String droppedInput;
		try {
			droppedInput = Inputs.get(7);
		} catch (Exception e) {
			droppedInput = "";

		}
		String historyInput;

		ArrayList<Ally> Allies = getAllies(previousNode);
		switch (action) {

		// move actions
		case LEFT:
			EthanY--;
			for (Ally ally : Allies) {

				if (!ally.isCarried() && !ally.isDropped()) {
					ally.damage(previousNode.getDepth());
				}
			}

			break;

		case UP:
			EthanX--;
			for (Ally ally : Allies) {

				if (!ally.isCarried() && !ally.isDropped() && ally.getDamage() < 100)
					ally.damage(previousNode.getDepth());
			}

			break;

		case RIGHT:
			EthanY++;
			for (Ally ally : Allies) {

				if (!ally.isCarried() && !ally.isDropped())
					ally.damage(previousNode.getDepth());
			}
			break;

		case DOWN:
			EthanX++;
			for (Ally ally : Allies) {

				if (!ally.isCarried() && !ally.isDropped() && ally.getDamage() < 100)
					ally.damage(previousNode.getDepth());
			}
			break;
		// Carry and drop actions

		case CARRY:
			for (Ally ally : Allies) {
				if (EthanX == ally.getX() && EthanY == ally.getY() && Capacity > 0 && ally.isDropped() == false) {
					//&& Capacity > 0 && ally.isDropped() == false
					ally.setCarried(true);
					Capacity--;
					break;
				}
				if (!ally.isCarried() && !ally.isDropped() && ally.getDamage() < 100)
					ally.damage(previousNode.getDepth());
			}
			break;
		case DROP:
			for (Ally ally : Allies) {
//				if (ally.isCarried())
//					System.out.println("***********************SHO8ULD DROP*************");
				if (EthanX == SubX && EthanY == SubY && ally.isCarried() && !ally.isDropped()) {
					ally.setDropped(true);
					ally.setCarried(false);
					Capacity = initCapacity;
				}
				if (!ally.isCarried() && !ally.isDropped() && ally.getDamage() < 100)
					ally.damage(previousNode.getDepth());

			}
			break;

		}
		for (Ally ally : Allies) {
			if (ally.isCarried() == true && ally.isDropped() == false) {
				ally.setX(EthanX);
				ally.setY(EthanY);

			}
		}
		// System.out.println(Allies.toString());

		// System.out.println(Allies.toString());
		String PositionsText = "";
		String HealthText = "";
		for (Ally ally : Allies) {
			// System.out.println(ally.toString());
			PositionsText = PositionsText + Integer.toString(ally.getX()) + "," + Integer.toString(ally.getY()) + ",";
			HealthText = HealthText + Integer.toString(ally.getDamage()) + ",";
		}
		
		String carriedString="";
		for (int i = 0; i < Allies.size(); i++) {
			if (Allies.get(i).isCarried() && !Allies.get(i).isDropped() && !carriedString.contains(Integer.toString(i))) {
				if (carriedString.length() == 0)
					carriedString = carriedString + i;
				else
					carriedString = carriedString + "," + i;

			}
		}
		String droppedString="";
		for (int i = 0; i < Allies.size(); i++) {
			if (Allies.get(i).isDropped() && !droppedString.contains(Integer.toString(i))) {
				if (droppedString.length() == 0)
					droppedString = droppedString + i;
				else
					droppedString = droppedString + "," + i;

			}
		}
		HealthText = HealthText.replaceFirst(".$", "");
		PositionsText = PositionsText.replaceFirst(".$", "");
		String newState = Integer.toString(m) + "," + Integer.toString(n) + ";" + Integer.toString(EthanX) + ","
				+ Integer.toString(EthanY) + ";" + Integer.toString(SubX) + "," + Integer.toString(SubY) + ";"
				+ PositionsText + ";" + HealthText + ";" + Integer.toString(Capacity) + ";" + carriedString + ";"+droppedString+";";
		PositionsText = PositionsText.replaceFirst(".$", "");

		// System.out.println(newState);
		Node next;
		next = new Node(newState, previousNode, action, 0, 0);
		ArrayList<String> depthFromState = new ArrayList<>(Arrays.asList(next.getPath().split(",")));
		ArrayList<String> depthFromStateT = new ArrayList<>(Arrays.asList(previousNode.getPath().split(",")));
next.setPathCost(getCost(next,Allies));
		next.setDepth(depthFromState.size());
		// if (previousNode.getParent()!=null)
		// tree_map.add(previousNode.getParent().getState());

		// System.out.println("****");
		// AlliesMap.entrySet().forEach(entry->{
		// System.out.println(entry.getKey() + " " + entry.getValue());
		// });
		// System.out.println("****");

		// System.out.println( Allies.toString());

		return next;

	}

	@Override
	public int getCost(Node start, Operator action, Node dest) {
		// TODO Auto-generated method stub
		return 0;
	}
	class NodeComparator implements Comparator<Node>{ 
        
  
        public int compare(Node s1, Node s2) { 
            if (s1.getPathCost() < s2.getPathCost()) 
                return 1; 
            else if (s1.getPathCost() > s2.getPathCost()) 
                return -1; 

            
        return 0;
    }}
	
	class SquareTextField extends JTextField {

		int size = 30;

		SquareTextField(String s) {
			super(s);
			setFont(getFont().deriveFont((float) size));
			int sz = size / 6;
			setMargin(new Insets(sz, sz, sz, sz));
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			int w = d.width;
			int h = d.height;
			int max = w > h ? w : h;

			return new Dimension(max, max);
		}
	}
}
