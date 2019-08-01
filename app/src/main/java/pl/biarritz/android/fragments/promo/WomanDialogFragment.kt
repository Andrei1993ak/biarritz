package pl.biarritz.android.fragments.promo

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_woman_dialog.*
import pl.biarritz.android.R
import pl.biarritz.android.utils.DateUtils

class WomanDialogFragment : DialogFragment() {

    var lastVisitTime: Long? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_woman_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        lastVisitTime = sharedPref.getLong(VisitorType.WOMAN.lastVisitProperty, 0)

        val currentTimeMillis = System.currentTimeMillis()
        val lastVisit = lastVisitTime

        val text = if (lastVisit == null || lastVisit == 0L) {
            "У вас нет активных посещений. При повторном посещении нашего салона в течение 5 недель предоставляется " +
                    "скидка 10%."
        } else {
            val period = currentTimeMillis - lastVisit

            if (period > DateUtils.FIVE_WEEKS_PERIOD_IN_MILLIS) {
                "Вы не посещали наш салон за последние 5 недель. При повторном посещении в течение 5 недель предоставляется " +
                        "скидка 10%."
            } else {
                val daysToDiscount = DiscountRecogniser().getDaysLeftToDiscount(lastVisit, currentTimeMillis)
                val lastVisitDate = DateUtils.convertLongToTime(lastVisit)
                val dayPlural = this.resources.getQuantityString(R.plurals.days, daysToDiscount, daysToDiscount)

                "Вы посещали наш салон $lastVisitDate. Вы получите скидку 10% при " +
                        "повторном посещении нашего салона в течение $daysToDiscount $dayPlural."
            }
        }

        womanDialog.text = text

        btnSetVisit.setOnClickListener {
            targetFragment?.let {
                if (it is OnDateRequestListener) {
                    it.onSetupVisitRequstListner(VisitorType.WOMAN)
                }
            }
        }

        btnOk.setOnClickListener {
            dismiss()
        }
    }
}