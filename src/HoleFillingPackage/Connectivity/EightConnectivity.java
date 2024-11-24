package HoleFillingPackage.Connectivity;


import java.util.ArrayList;
import java.util.List;
import HoleFillingPackage.PixelPoint;

public class EightConnectivity implements Connectivity {
    @Override
    public List<PixelPoint> getNeighbors(int row, int col, int maxRow, int maxCol) {
        List<PixelPoint> neighbors = new ArrayList<>();
        for (int r = Math.max(0, row - 1); r <= Math.min(maxRow - 1, row + 1); r++) {
            for (int c = Math.max(0, col - 1); c <= Math.min(maxCol - 1, col + 1); c++) {
                if (r != row || c != col) {
                    PixelPoint newNeighbor = new PixelPoint(c,r);
                    neighbors.add(newNeighbor);
                }
            }
        }
        return neighbors;
    }
}
