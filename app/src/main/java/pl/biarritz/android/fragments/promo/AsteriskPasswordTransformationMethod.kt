package pl.biarritz.android.fragments.promo

import android.text.method.PasswordTransformationMethod
import android.view.View

class AsteriskPasswordTransformationMethod : PasswordTransformationMethod() {

    override fun getTransformation(source: CharSequence, view: View): CharSequence = PasswordCharSequence(source)


    private inner class PasswordCharSequence(private val mSource: CharSequence) : CharSequence {

        override fun get(index: Int) = '*'

        override val length: Int
            get() = mSource.length

        override fun subSequence(start: Int, end: Int): CharSequence {
            return mSource.subSequence(start, end)
        }
    }
}