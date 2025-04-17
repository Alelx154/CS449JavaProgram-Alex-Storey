// package TestCode;

// import mainFiles.HumanPlayer;
// import mainFiles.Player;
// import mainFiles.SimpleGame;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class SimpleGameTest {
//     private SimpleGame game;
//     private Player player1;
//     private Player player2;

//     @BeforeEach
//     public void setUp() {
//         player1 = new HumanPlayer('S', "Player 1");
//         player2 = new HumanPlayer('O', "Player 2");
//         game = new SimpleGame(3, player1, player2) {

//         };
//     }

//     @Test
//     public void testInitialBoard() {
//         assertEquals(3, game.board_Size);
//         assertEquals(player1, game.getTurn());
//         assertArrayEquals(new int[3][3], game.getGrid());
//     }

//     @Test
//     public void testMakeMove() {
//         game.makeMove(0, 0);
//         assertEquals(1, game.getCell(0, 0)); // Assuming 1 represents 'S'
//         assertEquals(player2, game.getTurn());
//     }

//     @Test
//     public void testInvalidMove() {
//         game.makeMove(0, 0);
//         int firstValue = game.getCell(0, 0);
//         game.makeMove(0, 0);
//         assertEquals(firstValue, game.getCell(0, 0));
//     }
// }
