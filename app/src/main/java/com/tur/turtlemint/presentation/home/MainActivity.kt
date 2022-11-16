package com.tur.turtlemint.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.example.IssueResponse
import com.tur.turtlemint.R
import com.tur.turtlemint.databinding.ActivityMainBinding
import com.tur.turtlemint.presentation.OnIssuesCallback
import com.tur.turtlemint.presentation.detail.DetailsFragment
import com.tur.turtlemint.presentation.issues.IssuesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnIssuesCallback {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            navigateToRestaurantPage()
        }

    }

    private fun navigateToRestaurantPage() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frag_container,
                IssuesFragment.newInstance("",""),
                IssuesFragment.FRAGMENT_NAME
            ).commitAllowingStateLoss()
    }

    override fun navigateToDetailPage(issue: IssueResponse) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frag_container,
                DetailsFragment.newInstance(issue),
                DetailsFragment.FRAGMENT_NAME
            )
            .addToBackStack(DetailsFragment.FRAGMENT_NAME)
            .commitAllowingStateLoss()
    }
}