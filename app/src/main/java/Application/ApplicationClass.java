package Application;

import android.app.Application;

public class ApplicationClass extends Application {

    private static ApplicationClass aClass;

    public static synchronized ApplicationClass getInstance() {
        return aClass;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        aClass = this;
    }
}