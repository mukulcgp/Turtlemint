package com.tur.turtlemint.data.repository


import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.AppDatabase
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.data.source.remote.RetrofitService
import com.tur.turtlemint.domain.model.comments.Comments
import com.tur.turtlemint.domain.repository.TurtleRepository
import io.reactivex.Single

/**
 * This repository is responsible for
 * fetching data[TurtleMint] from server or db
 * */
class TurtleRepositoryImp(
    private val database: AppDatabase,
    private val retrofitService: RetrofitService
) : TurtleRepository {

    private lateinit var result: Single<Long>

    override fun getIssues(): Single<List<IssueResponse>> {
        return retrofitService.getIssues()
    }

    override fun getIssuesLocal(): Single<List<IssueEntity>> {
        return database.issueDao.loadAll()
    }

    override fun addIssuesLocal(issues: List<IssueEntity>): Single<Long> {

            database.issueDao.deleteAll()
            for(issue in issues) {
                val model = IssueEntity(issue.id, issue.title, issue.desc, issue.uName, issue.uDate)
                result = database.issueDao.insert(model)
            }
             return result
    }

    override fun getComments(commentsUrl: String): Single<List<Comments>> {
        return retrofitService.getComments(commentsUrl)
    }

}
