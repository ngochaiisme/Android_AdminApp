package ngochaiisme.com.vn.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_donhang;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {
    List<model_donhang> list_donhang;

    public DonHangAdapter(List<model_donhang> list_donhang) {
        this.list_donhang = list_donhang;
    }


    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang,parent,false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        model_donhang dh = list_donhang.get(position);
        if(dh==null)
            return;
        holder.tv_ngaydathang.setText(dh.getDh_thoigiandathang());


        Call<ResponseBody> call = APIService.service.getTenKhachHang(dh.getKh_id());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    holder.tv_tenkhachhang.setText(response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("check_0511","Fail to get tenKH");
            }
        });
        holder.tv_tongtien.setText("Tổng tiền: "+chuyenSoThucSangChuoi(dh.getDh_tongtien())+"đ");
      //  Log.e("check_0511",dh.getDh_trangthai());
        holder.tv_trangthai.setText(dh.getDh_trangthai());
    }

    public String chuyenSoThucSangChuoi(double soThuc) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(soThuc);
    }

    @Override
    public int getItemCount() {
        return list_donhang.size();
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ngaydathang,tv_tenkhachhang,tv_tongtien,tv_trangthai;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ngaydathang = itemView.findViewById(R.id.thoigiandathang);
            tv_tenkhachhang = itemView.findViewById(R.id.tenkhachhang);
            tv_tongtien = itemView.findViewById(R.id.tongtien);
            tv_trangthai = itemView.findViewById(R.id.trangthai);
        }
    }

}
