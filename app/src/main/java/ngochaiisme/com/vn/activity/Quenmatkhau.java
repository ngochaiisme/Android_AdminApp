package ngochaiisme.com.vn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Quenmatkhau extends AppCompatActivity {
    public static final String TAG = Quenmatkhau.class.getName();
    Button btn_continue;
    EditText edt_phonenumber;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau);
        Anhxa();

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_phonenumber = edt_phonenumber.getText().toString().trim();
                btn_continue_Onclick(str_phonenumber);
            }
        });
    }

    private void Anhxa() {
        btn_continue = findViewById(R.id.btn_continue);
        edt_phonenumber = findViewById(R.id.edt_phonenumber);
        mAuth = FirebaseAuth.getInstance();

    }

    private void btn_continue_Onclick(String phonenumber) {
        Log.e(TAG, "btn_continue_Onclick");
        Call<ResponseBody> call = APIService.service.getusername(phonenumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String username = response.body().string().trim();
                    Log.e(TAG, "username: "+username );

                    if(username.equals("Không tìm thấy dữ liệu")){
                        Toast.makeText(getApplicationContext(),"Số điện thoại không tồn tại",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                       // Toast.makeText(getApplicationContext(),"Oke get go"+username,Toast.LENGTH_SHORT).show();
                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(mAuth)
                                        .setPhoneNumber(ChuyenDoiSDT(phonenumber))       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(Quenmatkhau.this)                 // (optional) Activity for callback binding
                                        // If no activity is passed, reCAPTCHA verification can not be used.
                                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                            @Override
                                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                signInWithPhoneAuthCredential(phoneAuthCredential);
                                            }

                                            @Override
                                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                                Toast.makeText(getApplicationContext(),"Xác thực thất bại!...",Toast.LENGTH_SHORT).show();
                                                Log.e(TAG, e.getMessage() );
                                            }

                                            @Override
                                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                super.onCodeSent(verificationId, forceResendingToken);
                                                goToEnterOTPActivity(phonenumber,verificationId,username);

                                            }
                                        })          // OnVerificationStateChangedCallbacks
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);
                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Call getsuername onFailure" );
            }
        });

    }

    private  String ChuyenDoiSDT(String sdt){
        String kq ="";
        if (sdt.startsWith("0")) {
            kq = "+84" + sdt.substring(1);
        }
        return kq;
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Log.e(TAG, "on_signInwithPhone");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToLoginActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(),"Xác thực thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToLoginActivity(String phoneNumber) {
        Toast.makeText(getApplicationContext(),"Successful"+phoneNumber,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ResetPassword.class);
        startActivity(i);
    }
    private void goToEnterOTPActivity(String phonenumber, String verificationId,String username) {
        Intent i = new Intent(this, NhanOTP.class);
        i.putExtra("phonenumber",phonenumber);
        i.putExtra("verificationId",verificationId);
        i.putExtra("username",username);
        startActivity(i);

    }
}