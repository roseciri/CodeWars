package codewars.java.checkandmate;

public class PieceConfig {
	private String piece;

	private int owner;

	private int x;

	private int y;

	private boolean hasPrevious;

	private int prevX;

	private int prevY;

	public PieceConfig(String piece, int owner, int x, int y) {
		super();
		this.piece = piece;
		this.owner = owner;
		this.x = x;
		this.y = y;
	}

	public PieceConfig(String piece, int owner, int x, int y, int prevX, int prevY) {
		super();
		this.piece = piece;
		this.owner = owner;
		this.x = x;
		this.y = y;
		this.prevX = prevX;
		this.prevY = prevY;
	}

	public String getPiece() {
		return piece;
	}

	public int getOwner() {
		return owner;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean hasPrevious() {
		return hasPrevious;
	}

	public int getPrevX() {
		return prevX;// will throw a RuntimeException if invoked for an object that do not have informations about its previous move.
	}

	public int getPrevY() {
		return prevY;// will throw a RuntimeException if invoked for an object that do not have informations about its previous move.
	}
}
