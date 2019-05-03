package com.salihyusufcankurt.tts;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech mTTS;
    private EditText editText;
    private SeekBar seekBar_speed, seekBar_pitch;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        installVariables();
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    //int result = mTTS.setLanguage(Locale.ENGLISH);
                    int result = mTTS.setLanguage(new Locale("tr"));
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        button.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }

    public void installVariables (){
        editText = findViewById(R.id.editText);
        seekBar_pitch = findViewById(R.id.seekBar_pitch);
        seekBar_speed = findViewById(R.id.seekBar_speed);
        button = findViewById(R.id.button);
    }



    public void speak(){
        String text = editText.getText().toString();
        float pitch = (float) seekBar_pitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) seekBar_speed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

     //   mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        mTTS.speak(editText.getText().toString(),TextToSpeech.QUEUE_FLUSH,null,null);

    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }
}
