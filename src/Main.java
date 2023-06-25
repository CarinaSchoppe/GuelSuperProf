import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        var scanner = new Scanner(System.in);

        String line;
        while (!(line = scanner.nextLine()).equalsIgnoreCase("quit")) {
            System.out.println(line);
            switch (line) {
                default -> throw new Exception("ERROR: Unknown command");
            }
        }
    }
}
