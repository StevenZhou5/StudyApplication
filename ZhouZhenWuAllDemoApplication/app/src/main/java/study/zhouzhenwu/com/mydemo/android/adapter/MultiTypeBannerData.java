package study.zhouzhenwu.com.mydemo.android.adapter;

import java.util.List;

/**
 * 创建者： ZhouZhenWu/Steven.
 * 创建日期：2017/7/1
 * 类简介： 多类型Item中的Banner
 */
public class MultiTypeBannerData {

    public List<BannerItem> bannerItems;


    public class BannerItem {

        public long id;

        public String picUrl;

        public String targetUrl;

        public BannerItem() {
        }

        public BannerItem(long id,
                          String picUrl,
                          String jumpUrl) {
            this.id = id;
            this.picUrl = picUrl;
            this.targetUrl = jumpUrl;
        }
    }
}