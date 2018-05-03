package samples.rajesh.com.mvp_rxjava.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.observable.ObservableFromCallable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import samples.rajesh.com.mvp_rxjava.R;
import samples.rajesh.com.mvp_rxjava.services.Network.APIS;
import samples.rajesh.com.mvp_rxjava.services.Network.ApiService;
import samples.rajesh.com.mvp_rxjava.services.Network.RetrofitClient;
import samples.rajesh.com.mvp_rxjava.services.Network.RetrofitConverter;

/**
 * Created by Rajesh Kumar on 02-05-2018.
 */
public class RxjavaImpl extends AppCompatActivity {
    String lat = "16.989065", lng = "82.247465";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callingApi("post");
    }

    private void callingApi(String calling_type){

        if(calling_type.equalsIgnoreCase("get")){
            flatOperator("get").subscribe(new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    Log.e("inner On Next calling", "<><>" + s);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }else{

          /*  Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .build();

          Retrofit retrofit = new Retrofit.Builder()
                  .baseUrl(APIS.BASEURL_FOR_POST)
                  .addConverterFactory(new RetrofitConverter())
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                  .addConverterFactory(GsonConverterFactory.create(gson))
                  .client(okHttpClient)
                  .build();


           ApiService apiService = retrofit.create(ApiService.class);
           apiService.postData(APIS.uccfm_app_common_dtls, getParams(calling_type)).
                   subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(new Observer<String>() {
                       @Override
                       public void onSubscribe(Disposable d) {
                           Log.e("subcribe","<><>");
                       }

                       @Override
                       public void onNext(String s) {
                           Log.e("onNext","<><>"+s);
                       }

                       @Override
                       public void onError(Throwable e) {
                           Log.e("onError","<><>"+e.getMessage());
                       }

                       @Override
                       public void onComplete() {
                           Log.e("onComplete","<><>");
                       }
                   });*/


            Observable.just(RetrofitClient.getClient(APIS.BASEURL_FOR_POST).create(ApiService.class)).subscribeOn(Schedulers.computation())
                    .flatMap(s -> {
                        Observable<String> couponsObservable = s.postData(APIS.uccfm_app_common_dtls, getParams("post"));

                        return couponsObservable;
                    }).observeOn(Schedulers.computation())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.e("onSubscribe","<><>");
                        }

                        @Override
                        public void onNext(String s) {
                            Log.e("onNext","<><>"+s);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("onError","<><>");
                        }

                        @Override
                        public void onComplete() {
                            Log.e("onComplete","<><>");
                        }
                    });



            /*postObseravble("post")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            Log.e("subcribe","<><>");

                        }

                        @Override
                        public void onNext(String stringCall) {
                            Log.e("onNext","<><>"+stringCall.toString());



                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("onError","<><>");
                        }

                        @Override
                        public void onComplete() {
                            Log.e("onComplete","<><>");
                        }
                    });*/
        }
    }



    private Observable<String> flatOperator(String calling_type){

        return   Observable.defer(new ObservableFromCallable<>(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                try {
//                        return Observable.just(getGist(url_1));
                    return getObserable(calling_type);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;

            }
        }));
    }
    

    @SuppressLint("CheckResult")
    private Observable<String > getObserable(String calling_type) {
            return   (new RetrofitClient().getClient(APIS.BASEURL).create(ApiService.class))
                    .getData(APIS.GettingResDataBasedOnLat, getParams(calling_type))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

    }


    private Observable<String > postObseravble(String calling_type){

        return new RetrofitClient().getClient(APIS.BASEURL_FOR_POST).create(ApiService.class).postData(APIS.uccfm_app_common_dtls, getParams(calling_type));

        /*return   (new RetrofitClient().getClient(APIS.BASEURL_FOR_POST).create(ApiService.class))
                .postData(APIS.uccfm_app_common_dtls, getParams(calling_type))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()); */
    }

    private Map<String, String> getParams(String calling_type) {
        Map<String, String> params = new HashMap<>();

        switch (calling_type){
            case "get":
                params.put("action", APIS.GettingResDataBasedOnLat);
                params.put("LatLng", lat + "," + lng);
                break;
            case "post":
                params.put("go",APIS.uccfm_app_common_dtls);
                params.put("action", APIS.uccfm_app_common_dtls);
                params.put("field_executive_id", ""+227);
                params.put("api_id", "uccfm2018v1.0");
                break;
        }


        return params;
    }

}
