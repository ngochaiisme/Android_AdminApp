package ngochaiisme.com.vn.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import ngochaiisme.com.vn.MainActivity;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.adapter.ViewPageAdapter;

public class TrangChu extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    ViewPager2 view_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        view_page = findViewById(R.id.view_page);
        ViewPageAdapter vp_adapter = new ViewPageAdapter(this);
        view_page.setAdapter(vp_adapter);

        String fragmentName = getIntent().getStringExtra("fragment_name");
        if (fragmentName != null) {
            if (fragmentName.equals("sanpham_frm")) {
                view_page.setCurrentItem(1);
            }
            else
                view_page.setCurrentItem(1);
        }else
            setListener();
    }
    private void setListener() {

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.item_hopthu){
                    view_page.setCurrentItem(0);
                } else if (id == R.id.item_sanpham) {
                    view_page.setCurrentItem(1);
                }
                else if (id == R.id.item_donhang) {
                    view_page.setCurrentItem(2);
                }
                else if (id == R.id.item_thongke) {
                    view_page.setCurrentItem(3);
                }
                else if (id == R.id.item_dangxuat) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TrangChu.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn có chắc muốn đăng xuất?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(TrangChu.this,DangNhap.class);
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Xử lý khi nhấn nút Hủy
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return true;
            }
        });

        view_page.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottom_navigation.getMenu().findItem(R.id.item_hopthu).setChecked(true);
                        break;
                    case 1:
                        bottom_navigation.getMenu().findItem(R.id.item_sanpham).setChecked(true);
                        break;
                    case 2:
                        bottom_navigation.getMenu().findItem(R.id.item_donhang).setChecked(true);
                        break;
                    case 3:
                        bottom_navigation.getMenu().findItem(R.id.item_thongke).setChecked(true);
                        break;
                }
            }
        });
    }
}