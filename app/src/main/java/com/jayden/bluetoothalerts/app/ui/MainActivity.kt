package com.jayden.bluetoothalerts.app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jayden.bluetoothalerts.app.MainApplication
import com.jayden.bluetoothalerts.app.service.BluetoothAlertService
import com.jayden.bluetoothalerts.app.viewmodel.MainViewModel
import com.jayden.bluetoothalerts.data.source.events.BluetoothEventSerializer
import com.jayden.bluetoothalerts.data.source.events.BluetoothEventSerializer.toEventType
import com.jayden.bluetoothalerts.data.source.events.BluetoothEventSerializer.toProto
import com.jayden.bluetoothalerts.data.source.events.EventType
import com.jayden.bluetoothalerts.proto.AliasChangedEvent
import com.jayden.bluetoothalerts.proto.BluetoothEvent
import com.jayden.bluetoothalerts.proto.BondStateChangedEvent
import com.jayden.bluetoothalerts.proto.ClassChangedEvent
import com.jayden.bluetoothalerts.proto.ConnectionStateChangedEvent
import com.jayden.bluetoothalerts.proto.DeviceConnectedEvent
import com.jayden.bluetoothalerts.proto.DeviceDisconnectRequestedEvent
import com.jayden.bluetoothalerts.proto.DeviceDisconnectedEvent
import com.jayden.bluetoothalerts.proto.DeviceFoundEvent
import com.jayden.bluetoothalerts.proto.DiscoveryStateChangedEvent
import com.jayden.bluetoothalerts.proto.EncryptionChangedEvent
import com.jayden.bluetoothalerts.proto.KeyMissingEvent
import com.jayden.bluetoothalerts.proto.MonitorMode
import com.jayden.bluetoothalerts.proto.NameChangedEvent
import com.jayden.bluetoothalerts.proto.PairingRequestEvent
import com.jayden.bluetoothalerts.proto.ScanModeChangedEvent
import com.jayden.bluetoothalerts.proto.StateChangedEvent
import com.jayden.bluetoothalerts.proto.UuidFoundEvent
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels(
        factoryProducer = { (application as MainApplication).mainViewModelFactory }
    )

    private val startDestination = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleIntent(intent)
        setContent {
            var currentScreen by rememberSaveable { mutableStateOf(startDestination.value ?: Screen.SETTINGS) }
            Log.v(TAG, "currentScreen = $currentScreen")
            Log.v(TAG, "startDestination = $startDestination")

            when (currentScreen) {
                Screen.SETTINGS -> {
                    Log.d(TAG, "SettingsScreen init")
                    SettingsScreen(onGoToScreen = { route ->
                        if (route != currentScreen) currentScreen = route
                    })
                }

                Screen.EVENTS -> {
                    Log.d(TAG, "EventsScreen init")
                    EventsScreen(onGoToScreen = { route ->
                        if (route != currentScreen) currentScreen = route
                    })
                }
            }
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.d(TAG, "handling intent: ${intent?.action}")
        if (intent == null) return
        if (intent.getStringExtra(SettingsActivity.EXTRA_INIT) == Screen.SETTINGS) {
            Log.i(TAG, "intent extras want default screen destination to be Screen.SETTINGS")
            startDestination.value = Screen.SETTINGS
        }
    }

    private fun formatTimestamp(timestampMs: Long): String {
        val currentTime = Instant.ofEpochMilli(timestampMs).atZone(ZoneId.systemDefault())
        val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        return currentTime.format(timeFormatter)
    }

    @Composable
    fun EventsScreen(onGoToScreen: (String) -> Unit) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val lazyBluetoothEventItems = viewModel.bluetoothEventsPager.collectAsLazyPagingItems()

            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ) {
                items(lazyBluetoothEventItems.itemCount) { index ->
                    val bluetoothEventItem = lazyBluetoothEventItems[index]
                    bluetoothEventItem?.let { item ->
                        val formattedTimestamp = formatTimestamp(item.timestampMs)
                        val protoItem = item.toProto()

                        EventItem(
                            formatTimestamp = formattedTimestamp,
                            eventType = protoItem.toEventType(),
                            item = BluetoothEvent.getDefaultInstance(),
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun EventItem(
        modifier: Modifier = Modifier,
        formatTimestamp: String,
        eventType: Int,
        item: BluetoothEvent
    ) {
        Card(modifier = modifier) {
            Text(text = formatTimestamp, modifier = modifier)
            when (eventType) {
                EventType.NAME_CHANGED -> {
                    NameChangedItem(modifier, item.nameChangedEvent)
                }

                EventType.CONNECTION_STATE_CHANGED -> {
                    ConnectionStateChangedItem(
                        modifier,
                        item.connectionStateChangedEvent
                    )
                }

                EventType.DISCOVERY_STATE_CHANGED -> {
                    DiscoveryStateChangedItem(
                        modifier,
                        item.discoveryStateChangedEvent
                    )
                }

                EventType.SCAN_MODE_CHANGED -> {
                    ScanModeChangedItem(modifier, item.scanModeChangedEvent)
                }

                EventType.STATE_CHANGED -> {
                    StateChangedItem(modifier, item.stateChangedEvent)
                }

                EventType.DEVICE_CONNECTED -> {
                    DeviceConnectedItem(modifier, item.deviceConnectedEvent)
                }

                EventType.DEVICE_DISCONNECTED -> {
                    DeviceDisconnectedItem(modifier, item.deviceDisconnectedEvent)
                }

                EventType.DEVICE_DISCONNECT_REQUESTED -> {
                    DeviceDisconnectRequestedItem(
                        modifier,
                        item.deviceDisconnectRequestedEvent
                    )
                }

                EventType.ALIAS_CHANGED -> {
                    AliasChangedItem(modifier, item.aliasChangedEvent)
                }

                EventType.BOND_STATE_CHANGED -> {
                    BondStateChangedItem(modifier, item.bondStateChangedEvent)
                }

                EventType.CLASS_CHANGED -> {
                    ClassChangedItem(modifier, item.classChangedEvent)
                }

                EventType.ENCRYPTION_CHANGED -> {
                    EncryptionChangedItem(modifier, item.encryptionChangedEvent)
                }

                EventType.DEVICE_FOUND -> {
                    DeviceFoundItem(modifier, item.deviceFoundEvent)
                }

                EventType.KEY_MISSING -> {
                    KeyMissingItem(modifier, item.keyMissingEvent)
                }

                EventType.PAIRING_REQUEST -> {
                    PairingRequestItem(modifier, item.pairingRequestEvent)
                }

                EventType.UUID_FOUND -> {
                    UuidFoundItem(modifier, item.uuidFoundEvent)
                }
            }
        }
    }

    @Composable
    fun NameChangedItem(
        modifier: Modifier = Modifier,
        item: NameChangedEvent
    ) {

    }
    @Composable
    fun ConnectionStateChangedItem(
        modifier: Modifier = Modifier,
        item: ConnectionStateChangedEvent
    ) {

    }
    @Composable
    fun DiscoveryStateChangedItem(
        modifier: Modifier = Modifier,
        item: DiscoveryStateChangedEvent
    ) {

    }
    @Composable
    fun ScanModeChangedItem(
        modifier: Modifier = Modifier,
        item: ScanModeChangedEvent
    ) {

    }
    @Composable
    fun StateChangedItem(
        modifier: Modifier = Modifier,
        item: StateChangedEvent
    ) {

    }
    @Composable
    fun DeviceConnectedItem(
        modifier: Modifier = Modifier,
        item: DeviceConnectedEvent
    ) {

    }
    @Composable
    fun DeviceDisconnectedItem(
        modifier: Modifier = Modifier,
        item: DeviceDisconnectedEvent
    ) {

    }
    @Composable
    fun DeviceDisconnectRequestedItem(
        modifier: Modifier = Modifier,
        item: DeviceDisconnectRequestedEvent
    ) {

    }
    @Composable
    fun AliasChangedItem(
        modifier: Modifier = Modifier,
        item: AliasChangedEvent
    ) {

    }
    @Composable
    fun BondStateChangedItem(
        modifier: Modifier = Modifier,
        item: BondStateChangedEvent
    ) {

    }
    @Composable
    fun ClassChangedItem(
        modifier: Modifier = Modifier,
        item: ClassChangedEvent
    ) {

    }
    @Composable
    fun EncryptionChangedItem(
        modifier: Modifier = Modifier,
        item: EncryptionChangedEvent
    ) {

    }
    @Composable
    fun DeviceFoundItem(
        modifier: Modifier = Modifier,
        item: DeviceFoundEvent
    ) {

    }
    @Composable
    fun KeyMissingItem(
        modifier: Modifier = Modifier,
        item: KeyMissingEvent
    ) {

    }
    @Composable
    fun PairingRequestItem(
        modifier: Modifier = Modifier,
        item: PairingRequestEvent
    ) {

    }
    @Composable
    fun UuidFoundItem(
        modifier: Modifier = Modifier,
        item: UuidFoundEvent
    ) {

    }

    @Composable
    fun SettingsScreen(onGoToScreen: ((String) -> Unit)) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val enableForegroundServiceRef = createRef()
            val monitorModeRef = createRef()

            val optionState: Boolean by viewModel.settingsForegroundServiceEnabled.collectAsStateWithLifecycle(
                false
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
                modifier = Modifier
                    .constrainAs(monitorModeRef) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 72.dp),
                selected = selected,
                onSelectedChange = viewModel::updateMonitorMode
            )

            SettingsItem(
                title = "Enable Foreground Service",
                description = "By checking this option, you are allowing us to use a foreground service to keep us in the foreground. We will only use this to monitor Bluetooth state.",
                modifier = Modifier.constrainAs(enableForegroundServiceRef) {
                    top.linkTo(monitorModeRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                switchChecked = optionState,
                onClick = viewModel::updateForegroundServiceEnabled
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
                    Column(modifier = Modifier
                        .clickable { onSelectedChange(id) }
                        .fillMaxWidth(0.9f), verticalArrangement = Arrangement.Center) {
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

    companion object {
        private const val TAG = "MainActivity"
    }
}