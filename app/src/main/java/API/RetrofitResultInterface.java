package API;

public interface RetrofitResultInterface {

    void onGetFailed(String message, int msgID);

    void onGetSuccess(String getResponse, int msgID);
}