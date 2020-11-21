import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int input = reader.read();
        char[] inputArray = new char[50];
        int i = 0;
        while (input != -1) {
            inputArray[i++] = (char)input;
            input = reader.read();
        }
        reader.close();

        for (; i > 0; i--) {
            System.out.print(inputArray[i - 1]);
        }

    }
}