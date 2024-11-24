package CommandLineUtility;

import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;

public class ArgumentParser {

    public static ProgramArguments parse(String[] args) {
        if (args.length < 5) {
            throw new IllegalArgumentException("Usage: java Main <input_image_path> <mask_image_path> " +
                    "[weighting_function_type] [z] [epsilon] [FourWayConnectivity | EightWayConnectivity]");
        }

        String inputImagePath = args[0];
        String maskImagePath = args[1];
        String outputImagePath = "output1.png";

        int z = parsePositiveInt(args[2], "z");
        float epsilon = parsePositiveFloat(args[3], "epsilon");
        String connectivityType = args[4];

        if (!connectivityType.equals("FourWayConnectivity") && !connectivityType.equals("EightWayConnectivity")) {
            throw new IllegalArgumentException(
                    "Invalid connectivity type. Expected 'FourWayConnectivity' or 'EightWayConnectivity'."
            );
        }

        IWeightingFunctionFactory weightingFunctionType = new DefaultWeightingFunctionFactory();

        return new ProgramArguments(inputImagePath, maskImagePath, outputImagePath, weightingFunctionType, z, epsilon, connectivityType);
    }

    private static int parsePositiveInt(String value, String name) {
        try {
            int result = Integer.parseInt(value);
            if (result <= 0) throw new IllegalArgumentException(name + " must be a positive integer.");
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type for " + name + ". Expected a positive integer.");
        }
    }

    private static float parsePositiveFloat(String value, String name) {
        try {
            float result = Float.parseFloat(value);
            if (result <= 0) throw new IllegalArgumentException(name + " must be a positive float.");
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type for " + name + ". Expected a positive float.");
        }
    }

    public static class ProgramArguments {
        public final String inputImagePath;
        public final String maskImagePath;
        public final String outputImagePath;
        public final IWeightingFunctionFactory weightingFunctionType;
        public final int z;
        public final float epsilon;
        public final String connectivityType;

        public ProgramArguments(String inputImagePath, String maskImagePath, String outputImagePath,
                                IWeightingFunctionFactory weightingFunctionType, int z, float epsilon, String connectivityType) {
            this.inputImagePath = inputImagePath;
            this.maskImagePath = maskImagePath;
            this.outputImagePath = outputImagePath;
            this.weightingFunctionType = weightingFunctionType;
            this.z = z;
            this.epsilon = epsilon;
            this.connectivityType = connectivityType;
        }
    }
}
