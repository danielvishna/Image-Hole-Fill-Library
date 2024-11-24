package HoleFillingPackage.Connectivity;


import HoleFillingPackage.PixelPoint;

import java.util.List;

public interface Connectivity {
    List<PixelPoint> getNeighbors(int row, int col, int maxRow, int maxCol);

}
