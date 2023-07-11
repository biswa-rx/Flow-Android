package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class tut4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tut4)
        GlobalScope.launch (Dispatchers.Main) {
            producer()
                .map {
                    delay(1000)
                    it * 2
                    Log.d("biswa_rx", "Map thread - ${Thread.currentThread().name}")
                }
                .flowOn(Dispatchers.IO)
                .filter {
                    delay(500)
                    Log.d("biswa_rx", "Filter thread - ${Thread.currentThread().name}")
                    it < 8
                }
                .flowOn(Dispatchers.Main)
                .collect {
                    Log.d("biswa_rx", "Collecter thread - ${Thread.currentThread().name} + Value - > ${it}")
                }
        }
    }

    fun producer(): Flow<Int> {
        return flow<Int> {
//        withContext(Dispatchers.IO) {
            val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            list.forEach {
                delay(1000)
                Log.d("biswa_rx", "Emitter Thread - ${Thread.currentThread().name}")
                emit(it)
                throw Exception("Error in Emitter")
            }
//        }
        }.catch {
            Log.d("biswa_rx", "Emitter catch - ${it.message}")
            emit(-30)
        }
    }
}