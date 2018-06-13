package huang.zhiqin.attendance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Message;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import huang.zhiqin.attendance.Adapter.AttAdapter;
import huang.zhiqin.attendance.Bean.*;

public class ManagerActivity extends Activity {

    public static final int LOOK = 600;

    private EditText classId;
    private TextView nameDisplay;
    private TextView latePercent;
    private TextView absPercent;
    private Button btnSearch;
    private ListView listView;
    private Person person;

    private List<AttNow> attList = new ArrayList<>();//data in list view
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOOK:
                    if (((String) msg.obj).equals("[")) {
                        Toast.makeText(ManagerActivity.this, "Module not found", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            attList.clear();
                            JSONArray jsonArray = new JSONArray((String) msg.obj);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int stuId = jsonObject.getInt("stuId");
                                int lastStatus = jsonObject.getInt("state");
                                attList.add(new AttNow(stuId, lastStatus));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        AttAdapter adapter = new AttAdapter(ManagerActivity.this, R.layout.att_item, attList);
                        listView.setAdapter(adapter);
                        summary();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);
        Toolbar managerBar = (Toolbar) findViewById(R.id.toolbar_ma);
        setActionBar(managerBar);
        managerBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sign_out_ma:
                        //return login activity and wipe the data of login
                        SharedPreferences userInfo = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = userInfo.edit();
                        editor.clear();
                        editor.commit();
                        Intent intentBack = new Intent(ManagerActivity.this, LoginActivity.class);
                        startActivity(intentBack);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        nameDisplay = (TextView) findViewById(R.id.textName_ma);
        btnSearch = (Button) findViewById(R.id.search_ma);
        classId = (EditText) findViewById(R.id.course_text_ma);
        latePercent = (TextView) findViewById(R.id.textView_latePercent);
        absPercent = (TextView) findViewById(R.id.textView_absPercent);
        listView = (ListView) findViewById(R.id.listView_ma);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AttNow attOnClicked = attList.get(position);
                Intent intentToSingle = new Intent(ManagerActivity.this, SingleActivity.class);
                intentToSingle.putExtra("sid", attOnClicked.getStuId());
                intentToSingle.putExtra("cid", preDeal(classId.getText().toString()));
                intentToSingle.putExtra("from", "manager");
                startActivity(intentToSingle);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cIdBuffer = (classId.getText()).toString();
                boolean checkSta = true;
                if (cIdBuffer == "") {
                    Toast.makeText(ManagerActivity.this, "Course id contains three letters and four digits", Toast.LENGTH_SHORT).show();
                    checkSta = false;
                } else if (cIdBuffer.length() != 7) {
                    Toast.makeText(ManagerActivity.this, "Course id contains three letters and four digits", Toast.LENGTH_SHORT).show();
                    checkSta = false;
                } else {
                    for (int i = 0; i < 3; i++) {
                        if (Character.isLetter(cIdBuffer.charAt(i)) == false) {
                            checkSta = false;
                            break;
                        }
                    }
                    for (int j = 3; j < 7; j++) {
                        if (Character.isDigit(cIdBuffer.charAt(j)) == false) {
                            checkSta = false;
                            break;
                        }
                    }
                }
                if (checkSta == true) {
                    String lookUpId = preDeal(classId.getText().toString());
                    prepareList(lookUpId);
                } else {
                    Toast.makeText(ManagerActivity.this, "Wrong format", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("key");//get person object from LoginActivity
        nameDisplay.setText(person.getName() + "  " + person.getSurname());
    }

    public void prepareList(final String idString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://172.20.10.3:8080//Checking/Manager.do?" + "id=" + idString);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = LOOK;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String preDeal(String str) {
        char[] buffer = str.toCharArray();
        for (int n = 0; n < 3; n++) {
            if (Character.isLowerCase(buffer[n])) {
                buffer[n] = Character.toUpperCase(buffer[n]);
            }
        }
        return (String.valueOf(buffer));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager_menu, menu);
        return true;//allow the menu to display
    }

    public void summary() {
        int total;
        int lateness=0;
        int absence=0;
        String percentageAbs;
        String percentageLate;
        NumberFormat format=NumberFormat.getInstance();
        format.setMaximumFractionDigits(1);

        total = attList.size();
        for (int i = 0; i < attList.size(); i++) {
            if (attList.get(i).getLastStatus()==0) {
                absence++;
            }
            if (attList.get(i).getLastStatus()==2) {
                lateness++;
            }
        }

        percentageLate = format.format((float) lateness / (float) total * 100);
        percentageAbs = format.format((float) absence / (float) total * 100);

        latePercent.setText("late: "+lateness+"/"+total+"  "+percentageLate+"%");
        absPercent.setText("absence: "+absence+"/"+total+"  "+percentageAbs+"%");
    }
}
