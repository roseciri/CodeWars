package codewars.java.insanetriangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kata {
	static private Map<Character, List<Character>> tab = new HashMap<>();
	static {
		List<Character> ord = new ArrayList<>();
		ord.add('G');
		ord.add('B');
		ord.add('R');
		tab.put('R', ord);
		ord = new ArrayList<>();
		ord.add('B');
		ord.add('R');
		ord.add('G');
		tab.put('G', ord);
		ord = new ArrayList<>();
		ord.add('R');
		ord.add('G');
		ord.add('B');
		tab.put('B', ord);
	}

	private static char getSommet(Character a, Character b, Character c) {
		List<Character> list = tab.get(a);
		List<Character> list2 = tab.get(b);
		int indexOf = list2.indexOf(c);
		return list.get(indexOf);
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

	public static char triangle(final String row) {
		System.out.println("Je traite " + row);
		int length = row.length();
		if (length == 1) {
			System.out.println("J'ai une chaine de 1, value : " + row.charAt(0));
			return row.charAt(0);
		}
		if (length == 3) {
			System.out.println("J'ai une chaine de 3, getSommet result : " + getSommet(row.charAt(0), row.charAt(1), row.charAt(2)));
			return getSommet(row.charAt(0), row.charAt(1), row.charAt(2));
		} /*
			 * else if (row.length() == 2) { System.out.println("J'ai une chaine de 2, je rends le 2 : " + (char)
			 * triangle[row.charAt(0)][row.charAt(1)]); return row.charAt(1); }
			 */ else {
			int mid = Math.abs(row.length() / 2);
			System.out.println("Je coupe en 2  (" + row.substring(0, mid + 1) + ", " + row.substring(mid, length) + ")");
			String triangle1Str = row.substring(0, mid + 1);
			char triangle1;
			if (triangle1Str.length() > 2) {
				triangle1 = triangle(triangle1Str);
				System.out.println("La solution du triangle 1 (" + triangle1 + ")");
			} else {
				triangle1 = triangle1Str.charAt(0);
				System.out.println("La solution du triangle 1 , on fait descendre le char (" + triangle1 + ")");
			}

			System.out.println("Le caractère du milieu  (" + getSommet(row.charAt(mid-1), row.charAt(mid), row.charAt(mid+1))  + ")");
			String triangle2Str = row.substring(mid, length);
			char triangle2;
			if (triangle2Str.length() > 2) {
				triangle2 = triangle(triangle2Str);
				System.out.println("La solution du triangle2 (" + triangle2 + ")");
			} else {
				triangle2 = triangle2Str.charAt(1);
				System.out.println("La solution du triangle 2 , on fait descendre le char (" + triangle2 + ")");
			}
			char sommet = getSommet(triangle1, row.charAt(mid), triangle2);
			System.out.println("La solution du triangle  (" + row + ") = " + sommet);
			return sommet;
		}
	}

	public static void main(String[] args) {
		System.out.println("Calcul de RBRGBRB");
		if ('G' == Kata.triangle("RBRGBRB")) {
			System.out.println("OK");
		}
		;
		System.out.println("Calcul de RBRGBRBGGRRRBG");
		if ('G' == Kata.triangle("RBRGBRBGGRRRBG")) {
			System.out.println("OK");
		}
		;
	}
}
