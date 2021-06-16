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
        matrix.set(rowIndex, colIndex, value);
        assertEquals(expected, matrix);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // setDiagonal
    @SuppressWarnings("unused")
    static Stream<Arguments> setDiagonalArguments = Stream.of(
            Arguments.of(Matrix.create(new double[]{0}, 1, 1), 0,
                    Matrix.create(new double[]{0}, 1, 1))
            , Arguments.of(Matrix.create(new double[]{0}, 1, 1), 1,
                    Matrix.create(new double[]{1}, 1, 1))
            , Arguments.of(Matrix.create(new double[]{0, 0, 0, 0}, 2, 2), 1,
                    Matrix.create(new double[]{1, 0, 0, 1}, 2, 2))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0,
                    Matrix.create(new double[]{0, 2, 3, 4, 0, 6}, 2, 3))
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0,
                    Matrix.create(new double[]{0, 2, 3, 0, 5, 6}, 3, 2))
    );

    @ParameterizedTest
    @VariableSource("setDiagonalArguments")
    public void testSetDiagonal(Matrix matrix, double value, Matrix expected) {
        matrix.setDiagonal(value);
        assertEquals(expected, matrix);
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
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, new double[]{1, 2, 3})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1,
                    new double[]{4, 5, 6})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0, new double[]{1, 2})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1, new double[]{3, 4})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 2, new double[]{5, 6})
    );
    @ParameterizedTest
    @VariableSource("getRowArguments")
    public void testGetRow(Matrix matrix, int rowIndex, double[] expected) {
        double[] row = matrix.getRow(rowIndex);
        assertArrayEquals(expected, row, 0.0);
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
            Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 0, new double[]{1, 4})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 1, new double[]{2, 5})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3), 2, new double[]{3, 6})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 0,
                    new double[]{1, 3, 5})
            , Arguments.of(Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2), 1,
                    new double[]{2, 4, 6})
    );
    @ParameterizedTest
    @VariableSource("getColArguments")
    public void testGetCol(Matrix matrix, int colIndex, double[] expected) {
        double[] col = matrix.getCol(colIndex);
        assertArrayEquals(expected, col, 0.0);
    }

}

