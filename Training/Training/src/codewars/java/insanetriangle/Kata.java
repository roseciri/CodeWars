package codewars.java.insanetriangle;


public class Kata {

	static int cpt;

	static int i;

	static byte precedent;

	static byte current;

	static byte[] line;

	public static char triangle(final String row) {
		System.out.println(row);
		line = row.getBytes();
		for (cpt = 1; cpt < line.length; cpt++) {
			System.out.println("");
			Sommet s = Sommet.getFirst(line[cpt]);
			for (i = cpt; i > 0; i--) {
				s = s.next(line, i-1, line[i - 1]);
				System.out.print((char)line[i - 1]);
			}
		}
		return (char) line[0];
	}
	
	public static void main(String[] args) {
		Kata.triangle("RGBG");
	}
}
