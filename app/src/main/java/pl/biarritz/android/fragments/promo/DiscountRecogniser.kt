package pl.biarritz.android.fragments.promo

import pl.biarritz.android.utils.DateUtils

class DiscountRecogniser {

    companion object {
        private const val MAN_DISCOUNT_PERIODICAL = 3
    }

    fun getManVisitsLeftToDiscount(visitAmount: Int): Int =
        MAN_DISCOUNT_PERIODICAL - visitAmount % MAN_DISCOUNT_PERIODICAL - 1

    fun getDaysLeftToDiscount(lastVisit: Long, currentTimeMillis: Long): Int =
        DateUtils.convertLongToDays(DateUtils.FIVE_WEEKS_PERIOD_IN_MILLIS + lastVisit - currentTimeMillis)
}