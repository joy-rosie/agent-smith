package agent.smith;


// Copying arrays:
// https://stackoverflow.com/questions/1697250/difference-between-various-array-copy-methods

import java.util.Arrays;
import java.util.Objects;
import java.lang.Math;
import java.util.Random;

public class Matrix {

    private final double[] array;
    private final int numRows;
    private final int numCols;
    private final int length;

    private void validateNumRows() throws MatrixIllegalArgumentException {
        if (numRows <= 0) {
            throw new MatrixIllegalArgumentException(
                    String.format("'numRows' (%d) has to be a positive integer", numRows));
        }
    }

    private void validateNumCols() throws MatrixIllegalArgumentException {
        if (numCols <= 0) {
            throw new MatrixIllegalArgumentException(
                    String.format("'numCols' (%d) has to be a positive integer", numCols));
        }
    }

    private void validateNumRowsNumCols() throws MatrixIllegalArgumentException {
        validateNumRows();
        validateNumCols();
    }

    private Matrix(double value, int numRows, int numCols) throws MatrixIllegalArgumentException {

        this.numRows = numRows;
        this.numCols = numCols;
        length = this.numRows * this.numCols;

        validateNumRowsNumCols();

        array = new double[length];
        Arrays.fill(array, value);
    }

    public static Matrix of(double element, int numRows, int numCols) {
        return new Matrix(element, numRows, numCols);
    }

    private Matrix(double[] array, int numRows, int numCols) throws MatrixIllegalArgumentException {

        this.numRows = numRows;
        this.numCols = numCols;
        this.length = this.numRows * this.numCols;

        validateNumRowsNumCols();

        if (array == null) {
            throw new MatrixIllegalArgumentException("'array' cannot be null");
        }

        if (array.length != this.length) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Length of 'array' (%d) does not match 'numRows' * 'numCols' (%d)", array.length, this.length));
        }

