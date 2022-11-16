package com.tur.turtlemint.data.source.remote


import com.example.example.IssueResponse
import com.tur.turtlemint.domain.model.comments.Comments
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface RetrofitService {

    @GET("okhttp/issues")
    fun getIssues(): Single<List<IssueResponse>>

    @GET
    fun getComments(@Url fileUrl: String): Single<List<Comments>>

}
