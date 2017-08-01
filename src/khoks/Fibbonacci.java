package khoks;

public class Fibbonacci {

	public static void main(String[] args) {
		int zero = 0;
		int one = 1;
		int fibo = 0;
		for(int i = 2 ; i < 25 ; i++) {
			fibo = one + zero;
			zero = one;
			one = fibo;
		}
		System.out.println(fibo);
	}

}
