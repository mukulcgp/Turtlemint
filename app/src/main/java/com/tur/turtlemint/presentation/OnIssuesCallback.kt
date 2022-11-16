package com.tur.turtlemint.presentation

import com.example.example.IssueResponse

/**
 * To make an interaction between [MainActivity] & its children
 * */
interface OnIssuesCallback {
    fun navigateToDetailPage(issues: IssueResponse)
}
