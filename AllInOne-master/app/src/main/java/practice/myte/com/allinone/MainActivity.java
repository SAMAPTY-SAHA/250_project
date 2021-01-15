package practice.myte.com.allinone;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.Locale;

import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MainActivity extends AppCompatActivity {

    private TextToSpeech myTTS ;

    //Map
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;


    //Click sound
    MediaPlayer clicksound ;    //Music

    //Navigation start
    private DrawerLayout mDrawerLayout ;
    private ActionBarDrawerToggle mActionBarDrawerToggle ;
    ///Navigation end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Voice
        initializeTextToSpeech();

        //Map
        if(isServicesOK()){
            init();
        }

        //Navigation start
        mDrawerLayout = findViewById(R.id.main_activity);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ///Navigation end

        //Instruction start
        final FloatingTextButton floatingTextButton = findViewById(R.id.action_button) ;
        floatingTextButton.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("It's Instructions ... Double click to open");

                        }else if(i == 2) {
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("Opening instructions");
                            Intent intent = new Intent(MainActivity.this, Instruction.class);
                            startActivity(intent);
                        }
                        i = 0 ;
                    }
                },500);
            }
        });

        //Instruction END

        // Voice Call
        final Button buttonVoice = findViewById(R.id.action_button_five) ;
        buttonVoice.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("It's Voice Call ... Double click to open");

                        }else if(i == 2) {
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("Opening Voice call...Click and say any number to call");
                            Intent intent = new Intent(MainActivity.this, CallVoice.class);
                            startActivity(intent);
                        }
                        i = 0 ;
                    }
                },500);
            }
        });
        // Voice call end

        // Voice app opener
        final Button buttonAppOpener = findViewById(R.id.action_button_two) ;
        buttonAppOpener.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("It's voice apps opener ... Double click to open");

                        }else if(i == 2) {
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("Opening Voice apps opener");
                            Intent intent = new Intent(MainActivity.this, Voice.class);
                            startActivity(intent);
                        }
                        i = 0 ;
                    }
                },500);
            }
        });
        //Voice app opener end

        // To do list
        final Button buttonTodo = findViewById(R.id.action_button_three) ;
        buttonTodo.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("It's To Do list ... Double click to open");

                        }else if(i == 2) {
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("Opening to do list");
                            Intent intent = new Intent(MainActivity.this, ToDoList.class);
                            startActivity(intent);
                        }
                        i = 0 ;
                    }
                },500);
            }
        });
        // To do list End

    }

    //Map
    private void init(){
        Button btnMap = findViewById(R.id.action_button_four);
        btnMap.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("It's voice road ... Double click to open");

                        }else if(i == 2) {
                            //For sound
                            clicksound = MediaPlayer.create(MainActivity.this, R.raw.click) ;
                            clicksound.start();
                            speak("Opening voice road");
                            Intent intent = new Intent(MainActivity.this, MapActivity.class);
                            startActivity(intent);
                        }
                        i = 0 ;
                    }
                },500);
            }
        });
    }

    //Map
    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK : checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK : Google Play Services is working");
            return true ;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK : an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map requeste", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    //MAp end

    //Navigation start
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item)) return true ;
        return super.onOptionsItemSelected(item);
    }*/
    ///Navigation end

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0){
                    Toast.makeText(MainActivity.this, "There is no TTS engine on your device"
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
