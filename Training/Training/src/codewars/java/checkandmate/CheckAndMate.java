package codewars.java.checkandmate;

import java.util.*;

public class CheckAndMate {
	public static Set<PieceConfig> isCheck(final PieceConfig[] arrPieces, int player) {
		Plateau plateau = new Plateau(arrPieces);
		return plateau.isCheck(player);
	}

	public static boolean isMate(final PieceConfig[] arrPieces, int player) {
		Plateau plateau = new Plateau(arrPieces);
		return plateau.isMate(player);
	}

	public static void main(String[] args) {
		new CheckAndMate().test_isCheck_PawnThreatensKing();
	}

	public void test_isCheck_PawnThreatensKing() {
		PieceConfig[] game = new PieceConfig[] { 
				new PieceConfig("king", 1, 5, 3), 
				new PieceConfig("rook", 1, 5, 2), 
				new PieceConfig("bishop", 1, 4, 2), 
				new PieceConfig("knight", 1, 3, 3), 
				new PieceConfig("pawn", 1, 3, 4), 
				
				new PieceConfig("king", 0, 4, 7),
				new PieceConfig("queen", 0, 6, 5),
				new PieceConfig("knight", 0, 2, 5),
				new PieceConfig("pawn", 0, 4, 4),
				new PieceConfig("pawn", 0, 5, 6)};
		CheckAndMate.isMate(game, 1);
	}
}




