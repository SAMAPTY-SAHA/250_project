package practice.myte.com.allinone;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Voice extends AppCompatActivity {

    private TextToSpeech myTTS ;
    private SpeechRecognizer mySpeechRecognizer ;
    TreeMap<String,String> apps = new TreeMap<String, String>();
    private String s = "" ;
    public List<PackageInfo> packList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        creatList() ;
        initializeTextToSpeech();
        initializeSpeechRecognizer();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
                mySpeechRecognizer.startListening(intent);
            }
        });*/

        Button button = findViewById(R.id.button) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

                Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                startActivityForResult(intent1, 1);
                mySpeechRecognizer.startListening(intent);
            }
        });

    }

    public void creatList(){
        packList = getPackageManager().getInstalledPackages(0);

        for (int i=0; i < packList.size(); i++)
        {
            PackageInfo packInfo = packList.get(i);
            String pname  = packInfo.packageName ;
            /*if (  (packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
            {*/
                String appName = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                //Log.e("App № " + Integer.toString(i), appName);
                // Toast.makeText(MainActivity.this, "App Name : "+appName, Toast.LENGTH_LONG).show();
                //Log.e("Pack Name : "+pname, appName) ;
                // Toast.makeText(MainActivity.this, "Package name : "+pname, Toast.LENGTH_LONG).show();

                apps.put(appName, pname) ;

            //}
        }
    }

    private void initializeSpeechRecognizer() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            mySpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            mySpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle bundle) {
                    List<String> results = bundle.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                    );
                    processResult(results.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String command) {
        command = command.toLowerCase();

        for(Map.Entry m:apps.entrySet()){
            //Toast.makeText(Voice.this, "App : "+m.getKey()+"\nInside : "+m.getValue()+"and"+command, Toast.LENGTH_LONG).show();
            if(command.equals(m.getKey().toString().toLowerCase())){
                //Toast.makeText(Voice.this, "Inside if condition",Toast.LENGTH_LONG).show();
                Intent intent1 = getPackageManager().getLaunchIntentForPackage((String)m.getValue());
                startActivity(intent1);
                //break;
            }
            ///else return ;
        }

        /*// What
        if(command.indexOf("what") != -1){
            if(command.indexOf("your name") != -1){
                speak("Hello ! My name is Eno.");
            }
            if(command.indexOf("time") != -1){
                Date now = new Date() ;
                String time = DateUtils.formatDateTime(this, now.getTime(),
                        DateUtils.FORMAT_SHOW_TIME);
                speak("The time now is "+time);
            }
        }

        else if(command.indexOf("who") != -1){
            if(command.indexOf("are you") != -1){
                speak("Hi, I'm your apps assistant , Eno.");
            }
        }

        // Open
        else if(command.indexOf("open") !=  -1){
            if(command.indexOf("browser") != -1){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/"));
                startActivity(intent);
            }
            if(command.indexOf("adobe reader") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.adobe.reader");
                startActivity(intent);
            }
            if(command.indexOf("album") != -1 ){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.sonyericsson.album");
                startActivity(intent);
            }
            if(command.indexOf("calculator") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.calculator2");
                startActivity(intent);
            }
            if(command.indexOf("scientific calculator") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.blessingapps.scientificcalc");
                startActivity(intent);
            }
            if(command.indexOf("camera") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.sonyericsson.android.camera");
                startActivity(intent);
            }
            if(command.indexOf("chrome") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.chrome");
                startActivity(intent);
            }
            if(command.indexOf("whatsapp") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(intent);
            }
            if(command.indexOf("contacts") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.sonyericsson.android.socialphonebook");
                startActivity(intent);
            }
            if(command.indexOf("facebook") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                startActivity(intent);
            }
            if(command.indexOf("radio") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.mediatek.fmradio");
                startActivity(intent);
            }
            if(command.indexOf("telegram") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                startActivity(intent);
            }
            if(command.indexOf("dragons") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.berad.areffectdragons");
                startActivity(intent);
            }
            if(command.indexOf("camscanner") != -1){
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.intsig.camscanner");
                startActivity(intent);
            }

        }
        else if(command.indexOf("who") != -1){
            if(command.indexOf("boka") != -1)speak("wasif is boka choda");
        }*/
    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0){
                    Toast.makeText(Voice.this, "There is no TTS engine on your device"
                            , Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    myTTS.setLanguage(Locale.US);
                    speak("Hello! I am ready.");
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

    @Override
    protected void onPause() {
        super.onPause();
        myTTS.shutdown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!= null){
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
            s = arrayList.get(0) ;
            //Toast.makeText(Voice.this, ""+s+"\n", Toast.LENGTH_LONG).show();
            processResult(s);

           // Log.i("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nArray : ",arrayList.toString());
            //Log.i("STRING : ", " "+s) ;
        }
    }

}
