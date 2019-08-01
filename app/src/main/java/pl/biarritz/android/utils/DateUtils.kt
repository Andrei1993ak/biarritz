package pl.biarritz.android.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {

    companion object {

        const val FIVE_WEEKS_PERIOD_IN_MILLIS = 5 * DateUtils.WEEK_IN_MILLIS

        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd")
            return format.format(date)
        }

        fun convertLongToDays(time: Long): Int {
            return TimeUnit.MILLISECONDS.toDays(time).toInt()
        }
    }
}