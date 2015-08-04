package com.haha.video.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haha.common.entity.MainMedia;
import com.haha.hwidget.util.HaScreen;
import com.haha.video.R;
import com.haha.video.util.HaImageLoader;

public class HaFocusTemplate {
    
    private static HaFocusTemplate mTemplate;
    private int mFocuseHeight = 160;

    private HaFocusTemplate(Context ctx) {
        mFocuseHeight = Math.min(HaScreen.getScreenWidth(ctx), HaScreen.getScreenHeight(ctx)) / 2;
    }

    public static HaFocusTemplate getInstance(Context ctx){
        if (mTemplate == null)
            mTemplate = new HaFocusTemplate(ctx);
        return mTemplate;
    }
    
    public int getFocusHeight(){
        return mFocuseHeight;
    }

    public View getView(View view, MainMedia content, String template,Context context) {
        return getView(view, content, Template.getTemplate(template),context);
    }

    public View getView(View view, MainMedia content, Template template,Context context) {

        switch (template) {
        case FOCUSE:
            return getTemplateFocus(view, content,context);
        default:
            break;
        }
        return view;
    }

    @SuppressLint("InflateParams") 
    private View getTemplateFocus(View view, MainMedia content,Context context) {
        FocusHolder holder = null;
        if (view != null && view.getTag() != null && view.getTag() instanceof FocusHolder) {
            holder = (FocusHolder) view.getTag();
        } else {
            holder = new FocusHolder();
            view = LayoutInflater.from(context).inflate(R.layout.view_template_focus, null, false);
            holder.icon = (ImageView) view.findViewById(R.id.focus_icon);
            if (mFocuseHeight > 0)
                holder.icon.getLayoutParams().height = mFocuseHeight;
            holder.text = (TextView) view.findViewById(R.id.focus_title);
            view.setTag(holder);
        }

        HaImageLoader.displayFocus(content.getImgh_url(), holder.icon);
        holder.text.setText(content.getTitle());
        return view;
    }
    
    public static class FocusHolder {
        public ImageView icon;
        public TextView text;
    }
    
    public static enum Template {
        FOCUSE("focuse"), UNKNOWN("unknown");
        public String name;

        private Template(String name) {
            this.name = name;
        }

        public static Template getTemplate(String name) {
            for (Template template : Template.values()) {
                if (TextUtils.equals(name, template.name))
                    return template;
            }
            return UNKNOWN;
        }
    }


}
