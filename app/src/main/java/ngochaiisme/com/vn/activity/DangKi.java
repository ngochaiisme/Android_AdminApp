package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ngochaiisme.com.vn.R;

public class DangKi extends AppCompatActivity {

    TextView tv_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        tv_login = findViewById(R.id.tv_login);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),DangNhap.class);
                startActivity(i);
            }
        });


    }
}