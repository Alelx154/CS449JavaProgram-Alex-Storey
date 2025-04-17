// package TestCode;


// import mainFiles.ComputerPlayer;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class ComputerPlayerTest {
//     @Test
//     public void testComputerPlayerCreation() {
//         ComputerPlayer player = new ComputerPlayer('O', "Computer");
//         assertEquals('O', player.getSymbol());
//         assertEquals("Computer", player.getName());
//     }

//     @Test
//     public void testFindBestMove() {
//         ComputerPlayer player = new ComputerPlayer('S', "Computer");
//         int[][] grid = new int[3][3];
//         int[] move = player.findBestMove(grid);

//         assertTrue(move[0] >= 0 && move[0] < 3);
//         assertTrue(move[1] >= 0 && move[1] < 3);
//     }

//     @Test
//     public void testMakeValidMove() {
//         ComputerPlayer player = new ComputerPlayer('O', "Computer");
//         int[][] grid = new int[3][3];
//         player.makeMove(grid, 0, 0);

//         // Check that exactly one cell is filled
//         int filledCells = 0;
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 if (grid[i][j] != 0) filledCells++;
//             }
//         }
//         assertEquals(1, filledCells);
//     }
// }

