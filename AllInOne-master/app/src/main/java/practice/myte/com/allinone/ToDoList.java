package practice.myte.com.allinone;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ToDoList extends AppCompatActivity {

    //Click sound
    MediaPlayer clicksound ;    //Music

    private TextToSpeech myTTS ;
    NavigableMap<Integer,String> map = new TreeMap<Integer,String>();

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;
    String s = "";
    boolean flag ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        dbHelper = new DbHelper(this);

        lstTask = (ListView)findViewById(R.id.lstTask);

        initializeTextToSpeech();
        loadTaskList();

        Button fbutton = findViewById(R.id.fullButton) ;

        fbutton.setOnClickListener(new View.OnClickListener() {
            int i = 0 ;
            @Override
            public void onClick(View v) {
                //For sound
                clicksound = MediaPlayer.create(ToDoList.this, R.raw.click) ;
                clicksound.start();
                i++ ;
                Handler handler = new Handler() ;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(i == 1){
                            speak("Speak to add");
                            flag = true ;
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                            startActivityForResult(intent, 1);

                        }else if(i == 2) {
                            speak("Your task to do - ");
                            ArrayList<String> taskList = dbHelper.getTaskList();
                            for(int i=0;i<taskList.size();i++){
                                speak(taskList.get(i));
                                //Toast.makeText(MainActivity.this, ""+taskList.get(i)+"\n", Toast.LENGTH_LONG).show();
                            }
                        }
                        else if(i == 3){
                            speak("speak to delete");
                            flag = false ;
                            Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                            intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                            intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                            startActivityForResult(intent1, 1);
                        }
                        i = 0 ;
                    }
                },700);
            }
        });
    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if(mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.task_title,taskList);
            lstTask.setAdapter(mAdapter);
        }
        else{
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        //Change menu icon color
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_task:
                speak("Speak to add");
                flag = true ;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                startActivityForResult(intent, 1);
                /*final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add New Task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .create();
                dialog.show();*/
                return true;
            case R.id.action_read_task:
                speak("Your task to do - ");
                ArrayList<String> taskList = dbHelper.getTaskList();
                for(int i=0;i<taskList.size();i++){
                    speak(taskList.get(i));
                    //Toast.makeText(MainActivity.this, ""+taskList.get(i)+"\n", Toast.LENGTH_LONG).show();
                }
                return true ;
            case R.id.action_delete_task:
                speak("speak to delete");
                flag = false ;
                Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
                intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM) ;
                intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) ;
                intent1.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak ...!") ;
                startActivityForResult(intent1, 1);
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteTask(View view){
        View parent = (View)view.getParent();
        TextView taskTextView = (TextView)parent.findViewById(R.id.task_title);
        Log.e("String", (String) taskTextView.getText());
        String task = String.valueOf(taskTextView.getText());
        dbHelper.deleteTask(task);
        loadTaskList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!= null){
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
            s = arrayList.get(0) ;
            if(flag == true){
                dbHelper.insertNewTask(s);
                loadTaskList();
            }
            else{
                ArrayList<String> taskList = dbHelper.getTaskList();
                String str = "" ;
                for(int i=0;i<taskList.size();i++){
                    str = taskList.get(i) ;
                    LCS(s, str);
                    //speak(taskList.get(i));
                    //Toast.makeText(MainActivity.this, ""+taskList.get(i)+"\n", Toast.LENGTH_LONG).show();
                }
                String ansString = map.lastEntry().getValue();

                dbHelper.deleteTask(ansString);
                loadTaskList();
            }
            //t1.setText(s);
            //t1.setText(arrayList.get(0).toString());
            Log.i("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nArray : ",arrayList.toString());
            Log.i("STRING : ", " "+s) ;
        }
    }

    private void initializeTextToSpeech() {
        myTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(myTTS.getEngines().size() == 0){
                    Toast.makeText(ToDoList.this, "There is no TTS engine on your device"
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
            myTTS.speak(message, TextToSpeech.QUEUE_ADD, null, null) ;
        }else{
            myTTS.speak(message, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void LCS(String s1, String s2){
        char[] X = s1.toCharArray() ;
        char[] Y = s2.toCharArray() ;

        int m = X.length ;
        int n = Y.length ;

        int L[][] = new int[m+1][n+1];

        for (int i=0; i<=m; i++)
        {
            for (int j=0; j<=n; j++)
            {
                if (i == 0 || j == 0)
                    L[i][j] = 0;
                else if (X[i-1] == Y[j-1])
                    L[i][j] = L[i-1][j-1] + 1;
                else
                    L[i][j] = max(L[i-1][j], L[i][j-1]);
            }
        }
        map.put(L[m][n], s2) ;
    }

    int max(int a, int b)
    {
        return (a > b)? a : b;
    }
}
