package codewars.java.insanetriangle;

import java.math.BigDecimal;

public class Kata2 {
	
	public static void main(String[] args) {
		System.out.println( getTriangleSolution("RBRGBRBGGRRRBG"));
	}


	private static String getTriangleSolution(String chaine) {
		System.out.println(chaine);
		int taillePlusGrandTriangle = getTaillePlusGrandTriangle(chaine.length());
		if(taillePlusGrandTriangle == chaine.length()) {
			return String.valueOf((char) triangle[chaine.charAt(0)][chaine.charAt(chaine.length()-1)]) ;
		}
		int chaineTaille = chaine.length() - taillePlusGrandTriangle + 1;
		int frequence = getTaillePlusGrandTriangle(chaineTaille);
		String newChaine = calculNewChaine(chaine, taillePlusGrandTriangle, frequence);
		return getTriangleSolution(newChaine);
	}

	static private byte[][] triangle = new byte[83][83];

	static private byte rouge = 0b1010010;

	static private byte vert = 0b1000111;

	static private byte bleu = 0b1000010;
	
	static {
		triangle[rouge][rouge] = rouge;
		triangle[rouge][vert] = bleu;
		triangle[rouge][bleu] = vert;
		triangle[bleu][rouge] = vert;
		triangle[bleu][vert] = rouge;
		triangle[bleu][bleu] = bleu;
		triangle[vert][rouge] = bleu;
		triangle[vert][vert] = vert;
		triangle[vert][bleu] = rouge;
	};

	private static String calculNewChaine(String chaine, int taillePlusGrandTriangle, int frequence) {
		StringBuilder result = new StringBuilder();
		int debut = 0;
		int fin = taillePlusGrandTriangle-1;
		while(fin <= chaine.length()-1) {
			result.append((char) triangle[chaine.charAt(debut)][chaine.charAt(fin)]); 
			debut += frequence -1;
			fin += frequence - 1;
		}
		return result.toString();
	}

	private static int getTaillePlusGrandTriangle(int chaineTaille) {
		return new BigDecimal(3).pow(getExposant(chaineTaille)).add(BigDecimal.ONE).intValueExact();
	}
	
	private static int getExposant(int length) {
		return new BigDecimal(Math.log(length-1) / Math.log(3)).intValue();
	}

}