        this.array = new double[this.length];
        System.arraycopy(array, 0, this.array, 0, this.length);
    }

    public static Matrix create(double[] array, int numRows, int numCols) throws MatrixIllegalArgumentException {
        return new Matrix(array, numRows, numCols);
    }

    public static Matrix create(int numRows, int numCols) {
        return of(Double.NaN, numRows, numCols);
    }

    public static Matrix ofZeros(int numRows, int numCols) {
        return of(0, numRows, numCols);
    }

    public static Matrix ofZeros(int numRowsAndCols) {
        return ofZeros(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofOnes(int numRows, int numCols) {
        return of(1, numRows, numCols);
    }

    public static Matrix ofOnes(int numRowsAndCols) {
        return ofOnes(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix from(double[][] nestedArray) throws MatrixIllegalArgumentException {

        if (nestedArray == null) {
            throw new MatrixIllegalArgumentException("'nestedArray' cannot be null");
        }

        int numRows = nestedArray.length;
        if (numRows == 0) {
            throw new MatrixIllegalArgumentException("'nestedArray' cannot be empty");
        }

        if (nestedArray[0] == null) {
            throw new MatrixIllegalArgumentException("'nestedArray[0]' cannot be null");
        }

        int numCols = nestedArray[0].length;
        if (numCols == 0) {
            throw new MatrixIllegalArgumentException("'nestedArray' cannot be empty");
        }

        double[] array = new double[numRows * numCols];
        for (int indexRow = 0; indexRow < numRows; indexRow++) {

            if (nestedArray[indexRow] == null) {
                throw new MatrixIllegalArgumentException(String.format("'nestedArray[%d]' cannot be null", indexRow));
            }

            if (nestedArray[indexRow].length != numCols) {
                throw new MatrixIllegalArgumentException("Inconsistent number of rows for 'nestedArray'");
            }

            System.arraycopy(nestedArray[indexRow], 0, array, indexRow * numCols, numCols);
        }
        return create(array, numRows, numCols);
    }

    public double[] getArray() {
        double[] newArray = new double[length];
        System.arraycopy(array, 0, newArray, 0, length);
        return newArray;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    void validateRowIndex(int rowIndex) throws MatrixIllegalArgumentException {
        if (rowIndex < 0 || rowIndex >= numRows) {
            throw new MatrixIllegalArgumentException(
                    String.format("'rowIndex' = (%d) has to be between 0 and %d", rowIndex, this.numRows - 1));
        }
    }

    void validateColIndex(int colIndex) throws MatrixIllegalArgumentException {
        if (colIndex < 0 || colIndex >= numCols) {
            throw new MatrixIllegalArgumentException(
                    String.format("'colIndex' = (%d) has to be between 0 and %d", colIndex, this.numCols - 1));
        }
    }

    void validateIndex(int index) throws MatrixIllegalArgumentException {
        if (index < 0 || index >= this.length) {
            throw new MatrixIllegalArgumentException(
                    String.format("'index' = (%d) has to be between 0 and %d", index, this.length - 1));
        }
    }

    int getIndex(int rowIndex, int colIndex) throws MatrixIllegalArgumentException {
        validateRowIndex(rowIndex);
        validateColIndex(colIndex);
        return rowIndex * numCols + colIndex;
    }

    int getRowIndex(int index) throws MatrixIllegalArgumentException {
        validateIndex(index);
        return index / numCols;
    }

    int getColIndex(int index) throws MatrixIllegalArgumentException {
        validateIndex(index);
        return index % numCols;
    }

    public double[] getRow(int rowIndex) throws MatrixIllegalArgumentException {
        int index = getIndex(rowIndex, 0);
        return Arrays.copyOfRange(array, index, index + numCols);
    }

    public double[] getCol(int colIndex) throws MatrixIllegalArgumentException {
        double[] col = new double[numRows];
        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            col[rowIndex] = array[getIndex(rowIndex, colIndex)];
        }
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return numRows == matrix.numRows && numCols == matrix.numCols && Arrays.equals(array, matrix.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(numRows, numCols);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    public String toString(String format, String rowDelimiter, String colDelimiter) {
        String[] string = new String[numRows];
        for (int i = 0; i < numRows; i++) {
            String[] row = new String[numCols];
            for (int j = 0; j < numCols; j++) {
                row[j] = String.format(format,  array[getIndex(i, j)]);
            }
            string[i] = String.join(colDelimiter, row);
        }
        return "Matrix{" + String.join(rowDelimiter, string) + "}";
    }

    public String toString(String format) {
        return toString(format, "\n       ", " ");
    }

    @Override
    public String toString() {
        return toString("%.4e");
    }

    public Matrix copy() {
        return create(array, numRows, numCols);
    }

    public double get(int i, int j) {
        return array[getIndex(i, j)];
    }

    public void set(int i, int j, double element) {
        array[getIndex(i, j)] = element;
    }

    public void setDiagonal(double value) {
        for (int i = 0, n = Math.min(numRows, numCols); i < n; i++) {
            set(i, i, value);
        }
    }

    public static Matrix instanceOfEye(int numRows, int numCols) {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        matrix.setDiagonal(1);
        return matrix;
    }

    public static Matrix instanceOfEye(int numRowsAndCols) {
        return Matrix.instanceOfEye(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(Random r, int numRows, int numCols) {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        for (int i = 0; i < matrix.numRows; i++) {
            for (int j = 0; j < matrix.numCols; j++) {
                matrix.set(i, j, r.nextGaussian());
            }
        }
        return matrix;
    }

    public static Matrix instanceOfRandom(Random r, int numRowsAndCols) {
        return instanceOfRandom(r, numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(long seed, int numRows, int numCols) {
        Random r = new Random();
        r.setSeed(seed);
        return instanceOfRandom(r, numRows, numCols);
    }

    public static Matrix instanceOfRandom(long seed, int numRowsAndCols) {
        return instanceOfRandom(seed, numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(int numRows, int numCols) {
        Random r = new Random();
        return instanceOfRandom(r, numRows, numCols);
    }

    public static Matrix instanceOfRandom(int numRowsAndCols) {
        return instanceOfRandom(numRowsAndCols, numRowsAndCols);
    }

//    public static Matrix add(Matrix A, Matrix B) {
//        if (numRows != other.numRows || numCols != other.numCols) {
//            throw new MatrixIllegalArgumentException(String.format(
//                    "Dimension mismatch, numRows: %d vs %d and numCols: %d vs %d",
//                    numRows, other.numRows, numCols, other.numCols));
//        }
//        Matrix matrix = Matrix.create(numRows, numCols);
//        for (int index = 0; index < matrix.length; index++) {
//            matrix.array[index] = array[index] + other.array[index];
//        }
//        return matrix;
//    }

}
