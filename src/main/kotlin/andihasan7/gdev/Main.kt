package andihasan7.gdev

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "Online"
        protected set

    open val deviceType = "unknown"

    constructor(name: String, category: String, statusCode: Int) : this(name, category) {
        deviceStatus = when (statusCode) {
            0 -> "offline"
            1 -> "online"
            else -> "unknown"
        }
    }

    open fun turnOn() {
        deviceStatus = "on"

    }

    open fun turnOff() {
        deviceStatus = "off"
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) : SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }

    override fun turnOn() {
        super.turnOn()
        println("$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                "set to $channelNumber.")
    }

    override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    fun previousChannel() {
        channelNumber--
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart Light"

    private var brightnessLevel by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

    override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("Smart Light turned on. The brightness level is set to $brightnessLevel.")
    }

    override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
}

class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {

    var deviceTurnOnCount = 0
        private set
    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }
    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }
    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }
    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }
    fun turnOnLight() {
        deviceTurnOnCount++
        smartLightDevice.turnOn()
    }
    fun turnOffLight() {
        deviceTurnOnCount--
        smartLightDevice.turnOff()
    }
    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }
    fun turnOffAllDevice() {
        turnOffTv()
        turnOffLight()
    }
}

class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}

fun main() {
    val smartHome = SmartHome(
        SmartTvDevice(deviceName = "Android TV", deviceCategory = "Entertaiment"),
        SmartLightDevice(deviceName = "Google light", deviceCategory = "Utility")
    )
    smartHome.turnOnTv()
    smartHome.turnOnLight()
    println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")
    println(" ")

    smartHome.increaseTvVolume()
    smartHome.changeTvChannelToNext()
    smartHome.increaseLightBrightness()
    println(" ")

    smartHome.turnOffAllDevice()
    println("Total number of devices currently turned on : ${smartHome.deviceTurnOnCount}")
}