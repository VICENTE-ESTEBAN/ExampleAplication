package com.actia.myapplication.ui.base.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.actia.myapplication.R

class BaseActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initBackStackListener()
    }

    fun initBackStackListener() {
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = getCurrentFragment(supportFragmentManager)
            currentFragment?.userVisibleHint = true
        }
    }

    fun getCurrentFragment(fm: FragmentManager): Fragment? {
        return fm.findFragmentById(R.id.nav_fragment)
    }
}