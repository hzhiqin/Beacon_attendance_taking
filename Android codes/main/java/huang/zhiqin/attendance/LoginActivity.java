package huang.zhiqin.attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.aprilbrother.aprilbrothersdk.BeaconManager;

import huang.zhiqin.attendance.Bean.Person;


public class LoginActivity extends Activity {

    public static final int SENDING_TIP = 5;

    private Button signInButton;
    private EditText id, pw;
    private Toolbar loginBar;
    private CheckBox cb;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SENDING_TIP:
                    try {
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        int loginStatus = jsonObject.getInt("status");
                        switch (loginStatus) {
                            case 3:
                                Toast.makeText(LoginActivity.this, "Wrong id-password pair", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Person characterStd1 = new Person(jsonObject.getString("name"), jsonObject.getString("surname"), loginStatus, jsonObject.getInt("id"));
                                if (cb.isChecked()) {
                                    rememberUser(jsonObject.getInt("id"), true, jsonObject.getString("name"), jsonObject.getString("surname"), 0);
                                } else {
                                    rememberUser(jsonObject.getInt("id"), false, jsonObject.getString("name"), jsonObject.getString("surname"), 0);
                                }
                                Intent intent1 = new Intent(LoginActivity.this, StudentActivity.class);
                                intent1.putExtra("key", characterStd1);
                                startActivity(intent1);
                                finish();
                                break;
                            case 1:
                                Person characterMag = new Person(jsonObject.getString("name"), jsonObject.getString("surname"), loginStatus, jsonObject.getInt("id"));
                                if (cb.isChecked()) {
                                    rememberUser(jsonObject.getInt("id"), true, jsonObject.getString("name"), jsonObject.getString("surname"), 1);
                                } else {
                                    rememberUser(jsonObject.getInt("id"), false, jsonObject.getString("name"), jsonObject.getString("surname"), 1);
                                }
                                Intent intent2 = new Intent(LoginActivity.this, ManagerActivity.class);
                                intent2.putExtra("key", characterMag);
                                startActivity(intent2);
                                finish();
                                break;
                            case 2:
                                Person characterStd2 = new Person(jsonObject.getString("name"), jsonObject.getString("surname"), loginStatus, jsonObject.getInt("id"));
                                if (cb.isChecked()) {
                                    rememberUser(jsonObject.getInt("id"), true, jsonObject.getString("name"), jsonObject.getString("surname"), 2);
                                } else {
                                    rememberUser(jsonObject.getInt("id"), false, jsonObject.getString("name"), jsonObject.getString("surname"), 2);
                                }
                                Intent intent3 = new Intent(LoginActivity.this, StudentActivity.class);
                                intent3.putExtra("key", characterStd2);
                                startActivity(intent3);
                                finish();
                                break;
                            default:
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private String url = "http://172.20.10.3:8080//Checking/Login.do";//change when distribute

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        loginBar = (Toolbar) findViewById(R.id.toolbar_login);
        setActionBar(loginBar);

        signInButton = (Button) findViewById(R.id.sign_button);
        id = (EditText) findViewById(R.id.id_text);
        id.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        pw = (EditText) findViewById(R.id.pass_text);
        cb = (CheckBox) findViewById(R.id.login_checkbox_re);

        bluetoothCheck();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modification needed here
                String idText = id.getText().toString();
                String pwText = pw.getText().toString();
                if (idText == "" || pwText == "") {
                    Toast.makeText(LoginActivity.this, "Please input your id and password", Toast.LENGTH_SHORT).show();
                } else if (idText.length() != 9) {
                    Toast.makeText(LoginActivity.this, "ID should be 9-digit", Toast.LENGTH_SHORT).show();
                } else if (!LoginActivity.isNumeric(idText)) {
                    Toast.makeText(LoginActivity.this, "ID should only contain number", Toast.LENGTH_SHORT).show();
                } else {
                    sendRequestWithHttpClient();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothCheck();
    }

    /**
     * To verify value in id text field is numeric
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("id", id.getText().toString()));
                    params.add(new BasicNameValuePair("password", pw.getText().toString()));
                    params.add(new BasicNameValuePair("device",getDevice()));
                    if (cb.isChecked()){
                        params.add(new BasicNameValuePair("remember","1"));
                    }else {
                        params.add(new BasicNameValuePair("remember","0"));
                    }
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                    httpPost.setEntity(entity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = SENDING_TIP;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Checking and asking user to turn on Bluetooth if it is not usable
     */
    public void bluetoothCheck() {
        //Bluetooth usability check
        BeaconManager beaconManager = new BeaconManager(LoginActivity.this);
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(LoginActivity.this, "BLE support is needed for device", Toast.LENGTH_LONG).show();
        }

        if (!beaconManager.isBluetoothEnabled()) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Bluetooth permission")
                    .setMessage("This app needs your Bluetooth")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            bluetoothAdapter.enable();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this, "The app cannot work properly without bluetooth", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
    }

    /**
     * @param id
     * @param isCheck
     * @param name
     * @param surname
     * @param userType
     */
    public void rememberUser(int id, boolean isCheck, String name, String surname, int userType) {
        SharedPreferences userInfo = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        editor.putInt("id", id);
        editor.putBoolean("check", isCheck);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.putInt("user", userType);
        editor.commit();
    }

    public String getDevice(){
        TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String deviceIMEI = TelephonyMgr.getDeviceId();
        return deviceIMEI;
    }
}
