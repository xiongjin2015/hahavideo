
package com.haha.video.ui;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.common.entity.MainMedia;
import com.haha.hwidget.util.HaScreen;
import com.haha.video.R;
import com.haha.video.util.HaImageLoader;

public final class HaMediaTemplate {

    private final static String TAG = "HaMediaTmeplate";

    private static HaMediaTemplate mHaMediaTemplate;

    private int mPosterRowItemWidth;// 3列item的width：竖图

    private int mPosterRowItemHeight;// 3列item的height：竖图

    private int mStillRowItemWidth;// 横图width

    private int mStillRowItemHeight;// 横图height
    
    public static enum Type {
        SHORT_VIDEO("short_video"), CHANNEL_VIDEO("channel_video"),
        UNKNOWN("unknown");
        public String name;

        private Type(String name) {
            this.name = name;
        }

        public static Type getType(String name) {
            for (Type template : Type.values()) {
                if (TextUtils.equals(name, template.name))
                    return template;
            }
            return UNKNOWN;
        }
    }

    private HaMediaTemplate(Context context) {
        initItemSize(context);
    }

    public static HaMediaTemplate getInstance(Context context) {
        if (mHaMediaTemplate == null)
            mHaMediaTemplate = new HaMediaTemplate(context);
        return mHaMediaTemplate;
    }

    private void initItemSize(Context ctx) {
        int usableScreenWidth = Math.min(HaScreen.getScreenWidth(ctx),
                HaScreen.getScreenHeight(ctx));
        int margin = (int) ctx.getResources().getDimension(R.dimen.section_margin);
        int padding = (int) ctx.getResources().getDimension(R.dimen.section_padding);
        usableScreenWidth = usableScreenWidth - 2 * margin;
        usableScreenWidth = usableScreenWidth - 2 * padding;

        int itemSpacing = (int) ctx.getResources().getDimension(R.dimen.section_item_spacing);

        mPosterRowItemWidth = (usableScreenWidth - itemSpacing * 2) / 3;
        mPosterRowItemHeight = (int) (mPosterRowItemWidth * 1.33);

        mStillRowItemWidth = (usableScreenWidth - itemSpacing) / 2;
        mStillRowItemHeight = (int) (mStillRowItemWidth * 0.56);
    }
    
    public int getNumColumns(String template) {
        boolean still = TextUtils.equals(template, Type.SHORT_VIDEO.name);
        return still ? 2 : 3;
    }
    
    public View getView(Context ctx, View view, MainMedia content, String type){
        return getView(ctx, view, content, Type.getType(type));
    }

    /**
     * 根据类型返回不同的view：3列还是2列
     * @param ctx
     * @param view
     * @param content
     * @param type
     * @return
     */
    public View getView(Context ctx, View view, MainMedia content, Type type){
        switch (type) {
        case SHORT_VIDEO:
            return getTemplateStill(ctx, view, content);           
        case CHANNEL_VIDEO:
            return getTemplatePoster(ctx, view, content);
        default://返回模版信息不在现有模版列表中
            return getTemplatePoster(ctx, view, content);
        }
    }
    
    private static class ContentHolder {
        public ImageView icon;
        public View specialMark;
        public TextView update;
        public TextView title;
        public TextView duration;
    }
    
    /**
     * 返回竖图：3列图
     * @param ctx
     * @param view
     * @param content
     * @return
     */
    private View getTemplatePoster(Context ctx, View view, MainMedia content) {
        ContentHolder holder = null;
        if (view != null) {
            holder = (ContentHolder) view.getTag();
            holder.icon.setImageDrawable(null);
        } else {
            view = LayoutInflater.from(ctx).inflate(R.layout.view_template_content_poster, null, false);
            holder = new ContentHolder();
            holder.icon = (ImageView) view.findViewById(R.id.poster_icon);
            holder.specialMark = view.findViewById(R.id.poster_special_mark);
            holder.update = (TextView) view.findViewById(R.id.poster_update);
            holder.title = (TextView) view.findViewById(R.id.poster_title);
            view.setTag(holder);
        }

        refreshPosterIcon(holder.icon, content.getImgv_url());
        //refreshSpecialmark(holder.specialMark, content.getTemplate());
        resetUpdate(holder.update, content.getUpdate());
        resetTitle(holder.title, content.getTitle());
        return view;
    }

    /**
     * 返回横图：2列图
     * @param ctx
     * @param view
     * @param content
     * @return
     */
    private View getTemplateStill(Context ctx, View view, MainMedia content) {
        ContentHolder holder = null;
        if (view != null) {
            holder = (ContentHolder) view.getTag();
            holder.icon.setImageDrawable(null);
        } else {
            view = LayoutInflater.from(ctx).inflate(R.layout.view_template_content_still, null, false);
            holder = new ContentHolder();
            holder.icon = (ImageView) view.findViewById(R.id.still_icon);
            holder.specialMark = view.findViewById(R.id.still_special_mark);
            holder.update = (TextView) view.findViewById(R.id.still_update);
            holder.duration = (TextView) view.findViewById(R.id.still_duration);
            holder.title = (TextView) view.findViewById(R.id.still_title);
            view.setTag(holder);
        }

        refreshStillIcon(holder.icon, content.getImgh_url());
        //refreshSpecialmark(holder.specialMark, content.getTemplate());
        resetUpdate(holder.update, content.getUpdate());
        resetDuration(holder.duration, content.getDuration());

        String title = content.getTitle();
        String aword = content.getBrief();
        if (!TextUtils.isEmpty(aword)){
            aword = "<font color='#959da3'>" + aword + "</font>";
            title = title + "<br />" + aword;
        }
        resetTitle(holder.title, title);
        return view;
    }
    
    private void refreshPosterIcon(ImageView iconView, String iconUrl) {
        LayoutParams params = iconView.getLayoutParams();
        params.width = mPosterRowItemWidth;
        params.height = mPosterRowItemHeight;
        HaImageLoader.displayPoster(iconUrl, iconView);
    }
    
    private void refreshStillIcon(ImageView iconView, String iconUrl) {
        LayoutParams params = iconView.getLayoutParams();
        params.width = mStillRowItemWidth;
        params.height = mStillRowItemHeight;
        HaImageLoader.displayStill(iconUrl, iconView);
    }
    
    private void resetUpdate(TextView updateView, String update) {
        resetText(updateView, update);
    }
    
    private void resetDuration(TextView durationView, String duration) {
        if (TextUtils.equals(duration, "0"))
            duration = null;
        resetText(durationView, duration);
    }
    private void resetTitle(TextView titleView, String title) {
        resetText(titleView, Html.fromHtml(title));
    }
    
    private void resetText(TextView textView, CharSequence text){
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
    }

    public void onDestory() {
        mHaMediaTemplate = null;
    }

}
