package codewars.java.skycrapers;

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
		if (nbVueNeed != 0) {
			int nbfreeCase = 0;
			int i = 0;
			while (i < 6 && trait.cases.get(i).possibleValue != null) {
				nbfreeCase++;
				i++;
			}
			if (nbfreeCase >= nbVueNeed) {
				i = 0;
				while (i < nbVueNeed-1) {
					trait.cases.get(i).removeValue(getMaxValue(), trait);
					i++;
				}
			}
			trait.setOrphelin();
		}
	}
	private int getMaxValue() {
		int max = 0;
		for (Integer v : trait.values) {
			max= Math.max(max, v);
		}
		return max;
	}


	void calculNbVue() {
		for(int i = 0; i < 5 ; i++) {
			if(trait.cases.get(i).value > trait.cases.get(i+1).value) {
				nbVue++;
			}
		}
		calcul();
	}

	public Trait getTrait() {
		return trait;
	}

	public void setTrait(Trait trait) {
		this.trait = trait;
	}

	
}
