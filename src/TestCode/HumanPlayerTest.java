package TestCode;

import mainFiles.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {
    private HumanPlayer humanPlayer;
    private int[][] testGrid;

    @BeforeEach
    public void setUp() {
        humanPlayer = new HumanPlayer("Player");
        testGrid = new int[3][3];
    }

    @Test
    public void testInitialState() {
        assertEquals("Player", humanPlayer.getName());
        assertEquals(0, humanPlayer.score);
    }

    @Test
    public void testSetSymbol() {
        humanPlayer.setSymbol('S');
        assertEquals('S', humanPlayer.getSymbol());
        
        humanPlayer.setSymbol('O');
        assertEquals('O', humanPlayer.getSymbol());
    }

    @Test
    public void testSetInvalidSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.setSymbol('X');
        });
    }

    @Test
    public void testMakeValidMove() {
        humanPlayer.setSymbol('S');
        humanPlayer.makeMove(testGrid, 0, 0);
        assertEquals(1, testGrid[0][0]); // S is represented as 1
    }

    @Test
    public void testMakeMoveWithO() {
        humanPlayer.setSymbol('O');
        humanPlayer.makeMove(testGrid, 0, 0);
        assertEquals(2, testGrid[0][0]); // O is represented as 2
    }

    @Test
    public void testMakeInvalidMove() {
        // Try to make a move in an occupied cell
        humanPlayer.setSymbol('S');
        humanPlayer.makeMove(testGrid, 0, 0);
        
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.makeMove(testGrid, 0, 0);
        });
    }

    @Test
    public void testMakeMoveOutOfBounds() {
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.makeMove(testGrid, -1, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.makeMove(testGrid, 0, -1);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.makeMove(testGrid, 3, 0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            humanPlayer.makeMove(testGrid, 0, 3);
        });
    }
}