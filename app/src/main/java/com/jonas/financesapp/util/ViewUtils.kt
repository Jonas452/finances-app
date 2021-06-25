package com.jonas.financesapp.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputManager: InputMethodManager? =
        this.getSystemService(Context.INPUT_METHOD_SERVICE) as?
                InputMethodManager
    val v = this.currentFocus ?: return
    inputManager?.hideSoftInputFromWindow(v.windowToken, 0)
}
