package pl.biarritz.android.fragments.promo

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_man_dialog.*
import kotlinx.android.synthetic.main.fragment_woman_dialog.btnOk
import kotlinx.android.synthetic.main.fragment_woman_dialog.btnSetVisit
import pl.biarritz.android.R

class ManDialogFragment : DialogFragment() {

    var visitAmount: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_man_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        visitAmount = sharedPref.getInt(VisitorType.MAN.lastVisitProperty, 0)

        val mVisitAmount = visitAmount

        val text = if (mVisitAmount == null || mVisitAmount == 0) {
            "У вас нет активных посещений. При каждом 3 посещении предоставляется " +
                    "скидка 10%."
        } else {
            val visitAmountForDiscount = DiscountRecogniser().getManVisitsLeftToDiscount(mVisitAmount)
            val timesPlural =
                this.resources.getQuantityString(R.plurals.times, mVisitAmount, mVisitAmount)
            val visitPlural =
                this.resources.getQuantityString(R.plurals.visits, visitAmountForDiscount, visitAmountForDiscount)

            if (visitAmountForDiscount != 0) {
                "Вы посетили наш салон $mVisitAmount $timesPlural. Через $visitAmountForDiscount $visitPlural " +
                        "вы получите скидку 10%."
            } else {
                "При следующем посещении нашего салона вы получите скидку 10%."
            }
        }

        manDialog.text = text

        btnSetVisit.setOnClickListener {
            targetFragment?.let {
                if (it is OnDateRequestListener) {
                    it.onSetupVisitRequstListner(VisitorType.MAN)
                }
            }
        }

        btnOk.setOnClickListener {
            dismiss()
        }
    }
}