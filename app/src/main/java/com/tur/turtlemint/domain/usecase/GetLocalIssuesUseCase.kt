package com.tur.turtlemint.domain.usecase

import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.domain.repository.TurtleRepository
import com.tur.turtlemint.domain.usecase.base.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

/**
 * An interactor that calls the actual implementation of [ViewModel](which is injected by DI)
 * it handles the response that returns data &
 * contains a list of actions, event steps
 */
class GetLocalIssuesUseCase @Inject constructor(
    private val repository: TurtleRepository
) : SingleUseCase<List<IssueEntity>>() {

    override fun buildUseCaseSingle(): Single<List<IssueEntity>> {
        return repository.getIssuesLocal()
    }

}
