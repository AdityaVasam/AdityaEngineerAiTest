package com.smita.engineeraitest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.smita.engineeraitest.model.PostsModel;
import com.smita.engineeraitest.repository.PostsRepository;

import java.util.List;

public class PostsViewModel extends AndroidViewModel {
    private PostsRepository repository;

    public PostsViewModel(@NonNull Application application) {
        super(application);
        repository = new PostsRepository();
    }

    public LiveData<List<PostsModel>> getPostsLiveData(){
        return repository.getPostsListLiveData();
    }

    public void getPostsFromApiWithPageNo(int pageNo){
        repository.getPostsFromApi(pageNo);
    }
}
