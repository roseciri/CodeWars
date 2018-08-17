package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trait {

	Droite droite1;
	Droite droite2;
	
	List<Integer> values;
	public List<Case> cases = new ArrayList<>();
	
	public Trait(Droite droite1, Droite droite2) {
		super();
		this.droite1 = droite1;
		droite1.setTrait(this);
		this.droite2 = droite2;
		droite2.setTrait(this);
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
		for (Case caseItem : cases) {
			if (c != caseItem) {
				caseItem.removeValue(v, this);
			}
		}
		values.remove(new Integer(v));
		droite1.calculNbVue();
		droite2.calculNbVue();
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
				cases.get(0).setValue(entry.getKey());
			}
		}
	}
}
