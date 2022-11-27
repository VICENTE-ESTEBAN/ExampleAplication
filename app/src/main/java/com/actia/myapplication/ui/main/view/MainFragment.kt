package com.actia.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.actia.myapplication.R
import com.actia.myapplication.databinding.FragmentMainBinding
import com.actia.myapplication.ui.main.viewmodel.MainViewModel
import com.actia.myapplication.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding  = FragmentMainBinding.inflate(inflater, container, false)

        binding.rvItems.setEmptyView(binding.tvEmptyList)
        isShowVeloVisible(false)

        binding.btnBuscar.setOnClickListener {
            val textToSearch = binding.etTitle.text.toString()
            if(textToSearch.isEmpty())
            {
                Toast.makeText(context, resources.getText(R.string.title_mandatory), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            isShowVeloVisible(true)
            activity?.hideKeyboard()
            mViewModel.loadItems(binding.etTitle.text.toString())
        }

        mViewModel.getItemsLiveData.observe(viewLifecycleOwner){
            isShowVeloVisible(false)
            binding.spinYears.visibility = if(it.isNotEmpty()) View.VISIBLE else View.GONE
        }

        mViewModel.hasErrorOnRequestiveData.observe(viewLifecycleOwner){
            if(it && isResumed) {

                isShowVeloVisible(false)
                binding.spinYears.visibility = View.GONE

                Toast.makeText(
                    context,
                    resources.getText(R.string.error_on_request), Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        binding.lifecycleOwner = this
        binding.mainViewModel = mViewModel

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun isShowVeloVisible(isVisible:Boolean){
        binding.includeVelo.flVelo.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}