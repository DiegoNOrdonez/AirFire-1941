import java.util.Scanner;

public class DefaultMain {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		uberLoop: while (true) { //loop until exit
			System.out.println("9: Quit");
			byte choice = input.nextByte();
			switch (choice) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				break uberLoop;
			}
		}
	}

}
