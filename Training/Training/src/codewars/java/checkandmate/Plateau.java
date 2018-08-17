package codewars.java.checkandmate;

import java.util.*;

public class Plateau {
	static int cpt = 0;
	Map<Integer, Player> map = new HashMap<>();

	Player noir = new Player();

	Player blanc = new Player();

	CaseCheck[][] cases = new CaseCheck[8][8];

	PieceConfig[] origin;

	public Plateau(PieceConfig[] arrPieces) {
		cpt = cpt++;
		map.put(0, blanc);
		map.put(1, noir);
		this.origin = arrPieces;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				cases[i][j] = new CaseCheck(i, j);
			}

		}
		for (int i = 0; i < arrPieces.length; i++) {
			PieceCheck p = PieceCheckFactory.get(arrPieces[i]);
			p.plateau = this;
			if (arrPieces[i].getOwner() == 1) {
				donnePieceAuJoueur(p, noir);
			} else {
				donnePieceAuJoueur(p, blanc);
			}
			placePieceSurCase(p, cases[arrPieces[i].getX()][arrPieces[i].getY()]);
		}

	}

	public boolean isMate(int player) {
		Player def = map.get(player);
		if (isCheck(player).isEmpty()) {
			return false;
		} else {
		}
		for (Action action  : def.getKing().canGetTo())
			if(!simulateDefenseOption(player, def.getKing(), action))
			return false;
		for (PieceCheck piece : def.pieces) {
			Set<Action> canGetTo = piece.canGetTo();
			for (Action action : canGetTo) {				
				if(!simulateDefenseOption(player, piece, action))
					return false;
			}
		}
		return true;
	}

	private boolean simulateDefenseOption(int player, PieceCheck piece, Action action) {	
		Plateau simulation = this.copy();
		action.execute(this);		
		if (simulation.isCheck(player).isEmpty()) {
			return false;
		}
		return true;
	}

	public void mangePiece(PieceCheck piecemangee) {
			CaseCheck piecemangeecase = this.cases[piecemangee.position.x ][piecemangee.position.y] ;
			PieceCheck piecemanagee = piecemangeecase.piece;
			prendrePieceAuJoueur(piecemanagee);
			sortirPiecefromCase(piecemanagee);
	}

	public void movePiece(CaseCheck depart, CaseCheck arrive) {
		PieceCheck piece = this.cases[depart.x][depart.y].piece;
		CaseCheck caseChecks = this.cases[arrive.x][arrive.y];
		sortirPiecefromCase(piece);
		placePieceSurCase(piece, caseChecks);
	}

	private void sortirPiecefromCase(PieceCheck piece) {
		CaseCheck c = piece.position;
		c.piece = null;
		piece.position = null;

	}

	private void prendrePieceAuJoueur(PieceCheck piece) {
		piece.player.removePiece(piece);
	}

	private Plateau copy() {
		Plateau p = new Plateau(origin);
		return p;
	}

	private void placePieceSurCase(PieceCheck p, CaseCheck c) {
		c.piece = p;
		p.position = c;
	}

	private void donnePieceAuJoueur(PieceCheck p, Player j) {
		p.player = j;
		j.addPiece(p);
	}

	public Set<PieceConfig> isCheck(int player) {
		Player att = map.get((player + 1) % 2);
		Player def = map.get(player);
		return att.getPiecesCanGetTo(def.getKing().getPosition());
	}

	public CaseCheck getCaseFrom(CaseCheck caseCheck, int i, int j) {
		int nextX = caseCheck.x + i;
		int nextY = caseCheck.y + j;
		if (nextX < 8 && nextY < 8 && nextX >= 0 && nextY >= 0)
			return cases[nextX][nextY];
		return null;

	}

}

class PieceCheckFactory {

