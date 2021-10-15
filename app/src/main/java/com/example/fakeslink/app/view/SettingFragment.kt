package com.example.fakeslink.app.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.fakeslink.R
import com.example.fakeslink.app.viewmodel.*
import com.example.fakeslink.databinding.FragmentSettingBinding

private const val ARG_PARAM1 = "logout"

class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var param1: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as MainActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("TanKiem", "call onCreateView")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container,false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
//        binding.viewModel?.imageSource?.observe(this.viewLifecycleOwner) {
//            if(it != null) {
//                Glide.with(this)
//                    .load(it) // image url
//                    .placeholder(R.drawable.ptit) // any placeholder to load at start
//                    .error(R.drawable.ptit)  // any image in case of error
//                    .centerCrop()
//                    .into(binding.settingCircleImageView)
//            }
//        }
        binding.settingLayoutPersonalInfo.setOnClickListener { v ->
            binding.viewModel?.onPress(0)
        }
        binding.settingLayoutEvent.setOnClickListener { v ->
            binding.viewModel?.onPress(1)
        }
        binding.settingLayoutFeedback.setOnClickListener { v ->
            binding.viewModel?.onPress(2)
        }
        binding.settingLayoutFingerprint.setOnClickListener { v ->
            binding.viewModel?.onPress(3)
        }
        binding.settingLayoutLogOut.setOnClickListener { v ->
            binding.viewModel?.onPress(4)
            param1.logout()
        }
        observeState()
        return binding.root
    }

    private fun observeState() {
        binding.viewModel?.stateStream?.observe(this.viewLifecycleOwner,
            { settingState ->
                when(settingState) {
                    SettingCompleteState -> {
                        binding.settingLoading.visibility = View.INVISIBLE
                    }
                    is SettingErrorState -> {
                        binding.settingLoading.visibility = View.INVISIBLE
                        MyAlertDialog.showAlertDialog(this.requireContext(), "Error", settingState.message)
                    }
                    SettingLoadingState -> {
                        binding.settingLoading.visibility = View.VISIBLE
                    }
                }
            })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: MainActivity,) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}