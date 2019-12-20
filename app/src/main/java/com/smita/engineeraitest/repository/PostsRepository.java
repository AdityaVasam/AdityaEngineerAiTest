package com.smita.engineeraitest.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.smita.engineeraitest.model.PostsModel;
import com.smita.engineeraitest.retrofit.APIInterface;
import com.smita.engineeraitest.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {
    private APIInterface apiInterface;
    private MutableLiveData<List<PostsModel>> postsListMutableLiveData = new MutableLiveData<>();
    private int lastPageIndex = 0;

    public PostsRepository(){
        apiInterface = RetrofitClient.getRetrofitClient().create(APIInterface.class);
    }

    public LiveData<List<PostsModel>> getPostsListLiveData(){
        if (postsListMutableLiveData == null){
            postsListMutableLiveData = new MutableLiveData<>();
        }
        return postsListMutableLiveData;
    }

    public void getPostsFromApi(final int pageNo){
        if (pageNo <= lastPageIndex) {
            apiInterface.getPostsFromApi("", pageNo).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if (response.isSuccessful()){
                        List<PostsModel> postsModelList = new ArrayList<>();
                        lastPageIndex = response.body().getAsJsonObject().get("nbPages").getAsInt();
                        JsonArray postsArray = response.body().getAsJsonObject().get("hits").getAsJsonArray();
                        for (int i=0; i<postsArray.size();i++){
                            String postTitle = "",postCreatedDate = "";
                            if (!postsArray.get(i).getAsJsonObject().get("title").isJsonNull()){
                                postTitle = postsArray.get(i).getAsJsonObject().get("title").getAsString();
                            }else {
                                postTitle = "null";
                            }
                            if (!postsArray.get(i).getAsJsonObject().get("created_at").isJsonNull()){
                                postCreatedDate = postsArray.get(i).getAsJsonObject().get("created_at").getAsString();
                            }else {
                                postCreatedDate = "null";
                            }
                            PostsModel postsModel = new PostsModel();
                            postsModel.setTitle(postTitle);
                            postsModel.setCreated_at(postCreatedDate);
                            postsModel.setSwitchChecked(false);
                            postsModelList.add(postsModel);
                        }
                        postsListMutableLiveData.postValue(postsModelList);
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {

                }
            });
        }else {

        }
    }
}
