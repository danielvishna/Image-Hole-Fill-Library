package HoleFillingPackage;

import java.awt.Point;

public class DefaultWeightingFunction implements IWeightingFunction {
    private final int z;
    private final double epsilon;

    public DefaultWeightingFunction(int z, double epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    @Override
    public double calculate(Point u, Point v) {
        double distance = Math.sqrt(Math.pow(u.getX() - v.getX(), 2) + Math.pow(u.getY() - v.getY(), 2));
        return 1 / (Math.pow(distance, z) + epsilon);
    }
}
