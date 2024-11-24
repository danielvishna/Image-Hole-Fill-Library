package HoleFillingPackage.HoleFilling;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Utility.PixelPoint;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.opencv.core.Mat;

import java.util.*;

/**
 * Extends the HoleFilling class with optimizations for faster processing
 * by using grid-based boundary partitioning.
 */
public class OptimizedHoleFilling extends HoleFilling {

    private int gridSizeX; //How many pixels can be belonged to each grid cell
    private int gridSizeY; //How many pixels can be belonged to each grid cell

    private final int numGridCell; // to how may part divide the boundary

    public OptimizedHoleFilling(Mat image, Connectivity connectivity, IWeightingFunction IWeightingFunction, int numGridCell) {
        super(image, IWeightingFunction, connectivity);
        this.numGridCell = numGridCell;
    }


    /**
     * Divides the boundary into grid cells for optimized processing.
     *
     * @param boundary The set of boundary pixels.
     * @return A map of grid cells to their respective boundary pixels.
     */
    private Map<PixelPoint, List<PixelPoint>> divideBoundaryIntoGrids(HashSet<PixelPoint> boundary) {
        Map<PixelPoint, List<PixelPoint>> grid = new HashMap<>();
        for (PixelPoint boundaryPixel : boundary) {
            int gridX = (int) (boundaryPixel.getX() / gridSizeX);
            int gridY = (int) (boundaryPixel.getY() / gridSizeY);
            PixelPoint gridCell = new PixelPoint(gridX, gridY);
            grid.computeIfAbsent(gridCell, k -> new ArrayList<>()).add(boundaryPixel);
        }
        return grid;
    }

    /**
     * Calculates representative points for grid cells.
     *
     * @param grid A map of grid cells to boundary pixels.
     * @return A set of representative boundary pixels.
     */
    private HashSet<PixelPoint> calculateGridRepresentatives(Map<PixelPoint, List<PixelPoint>> grid) {
        HashSet<PixelPoint> representatives = new HashSet<>();
        for (Map.Entry<PixelPoint, List<PixelPoint>> entry : grid.entrySet()) {
            List<PixelPoint> points = entry.getValue();
            double meanX = 0, meanY = 0, meanColor = 0;
            for (PixelPoint p : points) {
                meanX += p.getX();
                meanY += p.getY();
                meanColor += this.image.get((int) p.getY(), (int) p.getX())[0];
            }
            int size = points.size();
            meanX /= size;
            meanY /= size;
            meanColor /= size;
            representatives.add(new PixelPoint((int) Math.round(meanX), (int) Math.round(meanY), meanColor));
        }
        return representatives;
    }

    @Override
    public Mat getFilledImage() {
        Tuple<List<PixelPoint>, HashSet<PixelPoint>> holeBoundary = FindHoleAndBound();
        if (holeBoundary == null) {
            return null;
        }

        List<PixelPoint> hole = holeBoundary.first();
        HashSet<PixelPoint> boundary = holeBoundary.second();

        PixelPoint gridSize = OptimizedHoleFilling.calculateOptimalCellSize(boundary, this.numGridCell);
        this.gridSizeX = (int) gridSize.getX();
        this.gridSizeY = (int) gridSize.getX();

        Map<PixelPoint, List<PixelPoint>> grid = divideBoundaryIntoGrids(boundary);
        HashSet<PixelPoint> representatives = calculateGridRepresentatives(grid);

        this.filledPixels(hole, representatives);
        return this.image;
    }

    /**
     * Determines the optimal cell size for dividing the boundary into grids.
     *
     * @param boundaryPoints The set of boundary pixels.
     * @param numGridCell              The desired number of grid cells.
     * @return The optimal grid cell size as a PixelPoint.
     */
    private static PixelPoint calculateOptimalCellSize(HashSet<PixelPoint> boundaryPoints, int numGridCell) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (PixelPoint p : boundaryPoints) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }
        int gridSizeX = (int) ((maxX - minX + 1) / Math.sqrt(numGridCell));
        int gridSizeY = (int) ((maxY - minY + 1) / Math.sqrt(numGridCell));

        return new PixelPoint(gridSizeX, gridSizeY);

    }


}

