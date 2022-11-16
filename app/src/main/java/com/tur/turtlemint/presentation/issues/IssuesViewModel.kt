package com.tur.turtlemint.presentation.issues

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity
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
class IssuesViewModel @Inject constructor(private val getIssuesUseCase: GetIssuesUseCase,
                                          private val getLocalIssuesUseCase: GetLocalIssuesUseCase,
                                          private val saveIssuesUseCase: SaveIssuesUseCase ) :
    ViewModel() {

    val issuesReceivedLiveData = MutableLiveData<List<IssueResponse>>()
    val isLoad = MutableLiveData<Boolean>()
    val issueData = MutableLiveData<List<IssueResponse>>()

    init {
        isLoad.value = false
    }

    fun set(issues: List<IssueResponse>) = run { issueData.value = issues }

    fun loadIssues() {
        getIssuesUseCase.execute(
            onSuccess = {
                isLoad.value = true
                issuesReceivedLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    val issuesReceivedLocalLiveData = MutableLiveData<List<IssueEntity>>()
    fun loadLocalIssues() {
        getLocalIssuesUseCase.execute(
            onSuccess = {
                isLoad.value = true
                issuesReceivedLocalLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }

    val issuesSaveLocalLiveData = MutableLiveData<Long>()
    fun saveIssuesLocal(iss: List<IssueEntity>) {
        saveIssuesUseCase.saveIssues(iss)
        saveIssuesUseCase.execute(
            onSuccess = {
                isLoad.value = true
                issuesSaveLocalLiveData.value = it
            },
            onError = {
                it.printStackTrace()
            }
        )
    }


}
