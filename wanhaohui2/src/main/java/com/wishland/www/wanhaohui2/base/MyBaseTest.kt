package com.wishland.www.wanhaohui2.base

/**
 * Created by admin on 2017/11/3.
 */
class MyActivity : BaseStyleActivity() {
    private val items = listOf("apple", "banana", "kiwi")

    override fun initVariable() {
        val a = 3
        items.forEach { print(it) }
        items.forEachIndexed { index, s -> items[index] }
        for (index in items.indices) {
            print(items[index])
        }
    }

    override fun initDate() {
        val b = 4
        maxOf(1, 4)
    }

    override fun initView() {
        val c = 4

    }

    private fun maxOf(a: Int, b: Int) = if (a > b) a else b


}