package HoleFillingPackage.HoleFilling;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Connectivity.ConnectivityFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;
import org.opencv.core.Mat;

public class HoleFillingFactory {
    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z,
                                                float epsilon, String connectivityType, int k) {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);
        return new OptimizedHoleFilling(image, connectivity, weightingFunction, k);
    }

    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z,
                                                float epsilon, String connectivityType) {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);

        return new HoleFilling(image, weightingFunction, connectivity);
    }
}
