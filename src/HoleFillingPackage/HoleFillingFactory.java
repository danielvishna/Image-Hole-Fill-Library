package HoleFillingPackage;

import org.opencv.core.Mat;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.function.Function;

public class HoleFillingFactory {
    public static HoleFilling createHoleFilling(Mat image, IWeightingFunctionFactory weightingFunctionFactory, int z, double epsilon, String connectivityType) throws Exception {
        IWeightingFunction weightingFunction;
        weightingFunction = weightingFunctionFactory.Create(z, epsilon);
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);
        return new HoleFilling(image, weightingFunction, connectivity);
    }
}
