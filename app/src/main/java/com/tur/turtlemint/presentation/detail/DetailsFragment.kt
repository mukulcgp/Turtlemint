package com.tur.turtlemint.presentation.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.example.IssueResponse
import com.tur.turtlemint.R
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.databinding.FragmentDetailsBinding
import com.tur.turtlemint.databinding.FragmentIssuesBinding
import com.tur.turtlemint.domain.model.comments.Comments
import com.tur.turtlemint.presentation.issues.CardAdapter
import com.tur.turtlemint.presentation.issues.IssuesViewModel
import com.tur.turtlemint.presentation.loadImage
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if(isOnline()) {
            viewModel.loadComments(issue.commentsUrl!!)
        } else {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
        }

    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding?.root

        viewModel.issuesCommentsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    Log.i("Issues", "list : $it")
                    setupRecyclerView(it)
                }
            }
        )

        return view!!
    }

    private fun setupRecyclerView(albums: List<Comments>) = binding!!.commentsRecyclerView.apply {
        try {
            layoutManager = GridLayoutManager(requireContext(), 1)

            adapter = CardAdapterComments(albums)


        } catch (e : Exception){

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.ivDetail?.loadImage(issue.user!!.avatarUrl!!, binding?.photoProgressBarDetail!!)
        binding?.tvNameDetail?.text = issue.title
        binding?.tvDetail?.text = issue.body
        binding?.tvUserDetail?.text = issue.user!!.login

        var uDate = issue.updatedAt!!.substring(0,10)

        var dd = uDate.substring(8, 10)
        var mm = uDate.substring(5, 7)
        var yyyy = uDate.substring(0, 4)

        binding?.tvUpdatedDetail?.text = dd+"-"+mm+"-"+yyyy
    }

    companion object {
        lateinit var issue: IssueResponse
        val FRAGMENT_NAME = DetailsFragment::class.java.name
        @JvmStatic
        fun newInstance(issueResponse: IssueResponse) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                   issue = issueResponse
                }
            }
    }
}