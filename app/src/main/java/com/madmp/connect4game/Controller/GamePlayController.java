package com.madmp.connect4game.Controller;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.madmp.connect4game.Activity.BoardView;
import com.madmp.connect4game.Board.BoardLogic;
import com.madmp.connect4game.Board.BoardLogic.Outcome;
import com.madmp.connect4game.BuildConfig;
import com.madmp.connect4game.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.madmp.connect4game.Activity.BoardView.firstTurnStatic;
import static com.madmp.connect4game.Activity.BoardView.mPlayer1;
import static com.madmp.connect4game.Activity.BoardView.mPlayer2;

public class GamePlayController implements View.OnClickListener {
    public static final int COLS = 7;
    public static final int ROWS = 6;
    public static int[][] mGrid = new int[ROWS][COLS];
    private static int[] mFree = new int[COLS];
    private final BoardLogic mBoardLogic = new BoardLogic(mGrid, mFree);
    public static int mPlayerTurn;
    private Outcome mOutcome = Outcome.NOTHING;
    private boolean mFinished = true;
    public GamePlayController(){
        initialize();
    }

    private void initialize() {

        mPlayerTurn = BoardView.firstTurnStatic;
        mFinished = false;
        for (int j = 0; j < COLS; ++j) {
            for (int i = 0; i < ROWS; ++i) {
                mGrid[i][j] = 0;
            }
            mFree[j] = ROWS;
        }
    }

    @Override
    public void onClick(View v) {
        Log.d("aa",v.toString());
        System.out.println("Vgetx"+v.getX());
        int col = BoardView.getInstance().colAtX(v.getX());
        System.out.println(col);
        selectColumn(col);
    }
    private void selectColumn(int column) {
        System.out.println("MFree column"+mFree[column]);
        if (mFree[column] == 0) {
            System.out.println(mFree[column]);
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "full column or game is mFinished");
            }
            return;
        }

        mBoardLogic.placeMove(column,mPlayerTurn);
        System.out.println(mFree[column]);
        // put disc
        BoardView.getInstance().dropDisc(mFree[column], column, mPlayerTurn);
        BoardView.getInstance().progressBarSwap(mPlayerTurn);

        togglePlayer(mPlayerTurn);
        mBoardLogic.displayBoard();
        checkForWin();
    }
    public void togglePlayer(int playerTurn) {
        if(playerTurn==1)
        {
            mPlayerTurn=2;

        }else {
            mPlayerTurn=1;
        }
    }
    private void checkForWin() {
        mOutcome = mBoardLogic.checkWin(mGrid);
        System.out.println("Hello");
        if (mOutcome != Outcome.NOTHING) {
            mFinished = true;
            ArrayList<ImageView> winDiscs = mBoardLogic.getWinDiscs(BoardView.getInstance().getCells());
            BoardView.getInstance().showWinStatus(mOutcome, winDiscs);

        } else {
//            togglePlayer(mPlayerTurn);
        }
    }
}
