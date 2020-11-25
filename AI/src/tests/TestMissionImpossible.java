package tests;
import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import code.mission.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestMissionImpossible {
	String grid5 = "5,5;2,1;1,0;1,3,4,2,4,1,3,1;54,31,39,98;2";
	String grid6 = "6,6;1,1;3,3;3,5,0,1,2,4,4,3,1,5;4,43,94,40,92;3";
	String grid7 = "7,7;1,6;5,4;2,2,1,4,0,3,2,3,0,1,4,5;6,44,82,49,24,54;4";
	String grid8 = "8,8;4,2;7,4;5,1,7,7,4,0,6,7;93,85,72,78;1";
	String grid9 = "9,9;8,7;5,0;0,8,2,6,5,6,1,7,5,5,8,3,2,2,2,5,0,7;11,13,75,50,56,44,26,77,18;2";
	String grid10 = "10,10;6,3;4,8;9,1,2,4,4,0,3,9,6,4,3,4,0,5,1,6,1,9;97,49,25,17,94,3,96,35,98;3";
	String grid11 = "11,11;7,7;8,8;9,7,7,4,7,6,9,6,9,5,9,1,4,5,3,10,5,10;14,3,96,89,61,22,17,70,83;5";
	String grid12 = "12,12;7,7;10,6;0,4,2,2,1,3,8,2,4,2,9,3;95,4,68,2,94,91;5";
	String grid13 = "13,13;7,4;4,0;9,3,3,9,12,7,7,9,3,12,11,8,4,2,12,6;22,62,74,56,43,70,17,14;4";
	String grid14 = "14,14;13,9;1,13;5,3,9,7,11,10,8,3,10,7,13,6,11,1,5,2;76,30,2,49,63,43,72,1;6";
	String grid15 = "15,15;5,10;14,14;0,0,0,1,0,2,0,3,0,4,0,5,0,6,0,7,0,8;81,13,40,38,52,63,66,36,13;1";
	

	@Test(timeout = 70000)
	public void BF() throws Exception {
		String solution = MissionImpossible.solve(grid6, "AS2", true);
		solution = solution.replace(" ", "");
		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid6, solution));
	}
//	@Test(timeout = 70000)
//	public void DF() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "DF", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	
//	@Test(timeout = 70000)
//	public void ID() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "ID", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	
//	@Test(timeout = 70000)
//	public void UC() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "UC", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	
//	@Test(timeout = 70000)
//	public void GR1() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "GR1", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	
//	@Test(timeout = 70000)
//	public void GR2() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "GR2", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	
//	@Test(timeout = 70000)
//	public void AS1() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "AS1", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
//	@Test(timeout = 70000)
//	public void AS2() throws Exception {
//		String solution = MissionImpossible.solve(grid10, "AS2", false);
//		solution = solution.replace(" ", "");
//		assertTrue("The output actions do not lead to a goal state.", applyPlan(grid10, solution));
//	}
	
	



	private boolean applyPlan(String grid, String solution) {
		char[][] g = convertToGrid(grid);
		String plan = solution.split(";")[0];
		plan.replace(" ", "");
		plan.replace("\n", "");
		plan.replace("\r", "");
		plan.replace("\n\r", "");
		plan.replace("\t", "");
		String[] actions = plan.split(",");
		String[] gridArray=  grid.split(";");
		String[] ethan = gridArray[1].split(",");
		String submarine = gridArray[2];
		String capacity = gridArray[5];
		int c = Integer.parseInt(capacity);
		int membersNum  = (gridArray[3].split(",").length)/2;
		int[] result = new int[4];
		result[0] = Integer.parseInt(ethan[0]);
		result[1] = Integer.parseInt(ethan[1]);
		result[2] = c;
		result[3] = membersNum;
		for (int i = 0; i < actions.length; i++) {
			switch (actions[i]) {
			case "up":
				applyUp(g, result);
				break;
			case "down":
				applyDown(g, result);
				break;
			case "right":
				applyRight(g, result);
				break;
			case "left":
				applyLeft(g, result);
				break;
			case "carry":
				applyCarry(g, result);
				break;
			case "drop":
				applyDrop(g, result, c);
				break;
			}
		}
		return done(submarine,result);
	}
	
	private boolean done(String submarine, int[] result) {
		return (result[0] + "," + result[1]).equals(submarine) && result[3] == 0;
	}

	private void applyDrop(char[][] g, int[] result, int capacity) {
		if (g[result[0]][result[1]] == 'S') {
			result[2]=capacity;
		}
	}


	private void applyCarry(char[][] g, int[] result) {
		if (g[result[0]][result[1]] == 'M' && result[2]>0) {
			g[result[0]][result[1]] = '\u0000';
			result[2]--;
			result[3]--;
		}
	}

	private void applyLeft(char[][] g, int[] result) {
		if (result[1] - 1 >= 0)
			result[1]--;
	}

	private void applyRight(char[][] g, int[] result) {
		int n = g[0].length;
		if (result[1] + 1 < n)
			result[1]++;
	}

	private void applyDown(char[][] g, int[] result) {
		int m = g.length;
		if (result[0] + 1 < m)
			result[0]++;

	}

	private void applyUp(char[][] g, int[] result) {
		if (result[0] - 1 >= 0)
			result[0]--;
	}

	private char[][] convertToGrid(String input) {
		String[] s = input.split(";");

		String[] dimensions = s[0].split(",");
		String[] submarine = s[2].split(",");
		String[] members = s[3].split(",");

		char[][] grid = new char[Integer.parseInt(dimensions[0])][Integer.parseInt(dimensions[1])];
		grid[Integer.parseInt(submarine[0])][Integer.parseInt(submarine[1])] = 'S';

		for (int i = 0; i < members.length - 1; i += 2)
			grid[Integer.parseInt(members[i])][Integer.parseInt(members[i + 1])] = 'M';


		return grid;
	}

}