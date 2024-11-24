package HoleFillingPackage.tests;
import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Connectivity.EightConnectivity;
import HoleFillingPackage.Connectivity.FourConnectivity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import HoleFillingPackage.Utility.PixelPoint;



class ConnectivityTests {


    @Test
    void testFourConnectivityAtCorner() {
        Connectivity connectivity = new FourConnectivity();
        List<PixelPoint> neighbors = connectivity.getNeighbors(0, 0, 3, 3);
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains(new PixelPoint(0, 1)));
        assertTrue(neighbors.contains(new PixelPoint(1, 0)));
    }

    @Test
    void testEightConnectivityAtBoundary() {
        Connectivity connectivity = new EightConnectivity();
        List<PixelPoint> neighbors = connectivity.getNeighbors(1, 0, 3, 3);
        assertEquals(5, neighbors.size());
        assertTrue(neighbors.contains(new PixelPoint(0, 0)));
        assertTrue(neighbors.contains(new PixelPoint(2, 0)));
        assertTrue(neighbors.contains(new PixelPoint(0, 1)));
        assertTrue(neighbors.contains(new PixelPoint(1, 1)));
        assertTrue(neighbors.contains(new PixelPoint(2, 1)));
    }
    @Test
    void testFourConnectivity() {
        Connectivity connectivity = new FourConnectivity();
        List<PixelPoint> neighbors = connectivity.getNeighbors(1, 1, 3, 3);
        assertEquals(4, neighbors.size());
        assertTrue(neighbors.contains(new PixelPoint(0, 1)));
        assertTrue(neighbors.contains(new PixelPoint(2, 1)));
        assertTrue(neighbors.contains(new PixelPoint(1, 0)));
        assertTrue(neighbors.contains(new PixelPoint(1, 2)));
    }

    @Test
    void testEightConnectivity() {
        Connectivity connectivity = new EightConnectivity();
        List<PixelPoint> neighbors = connectivity.getNeighbors(1, 1, 3, 3);
        assertEquals(8, neighbors.size());
        assertTrue(neighbors.contains(new PixelPoint(0, 0)));
        assertTrue(neighbors.contains(new PixelPoint(0, 1)));
        assertTrue(neighbors.contains(new PixelPoint(0, 2)));
        assertTrue(neighbors.contains(new PixelPoint(1, 0)));
        assertTrue(neighbors.contains(new PixelPoint(1, 2)));
        assertTrue(neighbors.contains(new PixelPoint(2, 0)));
        assertTrue(neighbors.contains(new PixelPoint(2, 1)));
        assertTrue(neighbors.contains(new PixelPoint(2, 2)));
    }

}
