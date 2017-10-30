package com.shwifty.tex.views.torrentSearch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shwifty.tex.R
import kotlinx.android.synthetic.main.activity_torrent_search.*

class TorrentSearchActivity : AppCompatActivity() {

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, TorrentSearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_torrent_search)
        setSupportActionBar(torrentSearchToolbar)
        supportActionBar?.title = getString(R.string.search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        torrentSearchToolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, TorrentSearchFragment.newInstance())
                .commit()
    }
}