package code.mission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import generic.Node;

public class Greedy1Comparator implements Comparator<Node> {
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

	public int compare(Node s1, Node s2) {
		Node start = s1;
		Node dest = s2;
		ArrayList<Ally> AlliesStart = getAllies(start);
		ArrayList<Ally> AlliesDest = getAllies(dest);
		int damageStart = 0;
		int damageEnd = 0;
		for (Ally ally : AlliesStart) {
			if(ally.isDropped()==false && ally.isCarried()==false)
			damageStart+=ally.getDamage();
		}
		for (Ally ally : AlliesDest) {
			if(ally.isDropped()==false && ally.isCarried()==false)
			damageEnd+=ally.getDamage();
		}
		return damageStart-damageEnd;
		

	}
}
