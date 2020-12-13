abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}

class Triangle extends Shape {
    private double a;
    private double b;
    private double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double getPerimeter() {
        return a + b+ c;
    }

    @Override
    double getArea() {
        double s = (a + b+ c) / 2;
        return (Math.sqrt(s*(s-a)*(s-b)*(s-c)));
    }
}

class Rectangle extends Shape {
    private double a;
    private double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    double getPerimeter() {
        return a*2+b*2;
    }

    @Override
    double getArea() {
        return a*b;
    }
}

class Circle extends Shape {
    private double rad;

    public Circle(double rad) {
        this.rad = rad;
    }

    @Override
    double getPerimeter() {
        return 2*Math.PI*rad;
    }

    @Override
    double getArea() {
        return Math.PI*rad*rad;
    }

}