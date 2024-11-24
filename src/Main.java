import HoleFillingPackage.*;
import HoleFillingPackage.Connectivity.ConnectivityFactory;
import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.HoleFilling.OptimizedHoleFilling;
import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        try {
            ProgramArguments programArgs = parseArguments(args);
            System.out.println("Loading images...");
            Image inputImage = Image.createMaskImage(programArgs.inputImagePath, programArgs.maskImagePath);
            if(inputImage == null){
                return;
            }
            HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(inputImage.getImageMat(),
                    programArgs.weightingFunctionType, programArgs.z,programArgs.epsilon, programArgs.connectivityType, 7);

            System.out.println("Filling the hole...");
            Mat filledImage = holeFilling.getFilledImage();
            if (filledImage == null) {
                System.err.println("Error: Unable find the hole");
                return;
            }
            System.out.println("Saving the filled image...");
            inputImage.setImage(filledImage);
            inputImage.saveImage(programArgs.outputImagePath);
            System.out.println("Hole filling completed successfully!");

        }
        catch (IllegalArgumentException e) {
            System.err.println("Input Error: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ProgramArguments parseArguments(String[] args) {
        if (args.length < 5) {
            throw new IllegalArgumentException("Usage: java Main <input_image_path> <mask_image_path> " +
                    "[weighting_function_type] [z] [epsilon] [FourWayConnectivity | EightWayConnectivity]");
        }
        String inputImagePath = args[0];
        String maskImagePath = args[1];
        String outputImagePath = "output1.png";
        int z;
        try {
            z = Integer.parseInt(args[2]);
            if (z <= 0) throw new IllegalArgumentException("z must be a positive integer.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type for z. Expected a positive integer.");
        }

        float epsilon;
        try {
            epsilon = Float.parseFloat(args[3]);
            if (epsilon <= 0) throw new IllegalArgumentException("epsilon must be a positive number.");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid type for epsilon. Expected a positive float.");
        }

        String connectivityType = args[4];
        if (!connectivityType.equals("FourWayConnectivity") && !connectivityType.equals("EightWayConnectivity")) {
            throw new IllegalArgumentException(
                    "Invalid connectivity type. Expected 'FourWayConnectivity' or 'EightWayConnectivity'."
            );
        }

        IWeightingFunctionFactory weightingFunctionType = new DefaultWeightingFunctionFactory();

        return new ProgramArguments(inputImagePath, maskImagePath, outputImagePath, weightingFunctionType, z, epsilon,
                connectivityType);
    }

    static class ProgramArguments {
        String inputImagePath;
        String maskImagePath;
        String outputImagePath;
        IWeightingFunctionFactory weightingFunctionType;
        int z;
        float epsilon;
        String connectivityType;

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