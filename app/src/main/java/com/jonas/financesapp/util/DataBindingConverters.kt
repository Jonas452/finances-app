package com.jonas.financesapp.util

import android.text.TextUtils
import androidx.databinding.InverseMethod

class DataBindingConverters {

    companion object {

        @InverseMethod("convertDoubleToString")
        @JvmStatic
        fun convertStringToDouble(value: String): Double? {
            if (TextUtils.isEmpty(value) || !TextUtils.isDigitsOnly(value)) {
                return null
            }
            return value.toDoubleOrNull()
        }

        @JvmStatic
        fun convertDoubleToString(value: Double?): String {
            return value?.toString() ?: ""
        }

    }

}