package android.app.wolf.peoplehousehold.http.httpconstant;

/**
 * Created by lh on 2017/10/16.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class HttpHost {

    private static boolean isDebug = true;

    public HttpHost() {
    }

    public String debugHost = "http://101.132.115.12:8080/";

    public String formalHost = "222";

    public static String getHttpHost(){
        if (isDebug){
            return new HttpHost().debugHost;
        }else {
            return new HttpHost().formalHost;
        }
    }

}
