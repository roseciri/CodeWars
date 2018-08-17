package codewars.java.insanetriangle;

public class Rouge extends Sommet {

	public Rouge(byte mybyte) {
		super(mybyte);
	}

	@Override
	public Sommet getValueAfterBleu() {
		return Sommet.vert;
	}


	@Override
	public Sommet getValueAfterVert() {
		return Sommet.bleu;
	}


	@Override
	public Sommet getValueAfterRouge() {
		return this;
	}

	@Override
	public String toString() {
		return "R";
	}

}
