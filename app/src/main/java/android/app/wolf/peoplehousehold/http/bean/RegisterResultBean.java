package android.app.wolf.peoplehousehold.http.bean;

/**
 * Created by lh on 2017/10/25.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class RegisterResultBean {


    /**
     * data : null
     * statusCode : 200
     * statusDesc : 注册成功
     */

    private Object data;
    private String statusCode;
    private String statusDesc;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
