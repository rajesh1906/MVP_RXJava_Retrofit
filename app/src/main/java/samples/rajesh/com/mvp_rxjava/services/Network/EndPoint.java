package samples.rajesh.com.mvp_rxjava.services.Network;

import android.content.Context;
import android.util.Log;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rajesh Kumar on 15-02-2018.
 */

public class EndPoint  {

   Context context;
    ApiService apiService;
    EndPoint(Context context,String coming_from){

        this.context = context;
         apiService =((ConnectApiService) RetrofitClient.getInstance()).getApiService(coming_from);

    }




    public void getResult(Map<String ,String > params, final APIResponse api_res ){
        params.put("InputType","M");
        String action = params.get("action");
        params.remove("action");
        for ( Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String tab = entry.getValue();
            Log.e("key is ","<><"+key+" value is "+tab);
            // do something with key and/or tab
        }



            apiService.getApiResultCity(action+"?",params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        try {

                            api_res.onSuccess(response.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                api_res.onFailure(call.toString());
                            } catch (Exception ee) {
                                ee.printStackTrace();
                            }
                        } finally {
                            RetrofitClient.getInstance().hideProgressDialog();
                        }

                    } else {
                        RetrofitClient.getInstance().hideProgressDialog();
                        api_res.onFailure("Data validation Error");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });




    }

    public void postResult(Map<String ,String > params, final APIResponse api_res){
        apiService.getPost(APIS.BASEURL_FOR_POST,params).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    try {

                        api_res.onSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            api_res.onFailure(call.toString());
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    } finally {
                        RetrofitClient.getInstance().hideProgressDialog();
                    }

                } else {
                    RetrofitClient.getInstance().hideProgressDialog();
                    api_res.onFailure("Data validation Error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


}
