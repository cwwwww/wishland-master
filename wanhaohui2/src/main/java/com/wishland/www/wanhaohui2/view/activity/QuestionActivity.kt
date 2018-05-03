package com.wishland.www.wanhaohui2.view.activity

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.umeng.analytics.MobclickAgent
import com.wishland.www.wanhaohui2.R
import com.wishland.www.wanhaohui2.base.BaseStyleActivity
import com.wishland.www.wanhaohui2.bean.QuestionBean
import com.wishland.www.wanhaohui2.model.Model
import com.wishland.www.wanhaohui2.model.UserSP
import com.wishland.www.wanhaohui2.utils.NetUtil
import com.wishland.www.wanhaohui2.utils.ToastUtil
import com.wishland.www.wanhaohui2.view.adapter.QuestionAdapter
import com.wishland.www.wanhaohui2.view.customlayout.refresh.RefreshFooterLayout
import kotlinx.android.synthetic.main.activity_question.*
import okhttp3.ResponseBody
import rx.Subscriber

class   QuestionActivity : BaseStyleActivity() {

    val adapter: QuestionAdapter by lazy { QuestionAdapter(this) }
    val footer: RefreshFooterLayout by lazy {
        RefreshFooterLayout(this).also { it ->
            it.setRefreshListener(refreshListener)
            it.setHasMore(false)
        }
    }
    val instance: Model by lazy { Model.getInstance() }
    val userSP: UserSP by lazy { instance.userSP }

    override fun initVariable() {

    }

    override fun initDate() {
        setTitle("常见问题")

    }

    override fun initView() {
        setContentView(R.layout.activity_question, R.layout.base_toolbar_back)
        tv_question.setOnClickListener {
            tv_question.setBackgroundResource(R.drawable.shape_query_two)
            tv_game_tz.setBackgroundResource(R.drawable.shape_query_one)
            tv_jishu.setBackgroundResource(R.drawable.shape_query_one)
            tv_question.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            tv_game_tz.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
            tv_jishu.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
        }
        tv_game_tz.setOnClickListener {
            tv_question.setBackgroundResource(R.drawable.shape_query_one)
            tv_game_tz.setBackgroundResource(R.drawable.shape_query_two)
            tv_jishu.setBackgroundResource(R.drawable.shape_query_one)
            tv_question.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
            tv_game_tz.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
            tv_jishu.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
        }
        tv_jishu.setOnClickListener {
            tv_question.setBackgroundResource(R.drawable.shape_query_one)
            tv_game_tz.setBackgroundResource(R.drawable.shape_query_one)
            tv_jishu.setBackgroundResource(R.drawable.shape_query_two)
            tv_question.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
            tv_game_tz.setTextColor(ContextCompat.getColor(applicationContext, R.color.text_color1))
            tv_jishu.setTextColor(ContextCompat.getColor(applicationContext, R.color.white))
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter.setData(getQuestionData())

        adapter.addFooterView(footer)
        recyclerView.adapter = adapter

    }

//    fun makeData(): List<QuestionBean.QuestionData> {
//        val list = arrayListOf<QuestionBean.QuestionData>()
//        for (i in 0..20) {
//            list.add(QuestionBean.QuestionData())
//        }
//        return list
//    }


    private val refreshListener = RefreshFooterLayout.RefreshListener {
        recyclerView.postDelayed({
            //            adapter.addData(getQuestionData())
            footer.onLoadComplete()
            footer.setHasMore(false)
        }, 2000)
    }

    private fun getQuestionData(): List<QuestionBean.QuestionData> {
        val token = userSP.getToken(UserSP.LOGIN_TOKEN)
        val list = arrayListOf<QuestionBean.QuestionData>()
        instance.apiHelp(token, NetUtil.getParamsPro().get("signature"), object : Subscriber<ResponseBody>() {
            override fun onCompleted() {

            }

            override fun onNext(t: ResponseBody?) {
                val string = t?.string()
                val json: QuestionBean = Gson().fromJson<QuestionBean>(string, QuestionBean::class.java)
                if (json.status == 200) {
                    val data = json.data
                    list.addAll(data)
                } else {
                    if (json.errorMsg.equals("用户未登录")) {
                        startActivity(Intent(this@QuestionActivity, LoginActivity::class.java))
                    } else {
                        ToastUtil.showShort(this@QuestionActivity, json.errorMsg)
                    }
                }
            }

            override fun onError(e: Throwable?) {

            }

        })
        return list
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

}