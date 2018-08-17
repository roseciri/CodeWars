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
			trait1 = new Trait( currentLignePos, currentLigneNeg);
			lignesPos.add(currentLignePos);
			lignesNeg.add(currentLigneNeg);
			for (int j = 0; j < 6; j++) {
				if (i == 0) {
					currentColonneNeg = new Colonne(-(j + 1));
					currentColonnePos = new Colonne((j + 1));
					trait2 = new Trait( currentColonnePos, currentColonneNeg);
					colonnesPos.add(currentColonnePos);
					colonnesNeg.add(currentColonneNeg);
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
		
		int cptclues = 0;
		for (int i = 0; i < 6; i++) {
			System.out.println("Colonne : " + colonnesPos.get(i)+" - Nombre de Vue "+clues[cptclues]);
			colonnesPos.get(i).affectNbVue(clues[cptclues++]);
			this.print();
		}
		for (int i = 0; i < 6; i++) {
			System.out.println("Ligne : " + lignesNeg.get(i)+" - Nombre de Vue "+clues[cptclues]);
			lignesNeg.get(i).affectNbVue(clues[cptclues++]);
			this.print();
		}
		for (int i = 5; i >= 0; i--) {
			System.out.println("Colonne : " + colonnesNeg.get(i)+" - Nombre de Vue "+clues[cptclues]);
			colonnesNeg.get(i).affectNbVue(clues[cptclues++]);
			this.print();
		}
		for (int i = 5; i >= 0; i--) {
			System.out.println("Ligne : " + lignesPos.get(i)+" - Nombre de Vue "+clues[cptclues]);
			lignesPos.get(i).affectNbVue(clues[cptclues++]);
			this.print();
		}
	}
	

	public void print() {
		for (Ligne l : lignesPos) {
			for (Case c : l.trait.getCases()) {
				c.print();
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------");
	}
}
