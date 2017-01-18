package qira.com.tangsimulator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    public void onResume() {
        super.onResume();
        //sendIntentWithExtras("cad.action","CAD","CAD11116666");
        //sendIntentWithExtras("led.action","LED","LED");
        //sendIntentWithExtras("rem.action","REM","REM87458754");
        //sendIntentWithExtras("list.action","LIST","LIST");
        sendIntentWithExtras("getMinutiae.action","GETMINUTIAE", "GETMINUTIAE87458754");

    }


    public void sendIntentWithExtras(String intentName, String extraName, String extra){
        Intent intent = new Intent(intentName);
        intent.putExtra(extraName,extra);
        sendBroadcast(intent);
    }

}
