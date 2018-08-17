package codewars.java.skycrapers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
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
		values = new ArrayList<>(values);
		Collections.reverse(values);
		values.forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer v) {
				removeImpossibleValues(v);
				// System.out.println("\t[Doite.calcul()] Recherche des orhpelins sur la grille");
				// grille.print();
				trait.setOrphelin();

			}
		});

	}

	private void removeImpossibleValues(int value) {
		int nbVueMax = nbVue;
		int i = 0;
		while (i < 6 && nbVueMax < nbVueNeed) {
			int[] tab = getTab();
			tab[i] = value;
			List<Integer> possibleValues = new ArrayList<>(trait.values);
			possibleValues.remove(new Integer(value));
			fillForMaxVue(tab, possibleValues);
			nbVueMax = calculNbVue(tab);
			if (nbVueMax < nbVueNeed) {
				Case cases = trait.getCases(i, this);
				System.out.println("\t[Doite.calcul()]Je ne peux pas mettre la valeur " + value + " dans la case " + cases);
				cases.removeValue(value, trait);
				i++;
			} else {
				break;
			}
		}
	}

	private void fillForMaxVue(int[] tab, List<Integer> possibleValues) {
		int maxPossibleValues = Collections.max(possibleValues);
		List<Integer> notPossibleValues = new ArrayList<Integer>();
		notPossibleValues.add(1);
		notPossibleValues.add(2);
		notPossibleValues.add(3);
		notPossibleValues.add(4);
		notPossibleValues.add(5);
		notPossibleValues.add(6);
		notPossibleValues.remove(possibleValues);
		int maxDansTabRestant = Collections.max(notPossibleValues);
		for (int i = 5; i >= 0; i--) {
			if (maxDansTabRestant > maxPossibleValues && tab[i] != maxDansTabRestant) {
				continue;
			} else if (tab[i] == maxDansTabRestant) {
				notPossibleValues.remove(new Integer(maxDansTabRestant));
				maxDansTabRestant = Collections.max(notPossibleValues);
			} else if (maxPossibleValues > maxDansTabRestant && tab[i] == 0) {
				tab[i] = maxPossibleValues;
				possibleValues.remove(new Integer(maxDansTabRestant));
				maxPossibleValues = Collections.max(possibleValues);
			}
		}

	}

	private int getMaxPossibleValue(int maxTemp, List<Integer> possibleValues, int indexCase) {
		final Droite d = this;
		List<Integer> intersect = possibleValues.stream().filter(new Predicate<Integer>() {

			@Override
			public boolean test(Integer t) {
				if (t >= maxTemp)
					return false;
				if (trait.getCases(indexCase, d).possibleValue != null)
					return trait.getCases(indexCase, d).possibleValue.contains(t);
				return false;
			}
		}).collect(Collectors.toList());
		if (intersect.isEmpty())
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
		System.out.println("\t[Doite.calculNbVue()]" + this + "calcule le nb de vue");
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
