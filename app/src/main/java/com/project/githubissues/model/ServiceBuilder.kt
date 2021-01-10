package com.project.githubissues.model

import com.project.githubissues.model.issuelist.IssueService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object ServiceBuilder {
//    private val client = OkHttpClient.Builder().build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://api.github.com/repos/square/okhttp/issues")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    fun getService(): IssueService {
//        return retrofit.create(IssueService::class.java)
//    }
//}

class ServiceBuilder {
    private val serviceBuilder: IssueService

    init {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/repos/square/okhttp/issues")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        serviceBuilder = retrofit.create(IssueService::class.java)
    }
}