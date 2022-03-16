package com.example.changebondtask.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.changebondtask.R
import com.example.changebondtask.databinding.FragmentDetailsBinding
import com.example.changebondtask.viewmodels.DetailsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding :  FragmentDetailsBinding

    val viewModel by viewModels<DetailsFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userModel.value = arguments?.getParcelable("model")

        binding.btnSave.setOnClickListener {
            if(binding.edtComments.text.toString().trim().isNullOrEmpty()){
                Toast.makeText(requireContext(),"Enter Comments",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.userModel.value?.let {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.updateComment(it)
                    }
                } ?: kotlin.run {
                    Toast.makeText(requireContext(),"Empty Value",Toast.LENGTH_SHORT).show()
                }
                findNavController().popBackStack()
            }
        }
    }






}