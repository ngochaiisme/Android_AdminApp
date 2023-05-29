package ngochaiisme.com.vn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import ngochaiisme.com.vn.R;

public class ThongKe extends Fragment {
    PieChart pieChart;
    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.thongke_fragment,container,false);
        pieChart = mView.findViewById(R.id.pieChart);
        InitPieChart();
        return mView;
    }

    private void InitPieChart() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // Thêm dữ liệu cho biểu đồ hình tròn
        entries.add(new PieEntry(40000, "Điện thoại"));
        entries.add(new PieEntry(60000, "Laptop"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.animateXY(2000, 2000);
        pieChart.setCenterText("Tổng doanh thu\n" + 100000);
        pieChart.setDrawEntryLabels(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(60);
        pieChart.setTransparentCircleRadius(64);


        Legend legend = pieChart.getLegend();
        legend.setTextSize(20); // thiết lập kích thước chữ cho label


        pieChart.invalidate();
    }
}
