package qira.com.tangsimulator;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class MainActivity extends AppCompatActivity {


    private static final int DELAY_AUTOSENSE = 500;

    Handler h = new Handler();

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
    private Button setMinutiae;
    private EditText returnSetMinutiae;


    private String id;


    private Button setMinutiaes;
    private Button reset;
    private Button cleanFields;
    private Button initializeSensor;


    BroadcastReceiver listReceiver;
    BroadcastReceiver rem;
    BroadcastReceiver led;
    BroadcastReceiver cad;
    BroadcastReceiver getMinutiaeReceiver;
    BroadcastReceiver add;
    BroadcastReceiver resetReceiver;
    BroadcastReceiver initReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attApp("qt24");

        setViews();


    }

    @Override
    public void onResume() {


        super.onResume();
        //sendIntentWithExtras("cad.action","CAD","CAD11116666");
        //sendIntentWithExtras("led.action","LED","LED");
        //sendIntentWithExtras("rem.action","REM","REM87458754");
        //sendIntentWithExtras("list.action","LIST","LIST");
        //sendIntentWithExtras("getMinutiae.action","GETMINUTIAE", "GETMINUTIAE87458754");
        ledReceiver();
        cadReceiver();
        remReceiver();
        getMinutiaeReceiver();
        listReceiver();
        addReceiver();
        resetReceiver();
        initReceiver();

        //sendIntentWithExtras("attApp.action", "NAMEAPK", "QiraBiometria-3.0.7");
        //sendIntentWithExtras("shutdown.action","SHUTDOWN","SHUTDOWN");


    }


    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(listReceiver);
        unregisterReceiver(rem);
        unregisterReceiver(led);
        unregisterReceiver(cad);
        unregisterReceiver(getMinutiaeReceiver);
        unregisterReceiver(add);
        unregisterReceiver(resetReceiver);
        unregisterReceiver(initReceiver);

    }


    public void listReceiver() {
        listReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("list.action.return")) {
                    returnLst.setText(intent.getExtras().getString("LIST"));
                }
            }
        };
        registerReceiver(listReceiver, new IntentFilter("list.action.return"));
    }

    public void initReceiver() {
        initReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("init.action.return")) {

                    Toast toast;
                    toast = Toast.makeText(getBaseContext(), intent.getExtras().getString("INIT"), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        registerReceiver(initReceiver, new IntentFilter("init.action.return"));
    }

    public void remReceiver() {
        rem = new BroadcastReceiver() {

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
        led = new BroadcastReceiver() {

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
        cad = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("cad.action.return")) {
                    registerID.setText(intent.getExtras().getString("CAD"));
                    if (intent.getExtras().getString("CAD").compareTo("0000") == 0)
                        sendIntentWithExtras("getMinutiae.action", "GETMINUTIAE", "GETMINUTIAE" + id);

                }
            }
        };
        registerReceiver(cad, new IntentFilter("cad.action.return"));
    }

    public void getMinutiaeReceiver() {
        getMinutiaeReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("getMinutiae.action.return")) {
                    returnGetMinutiae.setText(intent.getExtras().getString("GETMINUTIAE"));

                    if (intent.getExtras().getString("GETMINUTIAE").length() > 40) {

                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        int contMin = sharedPref.getInt("cont", 0) + 1;



                        editor.putString("min" + String.valueOf(contMin), intent.getExtras().getString("GETMINUTIAE"));
                        editor.apply();

                        Log.d("Minucia:",sharedPref.getString("min" +  String.valueOf(contMin), "Error") );

                        Log.d("Cont:", intent.getExtras().getString("GETMINUTIAE"));

                        editor.putInt("cont", contMin);
                        editor.apply();

                        Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(contMin), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        };
        registerReceiver(getMinutiaeReceiver, new IntentFilter("getMinutiae.action.return"));
    }

    public void addReceiver() {
        add = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("add.action.return")) {
                    returnSetMinutiae.setText(intent.getExtras().getString("ADD"));
                }
            }
        };
        registerReceiver(add, new IntentFilter("add.action.return"));
    }

    public void resetReceiver() {
        resetReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("reset.action.return")) {
                    Toast toast = Toast.makeText(getApplicationContext(), intent.getExtras().getString("RESET"), Toast.LENGTH_LONG);
                    toast.show();

                }
            }
        };
        registerReceiver(resetReceiver, new IntentFilter("reset.action.return"));
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

        setMinutiae = (Button) findViewById(R.id.set_minutiae);
        returnSetMinutiae = (EditText) findViewById(R.id.return_set_minutiae);

        cleanFields = (Button) findViewById(R.id.clean_fields);
        reset = (Button) findViewById(R.id.reset_sensor);
        initializeSensor = (Button) findViewById(R.id.initialize_sensor);
        setMinutiaes = (Button) findViewById(R.id.set_all_minutiaes);


        setMinutiaes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                final int cont = sharedPref.getInt("cont", 0);

                h.postDelayed(new Runnable() {

                    public void run() {
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        if (sharedPref.getInt("cont", 0) == 0) {
                            sharedPref.edit().putInt("cont", cont).apply();
                            Toast toast = Toast.makeText(getApplicationContext(), "Finish", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {


                            sendIntentWithExtras("add.action", "ADD", "ADD" + sharedPref.getString("min" + String.valueOf(sharedPref.getInt("cont", 0)), "ERROR"));
                            sharedPref.edit().putInt("cont", sharedPref.getInt("cont", 0) - 1).apply();

                            h.postDelayed(this, DELAY_AUTOSENSE);
                        }

                    }
                }, DELAY_AUTOSENSE);
            }

        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = registerID.getText().toString();
                if (id.length() == 8) {
                    sendIntentWithExtras("cad.action", "CAD", "CAD" + id);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "ID inválido", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        initializeSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntentWithExtras("init.action", "INIT", "INIT");
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
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "ID inválido", Toast.LENGTH_SHORT);
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
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Minúcia inválida", Toast.LENGTH_SHORT);
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


        setMinutiae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setMinutiae = returnSetMinutiae.getText().toString();
                if (setMinutiae.length() == 1712) {
                    sendIntentWithExtras("add.action", "ADD", "ADD" + setMinutiae);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Minúcia inválida", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                returnSetMinutiae.setText("");

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendIntentWithExtras("reset.action", "RESET", "RESET");
            }
        });
    }


    public void sendIntentWithExtras(String intentName, String extraName, String extra) {
        Intent i = new Intent();
        i.putExtra("NameCommand", intentName);
        i.putExtra("DataCommand", extra);
        i.setComponent(new ComponentName("qira.com.installpackagesbroadcast", "qira.com.installpackagesbroadcast.commandBroadcast"));
        ComponentName c = startService(i);


//        Intent intent = new Intent();
//        intent.setAction(intentName);
//        intent.putExtra(extraName, extra);
//        intent.addCategory("android.intent.category.DEFAULT");
//        sendBroadcast(intent, "qira.com.installpackagesbroadcast.PERMITTED_ACTION");
    }

    public void attApp(String intentName) {

        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("qira.com.installpackagesbroadcast");
        launchIntent.putExtra("nameApk", intentName);
        launchIntent.addFlags(FLAG_ACTIVITY_SINGLE_TOP);
        if (launchIntent != null) {
            startActivity(launchIntent);
        }


    }


}
