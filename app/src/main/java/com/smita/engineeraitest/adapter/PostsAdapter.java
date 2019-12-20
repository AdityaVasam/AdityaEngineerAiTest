package com.smita.engineeraitest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smita.engineeraitest.R;
import com.smita.engineeraitest.Utils.Utilities;
import com.smita.engineeraitest.interfaces.PostsItesmClickListener;
import com.smita.engineeraitest.model.PostsModel;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MyHolder> {
    private List<PostsModel> postsModelList;
    private PostsItesmClickListener postsItesmClickListener;
    private int selectedPostCount = 0;

    public PostsAdapter(List<PostsModel> postsModelList,PostsItesmClickListener postsItesmClickListener){
        this.postsModelList = postsModelList;
        this.postsItesmClickListener = postsItesmClickListener;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_layout,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder,final int position) {
        PostsModel model = postsModelList.get(position);
        holder.tv_post_title.setText(model.getTitle());
        holder.tv_post_date.setText(Utilities.getFormattedDate(model.getCreated_at()));
        holder.post_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    selectedPostCount++;
                }else {
                    selectedPostCount--;
                }
                postsItesmClickListener.onItemClick(position,selectedPostCount);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.post_switch.isChecked()){
                    holder.post_switch.setChecked(true);
                }else {
                    holder.post_switch.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tv_post_title,tv_post_date;
        private Switch post_switch;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_post_date = itemView.findViewById(R.id.tv_post_title_desc);
            tv_post_title = itemView.findViewById(R.id.tv_post_title);
            post_switch = itemView.findViewById(R.id.tv_post_switch);
        }
    }
}
