package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.List;

public class Case {
	
	public int value;
	Trait trait1,  trait2;
	
	public List<Integer> possibleValue = new ArrayList<>();
	
	public Case(Trait trait1, Trait trait2) {
		this.trait1 = trait1;
		this.trait2 = trait2;
		possibleValue.add(1);
		possibleValue.add(2);
		possibleValue.add(3);
		possibleValue.add(4);
		possibleValue.add(5);
		possibleValue.add(6);
	}

	public void setValue(int v) {
		value = v;
		possibleValue = null;
		trait1.valueIsAdded(this, v);
		trait2.valueIsAdded(this, v);
	}

	public void removeValue(int v, Trait t) {
		if(possibleValue == null)
			return;
		possibleValue.remove(Integer.valueOf(v));
		if(possibleValue.size()== 1) {
			setValue(possibleValue.get(0));
		}
		if(trait1 == t) {
			trait2.setOrphelin();
		} else {
			trait1.setOrphelin();
		}
	}

	
	public void print() {
		if(value == 0) {
			for (int i = 1; i <7; i++) {
				if(possibleValue.contains(Integer.valueOf(i))) {
					System.out.print(i);
				} else {
					System.out.print("X");
				}
			}
		} else {
			System.out.print("   ["+value+"]");
		}
	}


	@Override
	public String toString() {
		return "Case [" + trait1 + "," + trait2 + "]";
	}

	
	
}
