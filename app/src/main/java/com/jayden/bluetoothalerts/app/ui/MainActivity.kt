package com.jayden.bluetoothalerts.app.ui

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.protobuf.option
import com.jayden.bluetoothalerts.R
import com.jayden.bluetoothalerts.app.MainApplication
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModel
import com.jayden.bluetoothalerts.proto.MonitorMode
import kotlinx.coroutines.flow.map


class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels(
        factoryProducer = { (application as MainApplication).mainViewModelFactory }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutMainActivityContent()
        }
    }

    @Composable
    fun ConstraintLayoutMainActivityContent() {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val item = createRef()
            val radio = createRef()

            var optionState by remember { mutableStateOf(false) }

            SettingsItem(
                title = "Option",
                description = "This does something",
                switchChecked = optionState,
                modifier = Modifier.constrainAs(item) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }.padding(top = 64.dp),
                onClick = {}
            )

            val options = listOf(
                MonitorMode.PASSIVE to "Passive",
                MonitorMode.ADAPTIVE to "Adaptive",
                MonitorMode.ALWAYS to "Always"
            )

            val selected: MonitorMode by viewModel.settingsState.map { it.monitorMode }.collectAsStateWithLifecycle(MonitorMode.PASSIVE)

            SettingsRadio(
                title = "Monitor Mode",
                description = """Passive - The app will not be awake and the OS will wake us up
                    |Adaptive - The app will wake and launch a service to listen for more granular events, but will die afterwards
                    |Always - Always keep the app awake
                """.trimMargin(),
                options = options,
                modifier = Modifier.constrainAs(radio) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(item.bottom)
                },
                selected = selected,
                onSelectedChange = viewModel::updateMonitorMode
            )
        }
    }

    @Composable
    fun SettingsItem(
        title: String,
        modifier: Modifier = Modifier,
        switchChecked: Boolean,
        description: String? = null,
        onClick: ((Boolean) -> Unit)
    ) {
        Card(
            modifier = modifier
                .padding(6.dp)
                .fillMaxWidth(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row {
                Column(Modifier.weight(1f)) {
                    Text(
                        title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    if (description != null) {
                        Text(description, fontSize = 14.sp, modifier = Modifier.padding(4.dp))
                    }
                }
                Switch(checked = switchChecked,
                    modifier = Modifier.padding(4.dp),
                    onCheckedChange = {
                        onClick(switchChecked)
                    }
                )
            }
        }
    }

    @Composable
    fun <T : Any> SettingsRadio(
        title: String,
        options: List<Pair<T, String>>,
        modifier: Modifier = Modifier,
        selected: T?,
        description: String? = null,
        onSelectedChange: ((T) -> Unit)
    ) {
        Card(
            modifier = modifier
                .padding(6.dp)
                .fillMaxWidth(),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                )

                if (description != null) {
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 6.dp, top = 4.dp, bottom = 4.dp)
                    )
                }

                HorizontalDivider(Modifier.padding(8.dp))

                options.forEach { (id, label) ->
                    Column(modifier = Modifier.clickable { onSelectedChange(id) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = (selected == id),
                                onClick = { onSelectedChange(id) }
                            )
                            Spacer(Modifier.width(12.dp))
                            Text(label)
                        }
                    }
                }

            }
        }
    }

    @Preview(showSystemUi = true, device = "id:pixel_9", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewCompose() {
        ConstraintLayoutMainActivityContent()
    }
}