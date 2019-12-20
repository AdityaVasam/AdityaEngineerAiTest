package com.smita.engineeraitest.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smita.engineeraitest.R;
import com.smita.engineeraitest.adapter.PostsAdapter;
import com.smita.engineeraitest.interfaces.PostsItesmClickListener;
import com.smita.engineeraitest.model.PostsModel;
import com.smita.engineeraitest.viewmodel.PostsViewModel;

import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {
    private RecyclerView posts_recyclerview;
    private ProgressBar postsProgressBar;
    private List<PostsModel> postsModelList;
    private PostsAdapter postsAdapter;
    private PostsViewModel postsViewModel;
    private TextView postSelectedCountTv;
    private int lastPageIterated = 0;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_activity_layout);
        initViews();
        initActionBar();
        postsModelList = new ArrayList<>();
        initAdapter();
        initRecyclerView();
        postsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        postsProgressBar.setVisibility(View.VISIBLE);
        postsViewModel.getPostsFromApiWithPageNo(lastPageIterated);
        addRecyclerScrollListener();
        observePostsListLiveData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastPageIterated = 0;
                postsModelList.clear();
                postsAdapter.notifyDataSetChanged();
                postsViewModel.getPostsFromApiWithPageNo(lastPageIterated);
                postSelectedCountTv.setText("0");
            }
        });
    }

    private void initViews(){
        posts_recyclerview = findViewById(R.id.recyclerview_posts);
        postsProgressBar = findViewById(R.id.posts_progress_bar);
        swipeRefreshLayout = findViewById(R.id.posts_swipe_refresh);
    }

    private void initRecyclerView(){
        posts_recyclerview.setHasFixedSize(true);
        posts_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        posts_recyclerview.setAdapter(postsAdapter);
    }

    private void initAdapter(){
        postsAdapter = new PostsAdapter(postsModelList, new PostsItesmClickListener() {
            @Override
            public void onItemClick(int position, int switchCheckedCount) {
                postSelectedCountTv.setText(String.valueOf(switchCheckedCount));
            }
        });
        postsAdapter.setHasStableIds(true);
    }

    private void observePostsListLiveData(){
        postsViewModel.getPostsLiveData().observe(this, new Observer<List<PostsModel>>() {
            @Override
            public void onChanged(List<PostsModel> postsModels) {
                if (postsModels.get(0).isLastPageReached()){
                    postsProgressBar.setVisibility(View.GONE);
                }else {
                    postsProgressBar.setVisibility(View.GONE);
                    postsModelList.addAll(postsModels);
                    postsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initActionBar(){
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.posts_nav_bar);
        postSelectedCountTv = getSupportActionBar().getCustomView().findViewById(R.id.tv_posts_selected_count);
    }

    private void addRecyclerScrollListener(){
        posts_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)){
                    postsProgressBar.setVisibility(View.VISIBLE);
                    postsViewModel.getPostsFromApiWithPageNo(lastPageIterated++);
                }
            }
        });
    }
}
