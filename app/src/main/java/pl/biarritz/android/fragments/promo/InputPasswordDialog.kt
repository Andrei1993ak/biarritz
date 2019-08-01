package pl.biarritz.android.fragments.promo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_input_pin.*
import pl.biarritz.android.R



class InputPasswordDialog : DialogFragment() {

    private lateinit var visitorType: VisitorType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_input_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pinEditText.transformationMethod = AsteriskPasswordTransformationMethod()
        pinEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() == "1111") {
                    targetFragment?.let {
                        if (it is PinEnteredListener) {
                            it.onSuccessPinEntered(visitorType)
                        }
                    }

                    dismiss()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })
    }

    //todo move to args
    fun setVisitorType(visitorType: VisitorType) {
        this.visitorType = visitorType
    }
}