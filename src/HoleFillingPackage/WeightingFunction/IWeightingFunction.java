package HoleFillingPackage.WeightingFunction;

import java.awt.*;

/**
 * Interface for defining a weighting function used in hole filling.
 */
public interface IWeightingFunction {
    /**
     * Calculates the weight between two points in an image.
     *
     * @param u The first point.
     * @param v The second point.
     * @return The computed weight as a double.
     */
    double calculate(Point u, Point v);

}
