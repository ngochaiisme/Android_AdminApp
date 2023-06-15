package ngochaiisme.com.vn.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ngochaiisme.com.vn.R;

public class ThongKe_doanhthutheoloaisanpham extends Fragment {
    private View mView;
    private PieChart pie_chart;
    TextView tv_doanhthu_dt,tv_doanhthu_laptop;
    ImageView img_switch_chart;
    String ngaybatdau="",ngayketthuc="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.thongke_fragment_doanhthutheoloaisp,container,false);
        Anhxa();
        Bundle bundle = getArguments();
        if (bundle != null) {
            Log.e("OngetArguments", "success" );
            double tongdoanhthu = bundle.getDouble("tongdoanhthu");
            double dienthoai_doanhthu = bundle.getDouble("dienthoai_doanhthu");
            double laptop_doanhthu = bundle.getDouble("laptop_doanhthu");
            ngaybatdau = bundle.getString("ngaybatdau");
            ngayketthuc = bundle.getString("ngayketthuc");
            VeBieuDoPieChart(tongdoanhthu,dienthoai_doanhthu,laptop_doanhthu);
            tv_doanhthu_dt.setVisibility(View.VISIBLE);
            tv_doanhthu_dt.setText("Doanh thu từ điện thoại: "+chuyenDoiGiaTri((float) dienthoai_doanhthu));
            tv_doanhthu_laptop.setVisibility(View.VISIBLE);
            tv_doanhthu_laptop.setText("Doanh thu từ điện thoại: "+chuyenDoiGiaTri((float) laptop_doanhthu));
            tongdoanhthu=0;dienthoai_doanhthu=0;laptop_doanhthu=0;
        }
        else
            Log.e("OngetArguments", "fail" );


        img_switch_chart_setListener();


        return mView;
    }

    private void img_switch_chart_setListener() {
        img_switch_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thongke_soluongsanphambanduoc frg_thongketheosoluong = new Thongke_soluongsanphambanduoc();
                Bundle bundle = new Bundle();
                bundle.putString("ngaybatdau",ngaybatdau);
                bundle.putString("ngayketthuc",ngayketthuc);
                frg_thongketheosoluong.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager(); // Hoặc getSupportFragmentManager() nếu bạn đang sử dụng androidx.fragment.app.Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.child_fragment_container, frg_thongketheosoluong);
                fragmentTransaction.addToBackStack(null); // Thêm vào back stack nếu bạn muốn Fragment C được đẩy vào stack và quay lại Fragment B khi nhấn nút back
                fragmentTransaction.commit();
            }
        });
    }

    private void Anhxa() {
        pie_chart=mView.findViewById(R.id.pie_chart);
        tv_doanhthu_dt = mView.findViewById(R.id.tv_dienthoai_doanhthu);
        tv_doanhthu_laptop = mView.findViewById(R.id.tv_laptop_doanhthu);
        img_switch_chart = mView.findViewById(R.id.img_switch_chart);
    }
    private void VeBieuDoPieChart(double tongdoanhthu, double dienthoai_doanhthu,double laptop_doanhthu) {


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((float) dienthoai_doanhthu, "Điện thoại")); // Loại A
        entries.add(new PieEntry((float) laptop_doanhthu, "Laptop")); // Loại B

        float totalDoanhSo = (float) tongdoanhthu; // Tổng doanh số

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f); // Kích thước văn bản giá trị
        dataSet.setValueTextColor(Color.BLACK); // Màu sắc văn bản giá trị

        // Định dạng giá trị thành phần thành phần trăm (%)
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                float percentage = (value / totalDoanhSo) * 100;
                return String.format("%.0f%%", percentage);
            }
        });
        PieData pieData = new PieData(dataSet);
        pie_chart.setData(pieData);
        //Description
        pie_chart.getDescription().setEnabled(false); // Tắt chú thích mặc định
        Description customDescription = new Description();
        customDescription.setText("Biểu đồ biểu diễn thống kê doanh số");
        customDescription.setTextSize(12f);
        customDescription.setTextColor(Color.BLACK);
        pie_chart.setDescription(customDescription);
        pie_chart.getDescription().setEnabled(false);
        //
        pie_chart.setCenterText("Tổng doanh số: "+chuyenDoiGiaTri(totalDoanhSo));
        //Điều chỉnh label hiển thị
        Legend legend = pie_chart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(10f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);

        //Animation
        pie_chart.animateY(4000, Easing.EaseInOutCubic);
        pie_chart.invalidate();
    }
    private String chuyenDoiGiaTri(float giaTri) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        return decimalFormat.format(giaTri);
    }

}
