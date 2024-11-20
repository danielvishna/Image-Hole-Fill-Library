package HoleFillingPackage;

import org.opencv.core.Mat;
import java.io.Serializable;


import java.util.*;
public class HoleFilling {


    Mat image;

    class Point extends Tuple {
        Tuple<Integer, Integer> point;
        Point(int row, int col){
            super(row, col);
//            this.point = new Tuple<>(row, col);
        }
        public int getRow() {
            return this.point.getFirst();
        }
        public int getColumn() {
            return this.point.getSecond();
        }
    }


    public HoleFilling(Mat image){
        this.image = image;
    }

    private double distance(Point u, Point v, int z, double epsilon){
        double ans = Math.pow(Math.pow(v.getRow() -u.getRow(), 2) + Math.pow(v.getColumn() -u.getColumn(), 2), (double) z / 2);
        return 1 / (ans + epsilon);
    }

    private double culColor(Point hole, HashSet<Point> bound){
        double mone = 0;
        double mechane = 0;
        for (Point integerIntegerTuple : bound) {
            double pixelColor = this.image.get(integerIntegerTuple.getRow(), integerIntegerTuple.getColumn())[0];
            double tmp = this.distance(integerIntegerTuple, hole, 3, 0.01);
            mone += pixelColor * tmp;
            mechane += tmp;
        }
        return  mone / mechane;
    }

    public Mat getFilledImage(){
        Tuple<List<Point>, HashSet<Point>> holeBound = this.FindHoleAndBound();
        List<Tuple<Integer, Integer>> hole = holeBound.getFirst();
        HashSet<Tuple<Integer, Integer>> bound = holeBound.getSecond();
        for (Tuple<Integer, Integer> currHole : hole) {
            this.image.put(currHole.getRow(),  currHole.getColumn(), this.culColor(currHole, bound));
        }
        return this.image;

    }

    private Tuple<List<Tuple<Integer, Integer>>, HashSet<Tuple<Integer, Integer>>> BFS(int row, int col){
        Queue<Tuple<Integer, Integer>> q = new LinkedList<>();
        HashSet<Tuple<Integer, Integer>> visited = new HashSet<>();
        List<Tuple<Integer, Integer>> hole = new ArrayList<>();
        HashSet<Tuple<Integer, Integer>> bound = new HashSet<>();
        Tuple<Integer, Integer> firstPixelHole = new Tuple<>(row, col);
        visited.add(firstPixelHole);
        hole.add(firstPixelHole);
        q.add(firstPixelHole);
        while (!q.isEmpty()){
            Tuple<Integer, Integer> curr = q.poll();
            for(int c = Math.max(0, curr.getColumn() - 1); c < Math.min(this.image.width(), curr.getColumn() + 2); c ++){
                for(int r = Math.max(0, curr.getRow() - 1); r < Math.min(this.image.height(), curr.getRow() + 2); r ++){
                    Tuple<Integer, Integer> currPixel = new Tuple<>(r, c);
                    if(!visited.contains(currPixel)){
                        if(this.image.get(r, c)[0] == -1){
                            hole.add(currPixel);
                            q.add(currPixel);
                        }
                        else {
                            bound.add(currPixel);
                        }
                        visited.add(currPixel);

                    }
                }

            }
        }
        Tuple<List<Tuple<Integer, Integer>>, HashSet<Tuple<Integer, Integer>>> holeAndBound =  new Tuple<>(hole, bound);
        return holeAndBound;

    }

    public Tuple<List<Point>, HashSet<Point> >  FindHoleAndBound(){ //todo: change the func to privet

        for (int c = 0; c < this.image.width(); c++) {
            for (int r = 0; r < this.image.height(); r++) {
                double grayscale = this.image.get(r, c)[0];
                if (grayscale == -1.0){
                    Tuple<List<Point>, HashSet<Point>> holeBound = this.BFS(r,c);
                    System.out.println(holeBound.first.size());
                    return holeBound;
                }

            }

        }
        return null; // TODO: do someting here
    }
    
}
