package ngochaiisme.com.vn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {
    String TAG = ResetPassword.class.getName();
    String musername;
    TextView tv_username;
    EditText edt_newpass, edt_confirm_newpass;
    Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        getDataIntent();
        Log.e(TAG, "username: "+musername );
        Anhxa();
        tv_username.setText("Your username is "+ musername);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_finish_Onclick();
            }
        });
    }
    private void getDataIntent() {
        musername = getIntent().getStringExtra("username");
    }
    private void Anhxa() {
        tv_username = findViewById(R.id.tv_username);
        edt_newpass = findViewById(R.id.edt_newpassword);
        edt_confirm_newpass = findViewById(R.id.edt_confirm_password);
        btn_finish = findViewById(R.id.btn_finish);
    }

    private void btn_finish_Onclick() {
        Call<ResponseBody> call = APIService.service.resetpassword(musername,edt_newpass.getText().toString().trim());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String kq = response.body().string();
                    if(kq.equals("success")){
                        Toast.makeText(getApplicationContext(),"Cập nhập thành công",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),DangNhap.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra...",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Có lỗi xảy ra",Toast.LENGTH_SHORT).show();
            }
        });
    }
}