package ngochaiisme.com.vn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.adapter.DonHangAdapter;
import ngochaiisme.com.vn.model.model_donhang;


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

        model_donhang dh = new model_donhang(0511,"Nguyễn Bảo Châu", 3900000,"13/5/2022 11:11:11", "Đang giao hàng");
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        list_donhang.add(dh);
        dh_adapter.notifyDataSetChanged();

        return mView;
    }

    private void InitSpinner(){
        spinner = mView.findViewById(R.id.spinner);
        String[] items = {"Tất cả đơn hàng","Chờ xử lý","Đang chuẩn bị hàng","Đang giao hàng","Đã giao hàng"};
        ArrayAdapter<String> spiner_adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_item, items);
        spiner_adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinner.setAdapter(spiner_adapter);
    }
}
