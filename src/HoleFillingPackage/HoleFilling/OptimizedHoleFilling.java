package HoleFillingPackage.HoleFilling;
import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.opencv.core.Mat;
import HoleFillingPackage.PixelPoint;


import java.util.*;
public class OptimizedHoleFilling extends HoleFilling {

    private int gridSizeX;
    private int gridSizeY;

    private final int k;

    public OptimizedHoleFilling(Mat image, Connectivity connectivity, IWeightingFunction IWeightingFunction, int k) {
        super(image, IWeightingFunction, connectivity);
        this.k = k;
    }

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

    public Mat getFilledImage() {
        Tuple<List<PixelPoint>, HashSet<PixelPoint>> holeBoundary = FindHoleAndBound();
        if (holeBoundary == null) {
            return null;
        }

        List<PixelPoint> hole = holeBoundary.first();
        HashSet<PixelPoint> boundary = holeBoundary.second();

        PixelPoint gridSize = OptimizedHoleFilling.calculateOptimalCellSize(boundary, this.k);
        this.gridSizeX = (int) gridSize.getX();
        this.gridSizeY = (int) gridSize.getX();

        Map<PixelPoint, List<PixelPoint>> grid = divideBoundaryIntoGrids(boundary);
        HashSet<PixelPoint> representatives = calculateGridRepresentatives(grid);

        this.filledPixels(hole, representatives);
        return this.image;
    }

    public static PixelPoint calculateOptimalCellSize(HashSet<PixelPoint> boundaryPoints, int k) {
        // Get bounding box of boundary points
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (PixelPoint p : boundaryPoints) {
            minX = Math.min(minX, p.x);
            minY = Math.min(minY, p.y);
            maxX = Math.max(maxX, p.x);
            maxY = Math.max(maxY, p.y);
        }
        int gridSizeX = (int)((maxX - minX + 1) / Math.sqrt(k));
        int gridSizeY = (int)((maxY - minY + 1) / Math.sqrt(k));

        return new PixelPoint(gridSizeX, gridSizeY);

    }



}

