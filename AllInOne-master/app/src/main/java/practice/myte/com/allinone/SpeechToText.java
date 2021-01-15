package practice.myte.com.allinone;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends AppCompatActivity {

    Button b1 ;
    TextView t1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        b1 = findViewById(R.id.button) ;
        t1 = findViewById(R.id.textView) ;

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!= null){
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
            String s = arrayList.get(0) ;
            t1.setText(s);
            //t1.setText(arrayList.get(0).toString());
            Log.i("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nArray : ",arrayList.toString());
            Log.i("STRING : ", " "+s) ;
        }
    }
}
