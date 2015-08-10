
package com.haha.video.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.haha.video.R;

public class ChannelFragment extends Fragment implements OnItemClickListener {

    private final static int[] imageArray = {
            R.drawable.channel_gridview_movie, R.drawable.channel_gridview_tv, R.drawable.channel_gridview_variety,
            R.drawable.channel_gridview_comic
    };
    private GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_channel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        mGridView = (GridView) getView().findViewById(R.id.main_channle_grid);
        ChannelAdapter channelAdapter = new ChannelAdapter();
        mGridView.setAdapter(channelAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub

    }

    private class ChannelAdapter extends BaseAdapter {
        
        private LayoutInflater mInflater = LayoutInflater.from(getActivity());

        @Override
        public int getCount() {
            return imageArray.length;
        }

        @Override
        public Object getItem(int position) {
            return imageArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = this.mInflater.inflate(R.layout.channel_gridview_item, null);
            ((ImageView)convertView .findViewById(R.id.iv_item)).setBackgroundResource(imageArray[position]);
            return convertView;
        }

    }
}
