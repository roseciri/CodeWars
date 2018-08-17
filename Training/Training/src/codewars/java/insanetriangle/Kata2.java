package codewars.java.insanetriangle;

public class Kata2 {
	static short cpt;
	static short i;
	static byte[] line = new byte[1000];
	static byte[][] triangle = new byte[83][83];
	static byte rouge = 0b1010010;
	static byte vert = 0b1000111;
	static byte bleu = 0b1000010;
	static{
		triangle[rouge][rouge]= rouge;
		triangle[rouge][vert ]= bleu;
		triangle[rouge][bleu ]= vert;
		triangle[bleu][rouge]= vert;
		triangle[bleu][vert ]= rouge;
		triangle[bleu][bleu ]= bleu ;
		triangle[vert][rouge]= bleu ;
		triangle[vert][vert ]= vert;
		triangle[vert][bleu ]= rouge;
	};

	public static char triangle(final String row) {
		for (cpt = 0; cpt < row.length(); cpt++) {
			line[i] = (byte) row.charAt(cpt);
			for (i = cpt; i > 0; i--) {
				line[i - 1] = triangle[line[i]][line[i - 1]];
			}
		}
		return (char) line[0];
	}

	public static void main(String[] args) {
		Kata2.triangle("RGBG");
	}
}
