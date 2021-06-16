package agent.smith;


// Copying arrays:
// https://stackoverflow.com/questions/1697250/difference-between-various-array-copy-methods

import java.util.Arrays;
import java.util.Objects;
import java.lang.Math;
import java.util.Random;
import java.util.stream.IntStream;

public class Matrix {

    private final double[] array;
    private final int numRows;
    private final int numCols;
    private final int length;

    private void validateNumRows() throws MatrixIllegalArgumentException {
        if (this.numRows <= 0) {
            throw new MatrixIllegalArgumentException(
                    String.format("'numRows' (%d) has to be a positive integer", this.numRows));
        }
    }

    private void validateNumCols() throws MatrixIllegalArgumentException {
        if (this.numCols <= 0) {
            throw new MatrixIllegalArgumentException(
                    String.format("'numCols' (%d) has to be a positive integer", this.numCols));
        }
    }

    private void validateNumRowsNumCols() throws MatrixIllegalArgumentException {
        validateNumRows();
        validateNumCols();
    }

    private Matrix(double value, int numRows, int numCols) throws MatrixIllegalArgumentException {

        this.numRows = numRows;
        this.numCols = numCols;
        this.length = this.numRows * this.numCols;

        validateNumRowsNumCols();

        this.array = new double[length];
        Arrays.fill(this.array, value);
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
        return Matrix.of(Double.NaN, numRows, numCols);
    }

    public static Matrix ofZeros(int numRows, int numCols) {
        return Matrix.of(0, numRows, numCols);
    }

