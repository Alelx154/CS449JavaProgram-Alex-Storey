package TestCode;

import mainFiles.SimpleGame;
import mainFiles.HumanPlayer;
import mainFiles.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleGameTest {
    private SimpleGame game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        player1 = new HumanPlayer("Player 1");
        player2 = new HumanPlayer("Player 2");
        game = new SimpleGame(3, player1, player2);
    }

    @Test
    public void testInitialGameState() {
        assertEquals(player1, game.getTurn());
        assertFalse(game.checkWin());
        assertFalse(game.checkTie());
    }

    @Test
    public void testHorizontalSOSWin() {
        // Create a horizontal SOS pattern
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(0, 1);
        player1.setSymbol('S');
        game.makeMove(0, 2);
        
        assertTrue(game.checkWin());
    }

    @Test
    public void testVerticalSOSWin() {
        // Create a vertical SOS pattern
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(1, 0);
        player1.setSymbol('S');
        game.makeMove(2, 0);
        
        assertTrue(game.checkWin());
    }

    @Test
    public void testDiagonalSOSWin() {
        // Create a diagonal SOS pattern
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(1, 1);
        player1.setSymbol('S');
        game.makeMove(2, 2);
        
        assertTrue(game.checkWin());
    }

    @Test
    public void testGameTie() {
        // Fill the board without creating an SOS pattern
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('S');
        game.makeMove(0, 2);
        player1.setSymbol('S');
        game.makeMove(1, 0);
        player2.setSymbol('S');
        game.makeMove(1, 2);
        player1.setSymbol('S');
        game.makeMove(2, 0);
        player2.setSymbol('S');
        game.makeMove(2, 2);
        player1.setSymbol('S');
        game.makeMove(0, 1);
        player2.setSymbol('S');
        game.makeMove(2, 1);
        player1.setSymbol('S');
        game.makeMove(1, 1);
        
        assertTrue(game.checkTie());
        assertFalse(game.checkWin());
    }

    @Test
    public void testInvalidMove() {
        // Try to make a move in an occupied cell
        player1.setSymbol('S');
        game.makeMove(0, 0);
        
        // Try to make a move in the same cell
        assertThrows(IllegalArgumentException.class, () -> {
            player2.setSymbol('O');
            game.makeMove(0, 0);
        });
    }
}