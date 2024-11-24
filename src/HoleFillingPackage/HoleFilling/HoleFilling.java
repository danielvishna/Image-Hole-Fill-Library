package HoleFillingPackage.HoleFilling;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.Utility.PixelPoint;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.opencv.core.Mat;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Performs the hole-filling operation using the specified connectivity and weighting function.
 */
public class HoleFilling {

    protected final Mat image;
    protected final HoleFillingPackage.WeightingFunction.IWeightingFunction IWeightingFunction;
    protected final Connectivity connectivity;

    public HoleFilling(Mat image, IWeightingFunction IWeightingFunction, Connectivity connectivity) {
        this.image = image;
        this.IWeightingFunction = IWeightingFunction;
        this.connectivity = connectivity;
    }

    /**
     * Calculates the color value for a hole pixel based on the boundary.
     *
     * @param hole     The hole pixel.
     * @param boundary The set of boundary pixels.
     * @return The calculated color value for the hole pixel.
     */
    private double getPixelColor(PixelPoint hole, HashSet<PixelPoint> boundary) {
        double numerator = 0;
        double denominator = 0;
        for (PixelPoint boundaryPixel : boundary) {
            double weight = this.IWeightingFunction.calculate(boundaryPixel, hole);
            numerator += boundaryPixel.getColor() * weight;
            denominator += weight;
        }
        return numerator / denominator;
    }

    /**
     * Fills the pixels in the hole with calculated grayscale values based on the boundary pixels.
     * This method uses multithreading to improve performance.
     *
     * @param hole A list of pixels representing the hole to be filled.
     * @param boundary A set of boundary pixels surrounding the hole.
     */
    protected void filledPixels(List<PixelPoint> hole, HashSet<PixelPoint> boundary) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        for (PixelPoint holePixel : hole) {
            Future<?> future = executor.submit(() -> {
                double colorValue = this.getPixelColor(holePixel, boundary);
                synchronized (this.image) {
                    this.image.put((int) holePixel.getY(), (int) holePixel.getX(), colorValue);
                }
            });
            futures.add(future);

        }
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

    }

    /**
     * Fills the hole in the image using the provided algorithm.
     *
     * @return The processed image with the hole filled.
     */
    public Mat getFilledImage() {
        Tuple<List<PixelPoint>, HashSet<PixelPoint>> holeBound = this.FindHoleAndBound();
        if (holeBound == null || holeBound.first() == null || holeBound.second() == null) {
            return null;
        }

        List<PixelPoint> hole = holeBound.first();
        HashSet<PixelPoint> boundary = holeBound.second();
        this.filledPixels(hole, boundary);
        return this.image;

    }

    /**
     * Performs a breadth-first search (BFS) to find the hole and its boundary in the image.
     *
     * @param startRow The row index of the starting pixel in the hole.
     * @param startCol The column index of the starting pixel in the hole.
     * @return A tuple containing:
     *         - A list of pixels representing the hole.
     *         - A set of boundary pixels surrounding the hole.
     */
    private Tuple<List<PixelPoint>, HashSet<PixelPoint>> BFS(int startRow, int startCol) {
        Queue<PixelPoint> queue = new LinkedList<>();
        HashSet<PixelPoint> visited = new HashSet<>();
        List<PixelPoint> hole = new ArrayList<>();
        HashSet<PixelPoint> boundary = new HashSet<>();
        PixelPoint start = new PixelPoint(startCol, startRow);

        visited.add(start);
        hole.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            PixelPoint current = queue.poll();
            for (PixelPoint neighbor : this.connectivity.getNeighbors((int) current.getY(), (int) current.getX(), image.height(), image.width())) {
                if (!visited.contains(neighbor)) {
                    AddToHoleOrBoundary(queue, visited, hole, boundary, neighbor);
                }
            }
        }
        return new Tuple<>(hole, boundary);

    }

    /**
     * Determines whether a pixel is part of the hole or the boundary, and adds it to the appropriate collection.
     *
     * @param queue The queue used in the BFS algorithm for traversal.
     * @param visited A set of visited pixels to avoid processing the same pixel multiple times.
     * @param hole A list to store the pixels that belong to the hole.
     * @param boundary A set to store the pixels that belong to the boundary.
     * @param neighbor The pixel being evaluated.
     */
    private void AddToHoleOrBoundary(Queue<PixelPoint> queue, HashSet<PixelPoint> visited, List<PixelPoint> hole, HashSet<PixelPoint> boundary, PixelPoint neighbor) {
        neighbor.setColor(image.get((int) neighbor.getY(), (int) neighbor.getX())[0]);
        if (neighbor.getColor() == -1) {
            hole.add(neighbor);
            queue.add(neighbor);
        } else {
            boundary.add(neighbor);
        }
        visited.add(neighbor);
    }

    /**
     * Finds the hole and its boundary pixels.
     *
     * @return A tuple containing the hole pixels and boundary pixels.
     */
    protected Tuple<List<PixelPoint>, HashSet<PixelPoint>> FindHoleAndBound() {

        for (int c = 0; c < this.image.width(); c++) {
            for (int r = 0; r < this.image.height(); r++) {
                double grayscale = this.image.get(r, c)[0];
                if (grayscale == -1.0) {
                    return this.BFS(r, c);
                }

            }

        }
        return null;
    }

}
