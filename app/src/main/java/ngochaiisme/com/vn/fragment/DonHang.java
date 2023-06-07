package ngochaiisme.com.vn.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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

    RecyclerView rcv_donhang;
    DonHangAdapter dh_adapter;
    List<model_donhang> list_donhang;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.donhang_fragment,container,false);
        InitSpinner();
        rcv_donhang = mView.findViewById(R.id.rcv_donhang);
        list_donhang = new ArrayList<model_donhang>();
        dh_adapter = new DonHangAdapter(list_donhang);
        rcv_donhang.setAdapter(dh_adapter);
        LinearLayoutManager abc = new LinearLayoutManager(getContext());
        rcv_donhang.setLayoutManager(abc);
        Log.e("check_0511","chuan bi lay don hang");
        LoadDonHang();
        Log.e("check_0511","xong: "+list_donhang.size());





        return mView;
    }

//    public void LayTenKHtuID(String id) {
//
//        String kq = "null";
//        Call<ResponseBody>call = APIService.service.getTenKhachHang(id);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    kq = response.body().string();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("check_0511","Fail to get tenKH");
//            }
//        });
//    }

    private void LoadDonHang() {
        //Goi API lay danh sach san pham va them vao list_sanpham o day
        Call<List<model_donhang>> call = APIService.service.getAllDonhang();
        call.enqueue(new Callback<List<model_donhang>>() {
            @Override
            public void onResponse(Call<List<model_donhang>> call, Response<List<model_donhang>> response) {
                if(response.isSuccessful()){
                    Log.e("check_0511","sucessful: ");
                    List<model_donhang> DHList = response.body();
                    Log.e("check_0511","size: "+DHList.size());
                    list_donhang.addAll(DHList);
                    Log.e("check_0511","size: "+list_donhang.size());
                    dh_adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<model_donhang>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi xảy ra khi load đơn hàng",Toast.LENGTH_SHORT).show();
                Log.e("check_0511",t.getMessage());
            }
        });
    }


    private void InitSpinner(){
        spinner = mView.findViewById(R.id.spinner);
        String[] items = {"Tất cả đơn hàng","Chờ xử lý","Đang chuẩn bị hàng","Đang giao hàng","Đã giao hàng"};
        ArrayAdapter<String> spiner_adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_item, items);
        spiner_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(spiner_adapter);
    }
}
