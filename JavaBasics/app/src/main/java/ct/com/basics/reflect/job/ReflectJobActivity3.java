package ct.com.basics.reflect.job;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ct.com.basics.R;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC : 利用反射、注解、动态代理实现OnClick事件的自动注入
 */
public class ReflectJobActivity3 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect3);
        InjectUtil.inject(this);
    }

    @OnClick({R.id.btn_reflect3_01, R.id.btn_reflect3_02, R.id.btn_reflect3_03})
    public void onClick(View view) {
        Log.e("TAG", "被点击了：" + view);
    }
}
