package com.kc.pr.nagy.mohamed.keyconnector.process

/**
 * Created by mohamednagy on 8/27/2017.
 */
class Utility {

    enum class Extras(value :String){
        IP_ADDRESS_EXTRA("ip-address");

        private var mValue = value

        fun value(): String = mValue
    }

    enum class Loaders(value: Int){
        SENDING_DATA_LOADER(1),
        CLIENTS_LOADER_MANAGER(2);

        private var mValue = value

        fun value(): Int = mValue
    }

    enum class MovingAction(value: Int) {
        INCREASE_X_POSITION(0x001),
        INCREASE_Y_POSITION(0x002),
        DECREASE_X_POSITION(0x004),
        DECREASE_Y_POSITION(0x005),
        INCREASE_X_Y_POSITION(0x003),
        DECREASE_X_Y_POSITION(0x006),
        INCREASE_X_DECREASE_Y_POSITION(0x007),
        INCREASE_Y_DECREASE_X_POSITION(0x008);

        private var mValue = value

        fun value(): Int = mValue
    }
}