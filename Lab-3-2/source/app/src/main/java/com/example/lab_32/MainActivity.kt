package com.example.lab_32

import kotlinx.android.synthetic.main.activity_main.*

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sigma: Double = 0.0
        var deadline_seconds: Double = 0.0
        var deadline_iters = 0

        choose_sigma.setOnClickListener{
            val list_of_sigmas = arrayOf("0.001", "0.01", "0.05", "0.1", "0.2", "0.3")
            val mBuilder = AlertDialog.Builder(this@MainActivity)
            mBuilder.setTitle("Choose sigma")
            mBuilder.setSingleChoiceItems(list_of_sigmas, -1) { dialogInterface, i ->
                sigma = list_of_sigmas[i].toDouble()
                dialogInterface.dismiss()
            }

            mBuilder.setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        }

        choose_deadline_time.setOnClickListener{
            val list_of_times = arrayOf("0.5", "1", "2", "5")
            val mBuilder = AlertDialog.Builder(this@MainActivity)
            mBuilder.setTitle("Choose seconds")
            mBuilder.setSingleChoiceItems(list_of_times, -1) { dialogInterface, i ->
                deadline_seconds = list_of_times[i].toDouble()
                dialogInterface.dismiss()
            }

            mBuilder.setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        }

        choose_deadline_iters.setOnClickListener{
            val list_of_times = arrayOf("100", "200", "500", "1000")
            val mBuilder = AlertDialog.Builder(this@MainActivity)
            mBuilder.setTitle("Choose iterations")
            mBuilder.setSingleChoiceItems(list_of_times, -1) { dialogInterface, i ->
                deadline_iters = list_of_times[i].toInt()
                dialogInterface.dismiss()
            }

            mBuilder.setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            val mDialog = mBuilder.create()
            mDialog.show()
        }

//        radio_group.setOnCheckedChangeListener { group, checkedId ->
//            when(checkedId){
//                R.id.chosen_deadline -> {
//                    choose_deadline_time.isEnabled = true
//                    choose_deadline_iters.isEnabled = false
//                }
//                R.id.chosen_iters -> {
//                    choose_deadline_time.isEnabled = false
//                    choose_deadline_iters.isEnabled = true
//                }
//            }
//        }
        tv_calc.setOnClickListener{Perceptron(sigma,deadline_seconds, deadline_iters)}
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.chosen_deadline ->
                    choose_deadline_time.isEnabled = checked
                R.id.chosen_iters ->
                    choose_deadline_iters.isEnabled = checked
            }
        }
    }

    private fun Perceptron(speed: Double, time: Double, iterations: Int){
        var W1 = 0.00
        var W2 = 0.00
        val P = 4.00
        val points = arrayListOf(Pair(0.00, 6.00), Pair(1.00, 5.00), Pair(3.00, 3.00), Pair(2.00, 4.00))

        fun check(): Boolean {
            for (i in 0 .. 3){
                var y = W1 * points[i].first + W2 * points[i].second
                if ((i < 2 && y < P) || (i >= 2 && y > P) ) return false
            }
            return true
        }

        fun result(): Pair<Double, Double> {
            val startTime = System.currentTimeMillis()
            for (i in 0..iterations) {
                if ((System.currentTimeMillis() - startTime) <= time * 1000) {
                    for (k in 0 until points.size) {
                        val y = W1 * points[k].first + W2 * points[k].second
                        val delta = P - y
                        W1 += delta * points[k].first * speed
                        W2 += delta * points[k].second * speed
                        if (check()) {
                            return Pair(W1, W2)
                        }
                    }
                }
            }
            return Pair(W1, W2)
        }

        val res =  result()
        tv_result.text = "W1 = ${res.first}  W2 = ${res.second}"
    }
}
