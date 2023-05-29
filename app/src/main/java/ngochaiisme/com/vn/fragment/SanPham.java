package ngochaiisme.com.vn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.tabs.TabLayout;

import ngochaiisme.com.vn.R;

public class SanPham extends Fragment {
    private View mView;
    private TabLayout tabLayout;

    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sanpham_fragment,container,false);


        tabLayout = mView.findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Tất cả"));
        tabLayout.addTab(tabLayout.newTab().setText("Điện thoại"));
        tabLayout.addTab(tabLayout.newTab().setText("Laptop"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(new Sanpham_tatca());
                        break;
                    case 1:
                        showFragment(new Sanpham_dienthoai());
                        break;
                    case 2:
                        showFragment(new Sanpham_laptop());
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Xử lý khi tab không được chọn
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Xử lý khi tab được chọn lại
            }
        });
        showFragment(new Sanpham_tatca());
        return mView;
    }
    private void showFragment(Fragment fragment) {
        // Lấy đối tượng FragmentManager từ Fragment cha
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();

    }
}
