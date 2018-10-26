package codewars.java.insanetriangle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Kata {

	  public static char triangle(final String row) {
	   List<Integer> triangleTailles = new ArrayList<>();
	   triangleTailles.add(0);
	   return getTriangleSolution(row, triangleTailles, row.length()).charAt(0);
	  }
	  
	  private static String getTriangleSolution(String chaine, List<Integer> triangleTaille, int sizeNewChaine) {
			if(sizeNewChaine == 1) {
				return String.valueOf((char) getValue(0,  chaine, triangleTaille, triangleTaille.size() - 1)) ;
			} else {
				int taillePlusGrandTriangle = getTaillePlusGrandTriangle(sizeNewChaine);
				triangleTaille.add(taillePlusGrandTriangle);
				return getTriangleSolution(chaine, triangleTaille, sizeNewChaine - (taillePlusGrandTriangle - 1));
			}
		}
		
		private static char getValue(int index,  String chaine, List<Integer> triangleTailles, int indexTaille) {
			int triangleTaille = triangleTailles.get(indexTaille);
			if(triangleTaille == 0) {
				return chaine.charAt(index);
			}
			char debut = getValue(index,  chaine,  triangleTailles, indexTaille-1);
			char fin = getValue(index + (triangleTaille-1),  chaine,  triangleTailles, indexTaille-1); 
			return (char) triangle[debut][fin];
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


		private static int getTaillePlusGrandTriangle(int chaineTaille) {
			return new BigDecimal(3).pow(getExposant(chaineTaille)).add(BigDecimal.ONE).intValueExact();
		}
		
		private static int getExposant(int length) {
			return new BigDecimal(Math.log(length-1) / Math.log(3)).intValue();
		}
	  
	}
