import java.util.ArrayList;

public class Task {
	private static ArrayList<String> completed = new ArrayList<String>();
	
	public static void add(String ID) {
		if (!completed.contains(ID)) {
			completed.add(ID);
		}
	}
	public static void prime(String ID) {
		completed.remove(ID);
	}
	public static boolean done(String ID) {
		return completed.contains(ID);
	}
}
