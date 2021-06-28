package agent.smith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.Stream;

public class MatrixTest {

    // -----------------------------------------------------------------------------------------------------------------
    // of (double)
    @SuppressWarnings("unused")
    static Stream<Arguments> ofExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(0, -1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(0, 1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(0, 1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("ofExceptionArguments")
    public void testOfException(double value, int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.of(value, numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofArguments = Stream.of(
            // singleton
            Arguments.of(0, 1, 1, new double[]{0}, 1, 1)
            , Arguments.of(1, 1, 1, new double[]{1}, 1, 1)
            // square
            , Arguments.of(0, 2, 2, new double[]{0, 0, 0, 0}, 2, 2)
            // rectangle
            , Arguments.of(0, 2, 3, new double[]{0, 0, 0, 0, 0, 0}, 2, 3)
            , Arguments.of(0, 3, 2, new double[]{0, 0, 0, 0, 0, 0}, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("ofArguments")
    public void testOf(double value, int numRows, int numCols, double[] expectedArray, int expectedNumRows,
                       int expectedNumCols) {
        Matrix matrix = Matrix.of(value, numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // create (fromArray)
    @SuppressWarnings("unused")
    static Stream<Arguments> createFromArrayExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(new double[]{0}, 0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(new double[]{0}, -1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(new double[]{0}, 1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(new double[]{0}, 1, -1, "'numCols' (-1) has to be a positive integer")
            // array == null
            , Arguments.of(null, 1, 1, "'array' cannot be null")
            // numRows * numCols > array.length
            , Arguments.of(new double[]{1, 2}, 2, 2,
                    "Length of 'array' (2) does not match 'numRows' * 'numCols' (4)")
            // numRows * numCols > array.length
            , Arguments.of(new double[]{1, 2}, 1, 1,
                    "Length of 'array' (2) does not match 'numRows' * 'numCols' (1)")
    );

    @ParameterizedTest
    @VariableSource("createFromArrayExceptionArguments")
    public void testCreateFromArrayException(double[] array, int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.create(array, numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // create (Double.NaN)
    @SuppressWarnings("unused")
    static Stream<Arguments> createNaNExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("createNaNExceptionArguments")
    public void testCreateNaNException(int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.create(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> createNaNArguments = Stream.of(
            // singleton
            Arguments.of(1, 1, new double[]{Double.NaN}, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN}, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                    2, 3)
            , Arguments.of(3, 2, new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
                    3, 2)
    );

    @ParameterizedTest
    @VariableSource("createNaNArguments")
    public void testCreateNaN(int numRows, int numCols, double[] expectedArray, int expectedNumRows,
                              int expectedNumCols) {
        Matrix matrix = Matrix.create(numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // create (Double.NaN from matrix)
    @SuppressWarnings("unused")
    static Stream<Arguments> createNaNFromMatrixExceptionArguments = Stream.of(
            // matrix == null
            Arguments.of(null, "Input matrix cannot be null")
    );
    @ParameterizedTest
    @VariableSource("createNaNFromMatrixExceptionArguments")
    public void testCreateNaNFromMatrixException(Matrix matrix, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.create(matrix));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> createNaNFromMatrixArguments = Stream.of(
            // singleton
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), new double[]{Double.NaN}, 1, 1)
            // square
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN}, 2, 2)
            // rectangle
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN}, 2, 3)
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN}, 3, 2)
    );
    @ParameterizedTest
    @VariableSource("createNaNFromMatrixArguments")
    public void testCreateNaNFromMatrix(Matrix matrix, double[] expectedArray, int expectedNumRows,
                                        int expectedNumCols) {
        Matrix actual = Matrix.create(matrix);
        assertArrayEquals(expectedArray, actual.getArray());
        assertEquals(expectedNumRows, actual.getNumRows());
        assertEquals(expectedNumCols, actual.getNumCols());
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ofZeros
    @SuppressWarnings("unused")
    static Stream<Arguments> ofZerosRectangleExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("ofZerosRectangleExceptionArguments")
    public void testOfZerosRectangleException(int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.ofZeros(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofZerosRectangleArguments = Stream.of(
            // singleton
            Arguments.of(1, 1, new double[]{0}, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{0, 0, 0, 0}, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{0, 0, 0, 0, 0, 0}, 2, 3)
            , Arguments.of(3, 2, new double[]{0, 0, 0, 0, 0, 0}, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("ofZerosRectangleArguments")
    public void testOfZerosRectangle(int numRows, int numCols, double[] expectedArray, int expectedNumRows,
                                     int expectedNumCols) {
        Matrix matrix = Matrix.ofZeros(numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofZerosSquareExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, "'numRows' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("ofZerosSquareExceptionArguments")
    public void testOfZerosSquareException(int numRowsAndCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.ofZeros(numRowsAndCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofZerosSquareArguments = Stream.of(
            // singleton
            Arguments.of(1, new double[]{0}, 1, 1)
            // non singleton
            , Arguments.of(2, new double[]{0, 0, 0, 0}, 2, 2)
            , Arguments.of(3, new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, 3, 3)
    );

    @ParameterizedTest
    @VariableSource("ofZerosSquareArguments")
    public void testOfZerosSquare(int numRowsAndCols, double[] expectedArray, int expectedNumRows,
                                  int expectedNumCols) {
        Matrix matrix = Matrix.ofZeros(numRowsAndCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // ofOnes
    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesRectangleExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(1, -1, "'numCols' (-1) has to be a positive integer")
    );
    @ParameterizedTest
    @VariableSource("ofOnesRectangleExceptionArguments")
    public void testOfOnesRectangleException(int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.ofOnes(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesRectangleArguments = Stream.of(
            // singleton
            Arguments.of(1, 1, new double[]{1}, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{1, 1, 1, 1}, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{1, 1, 1, 1, 1, 1}, 2, 3)
            , Arguments.of(3, 2, new double[]{1, 1, 1, 1, 1, 1}, 3, 2)
    );
    @ParameterizedTest
    @VariableSource("ofOnesRectangleArguments")
    public void testOfOnesRectangle(int numRows, int numCols, double[] expectedArray, int expectedNumRows,
                                    int expectedNumCols) {
        Matrix matrix = Matrix.ofOnes(numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesSquareExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, "'numRows' (-1) has to be a positive integer")
    );
    @ParameterizedTest
    @VariableSource("ofOnesSquareExceptionArguments")
    public void testOfOnesSquareException(int numRowsAndCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.ofOnes(numRowsAndCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesSquareArguments = Stream.of(
            // singleton
            Arguments.of(1, new double[]{1}, 1, 1)
            // non singleton
            , Arguments.of(2, new double[]{1, 1, 1, 1}, 2, 2)
            , Arguments.of(3, new double[]{1, 1, 1, 1, 1, 1, 1, 1, 1}, 3, 3)
    );
    @ParameterizedTest
    @VariableSource("ofOnesSquareArguments")
    public void testOfOnesSquare(int numRowsAndCols, double[] expectedArray, int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.ofOnes(numRowsAndCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // ofOnes
    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesMatrixExceptionArguments = Stream.of(
            // matrix == null
            Arguments.of(null, "Input matrix cannot be null")
    );
    @ParameterizedTest
    @VariableSource("ofOnesMatrixExceptionArguments")
    public void testOfOnesMatrixException(Matrix matrix, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.ofOnes(matrix));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> ofOnesMatrixArguments = Stream.of(
            // singleton
            Arguments.of(Matrix.create(new double[] {0}, 1, 1), new double[]{1}, 1, 1)
            // non singleton
            , Arguments.of(Matrix.create(new double[] {0, 0, 0, 0}, 2, 2), new double[]{1, 1, 1, 1}, 2, 2)
    );
    @ParameterizedTest
    @VariableSource("ofOnesMatrixArguments")
    public void testOfOnesMatrix(Matrix matrix, double[] expectedArray, int expectedNumRows, int expectedNumCols) {
        Matrix actual = Matrix.ofOnes(matrix);
        assertArrayEquals(expectedArray, actual.getArray());
        assertEquals(expectedNumRows, actual.getNumRows());
        assertEquals(expectedNumCols, actual.getNumCols());
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // from (nestedArray)
    @SuppressWarnings("unused")
    static Stream<Arguments> fromNestedArrayExceptionArguments = Stream.of(
            // nestedArray == null
            Arguments.of(null, "'nestedArray' cannot be null")
            // nestedArray == {  }
            , Arguments.of(new double[][]{}, "'nestedArray' cannot be empty")
            // nestedArray[0] == null
            , Arguments.of(new double[][]{null}, "'nestedArray[0]' cannot be null")
            // nestedArray[0] == {  }
            , Arguments.of(new double[][]{{}}, "'nestedArray' cannot be empty")
            // nestedArray[1] == null
            , Arguments.of(new double[][]{{0}, null}, "'nestedArray[1]' cannot be null")
            // nestedArray[0].length != nestedArray[1].length
            , Arguments.of(new double[][]{{0}, {}}, "Inconsistent number of rows for 'nestedArray'")
            , Arguments.of(new double[][]{{1}, {2, 3}}, "Inconsistent number of rows for 'nestedArray'")
            , Arguments.of(new double[][]{{1, 2}, {3}}, "Inconsistent number of rows for 'nestedArray'")
    );

    @ParameterizedTest
    @VariableSource("fromNestedArrayExceptionArguments")
    public void testFromNestedArrayException(double[][] nestedArray, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> fromNestedArrayArguments = Stream.of(
            // square
            Arguments.of(new double[][]{{1, 2}, {3, 4}}, new double[]{1, 2, 3, 4}, 2, 2)
            // rectangle
            , Arguments.of(new double[][]{{1, 2, 3}, {4, 5, 6}}, new double[]{1, 2, 3, 4, 5, 6}, 2, 3)
            , Arguments.of(new double[][]{{1, 2}, {3, 4}, {5, 6}}, new double[]{1, 2, 3, 4, 5, 6}, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("fromNestedArrayArguments")
    public void testFromNestedArray(double[][] nestedArray, double[] expectedArray, int expectedNumRows,
                                    int expectedNumCols) {
        Matrix matrix = Matrix.from(nestedArray);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // equals
    @SuppressWarnings("unused")
    static Stream<Arguments> equalsArguments = Stream.of(
            // matrix != null
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), null, false)
            // matrix != double[]
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), new double[]{0}, false)
            // matrix == matrix
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1), true)
            , Arguments.of(Matrix.create(new double[]{1, 2}, 1, 2),
                    Matrix.create(new double[]{1, 2}, 1, 2), true)
            // matrix != matrix
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1}, 1, 1), false)
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1, 2}, 1, 2), false)
            , Arguments.of(Matrix.create(new double[]{1, 2}, 2, 1),
                    Matrix.create(new double[]{1, 2}, 1, 2), false)
    );

    @ParameterizedTest
    @VariableSource("equalsArguments")
    public void testEquals(Matrix matrix, Object other, boolean expected) {
        boolean actual = matrix.equals(other);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // hashCode
    @SuppressWarnings("unused")
    static Stream<Arguments> hashCodeArguments = Stream.of(
            // simple matrix
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), 30814)
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 30814)
            , Arguments.of(Matrix.create(new double[]{1}, 1, 1), 1072724062)
            , Arguments.of(Matrix.create(new double[]{0, 1}, 1, 2), 1072725023)
            , Arguments.of(Matrix.create(new double[]{0, 1}, 2, 1), 1072725953)
    );

    @ParameterizedTest
    @VariableSource("hashCodeArguments")
    public void testHashCode(Matrix matrix, int expected) {
        int actual = matrix.hashCode();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // toString
    @SuppressWarnings("unused")
    static Stream<Arguments> toStringWithFormatAndDelimitersArguments = Stream.of(
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), "%.4e", "\n       ", " ",
                    "Matrix{0.0000e+00}")
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), "%.6e", "; ", ", ",
                    "Matrix{0.000000e+00}")
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4}, 2, 2), "%.4e", "\n       ", " ",
                    "Matrix{1.0000e+00 2.0000e+00\n       3.0000e+00 4.0000e+00}")
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4}, 2, 2), "%.6e", "; ", ", ",
                    "Matrix{1.000000e+00, 2.000000e+00; 3.000000e+00, 4.000000e+00}")
    );

    @ParameterizedTest
    @VariableSource("toStringWithFormatAndDelimitersArguments")
    public void testToStringWithFormatAndDelimiters(Matrix matrix, String format, String rowDelimiter,
                                                    String colDelimiter, String expected) {
        String actual = matrix.toString(format, rowDelimiter, colDelimiter);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> toStringWithFormatArguments = Stream.of(
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), "%.4e", "Matrix{0.0000e+00}")
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), "%.6e", "Matrix{0.000000e+00}")
    );

    @ParameterizedTest
    @VariableSource("toStringWithFormatArguments")
    public void testToStringWithFormat(Matrix matrix, String format, String expected) {
        String actual = matrix.toString(format);
        assertEquals(expected, actual);
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> toStringArguments = Stream.of(
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), "Matrix{0.0000e+00}")
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    "Matrix{1.0000e+00 2.0000e+00\n       3.0000e+00 4.0000e+00}")
    );

    @ParameterizedTest
    @VariableSource("toStringArguments")
    public void testToString(Matrix matrix, String expected) {
        String actual = matrix.toString();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getIndex
    @SuppressWarnings("unused")
    static Stream<Arguments> getIndexExceptionArguments = Stream.of(
            // rowIndex < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1, 0,
                    "'rowIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1, 0,
                    "'rowIndex' = (1) has to be between 0 and 0")
            // colIndex < 0
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, -1,
                    "'colIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, 1,
                    "'colIndex' = (1) has to be between 0 and 0")
    );

