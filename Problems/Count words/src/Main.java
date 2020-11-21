import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sentence = new StringBuilder();
        int input = reader.read();

        while (input != -1) {
            sentence.append((char)input);
            input = reader.read();
        }

        int wordCount = 0;
        boolean prevChar = false;

        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == ' ') {
                prevChar = false;
            } else if (prevChar == false){
                prevChar = true;
                wordCount++;
            }
        }

        System.out.println(wordCount);
        reader.close();
    }
}