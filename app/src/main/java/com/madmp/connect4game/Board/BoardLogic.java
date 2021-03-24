package com.madmp.connect4game.Board;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.madmp.connect4game.BuildConfig;
import com.madmp.connect4game.Controller.GamePlayController;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.madmp.connect4game.Activity.BoardView.mPlayer1;

public class BoardLogic {

    private int mCellValue;
    @NonNull
    private final int[][] mGrid;
    public final int numCols;
    private final int numRows;
    private int p, q;
    private final int[] mFree;
    public enum Outcome {
        NOTHING, DRAW, PLAYER1_WINS, PLAYER2_WINS
    }
    private boolean mDraw;

    private int WIN_X = 0;
    private int WIN_Y = 0;

    public BoardLogic(@NonNull int[][] grid, int[] free) {
        mGrid = grid;
        numRows = grid.length;
        numCols = grid[0].length;
        this.mFree = free;
    }
    public void placeMove(int column, int player) {
        if (mFree[column] > 0) {
            mGrid[mFree[column] - 1][column] = player;
            mFree[column]--;
        }
    }
    public void displayBoard() {
        System.out.println();
        for (int i = 0; i <= 5; ++i) {
            for (int j = 0; j <= 6; ++j) {
                System.out.print(mGrid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public int columnHeight(int index) {
        return mFree[index];
    }

    @NonNull
    public Outcome checkWin(int[][] board) {
        mDraw = true;
        mCellValue = 0;
        if (horizontalCheck(board) || verticalCheck(board) ||
                ascendingDiagonalCheck(board) || descendingDiagonalCheck(board)) {
            return mCellValue ==  mPlayer1 ? Outcome.PLAYER1_WINS : Outcome.PLAYER2_WINS;
        }
        // nobody won, return mDraw if it is, nothing if it's not
        return mDraw ? Outcome.DRAW : Outcome.NOTHING;
    }
    private boolean horizontalCheck(int[][] board) {
        // horizontalCheck
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols - 3; j++) {
                mCellValue = board[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && board[i][j + 1] == mCellValue && board[i][j + 2] == mCellValue && board[i][j + 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Horizontal check pass");
                    }
                    p = i;
                    q = j;
                    WIN_X = 1;
                    WIN_Y = 0;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verticalCheck(int[][] board) {
        // verticalCheck
        for (int j = 0; j < numCols; j++) {
            for (int i = 0; i < numRows - 3; i++) {
                mCellValue = board[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && board[i + 1][j] == mCellValue && board[i + 2][j] == mCellValue && board[i + 3][j] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "Vertical check pass");
                    }
                    p = i;
                    q = j;
                    WIN_X = 0;
                    WIN_Y = 1;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ascendingDiagonalCheck(int[][] board) {
        // ascendingDiagonalCheck
        for (int i = 3; i < numRows; i++) {
            for (int j = 0; j < numCols - 3; j++) {
                mCellValue = board[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && board[i - 1][j + 1] == mCellValue && board[i - 2][j + 2] == mCellValue && board[i - 3][j + 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "ascendingDiagonal check pass");
                    }
                    p = i;
                    q = j;
                    WIN_X = 1;
                    WIN_Y = -1;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean descendingDiagonalCheck(int[][] board) {
        // descendingDiagonalCheck
        for (int i = 3; i < numRows; i++) {
            for (int j = 3; j < numCols; j++) {
                mCellValue = board[i][j];
                if (mCellValue == 0) mDraw = false;
                if (mCellValue != 0 && board[i - 1][j - 1] == mCellValue && board[i - 2][j - 2] == mCellValue && board[i - 3][j - 3] == mCellValue) {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "descendingDiagonal check pass");
                    }
                    p = i;
                    q = j;
                    WIN_X = -1;
                    WIN_Y = -1;
                    return true;
                }
            }
        }
        return false;
    }
    @NonNull
    public ArrayList<ImageView> getWinDiscs(ImageView[][] cells) {
        ArrayList<ImageView> combination = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            combination.add(cells[p + WIN_Y * i][q + WIN_X * i]);
        }
        return combination;
    }
}
