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
	
	private static char getSommet(Character a, Character b , Character c) {
		List<Character> list = tab.get(a);
		List<Character> list2 = tab.get(b);
		int indexOf = list2.indexOf(c);
		return list.get(indexOf);
	}
	static private byte[][] triangle = new byte[83][83];
	static private byte rouge = 0b1010010;
	static private byte vert = 0b1000111;
	static private byte bleu = 0b1000010;
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
		int length = row.length();
		if(length == 1) {
		    return row.charAt(0);
		    }
		if(length == 3) {
			return getSommet(row.charAt(0), row.charAt(1), row.charAt(2));
		} else if(row.length() == 2) {
			return (char) triangle[row.charAt(0)][row.charAt(1)];
		} else {
			return getSommet(triangle(row.substring(0, length-1)), triangle(row.substring(1, length-1)),triangle( row.substring(1, length)));
		}
	}


	public static void main(String[] args) {

		 if('G'== Kata.triangle("RBRGBRB")){
			 System.out.println("OK");
		 };
		 if('G'== Kata.triangle("RBRGBRBGGRRRBG")){
			 System.out.println("OK");
		 };
	}
}
