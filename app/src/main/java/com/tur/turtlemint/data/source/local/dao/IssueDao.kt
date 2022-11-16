package com.tur.turtlemint.data.source.local.dao

import androidx.room.*
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import io.reactivex.Single

/**
 * it provides access to [User] underlying database
 * */
@Dao
interface IssueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(issue:IssueEntity): Single<Long>

    @Query("SELECT * FROM Issue")
    fun loadAll(): Single<List<IssueEntity>>

    @Delete
    fun delete(issue: IssueEntity)

    @Query("DELETE FROM Issue")
    fun deleteAll()

    @Query("SELECT * FROM Issue where id = :issueId")
    fun loadOneByIssueId(issueId: Long): IssueEntity?

    @Update
    fun update(issue: IssueEntity)
}
