package android.app.wolf.peoplehousehold.http.httpinterface;

import android.app.wolf.peoplehousehold.http.bean.AliPayBean;
import android.app.wolf.peoplehousehold.http.bean.FlowInfoBean;
import android.app.wolf.peoplehousehold.http.bean.LoginResultBean;
import android.app.wolf.peoplehousehold.http.bean.MoneyFlowBean;
import android.app.wolf.peoplehousehold.http.bean.OrderInfoBean;
import android.app.wolf.peoplehousehold.http.bean.PlaceOrderResultBean;
import android.app.wolf.peoplehousehold.http.bean.PlaceOtherOrderBean;
import android.app.wolf.peoplehousehold.http.bean.PlaceShortOrderResult;
import android.app.wolf.peoplehousehold.http.bean.RegisterCodeBean;
import android.app.wolf.peoplehousehold.http.bean.RegisterResultBean;
import android.app.wolf.peoplehousehold.http.bean.SearchOrderInfoBean;
import android.app.wolf.peoplehousehold.http.bean.ServiceingInfoBean;
import android.app.wolf.peoplehousehold.http.bean.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lh on 2017/10/16.
 * <p/>
 * 功能作用： Retrofit框架进行网络请求时的请求接口
 * <p/>
 * 修改记录：
 */
public interface HttpRequest {

    /*
    *  调用后台的支付宝加签请求
    *  id 订单id
    *  money 金额
    * */
    @POST("orderinfo/zfbEndOrderPay.do")
    @FormUrlEncoded
    Call<AliPayBean> toAliPay(@Field("id") int id,@Field("money") double money);


    /*
    *  获取用户信息的post请求
    *  id 用户id
    * */
    @POST("userbasicinfo/queryUserCenterInfo.do")
    @FormUrlEncoded
    Call<UserInfoBean> postUserIdtoGetUserInfo(@Field("id") int id);

    /*
    *  用户获取验证码的post请求
    *  tele 电话号码
    * */
    @POST("userbasicinfo/queryRegVerifyCode.do")
    @FormUrlEncoded
    Call<RegisterCodeBean> postTeletoGetRegisterCode(@Field("tele") String tele);

    /*
    *  用户注册的post请求
    *  loginTele 注册的手机号
    *  password 注册的密码
    *  verifyCode 注册的验证码
    * */
    @POST("userbasicinfo/register.do")
    @FormUrlEncoded
    Call<RegisterResultBean> postTeleandPasstoRegister(@Field("loginTele") String loginTele,@Field("password") String password,@Field("verifyCode") String verifyCode);

    /*
    *  用户登录的post请求
    *  loginTele 登录电话号码
    *  password 登录密码
    * */
    @POST("userbasicinfo/loginUser.do")
    @FormUrlEncoded
    Call<LoginResultBean> postTeleandPasstoLogin(@Field("loginTele") String loginTele,@Field("password") String password);

    /*
    *  获取用户服务状态的post请求
    *  id 用户id
    *  page 页数
    *  rows 每页个数
    * */
    @POST("orderinfo/queryUserOrderStatusList.do")
    @FormUrlEncoded
    Call<ServiceingInfoBean> postuserIdtogetServiceStatus(@Field("id") int id,@Field("page") int page,@Field("rows") int rows);

    /*
    *  用户反馈的post请求
    *  userId 用户id
    *  feedbackDesc 用户反馈的内容
    * */
    @POST("feedback/save.do")
    @FormUrlEncoded
    Call<RegisterResultBean> postIdandInfotoFeedback(@Field("userId") int userId,@Query("feedbackDesc") String feedbackDesc);


    /*==========================================================首页================================================================*/

    /*
    *  用户下短单的post请求
    *  userId 用户id
    *  serviceStarttime 服务开始时间
    *  serviceTime 服务持续时间
    *  money 服务金额
    *  custEmplNum 阿姨数量
    *  serviceItemId 服务项目id
    *  serviceDesc 备注
    *  orderContactName 订单联系人
    *  orderContactTel 订单联系人电话
    *  address 订单地址
    * */
    @POST("orderinfo/userPlaceShortOrder.do")
    @FormUrlEncoded
    Call<PlaceShortOrderResult> postInfotoPlaceOrder(@Field("userId") int userId, @Field("tempTime") String serviceStarttime,
                                                     @Field("serviceTime") int serviceTime, @Field("money") double money, @Field("custEmplNum") int custEmplNum,
                                                     @Field("serviceItemId") int serviceItemId, @Query("serviceDesc") String serviceDesc, @Query("orderContactName") String orderContactName,
                                                     @Field("orderContactTel") String orderContactTel, @Query("address") String address);


/*
    *  用户下开荒短单的post请求
    *  userId 用户id
    *  serviceStarttime 服务开始时间
    *  serviceTime 服务持续时间
    *  money 服务金额
    *  custEmplNum 阿姨数量
    *  serviceItemId 服务项目id
    *  serviceDesc 备注
    *  orderContactName 订单联系人
    *  orderContactTel 订单联系人电话
    *  address 订单地址
    * */
    @POST("orderinfo/userPlaceShortOrder.do")
    @FormUrlEncoded
    Call<PlaceOrderResultBean> postInfotoPlaceReclaimOrder(@Field("userId") int userId,@Field("tempTime") String serviceStarttime,
                                                    @Field("money") double money, @Field("serviceItemId") int serviceItemId,@Field("serviceParam") int serviceParam,
                                                           @Query("serviceDesc") String serviceDesc,@Query("orderContactName") String orderContactName,
                                                    @Field("orderContactTel") String orderContactTel,@Query("address") String address);

