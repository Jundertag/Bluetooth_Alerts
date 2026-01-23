package com.jayden.bluetoothalerts.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeList()

        }
    }
}

@Composable
fun ComposeList() {
    Column {
        ListItem({ Text(text = "this is a list item") })
        ListItem({ Text(text = "this is a list item") })
        ListItem({ Text(text = "this is a list item") })
        ListItem({ Text(text = "this is a list item") })
        ListItem({ Text(text = "this is a list item") })
    }
}

@Preview(device = "id:pixel_9", showSystemUi = true)
@Composable
fun PreviewComposeList() {
    ComposeList()
}