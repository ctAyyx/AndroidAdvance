package ct.com.basics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ct.com.basics.reflect.job.ReflectJobActivity2;
import ct.com.basics.reflect.job.ReflectJobActivity3;

/**
 * TIME : 2020/6/20 0020
 * AUTHOR : CT
 * DESC :
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_main);

ThreadLocal
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reflect:
                readyGoReflectJobActivity();
                break;
            case R.id.btn_reflect3:
                startActivity(new Intent(this, ReflectJobActivity3.class));
                break;
        }
    }

    private void readyGoReflectJobActivity() {

        Intent intent = new Intent(this, ReflectJobActivity2.class);
        intent.putExtra("name", "张三");
        intent.putExtra("address", "三和小区");
        intent.putExtra("arr1", new String[]{"1", "2"});
        startActivity(intent);
    }
}
