package HoleFillingPackage;

import java.util.ArrayList;
import java.util.List;

public class FourConnectivity implements Connectivity {
    @Override
    public List<Point> getNeighbors(int row, int col, int maxRow, int maxCol) {
        List<Point> neighbors = new ArrayList<>();
        if (row > 0) neighbors.add(new Point(row - 1, col)); // North
        if (row < maxRow - 1) neighbors.add(new Point(row + 1, col)); // South
        if (col > 0) neighbors.add(new Point(row, col - 1)); // West
        if (col < maxCol - 1) neighbors.add(new Point(row, col + 1)); // East
        return neighbors;
    }
}
