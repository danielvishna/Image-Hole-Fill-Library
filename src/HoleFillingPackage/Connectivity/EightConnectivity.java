package HoleFillingPackage.Connectivity;

import java.util.ArrayList;
import java.util.List;

import java.awt.Point;

public class EightConnectivity implements Connectivity {
    @Override
    public List<Point> getNeighbors(int row, int col, int maxRow, int maxCol) {
        List<Point> neighbors = new ArrayList<>();
        for (int r = Math.max(0, row - 1); r <= Math.min(maxRow - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(maxCol - 1, col + 1); c++) {
                if (r != row || c != col) { // Exclude the current pixel
                    Point tmp = new Point(c,r);
                    neighbors.add(tmp);
                }
            }
        }
        return neighbors;
    }
}
