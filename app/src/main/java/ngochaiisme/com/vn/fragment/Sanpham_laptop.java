package ngochaiisme.com.vn.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.adapter.SanPhamAdapter;
import ngochaiisme.com.vn.model.model_Sanpham;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Sanpham_laptop extends Fragment {
    private View mView;
    private RecyclerView rcv_data;
    List<model_Sanpham> list_sanpham_laptop;
    SanPhamAdapter Adapter;

    TextView tv_soluongsp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sanpham_item,container,false);

        tv_soluongsp = mView.findViewById(R.id.tv_soluong);
        list_sanpham_laptop = new ArrayList<model_Sanpham>();
        rcv_data = mView.findViewById(R.id.rcv_dssp);
        Adapter = new SanPhamAdapter(getContext(),list_sanpham_laptop,tv_soluongsp);
        rcv_data.setAdapter(Adapter);
        LinearLayoutManager abc = new LinearLayoutManager(getContext());
        rcv_data.setLayoutManager(abc);
        LoadSanPham();
        return mView;
    }

    private void LoadSanPham(){
        //Goi API lay danh sach san pham va them vao list_sanpham o day

        Call<List<model_Sanpham>> call = APIService.service.getAllProducts();
        call.enqueue(new Callback<List<model_Sanpham>>() {
            @Override
            public void onResponse(Call<List<model_Sanpham>> call, Response<List<model_Sanpham>> response) {
                if(response.isSuccessful()){
                    List<model_Sanpham> productList = response.body();
                    for(int i=0;i<productList.size();i++){
                        if(productList.get(i).getLoaisp()==0)
                            list_sanpham_laptop.add(productList.get(i));
                    }
                    tv_soluongsp.setText(list_sanpham_laptop.size()+" sản phẩm");
                    Adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<model_Sanpham>> call, Throwable t) {
                Toast.makeText(getContext(),"Lỗi xảy ra khi load sản phẩm",Toast.LENGTH_SHORT).show();
                Log.e("check_0511",t.getMessage());
            }
        });
    }
}