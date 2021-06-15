package agent.smith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;

import static org.junit.jupiter.api.Assertions.*;

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
            Arguments.of(0, 1, 1, new double[]{ 0 }, 1, 1)
            , Arguments.of(1, 1, 1, new double[]{ 1 }, 1, 1)
            // square
            , Arguments.of(0, 2, 2, new double[]{ 0, 0, 0, 0 }, 2, 2)
            // rectangle
            , Arguments.of(0, 2, 3, new double[]{ 0, 0, 0, 0, 0, 0 }, 2, 3)
            , Arguments.of(0, 3, 2, new double[]{ 0, 0, 0, 0, 0, 0 }, 3, 2)
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
            Arguments.of(new double[]{ 0 }, 0, 1, "'numRows' (0) has to be a positive integer")
            , Arguments.of(new double[]{ 0 }, -1, 1, "'numRows' (-1) has to be a positive integer")
            // numCols <= 0
            , Arguments.of(new double[]{ 0 }, 1, 0, "'numCols' (0) has to be a positive integer")
            , Arguments.of(new double[]{ 0 }, 1, -1, "'numCols' (-1) has to be a positive integer")
            // array == null
            , Arguments.of(null, 1, 1, "'array' cannot be null")
            // numRows * numCols > array.length
            , Arguments.of(new double[]{ 1, 2 }, 2, 2, "Length of 'array' (2) does not match 'numRows' * 'numCols' (4)")
            // numRows * numCols > array.length
            , Arguments.of(new double[]{ 1, 2 }, 1, 1, "Length of 'array' (2) does not match 'numRows' * 'numCols' (1)")
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
            Arguments.of(1, 1, new double[]{ Double.NaN }, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{ Double.NaN, Double.NaN, Double.NaN, Double.NaN }, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{ Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN },
                    2, 3)
            , Arguments.of(3, 2, new double[]{ Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN },
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
            Arguments.of(1, 1, new double[]{ 0 }, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{ 0, 0, 0, 0 }, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{ 0, 0, 0, 0, 0, 0 }, 2, 3)
            , Arguments.of(3, 2, new double[]{ 0, 0, 0, 0, 0, 0 }, 3, 2)
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
            Arguments.of(1, new double[]{ 0 }, 1, 1)
            // non singleton
            , Arguments.of(2, new double[]{ 0, 0, 0, 0 }, 2, 2)
            , Arguments.of(3, new double[]{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 3, 3)
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
            Arguments.of(1, 1, new double[]{ 1 }, 1, 1)
            // square
            , Arguments.of(2, 2, new double[]{ 1, 1, 1, 1 }, 2, 2)
            // rectangle
            , Arguments.of(2, 3, new double[]{ 1, 1, 1, 1, 1, 1 }, 2, 3)
            , Arguments.of(3, 2, new double[]{ 1, 1, 1, 1, 1, 1 }, 3, 2)
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
            Arguments.of(1, new double[]{ 1 }, 1, 1)
            // non singleton
            , Arguments.of(2, new double[]{ 1, 1, 1, 1 }, 2, 2)
            , Arguments.of(3, new double[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 3, 3)
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
            , Arguments.of(new double[][]{  }, "'nestedArray' cannot be empty")
            // nestedArray[0] == null
            , Arguments.of(new double[][]{ null }, "'nestedArray[0]' cannot be null")
            // nestedArray[0] == {  }
            , Arguments.of(new double[][]{ {  } }, "'nestedArray' cannot be empty")
            // nestedArray[1] == null
            , Arguments.of(new double[][]{ { 0 }, null }, "'nestedArray[1]' cannot be null")
            // nestedArray[0].length != nestedArray[1].length
            , Arguments.of(new double[][]{ { 0 }, {  } }, "Inconsistent number of rows for 'nestedArray'")
            , Arguments.of(new double[][]{ { 1 }, { 2, 3 } }, "Inconsistent number of rows for 'nestedArray'")
            , Arguments.of(new double[][]{ { 1, 2 }, { 3 } }, "Inconsistent number of rows for 'nestedArray'")
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
            Arguments.of(new double[][]{ { 1, 2} , { 3, 4 } }, new double[]{ 1, 2, 3, 4 }, 2, 2)
            // rectangle
            , Arguments.of(new double[][]{ { 1, 2, 3 }, { 4, 5, 6 } }, new double[]{ 1, 2, 3, 4, 5, 6 }, 2, 3)
            , Arguments.of(new double[][]{ { 1, 2 }, { 3, 4 } , { 5, 6 } }, new double[]{ 1, 2, 3, 4, 5, 6 }, 3, 2)
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

//    public static class Indexing {
//
//        public static class ValidateNumRows {
//
//            @RunWith(Parameterized.class)
//            public static class MatrixIllegalArgumentExceptionTest {
//
//                @Parameters
//                public static Iterable<Object[]> data() {
//                    return Arrays.asList(new Object[][]{
//                            // index < 0
//                            { Matrix.create(new double[]{0}, 1, 1), -1,
//                                    "'rowIndex' = (-1) has to be between 0 and 0" },
//                            // index >= numRows
//                            { Matrix.create(new double[]{0}, 1, 1), 1,
//                                    "'rowIndex' = (1) has to be between 0 and 0" },
//                    });
//                }
//
//                private final Matrix matrix;
//                private final int rowIndex;
//                private final String expected;
//
//                public MatrixIllegalArgumentExceptionTest(Matrix matrix, int rowIndex, String expected) {
//                    this.matrix = matrix;
//                    this.rowIndex = rowIndex;
//                    this.expected = expected;
//                }
//
//                @Test
//                public void test() {
//                    Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
//                            () -> this.matrix.validateRowIndex(this.rowIndex));
//                    assertEquals(this.expected, thrown.getMessage());
//                }
//
//            }
//
//        }
//
//        public static class ValidateNumCols {
//
//            @RunWith(Parameterized.class)
//            public static class MatrixIllegalArgumentExceptionTest {
//
//                @Parameters
//                public static Iterable<Object[]> data() {
//                    return Arrays.asList(new Object[][]{
//                            // colIndex < 0
//                            { Matrix.create(new double[]{0}, 1, 1), -1,
//                                    "'colIndex' = (-1) has to be between 0 and 0" },
//                            // colIndex >= numCols
//                            { Matrix.create(new double[]{0}, 1, 1), 1,
//                                    "'colIndex' = (1) has to be between 0 and 0" },
//                    });
//                }
//
//                private final Matrix matrix;
//                private final int colIndex;
//                private final String expected;
//
//                public MatrixIllegalArgumentExceptionTest(Matrix matrix, int colIndex, String expected) {
//                    this.matrix = matrix;
//                    this.colIndex = colIndex;
//                    this.expected = expected;
//                }
//
//                @Test
//                public void test() {
//                    Exception thrown = assertThrows(MatrixIllegalArgumentException.class,
//                            () -> this.matrix.validateColIndex(this.colIndex));
//                    assertEquals(this.expected, thrown.getMessage());
//                }
//
//            }
//
//        }
//
//    }
//
//    @Test
//    public void testMatrixValidateRowIndexExceptionTooBig() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int i = 1;
//        String expected = "Row index 'i' = (1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateRowIndex(i));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixValidateRowIndexExceptionNegative() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int i = -1;
//        String expected = "Row index 'i' = (-1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateRowIndex(i));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixValidateRow() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int i = 0;
//
//        matrix.validateRowIndex(i);
//    }
//
//    @Test
//    public void testMatrixValidateColIndexExceptionTooBig() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int j = 1;
//        String expected = "Col index 'j' = (1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateColIndex(j));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixValidateColIndexExceptionNegative() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int j = -1;
//        String expected = "Col index 'j' = (-1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateColIndex(j));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixValidateCol() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int j = 0;
//
//        matrix.validateColIndex(j);
//    }
//
//    @Test
//    public void testMatrixGetIndex1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 0;
//        int j = 0;
//        int expected = 0;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 0;
//        int j = 1;
//        int expected = 1;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex3() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 0;
//        int j = 2;
//        int expected = 2;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex4() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 1;
//        int j = 0;
//        int expected = 3;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex5() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 1;
//        int j = 1;
//        int expected = 4;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex6() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 1;
//        int j = 2;
//        int expected = 5;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex7() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 0;
//        int j = 0;
//        int expected = 0;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex8() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 0;
//        int j = 1;
//        int expected = 1;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex9() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 1;
//        int j = 0;
//        int expected = 2;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex10() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 1;
//        int j = 1;
//        int expected = 3;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex11() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 2;
//        int j = 0;
//        int expected = 4;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixGetIndex12() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 2;
//        int j = 1;
//        int expected = 5;
//
//        int index = matrix.getIndex(i, j);
//        assertEquals(expected, index);
//    }
//
//    @Test
//    public void testMatrixValidateIndexExceptionNegative() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int index = -1;
//        String expected = "'index' = (-1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateIndex(index));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixValidateIndexExceptionTooBig() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int index = 1;
//        String expected = "'index' = (1) has to be between 0 and 0";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.validateIndex(index));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixGetRowIndex1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 0;
//        int expected = 0;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 1;
//        int expected = 0;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex3() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 2;
//        int expected = 0;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex4() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 3;
//        int expected = 1;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex5() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 4;
//        int expected = 1;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex6() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 5;
//        int expected = 1;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex7() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 0;
//        int expected = 0;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex8() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 1;
//        int expected = 0;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex9() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 2;
//        int expected = 1;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex10() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 3;
//        int expected = 1;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex11() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 4;
//        int expected = 2;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetRowIndex12() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 5;
//        int expected = 2;
//
//        int rowIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, rowIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 0;
//        int expected = 0;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 1;
//        int expected = 1;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex3() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 2;
//        int expected = 2;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex4() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 3;
//        int expected = 0;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex5() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 4;
//        int expected = 1;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex6() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int index = 5;
//        int expected = 2;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex7() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 0;
//        int expected = 0;
//
//        int colIndex = matrix.getRowIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex8() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 1;
//        int expected = 1;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex9() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 2;
//        int expected = 0;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex10() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 3;
//        int expected = 1;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex11() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 4;
//        int expected = 0;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetColIndex12() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int index = 5;
//        int expected = 1;
//
//        int colIndex = matrix.getColIndex(index);
//
//        assertEquals(expected, colIndex);
//    }
//
//    @Test
//    public void testMatrixGetRow1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 0;
//        double[] expected = new double[]{1, 2, 3};
//
//        double[] row = matrix.getRow(i);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetRow2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int i = 1;
//        double[] expected = new double[]{4, 5, 6};
//
//        double[] row = matrix.getRow(i);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetRow3() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 0;
//        double[] expected = new double[]{1, 2};
//
//        double[] row = matrix.getRow(i);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetRow4() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 1;
//        double[] expected = new double[]{3, 4};
//
//        double[] row = matrix.getRow(i);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetRow5() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int i = 2;
//        double[] expected = new double[]{5, 6};
//
//        double[] row = matrix.getRow(i);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetCol1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int j = 0;
//        double[] expected = new double[]{1, 4};
//
//        double[] col = matrix.getCol(j);
//
//        assertArrayEquals(expected, col, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetCol2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int j = 1;
//        double[] expected = new double[]{2, 5};
//
//        double[] col = matrix.getCol(j);
//
//        assertArrayEquals(expected, col, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetCol3() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//        int j = 2;
//        double[] expected = new double[]{3, 6};
//
//        double[] col = matrix.getCol(j);
//
//        assertArrayEquals(expected, col, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetCol4() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int j = 0;
//        double[] expected = new double[]{1, 3, 5};
//
//        double[] row = matrix.getCol(j);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixGetCol5() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//        int j = 1;
//        double[] expected = new double[]{2, 4, 6};
//
//        double[] row = matrix.getCol(j);
//
//        assertArrayEquals(expected, row, 0.0);
//    }
//
//    @Test
//    public void testMatrixEquals1() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        Matrix objectToCompare = null;
//        boolean expected = false;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals2() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        Matrix objectToCompare = Matrix.create(new double[]{0}, 1, 1);
//        boolean expected = true;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals3() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        Matrix objectToCompare = matrix;
//        boolean expected = true;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals4() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        Matrix objectToCompare = Matrix.create(new double[]{1}, 1, 1);
//        boolean expected = false;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals5() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
//        boolean expected = false;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals6() {
//        Matrix matrix = Matrix.create(new double[]{0, 1}, 2, 1);
//        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
//        boolean expected = false;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals7() {
//        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
//        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
//        boolean expected = true;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixEquals8() {
//        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
//        double[] objectToCompare = new double[]{0, 1};
//        boolean expected = false;
//
//        boolean equals = matrix.equals(objectToCompare);
//
//        assertEquals(expected, equals);
//    }
//
//    @Test
//    public void testMatrixHashCode1() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int expected = 30814;
//
//        int hashCode = matrix.hashCode();
//
//        assertEquals(expected, hashCode);
//    }
//
//    @Test
//    public void testMatrixHashCode2() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        int expected = 30814;
//
//        int hashCode = matrix.hashCode();
//
//        assertEquals(expected, hashCode);
//    }
//
//    @Test
//    public void testMatrixHashCode3() {
//        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
//        int expected = 1072725023;
//
//        int hashCode = matrix.hashCode();
//
//        assertEquals(expected, hashCode);
//    }
//
//    @Test
//    public void testMatrixHashCode4() {
//        Matrix matrix = Matrix.create(new double[]{0, 1}, 2, 1);
//        int expected = 1072725953;
//
//        int hashCode = matrix.hashCode();
//
//        assertEquals(expected, hashCode);
//    }
//
//    @Test
//    public void testMatrixToString1() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
//        String expected = "Matrix{0.0000e+00}";
//
//        String string = matrix.toString();
//
//        assertEquals(expected, string);
//    }
//
//    @Test
//    public void testMatrixToString2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2, 2);
//        String expected = "Matrix{1.0000e+00 2.0000e+00\n       3.0000e+00 4.0000e+00}";
//
//        String string = matrix.toString();
//
//        assertEquals(expected, string);
//    }
//
//    @Test
//    public void testMatrixToStringFormat() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2, 2);
//        String expected = "Matrix{1.000000e+00 2.000000e+00\n       3.000000e+00 4.000000e+00}";
//
//        String string = matrix.toString("%.6e");
//
//        assertEquals(expected, string);
//    }
//
//    @Test
//    public void testMatrixToStringFormatDelimiters() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2, 2);
//        String expected = "Matrix{1.000000e+00, 2.000000e+00; 3.000000e+00, 4.000000e+00}";
//
//        String string = matrix.toString("%.6e", "; ", ", ");
//
//        assertEquals(expected, string);
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionNull() {
//        double[][] nestedArray = null;
//        String expected = "'nestedArray' cannot be null";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionNestedNull0() {
//        double[][] nestedArray = {null};
//        String expected = "'nestedArray[0]' cannot be null";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionNestedNull1() {
//        double[] array = null;
//        double[][] nestedArray = {{0}, array};
//        String expected = "'nestedArray[1]' cannot be null";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows1() {
//        double[][] nestedArray = {{0}, {}};
//        String expected = "Inconsistent number of rows for 'nestedArray'";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows2() {
//        double[][] nestedArray = {{0}, {1, 2}};
//        String expected = "Inconsistent number of rows for 'nestedArray'";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows3() {
//        double[][] nestedArray = {{0}, {}};
//        String expected = "Inconsistent number of rows for 'nestedArray'";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows4() {
//        double[][] nestedArray = {{1, 2}, {0}};
//        String expected = "Inconsistent number of rows for 'nestedArray'";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionEmpty1() {
//        double[][] nestedArray = new double[][]{};
//        String expected = "'nestedArray' cannot be empty";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArrayExceptionEmpty2() {
//        double[][] nestedArray = new double[][]{{}};
//        String expected = "'nestedArray' cannot be empty";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.from(nestedArray));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArraySimple1() {
//        double[][] nestedArray = new double[][]{{1, 2}, {3, 4}};
//        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4}, 2, 2);
//
//        Matrix matrix = Matrix.from(nestedArray);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArraySimple2() {
//        double[][] nestedArray = new double[][]{{1, 3}, {2, 4}};
//        Matrix expected = Matrix.create(new double[]{1, 3, 2, 4}, 2, 2);
//
//        Matrix matrix = Matrix.from(nestedArray);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArraySimple3() {
//        double[][] nestedArray = new double[][]{{1, 2, 3}, {4, 5, 6}};
//        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);
//
//        Matrix matrix = Matrix.from(nestedArray);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryFromNestedArraySimple4() {
//        double[][] nestedArray = new double[][]{{1, 2}, {3, 4}, {5, 6}};
//        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);
//
//        Matrix matrix = Matrix.from(nestedArray);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixCopy() {
//        Matrix matrix = Matrix.create(new double[]{0}, 1 ,1);
//
//        Matrix copiedMatrix = matrix.copy();
//
//        assertEquals(matrix, copiedMatrix);
//        assertNotSame(matrix, copiedMatrix);
//    }
//
//    @Test
//    public void testMatrixGet1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2 ,2);
//        int i = 0;
//        int j = 0;
//        double expected = 1;
//
//        double element = matrix.get(i, j);
//
//        assertEquals(expected, element, 0.0);
//    }
//
//    @Test
//    public void testMatrixGet2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2 ,2);
//        int i = 1;
//        int j = 1;
//        double expected = 4;
//
//        double element = matrix.get(i, j);
//
//        assertEquals(expected, element, 0.0);
//    }
//
//    @Test
//    public void testMatrixSet1() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2 ,2);
//        int i = 0;
//        int j = 0;
//        double element = 4;
//        double expected = 4;
//
//        matrix.set(i, j, element);
//        double elementSet = matrix.get(i, j);
//
//        assertEquals(expected, elementSet, 0.0);
//    }
//
//    @Test
//    public void testMatrixSet2() {
//        Matrix matrix = Matrix.create(new double[]{1, 2, 3, 4}, 2 ,2);
//        int i = 1;
//        int j = 1;
//        double element = 1;
//        double expected = 1;
//
//        matrix.set(i, j, element);
//        double elementSet = matrix.get(i, j);
//
//        assertEquals(expected, elementSet, 0.0);
//    }
//
//    @Test
//    public void testMatrixFactoryOfNegativeNumRows() {
//        double element = 1;
//        int numRows = -1;
//        int numCols = 0;
//        String expected = "'numRows' (-1) has to be a positive integer";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.of(element, numRows, numCols));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryOfNegativeNumCols() {
//        double element = 1;
//        int numRows = 1;
//        int numCols = -1;
//        String expected = "'numCols' (-1) has to be a positive integer";
//
//        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> Matrix.of(element, numRows, numCols));
//
//        assertEquals(expected, thrown.getMessage());
//    }
//
//    @Test
//    public void testMatrixFactoryOf1() {
//        double element = 0;
//        int numRows = 1;
//        int numCols = 1;
//        Matrix expected = Matrix.create(new double[]{0}, 1, 1);
//
//        Matrix matrix = Matrix.of(element, numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOf2() {
//        double element = 1;
//        int numRows = 1;
//        int numCols = 1;
//        Matrix expected = Matrix.create(new double[]{1}, 1, 1);
//
//        Matrix matrix = Matrix.of(element, numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOf3() {
//        double element = 1;
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 2, 3);
//
//        Matrix matrix = Matrix.of(element, numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryCreateNaN() {
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(
//                new double[]{Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN},
//                2, 3);
//
//        Matrix matrix = Matrix.create(numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOfZerosRectangular() {
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{0, 0, 0, 0, 0, 0}, 2, 3);
//
//        Matrix matrix = Matrix.ofZeros(numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOfZerosSquare() {
//        int numRowsAndCols = 2;
//        Matrix expected = Matrix.create(new double[]{0, 0, 0, 0}, 2, 2);
//
//        Matrix matrix = Matrix.ofZeros(numRowsAndCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOfOnesRectangular() {
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 2, 3);
//
//        Matrix matrix = Matrix.ofOnes(numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixFactoryOfOnesSquare() {
//        int numRowsAndCols = 2;
//        Matrix expected = Matrix.create(new double[]{1, 1, 1, 1}, 2, 2);
//
//        Matrix matrix = Matrix.ofOnes(numRowsAndCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixSetDiagonal1() {
//        Matrix matrix = Matrix.ofZeros(2, 3);
//        double value = 1;
//        Matrix expected = Matrix.create(new double[]{1, 0, 0, 0, 1, 0}, 2, 3);
//
//        matrix.setDiagonal(value);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixSetDiagonal2() {
//        Matrix matrix = Matrix.ofZeros(3, 2);
//        double value = 1;
//        Matrix expected = Matrix.create(new double[]{1, 0, 0, 1, 0, 0}, 3, 2);
//
//        matrix.setDiagonal(value);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixSetDiagonal3() {
//        Matrix matrix = Matrix.ofZeros(2, 2);
//        double value = 1;
//        Matrix expected = Matrix.create(new double[]{1, 0, 0, 1}, 2, 2);
//
//        matrix.setDiagonal(value);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfEyeRectangular() {
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{1, 0, 0, 0, 1, 0}, 2, 3);
//
//        Matrix matrix = Matrix.instanceOfEye(numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//
//    @Test
//    public void testMatrixInstanceOfEyeSquare() {
//        int numRowsAndCols = 2;
//        Matrix expected = Matrix.create(new double[]{1, 0, 0, 1}, 2, 2);
//
//        Matrix matrix = Matrix.instanceOfEye(numRowsAndCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom1() {
//        Random r = new Random();
//        r.setSeed(0);
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{
//                0.8025330637390305, -0.9015460884175122, 2.080920790428163,
//                0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 2, 3);
//
//        Matrix matrix = Matrix.instanceOfRandom(r, numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom2() {
//        Random r = new Random();
//        r.setSeed(0);
//        int numRowsAndCols = 2;
//        Matrix expected = Matrix.create(new double[]{0.8025330637390305, -0.9015460884175122,
//                2.080920790428163, 0.7637707684364894}, 2, 2);
//
//        Matrix matrix = Matrix.instanceOfRandom(r, numRowsAndCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom3() {
//        long seed = 0;
//        int numRows = 2;
//        int numCols = 3;
//        Matrix expected = Matrix.create(new double[]{
//                0.8025330637390305, -0.9015460884175122, 2.080920790428163,
//                0.7637707684364894, 0.9845745328825128, -1.6834122587673428}, 2, 3);
//
//        Matrix matrix = Matrix.instanceOfRandom(seed, numRows, numCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom4() {
//        long seed = 0;
//        int numRowsAndCols = 2;
//        Matrix expected = Matrix.create(new double[]{0.8025330637390305, -0.9015460884175122,
//                2.080920790428163, 0.7637707684364894}, 2, 2);
//
//        Matrix matrix = Matrix.instanceOfRandom(seed, numRowsAndCols);
//
//        assertEquals(expected, matrix);
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom5() {
//        int numRows = 2;
//        int numCols = 3;
//
//        Matrix matrix = Matrix.instanceOfRandom(numRows, numCols);
//
//        assertEquals(Matrix.class, matrix.getClass());
//    }
//
//    @Test
//    public void testMatrixInstanceOfRandom6() {
//        int numRowsAndCols = 2;
//
//        Matrix matrix = Matrix.instanceOfRandom(numRowsAndCols);
//
//        assertEquals(Matrix.class, matrix.getClass());
//    }
//
////    @Test
////    public void testMatrixAddExceptionDimensionMismatch() {
////        Matrix matrix = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 2, 3);
////        Matrix other = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 3, 2);
////        String expected = "Dimension mismatch, numRows: 2 vs 3 and numCols: 3 vs 2";
////
////        Exception thrown = assertThrows(MatrixIllegalArgumentException.class, () -> matrix.add(other));
////
////        assertEquals(expected, thrown.getMessage());
////    }
////
////    @Test
////    public void testMatrixAdd1() {
////        Matrix matrix = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 2, 3);
////        Matrix other = Matrix.create(new double[]{1, 1, 1, 1, 1, 1}, 2, 3);
////        Matrix expected = Matrix.create(new double[]{2, 2, 2, 2, 2, 2}, 2, 3);
////
////        Matrix actual = matrix.add(other);
////
////        assertEquals(expected, actual);
////    }

}

