package huang.zhiqin.attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothDevice;
import android.widget.Toolbar;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import huang.zhiqin.attendance.Adapter.CourseAdapter;
import huang.zhiqin.attendance.Bean.*;

public class StudentActivity extends Activity {

    public static final int RECEIVING = 666;
    public static final int CHECKING = 998;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private Beacon where;
    private BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            where = Utils.beaconFromLeScan(device, rssi, scanRecord);
            if ((where != null) && (where.getProximityUUID().equals("d94f2542-c9c7-4de4-8879-25399fe2612d"))) {
                mBluetoothAdapter.stopLeScan(callback);
                roomDisplay.setText(where.getMajor() + "" + where.getMinor());
            }
        }
    };

    private List<Course> courseList = new ArrayList<Course>();//data in list view
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVING:
                    try {
                        JSONArray jsonArray = new JSONArray((String) msg.obj);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String oneId = jsonObject.getString("id");
                            int oneLoc = Integer.parseInt(jsonObject.getString("loc"));
                            String timeString = jsonObject.getString("time");
                            String[] timeBuffer = timeString.split(":");
                            Time oneTime = new Time(Integer.parseInt(timeBuffer[0]), Integer.parseInt(timeBuffer[1]), Integer.parseInt(timeBuffer[2]));
                            int oneDur = Integer.parseInt(jsonObject.getString("dur"));
                            courseList.add(new Course(oneId, oneLoc, oneTime, oneDur));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHECKING:
                    try {
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        String checkResult = jsonObject.getString("cr");
                        switch (checkResult) {
                            case "00":
                                Toast.makeText(StudentActivity.this, "not class time", Toast.LENGTH_SHORT).show();
                                break;
                            case "01":
                                Toast.makeText(StudentActivity.this, "lateness recorded", Toast.LENGTH_SHORT).show();
                                break;
                            case "10":
                                Toast.makeText(StudentActivity.this, "data recording error", Toast.LENGTH_SHORT).show();
                                break;
                            case "11":
                                Toast.makeText(StudentActivity.this, "check-in succeed", Toast.LENGTH_SHORT).show();
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
    private Person person;
    private TextView nameDisplay;
    private TextView roomDisplay;
    private Button btnRefresh;
    private ListView listView;
    private Toolbar studentBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_layout);

        /**
         * Create action bar
         */
        studentBar = (Toolbar) findViewById(R.id.toolbar_stu);
        setActionBar(studentBar);
        studentBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all_course:
                        Intent intentList = new Intent(StudentActivity.this, HistoryActivity.class);
                        intentList.putExtra("key", person);
                        startActivity(intentList);//open new activity for this student
                        break;
                    case R.id.sign_out:
                        //return login activity and wipe the data of login
                        SharedPreferences userInfo = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = userInfo.edit();
                        editor.clear();
                        editor.commit();
                        Intent intentBack = new Intent(StudentActivity.this, LoginActivity.class);
                        startActivity(intentBack);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        bluetoothCheck();
        /**
         * preparing ble device searching
         */
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        mBluetoothAdapter.startLeScan(callback);

        btnRefresh = (Button) findViewById(R.id.su_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "re-detecting location", Toast.LENGTH_SHORT).show();
                bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = bluetoothManager.getAdapter();
                mBluetoothAdapter.startLeScan(callback);
            }
        });

        /*
         *receiving person obj from last activity
         */
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("key");
        if (person.getStatus() == 2) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("New device detected")
                    .setContentText("Recording attendance only allowed in known device")
                    .setTicker("New device")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true);
            Notification note = builder.build();
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1,note);
        }

        nameDisplay = (TextView) findViewById(R.id.textName);
        roomDisplay = (TextView) findViewById(R.id.textRoom);
        nameDisplay.setText(person.getName() + "  " + person.getSurname());

        initCourses();//get listView prepared

        listView = (ListView) findViewById(R.id.listView_course);
        CourseAdapter adapter = new CourseAdapter(StudentActivity.this, R.layout.course_item, courseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Course courseOnclick = courseList.get(position);
                if (person.getStatus() == 0) {
                    if (!(String.valueOf(courseOnclick.getLocation())).equals(where.getMajor() + "" + where.getMinor())) {
                        Toast.makeText(StudentActivity.this, "This course is in Room " + courseOnclick.getLocation(), Toast.LENGTH_SHORT).show();
                    } else {
                        new AlertDialog.Builder(StudentActivity.this)
                                .setTitle("Check-in")
                                .setMessage("for module " + courseOnclick.getId())
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkForThis(courseOnclick, person);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stu_menu, menu);
        return true;//allow the menu to display
    }

    /**
     * reading class data from servlet
     * server logic needed
     */
    private void initCourses() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://172.20.10.3:8080//Checking/Student.do?" + "id=" + person.getId());
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = RECEIVING;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkForThis(final Course onClickCourse, final Person onePerson) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://172.20.10.3:8080//Checking/Student.do");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("student_id", String.valueOf(onePerson.getId())));
                    params.add(new BasicNameValuePair("course_id", onClickCourse.getId()));
                    params.add(new BasicNameValuePair("this_time", onClickCourse.getTime().toString()));
                    params.add(new BasicNameValuePair("this_dur", String.valueOf(onClickCourse.getDuration())));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                    httpPost.setEntity(entity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = CHECKING;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothCheck();
    }

    public void bluetoothCheck(){
        BeaconManager beaconManager = new BeaconManager(StudentActivity.this);
        if (!beaconManager.hasBluetooth()) {
            Toast.makeText(StudentActivity.this, "BLE support is needed for device", Toast.LENGTH_LONG).show();
        }

        if (!beaconManager.isBluetoothEnabled()) {
            new AlertDialog.Builder(StudentActivity.this)
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
                            Toast.makeText(StudentActivity.this, "The app cannot work properly without bluetooth", Toast.LENGTH_LONG).show();
                        }
                    })
                    .show();
        }
    }
}