package com.example.assmohamedfaridyoutube;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etSearch;
    private MaterialButton btnSearch;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private VideoAdapter adapter;
    private List<VideoModel> videoList;

    private static final String API_KEY =
            "AIzaSyAEk7F_bbhTFUWxwJXDn5fzxviwCJYk7EY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        videoList = new ArrayList<>();

        adapter = new VideoAdapter(this, videoList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {

            String query = etSearch.getText()
                    .toString()
                    .trim();

            if (query.isEmpty()) {

                Toast.makeText(
                        MainActivity.this,
                        "Please enter search text",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            searchVideos(query);
        });
    }

    private void searchVideos(String query) {

        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService =
                ApiClient.getClient()
                        .create(ApiService.class);

        apiService.searchVideos(
                        "snippet",
                        "video",
                        query,
                        10,
                        API_KEY
                )
                .enqueue(new Callback<JsonObject>() {

                    @Override
                    public void onResponse(
                            Call<JsonObject> call,
                            Response<JsonObject> response) {

                        progressBar.setVisibility(View.GONE);

                        if (!response.isSuccessful()
                                || response.body() == null) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "Error loading data",
                                    Toast.LENGTH_SHORT
                            ).show();

                            return;
                        }

                        videoList.clear();

                        JsonArray items =
                                response.body()
                                        .getAsJsonArray("items");

                        if (items.size() == 0) {

                            Toast.makeText(
                                    MainActivity.this,
                                    "No videos found",
                                    Toast.LENGTH_SHORT
                            ).show();

                            adapter.notifyDataSetChanged();
                            return;
                        }

                        for (int i = 0; i < items.size(); i++) {

                            JsonObject item =
                                    items.get(i).getAsJsonObject();

                            JsonObject snippet =
                                    item.getAsJsonObject("snippet");

                            String title =
                                    snippet.get("title")
                                            .getAsString();

                            String description =
                                    snippet.get("description")
                                            .getAsString();

                            String channel =
                                    snippet.get("channelTitle")
                                            .getAsString();

                            String date =
                                    snippet.get("publishedAt")
                                            .getAsString();

                            String thumbnail =
                                    snippet.getAsJsonObject("thumbnails")
                                            .getAsJsonObject("high")
                                            .get("url")
                                            .getAsString();

                            videoList.add(
                                    new VideoModel(
                                            title,
                                            description,
                                            channel,
                                            date,
                                            thumbnail
                                    )
                            );
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(
                            Call<JsonObject> call,
                            Throwable t) {

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(
                                MainActivity.this,
                                "Network Error",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}