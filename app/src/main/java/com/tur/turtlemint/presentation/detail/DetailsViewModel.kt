package com.tur.turtlemint.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.domain.model.comments.Comments
import com.tur.turtlemint.domain.usecase.GetCommentsUseCase
import com.tur.turtlemint.domain.usecase.GetIssuesUseCase
import com.tur.turtlemint.domain.usecase.GetLocalIssuesUseCase
import com.tur.turtlemint.domain.usecase.SaveIssuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**To store & manage UI-related data in a lifecycle conscious way!
 * this class allows data to survive configuration changes such as screen rotation
 * by interacting with [GetIssuesUseCase]
 *
 * */
@HiltViewModel
class DetailsViewModel @Inject constructor(private val getCommentsUseCase: GetCommentsUseCase) :
    ViewModel() {

    val issuesCommentsLiveData = MutableLiveData<List<Comments>>()
    val isLoad = MutableLiveData<Boolean>()

    init {
        isLoad.value = false
    }

    fun loadComments(urlComments: String) {
        if (urlComments == null) return
        getCommentsUseCase.saveCommentsUrl(urlComments)
        getCommentsUseCase.execute(
            onSuccess = {
                isLoad.value = true
                issuesCommentsLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

}
