package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_Sanpham;

public class CapNhapSanPham extends AppCompatActivity {

    TextView tv_tensp,tv_giatien,tv_cauhinh,tv_soluong;
    Button btn_capnhap,btn_huy;
    ImageButton btn_loadhinhanh;
    ImageView img_hinhanhsp;
    RadioButton radio_dienthoai,radio_laptop;

    model_Sanpham sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhapsanpham);

        AnhXa();
        CapNhapGiaoDien();
    }

    private void AnhXa() {
        tv_tensp = findViewById(R.id.tv_tensp);
        tv_giatien = findViewById(R.id.tv_giatien);
        tv_cauhinh = findViewById(R.id.tv_cauhinh);
        tv_soluong = findViewById(R.id.tv_soluong);
        btn_capnhap = findViewById(R.id.btn_capnhap);
        btn_huy = findViewById(R.id.btn_huy);
        btn_loadhinhanh = findViewById(R.id.btn_upload);
        img_hinhanhsp = findViewById(R.id.img_sp);
        radio_dienthoai = findViewById(R.id.radio_dienthoai);
        radio_laptop = findViewById(R.id.radio_laptop);
    }
    private void CapNhapGiaoDien() {
        Intent intent = getIntent();
        if (intent.hasExtra("sanpham")) {
            sp = (model_Sanpham) intent.getSerializableExtra("sanpham");
        }
        if (sp != null) {
            tv_tensp.setText(sp.getTensp());
            tv_giatien.setText(String.valueOf(sp.getGiatien()));
            tv_cauhinh.setText(sp.getCauhinh());
            tv_soluong.setText(String.valueOf(sp.getSoluong()));
            if(!sp.getLinkhinhanh().equals(""))
                Picasso.get().load(sp.getLinkhinhanh()).into(img_hinhanhsp);
            if(sp.getLoaisp()==1)
                radio_dienthoai.setChecked(true);
            else
                radio_laptop.setChecked(true);
        }
    }
}
