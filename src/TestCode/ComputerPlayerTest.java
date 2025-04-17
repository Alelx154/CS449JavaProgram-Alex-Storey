package TestCode;

import mainFiles.ComputerPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComputerPlayerTest {
    private ComputerPlayer computerPlayer;
    private int[][] testGrid;

    @BeforeEach
    public void setUp() {
        computerPlayer = new ComputerPlayer("Computer");
        testGrid = new int[3][3];
    }

    @Test
    public void testInitialState() {
        assertEquals("Computer", computerPlayer.getName());
        assertEquals(0, computerPlayer.score);
    }

    @Test
    public void testFindBestMoveOnEmptyBoard() {
        int[] bestMove = computerPlayer.findBestMove(testGrid);
        assertNotNull(bestMove);
        assertEquals(2, bestMove.length);
        assertTrue(bestMove[0] >= 0 && bestMove[0] < 3);
        assertTrue(bestMove[1] >= 0 && bestMove[1] < 3);
    }

    @Test
    public void testFindBestMoveWithSOSOpportunity() {
        // Set up a board with an SOS opportunity
        testGrid[0][0] = 1; // S
        testGrid[0][1] = 2; // O
        // Empty space at [0][2] for completing SOS
        
        int[] bestMove = computerPlayer.findBestMove(testGrid);
        assertNotNull(bestMove);
        assertEquals(0, bestMove[0]);
        assertEquals(2, bestMove[1]);
    }

    @Test
    public void testMakeMove() {
        computerPlayer.makeMove(testGrid, 0, 0);
        assertTrue(testGrid[0][0] == 1 || testGrid[0][0] == 2); // Should be S or O
    }

    @Test
    public void testSetSymbol() {
        computerPlayer.setSymbol('S');
        assertEquals('S', computerPlayer.getSymbol());
        
        computerPlayer.setSymbol('O');
        assertEquals('O', computerPlayer.getSymbol());
    }

    @Test
    public void testSetInvalidSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            computerPlayer.setSymbol('X');
        });
    }
}