    public static Matrix ofZeros(int numRowsAndCols) {
        return Matrix.ofZeros(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofOnes(int numRows, int numCols) {
        return Matrix.of(1, numRows, numCols);
    }

    public static Matrix ofOnes(int numRowsAndCols) {
        return Matrix.ofOnes(numRowsAndCols, numRowsAndCols);
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
        System.arraycopy(this.array, 0, newArray, 0, this.length);
        return newArray;
    }

    public int getNumRows() {
        return this.numRows;
    }

    public int getNumCols() {
        return this.numCols;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return this.numRows == matrix.numRows && this.numCols == matrix.numCols &&
                Arrays.equals(this.array, matrix.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.numRows, this.numCols);
        result = 31 * result + Arrays.hashCode(this.array);
        return result;
    }

    public String toString(String format, String rowDelimiter, String colDelimiter) {
        String[] string = new String[this.numRows];
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            String[] row = new String[this.numCols];
            for (int colIndex = 0; colIndex < this.numCols; colIndex++) {
                row[colIndex] = String.format(format,  array[getIndex(rowIndex, colIndex)]);
            }
            string[rowIndex] = String.join(colDelimiter, row);
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

//    private IntStream getRowIndicesStream() {
//        return IntStream.range(0, this.numRows);
//    }
//
//    private IntStream getColIndicesStream() {
//        return IntStream.range(0, this.numCols);
//    }
//
//    private IntStream getIndicesStream() {
//        return IntStream.range(0, this.length);
//    }

    private void validateRowIndex(int rowIndex) throws MatrixIllegalArgumentException {
        if (rowIndex < 0 || rowIndex >= this.numRows) {
            throw new MatrixIllegalArgumentException(
                    String.format("'rowIndex' = (%d) has to be between 0 and %d", rowIndex, this.numRows - 1));
        }
    }

    private void validateColIndex(int colIndex) throws MatrixIllegalArgumentException {
        if (colIndex < 0 || colIndex >= this.numCols) {
            throw new MatrixIllegalArgumentException(
                    String.format("'colIndex' = (%d) has to be between 0 and %d", colIndex, this.numCols - 1));
        }
    }

    private void validateIndex(int index) throws MatrixIllegalArgumentException {
        if (index < 0 || index >= this.length) {
            throw new MatrixIllegalArgumentException(
                    String.format("'index' = (%d) has to be between 0 and %d", index, this.length - 1));
        }
    }

    int getIndex(int rowIndex, int colIndex) throws MatrixIllegalArgumentException {
        validateRowIndex(rowIndex);
        validateColIndex(colIndex);
        return rowIndex * this.numCols + colIndex;
    }

    int getRowIndex(int index) throws MatrixIllegalArgumentException {
        validateIndex(index);
        return index / this.numCols;
    }

    int getColIndex(int index) throws MatrixIllegalArgumentException {
        validateIndex(index);
        return index % this.numCols;
    }

    public double get(int rowIndex, int colIndex) throws MatrixIllegalArgumentException {
        return this.array[getIndex(rowIndex, colIndex)];
    }

    public void set(int rowIndex, int colIndex, double value) throws MatrixIllegalArgumentException {
        this.array[getIndex(rowIndex, colIndex)] = value;
    }

    public void setDiagonal(double value) {
        for (int index = 0, n = Math.min(this.numRows, this.numCols); index < n; index++) {
            this.set(index, index, value);
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
        for (int index = 0; index < matrix.length; index++) {
            matrix.array[index] = r.nextGaussian();
        }
        return matrix;
    }

    public static Matrix instanceOfRandom(Random r, int numRowsAndCols) {
        return instanceOfRandom(r, numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(long seed, int numRows, int numCols) {
        Random r = new Random(seed);
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

    public double[] getRow(int rowIndex) throws MatrixIllegalArgumentException {
        int index = getIndex(rowIndex, 0);
        return Arrays.copyOfRange(this.array, index, index + this.numCols);
    }

    public double[] getCol(int colIndex) throws MatrixIllegalArgumentException {
        double[] col = new double[this.numRows];
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            col[rowIndex] = this.array[getIndex(rowIndex, colIndex)];
        }
        return col;
    }

    public Matrix copy() {
        return create(this.array, this.numRows, this.numCols);
    }

    static Matrix add(Matrix left, Matrix right, Matrix result) throws MatrixIllegalArgumentException {

        if (left == null || right == null) {
            throw new MatrixIllegalArgumentException("Matrices to be added cannot be null");
        }

        if (left.numRows != right.numRows || left.numCols != right.numCols) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Dimension mismatch (left vs right), 'numRows': %d vs %d and 'numCols': %d vs %d",
                    left.numRows, right.numRows, left.numCols, right.numCols));
        }

        if (result == null) {
            result = Matrix.create(left.numRows, left.numCols);
        }

        if (left.numRows != result.numRows || left.numCols != result.numCols) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Dimension mismatch (left vs result), 'numRows': %d vs %d and 'numCols': %d vs %d",
                    left.numRows, result.numRows, left.numCols, result.numCols));
        }

        for (int index = 0; index < result.length; index++) {
            result.array[index] = left.array[index] + right.array[index];
        }

        return result;

    }

    public Matrix add(Matrix other) throws MatrixIllegalArgumentException {
        return Matrix.add(this, other, this);
    }

    public static Matrix add(Matrix left, Matrix right) throws MatrixIllegalArgumentException {
        return Matrix.add(left, right, null);
    }

    static Matrix add(Matrix left, double right, Matrix result) throws MatrixIllegalArgumentException {

        if (left == null) {
            throw new MatrixIllegalArgumentException("Matrix to be added cannot be null");
        }

        if (result == null) {
            result = Matrix.create(left.numRows, left.numCols);
        }

        if (left.numRows != result.numRows || left.numCols != result.numCols) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Dimension mismatch (left vs result), 'numRows': %d vs %d and 'numCols': %d vs %d",
                    left.numRows, result.numRows, left.numCols, result.numCols));
        }

        for (int index = 0; index < result.length; index++) {
            result.array[index] = left.array[index] + right;
        }

        return result;

    }

    public Matrix add(double other) throws MatrixIllegalArgumentException {
        return Matrix.add(this, other, this);
    }

    public static Matrix add(Matrix left, double right) throws MatrixIllegalArgumentException {
        return Matrix.add(left, right, null);
    }

    public static Matrix add(double left, Matrix right) throws MatrixIllegalArgumentException {
        return Matrix.add(right, left, null);
    }

}
