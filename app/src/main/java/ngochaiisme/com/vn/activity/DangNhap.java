package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.MainActivity;
import ngochaiisme.com.vn.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {

    TextView tv_signup, tv_forgotpass;
    EditText edt_username, edt_password;
    Button btn_login;
    CheckBox check_remember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        Anhxa();

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");
            edt_username.setText(savedUsername);
            edt_password.setText(savedPassword);
            check_remember.setChecked(true);
        }

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DangKi.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_login_Onlick();
            }
        });
    }


    private void btn_login_Onlick() {
        if (!isValidated()) {
            return;
        }
        Call<ResponseBody> call = APIService.service.dangnhap(edt_username.getText().toString(), edt_password.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String rs = response.body().string();
                    Log.e("on Call API dang nhap", "onSucess: " + rs);
                    if (rs.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();

                        if (check_remember.isChecked()) {
                            // Lưu thông tin đăng nhập nếu người dùng đã chọn "Nhớ mật khẩu"
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("rememberMe", true);
                            editor.putString("username", edt_username.getText().toString());
                            editor.putString("password", edt_password.getText().toString());
                            editor.apply();
                        } else {
                            // Xóa thông tin đăng nhập nếu người dùng không chọn "Nhớ mật khẩu"
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                        }

                        Intent i = new Intent(getApplicationContext(), TrangChu.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sai tên tài đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("on Call API dang nhap", "onFail");
            }
        });
    }

    private boolean isValidated() {
        if (edt_username.getText().toString().isEmpty() || edt_password.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Anhxa() {
        tv_signup = findViewById(R.id.tv_signup);
        btn_login = findViewById(R.id.btn_login);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_pass);
        tv_forgotpass = findViewById(R.id.forgotpassword);
        check_remember = findViewById(R.id.checkbox_rememberme);
    }
}
