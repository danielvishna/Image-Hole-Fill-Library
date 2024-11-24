package CommandLineUtility;

import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;

/**
 * Parses command-line arguments for the hole-filling program.
 * Validates and converts input parameters into a usable format.
 */
public class ArgumentParser {

    /**
     * Parses command-line arguments and validates them, returning a structured ProgramArguments object.
     *
     * @param args Array of input arguments provided via command line.
     * @return Parsed and validated program arguments.
     * @throws IllegalArgumentException if input arguments are invalid or insufficient.
     */
    public static ProgramArguments parse(String[] args) {
        if (args.length < 5) {
            throw new IllegalArgumentException("Usage: java Main <input_image_path> <mask_image_path> " + " [z] [epsilon] [FourWayConnectivity | EightWayConnectivity]");
        }

        String inputImagePath = args[0];
        String maskImagePath = args[1];
        String outputImagePath = "outputImag.png";

        int z = parsePositiveInt(args[2], "z");
        float epsilon = parsePositiveFloat(args[3], "epsilon");
        String connectivityType = args[4];

        if (!connectivityType.equals("FourWayConnectivity") && !connectivityType.equals("EightWayConnectivity")) {
            throw new IllegalArgumentException("Invalid connectivity type. Expected 'FourWayConnectivity' or 'EightWayConnectivity'.");
        }

        IWeightingFunctionFactory weightingFunctionType = new DefaultWeightingFunctionFactory();

        return new ProgramArguments(inputImagePath, maskImagePath, outputImagePath, weightingFunctionType, z, epsilon, connectivityType);
    }

    /**
     * Parses a positive integer value from a string.
     *
     * @param value The string to parse.
     * @param name  The name of the parameter being parsed.
     * @return A positive integer.
     * @throws IllegalArgumentException if the value is not a positive integer.
     */
    private static int parsePositiveInt(String value, String name) {
        try {
            int result = Integer.parseInt(value);
            if (result <= 0) throw new IllegalArgumentException(name + " must be a positive integer.");
            return result;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type for " + name + ". Expected a positive integer.");
        }
    }

    /**
     * Parses a positive float value from a string.
     *
     * @param value The string to parse.
     * @param name  The name of the parameter being parsed.
     * @return A positive float.
     * @throws IllegalArgumentException if the value is not a positive float.
     */
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

        public ProgramArguments(String inputImagePath, String maskImagePath, String outputImagePath, IWeightingFunctionFactory weightingFunctionType, int z, float epsilon, String connectivityType) {
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
