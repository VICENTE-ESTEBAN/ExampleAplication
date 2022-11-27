package com.actia.myapplication.util

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Application.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

