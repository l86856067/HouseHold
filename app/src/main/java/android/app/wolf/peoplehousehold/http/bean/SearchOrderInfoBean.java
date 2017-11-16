package android.app.wolf.peoplehousehold.http.bean;

import java.util.List;

/**
 * Created by lh on 2017/10/26.
 * <p>
 * 功能作用：
 * <p>
 * 修改记录：
 */
public class SearchOrderInfoBean {


    /**
     * pageNum : 1
     * pageSize : 1
     * rows : [{"address":"","admin":"","applyTime":"","checker":"","contractNumber":"","createTime":"","custEmplIds":"","custEmplNum":1,"custId":1,"custName":"","doneTime":"","dr":1,"endTime":"","id":14,"isAcceptAgreement":1,"lat":2.23,"lng":23.23,"loginTele":"","modifyTime":"","money":2.22,"operateTime":"2017-10-23 10:15:12","orderContactName":"","orderContactTel":"","orderStatus":2,"orderStatusDesc":"","payMoney":3.21,"payTime":"","realname":"","serviceBail":4,"serviceDesc":"","serviceEvaluation":"","serviceItemId":2,"serviceName":"擦玻璃","serviceNotifyNum":1,"serviceScore":2,"serviceStarttime":"","serviceTime":"","serviceType":1,"submitTime":"","tele":"","userAddress":"","userIcon":"","userId":2,"username":""}]
     * total : 5
     */

    private int pageNum;
    private int pageSize;
    private int total;
    private List<RowsBean> rows;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * address :
         * admin :
         * applyTime :
         * checker :
         * contractNumber :
         * createTime :
         * custEmplIds :
         * custEmplNum : 1
         * custId : 1
         * custName :
         * doneTime :
         * dr : 1
         * endTime :
         * id : 14
         * isAcceptAgreement : 1
         * lat : 2.23
         * lng : 23.23
         * loginTele :
         * modifyTime :
         * money : 2.22
         * operateTime : 2017-10-23 10:15:12
         * orderContactName :
         * orderContactTel :
         * orderStatus : 2
         * orderStatusDesc :
         * payMoney : 3.21
         * payTime :
         * realname :
         * serviceBail : 4
         * serviceDesc :
         * serviceEvaluation :
         * serviceItemId : 2
         * serviceName : 擦玻璃
         * serviceNotifyNum : 1
         * serviceScore : 2
         * serviceStarttime :
         * serviceTime :
         * serviceType : 1
         * submitTime :
         * tele :
         * userAddress :
         * userIcon :
         * userId : 2
         * username :
         */

        private String address;
        private String admin;
        private String applyTime;
        private String checker;
        private String contractNumber;
        private String createTime;
        private String custEmplIds;
        private int custEmplNum;
        private int custId;
        private String custName;
        private String doneTime;
        private int dr;
        private String endTime;
        private int id;
        private int isAcceptAgreement;
        private double lat;
        private double lng;
        private String loginTele;
        private String modifyTime;
        private float money;
        private String operateTime;
        private String orderContactName;
        private String orderContactTel;
        private int orderStatus;
        private String orderStatusDesc;
        private float payMoney;
        private String payTime;
        private String realname;
        private int serviceBail;
        private String serviceDesc;
        private String serviceEvaluation;
        private int serviceItemId;
        private String serviceName;
        private int serviceNotifyNum;
        private int serviceScore;
        private String serviceStarttime;
        private String serviceTime;
        private int serviceType;
        private String submitTime;
        private String tele;
        private String userAddress;
        private String userIcon;
        private int userId;
        private String username;

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

        public String getApplyTime() {
            return applyTime;
        }

        public void setApplyTime(String applyTime) {
            this.applyTime = applyTime;
        }

        public String getChecker() {
            return checker;
        }

        public void setChecker(String checker) {
            this.checker = checker;
        }

        public String getContractNumber() {
            return contractNumber;
        }

        public void setContractNumber(String contractNumber) {
            this.contractNumber = contractNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCustEmplIds() {
            return custEmplIds;
        }

        public void setCustEmplIds(String custEmplIds) {
            this.custEmplIds = custEmplIds;
        }

        public int getCustEmplNum() {
            return custEmplNum;
        }

        public void setCustEmplNum(int custEmplNum) {
            this.custEmplNum = custEmplNum;
        }

        public int getCustId() {
            return custId;
        }

        public void setCustId(int custId) {
            this.custId = custId;
        }

        public String getCustName() {
            return custName;
        }

        public void setCustName(String custName) {
            this.custName = custName;
        }

        public String getDoneTime() {
            return doneTime;
        }

        public void setDoneTime(String doneTime) {
            this.doneTime = doneTime;
        }

        public int getDr() {
            return dr;
        }

        public void setDr(int dr) {
            this.dr = dr;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsAcceptAgreement() {
            return isAcceptAgreement;
        }

        public void setIsAcceptAgreement(int isAcceptAgreement) {
            this.isAcceptAgreement = isAcceptAgreement;
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

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public float getMoney() {
            return money;
        }

        public void setMoney(float money) {
            this.money = money;
        }

        public String getOperateTime() {
            return operateTime;
        }

        public void setOperateTime(String operateTime) {
            this.operateTime = operateTime;
        }

        public String getOrderContactName() {
            return orderContactName;
        }

        public void setOrderContactName(String orderContactName) {
            this.orderContactName = orderContactName;
        }

        public String getOrderContactTel() {
            return orderContactTel;
        }

        public void setOrderContactTel(String orderContactTel) {
            this.orderContactTel = orderContactTel;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusDesc() {
            return orderStatusDesc;
        }

        public void setOrderStatusDesc(String orderStatusDesc) {
            this.orderStatusDesc = orderStatusDesc;
        }

        public float getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(float payMoney) {
            this.payMoney = payMoney;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getServiceBail() {
            return serviceBail;
        }

        public void setServiceBail(int serviceBail) {
            this.serviceBail = serviceBail;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceEvaluation() {
            return serviceEvaluation;
        }

        public void setServiceEvaluation(String serviceEvaluation) {
            this.serviceEvaluation = serviceEvaluation;
        }

        public int getServiceItemId() {
            return serviceItemId;
        }

        public void setServiceItemId(int serviceItemId) {
            this.serviceItemId = serviceItemId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public int getServiceNotifyNum() {
            return serviceNotifyNum;
        }

        public void setServiceNotifyNum(int serviceNotifyNum) {
            this.serviceNotifyNum = serviceNotifyNum;
        }

        public int getServiceScore() {
            return serviceScore;
        }

        public void setServiceScore(int serviceScore) {
            this.serviceScore = serviceScore;
        }

        public String getServiceStarttime() {
            return serviceStarttime;
        }

        public void setServiceStarttime(String serviceStarttime) {
            this.serviceStarttime = serviceStarttime;
        }

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public String getSubmitTime() {
            return submitTime;
        }

        public void setSubmitTime(String submitTime) {
            this.submitTime = submitTime;
        }

        public String getTele() {
            return tele;
        }

        public void setTele(String tele) {
            this.tele = tele;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
