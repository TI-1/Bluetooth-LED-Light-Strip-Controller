package com.example.ledlightcontrol


import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*



class MainActivity : AppCompatActivity() {

    companion object {
        var m_myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var m_bluetoothSocket: BluetoothSocket? = null
        lateinit var m_progress: ProgressDialog
        lateinit var m_bluetoothAdapter: BluetoothAdapter
        var m_isConnected: Boolean = false
        lateinit var m_address: String
        val TEST: String = "test"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        m_address = intent.getStringExtra(SelectDeviceActivity.EXTRA_ADDRESS)!!

        ConnectToDevice(this).execute()


        flash.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 1
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
            //sendCommand("F1#")
            }
        fade.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 2
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
           // sendCommand("F2#")
        }
        Disconnect.setOnClickListener {
            val flag = 'F'
            val hash = '#'
            val id = 0
            val fA = byteArrayOf(flag.toByte(),id.toByte(),hash.toByte() )
            sendCommand2(fA)
            disconnect()
        }
        more.setOnClickListener{
            val intent = Intent(this, MoreFunctions::class.java)
            startActivity(intent)

        }


        val seekBright = findViewById<SeekBar>(R.id.seekBar3)
        seekBright?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBright: SeekBar,
                                           progress: Int, fromUser: Boolean) {


//                if (seekBright.progress <= 15 ){
//                    sendCommand("FB"+"%02X".format(seekBright.progress)+"#")
//                }else {
//                    val brightnessInt = seekBright.progress.toString(16)
//                    sendCommand("FB$brightnessInt#")
//                }

                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seekBright: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seekBright: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                    "Brightness is: " + seekBright.progress + "%",
                    Toast.LENGTH_SHORT).show()
                val star = '*'
                val hash = '#'
                val bA = byteArrayOf(star.toByte(),seekBright.progress.toByte(),hash.toByte() )
                sendCommand2(bA)
            }


        })

        val seekSpeed = findViewById<SeekBar>(R.id.seekBar)
        seekSpeed?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekSpeed: SeekBar,
                                           progress: Int, fromUser: Boolean) {

//                if (seekSpeed.progress <= 15 ){
//                    sendCommand("FS"+"%02X".format(seekSpeed.progress)+"#")
//                }else {
//                    val speedInt = seekSpeed.progress.toString(16)
//                    sendCommand("FS$speedInt#")
//                }
                
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                Toast.makeText(this@MainActivity,
                    "Speed is: " + seekSpeed.progress + "%",
                    Toast.LENGTH_SHORT).show()
                val speed = 'S'
                val hash = '#'
                val sA = byteArrayOf(speed.toByte(),seekSpeed.progress.toByte(),hash.toByte() )
                sendCommand2(sA)
            }
        })


        //val colorPickerView = findViewById<ColorPickerView>(R.id.colorPickerView)
        colorPickerView.flagView = CustomFlag(this, R.layout.layout_flag)
        //colorPickerView.FlagMode(FlagMode.ALWAYS)

            colorPickerView.setColorListener(
                ColorEnvelopeListener { envelope, _ ->
                    //textView.text = "#" + envelope.hexCode
                    alphaTileView.setPaintColor(envelope.color)
                    val colour = envelope.argb
                    val redByte = colour[1].toByte()
                    val red = 'R'
                    val greenByte = colour[2].toByte()
                    val green = 'G'
                    val blueByte = colour[3].toByte()
                    val blue = 'B'
                    val hash = '#'
                    val rA = byteArrayOf(red.toByte(), redByte, hash.toByte())
                    val gA = byteArrayOf(green.toByte(), greenByte, hash.toByte())
                    val bA = byteArrayOf(blue.toByte(), blueByte, hash.toByte() )
                    val r = envelope.hexCode.substring(2,4)
                    val g = envelope.hexCode.substring(4,6)
                    val b = envelope.hexCode.substring(6,8)
                    
                    //val rrr = byteArrayOf(red, r1)
                    if (m_isConnected) {
                        sendCommand2(rA)
                        sendCommand2(gA)
                        sendCommand2(bA)
                       // sendCommand("R$r#")
                       // sendCommand("G$g#")
                        //sendCommand("B$b#")
                    }

                })



    }
    private fun sendCommand(input: String) {
        if (m_bluetoothSocket != null) {
           try{
               m_bluetoothSocket!!.outputStream.write(input.toByteArray())
           } catch (e: IOException){
               e.printStackTrace()
           }
        }
    }
    private fun sendCommand2(byteArray: ByteArray) {
        if (m_bluetoothSocket != null) {
            try{
                m_bluetoothSocket!!.outputStream.write(byteArray)
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun disconnect() {
        if (m_bluetoothSocket != null) {
            try{
                m_bluetoothSocket!!.close()
                m_bluetoothSocket = null
                m_isConnected = false

            } catch(e: IOException) {
                e.printStackTrace()
            }

        }
        finish()
    }
    private class ConnectToDevice(c: Context): AsyncTask<Void, Void, String>(){
        private var connectSuccess: Boolean = true
        private val context: Context
        init{
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            m_progress = ProgressDialog.show(context, "Connecting...", "please wait")
        }

        override fun doInBackground(vararg p0: Void?): String? {
            try{
                if(m_bluetoothSocket == null || !m_isConnected) {
                    m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device: BluetoothDevice = m_bluetoothAdapter.getRemoteDevice(m_address)
                    m_bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(m_myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    m_bluetoothSocket!!.connect()
                }
            }catch (e:IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }
        override fun onPostExecute(result: String?){
            super.onPostExecute(result)
            if (!connectSuccess) {
                Log.i("data","couldn't connect")
            } else{
                m_isConnected = true
            }
            m_progress.dismiss()

        }
    }


}
