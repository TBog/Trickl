package com.shwifty.tex.views.splash.mvp

import android.content.Context
import android.content.Intent
import com.shwifty.tex.views.base.BaseContract

/**
 * Created by arran on 16/04/2017.
 */
interface SplashContract {
    interface View: BaseContract.MvpView {
        fun progressToMain()
    }

    interface Presenter: BaseContract.Presenter<View> {
        var magnet: String?
        fun startConfluenceDaemon(context: Context)
        fun handleIntent(intent: Intent)
    }
}