package android.app.wolf.peoplehousehold.http.bean;

/**
 * Created by lh on 2017/10/25.
 * <p/>
 * 功能作用：
 * <p/>
 * 修改记录：
 */
public class LoginResultBean {


    /**
     * data : {"address":"上海市浦东新区芳华路","admin":"","age":1,"availMoney":1.1,"childrenNumber":null,"createTime":"2017-10-25 14:39:47","creditLevel":"","dr":0,"email":"","emergencyContact1":"","emergencyTele1":"","id":5,"idcardNumber":"","lastLoginTime":"","lat":31.202263,"lng":121.56481,"loginTele":"18710430607","maritalStatus":1,"modifyTime":"2017-10-28 16:07:10","password":"c4ca4238a0b923820dcc509a6f75849b","qq":"","realname":"","regSource":1,"regTime":"","sex":1,"userDesc":"","userIcon":"","userScore":"","userStatus":1,"username":"进荣哎","weixinNumber":""}
     * statusCode : 200
     * statusDesc : 登录成功
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
         * address : 上海市浦东新区芳华路
         * admin :
         * age : 1
         * availMoney : 1.1
         * childrenNumber : null
         * createTime : 2017-10-25 14:39:47
         * creditLevel :
         * dr : 0
         * email :
         * emergencyContact1 :
         * emergencyTele1 :
         * id : 5
         * idcardNumber :
         * lastLoginTime :
         * lat : 31.202263
         * lng : 121.56481
         * loginTele : 18710430607
         * maritalStatus : 1
         * modifyTime : 2017-10-28 16:07:10
         * password : c4ca4238a0b923820dcc509a6f75849b
         * qq :
         * realname :
         * regSource : 1
         * regTime :
         * sex : 1
         * userDesc :
         * userIcon :
         * userScore :
         * userStatus : 1
         * username : 进荣哎
         * weixinNumber :
         */

        private String address;
        private String admin;
        private int age;
        private float availMoney;
        private Object childrenNumber;
        private String createTime;
        private String creditLevel;
        private int dr;
        private String email;
        private String emergencyContact1;
        private String emergencyTele1;
        private int id;
        private String idcardNumber;
        private String lastLoginTime;
        private double lat;
        private double lng;
        private String loginTele;
        private int maritalStatus;
        private String modifyTime;
        private String password;
        private String qq;
        private String realname;
        private int regSource;
        private String regTime;
        private int sex;
        private String userDesc;
        private String userIcon;
        private String userScore;
        private int userStatus;
        private String username;
        private String weixinNumber;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAdmin() {
            return admin;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public float getAvailMoney() {
            return availMoney;
        }

        public void setAvailMoney(float availMoney) {
            this.availMoney = availMoney;
        }

        public Object getChildrenNumber() {
            return childrenNumber;
        }

        public void setChildrenNumber(Object childrenNumber) {
            this.childrenNumber = childrenNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreditLevel() {
            return creditLevel;
        }

        public void setCreditLevel(String creditLevel) {
            this.creditLevel = creditLevel;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEmergencyContact1() {
            return emergencyContact1;
        }

        public void setEmergencyContact1(String emergencyContact1) {
            this.emergencyContact1 = emergencyContact1;
        }

        public String getEmergencyTele1() {
            return emergencyTele1;
        }

        public void setEmergencyTele1(String emergencyTele1) {
            this.emergencyTele1 = emergencyTele1;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIdcardNumber() {
            return idcardNumber;
        }

        public void setIdcardNumber(String idcardNumber) {
            this.idcardNumber = idcardNumber;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getLoginTele() {
            return loginTele;
        }

        public void setLoginTele(String loginTele) {
            this.loginTele = loginTele;
        }

        public int getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(int maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getRegSource() {
            return regSource;
        }

        public void setRegSource(int regSource) {
            this.regSource = regSource;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getUserDesc() {
            return userDesc;
        }

        public void setUserDesc(String userDesc) {
            this.userDesc = userDesc;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserScore() {
            return userScore;
        }

        public void setUserScore(String userScore) {
            this.userScore = userScore;
        }

        public int getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(int userStatus) {
            this.userStatus = userStatus;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getWeixinNumber() {
            return weixinNumber;
        }

        public void setWeixinNumber(String weixinNumber) {
            this.weixinNumber = weixinNumber;
        }
    }
}
