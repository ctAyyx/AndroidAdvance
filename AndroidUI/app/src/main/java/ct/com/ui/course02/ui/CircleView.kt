package ct.com.ui.course02.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CircleView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {


    init {
        //打印所有属性
        if (attrs != null) {

            for (i in 0 until attrs.attributeCount) {
                val name = attrs.getAttributeName(i)
                val value = attrs.getAttributeValue(i)
                val resource=attrs.getAttributeNameResource(i)

                Log.e("TAG", "属性名称:${name} -- 属性值:${value} -- 资源:${resource}")
            }
        }

    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawCircle(
            width / 2f,
            width / 2f,
            width / 2f,
            Paint().apply { color = Color.RED })
    }


}