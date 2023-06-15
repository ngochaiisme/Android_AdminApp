package ngochaiisme.com.vn.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ngochaiisme.com.vn.APIService;
import ngochaiisme.com.vn.R;
import ngochaiisme.com.vn.model.model_doanhthutheoloaisp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKe extends Fragment {
    private View mView;
    private EditText edt_ngaybatdau,edt_ngayketthuc;
    private Button btn_apdung;
    private Calendar calendar;
    private boolean isSelectingNgayBatDau = false;
    private boolean isSelectingNgayKetThuc = false;
    List<model_doanhthutheoloaisp> list_doanhthutheoloaisp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.thongke_fragment,container,false);
        
        AnhXa();
        SetDateTimePicker();
        btn_apdung_SetListener();




        return mView;

    }

    private void setFragment(Bundle bundle) {
        //Add child fragment to ThongKe fragment
        ThongKe_doanhthutheoloaisanpham fragment_piechart = new ThongKe_doanhthutheoloaisanpham();
        fragment_piechart.setArguments(bundle);
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.child_fragment_container, fragment_piechart);
        fragmentTransaction.commit();
    }

    private void btn_apdung_SetListener() {

        btn_apdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Kiem tra thong tin nhap
                String ngayBatDau = edt_ngaybatdau.getText().toString();
                String ngayKetThuc = edt_ngayketthuc.getText().toString();
                if (ngayBatDau.equals("")||ngayKetThuc.equals("")) {
                    Toast.makeText(getContext(),"Vui lòng chọn ngày bắt đầu và kết thúc",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dateBatDau = dateFormat.parse(ngayBatDau);
                    Date dateKetThuc = dateFormat.parse(ngayKetThuc);

                    if (dateBatDau.compareTo(dateKetThuc) > 0) {
                        Toast.makeText(getContext(),"Ngày kết thúc phải sau ngày bắt đầu",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
                //Goi API lay du lieu
                Call<List<model_doanhthutheoloaisp>> call = APIService.service.get_doanhthutheoloaisp(edt_ngaybatdau.getText().toString(),edt_ngayketthuc.getText().toString());
                call.enqueue(new Callback<List<model_doanhthutheoloaisp>>() {
                    @Override
                    public void onResponse(Call<List<model_doanhthutheoloaisp>> call, Response<List<model_doanhthutheoloaisp>> response) {
                        if(response.isSuccessful()){
                            List<model_doanhthutheoloaisp> rs = response.body();
                            list_doanhthutheoloaisp.clear();
                            list_doanhthutheoloaisp.addAll(rs);
                            double tongdoanhthu=0,dienthoai_doanhthu=0,laptop_doanhthu=0;

                            for(int i=0;i<list_doanhthutheoloaisp.size();i++){
                                tongdoanhthu+=list_doanhthutheoloaisp.get(i).getDoanhthu();
                                if(list_doanhthutheoloaisp.get(i).getLoaisp()==0)
                                    dienthoai_doanhthu+=list_doanhthutheoloaisp.get(i).getDoanhthu();
                                if(list_doanhthutheoloaisp.get(i).getLoaisp()==1)
                                    laptop_doanhthu += list_doanhthutheoloaisp.get(i).getDoanhthu();
                            }
                            Log.e("API sucessful: ", tongdoanhthu+"\t" +dienthoai_doanhthu+"\t" +laptop_doanhthu);

                            Bundle bundle = new Bundle();
                            bundle.putDouble("tongdoanhthu", tongdoanhthu);
                            bundle.putDouble("dienthoai_doanhthu", dienthoai_doanhthu);
                            bundle.putDouble("laptop_doanhthu", laptop_doanhthu);
                            bundle.putString("ngaybatdau",edt_ngaybatdau.getText().toString());
                            bundle.putString("ngayketthuc",edt_ngayketthuc.getText().toString());
                            setFragment(bundle);


                        }
                    }
                    @Override
                    public void onFailure(Call<List<model_doanhthutheoloaisp>> call, Throwable t) {
                        Log.e("call API get doanh thu theo loai sp", "On Failure" );
                    }
                });
            }
        });
    }

    private void AnhXa() {
        edt_ngaybatdau = mView.findViewById(R.id.ngaybatdau);
        edt_ngayketthuc = mView.findViewById(R.id.ngayketthuc);
        btn_apdung = mView.findViewById(R.id.btn_apdung);
        list_doanhthutheoloaisp = new ArrayList<>();
    }
    private void SetDateTimePicker() {
        calendar = Calendar.getInstance();
        edt_ngaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectingNgayBatDau = true;
                isSelectingNgayKetThuc = false;
                showDateTimePickerDialog();
            }
        });

        edt_ngayketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelectingNgayBatDau = false;
                isSelectingNgayKetThuc = true;
                showDateTimePickerDialog();
            }
        });

    }

    private void showDateTimePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                showTimePickerDialog();
            }
        };

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(getContext(), dateSetListener, year, month, day).show();
    }
    private void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND,00);

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                if (isSelectingNgayBatDau) {
                    edt_ngaybatdau.setText(dateTimeFormat.format(calendar.getTime()));
                } else if (isSelectingNgayKetThuc) {
                    edt_ngayketthuc.setText(dateTimeFormat.format(calendar.getTime()));
                }
            }
        };
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        new TimePickerDialog(getContext(), timeSetListener, hour, minute, true).show();
    }

}
