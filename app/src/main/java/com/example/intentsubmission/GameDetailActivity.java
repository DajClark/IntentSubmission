package com.example.intentsubmission;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class GameDetailActivity extends AppCompatActivity {

    // Key value names for passing values using intents.
    private static final String GAME_INDEX = "com.example.gameIndex";
    private static final String IS_GAME_COMPLETE = "com.example.isGameComplete";

    // Intent for passing the game index position to the GameDetailActivity class.
    public static Intent newIntent(Context packageContext, int gameIndex){
        Intent intent = new Intent(packageContext, GameDetailActivity.class);
        intent.putExtra(GAME_INDEX,gameIndex);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Calls super methods and sets the activity view using the XML layout file.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        // Gets and sets the game index position from the intent.
        int gameIndex = getIntent().getIntExtra(GAME_INDEX, 0);
        updateTextViewGameDetail(gameIndex);

        // Initialises and sets checkbox and listeners.
        CheckBox checkboxIsComplete = findViewById(R.id.checkBoxIsComplete);
        checkboxIsComplete.setOnClickListener(mGameListener);
    }

    private void updateTextViewGameDetail(int gameIndex) {

        // Initialises the TextViews using the corresponding resource ID.
        final TextView textViewGamePlatform = findViewById(R.id.textViewGamePlatform);
        final TextView textViewGameDescription = findViewById(R.id.textViewGameDescription);

        // Gets the non-code assets from resource interface to gather data from the XML Layout file.
        Resources res = getResources();

        // Initialises and sets the TextView values from the arrays in the XML layout file using their ID's.
        String[] gamePlatform = res.getStringArray(R.array.game_platform);
        String[] gameDetails = res.getStringArray(R.array.game_description);
        textViewGamePlatform.setText(gamePlatform[gameIndex]);
        textViewGameDescription.setText(gameDetails[gameIndex]);
    }

    // Listener for checkbox to check for checkbox status.
    private View.OnClickListener mGameListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.checkBoxIsComplete:
                    CheckBox checkboxIsComplete = findViewById(R.id.checkBoxIsComplete);
                    setIsComplete(checkboxIsComplete.isChecked());
                    break;
                default:
                    break;
            }
        }
    };

    // Method to initialise intent with current checked status to return to onActivityResult in GameActivity class.
    private void setIsComplete(boolean isChecked) {

        if(isChecked){
            Toast.makeText(GameDetailActivity.this,
                    "The game is completed", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent();
        intent.putExtra(IS_GAME_COMPLETE, isChecked);
        setResult(RESULT_OK, intent);
    }

}
