package com.sachin.test.home

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.sachin.test.R
import com.sachin.test.databinding.FragmentImageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ImageFragment : Fragment(),View.OnClickListener {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentImageBinding.inflate(layoutInflater)
    }
    private val viewModel: HomeViewModel by viewModels()
    private val args: ImageFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getImageDetailsUsingStateFlow(args.id)
        lifecycleScope.launchWhenStarted {
            viewModel.pageUiStateFlow.collectLatest {
                when(it){
                    is HomeViewModel.PageAPiuiState.Loading ->{
                        println("----loading----")
                    }

                    is HomeViewModel.PageAPiuiState.SuccessImage ->{
                        Glide.with(this@ImageFragment)
                            .load(it.imageObj.downloadUrl)
                            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                            .into(binding.itemImage)
                        binding.itemAuthor.text = "Author: " + it.imageObj.author
                        binding.downloadButton.setTag(it.imageObj.downloadUrl)
                        binding.downloadButton.setOnClickListener(this@ImageFragment)
                        println("----Success----")
                    }

                    is HomeViewModel.PageAPiuiState.Error ->{
                        println("----error----|"+it.msg)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        val id = v?.id
        val url : String = v?.getTag() as String
        when(id){
            R.id.downloadButton ->{
                val uri: Uri =
                    Uri.parse(url+".jpg")

                val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
                val request = DownloadManager.Request(uri)
                request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI or
                            DownloadManager.Request.NETWORK_MOBILE
                )

                request.setTitle("Download Complete")
                request.setDescription("Image has been downloaded successfully. Thank you")

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setMimeType("*/*")
                downloadManager!!.enqueue(request)
                Toast.makeText(context,"Donwload Started",Toast.LENGTH_SHORT).show()
            }
        }
    }
}