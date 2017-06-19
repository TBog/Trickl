package com.shwifty.tex.views.showtorrent

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.shwifty.tex.R
import com.shwifty.tex.TricklComponent
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_show_torrent.*


/**
 * Created by arran on 7/05/2017.
 */
class TorrentInfoActivity : AppCompatActivity(), TorrentInfoContract.View {

    lateinit var presenter: TorrentInfoContract.Presenter

    companion object {
        val ARG_TORRENT_HASH = "arg_torrent_hash"
        val ARG_TORRENT_NAME = "arg_torrent_name"
        val ARG_TORRENT_MAGNET = "arg_torrent_magnet"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_torrent)
        presenter = TorrentInfoPresenter()
        presenter.setup(this, this, intent.extras)

        setSupportActionBar(showTorrentToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        showTorrentToolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        presenter.fetchTorrent()

        if (presenter.torrentName != null) {
            showTorrentLoadingText.text = getString(R.string.loading_torrent_info_for, presenter.torrentName)
        }
    }

    override fun dismiss() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_show_torrent, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let { presenter.optionsItemSelected(it) }
        return true
    }


    override fun showError(stringId: Int) {
        Toasty.error(this, getString(stringId)).show()
    }

    override fun showInfo(stringId: Int) {
        Toasty.info(this, getString(stringId)).show()
    }

    override fun showSuccess(stringId: Int) {
        Toasty.success(this, getString(stringId)).show()
    }

    override fun notifyTorrentAdded() {
        supportActionBar?.title = presenter.torrentName
        showTorrentProgressBar.visibility = View.GONE
        showTorrentLoadingText.visibility = View.GONE
        showTorrentViewPager.visibility = View.VISIBLE
        showTorrentSmartTab.visibility = View.VISIBLE
        val adapter = ShowTorrentPagerAdapter(supportFragmentManager, presenter.torrentHash)
        showTorrentViewPager.adapter = adapter
        showTorrentSmartTab.setViewPager(showTorrentViewPager)
    }

    override fun notifyTorrentDeleted() {
        presenter.torrentInfo?.let {
            TricklComponent.dialogManager.showDeleteTorrentDialog(this, it, {
                showError(R.string.error_deleting_torrent)
            })
        }
    }
}