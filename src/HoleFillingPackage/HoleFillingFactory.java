package HoleFillingPackage;

import org.opencv.core.Mat;

public class HoleFillingFactory {

    public static HoleFilling createHoleFilling(
            Mat image,
            String weightingFunctionType,
            int z,
            double epsilon,
            String connectivityType) {

        // Create the WeightingFunction based on the provided type
        WeightingFunction weightingFunction;
        switch (weightingFunctionType.toLowerCase()) {
            case "default":
                weightingFunction = new DefaultWeightingFunction(z, epsilon);
                break;
            default:
                throw new IllegalArgumentException("Unknown weighting function type: " + weightingFunctionType);
        }

        // Create the Connectivity based on the provided type
        Connectivity connectivity = ConnectivityFactory.createConnectivity(connectivityType);

        // Return a HoleFilling instance with both strategies
        return new HoleFilling(image, weightingFunction, connectivity);
    }
}
