package com.wah.sem8_rpomp_ipr2.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.wah.sem8_rpomp_ipr2.model.RSSEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tw.ktrssreader.Reader
import tw.ktrssreader.kotlin.model.channel.RssStandardChannelData


@Composable
fun RSSScreen() {
    val coroutineScope = rememberCoroutineScope()
    var rssListState by remember {
        mutableStateOf(listOf<RSSEntry>())
    }

    var showStarredState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = 1) {
        coroutineScope.launch(Dispatchers.IO) {
            Reader.coRead<RssStandardChannelData>(url = "https://people.onliner.by/feed").items?.let {
                rssListState = it.map { entry -> RSSEntry(entry.title, entry.description, entry.pubDate) }.toList()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.spacedBy(Dp(2f)),
        ) {
        var starredRssSetState by remember {
            mutableStateOf(setOf<RSSEntry>())
        }

        Header(showStarredState) {
            showStarredState = !showStarredState
        }

        if (showStarredState) {
            StarredRSSList(starredRssSetState)
        } else {
            RSSList(rssList = rssListState) {
                starredRssSetState = starredRssSetState + it
            }
        }
    }
}

@Composable
fun Header(
    showStarred: Boolean,
    onChangeStateButtonClicked: () -> Unit
) {
    val buttonText = if (showStarred) {
        "Hide starred"
    } else {
        "Show starred"
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                onClick = { onChangeStateButtonClicked() }
            ) {
                Text(text = buttonText, color = Color.Cyan)
            }
        }
    }
}

@Composable
fun RSSList(
    rssList: List<RSSEntry>,
    onRssStarred: (RSSEntry) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dp(10f))
    ) {
        items(rssList) {
            RSSEntryWithStarButton(it) {
                onRssStarred(it)
            }
        }
    }
}

@Composable
fun StarredRSSList(
    starredRssList: Set<RSSEntry>
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Dp(10f))
    ) {
        items(starredRssList.toList()) {
            RSSEntry(it)
        }
    }
}

@Composable
fun RSSEntryWithStarButton(
    entry: RSSEntry,
    onRssStarred: () -> Unit
) {
    Column {
        Text(text = entry.title ?: "null", color = Color.Cyan)
        Text(text = entry.date ?: "null", color = Color.Cyan)
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            onClick = { onRssStarred() }
        ) {
            Text(text = "Star", color = Color.Cyan)
        }
    }
}

@Composable
fun RSSEntry(
    entry: RSSEntry
) {
    Column {
        Text(text = entry.title ?: "null", color = Color.Cyan)
        Text(text = entry.date ?: "null", color = Color.Cyan)
    }
}

