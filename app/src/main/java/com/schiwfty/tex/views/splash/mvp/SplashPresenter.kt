package com.schiwfty.tex.views.splash.mvp

import android.content.Context
import android.content.Intent
import com.schiwfty.tex.R
import com.schiwfty.tex.TricklComponent
import com.schiwfty.tex.confluence.ConfluenceDaemonService
import com.schiwfty.tex.repositories.ITorrentRepository
import com.schiwfty.tex.utils.composeIo
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by arran on 16/04/2017.
 */
class SplashPresenter : SplashContract.Presenter {
    lateinit var view: SplashContract.View

    @Inject
    lateinit var torrentRepository: ITorrentRepository

    var subscriptions = CompositeSubscription()

    override fun setup(context: Context, view: SplashContract.View) {
        this.view = view
        TricklComponent.networkComponent.injectSplashPresenter(this)
    }

    override fun destroy() {
        subscriptions.unsubscribe()
    }

    override fun startConfluenceDaemon(context: Context) {
        val daemonIntent = Intent(context, ConfluenceDaemonService::class.java)
        daemonIntent.addCategory(ConfluenceDaemonService.TAG)
        context.startService(daemonIntent)
        listenForDaemon()
    }


    fun listenForDaemon() {
        subscriptions.add(TricklComponent.heartbeat
                .composeIo()
                .subscribe({
                    view.showSuccess(R.string.splash_start_confluence_success)
                    view.progressToMain()
                }, {
                    view.showError(R.string.splash_start_confluence_error)
                }))

    }

}