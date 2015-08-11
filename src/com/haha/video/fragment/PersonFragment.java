package com.haha.video.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.haha.video.R;
import com.haha.video.adapter.PersonalAdapter;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class PersonFragment extends Fragment implements OnItemClickListener{
    
    private String[] mItems;
    private int[] mImages = {R.drawable.ic_personal_feedback,R.drawable.ic_personal_upgrade,R.drawable.ic_personal_share};
    private ListView mListView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_person, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVIew();
        mItems = getResources().getStringArray(R.array.personal_items);
        PersonalAdapter personalAdapter = new PersonalAdapter(mItems, mImages, getActivity());
        mListView.setAdapter(personalAdapter);
    }

    private void initVIew() {
        mListView = (ListView)getView().findViewById(R.id.view_main_pesonal_listview);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      switch (position) {
        case 0:
            FeedbackAgent agent = new FeedbackAgent(getActivity());
            agent.startFeedbackActivity();
            break;
        case 1:
            UmengUpdateAgent.forceUpdate(getActivity());
            break;
        case 2:
            break;

        default:
            break;
    }
    }
}
