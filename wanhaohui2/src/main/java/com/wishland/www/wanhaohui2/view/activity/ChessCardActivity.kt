package com.wishland.www.wanhaohui2.view.activity

import butterknife.BindView
import android.view.View
import com.wishland.www.wanhaohui2.R
import com.wishland.www.wanhaohui2.base.BaseStyleActivity

/**
 * Created by admin on 2017/11/7.
 */
class ChessCardActivity : BaseStyleActivity() {
    lateinit var mView: View

    override fun initVariable() {

    }

    override fun initDate() {
//        title = "棋牌游戏"
    }

    override fun initView() {
        setContentView(R.layout.activity_chess_card, R.layout.base_toolbar_back)
        //只读map
        val mapORead = mapOf("a" to "a", "b" to 2)

        //可操作map
        var mapACan= mutableMapOf("a" to "a","b" to 2)
        mapACan.put("aa","cc")

        //只读list
        val listORead= listOf("a","b")

        //可操作list
        var listACan= mutableListOf("a",1)
        listACan.add("a")

    }
}