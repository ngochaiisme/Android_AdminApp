package ngochaiisme.com.vn.activity;

import static ngochaiisme.com.vn.APIService.service;

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
import com.google.firebase.StartupTime;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.MainActivity;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.fragment.Sanpham_tatca;
import ngochaiisme.com.vn.model.model_Sanpham;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPham extends AppCompatActivity {
    TextView tv_tensp,tv_giatien,tv_cauhinh,tv_soluong;
    Button btn_themsp;
    ImageButton btn_loadhinhanh;
    ImageView img_hinhanhsp;
    RadioButton radio_dienthoai,radio_laptop;
    model_Sanpham sp_new;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        img_hinhanhsp.setImageURI(data.getData());
        LoadToFirebase();
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
                        sp_new.setLinkhinhanh(imageUrl);
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

    private void AddProduct(){
        Call<Void> call = APIService.service.addProduct(sp_new.getTensp(), sp_new.getGiatien(), sp_new.getCauhinh(), sp_new.getSoluong(), sp_new.getLinkhinhanh(), sp_new.getLoaisp());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Xử lý thành công
                    Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), TrangChu.class);
                    i.putExtra("fragment_name", "sanpham_frm");
                    startActivity(i);
                } else {
                    // Xử lý lỗi
                    Toast.makeText(getApplicationContext(), "Lỗi!!!: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(getApplicationContext(), "Lỗi...: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themsanpham);
        tv_tensp = findViewById(R.id.tv_tensp);
        tv_giatien = findViewById(R.id.tv_giatien);
        tv_cauhinh = findViewById(R.id.tv_cauhinh);
        tv_soluong = findViewById(R.id.tv_soluong);
        btn_themsp = findViewById(R.id.btn_them);
        btn_loadhinhanh = findViewById(R.id.btn_upload);
        img_hinhanhsp = findViewById(R.id.img_sp);
        radio_dienthoai = findViewById(R.id.radio_dienthoai);
        radio_laptop = findViewById(R.id.radio_laptop);
        sp_new = new model_Sanpham();

        btn_loadhinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getApplicationContext(),"click?",Toast.LENGTH_SHORT).show();
                ImagePicker.with(ThemSanPham.this)
                        .crop()	  //Crop image(Optional), Check Customization for more option
                        .compress(1024)	//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btn_themsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_new.setTensp(tv_tensp.getText().toString());
                sp_new.setGiatien(Float.parseFloat(tv_giatien.getText().toString()));
                sp_new.setCauhinh(tv_cauhinh.getText().toString());
                sp_new.setSoluong(Integer.parseInt(tv_soluong.getText().toString()));
                int loaisp;
                if(radio_dienthoai.isChecked())
                    loaisp = 1;
                else
                    loaisp = 0;
                sp_new.setLoaisp(loaisp);

                Log.e("check_0511", "sp_new: "+ sp_new.toString());

                AddProduct();

                // Gọi phương thức addProduct để thêm sản phẩm mới
            }
        });

    }


}