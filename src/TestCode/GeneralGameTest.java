// package TestCode;

// import mainFiles.GeneralGame;
// import mainFiles.HumanPlayer;
// import mainFiles.Player;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class GeneralGameTest {
//     private GeneralGame game;
//     private Player player1;
//     private Player player2;

//     @BeforeEach
//     public void setUp() {
//         player1 = new HumanPlayer('S', "Player 1");
//         player2 = new HumanPlayer('O', "Player 2");
//         game = new GeneralGame(3, player1, player2) {
//         };
//     }

//     @Test
//     public void testScoring() {
//         game.makeMove(0, 0); // S
//         game.makeMove(0, 1); // O
//         game.makeMove(0, 2); // S
//         assertTrue(game.checkSOS(0, 2));
//         assertEquals(1, player1.score);
//         game.grid[0][0] = 0;
//         game.grid[0][1] = 0;
//         game.grid[0][2] = 0;
//     }

//     @Test
//     public void testGameTie() {
//         game.makeMove(0,0);
//         game.makeMove(0,2);
//         game.makeMove(1,0);
//         game.makeMove(1,2);
//         game.makeMove(2,0);
//         game.makeMove(2,2);
//         game.makeMove(0,1);
//         game.makeMove(2,1);
//         game.makeMove(1,1);
//         assertTrue(game.checkTie());
//     }

// }
