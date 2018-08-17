package codewars.java.insanetriangle;

public class Vert extends Sommet {

	public Vert(byte mybyte) {
		super(mybyte);
	}


	@Override
	public Sommet getValueAfterBleu() {
		return Sommet.rouge;
	}


	@Override
	public Sommet getValueAfterVert() {
		return this;
	}


	@Override
	public Sommet getValueAfterRouge() {
		return  Sommet.bleu;
	}

	@Override
	public String toString() {
		return "G";
	}
}
