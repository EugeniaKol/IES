package com.example.lab_33

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.Math.abs
import java.util.Random
import kotlin.math.absoluteValue


class MainActivity : AppCompatActivity() {
    var mutationChance: Int = 0
    private fun gen_alg(x1_base: Double,
                    x2_base: Double,
                    x3_base: Double,
                    x4_base: Double,
                    y_base: Double) : MutableList<Double>{

        var counter = 0
        val populationZero: MutableList<MutableList<Double>> = mutableListOf()
        val population: MutableList<MutableList<Double>> = mutableListOf()
        val listOfFitnesses: MutableList<Double> = mutableListOf()
        val populationOfChild: MutableList<MutableList<Double>> = mutableListOf()
        var bestPopulation: MutableList<Double> = mutableListOf()


        fun fitness(population: MutableList<Double>): Double {
            val fitness: Double = y_base -
                    population[0] * x1_base -
                    population[1] * x2_base -
                    population[2] * x3_base -
                    population[3] * x4_base
            return fitness.absoluteValue
        }

        fun populationZeroFind() {
            for (i in 0..3) {
                populationZero.add(mutableListOf())
                for (j in 0..3) {
                    populationZero[i].add((1..8).random().toDouble())
                }
            }
        }

        fun findFitnessOfPopulation() {
            listOfFitnesses.clear()
            if (population.isEmpty()) {
                populationZero.mapTo(population) { it }
            }
            for (i in 0..3) {
                listOfFitnesses.add(fitness(population[i]))
            }
        }

        fun findRoulette() {
            populationOfChild.clear()
            var roulette = 0.00
            val roulettePercent: MutableList<Double> = mutableListOf()
            val circleRoulette: MutableList<Double> = mutableListOf()
            listOfFitnesses.forEach { roulette += 1 / it }
            for (i in 0..3) {
                roulettePercent.add(1 / listOfFitnesses[i] / roulette)
            }

            for (i in 0..3) {
                if (i == 0) {
                    circleRoulette.add(roulettePercent[i])
                } else {
                    circleRoulette.add(circleRoulette[i - 1] + roulettePercent[i])
                }
            }

            var i = 0
            populationOfChild.clear()
            while (i < 4) {
                val piu: Double = (1..100).random().toDouble() / 100
                var thisChild = 0
                for (k in 0..3) {
                    if (piu >= circleRoulette[k]) {
                        thisChild = k
                    }
                }
                populationOfChild.add(population[thisChild])
                i++
            }
        }

        fun crossingOver() {
            counter++
            population.clear()
            for (p in 0..3) {
                val c: MutableList<Double> = mutableListOf()
                c.clear()
                for (j in 0..3) {
                    if (p % 2 == 0) {
                        if (j < 2) {
                            c.add(populationOfChild[p][j])
                        } else c.add(populationOfChild[p + 1][j])
                    } else
                        if (j < 2) {
                            c.add(populationOfChild[p][j])
                        } else c.add(populationOfChild[p - 1][j])
                }
                
                val rand: Int = (1..100).random()
                if (rand < mutationChance){
                    populationOfChild[0][0]++
                }
                population.add(c)
            }
        }

        fun bestFitnessFind(): Boolean {
            findFitnessOfPopulation()
            listOfFitnesses.forEach { if (it == 0.0) return true }
            return false
        }

        fun life() {
            var q = 0
            while (!bestFitnessFind() && q < 10) {
                findFitnessOfPopulation()
                findRoulette()
                crossingOver()
                q++
            }
        }

        fun result(): MutableList<Double> {
            populationZeroFind()
            life()
            while ((!listOfFitnesses.contains(0.0)) &&
                population[0] == populationOfChild[0] &&
                population[1] == populationOfChild[1] &&
                population[2] == populationOfChild[2] &&
                population[3] == populationOfChild[3]
            ) {
                populationZero.clear()
                population.clear()
                listOfFitnesses.clear()
                populationOfChild.clear()
                populationZeroFind()
                life()
            }
            for (i in 0..3) {
                if (listOfFitnesses[i] == 0.0) {
                    bestPopulation = population[i]
                }
            }
            return bestPopulation
        }
        val answer : MutableList<Double>  =  result();

        if (answer.isEmpty()) {
            return answer;
        }
        answer.add(counter.toDouble())
        return answer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result: TextView = findViewById(R.id.show_result)
        val input_a: EditText = findViewById(R.id.input_data_a)
        val input_b: EditText = findViewById(R.id.input_data_b)
        val input_c: EditText = findViewById(R.id.input_data_c)
        val input_d: EditText = findViewById(R.id.input_data_d)
        val input_y: EditText = findViewById(R.id.input_data_y)
        val input_chance: EditText = findViewById(R.id.mutation_chance)

        val buttonCalculate: Button = findViewById(R.id.button_calculate)
        buttonCalculate.setOnClickListener {
            if (input_a.text.isEmpty()){
                result.text = "There was no number entered!"
            } else {
                val a = input_a.text.toString().toDouble()
                val b = input_b.text.toString().toDouble()
                val c = input_c.text.toString().toDouble()
                val d = input_d.text.toString().toDouble()
                val y = input_y.text.toString().toDouble()
                mutationChance = input_chance.text.toString().toInt()

                val ans = gen_alg(a, b, c, d, y)

                if (!ans.isEmpty()) { 
                    ans.removeAt(4)
                    result.text = "X1 = ${ans[0].toInt()}, X2 = ${ans[1].toInt()}, X3 = ${ans[2].toInt()}, X4 = ${ans[3].toInt()}"
                } else { 
                    result.text = "Couldn't find in range y/2. Try again"
                }
            }
        }
    }
}