	public static PieceCheck get(PieceConfig piece) {
		CaseCheck position = new CaseCheck(piece.getX(), piece.getY());
		if ("king".equals(piece.getPiece())) {
			return new Roi(position, piece);
		} else if ("knight".equals(piece.getPiece())) {
			return new Cheval(position, piece);
		} else if ("bishop".equals(piece.getPiece())) {
			return new Fou(position, piece);
		} else if ("queen".equals(piece.getPiece())) {
			return new Reine(position, piece);
		} else if ("pawn".equals(piece.getPiece())) {
			int nbdeplacement = piece.hasPrevious() ? Math.abs(piece.getPrevY()-piece.getY()) : 0;
			if (piece.getOwner() == 1) {
				return new Pion(position, piece, piece.getY() == 1, 1, nbdeplacement == 2 );
			} else {
				return new Pion(position, piece, piece.getY() == 6, -1, nbdeplacement == 2 );
			}

		} else if ("rook".equals(piece.getPiece())) {
			return new Tour(position, piece);
		}

		return null;
	}
}

class Player {

	Roi king;

	List<PieceCheck> pieces = new ArrayList<PieceCheck>();

	public List<PieceCheck> getPieces() {
		return pieces;
	}

	public void removePiece(PieceCheck piece) {
		pieces.remove(piece);
	}

	public void addPiece(PieceCheck p) {
		if (p instanceof Roi) {
			king = Roi.class.cast(p);
		} else {
			pieces.add(p);
		}

	}

	public Set<PieceConfig> getPiecesCanGetTo(CaseCheck caseCheck) {
		Set<PieceConfig> dangerousPiecesConfig = new HashSet<PieceConfig>();
		for (PieceCheck p : pieces) {
			for (Action action : p.canGetTo()) {
				if (action.nextCase.equals(caseCheck)) {
					dangerousPiecesConfig.add(p.getPieceConfig());
					break;
				}
			}
		}
		return dangerousPiecesConfig;
	}

	Roi getKing() {
		return king;
	}

	@Override
	public String toString() {
		return "Player [king=" + king + ", pieces=" + pieces + "]";
	}
}

class CaseCheck {

	public PieceCheck piece;

	public int x;

	public int y;

