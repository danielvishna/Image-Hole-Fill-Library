package HoleFillingPackage.tests;

import HoleFillingPackage.HoleFilling.HoleFilling;
import HoleFillingPackage.HoleFilling.HoleFillingFactory;
import HoleFillingPackage.HoleFilling.OptimizedHoleFilling;
import HoleFillingPackage.WeightingFunction.DefaultWeightingFunctionFactory;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTests {
    @Test
    void testHoleFillingFactoryCreatesOptimizedInstance() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Mat.ones(3, 3, CvType.CV_32F);
        HoleFilling holeFilling = HoleFillingFactory.createHoleFilling(
                image,
                new DefaultWeightingFunctionFactory(),
                2,
                0.01f,
                "EightWayConnectivity",
                10
        );
        assertTrue(holeFilling instanceof OptimizedHoleFilling);
    }

    @Test
    void testHoleFillingFactoryThrowsForInvalidConnectivity() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Mat.ones(3, 3, CvType.CV_32F);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> HoleFillingFactory.createHoleFilling(
                image,
                new DefaultWeightingFunctionFactory(),
                2,
                0.01f,
                "InvalidConnectivityType"
        ));
        assertEquals("No enum constant HoleFillingPackage.Connectivity.ConnectivityEnum.InvalidConnectivityType", exception.getMessage());
    }

}
