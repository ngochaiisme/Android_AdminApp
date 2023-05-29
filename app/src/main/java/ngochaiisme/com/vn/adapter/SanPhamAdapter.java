package ngochaiisme.com.vn.adapter;
import static java.security.AccessController.getContext;

import android.content.Context;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_Sanpham;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    private Context context;
    private List<model_Sanpham> list_sanpham;

    private TextView tv_soluongsp;

    public SanPhamAdapter(Context context, List<model_Sanpham> list_sanpham, TextView tv_soluongsp) {
        this.context = context;
        this.list_sanpham = list_sanpham;
        this.tv_soluongsp = tv_soluongsp;
    }
    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sanpham_item,parent,false);

        return new SanPhamViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        model_Sanpham sp = list_sanpham.get(position);
        if (sp == null)
            return;
        holder.tv_ten.setText(sp.getTensp());
        holder.tv_gia.setText(chuyenSoThucSangChuoi(sp.getGiatien()) + "đ");
        holder.tv_soluong.setText("Số lượng: " + sp.getSoluong());


        if(!sp.getLinkhinhanh().equals("")){
            Picasso.get().load(sp.getLinkhinhanh()).into(holder.img_hinhanh);
        }
    }
    public String chuyenSoThucSangChuoi(double soThuc) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(soThuc);
    }
    @Override
    public int getItemCount() {

        if(list_sanpham!=null)
            return list_sanpham.size();
        return 0;
    }
    class SanPhamViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        ImageView img_hinhanh;
        TextView tv_ten, tv_gia;
        TextView tv_soluong;
        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            img_hinhanh = itemView.findViewById(R.id.img_hinhanh);
            tv_ten = itemView.findViewById(R.id.tv_tensanpham);
            tv_gia = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            itemView.setOnLongClickListener(this);

        }
        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            showDialogToDelete(position);
            return false;
        }
        private void showDialogToDelete(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Bạn có muốn xóa sản phẩm này không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DeleteSanPham(list_sanpham.get(position).getId());
                            list_sanpham.remove(position);
                            tv_soluongsp.setText(list_sanpham.size()+" sản phẩm");
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

        private void DeleteSanPham(int i){
            Call<Void> call = APIService.service.deleteProduct(i);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(context, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

