package com.example.vinayak.voicecommand;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {

    TextView response;
    EditText editTextAddress, editTextPort,query;
    Button buttonConnect, buttonClear;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    //int flag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        //Disconnect = (Button) findViewById(R.id.Disconnect);
        response = (TextView) findViewById(R.id.responseTextView);
        query = (EditText) findViewById(R.id.query);

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar
        getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        /*sharedPreferences = getSharedPreferences("command", MODE_PRIVATE);
        editor = sharedPreferences.edit();*/

        /*Disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                flag = 0;
            }
        });*/

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                /*editor.putString("string", query.getText().toString());
                editor.apply();
                query.setText("");*/

                Client myClient = new Client(editTextAddress.getText()
                        .toString(), Integer.parseInt(editTextPort
                        .getText().toString()), response, query.getText().toString());
                myClient.execute();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, 5);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    query.setText(result.get(0));
                }
                break;
            }

        }
    }
}
