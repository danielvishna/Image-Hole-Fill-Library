import CommandLineUtility.ArgumentParser;
import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.Utility.Image;
import org.opencv.core.Core;

public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        try {
            ArgumentParser.ProgramArguments programArgs = ArgumentParser.parse(args);

            Image inputImage = Image.createMaskImage(programArgs.inputImagePath, programArgs.maskImagePath);
            if (inputImage == null) {
                throw new IllegalArgumentException("Failed to load images. Check file paths.");
            }

            HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(
                    inputImage.getImageMat(), programArgs.weightingFunctionType,
                    programArgs.z, programArgs.epsilon, programArgs.connectivityType);

            inputImage.setImage(holeFilling.getFilledImage());
            inputImage.saveImage(programArgs.outputImagePath);

            System.out.println("Hole filling completed successfully!");
        } catch (IllegalArgumentException e) {
            System.err.println("Input Error: " + e.getMessage());
        }
    }
}