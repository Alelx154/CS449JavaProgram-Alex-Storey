import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTest {

	@Test
	void testAddSomething() {
		int someNumber = 5;
		int someNumber2nd = 5;
		Main main = new Main();
		assertEquals(10, Main.AddSomething(someNumber, someNumber2nd));
		
	}

	@Test
	void testMultiplySomething() {
		int someNumber = 2;
		int someNumberTwo = 2;
		int soemNumberThree = 2;
		Main main = new Main();
		assertEquals(8, Main.MultiplySomething(someNumber, someNumberTwo, soemNumberThree));
	}

}
