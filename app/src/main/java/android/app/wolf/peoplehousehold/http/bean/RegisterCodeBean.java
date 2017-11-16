package android.app.wolf.peoplehousehold.http.bean;

/**
 * Created by lh on 2017/10/25.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class RegisterCodeBean {


    /**
     * data : {"verificationCode":"1111"}
     * statusCode : 200
     * statusDesc : 验证码发送成功
     */

    private DataBean data;
    private String statusCode;
    private String statusDesc;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * verificationCode : 1111
         */

        private String verificationCode;

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }
    }
}
