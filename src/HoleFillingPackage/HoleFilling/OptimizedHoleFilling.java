package HoleFillingPackage.HoleFilling;
import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.opencv.core.Mat;
import java.awt.Point;

import java.util.*;
public class OptimizedHoleFilling extends HoleFilling {

    private final int gridSize;

    public OptimizedHoleFilling(Mat image, Connectivity connectivity, IWeightingFunction IWeightingFunction, int gridSize) {
        super(image, IWeightingFunction, connectivity);
        this.gridSize = gridSize;
    }

    private Map<Point, List<Point>> divideBoundaryIntoGrids(HashSet<Point> boundary) {
        Map<Point, List<Point>> grid = new HashMap<>();
        for (Point boundaryPixel : boundary) {
            int gridX = (int) (boundaryPixel.getX() / gridSize);
            int gridY = (int) (boundaryPixel.getY() / gridSize);
            Point gridCell = new Point(gridX, gridY);
            grid.computeIfAbsent(gridCell, k -> new ArrayList<>()).add(boundaryPixel);
        }
        return grid;
    }

    private List<GridRepresentative> calculateGridRepresentatives(Map<Point, List<Point>> grid) {
        List<GridRepresentative> representatives = new ArrayList<>();
        for (Map.Entry<Point, List<Point>> entry : grid.entrySet()) {
            List<Point> points = entry.getValue();
            double meanX = 0, meanY = 0, meanColor = 0;
            for (Point p : points) {
                meanX += p.getX();
                meanY += p.getY();
                meanColor += this.image.get((int) p.getY(), (int) p.getX())[0];
            }
            int size = points.size();
            meanX /= size;
            meanY /= size;
            meanColor /= size;
            representatives.add(new GridRepresentative(meanX, meanY, meanColor));
        }
        return representatives;
    }

    public Mat getFilledImage() {
        Tuple<List<Point>, HashSet<Point>> holeBoundary = FindHoleAndBound();
        if (holeBoundary == null) {
            return null;
        }

        List<Point> hole = holeBoundary.getFirst();
        HashSet<Point> boundary = holeBoundary.getSecond();

        Map<Point, List<Point>> grid = divideBoundaryIntoGrids(boundary);
        List<GridRepresentative> representatives = calculateGridRepresentatives(grid);

        for (Point holePixel : hole) {
            double numerator = 0, denominator = 0;
            for (GridRepresentative rep : representatives) {
                Point repPoint = new Point((int) rep.meanX, (int) rep.meanY);
                double weight = IWeightingFunction.calculate(repPoint, holePixel);
                numerator += rep.meanColor * weight;
                denominator += weight;
            }
            double filledColor = numerator / denominator;
            image.put((int) holePixel.getY(), (int) holePixel.getX(), filledColor);
        }

        return image;
    }

    private static class GridRepresentative {
        double meanX, meanY, meanColor;

        GridRepresentative(double meanX, double meanY, double meanColor) {
            this.meanX = meanX;
            this.meanY = meanY;
            this.meanColor = meanColor;
        }
    }
}

