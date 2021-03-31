package com.ct.jetpack.optimize

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.concurrent.thread

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.RED
        canvas.drawText("${System.currentTimeMillis()}--->",0f,40f,paint)
    }

    fun startDraw() {
        thread(start = true) {
            while (true) {
                Thread.sleep(2)
                post {
                    invalidate()
                }
            }
        }
    }

}