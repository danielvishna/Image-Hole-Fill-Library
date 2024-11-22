package HoleFillingPackage.HoleFilling;

import java.util.concurrent.*;

import HoleFillingPackage.Connectivity.Connectivity;
import HoleFillingPackage.WeightingFunction.IWeightingFunction;
import org.opencv.core.Mat;

import java.awt.Point;


import java.util.*;

public class HoleFilling {


    private final Mat image;
    private final HoleFillingPackage.WeightingFunction.IWeightingFunction IWeightingFunction;
    private final Connectivity connectivity;


    public HoleFilling(Mat image, IWeightingFunction IWeightingFunction, Connectivity connectivity) {
        this.image = image;
        this.IWeightingFunction = IWeightingFunction;
        this.connectivity = connectivity;
    }


    private double culColor(Point hole, HashSet<Point> boundary) {
        double numerator = 0;
        double denominator = 0;
        for (Point boundaryPixel : boundary) {
            double pixelColor = this.image.get((int) boundaryPixel.getY(), (int) boundaryPixel.getX())[0];
            double weight = this.IWeightingFunction.calculate(boundaryPixel, hole);
            numerator += pixelColor * weight;
            denominator += weight;
        }
        return numerator / denominator;
    }

    public Mat getFilledImage() {
        Tuple<List<Point>, HashSet<Point>> holeBound = this.FindHoleAndBound();
        if (holeBound == null || holeBound.getFirst() == null || holeBound.getSecond() == null) {
            return null;
        }

        List<Point> hole = holeBound.getFirst();
        HashSet<Point> boundary = holeBound.getSecond();
        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>();
        for (Point holePixel : hole) {
            Future<?> future = executor.submit(() -> {
                double colorValue = this.culColor(holePixel, boundary);
                synchronized (this.image) {
                    this.image.put((int) holePixel.getY(), (int) holePixel.getX(), colorValue);
                }
            });
            futures.add(future);

        }
        // Wait for all threads to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor
        executor.shutdown();

        return this.image;

    }

    private Tuple<List<Point>, HashSet<Point>> BFS(int startRow, int startCol) {
        Queue<Point> queue = new LinkedList<>();
        HashSet<Point> visited = new HashSet<>();
        List<Point> hole = new ArrayList<>();
        HashSet<Point> boundary = new HashSet<>();
        Point start = new Point(startCol, startRow);

        visited.add(start);
        hole.add(start);
        queue.add(start);
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            for (Point neighbor : this.connectivity.getNeighbors((int) current.getY(), (int) current.getX(), image.height(), image.width())) {
                if (!visited.contains(neighbor)) {
                    AddToHoleOrBoundary(queue, visited, hole, boundary, neighbor);
                }
            }
        }
        return new Tuple<>(hole, boundary);

    }

    private void AddToHoleOrBoundary(Queue<Point> queue, HashSet<Point> visited, List<Point> hole, HashSet<Point> boundary, Point neighbor) {
        double pixelValue = image.get((int) neighbor.getY(), (int) neighbor.getX())[0];
        if (pixelValue == -1) {
            hole.add(neighbor);
            queue.add(neighbor);
        } else {
            boundary.add(neighbor);
        }
        visited.add(neighbor);
    }

    private Tuple<List<Point>, HashSet<Point>> FindHoleAndBound() {

        for (int c = 0; c < this.image.width(); c++) {
            for (int r = 0; r < this.image.height(); r++) {
                double grayscale = this.image.get(r, c)[0];
                if (grayscale == -1.0) {
                    return this.BFS(r, c);
                }

            }

        }
        return null; // TODO: do someting here
    }

}
