package mainFiles;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello world!");
		
		AddSomething(5, 5);
		PrintSomething("Words or something");

	}
	
	public static int AddSomething(int a, int b) {
		return a + b;
	}
	
	public static void PrintSomething(String a) {
		System.out.println(a);
	}
	
	public static int MultiplySomething(int a, int b, int c) {
		return a * b * c; 
	}

	
}
