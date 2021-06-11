/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package agent.smith;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatrixTest {

    @Test
    public void testMatrixFactoryCreateNegativeNumRows() {
        double[] array = new double[]{};
        int numRows = -1;
        int numCols = 0;
        String expected = "'numRows' (-1) has to be a non negative integer";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.create(array, numRows, numCols));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryCreateNegativeNumCols() {
        double[] array = new double[]{};
        int numRows = 0;
        int numCols = -1;
        String expected = "'numCols' (-1) has to be a non negative integer";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.create(array, numRows, numCols));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryCreateExceptionNull() {
        double[] array = null;
        int numRows = 0;
        int numCols = 0;
        String expected = "'array' cannot be null";

        Exception thrown = assertThrows(NullPointerException.class, () -> Matrix.create(array, numRows, numCols));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryCreateExceptionLengthTooLow() {
        double[] array = new double[]{};
        int numRows = 1;
        int numCols = 1;
        String expected = "Length of 'array' (0) does not match 'numRows' * 'numCols' (1)";

        Exception thrown = assertThrows(
                IllegalArgumentException.class, () -> Matrix.create(array, numRows, numCols));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryCreateExceptionLengthTooHigh() {
        double[] array = new double[]{1, 2, 3, 4};
        int numRows = 1;
        int numCols = 1;
        String expected = "Length of 'array' (4) does not match 'numRows' * 'numCols' (1)";

        Exception thrown = assertThrows(
                IllegalArgumentException.class, () -> Matrix.create(array, numRows, numCols));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryCreateEmpty() {
        double[] array = new double[]{};
        int numRows = 0;
        int numCols = 0;
        double[] expectedArray = new double[]{};
        int expectedNumRows = 0;
        int expectedNumCols = 0;

        Matrix matrix = Matrix.create(array, numRows, numCols);

        assertArrayEquals(matrix.getArray(), expectedArray, 0.0);
        assertEquals(matrix.getNumRows(), expectedNumRows);
        assertEquals(matrix.getNumCols(), expectedNumCols);
    }

    @Test
    public void testMatrixFactoryCreateSimple1() {
        double[] array = new double[]{1, 2, 3, 4};
        int numRows = 2;
        int numCols = 2;
        double[] expectedArray = new double[]{1, 2, 3, 4};
        int expectedNumRows = 2;
        int expectedNumCols = 2;

        Matrix matrix = Matrix.create(array, numRows, numCols);

        assertArrayEquals(matrix.getArray(), expectedArray, 0.0);
        assertEquals(matrix.getNumRows(), expectedNumRows);
        assertEquals(matrix.getNumCols(), expectedNumCols);
    }

    @Test
    public void testMatrixFactoryCreateSimple2() {
        double[] array = new double[]{1, 2, 3, 4, 5, 6};
        int numRows = 2;
        int numCols = 3;
        double[] expectedArray = new double[]{1, 2, 3, 4, 5, 6};
        int expectedNumRows = 2;
        int expectedNumCols = 3;

        Matrix matrix = Matrix.create(array, numRows, numCols);

        assertArrayEquals(matrix.getArray(), expectedArray, 0.0);
        assertEquals(matrix.getNumRows(), expectedNumRows);
        assertEquals(matrix.getNumCols(), expectedNumCols);
    }

    @Test
    public void testMatrixFactoryCreateSimple3() {
        double[] array = new double[]{1, 2, 3, 4, 5, 6};
        int numRows = 3;
        int numCols = 2;
        double[] expectedArray = new double[]{1, 2, 3, 4, 5, 6};
        int expectedNumRows = 3;
        int expectedNumCols = 2;

        Matrix matrix = Matrix.create(array, numRows, numCols);

        assertArrayEquals(matrix.getArray(), expectedArray, 0.0);
        assertEquals(matrix.getNumRows(), expectedNumRows);
        assertEquals(matrix.getNumCols(), expectedNumCols);
    }

    @Test
    public void testMatrixEquals1() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        Matrix objectToCompare = null;
        boolean expected = false;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals2() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        Matrix objectToCompare = Matrix.create(new double[]{}, 0, 0);
        boolean expected = true;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals3() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        Matrix objectToCompare = matrix;
        boolean expected = true;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals4() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        Matrix objectToCompare = Matrix.create(new double[]{1}, 1, 1);
        boolean expected = false;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals5() {
        Matrix matrix = Matrix.create(new double[]{0}, 1, 1);
        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
        boolean expected = false;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals6() {
        Matrix matrix = Matrix.create(new double[]{0, 1}, 2, 1);
        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
        boolean expected = false;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals7() {
        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
        Matrix objectToCompare = Matrix.create(new double[]{0, 1}, 1, 2);
        boolean expected = true;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixEquals8() {
        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
        double[] objectToCompare = new double[]{0, 1};
        boolean expected = false;

        boolean equals = matrix.equals(objectToCompare);

        assertEquals(equals, expected);
    }

    @Test
    public void testMatrixHashCode1() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        int expected = 29792;

        int hashCode = matrix.hashCode();

        assertEquals(hashCode, expected);
    }

    @Test
    public void testMatrixHashCode2() {
        Matrix matrix = Matrix.create(new double[]{}, 0, 0);
        int expected = 29792;

        int hashCode = matrix.hashCode();

        assertEquals(hashCode, expected);
    }

    @Test
    public void testMatrixHashCode3() {
        Matrix matrix = Matrix.create(new double[]{0, 1}, 1, 2);
        int expected = 1072725023;

        int hashCode = matrix.hashCode();

        assertEquals(hashCode, expected);
    }

    @Test
    public void testMatrixHashCode4() {
        Matrix matrix = Matrix.create(new double[]{0, 1}, 2, 1);
        int expected = 1072725953;

        int hashCode = matrix.hashCode();

        assertEquals(hashCode, expected);
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionNull() {
        double[][] nestedArray = null;
        String expected = "'nestedArray' cannot be null";

        Exception thrown = assertThrows(NullPointerException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionNestedNull0() {
        double[][] nestedArray = {null};
        String expected = "'nestedArray[0]' cannot be null";

        Exception thrown = assertThrows(NullPointerException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionNestedNull1() {
        double[] array = null;
        double[][] nestedArray = {{0}, array};
        String expected = "'nestedArray[1]' cannot be null";

        Exception thrown = assertThrows(NullPointerException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows1() {
        double[][] nestedArray = {{0}, {}};
        String expected = "Inconsistent number of rows for 'nestedArray'";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows2() {
        double[][] nestedArray = {{0}, {1, 2}};
        String expected = "Inconsistent number of rows for 'nestedArray'";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows3() {
        double[][] nestedArray = {{}, {0}};
        String expected = "Inconsistent number of rows for 'nestedArray'";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayExceptionInconsistentNumRows4() {
        double[][] nestedArray = {{1, 2}, {0}};
        String expected = "Inconsistent number of rows for 'nestedArray'";

        Exception thrown = assertThrows(IllegalArgumentException.class, () -> Matrix.from(nestedArray));

        assertEquals(expected, thrown.getMessage());
    }

    @Test
    public void testMatrixFactoryFromNestedArrayEmpty() {
        double[][] array = new double[][]{{}};
        double[] expected = new double[]{};

        Matrix matrix = Matrix.from(array);

        assertArrayEquals(expected, matrix.getArray(), 0.0);
    }

    @Test
    public void testMatrixFactoryFromNestedArraySimple1() {
        double[][] array = new double[][]{{1, 2}, {3, 4}};
        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4}, 2, 2);

        Matrix matrix = Matrix.from(array);

        assertEquals(matrix, expected);
    }

    @Test
    public void testMatrixFactoryFromNestedArraySimple2() {
        double[][] array = new double[][]{{1, 3}, {2, 4}};
        Matrix expected = Matrix.create(new double[]{1, 3, 2, 4}, 2, 2);

        Matrix matrix = Matrix.from(array);

        assertEquals(matrix, expected);
    }

    @Test
    public void testMatrixFactoryFromNestedArraySimple3() {
        double[][] array = new double[][]{{1, 2, 3}, {4, 5, 6}};
        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 2, 3);

        Matrix matrix = Matrix.from(array);

        assertEquals(matrix, expected);
    }

    @Test
    public void testMatrixFactoryFromNestedArraySimple4() {
        double[][] array = new double[][]{{1, 2}, {3, 4}, {5, 6}};
        Matrix expected = Matrix.create(new double[]{1, 2, 3, 4, 5, 6}, 3, 2);

        Matrix matrix = Matrix.from(array);

        assertEquals(matrix, expected);
    }

    @Test
    public void testMatrixCopy() {
        Matrix matrix = Matrix.create(new double[]{}, 0 ,0);

        Matrix copiedMatrix = matrix.copy();

        assertEquals(copiedMatrix, matrix);
        assertNotSame(copiedMatrix, matrix);
    }

}

