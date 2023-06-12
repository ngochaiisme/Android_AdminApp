package ngochaiisme.com.vn.fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.adapter.DonHangAdapter;
import ngochaiisme.com.vn.model.model_Sanpham;
import ngochaiisme.com.vn.model.model_donhang;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHang extends Fragment {
    private View mView;
    Spinner spinner;
    ImageView img_reset;
    RecyclerView rcv_donhang;
    DonHangAdapter dh_adapter;
    List<model_donhang> list_donhang,list_donhang_taca,list_donhang_choxuly,list_donhang_dangchuanbi,list_donhang_danggiaohang,list_donhang_dagiaohang,list_donhang_dahuy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.donhang_fragment,container,false);
        rcv_donhang = mView.findViewById(R.id.rcv_donhang);
        img_reset = mView.findViewById(R.id.img_reset);
        list_donhang = new ArrayList<model_donhang>();
        dh_adapter = new DonHangAdapter(list_donhang);
        rcv_donhang.setAdapter(dh_adapter);
        LinearLayoutManager abc = new LinearLayoutManager(getContext());
        rcv_donhang.setLayoutManager(abc);
        LoadDonHang();
        img_reset_setListener();
        InitSpinner();
        return mView;
    }

    private void img_reset_setListener() {
        img_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim);
                img_reset.startAnimation(rotation);
                spinner.setSelection(0);
                LoadDonHang();
            }
        });
    }

    private void LayDonHangTheoTrangThai() {
        list_donhang_choxuly = new ArrayList<model_donhang>();
        list_donhang_dangchuanbi = new ArrayList<model_donhang>();
        list_donhang_danggiaohang = new ArrayList<model_donhang>();
        list_donhang_dagiaohang = new ArrayList<model_donhang>();
        list_donhang_taca = new ArrayList<model_donhang>();
        list_donhang_dahuy = new ArrayList<model_donhang>();

        //Log.e("check_laydonhang", "size trong lay don hang"+list_donhang.size() );
        for (model_donhang item:list_donhang){
            list_donhang_taca.add(item);
            switch (item.getDh_trangthai()){
                case "Chờ xử lý":
                    list_donhang_choxuly.add(item);
                    break;
                case "Đang chuẩn bị hàng":
                    list_donhang_dangchuanbi.add(item);
                    break;
                case "Đang giao hàng":
                    list_donhang_danggiaohang.add(item);
                    break;
                case "Đã giao hàng":
                    list_donhang_dagiaohang.add(item);
                    break;
                case "Đã hủy":
                    list_donhang_dahuy.add(item);
                    break;
                default:
                    Log.e("check_laydonhangtheotrangthai", "trang thai khong hop le");
            }
        };
            Log.e("check_soluongtrongdanhsach", "tat ca: "+ list_donhang_taca.size()+
                    "cho xu ly: "+ list_donhang_choxuly.size()+
                    "dang chuan bi: "+ list_donhang_dangchuanbi.size()+
                    "dang giao hang: "+ list_donhang_danggiaohang.size()+
                    "da giao hang: "+ list_donhang_dagiaohang.size()+
                    "da giao hang: "+ list_donhang_dahuy.size());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("check_selected", "onSpiner selected" + i);
                switch (i){
                    case 0:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_taca);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_choxuly);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_dangchuanbi);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    case 3:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_danggiaohang);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    case 4:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_dagiaohang);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    case 5:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_dahuy);
                        dh_adapter.notifyDataSetChanged();
                        break;
                    default:
                        list_donhang.clear();
                        list_donhang.addAll(list_donhang_taca);
                        dh_adapter.notifyDataSetChanged();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.e("check_spinerclick", "nothing selected");
            }
        });

    }
    private void LoadDonHang() {


        //Goi API lay danh sach san pham va them vao list_sanpham o day
        Call<List<model_donhang>> call = APIService.service.getAllDonhang();
        call.enqueue(new Callback<List<model_donhang>>() {
            @Override
            public void onResponse(Call<List<model_donhang>> call, Response<List<model_donhang>> response) {
                if(response.isSuccessful()){
                   // Log.e("check_0511","sucessful: ");
                    List<model_donhang> DHList = response.body();
                  //  Log.e("check_0511","size: "+DHList.size());
                    list_donhang.clear();
                    dh_adapter.notifyDataSetChanged();
                    list_donhang.addAll(DHList);
                    Log.e("check_laydonhang", "size trong onResponse"+list_donhang.size() );
                    dh_adapter.notifyDataSetChanged();
                    LayDonHangTheoTrangThai();
                }
            }
            @Override
            public void onFailure(Call<List<model_donhang>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi xảy ra khi load đơn hàng",Toast.LENGTH_SHORT).show();
                //Log.e("check_0511",t.getMessage());
            }
        });
        Log.e("check_laydonhang", "size trong loaddonhang"+list_donhang.size() );
    }

    private void InitSpinner(){
        spinner = mView.findViewById(R.id.spinner);
        String[] items = {"Tất cả đơn hàng","Chờ xử lý","Đang chuẩn bị hàng","Đang giao hàng","Đã giao hàng","Đã hủy"};
        ArrayAdapter<String> spiner_adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_item, items);
        spiner_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(spiner_adapter);
    }
}
