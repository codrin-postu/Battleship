public class Main {

    public static void main(String[] args) {
        int count = 0;

        Secret[] values = Secret.values();
        for (Secret s : values
        ) {
            if (s.name().startsWith("STAR")) {
                count++;
            }
        }

        System.out.println(count);
    }
}


