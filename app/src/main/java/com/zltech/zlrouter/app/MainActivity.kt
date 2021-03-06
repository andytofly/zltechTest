package com.zltech.zlrouter.app

//import com.zltech.zlrouter.libs.providers.module1.Module1Providers
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zltech.zlrouter.R
import com.zltech.zlrouter.annotation.Route
import com.zltech.zlrouter.inject.ZlRouter
import com.zltech.zlrouter.libs.BaseFragment2
import kotlinx.android.synthetic.main.activity_main.*


@Route(path = "/app/entry")
class MainActivity : AppCompatActivity() {
    //    private lateinit var module1Providers: Module1Providers
    companion object {
        const val TAG = "app_MainActivity "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * 跳转activity，附带参数,获取返回结果
         */
        button1.setOnClickListener {
            ZlRouter.getInstance().build("/module1/module1main").withContext(this)
                .withString("msg", "zwf是猪~~~~").call { result ->
                    Log.d("msg", "result!! " + result)
                }
        }

        /**
         * 跳转activity，
         */
        button2.setOnClickListener {
            ZlRouter.getInstance().build("/module2/module2main").call()
        }

        /**
         * 获取其他模块的fragment
         */
        buttonFragment.setOnClickListener {
            val result = ZlRouter.getInstance().build("/module1/testFragment")
                .withString("key", "xinzailing").call()
            Log.d(TAG, "fragment = ${result?.getResult<BaseFragment2>("result")}")
        }

        /**
         * 获取其他模块的自定义view
         */
        buttonView.setOnClickListener {
            val result = ZlRouter.getInstance().build("/module2/testView").call()
            Log.d(TAG, "view = ${result?.getResult<TextView>("result")}")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1002) {
            val back = data?.extras?.getString("back")
            Log.d(TAG, back ?: "null....")
        }
    }
}
