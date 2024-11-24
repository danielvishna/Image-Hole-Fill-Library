package HoleFillingPackage;
import java.awt.Point;

public class MyPoint extends Point{
    private double color;

    public MyPoint(int x, int y){
        super(x, y);
    }
    public MyPoint(int x, int y, double color){
        super(x, y);
        this.color = color;
    }

    public double getColor() {
        return color;
    }
}
