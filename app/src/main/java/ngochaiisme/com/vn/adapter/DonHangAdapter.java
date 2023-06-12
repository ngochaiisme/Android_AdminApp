package ngochaiisme.com.vn.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.activity.ChiTietDonHang;
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
        switch (dh.getDh_trangthai()){
            case "Đang xử lý":
                holder.tv_trangthai.setTextColor(Color.BLUE);
                break;
            case "Đang chuẩn bị hàng":
                holder.tv_trangthai.setTextColor(Color.CYAN);
                break;
            case "Đang giao hàng":
                holder.tv_trangthai.setTextColor(Color.GREEN);
                break;
            case "Đã giao hàng":
                holder.tv_trangthai.setTextColor(Color.BLACK);
                break;
            case "Đã hủy":
                holder.tv_trangthai.setTextColor(Color.RED);
                break;
        }
    }

    public String chuyenSoThucSangChuoi(double soThuc) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(soThuc);
    }

    @Override
    public int getItemCount() {
        return list_donhang.size();
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView tv_ngaydathang,tv_tenkhachhang,tv_tongtien,tv_trangthai;
        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ngaydathang = itemView.findViewById(R.id.thoigiandathang);
            tv_tenkhachhang = itemView.findViewById(R.id.tenkhachhang);
            tv_tongtien = itemView.findViewById(R.id.tongtien);
            tv_trangthai = itemView.findViewById(R.id.trangthai);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            model_donhang item = list_donhang.get(position);
            Intent i = new Intent(view.getContext(), ChiTietDonHang.class);
            i.putExtra("item_donhang", (Serializable) item);
            i.putExtra("dh_tenkh", tv_tenkhachhang.getText());
            view.getContext().startActivity(i);
        }

        @Override
        public boolean onLongClick(View view) {
            Log.e("On long click", "state of order: "+ list_donhang.get(getAdapterPosition()).getDh_trangthai() );
            if(!list_donhang.get(getAdapterPosition()).getDh_trangthai().equals("Đã giao hàng")
            && !list_donhang.get(getAdapterPosition()).getDh_trangthai().equals("Đã hủy"))
                Toast.makeText(itemView.getContext(),"Không thể xóa sản phẩm đang thực thi",Toast.LENGTH_SHORT).show();
            else{
                showDialogToDelete(getAdapterPosition());
            }
            return true;
        }

        private void XoaDonHang(int i) {
            Call<Void> call = APIService.service.deleteDonhang(i);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(itemView.getContext(),"Đã xóa đơn hàng",Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(itemView.getContext(),"Có lỗi xảy ra...",Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void showDialogToDelete(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setMessage("Bạn có muốn xóa sản phẩm này không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            XoaDonHang(list_donhang.get(position).getDh_id());
                            list_donhang.remove(position);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            builder.show();
        }
    }
}
