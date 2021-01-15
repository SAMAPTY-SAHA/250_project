package practice.myte.com.allinone;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    MediaPlayer splashSong ;    //Music
    private TextToSpeech myTTS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initializeTextToSpeech();

        //For hiding android actionbar
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();

        //For music
        splashSong = MediaPlayer.create(SplashScreen.this, R.raw.music1) ;
        splashSong.start();

        Thread t = new Thread() {

            @Override
            public void run() {
                super.run();
                try {
                    /*sleep(12500);*/
                    sleep(5000);
                    splashSong.release();
                    finish();
                    speak("Hi, I am your assistant, Kito.. You are in home page");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0){
                    Toast.makeText(SplashScreen.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.US);
                    //speak("Your present address is "+myPlace);
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null, null) ;
        }else{
            myTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
