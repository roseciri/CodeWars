package codewars.java.insanetriangle;

public abstract class Sommet {

	
	public byte mybyte;
	static final Sommet rouge = new Rouge((byte) 0b1010010);
	static final Sommet bleu = new Bleu((byte) 0b1000010);
	static final Sommet vert = new Vert((byte) 0b1000111);
	
	
	public Sommet(byte mybyte) {
		this.mybyte= mybyte;
	}
	
	public Sommet next(byte[] result, int indexAvalorise, byte next) {
		Sommet nextSommet;
		switch (next) {
			case 0b1010010://R
				nextSommet = getValueAfterRouge();
				result[indexAvalorise] = nextSommet.mybyte;
				return  nextSommet;
			case 0b1000111://V
				nextSommet = getValueAfterVert();
				result[indexAvalorise] = nextSommet.mybyte;
				return  nextSommet;
			default://B
				nextSommet = getValueAfterBleu();
				result[indexAvalorise] = nextSommet.mybyte;
				return  nextSommet;
		}
	}

	static Sommet getFirst(byte first) {
		switch (first) {
			case 0b1010010:
				return  rouge;
			case 0b1000111:
				return  vert;
			default:
				return  bleu;
		}
	}

	public abstract Sommet getValueAfterBleu();
	public abstract Sommet getValueAfterVert();
	public abstract Sommet getValueAfterRouge();
}
