package pl.biarritz.android.fragments.promo

import pl.biarritz.android.Constants

enum class VisitorType(val lastVisitProperty: String) {
    MAN(Constants.LAST_VISIT_TIME_WOMAN),
    WOMAN(Constants.VISIT_AMOUNT_MAN),
    CHILD(Constants.LAST_VISIT_TIME_CHILD);
}