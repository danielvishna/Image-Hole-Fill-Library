package HoleFillingPackage;

import java.util.List;

public interface Connectivity {
    List<Point> getNeighbors(int row, int col, int maxRow, int maxCol);

}
