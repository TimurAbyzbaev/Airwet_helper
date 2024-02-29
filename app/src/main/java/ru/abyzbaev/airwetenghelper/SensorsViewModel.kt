package ru.abyzbaev.airwetenghelper

// SensorViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorsViewModel : ViewModel() {

    private val _zones1_5: MutableLiveData<MutableMap<Int, Int>> = MutableLiveData(
        mutableMapOf(
            1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0
        )
    )
    val zones1_5: LiveData<MutableMap<Int, Int>> = _zones1_5

    private val _zones6_10: MutableLiveData<MutableMap<Int, Int>> = MutableLiveData(
        mutableMapOf(
            6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0
        )
    )
    val zones6_10: LiveData<MutableMap<Int, Int>> = _zones6_10

    fun updateSensorCount(zone: Int, count: Int, firstGroup: Boolean) {
        val zones = if (firstGroup) _zones1_5 else _zones6_10
        zones.value?.set(zone, count)
        zones.postValue(zones.value)
    }

    fun calculateNumber(sensorMap: MutableMap<Int, Int>, firstGroup: Boolean): Int {
        var binaryNumber = 0
        var offsetNumber = 1
        if (!firstGroup) {
            offsetNumber = 6
        }

        for ((zone, sensorCount) in sensorMap) {
            val offset = (zone - offsetNumber) * 3
            when (sensorCount) {
                1 -> binaryNumber = binaryNumber or (1 shl offset)
                2 -> binaryNumber = binaryNumber or (3 shl offset)
                3 -> binaryNumber = binaryNumber or (7 shl offset)
            }
        }

        return binaryNumber
    }
}
