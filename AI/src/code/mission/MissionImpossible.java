package code.mission;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.*;
import javax.swing.*;

import generic.Node;
import generic.SearchProblem;
import generic.genericSearch;
import java.util.concurrent.TimeUnit;

public class MissionImpossible extends SearchProblem {

	Node initialNode;
	Integer m;
	Integer n;
	Integer SubX;
	Integer SubY;
	Integer debug = 0;
	Integer id = 0;
	String Strategy;
	JFrame frame;
	JPanel panel;
	String solutionString;
	static Node start;
	static MissionImpossible game;
	Integer targetCount = 0;
	Boolean goal = false;
	Integer initCapacity;

	@Override
	public Node getInitialState() {
		return initialNode;
	}

	public MissionImpossible(Node initialNode) {
		super();
		this.initialNode = initialNode;
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(initialNode.getState().split(";")));
		initCapacity = Integer.parseInt(Inputs.get(5));
	}

	public static String solve(String grid, String Strategy, boolean visualize) {
		start = new Node(grid, null, null, 0, 0);
		String state = grid;
		String[] split = state.split(";");
		String[] health = split[4].split(",");
		// prepare carry and drop
		state = state + ";";
		for (int i = 0; i < health.length; i++) {

			state = state + 0 + ",";
		}
		state = state.replaceFirst(".$", "");
		state = state + ";";
		for (int i = 0; i < health.length; i++) {

			state = state + 0 + ",";
		}
		state = state.replaceFirst(".$", "");
		state = state + ";";
		Node start = new Node(state, null, null, 0, 0);
		game = new MissionImpossible(start);
		game.Strategy = Strategy;

		genericSearch search = new genericSearch(game, Strategy);
		Node solution = search.solve(state, Strategy, visualize);
		game.solutionString = solution.getPath().replaceFirst(".$", "").toLowerCase();
		if (visualize) {
			game.visualize(start, game.solutionString);
		}
		Integer deaths = 0;
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(solution.getState().split(";")));
		ArrayList<String> Dead = new ArrayList<>(Arrays.asList(Inputs.get(4).split(",")));
		for (String s : Dead) {
			if (s.equals("100"))
				deaths++;
		}
		System.out.println(solution.getPath().replaceFirst(".$", "").toLowerCase() + ";" + deaths + ";"
				+ Dead.toString().replace("[", "").replace("]", "").replace(" ", "") + ";" + search.expandedCount);
		return solution.getPath().replaceFirst(".$", "").toLowerCase();
	}

	@Override
	public boolean goalTest(Node poppedNode) {
		Boolean goal = true;
		// If the dropped array is all ones then we are at the goal
		// We only check the array if the operation is drop
		if (poppedNode.getOperator() == Operator.DROP) {
			String state = poppedNode.getState();
			String[] split = state.split(";");
			String[] drop = split[7].split(",");
			for (String st : drop) {
				if (!st.equals("1")) {
					goal = false;
				}
			}

			return goal;
		}
		return false;
	}

	@Override
	public ArrayList<Operator> getAllowedOperators(Node inputNode) {
		// return an array of the allowed operators for this node
		String state = inputNode.getState();
		String[] split = state.split(";");
		ArrayList<Operator> validMoves = new ArrayList<Operator>();
		String[] Dimensions = split[0].split(",");
		Integer m = Integer.parseInt(Dimensions[0]);
		Integer n = Integer.parseInt(Dimensions[1]);
		String[] Ethan = split[1].split(",");
		Integer EthanX = Integer.parseInt(Ethan[0]);
		Integer EthanY = Integer.parseInt(Ethan[1]);

		String[] Sub = split[2].split(",");
		Integer SubX = Integer.parseInt(Sub[0]);
		Integer SubY = Integer.parseInt(Sub[1]);
		String[] health = split[4].split(",");
		String[] Pos = split[3].split(",");
		String[] Carry = split[6].split(",");
		String[] Drop = split[7].split(",");
		Integer Capacity = Integer.parseInt(split[5]);
		Integer[][] AlliesPos = new Integer[Pos.length / 2][2];
		for (int i = 0; i < Pos.length; i += 2) {
			Integer[] position = new Integer[2];
			position[0] = Integer.parseInt(Pos[i]);
			position[1] = Integer.parseInt(Pos[i + 1]);
			AlliesPos[i / 2] = position;
		}

		if (EthanY - 1 >= 0) {
			validMoves.add(Operator.LEFT);
		}
		if (EthanX - 1 >= 0) {
			validMoves.add(Operator.UP);
		}
		if (EthanY + 1 < n) {
			validMoves.add(Operator.RIGHT);
		}
		if (EthanX + 1 < m) {
			validMoves.add(Operator.DOWN);
		}
		// If ethan is standing on an ally and has capacity >0 and the ally is not
		// carried already
		for (int i = 0; i < health.length; i++) {
			if (EthanX == AlliesPos[i][0] && EthanY == AlliesPos[i][1] && Capacity > 0 && Carry[i].equals("0")) {
				validMoves.add(Operator.CARRY);
			}
			// if ethan is standing on the submarine and has a carried ally
			if (EthanX == SubX && EthanY == SubY && Carry[i].equals("1")) {
				validMoves.add(Operator.DROP);
				break;
			}

		}
		return validMoves;
	}

	public ArrayList<Ally> getAllies(Node inputNode) {
		// returns an array of ally objects, was an old implementation
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
				if (s.equals(("1"))) {
					Allies.get(Integer.parseInt(s)).setCarried(true);
				}
			}
		}
		if (droppedInput.size() > 0 && droppedInput.get(0).length() > 0) {
			for (String s : droppedInput) {
				if (s.equals(("1"))) {
					Allies.get(Integer.parseInt(s)).setDropped(true);
					Allies.get(Integer.parseInt(s)).setCarried(false);
				}

			}
		}
		return Allies;
	}

	@Override
	public Node getNextState(Node previousNode, Operator action) {
		// returns the new state after applying the operator
		String state = previousNode.getState();
		String[] split = state.split(";");
		String[] Dimensions = split[0].split(",");
		Integer m = Integer.parseInt(Dimensions[0]);
		Integer n = Integer.parseInt(Dimensions[1]);
		String[] Ethan = split[1].split(",");
		Integer EthanX = Integer.parseInt(Ethan[0]);
		Integer EthanY = Integer.parseInt(Ethan[1]);
		String[] Sub = split[2].split(",");
		Integer SubX = Integer.parseInt(Sub[0]);
		Integer SubY = Integer.parseInt(Sub[1]);
		String[] health = split[4].split(",");
		String[] Pos = split[3].split(",");
		String[] Carry = split[6].split(",");
		String[] Drop = split[7].split(",");
		Integer Capacity = Integer.parseInt(split[5]);
		Integer[][] AlliesPos = new Integer[Pos.length / 2][2];
		String PositionsText = "";
		String HealthText = "";
		String newState = "";
		for (int i = 0; i < Pos.length; i += 2) {
			Integer[] position = new Integer[2];
			position[0] = Integer.parseInt(Pos[i]);
			position[1] = Integer.parseInt(Pos[i + 1]);
			AlliesPos[i / 2] = position;
		}

		Node next = new Node(null, null, null, 0, 0);
		switch (action) {
		// move actions
		case LEFT:
			EthanY--;
			for (int i = 0; i < health.length; i++) {
				if (Carry[i].equals("1") && !Drop[i].equals("1")) {
					Integer[] pos = new Integer[2];
					pos[0] = EthanX;
					pos[1] = EthanY;
					AlliesPos[i] = pos;
				}
			}
			break;

		case UP:
			EthanX--;
			for (int i = 0; i < health.length; i++) {
				if (Carry[i].equals("1") && !Drop[i].equals("1")) {
					Integer[] pos = new Integer[2];
					pos[0] = EthanX;
					pos[1] = EthanY;
					AlliesPos[i] = pos;
				}
			}
			break;
		case RIGHT:
			EthanY++;
			for (int i = 0; i < health.length; i++) {
				if (Carry[i].equals("1") && !Drop[i].equals("1")) {
					Integer[] pos = new Integer[2];
					pos[0] = EthanX;
					pos[1] = EthanY;
					AlliesPos[i] = pos;
				}
			}
			break;
		case DOWN:
			EthanX++;
			for (int i = 0; i < health.length; i++) {
				if (Carry[i].equals("1") && !Drop[i].equals("1")) {
					Integer[] pos = new Integer[2];
					pos[0] = EthanX;
					pos[1] = EthanY;
					AlliesPos[i] = pos;
				}
			}
			break;
		case CARRY:

			for (int i = 0; i < health.length; i++) {
				if (EthanX == AlliesPos[i][0] && EthanY == AlliesPos[i][1] && Capacity > 0 && Drop[i].equals("0")
						&& Carry[i].equals("0")) {
					Carry[i] = "1";
					Capacity--;
				}
			}

			break;
		case DROP:
			for (int i = 0; i < health.length; i++) {
				if (EthanX == SubX && EthanY == SubY && Carry[i].equals("1") && Drop[i].equals("0")) {
					Drop[i] = "1";
					Carry[i] = "0";
					Capacity = initCapacity;
				}
			}
			break;
		}

		for (int i = 0; i < health.length; i++) {
			if (!Carry[i].equals("1") && !Drop[i].equals("1")) {
				String healthDamage = Integer.toString(Integer.parseInt(health[i]) + 2);
				health[i] = healthDamage;
			}
			if (Integer.parseInt(health[i]) > 100) {
				String healthDamage = Integer.toString(100);
				health[i] = healthDamage;
			}
			PositionsText = PositionsText + AlliesPos[i][0] + "," + AlliesPos[i][1] + ",";
			HealthText = HealthText + health[i] + ",";

		}
		PositionsText = PositionsText.replaceFirst(".$", "");
		HealthText = HealthText.replaceFirst(".$", "");
		newState = m + "," + n + ";" + EthanX + "," + EthanY + ";" + SubX + "," + SubY + ";" + PositionsText + ";"
				+ HealthText + ";" + Capacity + ";" + stringOut(Carry) + ";" + stringOut(Drop) + ";";

		next = new Node(newState, previousNode, action, 0, 0);
		if (game.Strategy == "UC" || game.Strategy == "GR1" || game.Strategy == "GR2" || game.Strategy == "AS1"
				|| game.Strategy == "AS2")
			next.setPathCost(getCost(previousNode, action, next));
		return next;

	}

	@Override
	public int getCost(Node start, Operator action, Node dest) {
		String state = dest.getState();
		String[] split = state.split(";");
		String[] health = split[4].split(",");
		String[] Drop = split[7].split(",");
		String[] Carry = split[6].split(",");

		int deadDest = 0;
		int damageDest = 0;
		for (int i = 0; i < health.length; i++) {
			if (Integer.parseInt(health[i]) != 100 && Drop[i].equals("0") && Carry[i].equals("0"))
				damageDest++;
			if (Integer.parseInt(health[i]) == 100) {
				deadDest++;
			}
		}
		Integer cost = ((deadDest * 2) + damageDest);
		return cost;
	}

	public void visualize(Node initNode, String plan) {
		ArrayList<String> Inputs = new ArrayList<>(Arrays.asList(initNode.getState().split(";")));
		ArrayList<String> Dimensions = new ArrayList<>(Arrays.asList(Inputs.get(0).split(",")));
		Integer rows = Integer.parseInt(Dimensions.get(0));
		Integer columns = Integer.parseInt(Dimensions.get(1));
		ArrayList<String> Ethan = new ArrayList<>(Arrays.asList(Inputs.get(1).split(",")));
		Integer EthanX = Integer.parseInt(Ethan.get(0));
		Integer EthanY = Integer.parseInt(Ethan.get(1));
		ArrayList<String> Sub = new ArrayList<>(Arrays.asList(Inputs.get(2).split(",")));
		Integer SubX = Integer.parseInt(Sub.get(0));
		Integer SubY = Integer.parseInt(Sub.get(1));
		ArrayList<Ally> Allies = getAllies(initNode);
		frame = new JFrame("Mission Impossible");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel = new JPanel(new GridLayout(rows, columns));
		frame.add(panel);

		JTextField[][] table = new JTextField[rows][columns];
		ArrayList<String> planInput = new ArrayList<>(Arrays.asList(plan.split(",")));

		for (int planStep = 0; planStep < planInput.size(); planStep++) {
			try {
				TimeUnit.MILLISECONDS.sleep(32000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(planInput.get(planStep));
			panel.removeAll();

			switch (planInput.get(planStep)) {
			// move actions
			case "left":
				EthanY--;
				for (int i = 0; i < Allies.size(); i++) {
					if (Allies.get(i).isCarried() == true && Allies.get(i).isDropped() == false) {
						Allies.get(i).setPos(EthanX, EthanY);

					}

					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();
				}

				break;
			case "up":
				EthanX--;
				for (int i = 0; i < Allies.size(); i++) {
					if (Allies.get(i).isCarried() == true && Allies.get(i).isDropped() == false) {
						Allies.get(i).setPos(EthanX, EthanY);

					}

					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();
				}

				break;

			case "right":
				EthanY++;
				for (int i = 0; i < Allies.size(); i++) {
					if (Allies.get(i).isCarried() == true && Allies.get(i).isDropped() == false) {
						Allies.get(i).setPos(EthanX, EthanY);

					}

					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();

				}

				break;

			case "down":
				EthanX++;
				for (int i = 0; i < Allies.size(); i++) {
					if (Allies.get(i).isCarried() == true && Allies.get(i).isDropped() == false) {
						Allies.get(i).setPos(EthanX, EthanY);

					}

					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();

				}
				break;
			// Carry and drop actions
			case "carry":

				for (int i = 0; i < Allies.size(); i++) {

					if (EthanX == Allies.get(i).getX() && EthanY == Allies.get(i).getY()
							&& Allies.get(i).isDropped() == false) {
						Allies.get(i).setCarried(true);
					}
					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();

				}
				break;
			case "drop":

				for (int i = 0; i < Allies.size(); i++) {

					if (EthanX == SubX && EthanY == SubY && Allies.get(i).isCarried() && !Allies.get(i).isDropped()) {
						Allies.get(i).setDropped(true);
						Allies.get(i).setCarried(false);
					}
					if (!Allies.get(i).isCarried() && !Allies.get(i).isDropped())
						Allies.get(i).damage();

				}
				break;

			}
			for (int i = 0; i < Allies.size(); i++) {
				if (Allies.get(i).isCarried() == true && Allies.get(i).isDropped() == false) {
					Allies.get(i).setPos(EthanX, EthanY);

				}
			}
			for (int m = 0; m < rows; m++) {
				for (int n = 0; n < columns; n++) {
					table[m][n] = new SquareTextField("   ");
					for (int i = 0; i < Allies.size(); i++) {
						if (Allies.get(i).getDamage() < 100)
							table[Allies.get(i).getX()][Allies.get(i).getY()] = new SquareTextField(
									" " + Integer.toString(Allies.get(i).getDamage()));
						else
							table[Allies.get(i).getX()][Allies.get(i).getY()] = new SquareTextField(
									Integer.toString(Allies.get(i).getDamage()));
					}
					table[EthanX][EthanY] = new SquareTextField("  E  ");
					table[SubX][SubY] = new SquareTextField("  S  ");

					panel.add(table[m][n]);
				}
			}

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

	class SquareTextField extends JTextField {

		int size = 50;

		SquareTextField(String s) {
			super(s);
			setFont(getFont().deriveFont((float) size));
			int sz = size / 3;
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

	@Override
	public String getOptimalRepresentation(Node state, String Strategy) {
		// compressing the state for performance by removing the hp part, submarine, dimensions
		String in = state.getState();
		String out = in.split(";")[1] + ";" + in.split(";")[6] + ";" + in.split(";")[7];
		return out.replace("100", "0");

	}

	// converting an array to a string
	public String stringOut(String[] st) {

		String out = "";
		for (String input : st) {
			out = out + "," + input;
		}
		out.replaceFirst(".$", "");
		return out.substring(1);
	}
}
