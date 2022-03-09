package com.phoenixdevelopers.pixabay.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.phoenixdevelopers.pixabay.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?
    ): View {

        _binding = FragmentDetailBinding
            .inflate(layoutInflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()

        loadImage(args.imageUrl)

    }

    private fun initClickListener() {

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loadImage(imageUrl: String) {

        Glide.with(requireContext())
            .load(imageUrl).into(binding.imageViewer)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }

}