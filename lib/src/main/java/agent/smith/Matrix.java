package agent.smith;


// Copying arrays:
// https://stackoverflow.com/questions/1697250/difference-between-various-array-copy-methods

import java.util.Arrays;
import java.util.Objects;

public class Matrix {

    private final double[] array;
    private final int numRows;
    private final int numCols;
    private final int length;

    private Matrix(double[] array, int numRows, int numCols) {

        this.numRows = numRows;
        this.numCols = numCols;
        this.length = this.numRows * this.numCols;

        if (this.numRows < 0) {
            throw new IllegalArgumentException(
                    String.format("'numRows' (%d) has to be a non negative integer", this.numRows));
        }

        if (this.numCols < 0) {
            throw new IllegalArgumentException(
                    String.format("'numCols' (%d) has to be a non negative integer", this.numCols));
        }

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

    public static Matrix from(double[] array, int numRows, int numCols) {
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
        return from(array, numRows, numCols);
    }

    public Matrix copy() {
        return from(array, numRows, numCols);
    }
}
