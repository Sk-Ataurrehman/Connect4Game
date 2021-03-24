package com.madmp.connect4game.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import static android.content.ContentValues.TAG;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.madmp.connect4game.Board.BoardLogic;
import com.madmp.connect4game.BuildConfig;
import com.madmp.connect4game.Controller.GamePlayController;

import com.madmp.connect4game.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.madmp.connect4game.Controller.GamePlayController.COLS;
import static com.madmp.connect4game.Controller.GamePlayController.ROWS;

public class BoardView extends AppCompatActivity {

    private View mBoardView;
    static BoardView boardView;
    private GamePlayController mGameController;
    private GamePlayController mListener;
    private View mBoardFrontView;
    private ImageView[][] mCells;
    public static int mPlayer1=1;
    public static int mPlayer2=2;
    public static int firstTurnStatic;
    public static int discColorPlayer1;
    public static int discColorPlayer2;
    private TextView mWinnerView;
    public static String player1Name;
    public String player1DiscColor;
    public static String player2Name;
    public ImageView[][] getCells() {
        return mCells;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_board);
        boardView = this;
        mBoardView = findViewById(R.id.gameBoard);
        mBoardFrontView = findViewById(R.id.game_board_front);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        player1Name=extras.getString("Player1Name");
        player2Name=extras.getString("Player2Name");
        String firstTurn=extras.getString("FirstTurn");
        player1DiscColor=extras.getString("Player1DiscColor");
        if(firstTurn.equals("Player1Turn")) {
            firstTurnStatic=mPlayer1;
        }else {
            firstTurnStatic=mPlayer2;
        }
        if(player1DiscColor.equals("Red")) {
            discColorPlayer1=R.drawable.red_disc_image_round;
            discColorPlayer2=R.drawable.yellow_disc_image_round;
        }else {
            discColorPlayer1=R.drawable.yellow_disc_image_round;
            discColorPlayer2=R.drawable.red_disc_image_round;
        }
        if(firstTurnStatic==1) {
            ImageView imageView1=(ImageView) findViewById(R.id.player1_disc);
            imageView1.setImageResource(discColorPlayer1);
            ImageView imageView2=(ImageView) findViewById(R.id.player2_disc);
            imageView2.setImageResource(discColorPlayer2);
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(VISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(INVISIBLE);
            TextView textView1=(TextView) findViewById(R.id.player1_name);
            textView1.setText(player1Name);
            TextView textView2=(TextView) findViewById(R.id.player2_name);
            textView2.setText(player2Name);
        }else {
            ImageView imageView1=(ImageView) findViewById(R.id.player1_disc);
            imageView1.setImageResource(discColorPlayer1);
            ImageView imageView2=(ImageView) findViewById(R.id.player2_disc);
            imageView2.setImageResource(discColorPlayer2);
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(INVISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(VISIBLE);
            TextView textView1=(TextView) findViewById(R.id.player1_name);
            textView1.setText(player1Name);
            TextView textView2=(TextView) findViewById(R.id.player2_name);
            textView2.setText(player2Name);
        }
        ImageView closeBtn=(ImageView) findViewById(R.id.close_button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BoardView.this);
                builder.setMessage("Back to Menu?").setPositiveButton("Yes", dialogClickListener)
                        .setTitle("4 IN A ROW")
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        ImageView resetButton=(ImageView) findViewById(R.id.reset_button);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                    initialize();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BoardView.this);
                builder.setMessage("Reset game?").setPositiveButton("Yes", dialogClickListener)
                        .setTitle("4 IN A ROW")
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
        initialize();
    }
    public void initialize(){
        buildCells();
    }
    public void buildCells() {
        //Not tested when this was added
        if(firstTurnStatic==1) {
            ImageView imageView1=(ImageView) findViewById(R.id.player1_disc);
            imageView1.setImageResource(discColorPlayer1);
            ImageView imageView2=(ImageView) findViewById(R.id.player2_disc);
            imageView2.setImageResource(discColorPlayer2);
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(VISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(INVISIBLE);
            TextView textView1=(TextView) findViewById(R.id.player1_name);
            textView1.setText(player1Name);
            TextView textView2=(TextView) findViewById(R.id.player2_name);
            textView2.setText(player2Name);
        }else {
            ImageView imageView1=(ImageView) findViewById(R.id.player1_disc);
            imageView1.setImageResource(discColorPlayer1);
            ImageView imageView2=(ImageView) findViewById(R.id.player2_disc);
            imageView2.setImageResource(discColorPlayer2);
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(INVISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(VISIBLE);
            TextView textView1=(TextView) findViewById(R.id.player1_name);
            textView1.setText(player1Name);
            TextView textView2=(TextView) findViewById(R.id.player2_name);
            textView2.setText(player2Name);
        }
        //till here
        mWinnerView = (TextView) findViewById(R.id.winner_text);
        mWinnerView.setVisibility(INVISIBLE);
        mCells = new ImageView[ROWS][COLS];
        for(int r = 0; r < 6; r++){
            ViewGroup row = (ViewGroup) ((ViewGroup) mBoardView).getChildAt(r);
            ViewGroup row1 = (ViewGroup) ((ViewGroup) mBoardFrontView).getChildAt(r);
            row.setClipChildren(false);
            for (int c = 0; c < 7; c++){
                ImageView imageView = (ImageView) row.getChildAt(c);
                ImageView imageView1 = (ImageView) row1.getChildAt(c);
//                imageView.setY(1000);
                imageView1.setBackgroundResource(R.color.transparenttwo);
//                imageView.animate().translationY(0).setInterpolator(new BounceInterpolator()).start();
                imageView.setImageResource(R.color.white);
                imageView.setOnClickListener(new GamePlayController());
                mCells[r][c] = imageView;
                Log.d("comp",imageView.toString());
            }
        }
    }
    @NonNull
    public void dropDisc(int row, int col,int playerTurn) {
        final ImageView cell = mCells[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight() + 15);
        cell.setY(move);
        cell.setImageResource(playerTurn == mPlayer1 ? discColorPlayer1 : discColorPlayer2);
        cell.animate().translationY(0).setInterpolator(new BounceInterpolator()).start();
    }

    @NonNull
    public int colAtX(float x) {
        float colWidth = mCells[0][0].getWidth();
        System.out.println("Col width"+colWidth);
        int col = (int) x / (int) colWidth;
        System.out.println("Column"+col);
        if (col < 0)
            return 0;
        if (col > 6)
            return 6;
        return col;
    }
    public static BoardView getInstance(){
        return   boardView;
    }
    public void progressBarSwap(int playerTurn)
    {
        if(playerTurn==1)
        {
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(INVISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(VISIBLE);
        }else {
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(VISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(INVISIBLE);
        }
    }
    public void showWinStatus(@NonNull BoardLogic.Outcome outcome, @NonNull ArrayList<ImageView> winDiscs) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, outcome.name());
        }
        if (outcome != BoardLogic.Outcome.NOTHING) {
            System.out.println("Hello inside ouytcome");
            mWinnerView.setVisibility(VISIBLE);
            ProgressBar progressBar1=(ProgressBar) findViewById(R.id.player1_indicator);
            progressBar1.setVisibility(INVISIBLE);
            ProgressBar progressBar2=(ProgressBar) findViewById(R.id.player2_indicator);
            progressBar2.setVisibility(INVISIBLE);
            switch (outcome) {
                case DRAW:
                    mWinnerView.setText("DRAW");
                    for(int r = 0; r < 6; r++){
                        ViewGroup row = (ViewGroup) ((ViewGroup) mBoardView).getChildAt(r);
                        row.setClipChildren(false);
                        for (int c = 0; c < 7; c++){
                            ImageView imageView = (ImageView) row.getChildAt(c);
                            imageView.setOnClickListener(null);
                        }
                    }
                    break;
                case PLAYER1_WINS:
                    System.out.println("Hello inside player1");
                    mWinnerView.setText(player1Name+" WINS!");
                    for (ImageView winDisc : winDiscs) {
                        if(player1DiscColor.equals("Red"))
                        {
                            winDisc.setImageResource(R.drawable.win_red);
                        }else {
                            winDisc.setImageResource(R.drawable.win_yellow);
                        }

                    }

                    for(int r = 0; r < 6; r++){
                        ViewGroup row = (ViewGroup) ((ViewGroup) mBoardView).getChildAt(r);
                        row.setClipChildren(false);
                        for (int c = 0; c < 7; c++){
                            ImageView imageView = (ImageView) row.getChildAt(c);
                            imageView.setOnClickListener(null);
                        }
                    }
                    break;
                case PLAYER2_WINS:
                    mWinnerView.setText(player2Name+" WINS!");
                    for (ImageView winDisc : winDiscs) {
                        if(player1DiscColor.equals("Red"))
                        {
                            winDisc.setImageResource(R.drawable.win_yellow);
                        }else {
                            winDisc.setImageResource(R.drawable.win_red);
                        }

                    }

                    for(int r = 0; r < 6; r++){
                        ViewGroup row = (ViewGroup) ((ViewGroup) mBoardView).getChildAt(r);
                        row.setClipChildren(false);
                        for (int c = 0; c < 7; c++){
                            ImageView imageView = (ImageView) row.getChildAt(c);
                            imageView.setOnClickListener(null);
                        }
                    }
                    break;
                default:
                    break;
            }
        } else {
            mWinnerView.setVisibility(INVISIBLE);
        }
    }

}
