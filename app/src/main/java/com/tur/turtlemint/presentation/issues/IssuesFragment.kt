package com.tur.turtlemint.presentation.issues

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.example.IssueResponse
import com.tur.turtlemint.data.source.local.entity.IssueEntity
import com.tur.turtlemint.databinding.FragmentIssuesBinding
import com.tur.turtlemint.presentation.OnIssuesCallback
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IssuesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class IssuesFragment : Fragment(), CardAdapter.IssueItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentIssuesBinding? = null
    private val binding get() = _binding

    private val viewModel: IssuesViewModel by viewModels()

    private val issues = mutableListOf<IssueResponse>()
    var issuesLocal = mutableListOf<IssueEntity>()

    private var mCallback: OnIssuesCallback? = null

    var commentUrl = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnIssuesCallback) {
            mCallback = context
        } else throw ClassCastException(context.toString() + "must implement OnIssuesCallback!")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        if(isOnline()) {
            viewModel.loadIssues()
        } else {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_LONG).show()
            viewModel.loadLocalIssues()
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
        _binding = FragmentIssuesBinding.inflate(inflater, container, false)
        val view = binding?.root

        viewModel.isLoad.observe(
            viewLifecycleOwner,
            Observer {
                it?.let { visibility ->
                    binding?.issuesProgressBar?.visibility = if (visibility) View.GONE else View.VISIBLE
                }
            }
        )

        viewModel.issuesReceivedLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    Log.i("Issues", "list : $it")
                    for( trueEntity in it ) {
                        this.issues.add(trueEntity)
                        val model = IssueEntity(trueEntity.id!!.toLong(), trueEntity.title ?: "", trueEntity.body ?: "", trueEntity.user?.login ?: "", trueEntity.updatedAt ?: "")
                        issuesLocal.add(model)
                    }
                    setupRecyclerView(it)
                    viewModel.saveIssuesLocal(issuesLocal)
                }
            }
        )


        viewModel.issuesReceivedLocalLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    Log.i("Issues", "list : $it")
                    setupLocalRecyclerView(it)
                }
            }
        )


        viewModel.issuesSaveLocalLiveData.observe(
            viewLifecycleOwner,
            Observer {
                it?.let {
                    Log.i("Isssues", "Saved : $it")
                }
            }
        )


        return view!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //progressView = binding!!.tvD




    }

    private fun setupRecyclerView(albums: List<IssueResponse>) = binding!!.issuesRecyclerView.apply {
        try {
            layoutManager = GridLayoutManager(requireContext(), 1)

            adapter = CardAdapter(albums, this@IssuesFragment)


        } catch (e : Exception){

        }
    }

    private fun setupLocalRecyclerView(albums: List<IssueEntity>) = binding!!.issuesRecyclerView.apply {
        try {
            layoutManager = GridLayoutManager(requireContext(), 1)

            adapter = CardAdapterLocal(albums)


        } catch (e : Exception){

        }
    }


    override fun onDetach() {
        super.onDetach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestaurantFragment.
         */
        // TODO: Rename and change types and number of parameters

        val FRAGMENT_NAME = IssuesFragment::class.java.name

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IssuesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun itemIssueSelected(item: IssueResponse) {
        mCallback?.navigateToDetailPage(item)
    }


}