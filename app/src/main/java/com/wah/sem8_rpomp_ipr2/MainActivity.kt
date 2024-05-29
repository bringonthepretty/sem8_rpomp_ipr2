package com.wah.sem8_rpomp_ipr2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wah.sem8_rpomp_ipr2.model.RSSEntry
import com.wah.sem8_rpomp_ipr2.ui.theme.RSSScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import tw.ktrssreader.Reader
import tw.ktrssreader.kotlin.model.channel.RssStandardChannelData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RSSScreen()
        }
    }
}