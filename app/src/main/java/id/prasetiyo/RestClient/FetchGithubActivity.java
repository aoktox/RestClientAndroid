package id.prasetiyo.RestClient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import id.prasetiyo.RestClient.adapter.GithubAdapter;
import id.prasetiyo.RestClient.helper.ImageLoadTask;
import id.prasetiyo.RestClient.model.RepoItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchGithubActivity extends AppCompatActivity {
    private static String TAG = "FetchGithubActivity";
    private static String URL = "https://api.github.com/users/";
    ProgressDialog prgDialog;
    OkHttpClient client;
    Request request;

    Button btn_start_fetch;
    EditText txt_user_github;
    String username;
    LinearLayout linear_1;
    ImageView pic_profile;
    TextView nama_profile;
    TextView url_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_github);
        btn_start_fetch = (Button) findViewById(R.id.btn_start_fetch);
        txt_user_github = (EditText) findViewById(R.id.txt_user_github);
        final ListView lv = (ListView) findViewById(R.id.listView_github);
        linear_1 = (LinearLayout) findViewById(R.id.linear_1);
        pic_profile = (ImageView) findViewById(R.id.imageView_profile);
        nama_profile = (TextView) findViewById(R.id.txt_nama_user);
        url_profile = (TextView) findViewById(R.id.txt_url_user);
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Tunggu sebentar...");
        prgDialog.setCancelable(false);
        linear_1.setVisibility(View.INVISIBLE);

        btn_start_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txt_user_github.getText().toString();
                prgDialog.show();
                client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

                client.cache();
                request = new Request.Builder().url(URL+username+"/repos").build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call,IOException e) {
                        FetchGithubActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                prgDialog.hide();
                                Toast.makeText(FetchGithubActivity.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()){
                            FetchGithubActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FetchGithubActivity.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                                    prgDialog.hide();
                                }
                            });
                            throw new IOException("Kode bukan 200 :"+response);
                        }
                        try {
                            String jsonData = response.body().string();
                            //JSONObject Jobject = new JSONObject(jsonData);
                            JSONArray Jarray = new JSONArray(jsonData);
                            //System.out.println(Jarray.get(1).toString());
                            final JSONObject owner = ((JSONObject)Jarray.getJSONObject(1)).getJSONObject("owner");
                            System.out.println(owner);
                            ArrayList<RepoItem> repoItems = new ArrayList<RepoItem>();
                            RepoItem temp;
                            for (int i = 0; i < Jarray.length(); i++) {
                                JSONObject object = Jarray.getJSONObject(i);
                                temp = new RepoItem();
                                temp.setNama(object.getString("name"));
                                temp.setDesc(object.getString("description"));
                                temp.setUrl(object.getString("html_url"));
                                repoItems.add(temp);
                            }
                            final ArrayList<RepoItem> items = repoItems;
                            FetchGithubActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String url = owner.getString("avatar_url");
                                        new ImageLoadTask(url, pic_profile).execute();
                                        nama_profile.setText(owner.getString("login"));
                                        url_profile.setText(owner.getString("html_url"));
                                        lv.setAdapter(new GithubAdapter(FetchGithubActivity.this, items));
                                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                                Object o = lv.getItemAtPosition(position);
                                                RepoItem fullObject = (RepoItem) o;
                                                //Toast.makeText(FetchGithubActivity.this, "You have chosen: " + " " + fullObject.getNama(), Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(Intent.ACTION_VIEW);
                                                i.setData(Uri.parse(((RepoItem) o).getUrl()));
                                                startActivity(i);
                                            }
                                        });
                                        linear_1.setVisibility(View.VISIBLE);
                                        prgDialog.hide();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            Log.d("JSONException", "onResponse: "+e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
