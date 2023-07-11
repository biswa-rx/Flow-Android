package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class tut5_shared_flow : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tut5_shared_flow)

        GlobalScope.launch {
            val data: Flow<Int> = producer()
            data.collect{
                Log.d("biswa_rx", it.toString())
            }
        }
        GlobalScope.launch {
            val data: Flow<Int> = producer()
            delay(5000)
            data.collect{
                Log.d("biswa_rx", it.toString())
            }
        }
    }


    //HOT STREAM
    //IF WE RETURN SHARED FLOW OBJECT THEN IT ALLOW TO OTHER USER TO EMIT BY SHAREDFLOW.EMIT(<something>)
    private fun producer() : Flow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>(replay = 2)//replay like buffer
        GlobalScope.launch {
            val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
            list.forEach {
                mutableSharedFlow.emit(it)
                delay(1000)
            }
        }
        return mutableSharedFlow
    }
}