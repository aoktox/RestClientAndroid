package id.prasetiyo.RestClient;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import id.prasetiyo.RestClient.API.StackExchangeAPI;
import id.prasetiyo.RestClient.adapter.AdapterQuestion;
import id.prasetiyo.RestClient.model.Item;
import id.prasetiyo.RestClient.model.QuestionsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Retrofit extends AppCompatActivity {

    private String TAG = "Retrofit";
    private Button btnProses;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        btnProses = (Button) findViewById(R.id.btn_proses);
        recyclerView = (RecyclerView) findViewById(R.id.rv_question);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //menghilangkan progress bar
        progressBar.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                String order = "desc";
                String sort = "activity";
                String tag = "android";
                String site = "stackoverflow";

                StackExchangeAPI apiservice = StackExchangeAPI.clientku.create(StackExchangeAPI.class);

                Call<QuestionsModel> call = apiservice.getQuestions(order,sort,tag,site);

                call.enqueue(new Callback<QuestionsModel>() {
                    @Override
                    public void onResponse(Call<QuestionsModel> call, Response<QuestionsModel> response) {
                        QuestionsModel questionsModel = response.body();

                        AdapterQuestion adapterQuestion = new AdapterQuestion(getApplicationContext(), questionsModel.getItems(), new AdapterQuestion.OnItemClickListener() {
                            @Override
                            public void onClick(Item Item) {
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(Item.getLink()));
                                startActivity(i);
                            }
                        });

                        progressBar.setVisibility(View.INVISIBLE);

                        recyclerView.setAdapter(adapterQuestion);
                    }

                    @Override
                    public void onFailure(Call<QuestionsModel> call, Throwable t) {
                        Log.e(TAG, "onFailure: ", t.fillInStackTrace());

                        //menghilangkan progres bar
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }
}
