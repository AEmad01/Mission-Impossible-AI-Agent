import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import java.awt.*;
import javax.swing.*;

public class MissonImpossible {
	JFrame frame;
	JPanel panel;
	public static void main(String args[]) {
		MissonImpossible x = new MissonImpossible();
		x.genGrid();
	}
	public void genGrid() {
		String PositionsText = "";
		String HealthText = "";
		frame = new JFrame("Mission Impossible");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Random r = new Random();
		int rows = r.nextInt(15 - 5) + 5;
		int columns = r.nextInt(15 - 5) + 5;

		int EthanX = r.nextInt(columns - 0) + 0;
		int EthanY = r.nextInt(rows - 0) + 0;

		int SubX = r.nextInt(columns - 0) + 0;
		int SubY = r.nextInt(rows - 0) + 0;

		Integer Capacity = r.nextInt(10 - 1) + 1;

		int AllyCount = r.nextInt(10 - 5) + 5;

		ArrayList<Ally> Allies = new ArrayList<Ally>(); // Create an ArrayList object
		//
		// System.out.println(EthanX);
		// System.out.println(EthanY);

		for (int i = 0; i < AllyCount; i++) {
			int Health = r.nextInt(99 - 2) + 2;
			int X = r.nextInt(columns - 0) + 0;
			int Y = r.nextInt(rows - 0) + 0;

			while (overlapCheck(X, Y, Allies, EthanX, EthanY, SubX, SubY)) {
				X = r.nextInt(columns - 0) + 0;
				Y = r.nextInt(rows - 0) + 0;
			}
			Allies.add(new Ally(Health, X, Y));

		}
		for (int i = 0; i < AllyCount; i++) {
			// System.out.println(Allies.get(i).toString());
		}
		panel = new JPanel(new GridLayout(rows, columns));
		frame.add(panel);
		// panel.setVisible(true); //unnecessary

		JTextField[][] table = new JTextField[rows][columns];

		for (int m = 0; m < rows; m++) {
			for (int n = 0; n < columns; n++) {

				table[m][n] = new SquareTextField("");
				// table[m][n] = new SquareTextField(n + " " + m);
				table[EthanY][EthanX] = new SquareTextField("E");
				table[SubY][SubX] = new SquareTextField("S");

				for (int i = 0; i < Allies.size(); i++) {
					table[Allies.get(i).getY()][Allies.get(i).getX()] = new SquareTextField(
							Integer.toString(Allies.get(i).getDamage()));
				}
				panel.add(table[m][n]);
			}
		}

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		for (int i = 0; i < Allies.size(); i++) {
			PositionsText = PositionsText + Integer.toString(Allies.get(i).getX()) + ","
					+ Integer.toString(Allies.get(i).getY())+",";
			HealthText = HealthText + Integer.toString(Allies.get(i).getDamage()) + ",";
		}
		HealthText = HealthText.replaceFirst(".$", "");

//		solve(Integer.toString(rows) + ',' + Integer.toString(columns) + ";" + Integer.toString(EthanX) + ','
//				+ Integer.toString(EthanY) + ";" + Integer.toString(SubX) + ',' + Integer.toString(SubY) + ";"
//				+ PositionsText + ";" + HealthText + ";" + Capacity
//
//		);
		Node start = new Node(Integer.toString(rows) + ',' + Integer.toString(columns) + ";" + Integer.toString(EthanX) + ','
				+ Integer.toString(EthanY) + ";" + Integer.toString(SubX) + ',' + Integer.toString(SubY) + ";"
				+ PositionsText + ";" + HealthText + ";" + Capacity+ ";;;;", null, null, 0, 0);
		//start = new Node("5,5;1,2;4,0;0,3,2,1,3,0,3,2,3,4,4,3;20,30,90,80,70,60;3;;;;",null,null,0,0);
		MissionImpossibleProblem problem = new MissionImpossibleProblem(start);
		//
		


		
		
		//problem.solve("BFS");
		//problem.solve("DFS");
		problem.solve("BFS");


	}
	public boolean overlapCheck(int X, int Y, ArrayList<Ally> Allies, int EthanX, int EthanY, int SubX, int SubY) {
		boolean flag = false;
		for (int i = 0; i < Allies.size(); i++) {
			if ((Allies.get(i).getX() == X && Allies.get(i).getY() == Y) || (EthanX == X && EthanY == Y)
					|| (SubX == X && SubY == Y)) {
				flag = true;
				System.out.println("overlap");
			}
		}
		return flag;
	}
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
