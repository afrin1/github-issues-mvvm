package com.project.githubissues.model.issuelist

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface IssueService {
    companion object {
        fun create(): IssueService {
            val client = OkHttpClient.Builder().build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(IssueService::class.java)
        }
    }

    @GET("repos/square/okhttp/issues")
    fun getIssuesList(): Call<List<Issue>>

}