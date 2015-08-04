
package com.haha.video.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haha.common.das.HaDas;
import com.haha.common.das.HaDasReq;
import com.haha.common.das.HaHandler;
import com.haha.common.entity.MainBlock;
import com.haha.common.entity.MainEntity;
import com.haha.common.entity.MainMedia;
import com.haha.common.logger.Logcat;
import com.haha.hwidget.HaCycleView;
import com.haha.hwidget.HaSectionView;
import com.haha.hwidget.HaSectionView.HeadItem;
import com.haha.hwidget.HaSectionView.OnHeadClickListener;
import com.haha.hwidget.adapter.HaBaseAdapter;
import com.haha.hwidget.adapter.HaBaseAdapter.OnItemLoadingView;
import com.haha.video.R;
import com.haha.video.ui.FocusItemView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * recommend tag fragment in main page
 * 
 * @author xj
 */
public class MainAllFragment extends Fragment {
    
    private final static String TAG = "MainAllFragment";

    private PullToRefreshListView mRefreshListView;
    private HaCycleView<FocusItemView> mFocusCycleView;
    
    private boolean startFocusAutoScroll = true;
    private boolean isRequestHomePageData;

    private boolean alreadyLoadView;
    
    /*fouces下面块状数据列表*/
    private HashMap<String, BlockItemView> mBlockItems = new HashMap<String, BlockItemView>();

