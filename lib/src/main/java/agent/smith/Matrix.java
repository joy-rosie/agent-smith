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
    private static final double MAX_TOLERANCE = 1e-15;

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
        this.validateNumRows();
        this.validateNumCols();
    }

    private Matrix(double value, int numRows, int numCols) throws MatrixIllegalArgumentException {

        this.numRows = numRows;
        this.numCols = numCols;
        this.length = this.numRows * this.numCols;

        this.validateNumRowsNumCols();

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

        this.validateNumRowsNumCols();

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
        Matrix.validateMatricesNonNull(matrix);
        return Matrix.create(matrix.numRows, matrix.numCols);
    }

    public static Matrix ofZeros(int numRows, int numCols) throws MatrixIllegalArgumentException {
        return Matrix.of(0, numRows, numCols);
    }

    public static Matrix ofZeros(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.ofZeros(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofZeros(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
        return Matrix.ofZeros(matrix.numRows, matrix.numCols);
    }

    public static Matrix ofOnes(int numRows, int numCols) throws MatrixIllegalArgumentException {
        return Matrix.of(1, numRows, numCols);
    }

    public static Matrix ofOnes(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.ofOnes(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix ofOnes(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
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
        this.validateRowIndex(rowIndex);
        this.validateColIndex(colIndex);
        return rowIndex * this.numCols + colIndex;
    }

    int getRowIndex(int index) throws MatrixIllegalArgumentException {
        this.validateIndex(index);
        return index / this.numCols;
    }

    int getColIndex(int index) throws MatrixIllegalArgumentException {
        this.validateIndex(index);
        return index % this.numCols;
    }

    public double get(int rowIndex, int colIndex) throws MatrixIllegalArgumentException {
        return this.array[getIndex(rowIndex, colIndex)];
    }

    private Matrix setToThis(int rowIndex, int colIndex, double value) throws MatrixIllegalArgumentException {
        this.array[getIndex(rowIndex, colIndex)] = value;
        return this;
    }

    public Matrix set(int rowIndex, int colIndex, double value) throws MatrixIllegalArgumentException {
        Matrix result = this.copy();
        return result.setToThis(rowIndex, colIndex, value);
    }

    public Matrix getDiagonal() {
        Matrix diagonal = Matrix.create(Math.min(this.numRows, this.numCols), 1);
        for (int index = 0; index < diagonal.length; index++) {
            diagonal.array[index] = this.get(index, index);
        }
        return diagonal;
    }

    private Matrix setDiagonalToThis(double value) {
        for (int index = 0, n = Math.min(this.numRows, this.numCols); index < n; index++) {
            this.setToThis(index, index, value);
        }
        return this;
    }

    public Matrix setDiagonal(double value) {
        Matrix result = this.copy();
        return result.setDiagonalToThis(value);
    }

    private static void validateVector(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
        if (matrix.numRows != 1 && matrix.numCols != 1) {
            throw new MatrixIllegalArgumentException(String.format(
                    "'numRows' = (%d) or 'numCols' = (%d) has to be 1 to be vector", matrix.numRows, matrix.numCols));
        }
    }

    private Matrix setDiagonalToThis(Matrix vector) throws MatrixIllegalArgumentException {

        Matrix.validateVector(vector);
        int diagonalLength = Math.min(this.numRows, this.numCols);
        if (vector.length != diagonalLength) {
            throw new MatrixIllegalArgumentException("Dimension mismatch for 'diagonal'");
        }

        for (int index = 0; index < diagonalLength; index++) {
            this.setToThis(index, index, vector.array[index]);
        }
        return this;
    }

    public Matrix setDiagonal(Matrix vector) {
        Matrix result = this.copy();
        return result.setDiagonalToThis(vector);
    }

    public Matrix diagonalizeToMatrix() {
        Matrix.validateVector(this);
        int numRowsAndCols = Math.max(this.numRows, this.numCols);
        Matrix result = Matrix.ofZeros(numRowsAndCols);
        return result.setDiagonalToThis(this);
    }

    public static Matrix instanceOfEye(int numRows, int numCols) throws MatrixIllegalArgumentException {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        return matrix.setDiagonalToThis(1);
    }

    public static Matrix instanceOfEye(int numRowsAndCols) throws MatrixIllegalArgumentException {
        return Matrix.instanceOfEye(numRowsAndCols, numRowsAndCols);
    }

    public static Matrix instanceOfEye(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
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
        Matrix.validateMatricesNonNull(matrix);
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
        Matrix.validateMatricesNonNull(matrix);
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
        Matrix.validateMatricesNonNull(matrix);
        return instanceOfRandom(matrix.numRows, matrix.numCols);
    }

    public Matrix getRow(int rowIndex) throws MatrixIllegalArgumentException {
        int index = this.getIndex(rowIndex, 0);
        double[] row = Arrays.copyOfRange(this.array, index, index + this.numCols);
        return Matrix.create(row, 1, this.numCols);
    }

    public Matrix getCol(int colIndex) throws MatrixIllegalArgumentException {
        double[] col = new double[this.numRows];
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            col[rowIndex] = this.get(rowIndex, colIndex);
        }
        return Matrix.create(col, this.numRows, 1);
    }

    private void validateSetRow(Matrix row) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(row);
        if (row.numRows != 1) {
            throw new MatrixIllegalArgumentException(String.format(
                    "'numRows' = (%d) has to be 1 for 'row'", row.numRows));
        }
        if (this.numCols != row.numCols) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Dimension mismatch for 'numCols': 'matrix' = (%d) vs 'row' = (%d)", this.numCols, row.numCols));
        }
    }

    public Matrix setRowToThis(Matrix row, int rowIndex) throws MatrixIllegalArgumentException {
        this.validateSetRow(row);
        int index = this.getIndex(rowIndex, 0);
        System.arraycopy(row.array, 0, this.array, index, this.numCols);
        return this;
    }

    public Matrix setRow(Matrix row, int rowIndex) throws MatrixIllegalArgumentException {
        Matrix result = this.copy();
        return result.setRowToThis(row, rowIndex);
    }

    private void validateSetCol(Matrix col) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(col);
        if (col.numCols != 1) {
            throw new MatrixIllegalArgumentException(String.format(
                    "'numCols' = (%d) has to be 1 for 'col'", col.numCols));
        }
        if (this.numRows != col.numRows) {
            throw new MatrixIllegalArgumentException(String.format(
                    "Dimension mismatch for 'numRows': 'matrix' = (%d) vs 'col' = (%d)", this.numRows, col.numRows));
        }
    }

    public Matrix setColToThis(Matrix col, int colIndex) throws MatrixIllegalArgumentException {
        this.validateSetCol(col);
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            this.setToThis(rowIndex, colIndex, col.get(rowIndex, 0));
        }
        return this;
    }

    public Matrix setCol(Matrix col, int colIndex) throws MatrixIllegalArgumentException {
        Matrix result = this.copy();
        return result.setColToThis(col, colIndex);
    }

    public Matrix copy() {
        return create(this.array, this.numRows, this.numCols);
    }

    private Matrix addToThis(double value) {
        for (int index = 0; index < this.length; index++) {
            this.array[index] += value;
        }
        return this;
    }

    public Matrix add(double value) {
        Matrix result = this.copy();
        return result.addToThis(value);
    }

    private static void validateMatricesDimensionAdd(Matrix result, Matrix... matrices)
            throws MatrixIllegalArgumentException {

        Matrix.validateMatricesNonNull(result);
        int numRows = result.numRows;
        int numCols = result.numCols;

        for (Matrix matrix : matrices) {
            Matrix.validateMatricesNonNull(matrix);
            if (numRows != matrix.numRows || numCols != matrix.numCols) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for adding matrices");
            }
        }

    }

    private Matrix addToThis(Matrix... matrices) throws MatrixIllegalArgumentException {

        Matrix.validateMatricesDimensionAdd(this, matrices);

        for (int index = 0; index < this.length; index++) {
            double addValue = 0;
            for (Matrix matrix : matrices) {
                addValue += matrix.array[index];
            }
            this.array[index] += addValue;
        }

        return this;
    }

    public Matrix add(Matrix... matrices) throws MatrixIllegalArgumentException {
        Matrix first = this.copy();
        return first.addToThis(matrices);
    }

    private static void validateMatricesNonEmpty(Matrix... matrices) throws MatrixIllegalArgumentException {
        if (matrices.length < 1) {
            throw new MatrixIllegalArgumentException("Need at least one matrix");
        }
    }

    public static Matrix sum(Matrix... matrices) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonEmpty(matrices);
        Matrix.validateMatricesNonNull(matrices[0]);
        Matrix result = Matrix.ofZeros(matrices[0]);
        return result.add(matrices);
    }

    private Matrix multiplyToThis(double value) {
        for (int index = 0; index < this.length; index++) {
            this.array[index] *= value;
        }
        return this;
    }

    public Matrix multiply(double value) {
        Matrix result = this.copy();
        return result.multiplyToThis(value);
    }

    private static void validateDimensionsProd(Matrix... matrices) throws MatrixIllegalArgumentException {

        Matrix.validateMatricesNonEmpty(matrices);
        Matrix.validateMatricesNonNull(matrices);

        int rightNumRows = matrices[matrices.length - 1].numRows;
        for (int matrixIndex = matrices.length - 2; matrixIndex >= 0; matrixIndex--) {
            int leftNumCols = matrices[matrixIndex].numCols;
            if (leftNumCols != rightNumRows) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for taking product of matrices");
            }
            rightNumRows = matrices[matrixIndex].numRows;
        }

    }

    public static Matrix prod(Matrix... matrices) throws MatrixIllegalArgumentException {

        Matrix.validateDimensionsProd(matrices);
        Matrix result = matrices[matrices.length - 1];

        for (int matrixIndex = matrices.length - 2; matrixIndex >= 0; matrixIndex--) {

            Matrix left = matrices[matrixIndex];
            Matrix right = result;
            result = Matrix.create(left.numRows, right.numCols);

            for (int rowIndex = 0; rowIndex < result.numRows; rowIndex++) {
                for (int colIndex = 0; colIndex < result.numCols; colIndex++) {
                    double value = 0;
                    for (int index = 0; index < left.numCols; index++) {
                        value += left.get(rowIndex, index) * right.get(index, colIndex);
                    }
                    result.setToThis(rowIndex, colIndex, value);
                }
            }
        }

        return result;
    }

    public Matrix multiply(Matrix matrix) throws MatrixIllegalArgumentException {
        return Matrix.prod(this, matrix);
    }

    public Matrix multiplyLeft(Matrix matrix) throws MatrixIllegalArgumentException {
        return Matrix.prod(matrix, this);
    }

    private static int validateHorizontalConcatenate(Matrix... matrices) throws MatrixIllegalArgumentException {

        Matrix.validateMatricesNonEmpty(matrices);
        Matrix.validateMatricesNonNull(matrices[0]);
        int numRows = matrices[0].numRows;
        int numCols = 0;

        for (Matrix matrix : matrices) {
            Matrix.validateMatricesNonNull(matrix);
            if (numRows != matrix.numRows) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for 'numRows'");
            }
            numCols += matrix.numCols;
        }

        return numCols;
    }

    public static Matrix horizontalConcatenate(Matrix... matrices) throws MatrixIllegalArgumentException {
        int numCols = Matrix.validateHorizontalConcatenate(matrices);
        int numRows = matrices[0].numRows;

        Matrix result = Matrix.create(numRows, numCols);

        for (int rowIndex = 0; rowIndex < result.numRows; rowIndex++) {
            int runningNumCols = 0;
            for (Matrix matrix: matrices) {
                System.arraycopy(matrix.array, matrix.getIndex(rowIndex, 0),
                        result.array, result.getIndex(rowIndex, runningNumCols), matrix.numCols);
                runningNumCols += matrix.numCols;
            }
        }

        return result;

    }

    private static int validateVerticalConcatenate(Matrix... matrices) throws MatrixIllegalArgumentException {

        Matrix.validateMatricesNonEmpty(matrices);
        Matrix.validateMatricesNonNull(matrices[0]);
        int numRows = 0;
        int numCols = matrices[0].numCols;

        for (Matrix matrix : matrices) {
            Matrix.validateMatricesNonNull(matrix);
            if (numCols != matrix.numCols) {
                throw new MatrixIllegalArgumentException("Dimension mismatch for 'numCols'");
            }
            numRows += matrix.numRows;
        }

        return numRows;
    }

    public static Matrix verticalConcatenate(Matrix... matrices) throws MatrixIllegalArgumentException {

        int numRows = Matrix.validateVerticalConcatenate(matrices);
        int numCols = matrices[0].numCols;

        Matrix result = Matrix.create(numRows, numCols);

        int runningLength = 0;
        for (Matrix matrix: matrices) {
            System.arraycopy(matrix.array, 0, result.array, runningLength, matrix.length);
            runningLength += matrix.length;
        }

        return result;

    }

    public Matrix transpose() {

        Matrix result = Matrix.create(this.numCols, this.numRows);
        for (int rowIndex = 0; rowIndex < result.numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < result.numCols; colIndex++) {
                result.setToThis(rowIndex, colIndex, this.get(colIndex, rowIndex));
            }
        }

        return result;
    }

    public Matrix reshape(int numRows, int numCols) {
        return Matrix.create(this.array, numRows, numCols);
    }

    public double sum() {
        double value = 0;
        for (int index = 0; index < this.length; index++) {
            value += this.array[index];
        }
        return value;
    }

    private Matrix sumOverRows() {
        Matrix result = Matrix.create(1, this.numCols);
        for (int colIndex = 0; colIndex < this.numCols; colIndex++) {
            result.array[colIndex] = 0;
            for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
                result.array[colIndex] += this.get(rowIndex, colIndex);
            }
        }
        return result;
    }

    private Matrix sumOverCols() {
        Matrix result = Matrix.create(this.numRows, 1);
        for (int rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            result.array[rowIndex] = 0;
            for (int colIndex = 0; colIndex < this.numCols; colIndex++) {
                result.array[rowIndex] += this.get(rowIndex, colIndex);
            }
        }
        return result;
    }

    public Matrix sum(int axis) throws MatrixIllegalArgumentException {

        if (axis != 0 && axis != 1) {
            throw new MatrixIllegalArgumentException(String.format("'axis' (%d) has to be 0 or 1", axis));
        }

        Matrix result;
        if (axis == 0) {
            result = this.sumOverRows();
        } else {
            result = this.sumOverCols();
        }
        return result;
    }

    public boolean isSquare() {
        return this.numRows == this.numCols;
    }

    private static void validateSquare(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
        if (!matrix.isSquare()) {
            throw new MatrixIllegalArgumentException("Matrix is not square");
        }
    }

    public boolean isSingleton() {
        return this.numRows == 1 && this.numCols == 1;
    }

    private static void validateSingleton(Matrix matrix) throws MatrixIllegalArgumentException {
        Matrix.validateMatricesNonNull(matrix);
        if (!matrix.isSingleton()) {
            throw new MatrixIllegalArgumentException("Matrix is not a singleton");
        }
    }

    public double toDouble() {
        Matrix.validateSingleton(this);
        return this.array[0];
    }

    public Matrix[] decomposeQRGramSchmidt() {
        Matrix.validateSquare(this);

        // Initialise Q and R matrices
        Matrix Q = Matrix.create(this);
        Matrix R = Matrix.ofZeros(this);

        // Loop to calculate each column of Q and corresponding values for R
        for (int colIndex = 0; colIndex < Q.numCols; colIndex++) {

            // Get column of this matrix and initialise the column for Q as the same column
            Matrix thisColTranspose = this.getCol(colIndex).transpose();
            Matrix QCol = this.getCol(colIndex);

            // Loop through all previous columns in Q and remove the projection of this column
            for (int prevColIndex = colIndex - 1; prevColIndex >= 0; prevColIndex--) {
                Matrix prevCol = Q.getCol(prevColIndex);
                double thisColDotQCol = thisColTranspose.multiply(prevCol).toDouble();
                R.setToThis(prevColIndex, colIndex, thisColDotQCol);
                QCol.addToThis(prevCol.multiplyToThis(-thisColDotQCol));
            }

            // Normalise the column for Q
            double QColNormInv = 1 / Math.sqrt(QCol.transpose().multiply(QCol).toDouble());
            QCol.multiplyToThis(QColNormInv);

            Q.setColToThis(QCol, colIndex);
            R.setToThis(colIndex, colIndex, thisColTranspose.multiply(QCol).toDouble());

        }

        return new Matrix[] { Q, R };
    }

    public double maxDifference(Matrix other) {
        double max_value = Double.MIN_VALUE;
        for (int index = 0; index < this.length; index++) {
            max_value = Math.max(max_value, Math.abs(this.array[index] - other.array[index]));
        }
        return max_value;
    }

    public boolean equalsWithinTolerance(Matrix other, double tolerance) {
        Matrix.validateMatricesNonNull(other);
        if (this.numRows != other.numRows || this.numCols != other.numCols) {
            return false;
        } else {
            double maxDifference = this.maxDifference(other);
            return maxDifference < tolerance;
        }
    }

    public boolean equalsWithinTolerance(Matrix other) {
        return this.equalsWithinTolerance(other, Matrix.MAX_TOLERANCE);
    }

    public static boolean equalsWithinTolerance(Matrix matrix, Matrix other, double tolerance) {
        Matrix.validateMatricesNonNull(matrix);
        return matrix.equalsWithinTolerance(other, tolerance);
    }

    public static boolean equalsWithinTolerance(Matrix matrix, Matrix other) {
        Matrix.validateMatricesNonNull(matrix);
        return matrix.equalsWithinTolerance(other);
    }

}