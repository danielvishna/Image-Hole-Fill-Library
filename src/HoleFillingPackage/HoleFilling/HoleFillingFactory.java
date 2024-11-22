package HoleFillingPackage.HoleFilling;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Connectivity.ConnectivityFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;
import org.opencv.core.Mat;

public class HoleFillingFactory {
    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z, double epsilon, String connectivityType) throws Exception {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);
        return new HoleFilling(image, weightingFunction, connectivity);
    }
}
