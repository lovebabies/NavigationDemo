package com.example.navigationdemo1

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
fun View.closeSoftKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}