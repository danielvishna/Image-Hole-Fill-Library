package HoleFillingPackage;

import org.opencv.core.Mat;
import java.io.Serializable;


import java.util.*;
public class HoleFilling {


    private final Mat image;
    private final WeightingFunction weightingFunction;
    private final Connectivity connectivity;



    public HoleFilling(Mat image,  WeightingFunction weightingFunction, Connectivity connectivity){
        this.image = image;
        this.weightingFunction = weightingFunction;
        this.connectivity = connectivity;
    }

    private double distance(Point u, Point v, int z, double epsilon){
        double ans = Math.pow(Math.pow(v.getRow() -u.getRow(), 2) + Math.pow(v.getColumn() -u.getColumn(), 2), (double) z / 2);
        return 1 / (ans + epsilon);
    }

    private double culColor(Point hole, HashSet<Point> bound){
        double numerator = 0;
        double denominator = 0;
        for (Point boundaryPixel  : bound) {
            double pixelColor = this.image.get(boundaryPixel .getRow(), boundaryPixel .getColumn())[0];
            double weight  = this.weightingFunction.calculate(boundaryPixel , hole);
            numerator += pixelColor * weight;
            denominator += weight;
        }
        return  numerator / denominator;
    }

    public Mat getFilledImage(){
        Tuple<List<Point>, HashSet<Point>> holeBound = this.FindHoleAndBound();
        List<Point> hole = holeBound.getFirst();
        HashSet<Point> bound = holeBound.getSecond();
        for (Point currHole : hole) {
            this.image.put(currHole.getRow(),  currHole.getColumn(), this.culColor(currHole, bound));
        }
        return this.image;

    }

    private Tuple<List<Point>, HashSet<Point>> BFS(int startRow, int startCol){
        Queue<Point> queue = new LinkedList<>();
        HashSet<Point> visited = new HashSet<>();
        List<Point> hole = new ArrayList<>();
        HashSet<Point> boundary = new HashSet<>();
        Point start = new Point(startRow, startCol);

        visited.add(start);
        hole.add(start);
        queue.add(start);
        while (!queue.isEmpty()){
            Point current = queue.poll();
            for (Point neighbor : this.connectivity.getNeighbors(current.getRow(), current.getColumn(), image.height(), image.width())) {
                if (!visited.contains(neighbor)) {
                    double pixelValue = image.get(neighbor.getRow(), neighbor.getColumn())[0];
                        if(pixelValue == -1){
                            hole.add(neighbor);
                            queue.add(neighbor);
                        }
                        else {
                            boundary.add(neighbor);
                        }
                        visited.add(neighbor);


                }

            }
        }
        Tuple<List<Point>, HashSet<Point>> holeAndBound =  new Tuple<>(hole, boundary);
        return holeAndBound;

    }

    public Tuple<List<Point>, HashSet<Point> >  FindHoleAndBound(){ //todo: change the func to privet

        for (int c = 0; c < this.image.width(); c++) {
            for (int r = 0; r < this.image.height(); r++) {
                double grayscale = this.image.get(r, c)[0];
                if (grayscale == -1.0){
                    Tuple<List<Point>, HashSet<Point>> holeBound = this.BFS(r,c);
                    System.out.println(holeBound.getFirst().size());
                    return holeBound;
                }

            }

        }
        return null; // TODO: do someting here
    }
    
}
