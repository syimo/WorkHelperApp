package com.king.app.workhelper.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.king.app.workhelper.R
import com.king.app.workhelper.constant.AnnotationSample
import com.king.app.workhelper.constant.GlobalConstant
import com.king.app.workhelper.model.entity.Student
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 *
 * @author VanceKing
 * @since 2018/3/28.
 */
class KotlinSampleActivity : AppCompatActivity(), View.OnClickListener {

    var student: Student ?= null
    var a: Int = 1
    var b = 2
    var c = "dog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        textView.text = "AAA"//使用扩展，直接赋值
        textView2.setOnClickListener { Log.i(GlobalConstant.LOG_TAG, "textView2 clicked") }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textView -> Log.i("aaa", student?.name ?: "name == null")
        }
    }

    //定义一个无返回值的方法
    private fun printMessage(message: String) {
        Log.i("aaa", message)
    }

    //定义一个 POJO。使用时直接使用 getters/setters，无需定义
    data class Custom(var name: String, var age: Int) {

        fun printText(text: String): Int {
            return 1
        }
    }

    //定义一个单例类，用"object"修饰
    object SingletonClass {
        var name: String = "Single Instance"
    }

    public fun onTextViewClick(textView: TextView) {

    }

    //定义一个返回 int 值的方法
    fun sum(first: Int, second: Int): Int {
        return first + second
    }

    fun testAnnotation() {
        val reqAnnotation = javaClass.getAnnotation(AnnotationSample.MethodInfo::class.java)
        reqAnnotation.author
    }
}