package com.example.ledlightcontrol

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.skydoves.colorpickerview.AlphaTileView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.flag.FlagView
import kotlinx.android.synthetic.main.activity_main.view.*

@SuppressLint("ViewConstructor")
class CustomFlag(context: Context, layout: Int) : FlagView(context, layout) {
    private val textView: TextView = findViewById(R.id.flag_color_code)

    private val view: View = findViewById(R.id.flag_color_layout)
    override fun onRefresh(colorEnvelope: ColorEnvelope) {
        textView.text = "#" + colorEnvelope.hexCode
        view.setBackgroundColor(colorEnvelope.color)
    }

}