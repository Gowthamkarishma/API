package API;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RetrofitInterface {

    // Without image upload
    @Multipart
    @POST("owner-signup")
    Call<ResponseBody> registerationCall(@PartMap() Map<String, RequestBody> partMap);

    // with image uploading
    @Multipart
    @POST("owner-signup")
    Call<ResponseBody> registerationCall(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part profile_picture);

    // Post Method
    @Headers("Content-Type: application/json")
    @POST("delete-cover-photo")
    Call<ResponseBody> loginCall(@Body JsonObject jsonObject);

    // Get Method
    @GET("cities")
    Call<ResponseBody> callCity();
}