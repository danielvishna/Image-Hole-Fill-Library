package HoleFillingPackage.WeightingFunction;

import java.awt.*;

/**
 * Implements the default weighting function for hole filling.
 * The function computes a weight based on the Euclidean distance
 * between two points and configurable parameters z and epsilon.
 */
public class DefaultWeightingFunction implements IWeightingFunction {
    private final int z;
    private final float epsilon;

    public DefaultWeightingFunction(int z, float epsilon) {
        this.z = z;
        this.epsilon = epsilon;
    }

    /**
     * Calculates the weight between two points using the weighting function.
     *
     * @param u The first point.
     * @param v The second point.
     * @return The computed weight as a double.
     */
    @Override
    public double calculate(Point u, Point v) {
        double distance = u.distance(v);
        return 1 / (Math.pow(distance, z) + epsilon);
    }
}
