
package com.haha.video.ui;

import com.haha.common.entity.MainMedia;
import com.haha.common.logger.Logcat;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class FocusItemView {
    
    private final static String TAG = "FocusItemView";
    private MainMedia mContent;

    public FocusItemView(MainMedia content) {
        this.mContent = content;
    }

    public MainMedia getContent() {
        return mContent;
    }

    public View getView(Context ctx, View convertView) {
        Logcat.i(TAG, "getView");
        return HaFocusTemplate.getInstance(ctx).getView(convertView, mContent,
                HaFocusTemplate.Template.FOCUSE,ctx);
    }

    public void click(Activity ctx) {
        //TODO
    }

    public void reportView(Context ctx) {
    }
}
