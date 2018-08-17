package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.List;

public class Grille {

	List<Ligne> lignesPos = new ArrayList<>();
	List<Ligne> lignesNeg = new ArrayList<>();

	List<Colonne> colonnesPos = new ArrayList<>();
	List<Colonne> colonnesNeg = new ArrayList<>();

	List<Case> cases = new ArrayList<>();

	public Grille(int[] clues) {
		Ligne currentLignePos;
		Ligne currentLigneNeg;
		Colonne currentColonneNeg;
		Colonne currentColonnePos;
		Trait trait1;
		Trait trait2;
		Trait[] traitTab = new Trait[6];
		for (int i = 0; i < 6; i++) {
			currentLignePos = new Ligne(i + 1);
			currentLigneNeg = new Ligne(-(i + 1));
			trait1 = new Trait(currentLigneNeg, currentLignePos);
			lignesPos.add(currentLignePos);
			lignesNeg.add(currentLigneNeg);
			for (int j = 0; j < 6; j++) {
				if (i == 0) {
					currentColonneNeg = new Colonne(j + 1);
					currentColonnePos = new Colonne(-(j + 1));
					trait2 = new Trait(currentColonneNeg, currentColonnePos);
					colonnesPos.add(currentColonneNeg);
					colonnesNeg.add(currentColonnePos);
					traitTab[j] = trait2;
				} else {
					trait2 = traitTab[j];
					currentColonneNeg = colonnesPos.get(j);
					currentColonnePos = colonnesNeg.get(j);
				}
				currentColonneNeg.grille = this;
				currentColonnePos.grille = this;
				currentLignePos.grille = this;
				currentLigneNeg.grille = this;
				Case c = new Case(trait1, trait2);
				trait1.addCase(c);
				trait2.addCase(c);
			}
		}
		for (int i = 0; i < 6; i++) {
			colonnesPos.get(i).affectNbVue(clues[i]);
			this.print();
		}
		for (int i = 0; i < 6; i++) {
			lignesPos.get(i).affectNbVue(clues[i + 6]);
			this.print();
		}
		for (int i = 5; i <= 0; i++) {
			colonnesNeg.get(5 - i).affectNbVue(clues[i + 12]);
			this.print();
		}
		for (int i = 5; i <= 0; i++) {
			lignesNeg.get(5 - i).affectNbVue(clues[i + 18]);
			this.print();
		}
	}
	

	public void print() {
		for (Ligne l : lignesPos) {
			for (Case c : l.trait.cases) {
				c.print();
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------");
	}
}
