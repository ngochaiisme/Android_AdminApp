package ngochaiisme.com.vn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ngochaiisme.com.vn.activity.DangNhap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView tv_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        tv_progressbar = findViewById(R.id.tv_progressbar);
        progressBar.setProgress(0); // Đặt giá trị tiến trình ban đầu là 0
        // Khởi động tiến trình thay đổi giá trị tiến trình
        startProgressBar();


    }

    private void startProgressBar() {
        // Tạo một luồng (thread) để cập nhật giá trị tiến trình
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Vòng lặp để thay đổi giá trị tiến trình từ 1 đến 100
                    for (int progress = 0; progress <= 200; progress = progress + 10) {
                        Thread.sleep(100); // Tạm dừng 100ms
                        final int finalProgress = progress;
                        if(finalProgress < 101)
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(finalProgress);
                                    tv_progressbar.setText(finalProgress+"%");
                                }
                            });
                        else {
                            Intent i = new Intent(MainActivity.this, DangNhap.class);
                            startActivity(i);
                            finish(); // Tùy chọn: Đóng Activity hiện tại sau khi chuyển sang Activity mới
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}