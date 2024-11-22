import HoleFillingPackage.*;
import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import HoleFillingPackage.WeightingFunction.IWeightingFunctionFactory;
import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {
    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Usage: java Main <input_image_path> <mask_image_path> " + "[weighting_function_type] [z] [epsilon] [FourWayConnectivity | EightWayConnectivity]");
            return;
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String inputImagePath = args[0];
        String maskImagePath = args[1];
        String outputImagePath = "output.png";
        IWeightingFunctionFactory weightingFunctionType = new DefaultWeightingFunctionFactory();
        int z = Integer.parseInt(args[2]);
        double epsilon = Double.parseDouble(args[3]);
        String connectivityType = args[4];

        try {
            System.out.println("Loading images...");
            Image inputImage = new Image(inputImagePath);
            Image maskImage = new Image(maskImagePath);

            if (!inputImage.loadImage() || !maskImage.loadImage()) {
                System.err.println("Error: Unable to load input or mask images.");
                return;
            }

            inputImage.combineImage(maskImage.getImageMat());

            HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(inputImage.getImageMat(), weightingFunctionType, z, epsilon, connectivityType);

            System.out.println("Filling the hole...");
            Mat filledImage = holeFilling.getFilledImage();
            if (filledImage == null) {
                System.err.println("Error: Unable find the hole");
            } else {
                System.out.println("Saving the filled image...");
                inputImage.setImage(filledImage);
                inputImage.setPath(outputImagePath);
                inputImage.saveImage();

                System.out.println("Hole filling completed successfully!");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
