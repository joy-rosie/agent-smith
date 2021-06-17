package agent.smith;


// Copying arrays:
// https://stackoverflow.com/questions/1697250/difference-between-various-array-copy-methods

import java.util.Arrays;
import java.util.Objects;
import java.lang.Math;
import java.util.Random;
//import java.util.stream.IntStream;

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

    public static Matrix create(int numRows, int numCols) throws MatrixIllegalArgumentException {
        return Matrix.of(Double.NaN, numRows, numCols);
    }

    private static void validateMatricesNonNull(Matrix... matrices) throws MatrixIllegalArgumentException {
        for (Matrix matrix: matrices) {
            if (matrix == null) {
                throw new MatrixIllegalArgumentException("Input matrix cannot be null");
            }
        }
    }

    public static Matrix create(Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return Matrix.create(matrix.numRows, matrix.numCols);
    }

    public static Matrix ofZeros(int numRows, int numCols) throws MatrixIllegalArgumentException {
        return Matrix.of(0, numRows, numCols);
    }

    public static Matrix ofZeros(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.ofZeros(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofZeros(Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return Matrix.ofZeros(matrix.numRows, matrix.numCols);
    }

    public static Matrix ofOnes(int numRows, int numCols) throws MatrixIllegalArgumentException {
        return Matrix.of(1, numRows, numCols);
    }

    public static Matrix ofOnes(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.ofOnes(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofOnes(Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return Matrix.ofOnes(matrix.numRows, matrix.numCols);
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
                row[colIndex] = String.format(format, array[getIndex(rowIndex, colIndex)]);
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

    public Matrix set(int rowIndex, int colIndex, double value) throws MatrixIllegalArgumentException {
        this.array[getIndex(rowIndex, colIndex)] = value;
        return this;
    }

    public Matrix setDiagonal(double value) {
        for (int index = 0, n = Math.min(this.numRows, this.numCols); index < n; index++) {
            this.set(index, index, value);
        }
        return this;
    }

    public static Matrix instanceOfEye(int numRows, int numCols) throws MatrixIllegalArgumentException {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        matrix.setDiagonal(1);
        return matrix;
    }

    public static Matrix instanceOfEye(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.instanceOfEye(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfEye(Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return Matrix.instanceOfEye(matrix.numRows, matrix.numCols);
    }

    public static Matrix instanceOfRandom(Random r, int numRows, int numCols) throws MatrixIllegalArgumentException {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        for (int index = 0; index < matrix.length; index++) {
            matrix.array[index] = r.nextGaussian();
        }
        return matrix;
    }

    public static Matrix instanceOfRandom(Random r, int numRowsAndCols) throws MatrixIllegalArgumentException {
        return instanceOfRandom(r, numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(Random r, Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return instanceOfRandom(r, matrix.numRows, matrix.numCols);
    }

    public static Matrix instanceOfRandom(long seed, int numRows, int numCols) throws MatrixIllegalArgumentException {
        Random r = new Random(seed);
        return instanceOfRandom(r, numRows, numCols);
    }

    public static Matrix instanceOfRandom(long seed, int numRowsAndCols) throws MatrixIllegalArgumentException {
        return instanceOfRandom(seed, numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(long seed, Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return instanceOfRandom(seed, matrix.numRows, matrix.numCols);
    }

    public static Matrix instanceOfRandom(int numRows, int numCols) throws MatrixIllegalArgumentException {
        Random r = new Random();
        return instanceOfRandom(r, numRows, numCols);
    }

    public static Matrix instanceOfRandom(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return instanceOfRandom(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfRandom(Matrix matrix) throws MatrixIllegalArgumentException {
        validateMatricesNonNull(matrix);
        return instanceOfRandom(matrix.numRows, matrix.numCols);
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

    public Matrix add(double value) {
        for (int index = 0; index < this.length; index++) {
            this.array[index] += value;
        }
        return this;
    }

    private static void validateMatricesDimensionAdd(Matrix result, Matrix... matrices)
            throws MatrixIllegalArgumentException {

        validateMatricesNonNull(result);
        int numRows = result.numRows;
        int numCols = result.numCols;

        for (Matrix matrix : matrices) {
            validateMatricesNonNull(matrix);
            if (numRows != matrix.numRows || numCols != matrix.numCols) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for adding matrices");
            }
        }

    }

    public Matrix add(Matrix... matrices) throws MatrixIllegalArgumentException {

        validateMatricesDimensionAdd(this, matrices);

        for (int index = 0; index < this.length; index++) {
            double addValue = 0;
            for (Matrix matrix : matrices) {
                addValue += matrix.array[index];
            }
            this.array[index] += addValue;
        }

        return this;
    }

    private static void validateMatricesNonEmpty(Matrix... matrices) {
        if (matrices.length < 1) {
            throw new MatrixIllegalArgumentException("Need at least one matrix for summing");
        }
    }

    public static Matrix sum(Matrix... matrices) throws MatrixIllegalArgumentException {
        validateMatricesNonEmpty(matrices);
        validateMatricesNonNull(matrices[0]);
        Matrix result = Matrix.ofZeros(matrices[0]);
        return result.add(matrices);
    }

    public Matrix multiply(double value) {
        for (int index = 0; index < this.length; index++) {
            this.array[index] *= value;
        }
        return this;
    }

    private static void validateDimensionsProd(Matrix... matrices) {

        validateMatricesNonEmpty(matrices);
        validateMatricesNonNull(matrices);

        int rightNumRows = matrices[matrices.length - 1].numRows;
        for (int matrixIndex = matrices.length - 2; matrixIndex >= 0; matrixIndex--) {
            int leftNumCols = matrices[matrixIndex].numCols;
            if (leftNumCols != rightNumRows) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for taking product of matrices");
            }
            rightNumRows = matrices[matrixIndex].numRows;
        }

    }

    public static Matrix prod(Matrix left, Matrix right) {

        validateDimensionsProd(left, right);
        Matrix result = Matrix.create(left.numRows, right.numCols);

        for (int rowIndex = 0; rowIndex < result.numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < result.numCols; colIndex++) {
                double value = 0;
                for (int index = 0; index < left.numCols; index++) {
                    value += left.get(rowIndex, index) * right.get(index, colIndex);
                }
                result.set(rowIndex, colIndex, value);
            }
        }

        return result;
    }

}