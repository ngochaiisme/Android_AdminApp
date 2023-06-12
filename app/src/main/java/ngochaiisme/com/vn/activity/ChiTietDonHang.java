package ngochaiisme.com.vn.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.adapter.SanPhamAdapter;
import ngochaiisme.com.vn.adapter.ctdh_SanPhamAdapter;
import ngochaiisme.com.vn.model.model_donhang;
import ngochaiisme.com.vn.model.model_item_ctdh;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHang extends AppCompatActivity {


    model_donhang item_donhang;
    Spinner spinner;
    ImageView img_edit;
    Button btn_huy,btn_capnhap;
    TextView tv_tenkh,tv_diachi,tv_tongtien;
    RecyclerView rcv_dssp;
    String tenKH;
    List<model_item_ctdh> list_item_ctdh;
    ctdh_SanPhamAdapter Adapter;

    int old_state = 3;
    String new_state ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietdonhang);
        getItem();
        Anhxa();
        Init_Spinner();
        Init_Imgedit();
        CapNhapGiaoDien();
        btn_huy_setListenner();
        btn_capnhap_setListener();



    }

    private void btn_capnhap_setListener() {



        btn_capnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (spinner.getSelectedItemPosition()){
                    case 0:
                        new_state = "Đang xử lý";
                        break;
                    case 1:
                        new_state = "Đang chuẩn bị hàng";
                        break;
                    case 2:
                        new_state = "Đang giao hàng";
                        break;
                    case 3:
                        new_state = "Đã giao hàng";
                        break;
                    case 4:
                        new_state = "Đã hủy";
                        break;
                }

                Log.e("btn_capnhap_gia tri dau vao: ", item_donhang.getDh_id()+"\t"+new_state );
                Call<Void> call = APIService.service.capnhaptrangthaidonhang(item_donhang.getDh_id(),new_state);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Cập nhập thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.e("On response", "onResponse: "+ response.message());
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Có lỗi xảy ra...", Toast.LENGTH_SHORT).show();
                        Log.e("Cập nhập trạng thái đơn hàng", "onFailure: ");
                    }
                });

                btn_capnhap.setVisibility(View.GONE);
                btn_huy.setVisibility(View.GONE);
                spinner.setEnabled(false);
                img_edit.setVisibility(View.VISIBLE);
            }
        });

    }

    private void btn_huy_setListenner() {
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setSelection(old_state);
                btn_huy.setVisibility(View.GONE);
                btn_capnhap.setVisibility(View.GONE);
                img_edit.setVisibility(View.VISIBLE);
                spinner.setEnabled(false);
            }
        });
    }

    private void CapNhapGiaoDien() {
        tv_tenkh.setText(tenKH);
        tv_tongtien.setText(chuyenSoThucSangChuoi(item_donhang.getDh_tongtien())+"đ");
        tv_diachi.setText(item_donhang.getDh_diachi());


        switch (item_donhang.getDh_trangthai()){
            case "Đang xử lý":
                spinner.setSelection(0);
                old_state=0;
                break;
            case "Đang chuẩn bị hàng":
                spinner.setSelection(1);
                old_state=1;
                break;
            case "Đang giao hàng":
                spinner.setSelection(2);
                old_state=2;
                break;
            case "Đã giao hàng":
                spinner.setSelection(3);
                old_state=3;
                break;
            case "Đã hủy":
                spinner.setSelection(4);
                old_state=4;
                break;
        }
        list_item_ctdh = new ArrayList<>();
        Adapter = new ctdh_SanPhamAdapter(list_item_ctdh);
        rcv_dssp.setAdapter(Adapter);
        LinearLayoutManager abc = new LinearLayoutManager(getApplicationContext());
        rcv_dssp.setLayoutManager(abc);
        getList_sanpham();

    }

    private void getList_sanpham() {
        Call<List<model_item_ctdh>> call = APIService.service.get_list_sanpham_ctdh(item_donhang.getDh_id());
        call.enqueue(new Callback<List<model_item_ctdh>>() {
            @Override
            public void onResponse(Call<List<model_item_ctdh>> call, Response<List<model_item_ctdh>> response) {
                List<model_item_ctdh> list_result = response.body();
                list_item_ctdh.addAll(list_result);
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<model_item_ctdh>> call, Throwable t) {
                Log.e("GetListSP_cthd", "onFailure: " );
            }
        });
    }

    public String chuyenSoThucSangChuoi(double soThuc) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(soThuc);
    }

    private void getItem() {
        tenKH = "";
        item_donhang = new model_donhang();
        Intent i = getIntent();
        if(i.hasExtra("item_donhang")){
            item_donhang = (model_donhang) i.getSerializableExtra("item_donhang");
            tenKH = i.getStringExtra("dh_tenkh");
            Log.e("get item_donhang",item_donhang.toString() );
        }
        else
            Log.e("get item_donhang", "that bai" );
    }

    private void Anhxa() {
        spinner = findViewById(R.id.spinnerTrangThai);
        img_edit = findViewById(R.id.img_edit);
        btn_huy = findViewById(R.id.btn_huy);
        btn_capnhap = findViewById(R.id.btn_capnhap);
        tv_tenkh=findViewById(R.id.tv_tenkh);
        tv_diachi = findViewById(R.id.tv_diachi);
        tv_tongtien = findViewById(R.id.tv_tongtien);
        rcv_dssp = findViewById(R.id.rcv_dssp);
    }

    private void Init_Imgedit() {
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setEnabled(true);
                img_edit.setVisibility(View.GONE);
                btn_huy.setVisibility(View.VISIBLE);
                btn_capnhap.setVisibility(View.VISIBLE);
            }
        });
    }

    private void Init_Spinner() {
        String[] trangThaiArray = {"Đang xử lý", "Đang chuẩn bị hàng", "Đang giao hàng", "Đã giao hàng","Đã hủy"};
        int[] mauSacArray = {Color.BLUE, Color.CYAN, Color.GREEN, Color.BLACK,Color.RED};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, trangThaiArray) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(mauSacArray[position]);
                textView.setTextSize(22);
                return textView;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
                textView.setTextColor(mauSacArray[position]);
                textView.setTextSize(22);
                return textView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setEnabled(false);
    }



}