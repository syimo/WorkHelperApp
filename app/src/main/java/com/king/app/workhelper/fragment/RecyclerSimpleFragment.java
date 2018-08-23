package com.king.app.workhelper.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.king.app.workhelper.R;
import com.king.app.workhelper.adapter.recyclerview.SimpleRecyclerAdapter;
import com.king.app.workhelper.common.AppBaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author VanceKing
 * @since 2018/8/22.
 */
public class RecyclerSimpleFragment extends AppBaseFragment {
    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;
    private SimpleRecyclerAdapter mAdapter;

    @Override protected int getContentLayout() {
        return R.layout.fragment_simple_recycler;
    }

    @Override protected void initContentView(View rootView) {
        super.initContentView(rootView);

        //设置 false。解决 NestScrollView 嵌套 RecyclerView 时 notifyItemRangeInserted() 无效的问题。
        //但是会渲染整个 List，不管是不是显示在屏幕上。
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SimpleRecyclerAdapter(SimpleRecyclerAdapter.fakeData(30));
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.floating_button)
    public void onFloatingButtonClick() {
        mAdapter.appendList(SimpleRecyclerAdapter.fakeData("aaa", 5));
//        mAdapter.setAdapterData(SimpleRecyclerAdapter.fakeData("aaa", 5), true);
    }
}
