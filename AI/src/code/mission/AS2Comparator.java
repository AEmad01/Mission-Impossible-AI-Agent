package code.mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import generic.Node;

public class AS2Comparator implements Comparator<Node> {
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

	public int getTotalCost(Node s) {
		if (s.getParent() == null)
			return s.getPathCost();
		return s.getPathCost() + getTotalCost(s.getParent());
	}

	public int compare(Node s1, Node s2) {

		ArrayList<Ally> allies1 = getAllies(s1);
		ArrayList<Ally> allies2 = getAllies(s2);
		Integer maxDmg1=0;
		Integer maxDmg2=0;
		Integer wrongCarry1=0;
		Integer wrongCarry2=0;
		for (Ally ally : allies2) {
			if (ally.getDamage()>maxDmg2)
				maxDmg2=ally.getDamage();
			if (ally.isCarried()&&ally.getDamage()!=maxDmg2)
				wrongCarry2++;
		
		}
		for (Ally ally : allies1) {
			if (ally.getDamage()>maxDmg1)
				maxDmg1=ally.getDamage();
			if (ally.isCarried()&&ally.getDamage()!=maxDmg1)
				wrongCarry1++;
		
		}

		return (wrongCarry1  + s1.totalCost()) - (wrongCarry2  + s2.totalCost());

	}
}
