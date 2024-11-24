package HoleFillingPackage.Connectivity;

import java.util.ArrayList;
import java.util.List;
import HoleFillingPackage.PixelPoint;

public class FourConnectivity implements Connectivity {
    @Override
    public List<PixelPoint> getNeighbors(int row, int col, int maxRow, int maxCol) {
        List<PixelPoint> neighbors = new ArrayList<>();
        int[][] directions =  new int[][]{{0, -1}, {-1, 0}, {1, 0}, {0, 1}};
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            if (newRow >= 0 && newRow < maxRow && newCol >= 0 && newCol < maxCol) {
                neighbors.add(new PixelPoint(newCol, newRow));
            }
        }
        return neighbors;
    }
}
