package com.jayden.bluetoothalerts.app.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jayden.bluetoothalerts.app.MainApplication
import com.jayden.bluetoothalerts.app.service.BluetoothAlertService
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModel
import com.jayden.bluetoothalerts.proto.MonitorMode
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels(
        factoryProducer = { (application as MainApplication).mainViewModelFactory }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutMainActivityContent()
        }

        lifecycleScope.launch {
            viewModel.settingsForegroundServiceEnabled.collect { shouldLaunchService ->
                val serviceIntent = Intent(applicationContext, BluetoothAlertService::class.java)
                if (shouldLaunchService) {
                    Log.d(TAG, "Starting BluetoothAlertService as foregroundServiceEnabled == true")
                    startService(serviceIntent)
                } else {
                    val serviceIntent = Intent(applicationContext, BluetoothAlertService::class.java)
                    val stopped = stopService(serviceIntent)
                    if (stopped) {
                        // service is likely already stopped;
                        Log.d(TAG, "BluetoothAlertService already stopped.")
                    } else {
                        Log.d(TAG, "Stopped BluetoothAlertService.")
                    }
                }
            }
        }
    }

    @Composable
    fun ConstraintLayoutMainActivityContent() {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val enableForegroundServiceRef = createRef()
            val monitorModeRef = createRef()

            val optionState: Boolean by viewModel.settingsForegroundServiceEnabled.collectAsStateWithLifecycle(false)

            SettingsItem(title = "Enable Foreground Service",
                description = "By checking this option, you are allowing us to use a foreground service to keep us in the foreground. We will only use this to monitor Bluetooth state.",
                modifier = Modifier.constrainAs(enableForegroundServiceRef) {
                    top.linkTo(monitorModeRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                switchChecked = optionState,
                onClick = viewModel::updateForegroundServiceEnabled
            )

            val options = listOf(
                MonitorMode.PASSIVE to "Passive",
                MonitorMode.ALWAYS to "Always"
            )

            val selected: MonitorMode by viewModel.settingsMonitorMode.collectAsStateWithLifecycle(
                MonitorMode.PASSIVE
            )

            SettingsRadio(
                title = "Monitor Mode",
                description = """Passive - The app will not be awake and the OS will wake this app up
                    |Always - Always keep the app awake
                """.trimMargin(),
                options = options,
                modifier = Modifier.constrainAs(monitorModeRef) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }.padding(top = 64.dp),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                        onClick(it)
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
                    Column(modifier = Modifier.clickable { onSelectedChange(id) }.fillMaxWidth(0.9f), verticalArrangement = Arrangement.Center) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
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

    companion object {
        private const val TAG = "MainActivity"
    }
}