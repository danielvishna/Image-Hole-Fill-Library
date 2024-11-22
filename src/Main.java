import HoleFillingPackage.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java Main <input_image_path> <mask_image_path> <output_image_path> " +
                    "[weighting_function_type] [z] [epsilon] [connectivity_type]");
            return;
        }

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Parse command-line arguments
        String inputImagePath = args[0];
        String maskImagePath = args[1];
        String outputImagePath = "output.png"; // todo:
        IWeightingFunctionFactory weightingFunctionType = new DefaultWeightingFunctionFactory();
        int z = args.length > 4 ? Integer.parseInt(args[2]) : 3;
        double epsilon = args.length > 5 ? Double.parseDouble(args[3]) : 0.01;
        String connectivityType = args.length > 6 ? args[4] : "8-connected";

        try {
            // Load images
            System.out.println("Loading images...");
            Mat inputImage = Imgcodecs.imread(inputImagePath);
            Mat maskImage = Imgcodecs.imread(maskImagePath);

            if (inputImage.empty() || maskImage.empty()) {
                System.err.println("Error: Unable to load input or mask images.");
                return;
            }

            // Convert images to grayscale
            Mat inputGray = new Mat();
            Mat maskGray = new Mat();
            Imgproc.cvtColor(inputImage, inputGray, Imgproc.COLOR_RGB2GRAY);
            Imgproc.cvtColor(maskImage, maskGray, Imgproc.COLOR_RGB2GRAY);

            if (inputGray.height() != maskGray.height() || inputGray.width() != maskGray.width() ) {
                System.err.println("Error: Input image and mask must have the same dimensions.");
                return;
            }

            // Combine input image and mask
            Mat combinedImage = new Mat(inputGray.size(0), inputGray.size(1), 3);
            for (int x = 0; x < maskGray.width(); x++) {
                for (int y = 0; y < maskGray.height(); y++) {
                    double[] grayscale = maskGray.get(y, x);
                    grayscale[0] /= 255;
                    if (grayscale[0] <= 0.5) {
                        grayscale[0] = -1.0;
                        combinedImage.put(y, x, grayscale);
                    } else {
                        combinedImage.put(y, x, inputGray.get(y, x));
                    }
                }
            }

            // Create HoleFilling instance using factory
            HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(
                    combinedImage,
                    weightingFunctionType,
                    z,
                    epsilon,
                    connectivityType
            );

            // Fill the hole
            System.out.println("Filling the hole...");
            Mat filledImage = holeFilling.getFilledImage();
            if(filledImage == null){
                System.err.println("Error: Unable find the hole"); //todo: may need to be change!
            }
            else {
                System.out.println("Saving the filled image...");
                Imgcodecs.imwrite(outputImagePath, filledImage);

                System.out.println("Hole filling completed successfully!");
            }

            // Save the output

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
