package qira.com.tangsimulator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private EditText registerID;
    private Button identify;
    private EditText returnIdentify;
    private Button remove;
    private EditText returnRemove;
    private Button getMinutiae;
    private EditText returnGetMinutiae;
    private Button list;
    private EditText returnLst;
    private Button cleanFields;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        ledReceiver();
        cadReceiver();
        remReceiver();
        getMinutiaeReceiver();
        listReceiver();

    }

    public void listReceiver() {
        BroadcastReceiver list = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("list.action.return")) {
                    returnLst.setText(intent.getExtras().getString("LIST"));
                }
            }
        };
        registerReceiver(list, new IntentFilter("list.action.return"));
    }

    public void remReceiver() {
        BroadcastReceiver rem = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("rem.action.return")) {
                    returnRemove.setText(intent.getExtras().getString("REM"));
                }
            }
        };
        registerReceiver(rem, new IntentFilter("rem.action.return"));
    }

    public void ledReceiver() {
        BroadcastReceiver led = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("led.action.return")) {
                    returnIdentify.setText(intent.getExtras().getString("LED"));
                }
            }
        };
        registerReceiver(led, new IntentFilter("led.action.return"));
    }


    public void cadReceiver() {
        BroadcastReceiver cad = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("cad.action.return")) {
                    registerID.setText(intent.getExtras().getString("CAD"));
                }
            }
        };
        registerReceiver(cad, new IntentFilter("cad.action.return"));
    }

    public void getMinutiaeReceiver() {
        BroadcastReceiver getMinutiae = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("getMinutiae.action.return")) {
                    returnGetMinutiae.setText(intent.getExtras().getString("GETMINUTIAE").substring(0, 40));
                }
            }
        };
        registerReceiver(getMinutiae, new IntentFilter("getMinutiae.action.return"));
    }


    public void setViews() {
        register = (Button) findViewById(R.id.register);
        registerID = (EditText) findViewById(R.id.register_id);
        identify = (Button) findViewById(R.id.identify);
        returnIdentify = (EditText) findViewById(R.id.return_identidy);
        remove = (Button) findViewById(R.id.remove);
        returnRemove = (EditText) findViewById(R.id.return_remove);
        getMinutiae = (Button) findViewById(R.id.get_minutiae);
        returnGetMinutiae = (EditText) findViewById(R.id.return_get_minutiae);
        list = (Button) findViewById(R.id.list);
        returnLst = (EditText) findViewById(R.id.return_list);
        cleanFields = (Button) findViewById(R.id.clean_fields);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = registerID.getText().toString();
                if (id.length() == 8) {
                    sendIntentWithExtras("cad.action", "CAD", "CAD" + id);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"ID inválido",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntentWithExtras("led.action", "LED", "LED");
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String removeId = returnRemove.getText().toString();
                if (removeId.length() == 8) {
                    sendIntentWithExtras("rem.action", "REM", "REM" + removeId);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"ID inválido",Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });
        getMinutiae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minutiae = returnGetMinutiae.getText().toString();
                if (minutiae.length() == 8) {
                    sendIntentWithExtras("getMinutiae.action", "GETMINUTIAE", "GETMINUTIAE" + minutiae);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Minutiae inválida",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendIntentWithExtras("list.action", "LIST", "LIST");
            }
        });

        cleanFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerID.setText("");
                returnIdentify.setText("");
                returnRemove.setText("");
                returnGetMinutiae.setText("");
                returnLst.setText("");

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //sendIntentWithExtras("cad.action","CAD","CAD11116666");
        //sendIntentWithExtras("led.action","LED","LED");
        //sendIntentWithExtras("rem.action","REM","REM87458754");
        //sendIntentWithExtras("list.action","LIST","LIST");
        //sendIntentWithExtras("getMinutiae.action","GETMINUTIAE", "GETMINUTIAE87458754");

    }


    public void sendIntentWithExtras(String intentName, String extraName, String extra) {
        Intent intent = new Intent(intentName);
        intent.putExtra(extraName, extra);
        sendBroadcast(intent);
    }


}
