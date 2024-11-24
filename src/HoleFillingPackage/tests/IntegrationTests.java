package HoleFillingPackage.tests;

import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.Utility.Image;
import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTests {
    @Test
    void testFullHoleFillingPipeline() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Image inputImage = new Image("C:\\Users\\DanielV\\IdeaProjects\\Lightricks image library\\src\\Lenna_32x32.png");
        Image maskImage = new Image("C:\\Users\\DanielV\\IdeaProjects\\Lightricks image library\\src\\Mask_32x32.png");


        inputImage.loadImage();
        assertNotNull(inputImage.getImageMat());

        maskImage.loadImage();
        assertNotNull(maskImage.getImageMat());


        inputImage.combineImage(maskImage.getImageMat());

        HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(
                inputImage.getImageMat(),
                new DefaultWeightingFunctionFactory(),
                2,
                0.01f,
                "FourWayConnectivity"
        );

        Mat filledImage = holeFilling.getFilledImage();
        assertNotNull(filledImage);

        inputImage.setImage(filledImage);
        inputImage.saveImage("test_output.png");

        File outputFile = new File("test_output.png");
        assertTrue(outputFile.exists());
    }

}
