package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ngochaiisme.com.vn.MainActivity;
import ngochaiisme.com.vn.R;

public class DangNhap extends AppCompatActivity {

    TextView tv_signup;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);
       // Toast.makeText(getApplicationContext(),"Hello dnh",Toast.LENGTH_SHORT).show();
        tv_signup = findViewById(R.id.tv_signup);
        btn_login = findViewById(R.id.btn_login);

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),DangKi.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),TrangChu.class);
                startActivity(i);
            }
        });
    }
}