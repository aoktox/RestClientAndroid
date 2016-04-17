package id.prasetiyo.RestClient;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHTTP extends AppCompatActivity {

    private static String get_url = "http://192.168.1.124:8000/api/v1/users";
    private static String post_url = "http://192.168.1.124:8000/api/v1/addUser";
    private static String put_url = "http://192.168.1.124:8000/api/v1/updateUser";

    private static String API_KEY;

    Button btn_req_okHTTP,btn_req_okHTTP_post,btn_req_okHTTP_put;
    TextView txt_hasil_req;
    ProgressDialog prgDialog;
    OkHttpClient client;
    Request request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        btn_req_okHTTP = (Button) findViewById(R.id.btn_req_okHTTP);
        btn_req_okHTTP_post = (Button) findViewById(R.id.btn_req_okHTTP_post);
        btn_req_okHTTP_put = (Button) findViewById(R.id.btn_req_okHTTP_put);
        txt_hasil_req = (TextView) findViewById(R.id.txt_hasil_req);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Tunggu sebentar...");
        prgDialog.setCancelable(false);

        btn_req_okHTTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog.show();
                //client = new OkHttpClient();
                client = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

                client.cache();
                request = new Request.Builder().url(get_url).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        OkHTTP.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(OkHTTP.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                                prgDialog.hide();
                            }
                        });
                        e.printStackTrace();
                        Log.d("FAIL", "onFailure: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()){
                            OkHTTP.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OkHTTP.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                                    prgDialog.hide();
                                }
                            });
                            throw new IOException("Kode bukan 200 :"+response);
                        }

                        final String responseData = response.body().string();

                        OkHTTP.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt_hasil_req.setText(responseData);
                                //Log.d("array",response.body().string());
                                prgDialog.hide();
                            }
                        });
                    }
                });
            }
        });


        btn_req_okHTTP_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody body = new FormBody.Builder()
                        .add("name","Agus Prasetiyo")
                        .add("password","rahasialah")
                        .add("email","agus@prasetiyo.id")
                        .build();
                OkHttpClient client = new OkHttpClient();
                client.cache();

                Request request = new Request.Builder().url(post_url)
                        .post(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(OkHTTP.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(!response.isSuccessful()){
                            throw new IOException("Error :"+response);
                        }

                        final String responeData = response.body().string();

                        OkHTTP.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt_hasil_req.setText(responeData);
                            }
                        });
                    }
                });
            }
        });

        btn_req_okHTTP_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgDialog.show();
                API_KEY="rahasia";
                RequestBody body = new FormBody.Builder()
                        .add("name","Agus Prasetiyo")
                        .add("password","rahasialah")
                        .add("email","agus@prasetiyo.id")
                        .add("email","agus@prasetiyo.id")
                        .build();
                OkHttpClient client = new OkHttpClient();
                client.cache();

                Request request = new Request.Builder()
                        .addHeader("Authorization", API_KEY)
                        .url(put_url)
                        .put(body).build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        //Toast.makeText(OkHTTP.this, "An error occured gan.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(!response.isSuccessful()){
                            throw new IOException("Error :"+response);
                        }

                        try {
                            String responseData = response.body().string();
                            JSONObject json = new JSONObject(responseData);
                            final Boolean isError = json.getBoolean("error");//json.getString("error");
                            final String pesan = json.getString("pesan");
                            OkHTTP.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txt_hasil_req.setText(pesan);
                                    prgDialog.hide();
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
