package huang.zhiqin.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import huang.zhiqin.attendance.Adapter.HistoryAdapter;
import huang.zhiqin.attendance.Bean.Person;
import huang.zhiqin.attendance.Bean.Record;

public class HistoryActivity extends Activity {

    private Toolbar historyBar;
    private ListView recordsListView;
    private List<Record> courseList = new ArrayList<>();
    private Person person;

    public static final int RECEIVE = 125;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RECEIVE:
                    try {
                        JSONArray jsonArray = new JSONArray((String) msg.obj);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String oneId = jsonObject.getString("id");
                            int oneAbs = Integer.parseInt(jsonObject.getString("abs"));
                            int oneLateness = Integer.parseInt(jsonObject.getString("late"));
                            courseList.add(new Record(oneId,oneAbs,oneLateness));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    HistoryAdapter historyAdapter=new HistoryAdapter(HistoryActivity.this,R.layout.history_item,courseList);
                    recordsListView.setAdapter(historyAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        historyBar = (Toolbar) findViewById(R.id.toolbar_history);
        setActionBar(historyBar);
        historyBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        person = (Person) intent.getSerializableExtra("key");

        recordsListView=(ListView) findViewById(R.id.listView_history);
        getRecords();
        recordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Record recordOnClicked=courseList.get(position);
                Intent intentToSingle=new Intent(HistoryActivity.this,SingleActivity.class);
                intentToSingle.putExtra("sid",person.getId());
                intentToSingle.putExtra("cid",recordOnClicked.getCId());
                intentToSingle.putExtra("from","student");
                startActivity(intentToSingle);
            }
        });
    }

    public void getRecords() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet("http://172.20.10.3:8080//Checking/View.do?" + "id=" + person.getId());
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = RECEIVE;
                        message.obj = response;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
