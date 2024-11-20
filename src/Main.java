// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import HoleFillingPackage.HoleFilling;
import HoleFillingPackage.HoleFillingFactory;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imagRGB = new Imgcodecs().imread(".\\src\\Lenna.png");
        Mat maskRGB = new Imgcodecs().imread(".\\src\\Mask.png");

        Mat imageGray = new Mat(imagRGB.size(0), imagRGB.size(1), 3);
        Mat maskGray = new Mat();


        Imgproc.cvtColor(imagRGB, imageGray, Imgproc.COLOR_RGB2GRAY);
        Imgproc.cvtColor(maskRGB, maskGray, Imgproc.COLOR_RGB2GRAY);

        Mat combinedImage = new Mat(imagRGB.size(0), imagRGB.size(1), 3);

        for (int x = 0; x < maskGray.width(); x++) {
            for (int y = 0; y < maskGray.height(); y++) {
                double[] grayscale = maskGray.get(y, x);
                grayscale[0] = grayscale[0] / 255;
                if (grayscale[0] <= 0.5)
                {
                    grayscale = new double[1];
                    grayscale[0]  = -1.0;
                    combinedImage.put(y, x, grayscale);
                }
                else{
                    grayscale = imageGray.get(y, x);
//                    grayscale[0] = grayscale[0] / 255;
                    combinedImage.put(y, x, grayscale);
                }
            }

        }
        HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(
                combinedImage,
                "default", // Weighting function type
                3,         // z parameter
                0.01,      // epsilon parameter
                "8-connected" // Connectivity type
        );

        Mat filledImage = holeFilling.getFilledImage();
        Imgcodecs.imwrite("output.png", filledImage);



    }
}