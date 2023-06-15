package ngochaiisme.com.vn.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_sanphamtheosoluongbanduoc;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Thongke_soluongsanphambanduoc extends Fragment {
    private View mView;
    BarChart barChart;
    String ngaybatdau ="",ngayketthuc="";
    List<model_sanphamtheosoluongbanduoc> list_sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.thongke_fragment_soluongsanphambanduoc,container,false);

        Anhxa();

        Bundle bundle = getArguments();
        if(bundle!=null){
            ngaybatdau = bundle.getString("ngaybatdau");
            ngayketthuc = bundle.getString("ngayketthuc");

            Call<List<model_sanphamtheosoluongbanduoc>> call = APIService.service.get_soluongsanphambanduoc(ngaybatdau,ngayketthuc);
            call.enqueue(new Callback<List<model_sanphamtheosoluongbanduoc>>() {
                @Override
                public void onResponse(Call<List<model_sanphamtheosoluongbanduoc>> call, Response<List<model_sanphamtheosoluongbanduoc>> response) {
                    List<model_sanphamtheosoluongbanduoc> list_rs = response.body();
                    list_sp.addAll(list_rs);
                    for(int i = 0;i<list_sp.size();i++)
                        Log.e("getSP successful", list_sp.get(i).toString());

                    Map<String,Integer> map_rs = new HashMap<>();
                    for(model_sanphamtheosoluongbanduoc item:list_sp){
                        map_rs.put(item.getSp_tensp(),item.getSp_soluong());
                    }

                    for(model_sanphamtheosoluongbanduoc item:list_sp){
                        map_rs.put(item.getSp_tensp(),item.getSp_soluong());
                    }


                    VeBieuDoCot(map_rs);
                }

                @Override
                public void onFailure(Call<List<model_sanphamtheosoluongbanduoc>> call, Throwable t) {
                    Log.e("get san pham ban duoc", "onFailure: " );
                }
            });
        }

        //VeBieuDoCot(danhsachsanphamtheosoluongbanduoc);
        return mView;
    }

    private void Anhxa() {
        barChart = mView.findViewById(R.id.bar_chart);
        list_sp = new ArrayList<>();
    }

    private void VeBieuDoCot(Map<String, Integer> danhSachSanPham) {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int i = 0;
        int[] colorsArray = ColorTemplate.MATERIAL_COLORS; // Mảng màu sắc
        List<Integer> colors = new ArrayList<>();
        for (int j = 0; j < colorsArray.length; j++) {
            colors.add(colorsArray[j]);
            Log.e("mau sac", colorsArray[j]+"\n" );
        }
        for(int k = 0;k<10;k++){
            colors.add(Color.parseColor("#FF0000"));   // Màu đỏ
            colors.add(Color.parseColor("#00FF00"));   // Màu xanh lá cây
            colors.add(Color.parseColor("#0000FF"));   // Màu xanh dương
            colors.add(Color.parseColor("#FFFF00"));   // Màu vàng
            colors.add(Color.parseColor("#FF00FF"));   // Màu hồng
            colors.add(Color.parseColor("#00FFFF"));   // Màu xanh lam
            colors.add(Color.parseColor("#800000"));   // Màu đỏ sẫm
            colors.add(Color.parseColor("#008000"));   // Màu xanh lá cây sẫm
            colors.add(Color.parseColor("#000080"));   // Màu xanh dương sẫm
            colors.add(Color.parseColor("#808000"));   // Màu xanh lá cây nhạt
            colors.add(Color.parseColor("#800080"));   // Màu tím
            colors.add(Color.parseColor("#008080"));   // Màu xanh lam nhạt
            colors.add(Color.parseColor("#808080"));   // Màu xám
            colors.add(Color.parseColor("#C0C0C0"));   // Màu bạc
            colors.add(Color.parseColor("#FFD700"));   // Màu vàng đồng
            colors.add(Color.parseColor("#FFA500"));   // Màu cam
            colors.add(Color.parseColor("#FF4500"));   // Màu cam đỏ
            colors.add(Color.parseColor("#9400D3"));   // Màu tím đậm
            colors.add(Color.parseColor("#00FF7F"));   // Màu xanh lục sáng
            colors.add(Color.parseColor("#7B68EE"));   // Màu xanh lục nhạt
        }
        for (Map.Entry<String, Integer> entry : danhSachSanPham.entrySet()) {
            String tenSanPham = entry.getKey();
            int soLuong = entry.getValue();
            entries.add(new BarEntry(i, soLuong));
            labels.add(tenSanPham);
            i++;
        }
        BarDataSet dataSet = new BarDataSet(entries, "Biểu đồ so sánh số lượng sản phẩm bán được");
        dataSet.setColors(colors);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barChart.getDescription().setEnabled(false);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.invalidate();
        // Tạo chú thích tùy chỉnh
//        Legend legend = barChart.getLegend();
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        legend.setDrawInside(false);
//        legend.setXEntrySpace(10f);
//        legend.setYEntrySpace(5f);
//        legend.setWordWrapEnabled(true);
//        legend.setTextColor(Color.BLACK);
//        legend.setTextSize(15f);
//        List<LegendEntry> legendEntries = new ArrayList<>();
//        for (int j = 0; j < labels.size(); j++) {
//            LegendEntry entry = new LegendEntry();
//            entry.label = labels.get(j);
//            entry.formColor = colors.get(j);
//            legendEntries.add(entry);
//        }
//        legend.setCustom(legendEntries);
//        Description description = new Description();
//        description.setText("Biểu đồ biểu diễn số lượng từng sản phẩm đã bán");
//        description.setPosition(0f, 0f); // Đặt vị trí mô tả
//        barChart.setDescription(description);
        //
        // Tạo một MarkerView để hiển thị giá trị trên cột
        MarkerView markerView = new MarkerView(getContext(), R.layout.custom_marker_view) {
            private TextView tvValue = findViewById(R.id.tv_value);
            @Override
            public void refreshContent(Entry e, Highlight highlight) {
                if (e instanceof BarEntry) {
                    BarEntry barEntry = (BarEntry) e;
                    int value = (int) barEntry.getY();
                    tvValue.setText(labels.get((int) barEntry.getX())+"\nSố lượng: "+String.valueOf(value));
                }
                super.refreshContent(e, highlight);
            }

            @Override
            public MPPointF getOffsetForDrawingAtPoint(float posX, float posY) {
                return new MPPointF(-getWidth() / 2f, -getHeight());
            }
        };
        barChart.setMarker(markerView);
        barChart.animateY(1500, Easing.EaseInOutCubic);
    }
}
