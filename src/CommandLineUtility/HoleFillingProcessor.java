package CommandLineUtility;

import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.Image;
import org.opencv.core.Mat;

public class HoleFillingProcessor {

    public void process(ArgumentParser.ProgramArguments args) {
        try {
            System.out.println("Loading images...");
            Image inputImage = Image.createMaskImage(args.inputImagePath, args.maskImagePath);
            if (inputImage == null) {
                throw new IllegalArgumentException("Failed to load images. Check file paths.");
            }

            HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(inputImage.getImageMat(),
                    args.weightingFunctionType, args.z, args.epsilon, args.connectivityType);

            System.out.println("Filling the hole...");
            Mat filledImage = holeFilling.getFilledImage();
            if (filledImage == null) {
                throw new IllegalArgumentException("Error: Unable to find the hole in the image.");
            }

            System.out.println("Saving the filled image...");
            inputImage.setImage(filledImage);
            inputImage.saveImage(args.outputImagePath);
            System.out.println("Hole filling completed successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Input Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Processing Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
