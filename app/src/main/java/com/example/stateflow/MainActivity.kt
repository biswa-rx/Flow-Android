package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

private const val TAG = "biswa_rx"
class MainActivity : AppCompatActivity() {
    val channel = Channel<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        producer()
        consumer()
    }
    private fun producer(){
        CoroutineScope(Dispatchers.Main).launch {
            channel.send(1)
            channel.send(2)
        }
    }
    private fun consumer(){
        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "consumer: "+channel.receive())
            Log.d(TAG, "consumer: "+channel.receive())
        }

    }
    fun coroutineDetail(){
//        CoroutineScope()
//        GlobalScope
//        ViewModelScope

//        Dispatchers.Main  - > Run in main thread //Prefered for UI operation
//        Dispatchers.IO   - > Run in IO thread // Prefered for IO operation where thread waiting state
//        Dispatchers.Default   - > Prefered for Heavy cpu intensive task, All cpu core used for that

//        .lunch   - > Fire and forget // not any data return expected
//        .async    - > Data expected form coroutine and receive by using await where that waiting for completion of coroutine
    }
}