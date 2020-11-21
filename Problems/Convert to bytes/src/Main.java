import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        int input = inputStream.read();
        while (input != -1) {
            System.out.print(input);
            input = inputStream.read();
        }
        inputStream.close();
    }
}