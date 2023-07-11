package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

private const val TAG = "biswa_rx"
class Tut2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tut2)

//        val job = GlobalScope.launch {
//            val data: Flow<Int> = producer()
//            data.collect{
//                Log.d("biswa_rx", it.toString())
//            }
//        }

//        GlobalScope.launch {
//            delay(3500)
//            job.cancel()
//        }

//        GlobalScope.launch {
////            val data: Flow<Int> = producer()
//            producer()
//                .onStart {
//                    emit(-1)
//                    Log.d(TAG, "Starting out")
//
//                }
//                .onCompletion {
//                    Log.d(TAG, "onCompleted")
//                    emit(40)
//                }
//                .onEach {
//                    Log.d(TAG, "About to emit -$it")
//                }
//                .collect{
//                    Log.d("biswa_rx", it.toString())
//            }
//        }

//        GlobalScope.launch {
//            val result1 = producer().first()
//            Log.d(TAG, "First element {$result1}")
//            val result = producer().toList()
//            Log.d(TAG, result.toString())
//        }

//        GlobalScope.launch {
//            producer()
//                .map {
//                    it * 2
//                }
//                .filter {
//                    it < 7
//                }
//                .collect{
//                    Log.d(TAG, "onCreate: "+it.toString())
//                }
//        }
        
        ////Filtered and map
        
//        GlobalScope.launch { 
//            getNote()
//                .map { FormattedNote(it.isActive,it.title,it.description) }
//                .filter { it.isActive }
//                .collect{
//                    Log.d(TAG, it.toString())
//                }
//        }
        
        
        //Buffer in flow
        GlobalScope.launch { 
            val time = measureTimeMillis { 
                producer()
                    .buffer(3)
                    .collect{
                        delay(1500)
                        Log.d(TAG, it.toString())
                    }
            }
            Log.d(TAG,"Time "+time.toString())
        }
        
        
    }

    fun producer() = flow {//By default flow create coroutine scope
        val list = listOf(1,2,3,4,5,6,7,8,9,10)
        list.forEach {
            delay(1000)
            emit(it)
        }
    }
}

private fun getNote() : Flow<Note> {
    val list = listOf(
        Note(1,true,"First","First description"),
        Note(2,true,"Second","Second description"),
        Note(1,false,"Third","Third description")
    )
    return list.asFlow()
}

data class Note(val id:Int,val isActive: Boolean,val title: String,val description: String)
data class FormattedNote(val isActive: Boolean,val title: String,val description: String)