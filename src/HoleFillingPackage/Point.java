package HoleFillingPackage;

class Point extends Tuple<Integer, Integer> {
    // Constructor
    public Point(int row, int col) {
        super(row, col); // Call the Tuple constructor
    }

    // Access row (first element)
    public int getRow() {
        return getFirst(); // Directly call the getFirst() method from Tuple
    }

    // Access column (second element)
    public int getColumn() {
        return getSecond(); // Directly call the getSecond() method from Tuple
    }
}