    private HaBaseAdapter<MoreTypeItemView> mRefreshAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_all, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View root = getView();
        initView(root);
        initListener(root);
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            startFocusAutoScroll();
            if (!alreadyLoadView) {
                alreadyLoadView = true;
                requestHomePageData();
            }
        } else {
            stopFocusAutoScroll();
        }
    }

    private void requestHomePageData() {
        if (isRequestHomePageData){
            onRequestHomePageDataEnd();
            return;
        }
        isRequestHomePageData = true;
        Logcat.i(TAG, "requestHomePageData");
        try {
            HaDas.getInstance().get(HaDasReq.BAIDU_MAIN_INDEX, null, mBlockDasHandler);
        } catch (Exception e) {
            onRequestHomePageDataEnd();
            Logcat.e(TAG, "requestHomePageData", e);
        }
    }

    private void initListener(View root) {
        // TODO Auto-generated method stub

    }

    private void initView(View root) {
        mRefreshListView = (PullToRefreshListView) root.findViewById(R.id.home_listview);
        addFocusCycleView();
    }

    private void addFocusCycleView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_cycleview, null);
        mFocusCycleView = (HaCycleView<FocusItemView>) view.findViewById(R.id.cycleview);
        mRefreshListView.getRefreshableView().addHeaderView(mFocusCycleView);
        //mFocusCycleView.setOnItemClickListener(mCycleItemClickListener);
        //mFocusCycleView.setOnCycleItemSelectedListener(mCycleItemSelectedListener);

    }
    
    private void startFocusAutoScroll() {
        if (mFocusCycleView != null && startFocusAutoScroll) {
            mFocusCycleView.startAutoScroll();
        }
    }

    private void stopFocusAutoScroll() {
        if (mFocusCycleView != null) {
            mFocusCycleView.stopAutoScroll();
        }
    }
    
    private void onRequestHomePageDataEnd() {
        isRequestHomePageData = false;
        mRefreshListView.onRefreshComplete();
    }
    
    private void refreshHomePageData(MainEntity data) {
        
        Logcat.i(TAG, "data:"+data.getSlices().size());

        /**刷新focus*/
        refreshFocus(data.getSlices().get(0));

        /**focus下的view展示*/
        refreshBlocks(data.getSlices());
    }
    
    private void refreshFocus(MainBlock focuses) {

        OnItemLoadingView<FocusItemView> loadingView = new OnItemLoadingView<FocusItemView>() {
            @Override
            public View getView(View convertView, FocusItemView focusItem) {
                try {
                    return focusItem.getView(getActivity(), convertView);
                } catch (Exception e) {
                    Logcat.e(TAG, "error:"+e.getMessage());
                    return new View(getActivity());
                }
            }
        };

        List<FocusItemView> focusItems = new ArrayList<FocusItemView>();
        for (MainMedia content : focuses.getHot()) {
            Logcat.i(TAG, content.toString());
            //if ("promotion".equals(content.getCorner_mark())) //remove ad;
                //continue;
            focusItems.add(new FocusItemView(content));
        }

        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.main_viewpager);
        mFocusCycleView.init(focusItems, loadingView, viewGroup, true);
    }
    
    private void refreshBlocks(List<MainBlock> blocks) {
        mBlockItems.clear();

        List<MoreTypeItemView> itemViews = createMoreTypeItems(blocks);
        if (mRefreshAdapter == null) {
            mRefreshAdapter = new HaBaseAdapter<MoreTypeItemView>(itemViews, new OnItemLoadingView<MoreTypeItemView>() {
                @Override
                public View getView(View convertView, MoreTypeItemView itemView) {
                    return itemView.getView(convertView);
                }
            });
            mRefreshListView.setAdapter(mRefreshAdapter);
        } else {
            mRefreshAdapter.reload(itemViews);
        }
    }
    
    private List<MoreTypeItemView> createMoreTypeItems(List<MainBlock> blocks){
        ArrayList<MoreTypeItemView> itemViews = new ArrayList<MoreTypeItemView>();
        for (MainBlock block : blocks) {
            MoreTypeItemView itemView = createMoreTypeItems(block);
            if (itemView == null)
                continue;
            itemViews.add(itemView);
        }
        return itemViews;
    }
    
    private MoreTypeItemView createMoreTypeItems(MainBlock block){
        if (block == null)
            return null;
        
        List<MainMedia> contents = block.getHot();

        BlockItemView itemView = new BlockItemView(block);
        mBlockItems.put(block.getTag(), itemView);
        return itemView;
    }
    
    private HaHandler mBlockDasHandler = new HaHandler() {
        
        @Override
        public void onSuccess(SResp sresp) {
            try {
                refreshHomePageData((MainEntity) sresp.getEntity());
            } catch (Exception e) {
                Logcat.e(TAG, "error:"+e.getMessage());
            }
            onRequestHomePageDataEnd();
            Logcat.i(TAG, "onSuccess-requestdata");
        }
        
        @Override
        public void onFailed(EResp eresp) {
            // TODO Auto-generated method stub
            
        }
    };
    
    public static class MoreTypeItemView{
        protected View oldView = null;
        private boolean usable = true;
        private RefreshMoreTypeItemView mRefreshView = null;
        public MoreTypeItemView() {
        }

        public MoreTypeItemView(View view) {
            oldView = view;
        }

        public MoreTypeItemView(View view, RefreshMoreTypeItemView refreshView) {
            oldView = view;
            mRefreshView = refreshView;
        }

        public void unusable() {
            usable = false;
        }

        public View getView(View view) {
            if (useOld()){
                if (mRefreshView != null)
                    mRefreshView.refresh(oldView);
                return oldView;
            }
            return view;
        }

        protected boolean useOld() {
            return usable && oldView != null;
        }

    }

    public class BlockItemView extends MoreTypeItemView {

        private MainBlock mBlock = null;
        public BlockItemView(MainBlock block) {
            super();
            mBlock = block;
        }

        protected void update(MainMedia content, int position) {
            if (useOld()) {
                try {
                    getBlockView().add(content, position);
                } catch (Exception e) {
                }
            } else {
                try {
                    mBlock.getHot().add(position, content);
                    mRefreshAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }
        }

        protected int getCount() {
            if (useOld())
                return getBlockView().getCount();
            try {
                return mBlock.getHot().size();
            } catch (Exception e) {
            }
            return 0;
        }

        @SuppressWarnings("unchecked")
        private HaSectionView<MainMedia> getBlockView() {
            return (HaSectionView<MainMedia>) oldView;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(View view) {
            HaSectionView<MainMedia> sView = null;
            if (view != null && view instanceof HaSectionView && ((HaSectionView<MainMedia>) view).isReuseabule())
                sView = (HaSectionView<MainMedia>) view;

            return getSectionView(mBlock, sView);
        }

    }
    
    private HaSectionView<MainMedia> getSectionView(final MainBlock block, HaSectionView<MainMedia> view) {
        if (block == null)
            return null;

        List<MainMedia> contents = block.getHot();
        if (contents== null || contents.size() <= 0)
            return null;

        HaSectionView<MainMedia> sectionView =  view;
        if (sectionView == null)
            sectionView = new HaSectionView<MainMedia>(getActivity());

        sectionView.setVerticalSpacing(0);

        setSectionViewHead(sectionView, block);

        int numColumns = 2;
        sectionView.setNumColumns(numColumns);
        sectionView.setCompleteRow(true);
        sectionView.init(contents, new HaBaseAdapter.OnItemLoadingView<MainMedia>() {

            @Override
            public View getView(View convertView, MainMedia item) {
                TextView view = new TextView(getActivity());
                view.setText("test");
                return view;
                //return FSMediaTemplate.getInstance(getActivity()).getView(getActivity(), convertView, content, template);
            }
//            @Override
//            public View getView(View convertView, Content content) {
//                
//            }
        });

        //setSectionViewItemClickListener(sectionView, block);
        return sectionView;
    }
    

    private void setSectionViewHead(HaSectionView<MainMedia> sectionView, MainBlock block) {
        sectionView.setTitleText(block.getName());
        OnHeadClickListener headClickListener = new OnHeadClickListener() {

            @Override
            public void onClick(HeadItem item) {
                switch (item) {
                case MORE:
                case TITLE:
                case ALL:
                    break;

                default:
                    break;
                }
            }
        };
        sectionView.setMoreVisibility(View.VISIBLE);
        sectionView.setOnClickListener(headClickListener);
    }

    public static interface RefreshMoreTypeItemView{
        public void refresh(View view);
    }

}
