package generic;

import java.util.*;
import code.mission.UniformCostComparator;
import code.mission.Greedy1Comparator;
import code.mission.Greedy2Comparator;

import code.mission.AS1Comparator;
import code.mission.AS2Comparator;
import code.mission.Operator;

public class genericSearch {
	String Qing;
	public Integer expandedCount = 1;
	HashSet<String> expanded = new HashSet<String>();
	Boolean bottomReached = false;
	SearchProblem problem;
	UniformCostComparator comparator = new UniformCostComparator();
	Greedy1Comparator comparatorGR1 = new Greedy1Comparator();
	int depth;
	TreeMap<Integer, HashSet<String>> iter_map = new TreeMap<Integer, HashSet<String>>();

	public genericSearch(SearchProblem problem, String Strategy) {
		this.problem = problem;

		// return solve(problem.getInitialState().getState(), Strategy, false);

	}

	public Node solve(String grid, String Qing, Boolean Visualize) {
		this.Qing = Qing;
		expandedCount = 0;
		switch (Qing) {
		case "BF":
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(problem.getInitialState());
			while (!queue.isEmpty()) {

				Node current = queue.remove();

				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				;
				if (problem.goalTest(current)) {
					return current;
				}
				for (Operator op : Operators) {

					Node child = problem.getNextState(current, op);

					if (child != null && !expanded.contains(problem.getOptimalRepresentation(child, Qing))) {
						queue.add(child);
						expanded.add(problem.getOptimalRepresentation(child, Qing));

					}
				}

			}
			break;
		case "DF":
			Stack<Node> stack = new Stack<Node>();
			stack.add(problem.getInitialState());
			expanded.add(problem.getInitialState().getState());
			while (!stack.isEmpty()) {
				Node current = stack.pop();

				if (problem.goalTest(current)) {
					return current;
				}

				ArrayList<Operator> Operators = problem.getAllowedOperators(current);

				expandedCount += Operators.size();
				;

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(current, op));
				}
				for (Node child : children) {
					if (child != null && !expanded.contains(problem.getOptimalRepresentation(child, Qing))) {
						stack.add(child);
						expanded.add(problem.getOptimalRepresentation(child, Qing));
					}
				}

			}
			break;
		case "ID":
			for (int depth = 1; depth < Integer.MAX_VALUE; depth += 5) {
				System.out.println("Increasing Depth to: " + depth / 5);
				Node found = DLS(problem.getInitialState(), depth, depth);
				if (found != null) {
					return found;
				}

			}
			break;
		case "UC":
			PriorityQueue<Node> frontier = new PriorityQueue<Node>(new UniformCostComparator());

			frontier.add(problem.getInitialState());

			Node solution = null;
			while (!frontier.isEmpty()) {

				Node top = frontier.poll();
				if (expanded.contains(problem.getOptimalRepresentation(top, Qing))) {
					continue;
				}
				expanded.add(problem.getOptimalRepresentation(top, Qing));
				if (problem.goalTest(top)) {
					solution = top;

					return solution;

				}
				ArrayList<Operator> Operators = problem.getAllowedOperators(top);
				expandedCount += Operators.size();

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(top, op));
				}
				for (Node child : children) {
					frontier.offer(child);
				}
			}

			break;
		case "GR1":
			PriorityQueue<Node> frontierGR = new PriorityQueue<Node>(new Greedy1Comparator());

			frontierGR.add(problem.getInitialState());

			Node solutionGR = null;
			while (!frontierGR.isEmpty()) {
				Node top = frontierGR.poll();

				if (expanded.contains(top.getState())) {
					continue;
				}

				expanded.add(top.getState());
				if (problem.goalTest(top)) {
					solutionGR = top;
					return solutionGR;
				}
				ArrayList<Operator> Operators = problem.getAllowedOperators(top);
				expandedCount += Operators.size();
				;

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(top, op));
				}
				for (Node child : children) {
					frontierGR.offer(child);
				}
			}

			break;
		case "GR2":
			PriorityQueue<Node> frontierGR2 = new PriorityQueue<Node>(new Greedy2Comparator());

			frontierGR2.add(problem.getInitialState());

			Node solutionGR2 = null;
			while (!frontierGR2.isEmpty()) {
				Node top = frontierGR2.poll();

				if (expanded.contains(problem.getOptimalRepresentation(top, Qing))) {
					continue;
				}

				expanded.add(problem.getOptimalRepresentation(top, Qing));
				if (problem.goalTest(top)) {
					solutionGR2 = top;
					return solutionGR2;
				}
				ArrayList<Operator> Operators = problem.getAllowedOperators(top);
				expandedCount += Operators.size();
				;

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(top, op));
				}
				for (Node child : children) {
					frontierGR2.offer(child);
				}
			}

			break;

		case "AS1":
			PriorityQueue<Node> frontierAS1 = new PriorityQueue<Node>(new AS1Comparator());

			frontierAS1.add(problem.getInitialState());

			Node solutionAS1 = null;
			while (!frontierAS1.isEmpty()) {
				Node top = frontierAS1.poll();
				if (expanded.contains(problem.getOptimalRepresentation(top, Qing))) {
					continue;
				}

				expanded.add(problem.getOptimalRepresentation(top, Qing));
				if (problem.goalTest(top)) {
					solutionAS1 = top;
					return solutionAS1;
				}
				ArrayList<Operator> Operators = problem.getAllowedOperators(top);
				expandedCount += Operators.size();
				;

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(top, op));
				}
				for (Node child : children) {
					frontierAS1.offer(child);
				}
			}

			break;

		case "AS2":
			PriorityQueue<Node> frontierAS2 = new PriorityQueue<Node>(new AS2Comparator());
			frontierAS2.add(problem.getInitialState());

			Node solutionAS2 = null;
			while (!frontierAS2.isEmpty()) {
				Node top = frontierAS2.poll();
				if (expanded.contains(problem.getOptimalRepresentation(top, Qing))) {
					continue;
				}

				expanded.add(problem.getOptimalRepresentation(top, Qing));
				if (problem.goalTest(top)) {
					solutionAS2 = top;
					return solutionAS2;
				}
				ArrayList<Operator> Operators = problem.getAllowedOperators(top);
				expandedCount += Operators.size();
				;

				ArrayList<Node> children = new ArrayList<Node>();
				for (Operator op : Operators) {
					children.add(problem.getNextState(top, op));
				}
				for (Node child : children) {
					frontierAS2.offer(child);
				}
			}

			break;
		}
		return null;
	}

	public Node DLS(Node current, int depth, Integer initDepth) {
		if (!iter_map.containsKey(depth))
			iter_map.put(depth, new HashSet<String>());
		if (problem.goalTest(current)) {
			return current;
		}
		if (depth > depth * -5) {
			ArrayList<Operator> Operators = problem.getAllowedOperators(current);
			expandedCount += Operators.size();
			;

			for (int i = 0; i < Operators.size(); i++) {
				Node child = problem.getNextState(current, Operators.get(i));

				if (child != null && !iter_map.get(depth).contains(problem.getOptimalRepresentation(child, Qing))) {
					Node found = DLS(child, depth - 5, initDepth);
					iter_map.get(depth).add(problem.getOptimalRepresentation(child, Qing));

					if (found != null) {
						return found;
					}
				}
			}
		}
		return null;
	}

}
