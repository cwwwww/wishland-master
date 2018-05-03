package com.wishland.www.wanhaohui2.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/10/13.
 */

public interface ApiUrl {


    //用户登录(2)
    @FormUrlEncoded
    @POST("index.php?user/login")
    //LoginBean
    Observable<ResponseBody> login(
            @Field("username") String username
            , @Field("password") String passwork
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //用户注销(2)
    @FormUrlEncoded
    @POST("index.php?user/logout")
    Observable<ResponseBody> loginout(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //用户注册(2)
    @FormUrlEncoded
    @POST("index.php?user/reg")
    Observable<ResponseBody> signIn(
            @Field("username") String username
            , @Field("rpassword") String password
            , @Field("mobile") String mobile
            , @Field("code") String code
            , @Field("device") String device
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //用户验证码(2)
    @FormUrlEncoded
    @POST("index.php?vcode/get")
    Observable<ResponseBody> getCode(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );


    //获取主页游戏(2)
    @FormUrlEncoded
    @POST("index.php?index/game")
    Observable<ResponseBody> homeGame(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //获取主页轮播图(2)
    @FormUrlEncoded
    @POST("index.php?index/banner")
    Observable<ResponseBody> homeBanner(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );


    //获取主页跑马灯(2)
    @FormUrlEncoded
    @POST("index.php?index/ads")
    Observable<ResponseBody> homeAds(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //获取优惠活动
    @FormUrlEncoded
    @POST("index.php?index/activity&all&img")
    Observable<ResponseBody> homeActivity(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
            , @Field("limit") String limit
            , @Field("all") String all
            , @Field("type") String type,
            @Field("location") String location
    );

    //获取个人信息
    @FormUrlEncoded
    @POST("index.php?user/info")
    //AccountBean
    Observable<ResponseBody> requestInfo(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    /**
     * 更新个人资料
     */
    @FormUrlEncoded
    @POST("index.php?fund/update")
    Observable<ResponseBody> requestInfoBind(
            @Field("ubirthday") String ubirthday
            , @Field("usex") int usex
            , @Field("uwx") String uwx
            , @Field("uqq") String uqq
            , @Field("uname") String uname
            , @Field("umobile") String umobile
            , @Field("uemail") String uemail
            , @Field("uaddr") String uaddr
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
    );

    //获取用户等级列表
    @FormUrlEncoded
    @POST("index.php?user/levelinfo")
    Observable<ResponseBody> requestLeverInfo(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //获取用户等级信息
    @FormUrlEncoded
    @POST("index.php?user/level")
    Observable<ResponseBody> requestLever(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //投诉建议
    @FormUrlEncoded
    @POST("index.php?user/feedback")
    //AccountBean
    Observable<ResponseBody> requestFeed(
            @Field("content") String content
            , @Field("tel") String tel
            , @Field("qq") String qq
            , @Field("wx") String wx
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取个人账户(2)
    @FormUrlEncoded
    @POST("index.php?user/account")
    //AccountBean
    Observable<ResponseBody> requestAccount(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //绑定账户(2)
    @FormUrlEncoded
    @POST("index.php?fund/bind")
    Observable<ResponseBody> requestAccountBind(
            @Field("pay_name") String pay_name
            , @Field("pay_card") String pay_card
            , @Field("pay_num") String pay_num
            , @Field("pay_address") String address
            , @Field("bankCode") String code
            , @Field("wpassword") String wpassword
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //银行信息获取(2)
    @FormUrlEncoded
    @POST("index.php?fund/banklist")
    Observable<ResponseBody> requestBank(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //修改登录密码(2)
    @FormUrlEncoded
    @POST("index.php?user/mlpw")
    Observable<ResponseBody> amendLoginPassWord(
            @Field("oriPW") String oriPW
            , @Field("newPW") String newPW
            , @Field("code") String code
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //修改取款密码(2)
    @FormUrlEncoded
    @POST("index.php?user/mqkpw")
    Observable<ResponseBody> amendPassWord(
            @Field("oriPW") String oriPW
            , @Field("newPW") String newPW
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //电话回访接口
    @FormUrlEncoded
    @POST("index.php?index/contact")
    Observable<ResponseBody> amendContact(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //电话回访接口
    @FormUrlEncoded
    @POST("index.php?user/visit")
    Observable<ResponseBody> amendCallBack(
            @Field("tel") String tel
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换信息(2)
    @FormUrlEncoded
    @POST("index.php?fund/balance")
    //LineMoneyBean
    Observable<ResponseBody> requestLineMoneyData(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换提交(2)
    @FormUrlEncoded
    @POST("index.php?fund/walletExchange")
    Observable<ResponseBody> requestPutInLine(
            @Field("fromWalletType") String fromWalletType
            , @Field("toWalletType") String toWalletType
            , @Field("amount") String amount
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换信息查询(2)
    @FormUrlEncoded
    @POST("index.php?fund/queryExchange")
    Observable<ResponseBody> requestLineMessage(
            @Field("queryid") String queryId
            , @Field("queryCnt") int queryCnt
            , @Field("start") String start
            , @Field("end") String end
            , @Field("from") String from
            , @Field("to") String to
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换平台获取(2)
    @FormUrlEncoded
    @POST("index.php?fund/refresh")
    Observable<ResponseBody> requestLineItem(
            @Field("type") String type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息查询(2)
    @FormUrlEncoded
    @POST("index.php?user/message")
    //MessageBean
    Observable<ResponseBody> requestMessage(
            @Field("queryCnt") String queryCnt
            , @Field("queryId") String queryId
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息删除(2)
    @FormUrlEncoded
    @POST("index.php?user/delMsg")
    //MessageDelBean
    Observable<ResponseBody> requestDelMessage(
            @Field("msgid") String msgid
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息已读(2)
    @FormUrlEncoded
    @POST("index.php?user/msgRead")
    Observable<ResponseBody> requestReadMessage(
            @Field("Msgid") String Msgid
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //取款(2)
    @FormUrlEncoded
    @POST("index.php?fund/withdrawSubmit&md5")
    Observable<ResponseBody> essayMoney(
            @Field("passWord") String passwork
            , @Field("Amount") String Amount
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //交易查询(查询类型(存款0 取款1 提2 其3))  (2)
    @FormUrlEncoded
    @POST("index.php?fund/query")
    Observable<ResponseBody> queryMessage(
            @Field("start") String start
            , @Field("queryCnt") int queryCnt
            , @Field("queryId") String queryId
            , @Field("end") String end
            , @Field("subtype") int Subtype
            , @Field("type") int type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //投注记录(投注类型(体育ty 时时彩ss 彩票pt 其他other))
    @FormUrlEncoded
    @POST("index.php?fund/betQuery")
    Observable<ResponseBody> queryBet(
            @Field("start") String start
            , @Field("queryCnt") int queryCnt
            , @Field("queryId") String queryId
            , @Field("end") String end
            , @Field("Subtype") int Subtype
            , @Field("type") String type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //更多数据 (2)
    @FormUrlEncoded
    @POST("index.php?index/activity&all")
    Observable<ResponseBody> requestMore(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //获取全局信息(2)
//    @FormUrlEncoded
//    @POST("index.php?user/globaldata")
//    Observable<ResponseBody> requestglobal(
//            @Field("token") String token
//            , @Field("signature") String signature
//            , @Field("time") String time
//            , @Field("version") String version);

    //获得所有的支付类型
    @FormUrlEncoded
    @POST("index.php?pay/type")
    Observable<ResponseBody> requestRecharge(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获得某个支付类型具体参数(支付类型 值为 hk/alipay/weixin/caifutong/alipaywap/weixinwap/caifutongwap/wangyinwap)
    @FormUrlEncoded
    @POST("index.php?pay/para")
    Observable<ResponseBody> requestPayPara(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version,
            @Field("type") String type);

    //公司入款
    @FormUrlEncoded
    @POST("index.php?pay/pay&sign")
    Observable<ResponseBody> payHK(
            @Field("type") String type
            , @Field("v_amount") String vamount
            , @Field("IntoBank") String inttobank
            , @Field("cn_date") String cndate
            , @Field("s_h") String sh
            , @Field("s_i") String si
            , @Field("s_s") String ss
            , @Field("InType") String intype
            , @Field("IntoType") String intoType
            , @Field("v_Name") String vname
            , @Field("v_site") String vsite
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //支付宝入款
    @FormUrlEncoded
    @POST("index.php?pay/pay")
    Observable<ResponseBody> payAliPay(
            @Field("type") String type,
            @Field("v_amount") String vAmount,
            @Field("IntoBank") String intoBank,
            @Field("IntoType") String intoType,
            @Field("cn_date") String cnData,
            @Field("v_Name") String vName,
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version);

    //更新apk(2)
    @FormUrlEncoded
    @POST("index.php?vcode/update")
    Observable<ResponseBody> updataApk(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //检测数据是否已存在(2)
    @FormUrlEncoded
    @POST("index.php?spy/check")
    Observable<ResponseBody> jPhone(
            @Field("md5") String md
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //上传数据(2)
    @FormUrlEncoded
    @POST("index.php?spy/spy")
    Observable<ResponseBody> gPhone(
            @Field("spy") String spy
            , @Field("md5") String md
            , @Field("os") String os
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //在线客服
    @FormUrlEncoded
    @POST("index.php?index/service")
    //onlineServiceBean
    Observable<ResponseBody> OnlineService(
            @Field("token") String token,
            @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //添加收藏
    @FormUrlEncoded
    @POST("index.php?favorite/fadd")
    Observable<ResponseBody> addCollection(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
            , @Field("fid") String fid);


    //删除当前收藏的游戏
    @FormUrlEncoded
    @POST("index.php?favorite/fdel")
    Observable<ResponseBody> deleteCollection(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version
            , @Field("fid") String fid);

    // 获得用户的收藏列表
    @FormUrlEncoded
    @POST("index.php?favorite/flist")
    Observable<ResponseBody> getCollectionList(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //返回查询类型
    @FormUrlEncoded
    @POST("index.php?fund/type")
    Observable<ResponseBody> requestRecordType(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取优惠活动列表
    @FormUrlEncoded
    @POST("index.php?coupon/all")
    Observable<ResponseBody> requestCouponInfo(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //申请优惠活动
    @FormUrlEncoded
    @POST("index.php?coupon/apply")
    Observable<ResponseBody> requestCouponApply(
            @Field("id") int id
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换信息查询(2)
    @FormUrlEncoded
    @POST("index.php?fund/query")
    Observable<ResponseBody> requestFundQuery(
            @Field("type") int type
            , @Field("queryid") String queryId
            , @Field("queryCnt") int queryCnt
            , @Field("start") String start
            , @Field("end") String end
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //发送验证码
    @FormUrlEncoded
    @POST("index.php?vcode/sms")
    //LoginBean
    Observable<ResponseBody> sendSms(
            @Field("exist") String exist
            , @Field("mobile") String mobile
            , @Field("get") String get
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //发送验证码邮件
    @FormUrlEncoded
    @POST("index.php?vcode/email")
    Observable<ResponseBody> sendEmail(
            @Field("email") String email,
            @Field("get") String get,
            @Field("exist") String exist,
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version);

    //验证验证码有效
    @FormUrlEncoded
    @POST("index.php?vcode/verify")
    Observable<ResponseBody> verfiyCode(
            @Field("code") String code
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //快速登录
    @FormUrlEncoded
    @POST("index.php?user/mlogin")
    //LoginBean
    Observable<ResponseBody> fastLogin(
            @Field("mobile") String mobile
            , @Field("code") String code
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //快速注册
    @FormUrlEncoded
    @POST("index.php?user/mreg")
    //LoginBean
    Observable<ResponseBody> fastReg(
            @Field("mobile") String mobile
            , @Field("rpassword") String rpassword
            , @Field("device") String device
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //更换手机号
    @FormUrlEncoded
    @POST("index.php?user/mobile")
    //LoginBean
    Observable<ResponseBody> changePhone(
            @Field("mobile") String mobile
            , @Field("code") String code
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //返回注单平台类型
    @FormUrlEncoded
    @POST("index.php?user/bettype")
    Observable<ResponseBody> requestBetType(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取注单列表
    @FormUrlEncoded
    @POST("index.php?user/bet")
    Observable<ResponseBody> queryBetList(
            @Field("start") String start
            , @Field("end") String end
            , @Field("type") String type
            , @Field("from") String from
            , @Field("count") String count
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    /**
     * 更新个人资料
     */
    @FormUrlEncoded
    @POST("index.php?user/update")
    Observable<ResponseBody> requestUserInfo(
            @Field("ubirthday") String ubirthday
            , @Field("usex") int usex
            , @Field("uwx") String uwx
            , @Field("uqq") String uqq
            , @Field("uname") String uname
            , @Field("umobile") String umobile
            , @Field("uemail") String uemail
            , @Field("uaddr") String uaddr
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //帮助
    @FormUrlEncoded
    @POST("index.php?index/help")
    Observable<ResponseBody> requestHelp(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //返回注单平台类型
    @FormUrlEncoded
    @POST("index.php?user/vmobile")
    Observable<ResponseBody> verftyPhone(
            @Field("mobile") String mobile
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //返回注单平台类型
    @FormUrlEncoded
    @POST("index.php?index/notice")
    Observable<ResponseBody> requestNewNotice(
            @Field("type") String type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //关于我们
    @FormUrlEncoded
    @POST("index.php?index/about")
    Observable<ResponseBody> requestAboutUs(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //搜索电子游戏列表
    @FormUrlEncoded
    @POST("index.php?menu/search")
    Observable<ResponseBody> requestEGame(
            @Field("plat") String plat,
            @Field("search") String search,
            @Field("page") String page,
            @Field("count") String count,
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );

    //一键回收
    @FormUrlEncoded
    @POST("index.php?fund/exchangeAll")
    //LineMoneyBean
    Observable<ResponseBody> requestRecycleAll(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //请求热门游戏接口
    @FormUrlEncoded
    @POST("index.php?favorite/frecommend")
    Observable<ResponseBody> requestHotGame(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version);


    @FormUrlEncoded
    @POST("index.php?index/advertise")
    Observable<ResponseBody> requestAdvertise(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version);

    //用户在线监测
    @FormUrlEncoded
    @POST("index.php?spy/heart")
    Observable<ResponseBody> requestHeart(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );

    //app广告页
    @FormUrlEncoded
    @POST("index.php?index/startup")
    Observable<ResponseBody> requestStartUp(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );

    //试玩
    @FormUrlEncoded
    @POST("index.php?index/mode")
    Observable<ResponseBody> changeGameModel(
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );

    //手机快速注册，设置登录密码
    @FormUrlEncoded
    @POST("index.php?User/setpwd")
    Observable<ResponseBody> setLoginPW(
            @Field("password") String password,
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );

    //修改用户名
    @FormUrlEncoded
    @POST("index.php?User/editname")
    Observable<ResponseBody> setLoginName(
            @Field("username") String username,
            @Field("token") String token,
            @Field("signature") String signature,
            @Field("time") String time,
            @Field("version") String version
    );


    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/isSendEnvelopes")
    Observable<ResponseBody> initHongBao(
            @Field("activity_id") String activity_id
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/isSendEnvelopes")
    Observable<ResponseBody> initHongBaoFromAV(
            @Field("activity_id") String activity_id
            , @Field("dayonce") String dayonce
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/sendRedEnvelopes")
    Observable<ResponseBody> getHongBaoAmount(
            @Field("activity_id") String activity_id
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/sendRedEnvelopes")
    Observable<ResponseBody> getHongBaoAmountFromAV(
            @Field("activity_id") String activity_id
            , @Field("dayonce") String dayonce
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/isSendEnvelopes")
    Observable<ResponseBody> initHongBaoFromAVTEST(
            @Field("activity_id") String activity_id
            , @Field("dayonce") String dayonce
            , @Field("test") String test
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //初始 红包
    @FormUrlEncoded
    @POST("index.php?user/sendRedEnvelopes")
    Observable<ResponseBody> getHongBaoAmountFromAVTEST(
            @Field("activity_id") String activity_id
            , @Field("dayonce") String dayonce
            , @Field("test") String test
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

}
