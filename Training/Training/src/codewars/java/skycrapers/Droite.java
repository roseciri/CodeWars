package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Droite {

	public Grille grille;

	public Integer nbVue = 0;

	public Integer nbVueNeed = 0;

	protected int numero;

	Trait trait;

	public void affectNbVue(int nbVue) {
		nbVueNeed = nbVue;
		calcul();
	}

	private void calcul() {
			List<Integer> values = trait.values;
			for (Integer v : new ArrayList<>(values)) {
				removeImpossibleValues(v);
			}
			trait.setOrphelin();
	}

	private void removeImpossibleValues(int value) {
		int nbVueMax = nbVue;
		int i = 0;
		while (i < 6 && nbVueMax < nbVueNeed) {
			int[] tab = getTab();
			tab[i] = value;
			fillForMaxVue(tab);
			nbVueMax = calculNbVue(tab);
			if(nbVueMax < nbVueNeed) {
				Case cases = trait.getCases(i, this);
				System.out.println("\t[Doite.calcul()]Je ne peux pas mettre la valeur " + value + " dans la case " + cases);
				cases.removeValue(value, trait);
				i++;
			} else {
				break;
			}
		}
	}

	private void fillForMaxVue(int[] tab) {
		int maxTemp = 0;
		List<Integer> possibleValues = new ArrayList<>(trait.values);
		for(int i = 5 ; i >= 0 ; i-- ) {
			if(possibleValues.contains(6)) {
				tab[i] = 6;
				maxTemp = 6;
				possibleValues.remove(new Integer(6));
				continue;
			} else if(tab[i] == 6) {
				maxTemp = 6;
				continue;
			} else if(maxTemp == 0) {
				continue;
			}
			if(tab[i] == 0) {
				tab[i] = getMaxPossibleValue(maxTemp , possibleValues, i);
				maxTemp = tab[i];
				possibleValues.remove(new Integer(maxTemp));
			} else {
				maxTemp = Math.min(maxTemp, tab[i]);
			}
		}
		
	}

	private int getMaxPossibleValue(int maxTemp, List<Integer> possibleValues, int indexCase) {
		final Droite d = this;
		List<Integer> intersect = possibleValues.stream()
			    .filter(new Predicate<Integer>() {

					@Override
					public boolean test(Integer t) {
						if(t >= maxTemp)
							return false;
						if(trait.getCases(indexCase, d).possibleValue != null)
							return trait.getCases(indexCase, d).possibleValue.contains(t);
						return false;
					}
				})
			    .collect(Collectors.toList());
		if(intersect.isEmpty())
			return 0;
		return Collections.max(intersect);
	}

	private int getMaxValue() {
		int max = 0;
		for (Integer v : trait.values) {
			max = Math.max(max, v);
		}
		return max;
	}

	void calculNbVue() {
		System.out.println(
				"\t[Doite.calculNbVue()]" + this + "calcule le nb de vue");
		int nbVueTemp = calculNbVue(getTab());
		if (nbVueTemp != nbVue) {	
			nbVue = nbVueTemp;
			System.out.println(
					"\t[Doite.calculNbVue()]" + this + " mets à jour le nombre de vue " + nbVue + " et je remets a jour les valeurs possible");
			calcul();
		}
	}
	
	private int[] getTab() {
		int[] tab = new int[6];
		for (int i = 0; i < tab.length; i++) {
			int value = trait.getCases(i, this).value;
			tab[i] = value != Integer.MAX_VALUE ? value : 0;
		}
		return tab;
	}

	private int calculNbVue(int[] tab) {
		int nbVueTemp = 0;
		int lastMax = 0;
		for (int i = 0; i < 6; i++) {
			if (tab[i] > lastMax) {
				nbVueTemp++;
				lastMax = tab[i];
			}
		}
		return nbVueTemp;
	}
	


	public Trait getTrait() {
		return trait;
	}

	public void setTrait(Trait trait) {
		this.trait = trait;
	}

}
