package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.regex.Pattern;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKi extends AppCompatActivity {



    TextView tv_login;
    EditText edt_username, edt_phonenumber,edt_email, edt_password, edt_re_password, edt_masterkey;
    Button btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangki);

        Mapping();


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSignup_Onclick();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),DangNhap.class);
                startActivity(i);
            }
        });
    }

    private void btnSignup_Onclick() {
        if(checkInput()==false)
            return;
        String username = edt_username.getText().toString();
        String phonenumber = edt_phonenumber.getText().toString();
        String password = edt_password.getText().toString();
        String email = edt_email.getText().toString();
        Call<ResponseBody> call = APIService.service.add_newaccount(username,phonenumber,email,password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("On Call API add_newaccount", "onSuccess");
                try {
                    if(response.isSuccessful()){
                        String callback_rs = response.body().string();
                        Log.e("On Call API add_newaccount", "onSuccess"+callback_rs);
                        Toast.makeText(getApplicationContext(),callback_rs,Toast.LENGTH_LONG).show();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("On Call API add_newaccount", "onFailure"+ t.getMessage() );
            }
        });

    }

    private boolean checkInput() {
        if (tv_login.getText().toString().equals("")
                || tv_login.getText().toString().equals("")
                || edt_username.getText().toString().equals("")
                || edt_phonenumber.getText().toString().equals("")
                || edt_password.getText().toString().equals("")
                || edt_re_password.getText().toString().equals("")
                || edt_masterkey.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(),"Vui lòng nhập đủ thông tin",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!edt_password.getText().toString().equals(edt_re_password.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Nhập khẩu xác nhận không khớp",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isValidEmail(edt_email.getText().toString())){
            Toast.makeText(getApplicationContext(),"Email không đúng định dạng",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isValidPhoneNumber(edt_phonenumber.getText().toString())){
            Toast.makeText(getApplicationContext(),"Số điện thoại không đúng định dạng",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!edt_masterkey.getText().toString().equals("ankhangshop002")){
            Toast.makeText(getApplicationContext(),"Master Key không hợp lệ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        // Biểu thức chính quy để kiểm tra địa chỉ email
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        // Kiểm tra chuỗi email với biểu thức chính quy
        Pattern pattern = Pattern.compile(emailPattern);
        return pattern.matcher(email).matches();
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        // Biểu thức chính quy để kiểm tra số điện thoại
        String phonePattern = "^\\+?\\d{1,4}?[-.\\s]?\\(?(\\d{1,3})?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";
        // Kiểm tra chuỗi số điện thoại với biểu thức chính quy
        Pattern pattern = Pattern.compile(phonePattern);
        return pattern.matcher(phoneNumber).matches();
    }

    private void Mapping() {
        tv_login = findViewById(R.id.tv_login);
        edt_username = findViewById(R.id.edt_username);
        edt_phonenumber = findViewById(R.id.edt_phone_number);
        edt_password = findViewById(R.id.edt_password);
        edt_re_password = findViewById(R.id.edt_confirm_password);
        edt_email = findViewById(R.id.edt_email);
        edt_masterkey = findViewById(R.id.edt_masterkey);
        btn_signup = findViewById(R.id.btn_signup);
    }
}