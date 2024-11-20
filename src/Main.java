// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import HoleFillingPackage.HoleFilling;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class Main {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat rgb = new Imgcodecs().imread("C:\\Users\\DanielV\\IdeaProjects\\Lightricks image library\\src\\Lenna.png");
        System.out.println(rgb);
        Mat new_image = new Mat(rgb.size(0), rgb.size(1), 3);

        Mat image = new Mat(rgb.size(0), rgb.size(1), 3);
        Imgproc.cvtColor(rgb, image, Imgproc.COLOR_RGB2GRAY);
        Mat maskRGB = new Imgcodecs().imread("C:\\Users\\DanielV\\IdeaProjects\\Lightricks image library\\src\\Hole_Mask_Circle.png");
        Mat mask = new Mat();
        Imgproc.cvtColor(maskRGB, mask, Imgproc.COLOR_RGB2GRAY);
        System.out.println(mask);
        int count = 0;
        for (int x = 0; x < mask.width(); x++) {
            for (int y = 0; y < mask.height(); y++) {
                double[] grayscale = mask.get(y, x);
                grayscale[0] = grayscale[0] / 255;
                if (grayscale[0] <= 0.5)
                {
                    grayscale = new double[1];
                    grayscale[0]  = -1.0;
                    new_image.put(y, x, grayscale);
                }
                else{
                    grayscale = image.get(y, x);
//                    grayscale[0] = grayscale[0] / 255;
                    new_image.put(y, x, grayscale);
                }
            }

        }
        HoleFilling tmp = new HoleFilling(new_image);
        Mat m = tmp.getFilledImage();
        Imgcodecs.imwrite("input.png", m);



    }
}