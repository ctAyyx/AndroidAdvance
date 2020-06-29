package ct.com.basics.reflect.job;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ct.com.basics.R;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC : 作业界面
 * <p>
 * 实现Intent中带过来的数据的自动注入
 */
public class ReflectJobActivity2 extends AppCompatActivity {

    @AutoWrite
    public String name;

    @AutoWrite("address")
    public String home;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        AutoWritedUtil.autoWrite(this);

        Log.e("TAG", "name:" + name + "--home:" + home);
    }
}
