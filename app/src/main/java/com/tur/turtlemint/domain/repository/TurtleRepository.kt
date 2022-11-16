package com.tur.turtlemint.domain.repository

import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.domain.model.comments.Comments
import io.reactivex.Single

/**
 * To make an interaction between [TurtleRepositoryImp] & [UseCase]
 * */
interface TurtleRepository {
    fun getIssues(): Single<List<IssueResponse>>
    fun getIssuesLocal(): Single<List<IssueEntity>>
    fun addIssuesLocal(issues: List<IssueEntity>): Single<Long>
    fun getComments(commentsUrl: String): Single<List<Comments>>
}
