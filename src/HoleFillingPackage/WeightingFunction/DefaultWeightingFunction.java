package HoleFillingPackage.WeightingFunction;

import java.awt.*;

public class DefaultWeightingFunction implements IWeightingFunction {
    private final int z;
    private final float epsilon;

    public DefaultWeightingFunction(int z, float epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    @Override
    public double calculate(Point u, Point v) {
        double distance = u.distance(v);
        return 1 / (Math.pow(distance, z) + epsilon);
    }
}
