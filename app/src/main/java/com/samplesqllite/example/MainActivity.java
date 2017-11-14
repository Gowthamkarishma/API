package com.samplesqllite.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;

import API.RetrofitClient;
import API.RetrofitResultInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements RetrofitResultInterface {

    private Call<ResponseBody> result;
    private String img_path = "";
    private File imageUpdateFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    private void callAddCarAPI() {

        HashMap<String, RequestBody> params = new HashMap<>();
        params.put("id", convertRequestBody(""));
        params.put("shop_id", convertRequestBody(""));

        MultipartBody.Part body = null;
        if (img_path != null && !img_path.isEmpty())

        {
            String fileName = "";
            imageUpdateFile = new File(img_path);
            File file = new File(img_path);
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            if (!imageUpdateFile.getName().contains(".")) {
                fileName = imageUpdateFile.getName() + ".jpg";
            } else {
                fileName = imageUpdateFile.getName();
            }
            body = MultipartBody.Part.createFormData("image", fileName, requestBody);
            result = RetrofitClient.getApiService().registerationCall(params, body);
            RetrofitClient.callGetResponseString(this, this, result, 1);
        } else {
            result = RetrofitClient.getApiService().registerationCall(params);
            RetrofitClient.callGetResponseString(this, this, result, 1);
        }
    }


    private void getInfo(String id) {
        JsonObject object = new JsonObject();
        object.addProperty("id", "key");
        Call<ResponseBody> result = RetrofitClient.getApiService().loginCall(object);
        RetrofitClient.callGetResponseString(this, this, result, 1);
    }

    @Override
    public void onGetFailed(String message, int msgID) {

    }

    @Override
    public void onGetSuccess(String getResponse, int msgID) {

    }

    public static RequestBody convertRequestBody(String inputTxt) {
        return RequestBody.create(MediaType.parse("text/plain"), inputTxt);
    }
}