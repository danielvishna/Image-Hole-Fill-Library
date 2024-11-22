package HoleFillingPackage.tests;
import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Connectivity.EightConnectivity;
import HoleFillingPackage.Connectivity.FourConnectivity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import java.awt.Point;


class ConnectivityTests {

    @Test
    void testFourConnectivity() {
        Connectivity connectivity = new FourConnectivity();
        List<Point> neighbors = connectivity.getNeighbors(1, 1, 3, 3);
        assertEquals(4, neighbors.size());
        assertTrue(neighbors.contains(new Point(0, 1)));
        assertTrue(neighbors.contains(new Point(2, 1)));
        assertTrue(neighbors.contains(new Point(1, 0)));
        assertTrue(neighbors.contains(new Point(1, 2)));
    }

    @Test
    void testEightConnectivity() {
        Connectivity connectivity = new EightConnectivity();
        List<Point> neighbors = connectivity.getNeighbors(1, 1, 3, 3);
        assertEquals(8, neighbors.size());
        assertTrue(neighbors.contains(new Point(0, 0)));
        assertTrue(neighbors.contains(new Point(0, 1)));
        assertTrue(neighbors.contains(new Point(0, 2)));
        assertTrue(neighbors.contains(new Point(1, 0)));
        assertTrue(neighbors.contains(new Point(1, 2)));
        assertTrue(neighbors.contains(new Point(2, 0)));
        assertTrue(neighbors.contains(new Point(2, 1)));
        assertTrue(neighbors.contains(new Point(2, 2)));
    }

}
