package com.example.ledlightcontrol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.more_functions.*
import java.io.IOException

class MoreFunctions : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.more_functions)
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Extra Lighting Effects"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        white.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 3
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
           // sendCommand("F3#")
        }
        cylon.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 4
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
           // sendCommand("F4#")
        }
        snowsparkle.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 5
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
            //sendCommand("F5#")
        }
        meteorrain.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 6
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F6#")
        }
        rainbowcycle.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 7
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F7#")
        }
        red.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 8
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F8#")
        }
        colourwipe.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 9
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F9#")
        }

        blue.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 'Z'
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F9#")
        }

        green.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 'X'
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
//            sendCommand("F9#")
        }



    }
    private fun sendCommand(input: String) {
        if (MainActivity.m_bluetoothSocket != null) {
            try{
                MainActivity.m_bluetoothSocket!!.outputStream.write(input.toByteArray())
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun sendCommand2(byteArray: ByteArray) {
        if (MainActivity.m_bluetoothSocket != null) {
            try{
                MainActivity.m_bluetoothSocket!!.outputStream.write(byteArray)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}