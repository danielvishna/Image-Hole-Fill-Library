package HoleFillingPackage;
import java.awt.Point;

public class PixelPoint extends Point {
    private double color;

    public PixelPoint(int x, int y){
        super(x, y);
    }
    public PixelPoint(int x, int y, double color){
        super(x, y);
        this.color = color;
    }

    public double getColor() {
        return color;
    }

    public void setColor(double color) {
        this.color = color;
    }
}
