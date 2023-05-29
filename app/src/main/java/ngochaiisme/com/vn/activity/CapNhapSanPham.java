package ngochaiisme.com.vn.activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_Sanpham;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapNhapSanPham extends AppCompatActivity {

    TextView tv_tensp,tv_giatien,tv_cauhinh,tv_soluong;
    Button btn_capnhap,btn_huy,btn_edit;
    ImageButton btn_loadhinhanh;
    ImageView img_hinhanhsp;
    RadioButton radio_dienthoai,radio_laptop;

    model_Sanpham sp;
    model_Sanpham sp_updated;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        img_hinhanhsp.setImageURI(data.getData());
        LoadToFirebase();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhapsanpham);

        AnhXa();
        CapNhapGiaoDien();
        XuLyButton();

    }

    private void XuLyButton() {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_edit.setVisibility(View.GONE);
                btn_loadhinhanh.setVisibility(View.VISIBLE);
                btn_huy.setVisibility(View.VISIBLE);
                btn_capnhap.setVisibility(View.VISIBLE);
                tv_tensp.setEnabled(true);
                tv_cauhinh.setEnabled(true);
                tv_giatien.setEnabled(true);
                tv_soluong.setEnabled(true);
                radio_dienthoai.setClickable(true);
                radio_laptop.setClickable(true);
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TrangChu.class);
                i.putExtra("fragment_name", "sanpham_frm");
                startActivity(i);
            }
        });

        btn_capnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_updated.setTensp(tv_tensp.getText().toString());
                sp_updated.setGiatien(Float.parseFloat(tv_giatien.getText().toString()));
                sp_updated.setCauhinh(tv_cauhinh.getText().toString());
                sp_updated.setSoluong(Integer.parseInt(tv_soluong.getText().toString()));
                int loaisp;
                if(radio_dienthoai.isChecked())
                    loaisp = 1;
                else
                    loaisp = 0;
                sp_updated.setLoaisp(loaisp);

                Log.e(TAG, "sp_updated: "+ sp_updated.toString() );

                UpdateProduct();
            }
        });

        btn_loadhinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getApplicationContext(),"click?",Toast.LENGTH_SHORT).show();
                ImagePicker.with(CapNhapSanPham.this)
                        .crop()	  //Crop image(Optional), Check Customization for more option
                        .compress(1024)	//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void UpdateProduct() {
        Call<Void> call = APIService.service.UpdateProduct(
                sp_updated.getId(),
                sp_updated.getTensp(),
                sp_updated.getGiatien(),
                sp_updated.getCauhinh(),
                sp_updated.getSoluong(),
                sp_updated.getLinkhinhanh(),
                sp_updated.getLoaisp());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(getApplicationContext(), "Cập nhập sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), TrangChu.class);
                    i.putExtra("fragment_name", "sanpham_frm");
                    startActivity(i);
                } else {
                    // Xử lý lỗi
                    Toast.makeText(getApplicationContext(), "Lỗi!!! " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(getApplicationContext(), "Lỗi...: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        btn_edit= findViewById(R.id.btn_edit);

        sp_updated = new model_Sanpham();

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

            sp_updated.setId(sp.getId());
            sp_updated.setLinkhinhanh(sp.getLinkhinhanh());
        }
    }

    private void LoadToFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        img_hinhanhsp.setDrawingCacheEnabled(true);
        img_hinhanhsp.buildDrawingCache();
        Bitmap bitmap = img_hinhanhsp.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        String fileName = now();
        StorageReference imageRef = storageRef.child(fileName);


        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("check_0511", "onSucess: ");
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUrl) {
                        String imageUrl = downloadUrl.toString();
                        Log.e("check_0511", "onSucess_getlink: "+ downloadUrl);
                        sp_updated.setLinkhinhanh(imageUrl);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("check_0511", "Load firebase that bai! ");
            }
        });
    }

    private String now() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy__HH_mm_ss");
        String now = dateFormat.format(currentDate);
        return  now;
    }


}
