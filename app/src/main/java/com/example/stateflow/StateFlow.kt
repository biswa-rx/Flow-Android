package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "biswa_rx"
class StateFlow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        
        GlobalScope.launch(Dispatchers.Main) {
            val result = producer()
            delay(6000)
            result.collect {
                Log.d(TAG, "Item $it")
            }
        }

//
//        GlobalScope.launch(Dispatchers.Main) {
//            val result = producer2()
//            Log.d(TAG, result.value.toString())
//        }
    }

    
    private fun producer() : Flow<Int> {
        val mutableStateFlow = MutableStateFlow(10)
        GlobalScope.launch (Dispatchers.Main){
            delay(2000)
            mutableStateFlow.emit(20)
            delay(2000)
            mutableStateFlow.emit(30)
        }
        return mutableStateFlow
    }

//    private fun producer2() : StateFlow<Int> {
//        val mutableStateFlow = MutableStateFlow(10)
//        GlobalScope.launch (Dispatchers.Main){
//            delay(2000)
//            mutableStateFlow.emit(20)
//            delay(2000)
//            mutableStateFlow.emit(30)
//        }
//        return mutableStateFlow
//    }
}