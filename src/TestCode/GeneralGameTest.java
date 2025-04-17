package TestCode;

import mainFiles.GeneralGame;
import mainFiles.HumanPlayer;
import mainFiles.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeneralGameTest {
    private GeneralGame game;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setUp() {
        player1 = new HumanPlayer("Player 1");
        player2 = new HumanPlayer("Player 2");
        game = new GeneralGame(3, player1, player2);
    }

    @Test
    public void testInitialGameState() {
        assertEquals(player1, game.getTurn());
        assertEquals(0, player1.score);
        assertEquals(0, player2.score);
        assertFalse(game.checkTie());
    }

    @Test
    public void testScoring() {
        // Test horizontal SOS
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(0, 1);
        player1.setSymbol('S');
        game.makeMove(0, 2);
        
        assertEquals(1, player1.score);
        assertEquals(0, player2.score);
    }

    @Test
    public void testConsecutiveTurnsAfterSOS() {
        // Player 1 makes an SOS, should get another turn
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(0, 1);
        player1.setSymbol('S');
        game.makeMove(0, 2);
        
        // Should still be player 1's turn
        assertEquals(player1, game.getTurn());
    }

    @Test
    public void testGameTie() {
        // Fill the board without any SOS patterns
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
        assertEquals(0, player1.score);
        assertEquals(0, player2.score);
    }

    @Test
    public void testMultipleSOSPatterns() {
        // Test creating multiple SOS patterns in one move
        player1.setSymbol('S');
        game.makeMove(0, 0);
        player2.setSymbol('O');
        game.makeMove(0, 1);
        player1.setSymbol('S');
        game.makeMove(0, 2);
        player2.setSymbol('O');
        game.makeMove(1, 1);
        player1.setSymbol('S');
        game.makeMove(2, 0);
        player2.setSymbol('O');
        game.makeMove(2, 1);
        player1.setSymbol('S');
        game.makeMove(2, 2);
        
        // Should have created at least one SOS pattern
        assertTrue(player1.score > 0);
    }
}