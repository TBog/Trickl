package com.schiwfty.tex.views.main.mvp

import android.content.Context

/**
 * Created by arran on 16/04/2017.
 */
interface MainContract {
    interface View {
        fun showError(stringId: Int)
        fun showInfo(stringId: Int)
        fun showSuccess(stringId: Int)
        fun showAllFragment()
    }

    interface Presenter {
        fun setup(context: Context, view: MainContract.View)
    }
}