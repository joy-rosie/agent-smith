package agent.smith;


public class Matrix {

    private final double [][] array;
    private final int numRows;
    private final int numCols;

    private static double[][] copyArray(double[][] array, int numRows, int numCols) {
        double[][] arrayCopy = new double[numRows][numCols];
        for (int i=0; i < numRows; i++) {
//            if (array[i].length != numCols) {
//                throw new AssertionError("Inconsistent row sizes");
//            }
            // Copy array to make sure we do not reference input array
            // https://stackoverflow.com/questions/1697250/difference-between-various-array-copy-methods
            System.arraycopy(array[i], 0, arrayCopy[i], 0, numCols);
        }
        return arrayCopy;
    }

    public Matrix(Matrix matrix) {
        numRows = matrix.getNumRows();
        numCols = matrix.getNumCols();
        array = copyArray(matrix.viewArray(), numRows, numCols);
    }

    public Matrix(double[][] array) {
        numRows = array.length;
        numCols = array[0].length;
        this.array = copyArray(array, numRows, numCols);
    }

    public double[][] viewArray() {
        return array;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }
}
