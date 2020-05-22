package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			pawnMoviment(getModificador(true), p, mat);
			// #specialmove en passant white
			whitePassant(mat);
		}
		else {
			pawnMoviment(getModificador(false), p, mat);
			// #specialmove en passant black
			blackPassant(mat);
		}
		return mat;
	}

	private void pawnMoviment(int modificador, Position p, boolean[][] mat){
		p.setValues(position.getRow() - modificador, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		p.setValues(position.getRow() - (2 * modificador), position.getColumn());
		Position p2 = new Position(position.getRow() - modificador, position.getColumn());
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		p.setValues(position.getRow() - modificador, position.getColumn() - modificador);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		p.setValues(position.getRow() - modificador, position.getColumn() + modificador);
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
	}

	private int getModificador(boolean isWhite) {
		if(isWhite){
			return 1;
		}else {
			return -1;
		}
	}

	private void whitePassant(boolean[][] mat){
		if (position.getRow() == 3) {
			passant(getModificador(true), mat);
		}
	}

	private void blackPassant(boolean[][] mat){
		if (position.getRow() == 4) {
			passant(getModificador(false), mat);
		}
	}

	private void passant(int modificador, boolean[][] mat){
		Position left = new Position(position.getRow(), position.getColumn() - (modificador));
		if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
			mat[left.getRow() + 1][left.getColumn()] = true;
		}
		Position right = new Position(position.getRow(), position.getColumn() + (modificador));
		if (getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
			mat[right.getRow() + 1][right.getColumn()] = true;
		}
	}

	@Override
	public String toString() {
		return "P";
	}
	
}