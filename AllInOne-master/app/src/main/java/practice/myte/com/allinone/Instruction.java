package practice.myte.com.allinone;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.Locale;

public class Instruction extends AppCompatActivity {

    TextToSpeech myTTS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        initializeTextToSpeech();

    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0){
                    Toast.makeText(Instruction.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.US);
                    workDirection();
                    //speak("Your present address is "+myPlace);
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            myTTS.speak(message, TextToSpeech.QUEUE_ADD, null, null) ;
        }else{
            myTTS.speak(message, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void workDirection(){
        speak("hey, I am kito ,your assistant ." +
                "You can ask me to call any number and open any apps on your phone ." +
                "also i can note down your work ." +
                "you can use google map to get your location .");
    }

}
