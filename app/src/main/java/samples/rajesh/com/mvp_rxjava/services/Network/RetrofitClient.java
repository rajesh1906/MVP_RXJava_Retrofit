package samples.rajesh.com.mvp_rxjava.services.Network;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public class RetrofitClient extends AppCompatActivity implements ConnectApiService {
    private static Retrofit retrofit = null;
    private static RetrofitClient uniqInstance;
    Context ctx;
    EndPoint endPoint = null;
    public static RetrofitClient getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new RetrofitClient();
        }
        return uniqInstance;
    }

    public static Retrofit getClient(String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new RetrofitConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }





    public EndPoint getEndPoint(Context context, String progress_bar_status){
        this.ctx = context;

            if (endPoint == null) {
                endPoint = new EndPoint(context,"get");
            }
            if (progress_bar_status.length()!=0) {
                showProgressDialog(context);
            }
        return endPoint;
    }
    public EndPoint postEndPoint(Context context, String progress_bar_status){
        this.ctx = context;

        if (endPoint == null) {
            endPoint = new EndPoint(context,"post");
        }
        if (progress_bar_status.length()!=0) {
            showProgressDialog(context);
        }
        return endPoint;
    }


    public void showProgressDialog(Context context){


    }

    public void hideProgressDialog(){

    }
    @Override
    public ApiService getApiService(String coming_from) {
        return  RetrofitClient.getClient(coming_from.equalsIgnoreCase("get")?APIS.BASEURL:APIS.BASEURL_FOR_POST).create(ApiService.class);
    }
}
