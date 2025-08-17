package game2048logic;

import game2048rendering.Side;
import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        // fill this in
        if (side == Side.NORTH) {
           matrixTranspose(board);
           for (int[] column : board) {
               mergeNum(column);
           }
           matrixTranspose(board);
            return;
        } else if (side == Side.EAST) {
            for (int[] row : board) {
                reverseArray(row);
                mergeNum(row);
                reverseArray(row);
            }
            return;
        } else if (side == Side.WEST) {
            for(int[] row : board) {
                mergeNum(row);
            }
            return;
        } else { // SOUTH
            matrixTranspose(board);
            for (int[] column : board) {
                reverseArray(column);
                mergeNum(column);
                reverseArray(column);
            }
            matrixTranspose(board);
            return;
        }
    }

    //对一个数组从左至右合并数字
    public static void mergeNum(int[] array) {
        //移动数字
        moveNum(array);
        //合并数字
        for (int i = 0; i < array.length - 1; i++) {
            if (hasNum(array[i]) && array[i] == array[i + 1]) {
                array[i] = doubleNum(array[i]);
                array[i + 1] = 0;
            }
        }
        //移动数字
        moveNum(array);
    }

    public static int doubleNum(int n) {
       return n * 2;
    }

    public static boolean hasNum(int n) {
        return n != 0;
    }

    public static void matrixTranspose(int[][] board) {
        int length = 4;
        int[][] copy = new int[length][length];
        for (int i = 0; i < length; i++) {
           for (int j = 0; j < length; j++) {
               copy[j][i] = board[i][j];
           }
        }
        for(int i = 0; i < length; i++) {
            System.arraycopy(copy[i], 0, board[i], 0, length);
        }
    }

    // 从左到右移动数字
    public static void moveNum(int[] array) {
        int zeroIndex = 0;
        for(int i = 0; i < array.length; i++) {
            if(hasNum(array[i])) {
                array[zeroIndex] = array[i];
                zeroIndex++;
            }
        }
        while (zeroIndex < array.length) {
            array[zeroIndex] = 0;
            zeroIndex++;
        }
    }

    // 反转array
    public static void reverseArray(int[] array) {
        // 创建copy
        int length = array.length;
        int[] copyArray = new int[length];
        for(int i = 0; i < length; i++){
            copyArray[i] = array[length - 1 - i];
        }
        System.arraycopy(copyArray, 0, array, 0, length);
    }
}
