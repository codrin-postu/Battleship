class Cat {

    public static int counter = 0;

    String name;
    int age;

    public Cat(String name, int age) {
        counter++;
        if (counter > 5) {
            System.out.println("You have too many cats");
        }
        this.name = name;
        this.age = age;
    }

    public static int getNumberOfCats() {
        return counter;
    }
}