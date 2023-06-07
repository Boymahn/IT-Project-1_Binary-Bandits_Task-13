package com.example.taskoptimizer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WitAiService {
    @POST("message")
    Call<WitAiResponse> sendMessage(@Header("Authorization") String authorization, @Body String message);
}