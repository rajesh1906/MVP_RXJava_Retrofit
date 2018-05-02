package samples.rajesh.com.mvp_rxjava.services;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
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
import samples.rajesh.com.mvp_rxjava.R;
import samples.rajesh.com.mvp_rxjava.services.Network.APIS;
import samples.rajesh.com.mvp_rxjava.services.Network.ApiService;
import samples.rajesh.com.mvp_rxjava.services.Network.RetrofitClient;

/**
 * Created by Rajesh Kumar on 02-05-2018.
 */
public class RxjavaImpl extends AppCompatActivity {
    String lat = "16.989065", lng = "82.247465";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*flatOperator().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("Subcribe calling is ","<><>");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("On next is ","<><>"+s);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error calling ","<><>"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("complte calling ","<><>");
                    }
                });*/

        flatOperator().subscribe(new Observer<String>() {
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
    }
    private Observable<String> flatOperator(){

        return   Observable.defer(new ObservableFromCallable<>(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                try {
//                        return Observable.just(getGist(url_1));
                    return getObserable();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;

            }
        }));
    }
    

    @SuppressLint("CheckResult")
    private Observable<String > getObserable() {
        return   (new RetrofitClient().getClient(APIS.BASEURL).create(ApiService.class))
                .getData(APIS.GettingResDataBasedOnLat, getParams())
                .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread());
    }

    private Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        params.put("action", APIS.GettingResDataBasedOnLat);
        params.put("LatLng", lat + "," + lng);

        return params;
    }

}
