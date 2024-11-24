package HoleFillingPackage.HoleFilling;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Connectivity.ConnectivityFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;
import org.opencv.core.Mat;

/**
 * A factory class for creating instances of the HoleFilling class.
 * This factory handles the configuration of connectivity types, weighting functions, and optimizations.
 */
public class HoleFillingFactory {
    /**
     * Creates an instance of OptimizedHoleFilling with grid-based optimization.
     *
     * @param image                    The input image represented as a Mat object.
     * @param weightingFunctionFactory Factory for creating the weighting function.
     * @param z                        The power parameter for the weighting function.
     * @param epsilon                  A small float to avoid division by zero in the weighting function.
     * @param connectivityType         The type of connectivity ("FourWayConnectivity" or "EightWayConnectivity").
     * @param numGridCell              The number of grid cells for optimization.
     * @return An instance of OptimizedHoleFilling.
     */
    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z,
                                                float epsilon, String connectivityType, int numGridCell) {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);
        return new OptimizedHoleFilling(image, connectivity, weightingFunction, numGridCell);
    }

    /**
     * Creates an instance of HoleFilling with the default algorithm.
     *
     * @param image                    The input image represented as a Mat object.
     * @param weightingFunctionFactory Factory for creating the weighting function.
     * @param z                        The power parameter for the weighting function.
     * @param epsilon                  A small float to avoid division by zero in the weighting function.
     * @param connectivityType         The type of connectivity ("FourWayConnectivity" or "EightWayConnectivity").
     * @return An instance of HoleFilling.
     */
    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z,
                                                float epsilon, String connectivityType) {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);

        return new HoleFilling(image, weightingFunction, connectivity);
    }
}
