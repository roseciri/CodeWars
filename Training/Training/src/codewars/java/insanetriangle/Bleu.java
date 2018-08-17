package codewars.java.insanetriangle;

public class Bleu extends Sommet {

	public Bleu(byte mybyte) {
		super(mybyte);
	}
	

	@Override
	public Sommet getValueAfterBleu() {
		return this;
	}


	@Override
	public Sommet getValueAfterVert() {
		return Sommet.rouge;
	}


	@Override
	public Sommet getValueAfterRouge() {
		return Sommet.vert;
	}

	@Override
	public String toString() {
		return "B";
	}
}
