package study.zhouzhenwu.com.mydemo.android.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;
import study.zhouzhenwu.com.mydemo.R;
import study.zhouzhenwu.com.mydemo.common.utils.DensityUtil;
import study.zhouzhenwu.com.mydemo.common.utils.ScreenUtils;
import study.zhouzhenwu.com.mydemo.common.widgets.PagerIndicator;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/7/1
 * 类简介：用与展示首页Banner的数据
 */
public class BannerBeanViewBinder extends ItemViewBinder<MultiTypeBannerData, BannerBeanViewBinder.ViewHolder> {
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.adapter_item_banner_bean, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MultiTypeBannerData bannerBean) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder implements ViewPager.OnPageChangeListener {
        private ViewPager mBannerImageViewPager;
        private PagerIndicator mBannerIndicator;

        ViewHolder(View itemView) {
            super(itemView);
            mBannerImageViewPager = (ViewPager) itemView.findViewById(R.id.vp_image_list);
            mBannerImageViewPager.addOnPageChangeListener(this);
            int width = DensityUtil.getScreenWidth(itemView.getContext());
            ViewGroup.LayoutParams layoutParams = mBannerImageViewPager.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(0, 0);
            }
            layoutParams.width = width;
            layoutParams.height = (int) (width * 0.67);
            mBannerImageViewPager.setLayoutParams(layoutParams);
            mBannerIndicator = (PagerIndicator) itemView.findViewById(R.id.banner_indicator);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