	CaseCheck(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static boolean exist(int x, int y) {
		return x < 8 && y < 8 && x >= 0 && y >= 0;
	}

	@Override
	public String toString() {
		return x + ":" + y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CaseCheck other = (CaseCheck) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public boolean hasPieceAutreJoueur(Player player) {
		return !piece.player.equals(player);
	}
}

abstract class PieceCheck {

	public Player player;

	public Plateau plateau;

	protected CaseCheck position;

	PieceConfig config;

	PieceCheck(CaseCheck position, PieceConfig config) {
		this.position = position;
		this.config = config;
	}

	public CaseCheck getPosition() {
		return position;
	}

	public abstract Set<Action> canGetTo();

	public PieceConfig getPieceConfig() {
		return config;
	}

}

class Cheval extends PieceCheck {

	Cheval(CaseCheck position, PieceConfig config) {
		super(position, config);
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		addToListIfMoveOK(afterMoveCase, 1, 2);
		addToListIfMoveOK(afterMoveCase, 1, -2);
		addToListIfMoveOK(afterMoveCase, -1, 2);
		addToListIfMoveOK(afterMoveCase, -1, -2);
		addToListIfMoveOK(afterMoveCase, 2, -1);
		addToListIfMoveOK(afterMoveCase, 2, 1);
		addToListIfMoveOK(afterMoveCase, -2, -1);
		addToListIfMoveOK(afterMoveCase, -2, 1);
		return afterMoveCase;
	}

	private void addToListIfMoveOK(Set<Action> afterMoveCase, int i, int j) {
		CaseCheck c = plateau.getCaseFrom(this.position, i, j);
		if (c != null && (c.piece == null || c.hasPieceAutreJoueur(this.player))) {
			if(c.piece == null) {
				afterMoveCase.add(new Action(this,c));
			} else {
				afterMoveCase.add(new ActionMangee(this,c));
			}
		} else {
		}
	}

	@Override
	public String toString() {
		return "Cheval.(" + position + ")";
	}
}

class Roi extends PieceCheck {

	Roi(CaseCheck position, PieceConfig config) {
		super(position, config);
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		addToListIfMoveOK(afterMoveCase, 1, 0);
		addToListIfMoveOK(afterMoveCase, 1, 1);
		addToListIfMoveOK(afterMoveCase, 0, 1);
		addToListIfMoveOK(afterMoveCase, -1, 1);
		addToListIfMoveOK(afterMoveCase, -1, 0);
		addToListIfMoveOK(afterMoveCase, -1, -1);
		addToListIfMoveOK(afterMoveCase, 0, -1);
		addToListIfMoveOK(afterMoveCase, 1, -1);
		return afterMoveCase;
	}

	private void addToListIfMoveOK(Set<Action> afterMoveCase, int i, int j) {
		CaseCheck c = plateau.getCaseFrom(this.position, i, j);
		if (c != null && (c.piece == null || c.hasPieceAutreJoueur(this.player))) {
			if(c.piece == null) {
				afterMoveCase.add(new Action(this,c));
			} else {
				afterMoveCase.add(new ActionMangee(this,c));
			}
		}

	}

	@Override
	public String toString() {
		return "Roi.(" + position + ")";
	}
}

class Reine extends PieceCheck {

	Reine(CaseCheck position, PieceConfig config) {
		super(position, config);
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		addToListIfMoveOK(afterMoveCase, 1, 0);
		addToListIfMoveOK(afterMoveCase, 1, 1);
		addToListIfMoveOK(afterMoveCase, 0, 1);
		addToListIfMoveOK(afterMoveCase, -1, 1);
		addToListIfMoveOK(afterMoveCase, -1, 0);
		addToListIfMoveOK(afterMoveCase, -1, -1);
		addToListIfMoveOK(afterMoveCase, 0, -1);
		addToListIfMoveOK(afterMoveCase, 1, -1);
		return afterMoveCase;
	}

	private void addToListIfMoveOK(Set<Action> afterMoveCase, int i, int j) {
		CaseCheck next = plateau.getCaseFrom(this.position, i, j);
		while (next != null) {
			if (next.piece == null) {
				afterMoveCase.add(new Action(this,next));
				next = plateau.getCaseFrom(next, i, j);
			} else if (next.hasPieceAutreJoueur(player)) {
				afterMoveCase.add(new ActionMangee(this,next));
				next = null;
			} else {
				next = null;
			}
		}
	}

	@Override
	public String toString() {
		return "Reine.(" + position + ")";
	}
}

class Tour extends PieceCheck {

	Tour(CaseCheck position, PieceConfig config) {
		super(position, config);
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		addToListIfMoveOK(afterMoveCase, 1, 0);
		addToListIfMoveOK(afterMoveCase, 0, 1);
		addToListIfMoveOK(afterMoveCase, -1, 0);
		addToListIfMoveOK(afterMoveCase, 0, -1);
		return afterMoveCase;
	}

	private void addToListIfMoveOK(Set<Action> afterMoveCase, int i, int j) {
		CaseCheck next = plateau.getCaseFrom(this.position, i, j);
		while (next != null) {
			if (next.piece == null) {
				afterMoveCase.add(new Action(this,next));
				next = plateau.getCaseFrom(next, i, j);
			} else if (next.hasPieceAutreJoueur(player)) {
				afterMoveCase.add(new ActionMangee(this,next));
				next = null;
			} else {
				next = null;
			}
		}
	}

	@Override
	public String toString() {
		return "Tour.(" + position + ")";
	}
}

class Pion extends PieceCheck {

	boolean originalPlace;
	boolean fromOriginalplace;

	private int multiplicateur;


	public Pion(CaseCheck position, PieceConfig piece, boolean originalPlace, int multiplicateur, boolean fromOriginalplace){
		super(position, piece);
		this.originalPlace = originalPlace;
		this.multiplicateur = multiplicateur;
		this.fromOriginalplace= fromOriginalplace;
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		CaseCheck next = plateau.getCaseFrom(this.position, 0, 1 * multiplicateur);
		if (next != null &&next.piece == null || !next.hasPieceAutreJoueur(player)) {
			afterMoveCase.add(new Action(this,next));
		}
		if (originalPlace) {
			next = plateau.getCaseFrom(this.position, 0, 2 * multiplicateur);
			if (next != null && next.piece == null || next.hasPieceAutreJoueur(player)) {
				afterMoveCase.add(new Action(this,next));
			}
		}
		next = plateau.getCaseFrom(this.position, 1, 1 * multiplicateur);
		if (next != null && next.piece != null && next.hasPieceAutreJoueur(player)) {
			afterMoveCase.add(new ActionMangee(this,next));
		}
		next = plateau.getCaseFrom(this.position, -1, 1 * multiplicateur);
		if (next != null && next.piece != null && next.hasPieceAutreJoueur(player)) {
			afterMoveCase.add(new ActionMangee(this,next));
		}
		CaseCheck adjacent = plateau.getCaseFrom(this.position, 1, 0);
		if(adjacent != null && adjacent.piece!= null) {
			if(adjacent.piece instanceof Pion) {
				Pion otherPion = Pion.class.cast(adjacent.piece);
				if(otherPion.fromOriginalplace) {
					CaseCheck nextCase = plateau.getCaseFrom(this.position, 1, 1 * multiplicateur);
					afterMoveCase.add(new ActionMangee(this,nextCase, otherPion) );
				}
			}
		};
		adjacent = plateau.getCaseFrom(this.position, -1, 0);
		if(adjacent != null && adjacent.piece!= null) {
			if(adjacent.piece instanceof Pion) {
				Pion otherPion = Pion.class.cast(adjacent.piece);
				if(otherPion.fromOriginalplace) {
					CaseCheck nextCase = plateau.getCaseFrom(this.position, -1, 1 * multiplicateur);
					afterMoveCase.add(new ActionMangee(this,nextCase, otherPion) );
				}
			}
		}
		return afterMoveCase;
	}

	@Override
	public String toString() {
		return "Pion.(" + position + ")";
	}
}

class Fou extends PieceCheck {

	Fou(CaseCheck position, PieceConfig config) {
		super(position, config);
	}

	public Set<Action> canGetTo() {
		Set<Action> afterMoveCase = new HashSet<Action>();
		addToListIfMoveOK(afterMoveCase, 1, 1);
		addToListIfMoveOK(afterMoveCase, -1, 1);
		addToListIfMoveOK(afterMoveCase, -1, -1);
		addToListIfMoveOK(afterMoveCase, 1, -1);
		return afterMoveCase;
	}

	private void addToListIfMoveOK(Set<Action> afterMoveCase, int i, int j) {
		CaseCheck next = plateau.getCaseFrom(this.position, i, j);
		while (next != null) {
			if (next.piece == null) {
				afterMoveCase.add(new Action(this, next));
				next = plateau.getCaseFrom(next, i, j);
			} else if (next.hasPieceAutreJoueur(player)) {
				 afterMoveCase.add(new ActionMangee(this, next));
				next = null;
			} else {
				next = null;
			}
		}
	}

	@Override
	public String toString() {
		return "Fou.(" + position + ")";
	}
}

class Action {
	public CaseCheck nextCase;
	public PieceCheck piecemangee;
	public PieceCheck pieceadeplacer;
	
	public Action(PieceCheck pieceadeplacer, CaseCheck nextCase) {
		super();
		this.pieceadeplacer = pieceadeplacer;
		this.nextCase = nextCase;
	}

	public void execute(Plateau plateau) {
		plateau.movePiece(pieceadeplacer.position, nextCase);
	}
	
	
}

class ActionMangee extends Action {
	
	public ActionMangee(PieceCheck pieceadeplacer, CaseCheck nextCase, PieceCheck piecemangee) {
		super(pieceadeplacer, nextCase);
		this.piecemangee = piecemangee;
	}
	
	public ActionMangee(PieceCheck pieceadeplacer, CaseCheck nextCase) {
		super(pieceadeplacer, nextCase);
		this.piecemangee = nextCase.piece;
	}
	
	public void execute(Plateau plateau) {
		plateau.mangePiece(piecemangee);
		super.execute(plateau);
	}
	
}