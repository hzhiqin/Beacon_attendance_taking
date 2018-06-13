package huang.zhiqin.attendance;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import huang.zhiqin.attendance.Adapter.CounterAdapter;
import huang.zhiqin.attendance.Bean.AttNow;
import huang.zhiqin.attendance.Bean.Counter;

public class SingleActivity extends Activity {

    public static final int LIST = 99;
    Toolbar toolbarSingle;
    ListView listViewSingle;
    TextView singleText;
    String type;
    int sId;
    String cId;
    private List<Counter> detail = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LIST:
                    try {
                        JSONArray jsonArray = new JSONArray((String) msg.obj);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String oneWeek = jsonObject.getString("week");
                            int abs = jsonObject.getInt("abs");
                            int late = jsonObject.getInt("late");
                            detail.add(new Counter(oneWeek, abs, late));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CounterAdapter adapter = new CounterAdapter(SingleActivity.this, R.layout.counter_item, detail);
                    listViewSingle.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_layout);
        toolbarSingle = (Toolbar) findViewById(R.id.toolbar_single);
        setActionBar(toolbarSingle);
        toolbarSingle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        singleText = (TextView) findViewById(R.id.textView_single);
        type = intent.getStringExtra("from");
        sId = intent.getIntExtra("sid", 0);
        if (sId == 0) {
            Toast.makeText(SingleActivity.this, "loading error", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        cId = intent.getStringExtra("cid");
        if (type.equals("manager")) {
            singleText.setText("student ID: "+sId);
        }
        if (type.equals("student")) {
            singleText.setText("course ID: "+cId);
        }
        listViewSingle = (ListView) findViewById(R.id.listView_single);
        getRecord(sId, cId);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void getRecord(final int sId, final String cId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /*initiating http connection*/
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://172.20.10.3:8080//Checking/View.do");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sid", String.valueOf(sId)));
                    params.add(new BasicNameValuePair("cid", cId));
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
                    httpPost.setEntity(entity);
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    /*response handler*/
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        HttpEntity httpEntity = httpResponse.getEntity();
                        String response = EntityUtils.toString(httpEntity, "UTF-8");
                        Message message = new Message();
                        message.what = LIST;
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
