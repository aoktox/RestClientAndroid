package id.prasetiyo.RestClient.API;

import id.prasetiyo.RestClient.model.QuestionsModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aoktox on 16/04/16.
 */
public interface StackExchangeAPI {
    public static final String baseURL="https://api.stackexchange.com/2.2/";

    Retrofit clientku = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/search?")
    public Call<QuestionsModel> getQuestions(@Query("order") String order,
                                             @Query("sort") String sort,
                                             @Query("tagged") String tagged,
                                             @Query("site") String site);

}
