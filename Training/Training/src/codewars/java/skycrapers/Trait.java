package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trait {

	Droite positive;
	Droite negative;
	
	List<Integer> values;
	private List<Case> cases = new ArrayList<>();
	
	public Trait(Droite positive, Droite negative) {
		super();
		this.positive = positive;
		positive.setTrait(this);
		this.negative = negative;
		negative.setTrait(this);
		values = new ArrayList<>();
		values.add(1);
		values.add(2);
		values.add(3);
		values.add(4);
		values.add(5);
		values.add(6);
	}

	public void addCase(Case c) {
		cases.add(c);
	}
	
	public void valueIsAdded(Case c, int v) {
		System.out.println("\tUne valeur a été ajouté sur le trait "+this);
		for (Case caseItem : cases) {
			if (c != caseItem) {
				caseItem.removeValue(v, this);
			}
		}
		values.remove(new Integer(v));
		positive.calculNbVue();
		negative.calculNbVue();
		setOrphelin();
	}
	
	private void addToMap(Map<Integer, List<Case>> nbByNumber, Integer value, Case caseItem) {
		List<Case> cases = nbByNumber.get(value);
		if (cases == null) {
			cases = new ArrayList<>();
			nbByNumber.put(value, cases);
		}
		cases.add(caseItem);
	}

	public void setOrphelin() {
		Map<Integer, List<Case>> nbByNumber = new HashMap<>();
		for (Case caseItem : cases) {
			if (caseItem.possibleValue == null)
				continue;
			for (Integer value : caseItem.possibleValue) {
				addToMap(nbByNumber, value, caseItem);
			}
		}
		for (Map.Entry<Integer, List<Case>> entry : nbByNumber.entrySet()) {
			List<Case> cases = entry.getValue();
			if (cases.size() == 1) {
				System.out.println("\tLa valeur "+entry.getKey()+" ne peut pas être que sur la case "+cases.get(0));
				cases.get(0).setValue(entry.getKey());
			}
		}
	}
	
	@Override
	public String toString() {
		if(positive instanceof Ligne)
			return "Horizontal ["+Math.abs( positive.numero)+"]";
		else
			return "Vertical ["+Math.abs( positive.numero)+"]";
	}

	public Case getCases(int i, Droite droite) {
		if(droite == positive) {
			return cases.get(i);
		} else {
			return cases.get(5-i);
		}
	}

	public List<Case> getCases() {
		return cases;
	}
}
