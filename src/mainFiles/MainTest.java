package mainFiles;
import mainFiles.Board;
import mainFiles.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

	private Board board;
	private GameBoard gameBoard;

	@BeforeEach
	public void setUp() {
		board = new Board(3);
		gameBoard = new GameBoard(board, 3);
	}

	@Test
	public void testGetCell() {
		assertEquals(0, board.getCell(0, 0));
		assertEquals(-1, board.getCell(-1, 0));
		assertEquals(-1, board.getCell(0, 3));
	}

	@Test
	public void testGetTurn() {
		assertEquals('S', board.getTurn());
	}

	@Test
	public void testMakeMove() {
		board.makeMove(0, 0);
		assertEquals(1, board.getCell(0, 0));
		assertEquals('O', board.getTurn());

		board.makeMove(0, 1);
		assertEquals(2, board.getCell(0, 1));
		assertEquals('S', board.getTurn());

		// Attempt to make a move on an already occupied space
		board.makeMove(0, 0);
		assertEquals(1, board.getCell(0, 0)); // The cell should still be occupied by the first move
		assertEquals('S', board.getTurn()); // The turn should not change
	}

	@Test
	public void testGameBoardInitialization() {
		assertNotNull(gameBoard.getBoard());
		assertEquals(0, gameBoard.getBoard().getCell(0, 0));
	}

}
