package com.wishland.www.wanhaohui2.view.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umeng.analytics.MobclickAgent
import com.wishland.www.wanhaohui2.R
import com.wishland.www.wanhaohui2.base.BaseStyleActivity
import com.wishland.www.wanhaohui2.bean.MessageBean
import com.wishland.www.wanhaohui2.model.Model
import com.wishland.www.wanhaohui2.model.UserSP
import com.wishland.www.wanhaohui2.utils.NetUtil
import com.wishland.www.wanhaohui2.utils.ToastUtil
import com.wishland.www.wanhaohui2.view.adapter.MyMessageAdapter
import com.wishland.www.wanhaohui2.view.customlayout.refresh.RefreshFooterLayout
import kotlinx.android.synthetic.main.activity_message.*
import okhttp3.ResponseBody
import rx.Subscriber

class MessageListActivity : BaseStyleActivity() {

    val instance: Model by lazy { Model.getInstance() }
    val useSP: UserSP by lazy { instance.userSP }

    val adapter: MyMessageAdapter by lazy { MyMessageAdapter(this@MessageListActivity) }
    val list = arrayListOf<MessageBean.DataBean.DataListBean>()
    var queryCnt = 10
    var queryId = 0

    val footer: RefreshFooterLayout by lazy {
        RefreshFooterLayout(this).also { it ->
            it.setRefreshListener(refreshListener)
            it.setHasMore(true)
        }
    }

    override fun initVariable() {

    }

    override fun initDate() {
        title = "我的消息"
    }

    override fun initView() {
        setContentView(R.layout.activity_message, R.layout.base_toolbar_back)
        rv_my_message.layoutManager = LinearLayoutManager(this)
        adapter.addFooterView(footer)
        rv_my_message.adapter = adapter
        getMessageData()
    }

    private fun getMessageData() {
        val token = useSP.getToken(UserSP.LOGIN_TOKEN)
        val map = hashMapOf<String, String>("queryCnt" to queryCnt.toString(), "queryId" to queryId.toString())

//        map.put("queryCnt", queryCnt.toString())
//        map.put("queryId", queryId.toString())

        instance.apiMessage(queryCnt.toString(), queryId.toString(), token, NetUtil.getParamsPro(map).get("signature"), object : Subscriber<ResponseBody>() {
            override fun onCompleted() {

            }

            override fun onNext(t: ResponseBody?) {
                val string = t?.string()
                val json: MessageBean = Gson().fromJson<MessageBean>(string, MessageBean::class.java)
                if (json.status == 200) {
                    list.clear()
                    val data = json.data.dataList
                    if (data.size == 0) {
                        footer.setHasMore(false)
                    }
                    list.addAll(data)
                    adapter.setData(list)
                    footer.onLoadComplete()
                } else {
                    if (json.errorMsg.equals("用户未登录")) {
                        startActivity(Intent(this@MessageListActivity, LoginActivity::class.java))
                    } else {
                        ToastUtil.showShort(this@MessageListActivity, json.errorMsg)
                    }
                }
            }

            override fun onError(e: Throwable?) {

            }
        })


    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    private val refreshListener = RefreshFooterLayout.RefreshListener {
        //        rv_my_message.postDelayed({
        queryId += queryCnt + 1
        getMessageData()
//        }, 2000)

    }

}