    /*
    *  下长单的post请求
    *  userId 用户id
    *  serviceItemId 服务项目id
    *  serviceNotifyNum 通知门店数量
    *  address 服务地址
    *  serviceDesc 备注
    *  orderContactName 订单联系人姓名
    *  orderContactTel 订单联系人电话
    * */
    @POST("orderinfo/userPlaceLongOrder.do")
    @FormUrlEncoded
    Call<PlaceOrderResultBean> postInfotoPlaceLongOrder(@Field("userId") int userId,@Field("serviceItemId") int serviceItemId,
                                                        @Field("serviceNotifyNum") int serviceNotifyNum,@Query("address") String address,@Query("serviceDesc") String serviceDesc,
                                                        @Query("orderContactName") String orderContactName,@Field("orderContactTel") String orderContactTel);


    /*
    *  用户下其他订单的post请求
    *  userId 用户id
    *  serviceDesc 用户订单需求
    * */
    @POST("orderinfo/userPlaceOrtherOrder.do")
    @FormUrlEncoded
    Call<PlaceOtherOrderBean> postIdandOtherInfotoPlace(@Field("userId") int userId,@Query("serviceDesc") String serviceDesc,@Field("serviceItemId") int serviceItemId);

    /*========================================================搜索订单=============================================================*/

    /*
    *  搜索历史订单的post请求
    *  id 用户id
    *  startdate 开始时间
    *  enddate 结束时间
    *  page 页数
    *  rows每页的个数
    * */
    @POST("orderinfo/queryUserOldOrder.do")
    @FormUrlEncoded
    Call<SearchOrderInfoBean> postIdtoGetHisOrderList(@Field("id")int id,@Field("startdate") String startdate,@Field("enddate") String enddate,@Field("page")int page,@Field("rows")int rows);

    /*
    *  获取订单详情的post请求
    *  oid 订单id
    * */
    @POST("orderinfo/orderInfodetail.do")
    @FormUrlEncoded
    Call<OrderInfoBean> postOrderIdtoGetOrderInfo(@Field("oid") int oid);

    /*
    *  完成订单的post请求
    *  id 订单id
    * */
    @POST("orderinfo/orderEnd.do")
    @FormUrlEncoded
    Call<RegisterResultBean> postIdtoEndOrder(@Field("id") int id);


    /*
    *  评价订单的post请求
    *  oid 订单id
    *  serviceScore 评价分数
    *  serviceEvaluation 评价
    * */
    @POST("orderinfo/serviceEvaluation.do")
    @FormUrlEncoded
    Call<RegisterResultBean> postIdandInfotoEvaluateOrder(@Field("oid") int oid,@Field("serviceScore") float serviceScore,@Query("serviceEvaluation") String serviceEvaluation);

















    /*
    *  用户修改个人信息的post请求
    *  id 用户id
    *  username 用户姓名
    *  address 用户地址
    * */
    @POST("userbasicinfo/updateUserInfo.do")
    @FormUrlEncoded
    Call<RegisterResultBean> postIdandInfotoUpdataUserInfo(@Field("id") int id,@Query("username") String username,@Query("address") String address);

    /*
    *  用户充值的请求
    *  money 充值金额
    *  uid 用户id
    * */
    @POST("orderinfo/zfbRecharge.do")
    @FormUrlEncoded
    Call<AliPayBean> postIdandMoneytoRecharge(@Field("money") double money,@Field("uid") int uid);

    /*
    *  获取用户资金流水的额请求
    *  uid 用户id
    *  page 页码
    *  rows每页个数
    * */
    @POST("cashflow/queryCashFlowPageList.do")
    @FormUrlEncoded
    Call<MoneyFlowBean> postIdtoUserMoneyFlowList(@Field("uid") int id,@Field("page") int page,@Field("rows") int rows);

}
