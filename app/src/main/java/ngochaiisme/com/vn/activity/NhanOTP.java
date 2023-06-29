package ngochaiisme.com.vn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.concurrent.TimeUnit;

import ngochaiisme.com.vn.R;

public class NhanOTP extends AppCompatActivity {
    String TAG = NhanOTP.class.getName();
    FirebaseAuth mAuth;
    Button btn_submit;
    EditText edt_otp;
    TextView tv_resendotp,tv_timer;
    String mPhonenumber,mVerificationId,musername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapotp);
        getDataIntent();

        Anhxa();


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_submit_Onclick(edt_otp.getText().toString().trim());
            }
        });

        CapNhapTimer();
        tv_resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_resendotp_Onclick(mPhonenumber);
            }
        });
    }



    private void getDataIntent() {
        mPhonenumber = getIntent().getStringExtra("phonenumber");
        mVerificationId = getIntent().getStringExtra("verificationId");
        musername = getIntent().getStringExtra("username");
    }
    private void Anhxa() {
        btn_submit = findViewById(R.id.btn_submit);
        edt_otp = findViewById(R.id.edt_otp);
        tv_resendotp = findViewById(R.id.tv_resend_otp);
        mAuth = FirebaseAuth.getInstance();
        tv_timer = findViewById(R.id.tv_timer);
    }
    private void CapNhapTimer() {
        tv_resendotp.setVisibility(View.GONE);
        tv_timer.setVisibility(View.VISIBLE);
        new CountDownTimer(61000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
                final String timeLeft = seconds + "s";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setText(timeLeft);
                    }
                });
            }

            @Override
            public void onFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_timer.setVisibility(View.GONE);
                        tv_resendotp.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }


    private void tv_resendotp_Onclick(String mPhonenumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(ChuyenDoiSDT(mPhonenumber))       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(NhanOTP.this)                 // (optional) Activity for callback binding
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
                                Toast.makeText(getApplicationContext(),"Đã gửi lại mã OTP",Toast.LENGTH_SHORT).show();
                                tv_resendotp.setVisibility(View.GONE);
                                CapNhapTimer();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void btn_submit_Onclick(String strOtp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, strOtp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            goToLoginActivity(user.getPhoneNumber(),musername);
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

    private void goToLoginActivity(String phoneNumber,String username) {
        Toast.makeText(getApplicationContext(),"Successful"+phoneNumber,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, ResetPassword.class);
        i.putExtra("username",username);
        startActivity(i);
    }

    private  String ChuyenDoiSDT(String sdt){
        String kq ="";
        if (sdt.startsWith("0")) {
            kq = "+84" + sdt.substring(1);
        }
        return kq;
    }
}