package HoleFillingPackage.tests;

import HoleFillingPackage.DefaultWeightingFunction;
import HoleFillingPackage.EightConnectivity;
import HoleFillingPackage.HoleFilling;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.CvType;
import static org.junit.jupiter.api.Assertions.*;

class HoleFillingTests {

    @Test
    void testHoleFilling() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = new Mat(3, 3, CvType.CV_32F);
        image.put(0, 0, 0.5);
        image.put(0, 1, 0.6);
        image.put(0, 2, 0.7);
        image.put(1, 0, 0.4);
        image.put(1, 1, -1.0); // Hole
        image.put(1, 2, 0.8);
        image.put(2, 0, 0.3);
        image.put(2, 1, 0.2);
        image.put(2, 2, 0.1);

        HoleFilling holeFilling = new HoleFilling(
                image,
                new DefaultWeightingFunction(2, 0.01),
                new EightConnectivity()
        );

        Mat filledImage = holeFilling.getFilledImage();
        assertNotNull(filledImage);
        assertNotEquals(-1.0, filledImage.get(1, 1)[0]);
    }
}
