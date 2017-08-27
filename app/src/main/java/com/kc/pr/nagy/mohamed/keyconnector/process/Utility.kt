package com.kc.pr.nagy.mohamed.keyconnector.process

/**
 * Created by mohamednagy on 8/27/2017.
 */
class Utility {

    enum class Extras(value :String){
        IP_ADDRESS_EXTRA("up-left");

        private var mValue = value

        fun value(): String = mValue
    }

    enum class MovingAction(value: String) {
        INCREASE_X_POSITION("right"),
        INCREASE_Y_POSITION("up"),
        INCREASING_X_Y_POSITION("up-right"),
        DECREASE_X_POSITION("left"),
        DECREASE_Y_POSITION("down"),
        DECREASE_X_Y_POSITION("down-left"),
        INCREASE_X_DECREASE_Y_POSITION("down-right"),
        INCREASE_Y_DECREASE_X_POSITION("up-left");

        private var mValue = value

        fun value(): String = mValue
    }
}