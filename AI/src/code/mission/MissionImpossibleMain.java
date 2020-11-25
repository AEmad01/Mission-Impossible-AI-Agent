package code.mission;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import generic.Node;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.*;
import javax.swing.*;
import code.mission.*;

public class MissionImpossibleMain {
	JFrame frame;
	JPanel panel;

	public static void main(String args[]) {
		MissionImpossibleMain x = new MissionImpossibleMain();
		x.genGrid();
	}

	public static ArrayList<Ally> getAllies(Node inputNode) {
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
				Allies.get(Integer.parseInt(s)).setCarried(true);
			}
		}
		if (droppedInput.size() > 0 && droppedInput.get(0).length() > 0) {
			for (String s : droppedInput) {
				Allies.get(Integer.parseInt(s)).setDropped(true);
				Allies.get(Integer.parseInt(s)).setCarried(false);

			}
		}

		return Allies;
	}


	public void genGrid() {
		String PositionsText = "";
		String HealthText = "";
		frame = new JFrame("Mission Impossible");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Random r = new Random();
		int rows = 5;
		int columns = 6;

		int EthanX = r.nextInt(rows - 0) + 0;
		int EthanY = r.nextInt(columns - 0) + 0;

		int SubX = r.nextInt(rows - 0) + 0;
		int SubY = r.nextInt(columns - 0) + 0;

		Integer Capacity = r.nextInt(10 - 1) + 1;

		int AllyCount = r.nextInt(10 - 5) + 5;

		ArrayList<Ally> Allies = new ArrayList<Ally>(); // Create an ArrayList object

		for (int i = 0; i < AllyCount; i++) {
			int Health = r.nextInt(99 - 2) + 2;
			int X = r.nextInt(rows - 0) + 0;
			int Y = r.nextInt(columns - 0) + 0;

			while (overlapCheck(X, Y, Allies, EthanX, EthanY, SubX, SubY)) {
				X = r.nextInt(rows - 0) + 0;
				Y = r.nextInt(columns - 0) + 0;
			}
			Allies.add(new Ally(Health, X, Y));

		}

		for (int i = 0; i < Allies.size(); i++) {
			PositionsText = PositionsText + Integer.toString(Allies.get(i).getX()) + ","
					+ Integer.toString(Allies.get(i).getY()) + ",";
			HealthText = HealthText + Integer.toString(Allies.get(i).getDamage()) + ",";
		}
		HealthText = HealthText.replaceFirst(".$", "");
		String state = Integer.toString(rows) + ',' + Integer.toString(columns) + ";" + Integer.toString(EthanX)
		+ ',' + Integer.toString(EthanY) + ";" + Integer.toString(SubX) + ',' + Integer.toString(SubY) + ";"
		+ PositionsText + ";" + HealthText + ";" + Capacity;
		
		
		state = state + ";";
		for (int i = 0; i < Allies.size(); i++) {

			state = state + 0 + ",";
		}
		state = state.replaceFirst(".$", "");
		state = state + ";";
		for (int i = 0; i < Allies.size(); i++) {

			state = state + 0 + ",";
		}
		state = state.replaceFirst(".$", "");
		state = state + ";";
		Node start = new Node( state, null, null, 0, 0);
		System.out.println(start.getState());
		String plan = MissionImpossible.solve(start.getState(), "UC", true);
	}

	public boolean overlapCheck(int X, int Y, ArrayList<Ally> Allies, int EthanX, int EthanY, int SubX, int SubY) {
		boolean flag = false;
		for (int i = 0; i < Allies.size(); i++) {
			if ((Allies.get(i).getX() == X && Allies.get(i).getY() == Y) || (EthanX == X && EthanY == Y)
					|| (SubX == X && SubY == Y)) {
				flag = true;
			}
		}
		return flag;
	}


}
