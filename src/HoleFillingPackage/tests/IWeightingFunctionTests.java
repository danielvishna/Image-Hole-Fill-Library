package HoleFillingPackage.tests;

import HoleFillingPackage.WeightingFunction.DefaultWeightingFunction;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class IWeightingFunctionTests {

    @Test
    void testDefaultWeightingFunction() {
        IWeightingFunction IWeightingFunction = new DefaultWeightingFunction(2, 0.01);
        Point u = new Point(0, 0);
        Point v = new Point(3, 4);
        double weight = IWeightingFunction.calculate(u, v);
        assertEquals(1 / (Math.pow(5, 2) + 0.01), weight, 1e-6);
    }
}

