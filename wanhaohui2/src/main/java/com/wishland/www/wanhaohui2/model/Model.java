package com.wishland.www.wanhaohui2.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.wishland.www.wanhaohui2.api.ApiUrl;
import com.wishland.www.wanhaohui2.api.BaseApi;
import com.wishland.www.wanhaohui2.base.ActivityManager;
import com.wishland.www.wanhaohui2.base.MyApplication;
import com.wishland.www.wanhaohui2.utils.LogUtil;
import com.wishland.www.wanhaohui2.utils.TimeUtil;
import com.wishland.www.wanhaohui2.utils.ToastUtil;
import com.wishland.www.wanhaohui2.view.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/10/15.
 */

public class Model {
    private static Model model;
    private Gson gson = new Gson();
    public static ApiUrl mObservable;

    /**
     * @return 返回Model实例
     */
    public static Model getInstance() {

        if (model == null) {
            synchronized (Model.class) {
                if (model == null) {
                    model = new Model();
                }
            }
        }
        return model;
    }

    //创建一个缓存线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(12);

    /**
     * @return 提供公共的访问方法
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    public JSONObject getJsonObject(String json) throws JSONException {
        return new JSONObject(json);
    }

    public Gson getGson() {
        return gson;
    }

    public AlertDialog.Builder getAlertDialog(Context context) {
        return new AlertDialog.Builder(context);
    }

    /**
     * @return 返回登录信息
     */
    public UserSP getUserSP() {
        return UserSP.getSPInstance();
    }

    /**
     * @return 返回个人信息
     */
    public AccountDataSP getAccountDataSP() {
        return AccountDataSP.getSPInstance();
    }

    public void setToken_SP(String token) {
        if (!token.isEmpty()) {
            getUserSP().setToken(UserSP.LOGIN_TOKEN, token);
        }

    }


    public void skipLoginActivity(Activity activity, Class zlass, String debug) {
        //ToastUtil.showLong(MyApplication.baseContext, "用户登录超时，请重新登录！");
        Log.e("cww", debug + "come from");
        if (getUserSP().getString(UserSP.LOGIN_USERNAME) != null && !"".equals(getUserSP().getString(UserSP.LOGIN_USERNAME)) && !getUserSP().getLoginOut(UserSP.Login_OUT)) {
            ToastUtil.showShort(MyApplication.baseContext, "用户登录超时，请重新登录！");
        } else {
            ToastUtil.showShort(MyApplication.baseContext, "请先登录！");
        }
        getUserSP().setSuccess(UserSP.LOGIN_SUCCESS, -1);
        Intent intent = new Intent(activity, zlass);
        activity.startActivityForResult(intent, 2);


    }

    /**
     * 进入浏览器
     */
    public void toBrowser(Context activity) {
        String string = getUserSP().getString(UserSP.LOGIN_SITE);
        if (!string.isEmpty()) {
            LogUtil.e("进入浏览器");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(string);
            intent.setData(content_url);
            activity.startActivity(intent);
        }
    }


    /**
     * 登录页面请求
     */
    public void api(String username, String password, String token, String signtrue,
                    Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", Utils_time.getSeconds());
//        params.put("version", BastApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = mObservable.login(username, password, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(login, subscriber);
    }

