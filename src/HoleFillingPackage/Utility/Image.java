package HoleFillingPackage.Utility;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Represents an image with utility methods for loading, saving,
 * and applying masks for hole-filling operations.
 */
public class Image {
    String path;
    Mat image;

    public Image(String path) {
        this.path = path;
    }

    /**
     * Loads the image from the specified path.
     *
     * @return True if the image was successfully loaded; false otherwise.
     */
    public boolean loadImage() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imageRGB = Imgcodecs.imread(this.path);
        if (imageRGB.empty()) {
            return true;
        }
        Mat imageGray = new Mat();
        Imgproc.cvtColor(imageRGB, imageGray, Imgproc.COLOR_RGB2GRAY);
        this.image = new Mat(imageGray.size(0), imageGray.size(1), CvType.CV_32F);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        for (int row = 0; row < imageGray.height(); row++) {
            for (int col = 0; col < imageGray.width(); col++) {

                int finalRow = row;
                int finalCol = col;
                Future<?> future = executor.submit(() -> {
                    double[] grayscale = imageGray.get(finalRow, finalCol);
                    grayscale[0] /= 255;
                    this.image.put(finalRow, finalCol, grayscale);
                });
                futures.add(future);
            }
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();

        return this.image.empty();
    }

    public Mat getImageMat() {
        return image;
    }

    public void setImage(Mat mat) {
        this.image = mat;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Combines the image with a mask to create a hole.
     *
     * @param mask The mask matrix.
     */
    public void combineImage(Mat mask) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        for (int row = 0; row < mask.height(); row++) {
            for (int col = 0; col < mask.width(); col++) {
                if (mask.get(row, col)[0] <= 0.5) {
                    int finalRow = row;
                    int finalCol = col;
                    Future<?> future = executor.submit(() -> {
                        double[] grayscale = new double[1];
                        grayscale[0] = -1.0;
                        this.image.put(finalRow, finalCol, grayscale);
                    });
                    futures.add(future);
                }

            }
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    /**
     * Saves the image to the specified path.
     */
    public void saveImage(String path) {
        this.setPath(path);
        this.saveImage();
    }

    /**
     * Saves the image to the this.path
     */
    public void saveImage() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        for (int row = 0; row < this.image.height(); row++) {
            for (int col = 0; col < this.image.width(); col++) {

                int finalRow = row;
                int finalCol = col;
                Future<?> future = executor.submit(() -> {
                    double[] grayscale = this.image.get(finalRow, finalCol);
                    grayscale[0] *= 255;
                    this.image.put(finalRow, finalCol, grayscale);
                });
                futures.add(future);


            }
        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        Imgcodecs.imwrite(path, image);
    }

    /**
     * Creates an Image instance with a hole applied based on the input image and mask.
     *
     * @param inputImagePath The file path to the input image.
     * @param maskImagePath  The file path to the mask image, which defines the hole.
     * @return An Image instance with the hole applied, or null if image loading fails.
     */
    public static Image createMaskImage(String inputImagePath, String maskImagePath) {
        Image inputImage = new Image(inputImagePath);
        Image maskImage = new Image(maskImagePath);
        if (inputImage.loadImage() || maskImage.loadImage()) {
            System.err.println("Error: Unable to load input or mask images.");
            return null;
        }

        inputImage.combineImage(maskImage.getImageMat());
        return inputImage;
    }


}
