package API;

import android.content.Context;
import android.util.Log;

import com.samplesqllite.example.BuildConfig;
import com.samplesqllite.example.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import Application.ApplicationClass;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationClass.getInstance().getResources().getString(R.string.BASE_URL))
                    .client(getOkhttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient getOkhttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (!BuildConfig.RETROFIT_LOG_INTERCEPTOR) {
            return new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build();
        } else {
            return new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .addInterceptor(interceptor)
                    .build();
        }
    }

    public static void callGetResponseString(final Context mContext, final RetrofitResultInterface resultInterface, Call<ResponseBody> result, final int msgId) {

        result.enqueue(new Callback<ResponseBody>() {
                           @Override
                           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                               if (response != null && !response.isSuccessful() && response.errorBody() != null) {
                                   resultInterface.onGetFailed(getResponseBody(response.errorBody()), msgId);
                                   Log.e("callGetResponseString", response.errorBody() + "");
                                   return;
                               } else {
                                   resultInterface.onGetSuccess(getResponseBody(response.body()), msgId);
                               }
                           }

                           @Override
                           public void onFailure(Call<ResponseBody> call, Throwable t) {
                               resultInterface.onGetFailed(t.getMessage(), msgId);
                               Log.e("getErrorDescription", t.getLocalizedMessage() + "");
                           }
                       }
        );

    }

    private static String getResponseBody(ResponseBody responseBody) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(responseBody.byteStream()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static RetrofitInterface getApiService() {
        return RetrofitClient.getClient().create(RetrofitInterface.class);
    }
}