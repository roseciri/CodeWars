package codewars.java.skycrapers;

public class Ligne extends Droite {


	public Ligne(int numero) {
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "L [" + numero + "]";
	}
	
}
