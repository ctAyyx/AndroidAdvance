package ct.com.basics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


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

                break;
        }
    }

    private void readyGoReflectJobActivity() {


    }
}
