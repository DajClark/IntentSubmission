package com.example.intentsubmission;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    // Private variables for game index array and request codes.
    private String[] mGames;
    private int mGameIndex = 0;
    private static final int IS_SUCCESSFUL = 0;

    // Tag for log messages.
    public static final String TAG = "GameActivity";

    // Key value name for value passed back in intent upon result.
    private static final String IS_GAME_COMPLETE = "com.example.isGameComplete";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Call to super method to use super class onCreate methods.
        super.onCreate(savedInstanceState);

        // Log message to show lifecycle state.
        Log.d(TAG, " *** the PC is in onCreate ***");

        // Sets activity view using XML layout file.
        setContentView(R.layout.activity_game);

        // Initialises and sets the main text view to the appropriate array value.
        final TextView gameTextView = findViewById(R.id.textViewGame);
        Resources res = getResources();
        mGames = res.getStringArray(R.array.game);
        gameTextView.setText(mGames[mGameIndex]);

        // Initialises previous button and listener to decrement array index.
        Button buttonPrev = findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameIndex = ((mGameIndex - 1) + mGames.length) % mGames.length;
                gameTextView.setText(mGames[mGameIndex]);
                gameTextView.setBackgroundColor(ContextCompat.getColor(GameActivity.this,R.color.backgroundDefault));
                setTextViewComplete("");
            }
        });

        // Initialises detail button and calls intent from GameDetailActivity and starts the activity for a result.
        Button buttonDetail = findViewById(R.id.buttonDetail);
        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = GameDetailActivity.newIntent(GameActivity.this, mGameIndex);
                startActivityForResult(intent, IS_SUCCESSFUL);
            }
        });

        // Initialises next button and listener to increment array index.
        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mGameIndex = (mGameIndex + 1) % mGames.length;
                gameTextView.setText(mGames[mGameIndex]);
                gameTextView.setBackgroundColor(ContextCompat.getColor(GameActivity.this,R.color.backgroundDefault));
                setTextViewComplete("");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Checks if request code is successful and calls the update view method or shows toast message.
        if(requestCode == IS_SUCCESSFUL) {
            if(intent != null) {

                // Gathers the result from the intent and updates view accordingly.
                boolean isGameComplete = intent.getBooleanExtra(IS_GAME_COMPLETE, false);
                updateGameComplete(isGameComplete);
            } else {
                Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.request_code_mismatch, Toast.LENGTH_SHORT).show();
        }
    }

    // Method to update the activity view data if the value is passed that the game has been completed.
    private void updateGameComplete(boolean isGameComplete) {

        // Gets the main TextView by its id and sets the text and background colour to show the game has been set to completed.
        final TextView textViewGame = findViewById(R.id.textViewGame);
        if(isGameComplete) {
            textViewGame.setBackgroundColor(ContextCompat.getColor(this,R.color.backgroundSuccess));
            textViewGame.setTextColor(ContextCompat.getColor(this,R.color.colorSuccess));
            setTextViewComplete("Complete");
        } else {
            textViewGame.setBackgroundColor(ContextCompat.getColor(this,R.color.backgroundDefault));
            setTextViewComplete("");
        }
    }

    // Method to set the appropriate game completion status.
    private void setTextViewComplete(String message) {

        // Gets and sets the completion TextView text using the TextView ID.
        final TextView textViewComplete = findViewById(R.id.textViewComplete);
        textViewComplete.setText(message);
    }
}
