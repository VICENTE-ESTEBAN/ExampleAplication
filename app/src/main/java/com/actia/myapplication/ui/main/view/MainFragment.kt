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
import com.actia.myapplication.ui.main.adapters.ItemAdapter
import com.actia.myapplication.ui.main.viewmodel.MainViewModel
import com.actia.myapplication.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val mViewModel: MainViewModel by sharedViewModel()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding  = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        //binding  = FragmentMainBinding.inflate(inflater, container, false)

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
            binding.rvItems.adapter = null
            mViewModel.loadItems(binding.etTitle.text.toString())
        }

        mViewModel.getItemsLiveData.observe(viewLifecycleOwner){
            binding.rvItems.adapter = ItemAdapter(it)
            isShowVeloVisible(false)
        }

        mViewModel.hasErrorOnRequestiveData.observe(viewLifecycleOwner){
            if(it && isResumed) {
                Toast.makeText(
                    context,
                    resources.getText(R.string.error_on_request), Toast.LENGTH_LONG
                )
                    .show()
            }
        }


        // Inflate the layout for this fragment
        return binding.root
    }


    private fun isShowVeloVisible(isVisible:Boolean){
        binding.includeVelo.flVelo.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}