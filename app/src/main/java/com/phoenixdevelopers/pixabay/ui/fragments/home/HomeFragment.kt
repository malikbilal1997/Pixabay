package com.phoenixdevelopers.pixabay.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.phoenixdevelopers.pixabay.R
import com.phoenixdevelopers.pixabay.databinding.DialogDetailBinding
import com.phoenixdevelopers.pixabay.databinding.FragmentHomeBinding
import com.phoenixdevelopers.pixabay.enums.GridType
import com.phoenixdevelopers.pixabay.ui.fragments.home.adapters.ImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var imageAdapter: ImageAdapter

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {

        _binding = FragmentHomeBinding
            .inflate(layoutInflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        initClickListener()

        initLayoutObserver()

        initImageListScroll()

        initProgressObserver()

        initImageListObserver()

    }

    private fun initRecyclerView() {

        imageAdapter = ImageAdapter {
            showDetailDialog(it.largeFormatImage)
        }

        with(binding.imagesRecyclerView) {
            adapter = imageAdapter
        }

    }

    private fun initClickListener() {

        binding.layoutButton.setOnClickListener {
            viewModel.changeGridColumns()
        }
    }

    private fun initLayoutObserver() {

        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(RESUMED) {

                viewModel.spanCount.collect {

                    when (it) {

                        GridType.TWO_COLUMNS -> {

                            binding.layoutButton.setImageResource(R.drawable.ic_grid)

                            binding.imagesRecyclerView.layoutManager = GridLayoutManager(
                                requireContext(), 2
                            )

                        }

                        GridType.THREE_COLUMNS -> {

                            binding.layoutButton.setImageResource(R.drawable.ic_table)

                            binding.imagesRecyclerView.layoutManager = GridLayoutManager(
                                requireContext(), 3
                            )
                        }

                    }
                }
            }
        }
    }

    private fun initImageListScroll() {

        val scrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val manager = recyclerView.layoutManager as LinearLayoutManager

                val totalItemCount = manager.itemCount

                val visibleItemCount = manager.childCount

                val visibleItemPosition = manager
                    .findFirstVisibleItemPosition()

                if (visibleItemPosition + visibleItemCount == totalItemCount) {

                    Timber.d("Called -> Fetch Next Page of Images")

                    // Fetch a next batch of records from the database
                    viewModel.fetchImagesList()
                }
            }
        }

        binding.imagesRecyclerView.addOnScrollListener(scrollListener)

    }

    private fun initProgressObserver() {

        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(RESUMED) {

                viewModel.progressBar.collect {

                    when {

                        it -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        else -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun initImageListObserver() {

        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(RESUMED) {

                viewModel.imageList.collect { response ->

                    imageAdapter.submitList(response)
                }
            }
        }

    }

    private fun showDetailDialog(url: String) {

        val binding: DialogDetailBinding = DialogDetailBinding.inflate(layoutInflater)

        val alertDialog: AlertDialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root).setCancelable(false).create()

        binding.noButton.setOnClickListener {

            alertDialog.dismiss()

        }

        binding.yesButton.setOnClickListener {

            alertDialog.dismiss()

            openDetailScreen(url)

        }

        alertDialog.show()

    }

    private fun openDetailScreen(url: String) {

        val homeToDetail = HomeFragmentDirections
            .actionHomeFragmentToDetailFragment(url)

        findNavController().navigate(homeToDetail)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}