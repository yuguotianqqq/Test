package test;

public enum EnumTest {

	A, B, C;
	public static void main(String[] args) {
		EnumTest t = EnumTest.B;
		switch (t) {
		case A:
			System.out.println("A");
			break;
		case B:
			System.out.println("B");
			break;
		default:
			break;

		}
	}
}