    /**
     * 注销页面
     */
    public void apiLoginOut(String token, String signtrue,
                            Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .loginout(token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }

    /**
     * 注册页面
     */
    public void apiSignIn(String username, String password, String mobile, String code, String device, String token, String signtrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("rpassword", password);
//        params.put("realName", realName);
//        params.put("code", code);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/reg", params);
        Observable<ResponseBody> signin = mObservable
                .signIn(username, password, mobile, code, device, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(signin, subscriber);
    }

    /**
     * 注册验证码
     */
    public void apiCode(String token, String signature, final Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signature);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?vcode/get", params);
        Observable<ResponseBody> signin = mObservable
                .getCode(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(signin, subscriber);
    }


    /**
     * Home-banner页面请求
     */
    public void apiGame(String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/game", params);
        Observable<ResponseBody> observable1 = mObservable
                .homeGame(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * Home-game页面请求
     */
    public void apiBanner(String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/banner", params);
        Observable<ResponseBody> observable1 = mObservable
                .homeBanner(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * Home-跑马灯页面请求
     */
    public void apiAds(String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/ads", params);
        Observable<ResponseBody> observable1 = mObservable
                .homeAds(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * Home-优惠页面请求
     */
    public void apiActivity(String token, String signatrue, String limit, String all, String content, String location, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/activity", params);
        Observable<ResponseBody> observable1 = mObservable
                .homeActivity(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION, limit, all, content, location);
        setmObservable(observable1, subscriber);
    }

    /**
     * 个人页面数据
     */
    public void apiAccount(String token, String signatrue,
                           Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestAccount(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取用户等级信息
     */
    public void apiLever(String token, String signtrue,
                         Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .requestLever(token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }


    /**
     * 获取用户等级信息列表
     */
    public void apiLeverList(String token, String signtrue,
                             Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .requestLeverInfo(token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }

    /**
     * 投诉建议
     */
    public void apiFeed(String cotent, String phone, String qq, String wecat, String token, String signatrue,
                        Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestFeed(cotent, phone, qq, wecat, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取个人资料
     */
    public void apiInfo(String token, String signatrue,
                        Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestInfo(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 修改个人资料
     */
    public void apiChangeInfo(String ubirthday, int usex, String uwx, String uqq, String uname, String umobile, String uemail,
                              String uaddr, String token, String signatrue,
                              Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestInfoBind(ubirthday, usex, uwx, uqq, uname, umobile, uemail, uaddr, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }


    /**
     * 绑定账户
     */
    public void apiAccount_Bind(String pay_name, String pay_card, String pay_num,
                                String pay_address, String bankCode, String wpassword, String token, String signatrue,
                                Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("pay_name", pay_name);
//        params.put("pay_card", pay_card);
//        params.put("pay_num", pay_num);
//        params.put("pay_address", address);
//        params.put("bankCode", code);
//        params.put("wpassword", wpassword);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/bind", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestAccountBind(pay_name, pay_card, pay_num, pay_address, bankCode, wpassword, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取联系方式
     */
    public void apiContact(String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("oriPW", oriPW);
//        params.put("newPW", newPW);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/mlpw", params);
        Observable<ResponseBody> observable1 = mObservable
                .amendContact(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }


    /**
     * 电话回访
     */
    public void apiCallBack(String tel, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("oriPW", oriPW);
//        params.put("newPW", newPW);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/mlpw", params);
        Observable<ResponseBody> observable1 = mObservable
                .amendCallBack(tel, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 登录密码修改
     */
    public void apiAmendLogin(String oriPW, String newPW, String code,
                              String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("oriPW", oriPW);
//        params.put("newPW", newPW);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/mlpw", params);
        Observable<ResponseBody> observable1 = mObservable
                .amendLoginPassWord(oriPW, newPW, code, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 取款密码修改
     */
    public void apiAmend(String oriPW, String newPW,
                         String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("oriPW", oriPW);
//        params.put("newPW", newPW);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/mqkpw", params);
        Observable<ResponseBody> observable1 = mObservable
                .amendPassWord(oriPW, newPW, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取额度转换数据
     */
    public void apiLineMoneyData(String token, String signatrue,
                                 Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/balance", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestLineMoneyData(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取额度转换数据提交
     */
    public void apiLinePutIn(String fromWalletType, String toWalletType,
                             String amount, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("fromWalletType", fromWalletType);
//        params.put("toWalletType", toWalletType);
//        params.put("amount", amount);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/walletExchange", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestPutInLine(fromWalletType, toWalletType, amount, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);

    }

    /**
     * 获取转换信息查询
     */
    public void apiLine(String queryid, int queryCnt, String start, String end, String fromWalletType, String toWalletType,
                        String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("queryid", queryid);
//        params.put("queryCnt", queryCnt);
//        params.put("start", start);
//        params.put("end", end);
//        params.put("from", fromWalletType);
//        params.put("to", toWalletType);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/queryExchange", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestLineMessage(queryid, queryCnt, start, end, fromWalletType, toWalletType, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);

    }

    /**
     * 额度信息item抓取
     */
    public void apiItem(String type, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", type);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/refresh", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestLineItem(type, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);

    }

    /**
     * 消息查询
     */
    public void apiMessage(String queryCnt, String queryId,
                           String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("queryCnt", queryCnt);
//        params.put("queryId", queryId);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/message", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestMessage(queryCnt, queryId, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 消息删除
     */
    public void apiDelMessage(String msgid, String token,
                              String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("msgid", msgid);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/delMsg", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestDelMessage(msgid, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 消息已读
     */
    public void apiReadMessage(String msgid, String token,
                               String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("Msgid", msgid);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/msgRead", params);
        Observable<ResponseBody> obser = mObservable
                .requestReadMessage(msgid, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(obser, subscriber);
    }

    /**
     * 取款
     */
    public void apiEssayMoney(String password, String Amount, String token,
                              String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("passWord", password);
//        params.put("Amount", Amount);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/withdrawSubmit", params);
        Observable<ResponseBody> observable1 = mObservable.essayMoney(password, Amount, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);

    }


    /**
     * 交易查询
     */
    public void apiMessage(String start, int queryCnt, String queryId,
                           String end, int subtype, int type, String token,
                           String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("start", start);
//        params.put("queryCnt", queryCnt);
//        params.put("queryId", queryId);
//        params.put("end", end);
//        params.put("subtype", Subtype);
//        params.put("type", type);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/tradeQuery", params);
        Observable<ResponseBody> observable1 = mObservable
                .queryMessage(start, queryCnt, queryId, end, subtype, type, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 投注记录
     */
    public void apiBet(String start, int queryCnt, String queryId,
                       String end, int Subtype, String type, String token,
                       String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("start", start);
//        params.put("queryCnt", queryCnt);
//        params.put("queryId", queryId);
//        params.put("end", end);
//        params.put("Subtype", Subtype);
//        params.put("type", type);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/betQuery", params);
        Observable<ResponseBody> observable1 = mObservable.queryBet(start, queryCnt, queryId, end, Subtype, type, token
                , signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);

    }

    /**
     * 获取全局信息
     */
//    public void apiGlobal(String token, String signatrue,
//                          Subscriber<?> subscriber) {
////        Map<String, Object> params = new HashMap<>();
////        params.put("token", token);
////        params.put("signature", signatrue);
////        params.put("time", TimeUtil.getSeconds());
////        params.put("version", BaseApi.VERSION);
////        AppUtils.getInstance().onRequest("index.php?user/globaldata", params);
//        mObservable<ResponseBody> mObservable1 = mObservable
//                .requestglobal(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
//        setmObservable(mObservable1, subscriber);
//    }

    /**
     * 获取银行信息
     */
    public void apiBank(String token, String signatrue,
                        Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?fund/banklist", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestBank(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取存款路径银行信息
     */
    public void apiRecharge(String token, String signatrue,
                            Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?pay/type", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestRecharge(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获取存款支付方式
     */
    public void apiRequestPayPara(String token, String signature, String type, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestPayPara(token, signature, TimeUtil.getSeconds(),
                BaseApi.VERSION, type);
        setmObservable(observable, subscriber);
    }

    /**
     * 更多优惠
     */
    public void apiMore(String token, String signatrue,
                        Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/activity&all", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestMore(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 更新apk
     */
    public void updata(String token, String signatrue, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable1 = mObservable
                .updataApk(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 公司入款提交
     */
    public void apiPayHK(String pay_type, String money, String bank, String date, String shi, String fen,
                         String miao, String type, String intoType, String name, String address, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("type", type);
//        params.put("v_amount", money);
//        params.put("IntoBank", bank);
//        params.put("cn_date", date);
//        params.put("s_h", shi);
//        params.put("s_i", fen);
//        params.put("s_s", miao);
//        params.put("InType", type);
//        params.put("IntoType", othertype);
//        params.put("v_Name", name);
//        params.put("v_site", address);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?pay/pay", params);
        Observable<ResponseBody> observable1 = mObservable
                .payHK(pay_type, money, bank, date, shi, fen, miao, type, intoType, name, address, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 支付宝入款
     */
    public void apiPayAliPay(String type, String vAmount, String intoBank, String intoType, String cnDate, String vName, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable
                .payAliPay(type, vAmount, intoBank, intoType, cnDate, vName, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 判断是否上传过手机数据
     */
    public void jPhone(String md, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("md5", md);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?spy/check", params);
        Observable<ResponseBody> observable1 = mObservable
                .jPhone(md, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }


    /**
     * 上传过手机数据
     */
    public void gPhone(String spy, String md, String os, String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("spy", spy);
//        params.put("md5", md);
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?spy/spy", params);
        Observable<ResponseBody> observable1 = mObservable
                .gPhone(spy, md, os, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 在线客服
     */
    public void apiOnlineSerivce(String token, String signatrue, Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?index/service", params);
        Observable<ResponseBody> observable1 = mObservable
                .OnlineService(token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }


    /**
     * 获得用户的收藏列表
     */

    public void apiGetMindCollectionList(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.getCollectionList(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 添加收藏
     */
    public void apiAddCollection(String token, String signature, String fid, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.addCollection(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION, fid);
        setmObservable(observable, subscriber);
    }

    /**
     * 删除收藏
     */
    public void apiDeleteCollection(String token, String signature, String fid, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.deleteCollection(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION, fid);
        setmObservable(observable, subscriber);
    }

    private void setmObservable(Observable<ResponseBody> mObservable, Subscriber<?> subscriber) {
        mObservable.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Subscriber<? super ResponseBody>) subscriber);
    }

    /**
     * 获得查询类型
     */
    public void apiRecordType(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestRecordType(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获取优惠活动列表
     */
    public void apiCoupon(String token, String signtrue,
                          Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .requestCouponInfo(token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }

    /**
     * 申请优惠活动
     */
    public void apiCouponApply(int id, String token, String signtrue,
                               Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .requestCouponApply(id, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }

    /**
     * 交易查询
     */
    public void apiFundQuery(int type, String queryid, int queryCnt, String start, String end, String token, String signtrue,
                             Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = mObservable
                .requestFundQuery(type, queryid, queryCnt, start, end, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(loginout, subscriber);
    }

    /**
     * 发送验证短信
     */
    public void apiSms(String exist, String mobile, String get, String token, String signtrue,
                       Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", Utils_time.getSeconds());
//        params.put("version", BastApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = mObservable.sendSms(exist, mobile, get, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(login, subscriber);
    }

    /**
     * 发送验证邮件
     */
    public void apiEmail(String email, String get, String exist, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.sendEmail(email, get, exist, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 验证验证码是否有效
     */
    public void apiVerifyCode(String code, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.verfiyCode(code, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 快速登录
     */
    public void apiFastLogin(String mobile, String code, String token, String signtrue,
                             Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", Utils_time.getSeconds());
//        params.put("version", BastApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = mObservable.fastLogin(mobile, code, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(login, subscriber);
    }

    /**
     * 快速注册
     */
    public void apiFastReg(String mobile, String rpassword, String device, String token, String signture,
                           Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", Utils_time.getSeconds());
//        params.put("version", BastApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = mObservable.fastReg(mobile, rpassword, device, token, signture, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(login, subscriber);
    }


    /**
     * 更换手机号
     */
    public void apiChangePhone(String mobile, String code, String token, String signtrue,
                               Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("token", token);
//        params.put("signature", signtrue);
//        params.put("time", Utils_time.getSeconds());
//        params.put("version", BastApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = mObservable.changePhone(mobile, code, token, signtrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(login, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiBetType(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestBetType(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获取注单列表
     */
    public void apiQueryList(String start, String end, String type,
                             String from, String count, String token,
                             String signatrue, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable1 = mObservable
                .queryBetList(start, end, type, from, count, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 修改个人资料
     */
    public void apiChangeUserInfo(String ubirthday, int usex, String uwx, String uqq, String uname, String umobile, String uemail,
                                  String uaddr, String token, String signatrue,
                                  Subscriber<?> subscriber) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("token", token);
//        params.put("signature", signatrue);
//        params.put("time", TimeUtil.getSeconds());
//        params.put("version", BaseApi.VERSION);
//        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = mObservable
                .requestUserInfo(ubirthday, usex, uwx, uqq, uname, umobile, uemail, uaddr, token, signatrue, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable1, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiHelp(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestHelp(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiVerftyPhone(String mobile, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.verftyPhone(mobile, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiNewNotice(String type, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestNewNotice(type, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 关于我们
     */
    public void apiAboutUs(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestAboutUs(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }


    /**
     * 一键回收
     */
    public void apiRecycleAllMoney(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestRecycleAll(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 搜索电子游戏列表
     */
    public void apiRequestEGame(String plat, String search, String page, String count, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestEGame(plat, search, page, count, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 请求热门游戏
     */
    public void apiRequestHotGame(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestHotGame(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    public void apiRequestAdvertise(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestAdvertise(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 用户在线监测
     */
    public void apiHeartRequest(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestHeart(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * app的广告页
     */
    public void apiStartUp(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.requestStartUp(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }


    /**
     * 请求试玩游戏
     */
    public void apiChangeGameModel(String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.changeGameModel(token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 设置登录密码
     */
    public void apiSetLoginPW(String password, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.setLoginPW(password, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 设置用户名
     */
    public void apiSetLoginName(String username, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.setLoginName(username, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiInitHongBao(String activity_id, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.initHongBao(activity_id, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }


    /**
     * 检测女优红包
     */
    public void apiInitHongBaoFromAV(String activity_id, String anyString, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.initHongBaoFromAV(activity_id, anyString, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }

    /**
     * 获得注单平台类型
     */
    public void apiGetHongBao(String activity_id, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.getHongBaoAmount(activity_id, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }


    /**
     * 获得注单平台类型
     */
    public void apiGetHongBaoFromAV(String activity_id, String anyString, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.getHongBaoAmountFromAV(activity_id, anyString,token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }
    /**
     * 获得注单平台类型
     */
    public void apiInitHongBaoFromAVTEST(String activity_id, String anyString, String test, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.initHongBaoFromAVTEST(activity_id, anyString, test, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }
    /**
     * 获得注单平台类型
     */
    public void apiGetHongBaoFromAVTEST(String activity_id, String anyString, String test, String token, String signature, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable = mObservable.getHongBaoAmountFromAVTEST(activity_id, anyString, test, token, signature, TimeUtil.getSeconds(), BaseApi.VERSION);
        setmObservable(observable, subscriber);
    }




}
