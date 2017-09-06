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

    enum class Click(value: Int){
        RIGHT_CLICK(0x00B1),
        LEFT_CLICK(0x00C2);
        private var mValue = value

        fun value(): Int = mValue
    }
}