    @ParameterizedTest
    @VariableSource("getIndexExceptionArguments")
    public void testGetIndexException(Matrix matrix, int rowIndex, int colIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.getIndex(rowIndex, colIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getIndexArguments = Stream.of(
            // 2x3 matrix
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 1, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 2, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 0, 3)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 1, 4)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 2, 5)
            // 3x2 matrix
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 1, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 0, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 1, 3)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 0, 4)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 1, 5)
    );

    @ParameterizedTest
    @VariableSource("getIndexArguments")
    public void testGetIndex(Matrix matrix, int rowIndex, int colIndex, int expected) {
        int actual = matrix.getIndex(rowIndex, colIndex);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getRowIndex
    @SuppressWarnings("unused")
    static Stream<Arguments> getRowIndexExceptionArguments = Stream.of(
            // index < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1,
                    "'index' = (-1) has to be between 0 and 0")
            // index >= matrix.length
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1,
                    "'index' = (1) has to be between 0 and 0")
    );

    @ParameterizedTest
    @VariableSource("getRowIndexExceptionArguments")
    public void testGetRowIndexException(Matrix matrix, int index, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.getRowIndex(index));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getRowIndexArguments = Stream.of(
            // 2x3 matrix
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 2, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 3, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 4, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 5, 1)
            // 3x2 matrix
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 3, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 4, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 5, 2)
    );

    @ParameterizedTest
    @VariableSource("getRowIndexArguments")
    public void testGetRowIndex(Matrix matrix, int index, int expected) {
        int actual = matrix.getRowIndex(index);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getColIndex
    @SuppressWarnings("unused")
    static Stream<Arguments> getColIndexExceptionArguments = Stream.of(
            // index < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1,
                    "'index' = (-1) has to be between 0 and 0")
            // index >= matrix.length
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1,
                    "'index' = (1) has to be between 0 and 0")
    );

    @ParameterizedTest
    @VariableSource("getColIndexExceptionArguments")
    public void testGetColIndexException(Matrix matrix, int index, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.getColIndex(index));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getColIndexArguments = Stream.of(
            // 2x3 matrix
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 2, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 3, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 4, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 5, 2)
            // 3x2 matrix
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 3, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 4, 0)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 5, 1)
    );

    @ParameterizedTest
    @VariableSource("getColIndexArguments")
    public void testGetColIndex(Matrix matrix, int index, int expected) {
        int actual = matrix.getColIndex(index);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // get (element)
    @SuppressWarnings("unused")
    static Stream<Arguments> getExceptionArguments = Stream.of(
            // rowIndex < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1, 0,
                    "'rowIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1, 0,
                    "'rowIndex' = (1) has to be between 0 and 0")
            // colIndex < 0
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, -1,
                    "'colIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, 1,
                    "'colIndex' = (1) has to be between 0 and 0")
    );

    @ParameterizedTest
    @VariableSource("getExceptionArguments")
    public void testGetException(Matrix matrix, int rowIndex, int colIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.get(rowIndex, colIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getArguments = Stream.of(
            // 2x3 matrix
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 0, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 1, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 2, 3)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 0, 4)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 1, 5)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 2, 6)
            // 3x2 matrix
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 0, 1)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 1, 2)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 0, 3)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 1, 4)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 0, 5)
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 1, 6)
    );

    @ParameterizedTest
    @VariableSource("getArguments")
    public void testGet(Matrix matrix, int rowIndex, int colIndex, double expected) {
        double actual = matrix.get(rowIndex, colIndex);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // set (element)
    @SuppressWarnings("unused")
    static Stream<Arguments> setExceptionArguments = Stream.of(
            // rowIndex < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1, 0, 0,
                    "'rowIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1, 0, 0,
                    "'rowIndex' = (1) has to be between 0 and 0")
            // colIndex < 0
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, -1, 0,
                    "'colIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0, 1, 0,
                    "'colIndex' = (1) has to be between 0 and 0")
    );

    @ParameterizedTest
    @VariableSource("setExceptionArguments")
    public void testSetException(Matrix matrix, int rowIndex, int colIndex, double value, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.set(rowIndex, colIndex, value));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> setArguments = Stream.of(
            // 2x3 matrix
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 0, 0,
                    Matrix.create(new double[]{0, 2, 3, 4, 5, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 1, 0,
                    Matrix.create(new double[]{1, 0, 3, 4, 5, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, 2, 0,
                    Matrix.create(new double[]{1, 2, 0, 4, 5, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 0, 0,
                    Matrix.create(new double[]{1, 2, 3, 0, 5, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 1, 0,
                    Matrix.create(new double[]{1, 2, 3, 4, 0, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, 2, 0,
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 0}, 2, 3))
            // 3x2 matrix
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 0, 0,
                    Matrix.create(new double[]{0, 2, 3, 4, 5, 6}, 3, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, 1, 0,
                    Matrix.create(new double[]{1, 0, 3, 4, 5, 6}, 3, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 0, 0,
                    Matrix.create(new double[]{1, 2, 0, 4, 5, 6}, 3, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, 1, 0,
                    Matrix.create(new double[]{1, 2, 3, 0, 5, 6}, 3, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 0, 0,
                    Matrix.create(new double[]{1, 2, 3, 4, 0, 6}, 3, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, 1, 0,
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 0}, 3, 2))
    );
    @ParameterizedTest
    @VariableSource("setArguments")
    public void testSet(Matrix matrix, int rowIndex, int colIndex, double value, Matrix expected) {
        Matrix actual = matrix.set(rowIndex, colIndex, value);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getDiagonal
    @SuppressWarnings("unused")
    static Stream<Arguments> getDiagonalArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    Matrix.create(new double[]{1}, 1, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{0, 1}, 1, 2),
                    Matrix.create(new double[]{0}, 1, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{1, 0}, 1, 2),
                    Matrix.create(new double[]{1}, 1, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    Matrix.create(new double[]{1, 4}, 2, 1)
            )
    );
    @ParameterizedTest
    @VariableSource("getDiagonalArguments")
    public void testGetDiagonal(Matrix matrix, Matrix expected) {
        Matrix actual = matrix.getDiagonal();
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // setDiagonal
    @SuppressWarnings("unused")
    static Stream<Arguments> setDiagonalArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    1,
                    Matrix.create(new double[]{1}, 1, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{0, 0}, 1, 2),
                    1,
                    Matrix.create(new double[]{1, 0}, 1, 2)
            )
            , Arguments.of(
                    Matrix.create(new double[]{0, 0}, 2, 1),
                    1,
                    Matrix.create(new double[]{1, 0}, 2, 1)
            )
            , Arguments.of(
                    Matrix.create(new double[]{0, 0, 0, 0}, 2, 2),
                    1,
                    Matrix.create(new double[]{1, 0, 0, 1}, 2, 2)
            )
    );
    @ParameterizedTest
    @VariableSource("setDiagonalArguments")
    public void testSetDiagonal(Matrix matrix, double value, Matrix expected) {
        Matrix actual = matrix.setDiagonal(value);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // instanceOfEye
    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeRectangleExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("instanceOfEyeRectangleExceptionArguments")
    public void testInstanceOfEyeRectangleException(int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfEye(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeRectangleArguments = Stream.of(
            // singleton
            Arguments.of(1, 1, new double[]{1}, 1, 1)
            // non singleton
            , Arguments.of(1, 2, new double[]{1, 0}, 1, 2)
            , Arguments.of(2, 1, new double[]{1, 0}, 2, 1)
            , Arguments.of(2, 2, new double[]{1, 0, 0, 1}, 2, 2)
            , Arguments.of(2, 3, new double[]{1, 0, 0, 0, 1, 0}, 2, 3)
            , Arguments.of(3, 2, new double[]{1, 0, 0, 1, 0, 0}, 3, 2)
            , Arguments.of(3, 3, new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, 3, 3)
    );

    @ParameterizedTest
    @VariableSource("instanceOfEyeRectangleArguments")
    public void testInstanceOfEyeRectangle(int numRows, int numCols, double[] expectedArray, int expectedNumRows,
                                           int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfEye(numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeSquareExceptionArguments = Stream.of(
            // numRowsAndCols <= 0
            Arguments.of(0, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, "'numRows' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("instanceOfEyeSquareExceptionArguments")
    public void testInstanceOfEyeSquareException(int numRowsAndCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfEye(numRowsAndCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeSquareArguments = Stream.of(
            // singleton
            Arguments.of(1, new double[]{1}, 1, 1)
            // non singleton
            , Arguments.of(2, new double[]{1, 0, 0, 1}, 2, 2)
            , Arguments.of(3, new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, 3, 3)
    );
    @ParameterizedTest
    @VariableSource("instanceOfEyeSquareArguments")
    public void testInstanceOfEyeSquare(int numRowsAndCols, double[] expectedArray, int expectedNumRows,
                                        int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfEye(numRowsAndCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }


    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeMatrixExceptionArguments = Stream.of(
            // matrix == null
            Arguments.of(null, "Input matrix cannot be null")
    );
    @ParameterizedTest
    @VariableSource("instanceOfEyeMatrixExceptionArguments")
    public void testInstanceOfEyeMatrixException(Matrix matrix, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfEye(matrix));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfEyeMatrixArguments = Stream.of(
            // singleton
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), new double[]{1}, 1, 1)
            // non singleton
            , Arguments.of(Matrix.create(new double[]{0, 0}, 1, 2), new double[]{1, 0}, 1, 2)
            , Arguments.of(Matrix.create(new double[]{0, 0}, 2, 1), new double[]{1, 0}, 2, 1)
            , Arguments.of(
                    Matrix.create(new double[]{0, 0, 0, 0}, 2, 2),
                    new double[]{1, 0, 0, 1}, 2, 2)
    );
    @ParameterizedTest
    @VariableSource("instanceOfEyeMatrixArguments")
    public void testInstanceOfEyeMatrix(Matrix matrix, double[] expectedArray, int expectedNumRows,
                                        int expectedNumCols) {
        Matrix actual = Matrix.instanceOfEye(matrix);
        assertArrayEquals(expectedArray, actual.getArray());
        assertEquals(expectedNumRows, actual.getNumRows());
        assertEquals(expectedNumCols, actual.getNumCols());
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // instanceOfRandom
    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithRandomExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(new Random(0), 0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(new Random(0), -1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(new Random(0), 1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(new Random(0), 1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithRandomExceptionArguments")
    public void testInstanceOfRandomWithRandomException(Random r, int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfRandom(r, numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithRandomArguments = Stream.of(
            Arguments.of(new Random(0), 2, 3, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163,
                    0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 2, 3)
            , Arguments.of(new Random(0), 3, 2, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163,
                    0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithRandomArguments")
    public void testInstanceOfRandomWithRandom(Random r, int numRows, int numCols, double[] expectedArray,
                                               int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(r, numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithRandomNumRowsAndColsArguments = Stream.of(
            Arguments.of(new Random(0), 1, new double[]{0.8025330637390305}, 1, 1)
            , Arguments.of(new Random(0), 2, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163, 0.7637707684364894}, 2, 2)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithRandomNumRowsAndColsArguments")
    public void testInstanceOfRandomWithRandomNumRowsAndCols(Random r, int numRowsAndCols, double[] expectedArray,
                                                             int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(r, numRowsAndCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithSeedExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(0, -1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(0, 1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(0, 1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithSeedExceptionArguments")
    public void testInstanceOfRandomWithSeedException(long seed, int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfRandom(seed, numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithSeedArguments = Stream.of(
            Arguments.of(0, 2, 3, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163,
                    0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 2, 3)
            , Arguments.of(0, 3, 2, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163,
                    0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithSeedArguments")
    public void testInstanceOfRandomWithSeed(long seed, int numRows, int numCols, double[] expectedArray,
                                             int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(seed, numRows, numCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomWithSeedNumRowsAndColsArguments = Stream.of(
            Arguments.of(0, 1, new double[]{0.8025330637390305}, 1, 1)
            , Arguments.of(0, 2, new double[]{
                    0.8025330637390305, -0.9015460884175122, 2.080920790428163, 0.7637707684364894}, 2, 2)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomWithSeedNumRowsAndColsArguments")
    public void testInstanceOfRandomWithSeedNumRowsAndCols(long seed, int numRowsAndCols, double[] expectedArray,
                                                           int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(seed, numRowsAndCols);
        assertArrayEquals(expectedArray, matrix.getArray());
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomExceptionArguments = Stream.of(
            // numRows <= 0
            Arguments.of(0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(-1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(1, -1, "'numCols' (-1) has to be a positive integer")
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomExceptionArguments")
    public void testInstanceOfRandomException(int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.instanceOfRandom(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomArguments = Stream.of(
            Arguments.of(2, 3, 2, 3)
            , Arguments.of(3, 2, 3, 2)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomArguments")
    public void testInstanceOfRandomArguments(int numRows, int numCols, int expectedNumRows, int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(numRows, numCols);
        assertEquals(matrix.getClass(), Matrix.class);
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> instanceOfRandomNumRowsAndColsArguments = Stream.of(
            Arguments.of(1, 1, 1)
            , Arguments.of(2, 2, 2)
            , Arguments.of(3, 3, 3)
    );

    @ParameterizedTest
    @VariableSource("instanceOfRandomNumRowsAndColsArguments")
    public void testInstanceOfRandomNumRowsAndColsArguments(int numRowsAndCols, int expectedNumRows,
                                                            int expectedNumCols) {
        Matrix matrix = Matrix.instanceOfRandom(numRowsAndCols);
        assertEquals(matrix.getClass(), Matrix.class);
        assertEquals(expectedNumRows, matrix.getNumRows());
        assertEquals(expectedNumCols, matrix.getNumCols());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // copy
    @SuppressWarnings("unused")
    static Stream<Arguments> copyArguments = Stream.of(
            Arguments.of(Matrix.create(new double[]{0}, 1, 1))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2))
    );

    @ParameterizedTest
    @VariableSource("copyArguments")
    public void testCopy(Matrix matrix) {
        Matrix copiedMatrix = matrix.copy();
        assertEquals(matrix, copiedMatrix);
        assertNotSame(matrix, copiedMatrix);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getRow
    @SuppressWarnings("unused")
    static Stream<Arguments> getRowExceptionArguments = Stream.of(
            // rowIndex < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1,
                    "'rowIndex' = (-1) has to be between 0 and 0")
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1,
                    "'rowIndex' = (1) has to be between 0 and 0")
    );
    @ParameterizedTest
    @VariableSource("getRowExceptionArguments")
    public void testGetRowException(Matrix matrix, int rowIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.getRow(rowIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getRowArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    0,
                    Matrix.create(new double[]{1, 2, 3}, 1, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    1,
                    Matrix.create(new double[]{4, 5, 6}, 1, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    0,
                    Matrix.create(new double[]{1, 2}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    1,
                    Matrix.create(new double[]{3, 4}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    2,
                    Matrix.create(new double[]{5, 6}, 1, 2))
    );
    @ParameterizedTest
    @VariableSource("getRowArguments")
    public void testGetRow(Matrix matrix, int rowIndex, Matrix expected) {
        Matrix actual = matrix.getRow(rowIndex);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // getCol
    @SuppressWarnings("unused")
    static Stream<Arguments> getColExceptionArguments = Stream.of(
            // rowIndex < 0
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), -1,
                    "'colIndex' = (-1) has to be between 0 and 0")
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1,
                    "'colIndex' = (1) has to be between 0 and 0")
    );
    @ParameterizedTest
    @VariableSource("getColExceptionArguments")
    public void testGetColException(Matrix matrix, int colIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.getCol(colIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> getColArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    0,
                    Matrix.create(new double[]{1, 4}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    1,
                    Matrix.create(new double[]{2, 5}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    2,
                    Matrix.create(new double[]{3, 6}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    0,
                    Matrix.create(new double[]{1, 3, 5}, 3, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    1,
                    Matrix.create(new double[]{2, 4, 6}, 3, 1))
    );
    @ParameterizedTest
    @VariableSource("getColArguments")
    public void testGetCol(Matrix matrix, int colIndex, Matrix expected) {
        Matrix actual = matrix.getCol(colIndex);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // setRow
    @SuppressWarnings("unused")
    static Stream<Arguments> setRowExceptionArguments = Stream.of(
            // row == null
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    null,
                    0,
                    "Input matrix cannot be null")
            // row.numRows != 1
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1, 2}, 2, 1),
                    0,
                    "'numRows' = (2) has to be 1 for 'row'")
            // matrix.numCols != row.numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1, 2}, 1, 2),
                    0,
                    "Dimension mismatch for 'numCols': 'matrix' = (1) vs 'row' = (2)")
            , Arguments.of(
                    Matrix.create(new double[]{1, 2}, 1, 2),
                    Matrix.create(new double[]{0}, 1, 1),
                    0,
                    "Dimension mismatch for 'numCols': 'matrix' = (2) vs 'row' = (1)")
            // rowIndex < 0
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1),
                    -1,
                    "'rowIndex' = (-1) has to be between 0 and 0")
            // rowIndex >= matrix.numRows
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1),
                    1,
                    "'rowIndex' = (1) has to be between 0 and 0")
    );
    @ParameterizedTest
    @VariableSource("setRowExceptionArguments")
    public void testSetRowException(Matrix matrix, Matrix row, int rowIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.setRow(row, rowIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> setRowArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{3}, 1, 1),
                    0,
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[]{0, 0, 0}, 1, 3),
                    0,
                    Matrix.create(new double[]{0, 0, 0, 4, 5, 6}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[]{0, 0, 0}, 1, 3),
                    1,
                    Matrix.create(new double[]{1, 2, 3, 0, 0, 0}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[]{0, 0}, 1, 2),
                    0,
                    Matrix.create(new double[]{0, 0, 3, 4, 5, 6}, 3, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[]{0, 0}, 1, 2),
                    1,
                    Matrix.create(new double[]{1, 2, 0, 0, 5, 6}, 3, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[]{0, 0}, 1, 2),
                    2,
                    Matrix.create(new double[]{1, 2, 3, 4, 0, 0}, 3, 2))
    );
    @ParameterizedTest
    @VariableSource("setRowArguments")
    public void testSetRow(Matrix matrix, Matrix row, int rowIndex, Matrix expected) {
        Matrix actual = matrix.setRow(row, rowIndex);
        assertEquals(expected, actual);
        assertNotSame(actual, matrix);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // setCol
    @SuppressWarnings("unused")
    static Stream<Arguments> setColExceptionArguments = Stream.of(
            // col == null
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    null,
                    0,
                    "Input matrix cannot be null")
            // col.numCols != 1
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1, 2}, 1, 2),
                    0,
                    "'numCols' = (2) has to be 1 for 'col'")
            // matrix.numRows != row.numRows
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{1, 2}, 2, 1),
                    0,
                    "Dimension mismatch for 'numRows': 'matrix' = (1) vs 'col' = (2)")
            , Arguments.of(
                    Matrix.create(new double[]{1, 2}, 2, 1),
                    Matrix.create(new double[]{0}, 1, 1),
                    0,
                    "Dimension mismatch for 'numRows': 'matrix' = (2) vs 'col' = (1)")
            // colIndex < 0
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1),
                    -1,
                    "'colIndex' = (-1) has to be between 0 and 0")
            // colIndex >= matrix.numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{0}, 1, 1),
                    1,
                    "'colIndex' = (1) has to be between 0 and 0")
    );
    @ParameterizedTest
    @VariableSource("setColExceptionArguments")
    public void testSetColException(Matrix matrix, Matrix col, int colIndex, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.setCol(col, colIndex));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> setColArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    Matrix.create(new double[]{3}, 1, 1),
                    0,
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[]{0, 0}, 2, 1),
                    0,
                    Matrix.create(new double[]{0, 2, 3, 0, 5, 6}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[]{0, 0}, 2, 1),
                    1,
                    Matrix.create(new double[]{1, 0, 3, 4, 0, 6}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[]{0, 0}, 2, 1),
                    2,
                    Matrix.create(new double[]{1, 2, 0, 4, 5, 0}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[]{0, 0, 0}, 3, 1),
                    0,
                    Matrix.create(new double[]{0, 2, 0, 4, 0, 6}, 3, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[]{0, 0, 0}, 3, 1),
                    1,
                    Matrix.create(new double[]{1, 0, 3, 0, 5, 0}, 3, 2))
    );
    @ParameterizedTest
    @VariableSource("setColArguments")
    public void testSetCol(Matrix matrix, Matrix col, int colIndex, Matrix expected) {
        Matrix actual = matrix.setCol(col, colIndex);
        assertEquals(expected, actual);
        assertNotSame(actual, matrix);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // add (matrix with double)
    @SuppressWarnings("unused")
    static Stream<Arguments> addDoubleArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    1,
                    Matrix.create(new double[]{2}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    2,
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{2}, 1, 1),
                    1,
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    3,
                    Matrix.create(new double[]{4, 5, 6, 7}, 2, 2))
    );

    @ParameterizedTest
    @VariableSource("addDoubleArguments")
    public void testAddDouble(Matrix matrix, double value, Matrix expected) {
        Matrix actual = matrix.add(value);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // add (matrix with matrix)
    @SuppressWarnings("unused")
    static Stream<Arguments> addMatrixExceptionArguments = Stream.of(
            // matrices[0] == null
            Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{null},
                    "Input matrix cannot be null")
            // matrices[1] == null
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1), null},
                    "Input matrix cannot be null")
            // matrices[2] == null
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{0}, 1, 1),
                            null},
                    "Input matrix cannot be null")
            // left.numRows != matrices[0].numRows
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{1, 2}, 2, 1)},
                    "Dimension mismatch for adding matrices")
            // left.numCols != matrices[0].numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{1, 2}, 1, 2)},
                    "Dimension mismatch for adding matrices")
            // left.numRows != matrices[0].numRows && left.numCols != matrices[0].numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    "Dimension mismatch for adding matrices")
            // left.numRows != matrices[1].numRows
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 1, 2)},
                    "Dimension mismatch for adding matrices")
            // left.numCols != matrices[1].numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 2, 1)},
                    "Dimension mismatch for adding matrices")
            // left.numRows != matrices[1].numRows && left.numCols != matrices[1].numCols
            , Arguments.of(
                    Matrix.create(new double[]{0}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    "Dimension mismatch for adding matrices")
    );

    @ParameterizedTest
    @VariableSource("addMatrixExceptionArguments")
    public void testAddMatrixException(Matrix matrix, Matrix[] matrices, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.add(matrices));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> addMatrixArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    new Matrix[]{},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{2}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    new Matrix[]{Matrix.create(new double[]{5, 6, 7, 8}, 2, 2)},
                    Matrix.create(new double[]{6, 8, 10, 12}, 2, 2))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                    new Matrix[]{Matrix.create(new double[]{5, 6, 7, 8, 9, 10}, 2, 3)},
                    Matrix.create(new double[]{6, 8, 10, 12, 14, 16}, 2, 3))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    new Matrix[]{Matrix.create(new double[]{2}, 1, 1), Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{4}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    new Matrix[]{
                            Matrix.create(new double[]{2}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{6}, 1, 1))
    );

    @ParameterizedTest
    @VariableSource("addMatrixArguments")
    public void testAddMatrix(Matrix matrix, Matrix[] matrices, Matrix expected) {
        Matrix actual = matrix.add(matrices);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // sum
    @SuppressWarnings("unused")
    static Stream<Arguments> sumExceptionArguments = Stream.of(
            // matrices.length = 0
            Arguments.of(
                    new Matrix[]{},
                    "Need at least one matrix")
            // matrices[0] == null
            , Arguments.of(
                    new Matrix[]{null},
                    "Input matrix cannot be null")
            // matrices[1] == null
            , Arguments.of(
                    new Matrix[]{Matrix.create(new double[]{0}, 1, 1), null},
                    "Input matrix cannot be null")
            // matrices[2] == null
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{0}, 1, 1),
                            null},
                    "Input matrix cannot be null")
            // matrices[0].numRows != matrices[1].numRows
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 2, 1)},
                    "Dimension mismatch for adding matrices")
            // matrices[0].numCols != matrices[1].numCols
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 1, 2)},
                    "Dimension mismatch for adding matrices")
            // matrices[0].numRows != matrices[1].numRows && left.numCols != matrices[0].numCols != matrices[1].numCols
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    "Dimension mismatch for adding matrices")
            // matrices[0].numRows != matrices[2].numRows
            , Arguments.of(

                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 1, 2)},
                    "Dimension mismatch for adding matrices")
            // matrices[0].numCols != matrices[2].numCols
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2}, 2, 1)},
                    "Dimension mismatch for adding matrices")
            // matrices[0].numRows != matrices[2].numRows && matrices[0].numCols != matrices[2].numCols
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{0}, 1, 1),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    "Dimension mismatch for adding matrices")
    );

    @ParameterizedTest
    @VariableSource("sumExceptionArguments")
    public void testSumException(Matrix[] matrices, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.sum(matrices));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> sumArguments = Stream.of(
            Arguments.of(
                    new Matrix[]{Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{2}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                            Matrix.create(new double[]{5, 6, 7, 8}, 2, 2)},
                    Matrix.create(new double[]{6, 8, 10, 12}, 2, 2))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3),
                            Matrix.create(new double[]{5, 6, 7, 8, 9, 10}, 2, 3)},
                    Matrix.create(new double[]{6, 8, 10, 12, 14, 16}, 2, 3))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{3}, 1, 1))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{4}, 1, 1))
            , Arguments.of(
                    new Matrix[]{
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{2}, 1, 1)},
                    Matrix.create(new double[]{6}, 1, 1))
    );

    @ParameterizedTest
    @VariableSource("sumArguments")
    public void testSum(Matrix[] matrices, Matrix expected) {
        Matrix actual = Matrix.sum(matrices);
        assertEquals(expected, actual);
        for (Matrix matrix: matrices) {
            assertNotSame(matrix, actual);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // multiply (matrix with double)
    @SuppressWarnings("unused")
    static Stream<Arguments> multiplyDoubleArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    1,
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1}, 1, 1),
                    0,
                    Matrix.create(new double[]{0}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{2}, 1, 1),
                    2,
                    Matrix.create(new double[]{4}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{2}, 1, 1),
                    3,
                    Matrix.create(new double[]{6}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                    3,
                    Matrix.create(new double[]{3, 6, 9, 12}, 2, 2))
    );

    @ParameterizedTest
    @VariableSource("multiplyDoubleArguments")
    public void testMultiplyDouble(Matrix matrix, double value, Matrix expected) {
        Matrix actual = matrix.multiply(value);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // prod
    @SuppressWarnings("unused")
    static Stream<Arguments> prodExceptionArguments = Stream.of(
            // matrices[0] == null
            Arguments.of(
                    new Matrix[] {
                            null,
                            Matrix.create(new double[] {1}, 1, 1)},
                    "Input matrix cannot be null")
            // matrices[1] == null
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            null},
                    "Input matrix cannot be null")
            // matrices[0].numCols != matrices[1].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2}, 2, 1)},
                    "Dimension mismatch for taking product of matrices")
            // matrices[0].numCols != matrices[1].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1, 2}, 1, 2),
                            Matrix.create(new double[] {1}, 1, 1)},
                    "Dimension mismatch for taking product of matrices")
            // matrices[1].numCols != matrices[2].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1, 2}, 1, 2),
                            Matrix.create(new double[] {1, 1}, 2, 1),
                            Matrix.create(new double[] {1, 2}, 2, 1)},
                    "Dimension mismatch for taking product of matrices")
    );

    @ParameterizedTest
    @VariableSource("prodExceptionArguments")
    public void testProdException(Matrix[] matrices, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.prod(matrices));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> prodArguments = Stream.of(
            Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{1, 0, 0, 1}, 2, 2),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    Matrix.create(new double[]{1, 2, 3, 4}, 2, 2))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{0, 0, 0, 0}, 2, 2),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2)},
                    Matrix.create(new double[]{0, 0, 0, 0}, 2, 2))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{1, 2, 3, 4}, 1, 4),
                            Matrix.create(new double[]{1, 2, 3, 4}, 4, 1)},
                    Matrix.create(new double[]{30}, 1, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                            Matrix.create(new double[]{1, 2}, 2, 1)},
                    Matrix.create(new double[]{5, 11}, 2, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1),
                            Matrix.create(new double[]{1}, 1, 1)},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[]{2, 1}, 1, 2),
                            Matrix.create(new double[]{1, 2, 3, 4}, 2, 2),
                            Matrix.create(new double[]{1, 2}, 2, 1)},
                    Matrix.create(new double[]{21}, 1, 1))
    );
    @ParameterizedTest
    @VariableSource("prodArguments")
    public void testProd(Matrix[] matrices, Matrix expected) {
        Matrix actual = Matrix.prod(matrices);
        assertEquals(expected, actual);
        for (Matrix matrix: matrices) {
            assertNotSame(matrix, actual);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // horizontalConcatenate
    @SuppressWarnings("unused")
    static Stream<Arguments> horizontalConcatenateExceptionArguments = Stream.of(
            // matrices == {}
            Arguments.of(
                    new Matrix[] {},
                    "Need at least one matrix")
            // matrices == {null}
            , Arguments.of(
                    new Matrix[] {null},
                    "Input matrix cannot be null")
            // matrices == {null, ...}
            , Arguments.of(
                    new Matrix[] {null, Matrix.create(new double[] {1}, 1, 1)},
                    "Input matrix cannot be null")
            // matrices[0].numRows != matrices[1].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2}, 2, 1)},
                    "Dimension mismatch for 'numRows'")
            // matrices[0].numRows != matrices[1].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2, 3}, 3, 1)},
                    "Dimension mismatch for 'numRows'")
            // matrices[0].numRows != matrices[2].numRows
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2}, 2, 1)},
                    "Dimension mismatch for 'numRows'")
    );

    @ParameterizedTest
    @VariableSource("horizontalConcatenateExceptionArguments")
    public void testHorizontalConcatenateException(Matrix[] matrices, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.horizontalConcatenate(matrices));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> horizontalConcatenateArguments = Stream.of(
            Arguments.of(
                    new Matrix[] {Matrix.create(new double[] {1}, 1, 1)},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {2, 3}, 1, 2)},
                    Matrix.create(new double[]{1, 2, 3}, 1, 3))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {2, 3}, 1, 2),
                            Matrix.create(new double[] {4, 5, 6}, 1, 3)},
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 1, 6))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1, 2}, 2, 1),
                            Matrix.create(new double[] {3, 4, 5, 6}, 2, 2),
                            Matrix.create(new double[] {7, 8, 9, 10, 11, 12}, 2, 3)},
                    Matrix.create(new double[]{1, 3, 4, 7, 8, 9, 2, 5, 6, 10, 11, 12}, 2, 6))
    );
    @ParameterizedTest
    @VariableSource("horizontalConcatenateArguments")
    public void testHorizontalConcatenate(Matrix[] matrices, Matrix expected) {
        Matrix actual = Matrix.horizontalConcatenate(matrices);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // verticalConcatenate
    @SuppressWarnings("unused")
    static Stream<Arguments> verticalConcatenateExceptionArguments = Stream.of(
            // matrices == {}
            Arguments.of(
                    new Matrix[] {},
                    "Need at least one matrix")
            // matrices == {null}
            , Arguments.of(
                    new Matrix[] {null},
                    "Input matrix cannot be null")
            // matrices == {null, ...}
            , Arguments.of(
                    new Matrix[] {null, Matrix.create(new double[] {1}, 1, 1)},
                    "Input matrix cannot be null")
            // matrices[0].numCols != matrices[1].numCols
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2}, 1, 2)},
                    "Dimension mismatch for 'numCols'")
            // matrices[0].numCols != matrices[1].numCols
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2, 3}, 1, 3)},
                    "Dimension mismatch for 'numCols'")
            // matrices[0].numCols != matrices[2].numCols
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {1, 2}, 1, 2)},
                    "Dimension mismatch for 'numCols'")
    );

    @ParameterizedTest
    @VariableSource("verticalConcatenateExceptionArguments")
    public void testVerticalConcatenateException(Matrix[] matrices, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> Matrix.verticalConcatenate(matrices));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> verticalConcatenateArguments = Stream.of(
            Arguments.of(
                    new Matrix[] {Matrix.create(new double[] {1}, 1, 1)},
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {2, 3}, 2, 1)},
                    Matrix.create(new double[]{1, 2, 3}, 3, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1}, 1, 1),
                            Matrix.create(new double[] {2, 3}, 2, 1),
                            Matrix.create(new double[] {4, 5, 6}, 3, 1)},
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 6, 1))
            , Arguments.of(
                    new Matrix[] {
                            Matrix.create(new double[] {1, 2}, 1, 2),
                            Matrix.create(new double[] {3, 4, 5, 6}, 2, 2),
                            Matrix.create(new double[] {7, 8, 9, 10, 11, 12}, 3, 2)},
                    Matrix.create(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}, 6, 2))
    );
    @ParameterizedTest
    @VariableSource("verticalConcatenateArguments")
    public void testVerticalConcatenate(Matrix[] matrices, Matrix expected) {
        Matrix actual = Matrix.verticalConcatenate(matrices);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // transpose

    @SuppressWarnings("unused")
    static Stream<Arguments> transposeArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[] {1}, 1, 1),
                    Matrix.create(new double[] {1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 2, 1),
                    Matrix.create(new double[] {1, 2}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 1, 2),
                    Matrix.create(new double[] {1, 2}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4}, 2, 2),
                    Matrix.create(new double[] {1, 3, 2, 4}, 2, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3),
                    Matrix.create(new double[] {1, 4, 2, 5, 3, 6}, 3, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2),
                    Matrix.create(new double[] {1, 3, 5, 2, 4, 6}, 2, 3))
    );
    @ParameterizedTest
    @VariableSource("transposeArguments")
    public void testTranspose(Matrix matrix, Matrix expected) {
        Matrix actual = matrix.transpose();
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // reshape
    @SuppressWarnings("unused")
    static Stream<Arguments> reshapeExceptionArguments = Stream.of(
            // numRows * numCols != matrix.length
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), 1, 2,
                    "Length of 'array' (1) does not match 'numRows' * 'numCols' (2)")
            , Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), 1, 1,
                    "Length of 'array' (2) does not match 'numRows' * 'numCols' (1)")
    );

    @ParameterizedTest
    @VariableSource("reshapeExceptionArguments")
    public void testReshapeException(Matrix matrix, int numRows, int numCols, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.reshape(numRows, numCols));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> reshapeArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[] {1}, 1, 1), 1, 1,
                    Matrix.create(new double[] {1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 2, 1), 2, 1,
                    Matrix.create(new double[] {1, 2}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 2, 1), 1, 2,
                    Matrix.create(new double[] {1, 2}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 1, 2), 1, 2,
                    Matrix.create(new double[] {1, 2}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 1, 2), 2, 1,
                    Matrix.create(new double[] {1, 2}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4}, 2, 2), 1, 4,
                    Matrix.create(new double[] {1, 2, 3, 4}, 1, 4))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4}, 2, 2), 4, 1,
                    Matrix.create(new double[] {1, 2, 3, 4}, 4, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3), 3, 2,
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2), 2, 3,
                    Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3))
    );
    @ParameterizedTest
    @VariableSource("reshapeArguments")
    public void testReshape(Matrix matrix, int numRows, int numCols, Matrix expected) {
        Matrix actual = matrix.reshape(numRows, numCols);
        assertEquals(expected, actual);
        assertNotSame(matrix, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // sum (all elements)
    @SuppressWarnings("unused")
    static Stream<Arguments> sumAllElementsArguments = Stream.of(
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), 1)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), 3)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 2, 1), 3)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4}, 2, 2), 10)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3), 21)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2), 21)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3), 45)
    );
    @ParameterizedTest
    @VariableSource("sumAllElementsArguments")
    public void testSumAllElements(Matrix matrix, double expected) {
        double actual = matrix.sum();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // sum (over axis)
    @SuppressWarnings("unused")
    static Stream<Arguments> sumOverAxisExceptionArguments = Stream.of(
            // axis != 0 && axis != 1
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), -1, "'axis' (-1) has to be 0 or 1")
            , Arguments.of(Matrix.create(new double[] {1}, 1, 1), 2, "'axis' (2) has to be 0 or 1")
    );
    @ParameterizedTest
    @VariableSource("sumOverAxisExceptionArguments")
    public void testSumOverAxisException(Matrix matrix, int axis, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                () -> matrix.sum(axis));
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> sumOverAxisArguments = Stream.of(
            Arguments.of(
                    Matrix.create(new double[] {1}, 1, 1),
                    0,
                    Matrix.create(new double[] {1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1}, 1, 1),
                    1,
                    Matrix.create(new double[] {1}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 1, 2),
                    0,
                    Matrix.create(new double[] {1, 2}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 1, 2),
                    1,
                    Matrix.create(new double[] {3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 2, 1),
                    0,
                    Matrix.create(new double[] {3}, 1, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2}, 2, 1),
                    1,
                    Matrix.create(new double[] {1, 2}, 2, 1))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4}, 2, 2),
                    0,
                    Matrix.create(new double[] {4, 6}, 1, 2))
            , Arguments.of(
                    Matrix.create(new double[] {1, 2, 3, 4}, 2, 2),
                    1,
                    Matrix.create(new double[] {3, 7}, 2, 1))
    );
    @ParameterizedTest
    @VariableSource("sumOverAxisArguments")
    public void testSumOverAxisElements(Matrix matrix, int axis, Matrix expected) {
        Matrix actual = matrix.sum(axis);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // isSquare
    @SuppressWarnings("unused")
    static Stream<Arguments> isSquareArguments = Stream.of(
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), true)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), false)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 2, 1), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4}, 2, 2), true)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3), true)
    );
    @ParameterizedTest
    @VariableSource("isSquareArguments")
    public void testIsSquare(Matrix matrix, boolean expected) {
        boolean actual = matrix.isSquare();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // isSingleton
    @SuppressWarnings("unused")
    static Stream<Arguments> isSingletonArguments = Stream.of(
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), true)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), false)
            , Arguments.of(Matrix.create(new double[] {1, 2}, 2, 1), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4}, 2, 2), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 2, 3), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6}, 3, 2), false)
            , Arguments.of(Matrix.create(new double[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, 3, 3), false)
    );
    @ParameterizedTest
    @VariableSource("isSingletonArguments")
    public void testIsSingleton(Matrix matrix, boolean expected) {
        boolean actual = matrix.isSingleton();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // toDouble
    @SuppressWarnings("unused")
    static Stream<Arguments> toDoubleExceptionArguments = Stream.of(
            // matrix.numRows != 1 || matrix.numCols != 1
            Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), "Matrix is not a singleton")
            , Arguments.of(Matrix.create(new double[] {1, 2}, 2, 1), "Matrix is not a singleton")
    );
    @ParameterizedTest
    @VariableSource("toDoubleExceptionArguments")
    public void testToDoubleException(Matrix matrix, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                matrix::toDouble);
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> toDoubleArguments = Stream.of(
            Arguments.of(Matrix.create(new double[] {1}, 1, 1), 1)
            , Arguments.of(Matrix.create(new double[] {0}, 1, 1), 0)
            , Arguments.of(Matrix.create(new double[] {Double.NaN}, 1, 1), Double.NaN)
    );
    @ParameterizedTest
    @VariableSource("toDoubleArguments")
    public void testToDouble(Matrix matrix, double expected) {
        double actual = matrix.toDouble();
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // decomposeQRGramSchmidt
    @SuppressWarnings("unused")
    static Stream<Arguments> decomposeQRGramSchmidtExceptionArguments = Stream.of(
            // matrix.numRows != matrix.numCols
            Arguments.of(Matrix.create(new double[] {1, 2}, 1, 2), "Matrix is not square")
            , Arguments.of(Matrix.create(new double[] {1, 2}, 2, 1), "Matrix is not square")
    );
    @ParameterizedTest
    @VariableSource("decomposeQRGramSchmidtExceptionArguments")
    public void testDecomposeQRGramSchmidtException(Matrix matrix, String expected) {
        Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
                matrix::decomposeQRGramSchmidt);
        assertEquals(expected, thrown.getMessage());
    }

    @SuppressWarnings("unused")
    static Stream<Arguments> decomposeQRGramSchmidtArguments = Stream.of(
//            Arguments.of(
//                    Matrix.create(new double[] {1}, 1, 1),
//                    Matrix.create(new double[] {1}, 1, 1),
//                    Matrix.create(new double[] {1}, 1, 1)
//            )
//            , Arguments.of(
//                    Matrix.create(new double[] {1, 0, 0, 1}, 2, 2),
//                    Matrix.create(new double[] {1, 0, 0, 1}, 2, 2),
//                    Matrix.create(new double[] {1, 0, 0, 1}, 2, 2)
//            )
            Arguments.of(
                    Matrix.create(new double[] {1, 1, 0, 1, 0, 1, 0, 1, 1}, 3, 3),
                    Matrix.create(new double[] {0.7071067811865475, 0.4082482904638632, -0.5773502691896257, 0.7071067811865475, -0.40824829046386296, 0.5773502691896258, 0.0, 0.8164965809277261, 0.5773502691896255}, 3, 3),
                    Matrix.create(new double[] {1.414213562373095, 0.7071067811865475, 0.7071067811865475, 0.0, 1.2247448713915894, 0.4082482904638632, 0.0, 0.0, 1.1547005383792515}, 3, 3)
            )
    );
    @ParameterizedTest
    @VariableSource("decomposeQRGramSchmidtArguments")
    public void testDecomposeQRGramSchmidt(Matrix matrix, Matrix expectedQ, Matrix expectedR) {
        Matrix[] QR = matrix.decomposeQRGramSchmidt();
        assertEquals(expectedQ, QR[0]);
        assertEquals(expectedR, QR[1]);
    }

}