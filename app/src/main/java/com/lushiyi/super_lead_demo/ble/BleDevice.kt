package com.lushiyi.super_lead_demo.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.lushiyi.super_lead_demo.utils.UBluetooth
import com.lushiyi.super_lead_demo.utils.ULogTag
import java.util.*

/**
 * BLE 模式
 */
@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("MissingPermission")
class BleDevice(
    context: Context,
    name: String,
    uart: String,
    rx: String,
    client: String,
    doSomething: ((s: String) -> Unit)? = null
) {
    private val tag = ULogTag.getTag("BarCodeBox")
    private var scannerGatt: BluetoothGatt? = null

    init {
        val device = UBluetooth.getBTDevice(name)
        Log.i(tag, "获取扫描盒蓝牙设备：${device?.name}")
        if (device != null) {
            scannerGatt?.disconnect()
            scannerGatt?.close();
            scannerGatt = device.connectGatt(context, true, object : BluetoothGattCallback() {
                @SuppressLint("MissingPermission")
                override fun onConnectionStateChange(
                    gatt: BluetoothGatt?,
                    status: Int,
                    newState: Int
                ) {
                    Log.i(tag, "蓝牙设备:${device.name} onConnectionStateChange：${newState}")
                    if (newState == BluetoothProfile.STATE_CONNECTED) {
                        // successfully connected to the GATT Server
                        gatt?.discoverServices()
                    } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                        // disconnected from the GATT Server
                    }
                }

                @SuppressLint("MissingPermission")
                override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        val characteristic = gatt
                            .getService(UUID.fromString(uart))
                            .getCharacteristic(UUID.fromString(rx))
                        val success = gatt.setCharacteristicNotification(characteristic, true)
                        val descriptor = characteristic
                            .getDescriptor(UUID.fromString(client))
                        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
                        val success2 = gatt.writeDescriptor(descriptor)
                        Log.d(tag, "蓝牙设备:${device.name} 状态：$success:$success2")
                    } else {
                        Log.w(tag, "onServicesDiscovered received: $status")
                    }
                }

                @SuppressLint("MissingPermission")
                override fun onCharacteristicChanged(
                    gatt: BluetoothGatt?,
                    characteristic: BluetoothGattCharacteristic
                ) {
                    Log.d(
                        tag,
                        "${name}:" + characteristic.value.toString(Charsets.UTF_8)
                            .replace("\\s".toRegex(), "")
                    )
                    // 注意、注意、注意。字符串里面可能有其它字符
                    doSomething?.let {
                        it(
                            characteristic.value.toString(Charsets.UTF_8)
                                .replace("\\s".toRegex(), "")
                        )
                    }
                }
            })
        }
    }
}