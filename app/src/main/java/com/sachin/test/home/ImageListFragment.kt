package com.sachin.test.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sachin.test.R
import com.sachin.test.databinding.FragmentImageListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ImageListFragment : Fragment() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentImageListBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getImageListUsingStaeFlow()
        lifecycleScope.launchWhenStarted {
            viewModel.pageUiStateFlow.collectLatest {
                when(it){
                    is HomeViewModel.PageAPiuiState.Loading ->{
                        println("----loading----")
                    }

                    is HomeViewModel.PageAPiuiState.Success ->{
                        val adapter = HomeAdapter(ImageClickListener,it.list)
                        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2,
                            LinearLayoutManager.VERTICAL)
                        binding.recyclerViewContainer.layoutManager = staggeredGridLayoutManager
                        binding.recyclerViewContainer.adapter = adapter
                        println("----Success----")
                    }

                    is HomeViewModel.PageAPiuiState.Error ->{
                        println("----error----|"+it.msg)
                    }
                }
            }
        }
    }
}

object ImageClickListener : HomeAdapter.ListClickListener {
    override fun onImageClick(id: String,v:View) {
        val action = ImageListFragmentDirections.actionImagelistFragmentToImageFragment(id)
        v.findNavController().navigate(action)
    }

}