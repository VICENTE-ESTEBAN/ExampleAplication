package com.actia.myapplication.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.actia.myapplication.R
import com.actia.myapplication.databinding.ActivityMainBinding
import com.actia.myapplication.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       //binding  = ActivityMainBinding.inflate(layoutInflater)
       binding = setContentView(this, R.layout.activity_main)
    }

}