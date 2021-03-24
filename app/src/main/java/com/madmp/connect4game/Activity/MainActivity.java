package com.madmp.connect4game.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.madmp.connect4game.R;

public class MainActivity extends AppCompatActivity {
    String player1Name="";
    String player2Name="";
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        final EditText editText=(EditText) findViewById(R.id.playerName);
        final RadioButton firstPlayerRadioButton=(RadioButton) findViewById(R.id.first_turn_player1);
        RadioButton secondPlayerRadioButton=(RadioButton) findViewById(R.id.first_turn_player2);

        final RadioButton discColor1=(RadioButton) findViewById(R.id.disc_red);
        final RadioButton discColor2=(RadioButton) findViewById(R.id.disc_yellow);
        firstPlayerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                player2Name=editText.getText().toString();
                if(editText.getText().toString().equals(player2Name)) {
                    editText.setText(player1Name);
                }
            }
        });
        secondPlayerRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                player1Name=editText.getText().toString();
                if(editText.getText().toString().equals(player1Name)) {
                    editText.setText(player2Name);
                }
            }
        });

        Button playButton = (Button) findViewById(R.id.play);
        mediaPlayer=MediaPlayer.create(this, R.raw.buttonclicksound);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                if(player1Name.equals("") || player2Name.equals("") ) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Enter Players Name!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else {
                    Intent intent=new Intent(MainActivity.this, BoardView.class);
                    intent.putExtra("Player1Name",player1Name);
                    intent.putExtra("Player2Name",player2Name);
                    if(firstPlayerRadioButton.isChecked())
                        intent.putExtra("FirstTurn","Player1Turn");
                    else
                        intent.putExtra("FirstTurn", "Player2Turn");
                    if(discColor1.isChecked())
                        intent.putExtra("Player1DiscColor","Red");
                    else
                        intent.putExtra("Player1DiscColor","Yellow");
                    startActivity(intent);
                }
            }
        });
    }
}
