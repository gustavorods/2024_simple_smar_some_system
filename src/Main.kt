import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// Superclass
open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
        protected set /*property must be readable outside the
        class through class objects. However, only the class
        and its children should be able to update or write
        the value.*/

    open val deviceType = "unknown"

    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "on"
    }
}

// Smart Tv
class SmartTvDevice(deviceName: String, deviceCategory: String) : // Inheriting the SmartDevice class
    SmartDevice(name = deviceName, category = deviceCategory) {

    override val deviceType = "Smart TV"

    private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)
    private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume")
    }

    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

    override fun turnOn() {
        super.turnOn() // CALLING THE SUPERCLASS METHOD!
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        super.turnOff() // CALLING THE SUPERCLASS METHOD!
        println("$name turned off")
    }
}

// Smart Light
class SmartLightDevice(deviceName: String, deviceCategory: String)
    : SmartDevice(name = deviceName, category = deviceCategory) { // Inheriting the SmartDevice class

    override val deviceType = "Smart Light"

    private var britghtnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)

    fun increaseBrightness() {
        britghtnessLevel++
        println("Brightness increased to $britghtnessLevel")
    }

    // Turn on and turn off
    override fun turnOn() {
        super.turnOn() // CALLING THE SUPERCLASS METHOD!
        britghtnessLevel = 2
        println("$name turned on. The Brightness level is $britghtnessLevel")
    }

    override fun turnOff() {
        super.turnOff() // CALLING THE SUPERCLASS METHOD!
        britghtnessLevel = 0
        println("Smart Light turned off")
    }
}

// Smart Home
class SmartHome(val smartTvDevice: SmartTvDevice, val smartLightDevice: SmartLightDevice) {

    var deviceTurnOnCount = 0
        private set

    // TV functions
        // Turn on and turn off
        fun turnOnTv() {
            deviceTurnOnCount++
            smartTvDevice.turnOn() //We are using the SmartDevices's functions, because SmartTvDevies inherits this.
        }
        fun turnOffTv() {
            deviceTurnOnCount--
            smartTvDevice.turnOff()
        }

        // increase Volume
        fun increaseTvVolume() {
            smartTvDevice.increaseSpeakerVolume()
        }

        // Change channel
        fun changeTvChannelToNext() {
            smartTvDevice.nextChannel()
        }

    // Light functions
        // Turn on and turn off
        fun turnOnLight() {
            deviceTurnOnCount++
            smartLightDevice.turnOn() //we are not using the SmartDevices's
        }

        fun turnOffLight() {
            deviceTurnOnCount--
            smartLightDevice.turnOff()
        }

        // increase light Brightness
        fun increaseLightBrightness() {
            smartLightDevice.increaseBrightness()
        }

    // Turn off all Devices
    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}

// Range Regulator
class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int) : ReadWriteProperty<Any?, Int> {

        var fieldData = initialValue
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }


    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if(value in minValue..maxValue) {
            fieldData = value
        }
    }


}

// Main
fun main() {
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
}