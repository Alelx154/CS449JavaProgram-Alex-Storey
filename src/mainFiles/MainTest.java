package mainFiles;
import mainFiles.CommonFunctions;
import mainFiles.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

	private CommonFunctions board;
	private GameBoard gameBoard;
	private SimpleGame simpleGame;
	private GeneralGame generalGame;

	@BeforeEach
	void setUpSimpleGame() {
		simpleGame = new SimpleGame(3, 'S', 'O') {
			@Override
			public boolean checkSOS() {
				return false;
			}
		};
	}

	@Test
	void testMakeMove() {
		simpleGame.makeMove(0, 0);
		assertEquals(1, simpleGame.getCell(0, 0));
		assertEquals('O', simpleGame.getTurn());

		simpleGame.makeMove(0, 1);
		assertEquals(2, simpleGame.getCell(0, 1));
		assertEquals('S', simpleGame.getTurn());
	}

	@Test
	void testMakeMoveSimpleGame() {
		simpleGame.makeMove(0, 0);
		assertEquals(1, simpleGame.getCell(0, 0));
		assertEquals('O', simpleGame.getTurn());

		simpleGame.makeMove(0, 1);
		assertEquals(2, simpleGame.getCell(0, 1));
		assertEquals('S', simpleGame.getTurn());
	}

	@Test
	void testCheckSOS() {
		simpleGame.makeMove(0, 0);
		simpleGame.makeMove(0, 1);
		simpleGame.makeMove(0, 2);
		assertTrue(simpleGame.checkWin());
	}

	@BeforeEach
	void setUpGeneralGame() {
		generalGame = new GeneralGame(3, 'S', 'O') {
			@Override
			public boolean checkSOS() {
				return false;
			}
			@Override
			public boolean checkTie(){
				return false;
			}
		};
	}

	@Test
	void testMakeMoveGeneralGame() {
		generalGame.makeMove(0, 0);
		assertEquals(1, generalGame.getCell(0, 0));
		assertEquals('O', generalGame.getTurn());

		generalGame.makeMove(0, 1);
		assertEquals(2, generalGame.getCell(0, 1));
		assertEquals('S', generalGame.getTurn());
	}

	@Test
	void testCheckSOSGeneralGame() {
		generalGame.makeMove(0, 0);
		generalGame.makeMove(0, 1);
		generalGame.makeMove(0, 2);
		assertTrue(generalGame.checkSOS(0, 2));

		generalGame.player1Score = 0;
	}

	@Test
	void testGetPlayer1Score() {
		assertEquals(0, GeneralGame.getPlayer1Score());
	}

	@Test
	void testGetPlayer2Score() {
		assertEquals(0, GeneralGame.getPlayer2Score());
	}

	@BeforeEach
	void setUpCommonFunctions() {
		board = new GeneralGame(3, 'S', 'O') {
			@Override
			public boolean checkSOS() {
				return false;
			}
			@Override
			public boolean checkTie(){
				return false;
			}
		};
	}

	@Test
	void testGetCell() {
		assertEquals(0, board.getCell(0, 0));
		board.makeMove(0, 0);
		assertEquals(1, board.getCell(0, 0));
	}

	@Test
	void testGetTurn() {
		assertEquals('S', board.getTurn());
		board.makeMove(0, 0);
		assertEquals('O', board.getTurn());
	}

	@Test
	void testMakeMoveCommonFunctions() {
		board.makeMove(0, 0);
		assertEquals(1, board.getCell(0, 0));
		assertEquals('O', board.getTurn());

		board.makeMove(0, 1);
		assertEquals(2, board.getCell(0, 1));
		assertEquals('S', board.getTurn());
	}
}
