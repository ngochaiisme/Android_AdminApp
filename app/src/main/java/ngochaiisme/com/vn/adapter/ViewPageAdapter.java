package ngochaiisme.com.vn.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ngochaiisme.com.vn.fragment.DonHang;
import ngochaiisme.com.vn.fragment.HopThu;
import ngochaiisme.com.vn.fragment.SanPham;
import ngochaiisme.com.vn.fragment.ThongKe;


public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HopThu();
            case 1:
                return new SanPham();
            case 2:
                return new DonHang();
            case 3:
                return new ThongKe();
            default:
                return new HopThu();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
