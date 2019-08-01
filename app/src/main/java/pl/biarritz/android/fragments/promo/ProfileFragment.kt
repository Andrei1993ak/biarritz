package pl.biarritz.android.fragments.promo

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_profile.*
import pl.biarritz.android.R


class ProfileFragment : Fragment(), OnDateRequestListener, PinEnteredListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        womanProfile.setOnClickListener {
            fragmentManager?.let {
                val womanDialogFragment = WomanDialogFragment()
                womanDialogFragment.setTargetFragment(this@ProfileFragment, 1)
                womanDialogFragment.show(it, "promoDialog")
            }
        }

        manProfile.setOnClickListener {
            fragmentManager?.let {
                val manDialogFragment = ManDialogFragment()
                manDialogFragment.setTargetFragment(this@ProfileFragment, 1)
                manDialogFragment.show(it, "promoDialog")
            }
        }

        childrenProfile.setOnClickListener {
            fragmentManager?.let {
                val childrenDialogFragment = ChildrenDialogFragment()
                childrenDialogFragment.setTargetFragment(this@ProfileFragment, 1)
                childrenDialogFragment.show(it, "promoDialog")
            }
        }
    }

    override fun onSetupVisitRequstListner(visitorType: VisitorType) {
        fragmentManager?.let {
            val inputPasswordDialog = InputPasswordDialog()
            inputPasswordDialog.setVisitorType(visitorType)
            inputPasswordDialog.setTargetFragment(this@ProfileFragment, 1)
            inputPasswordDialog.show(it, "inputDialog")
        }
    }

    override fun onSuccessPinEntered(visitorType: VisitorType) {
        fragmentManager?.findFragmentByTag("promoDialog")?.let {
            if (it is DialogFragment) {
                it.dismiss()
            }
        }

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        if (visitorType == VisitorType.CHILD || visitorType == VisitorType.WOMAN) {
            sharedPref.edit().putLong(visitorType.lastVisitProperty, System.currentTimeMillis()).apply()
        } else {
            sharedPref.edit()
                .putInt(visitorType.lastVisitProperty, sharedPref.getInt(VisitorType.MAN.lastVisitProperty, 0) + 1)
                .apply()
        }


    }
}