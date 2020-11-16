package ct.com.ui.course12.cs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_constraint.*

/**
 * 约束布局使用
 *
 * 使用 ConstraintSet 为控件添加尺寸和位置变化的动画
 * */
class ConstraintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //使用 ConstraintSet 为控件添加尺寸和位置变化的动画
        setContentView(R.layout.activity_constraint)

        //使用 MotionLayout
       // setContentView(R.layout.layout_constraint_motion_01)

    }


    private fun anim4ConstraintSet() {

        btn_constraint_01.setOnClickListener {
            val constraintSet = ConstraintSet()
            constraintSet.load(this, R.layout.layout_constraint_set)
            TransitionManager.beginDelayedTransition(constraint_01)
            constraintSet.applyTo(constraint_01)
        }

    }


}