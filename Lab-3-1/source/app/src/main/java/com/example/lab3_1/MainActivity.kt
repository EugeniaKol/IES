package com.example.lab3_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_0.setOnClickListener{ appendNum("0")}
        tv_1.setOnClickListener{ appendNum("1")}
        tv_2.setOnClickListener{ appendNum("2")}
        tv_3.setOnClickListener{ appendNum("3")}
        tv_4.setOnClickListener{ appendNum("4")}
        tv_5.setOnClickListener{ appendNum("5")}
        tv_6.setOnClickListener{ appendNum("6")}
        tv_7.setOnClickListener{ appendNum("7")}
        tv_8.setOnClickListener{ appendNum("8")}
        tv_9.setOnClickListener{ appendNum("9")}
        tv_calc.setOnClickListener{Fermat(input.text.toString())}
        tv_clear.setOnClickListener{Clear()}

    }

    fun appendNum(string: String){
        input.append(string)
        result.text = ""
    }

    fun Clear(){
        result.text = ""
        input.text = ""
    }

    fun Fermat(string: String){
        var num = string.toInt()
        var res = ""

        if (num % 2 == 0 ){
            res = "2, ${num / 2}"
            result.text = res
            return
        }

        var a = ceil(sqrt(num.toDouble())).toInt(); var b = 0;

        if(a * a == num){
            res = "${a}, $a"
            result.text = res
            return
        }

        while(true){
            var b1 = a * a - num
            b = sqrt(b1.toDouble()).toInt()
            if(b * b == b1){break}
            a += 1
        }

        res = "${a - b}, ${a + b}"
        result.text = res
    }
}