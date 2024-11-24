package HoleFillingPackage.Connectivity;


import HoleFillingPackage.Utility.PixelPoint;

import java.util.List;

/**
 * Defines connectivity strategies for determining neighboring pixels.
 */
public interface Connectivity {

    /**
     * Retrieves neighboring pixels based on connectivity type.
     *
     * @param row    The row index of the current pixel.
     * @param col    The column index of the current pixel.
     * @param maxRow Maximum row index of the image.
     * @param maxCol Maximum column index of the image.
     * @return A list of neighboring pixel points.
     */
    List<PixelPoint> getNeighbors(int row, int col, int maxRow, int maxCol);

}
