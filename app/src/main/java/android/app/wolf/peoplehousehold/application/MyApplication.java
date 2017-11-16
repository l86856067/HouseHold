package android.app.wolf.peoplehousehold.application;

import android.app.Application;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        if (instance == null){
            return instance;
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
