package ngochaiisme.com.vn.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_item_ctdh;

public class ctdh_SanPhamAdapter extends RecyclerView.Adapter<ctdh_SanPhamAdapter.ctdh_SanPhamViewHolder>{

    private List<model_item_ctdh> list_item_ctdh;

    public ctdh_SanPhamAdapter(List<model_item_ctdh> list_item_ctdh) {
        this.list_item_ctdh = list_item_ctdh;
    }

    @NonNull
    @Override
    public ctdh_SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ctdh_sanpham,parent,false);
        return new ctdh_SanPhamViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ctdh_SanPhamViewHolder holder, int position) {
        model_item_ctdh item = list_item_ctdh.get(position);
        if(item == null)
            return;
        holder.tv_tensp.setText(item.getSp_tensp());
        holder.tv_soluong.setText(String.valueOf(item.getSp_soluong()));
        holder.tv_giatien.setText(chuyenSoThucSangChuoi(item.getSp_giatien()));
        //hinh anh sp
        if(!item.getSp_linkhinhanh().equals("")){
            Picasso.get().load(item.getSp_linkhinhanh()).into(holder.img_hinhanh);
        }
    }
    public String chuyenSoThucSangChuoi(double soThuc) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(soThuc);
    }
    @Override
    public int getItemCount() {
        if(list_item_ctdh!=null)
            return list_item_ctdh.size();
        return 0;
    }
    class ctdh_SanPhamViewHolder extends RecyclerView.ViewHolder {
        ImageView img_hinhanh;
        TextView tv_tensp,tv_giatien,tv_soluong;
        public ctdh_SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            img_hinhanh = itemView.findViewById(R.id.img_hinhanh);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_giatien = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
        }
    }
}
