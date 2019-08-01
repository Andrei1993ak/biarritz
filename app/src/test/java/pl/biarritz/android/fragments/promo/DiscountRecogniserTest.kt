package pl.biarritz.android.fragments.promo

import org.junit.Test

import org.junit.Assert.*

class DiscountRecogniserTest {

    @Test
    fun getManVisitsLeftToDiscount() {
        assertEquals(2,DiscountRecogniser().getManVisitsLeftToDiscount(0))
        assertEquals(1,DiscountRecogniser().getManVisitsLeftToDiscount(1))
        assertEquals(0,DiscountRecogniser().getManVisitsLeftToDiscount(2))
        assertEquals(2,DiscountRecogniser().getManVisitsLeftToDiscount(3))
        assertEquals(1,DiscountRecogniser().getManVisitsLeftToDiscount(4))
        assertEquals(0,DiscountRecogniser().getManVisitsLeftToDiscount(5))
        assertEquals(2,DiscountRecogniser().getManVisitsLeftToDiscount(6))
    }
}