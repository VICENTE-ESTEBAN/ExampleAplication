package com.actia.myapplication.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.actia.myapplication.R
import com.actia.myapplication.data.domain.model.Item
import com.actia.myapplication.databinding.FragmentDetailBinding
import com.actia.myapplication.ui.main.viewmodel.MainViewModel
import com.actia.myapplication.util.Constants
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private lateinit var binding:FragmentDetailBinding

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

        if(arguments==null)
        {
            showToastAndExit(resources.getString(R.string.no_data_movie))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val args = arguments

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false)

        configToolBar()

        mViewModel.getDetailItemLiveData.observe(viewLifecycleOwner) {

            isShowVeloVisible(false)

            if (it != null) {
                binding.obj = it
                fillPoster(it.poster)
            } else
            {
                showToastAndExit(resources.getString(R.string.no_data_movie))
            }
        }

        mViewModel.hasErrorOnRequestiveData.observe(viewLifecycleOwner){
            if(it && isResumed) {
                showToastAndExit(
                    resources.getString(R.string.error_on_request), Toast.LENGTH_LONG
                )
                    .show()
            }
        }

        val data = args!!.getParcelable<Item>(Constants.KEY_BUNDLE_ITEM)
        if(!mViewModel.canGetDetail(data)) {
            showToastAndExit(resources.getString(R.string.no_data_movie))
        }else
        {
            isShowVeloVisible(true)
        }

        //here data must be an instance of the class MarsDataProvider
        //here data must be an instance of the class MarsDataProvider

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun fillPoster(poster:String){
        Picasso.get()
            .load(parseURL(poster))
            .resize(
                resources.getDimension(R.dimen.width_item_image).toInt(),
                resources.getDimension(R.dimen.height_item_image).toInt()
            )
            .into(binding.imgPoster)
    }

    private fun parseURL(value:String?): String? {
        return if (value.isNullOrEmpty() || value == Constants.EMPTY_FIELD)
            null
        else
            value
    }

    private fun showToastAndExit(textToShow:String){
        Toast.makeText(context, textToShow, Toast.LENGTH_LONG).show()
        activity?.onBackPressed()
    }

    private fun configToolBar() {
        val mToolbar: Toolbar = binding.toolbar
        mToolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
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
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}