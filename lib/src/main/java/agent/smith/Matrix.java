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

    private static void validateNumRows(int numRows) {
        if (numRows < 0) {
            throw new IllegalArgumentException(
                    String.format("'numRows' (%d) has to be a non negative integer", numRows));
        }
    }

    private static void validateNumCols(int numCols) {
        if (numCols < 0) {
            throw new IllegalArgumentException(
                    String.format("'numCols' (%d) has to be a non negative integer", numCols));
        }
    }

    private static void validateNumRowsNumCols(int numRows, int numCols) {
        validateNumRows(numRows);
        validateNumCols(numCols);
    }

    private Matrix(double[] array, int numRows, int numCols) {

        this.numRows = numRows;
        this.numCols = numCols;
        this.length = this.numRows * this.numCols;

        validateNumRowsNumCols(numRows, numCols);

        if (array == null) {
            throw new NullPointerException("'array' cannot be null");
        }

        if (array.length != this.length) {
            throw new IllegalArgumentException(String.format(
                    "Length of 'array' (%d) does not match 'numRows' * 'numCols' (%d)", array.length, this.length));
        }

        this.array = new double[this.length];
        System.arraycopy(array, 0, this.array, 0, this.length);
    }

    private Matrix(double element, int numRows, int numCols) {

        this.numRows = numRows;
        this.numCols = numCols;
        length = this.numRows * this.numCols;

        validateNumRowsNumCols(numRows, numCols);

        array = new double[length];
        Arrays.fill(array, element);
    }

    public static Matrix create(double[] array, int numRows, int numCols) {
        return new Matrix(array, numRows, numCols);
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

    void validateRowIndex(int i) {
        if (numRows == 0) {
            throw new IllegalStateException(
                    String.format("Row index 'i' = (%d) cannot be used as matrix is empty", i));
        }
        if (i < 0 || i >= numRows) {
            throw new IllegalArgumentException(
                    String.format("Row index 'i' = (%d) has to be between 0 and %d", i, Math.max(numRows - 1, 0)));
        }
    }

    void validateColIndex(int j) {
        if (numCols == 0) {
            throw new IllegalStateException(
                    String.format("Col index 'j' = (%d) cannot be used as matrix is empty", j));
        }
        if (j < 0 || j >= numCols) {
            throw new IllegalArgumentException(
                    String.format("Col index 'j' = (%d) has to be between 0 and %d", j, Math.max(numCols - 1, 0)));
        }
    }

    int getIndex(int i, int j) {
        validateRowIndex(i);
        validateColIndex(j);
        return i * numCols + j;
    }

    public double[] getRow(int i) {
        int index = getIndex(i, 0);
        return Arrays.copyOfRange(array, index, index + numCols);
    }

    public double[] getCol(int j) {
        double[] col = new double[numRows];
        for (int i = 0; i < numRows; i++) {
            col[i] = array[getIndex(i, j)];
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

    public static Matrix from(double[][] nestedArray) {

        if (nestedArray == null) {
            throw new NullPointerException("'nestedArray' cannot be null");
        }

        if (nestedArray[0] == null) {
            throw new NullPointerException("'nestedArray[0]' cannot be null");
        }

        int numRows = nestedArray.length;
        int numCols = nestedArray[0].length;

        double[] array = new double[numRows * numCols];
        for (int i=0; i < numRows; i++) {

            if (nestedArray[i] == null) {
                throw new NullPointerException(String.format("'nestedArray[%d]' cannot be null", i));
            }

            if (nestedArray[i].length != numCols) {
                throw new IllegalArgumentException("Inconsistent number of rows for 'nestedArray'");
            }

            System.arraycopy(nestedArray[i], 0, array, i * numCols, numCols);
        }
        return create(array, numRows, numCols);
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

    public static Matrix of(double element, int numRows, int numCols) {
        return new Matrix(element, numRows, numCols);
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

}
