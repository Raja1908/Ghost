/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
TextView ghostText;
TextView gameStatus;
Button challenge;
Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        onStart(null);
        try {
            dictionary=new FastDictionary(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ghostText=(TextView)findViewById(R.id.ghostText);
        gameStatus=(TextView)findViewById(R.id.gameStatus);
        challenge=(Button)findViewById(R.id.challenge);
        reset=(Button)findViewById(R.id.reset);
    challenge.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String b=ghostText.getText().toString();
            if(b.length()>=4 && dictionary.isWord(b)) {
                gameStatus.setText("User Wins");
            return;
            }
            String d=dictionary.getAnyWordStartingWith(b);
            if(d==null)
                gameStatus.setText("User wins");
            else
                gameStatus.setText("Computer wins");
        }
    });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        try {
            String word = ghostText.getText().toString();
            if (word.length() > 4 && dictionary.isWord(word)) {

                gameStatus.setText("Computer Won");
            }
            String newWord = dictionary.getAnyWordStartingWith(word);
            if (newWord == null) {
                gameStatus.setText("Computer Won");
                return;

            } else {
                char c = newWord.charAt(word.length());
                String s = ghostText.getText().toString();
                s = s + c;
                ghostText.setText(s);
            }
        }
        catch (Exception e)
        {}
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(KeyEvent.KEYCODE_A<=keyCode && keyCode<=KeyEvent.KEYCODE_Z)
        {
            char x= (char) event.getUnicodeChar();
        String s=ghostText.getText().toString();
            s=s+String.valueOf(x);
            ghostText.setText(s);
            computerTurn();
        }

        return super.onKeyUp(keyCode, event);
    }

}
