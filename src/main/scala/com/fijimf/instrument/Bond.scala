package com.fijimf.instrument

import org.joda.time.LocalDate
import com.fijimf.cashflow.DiscountFunction
import com.fijimf.util.CalendarNY
import util._




trait Bond {
  def issueDate: LocalDate

  def maturity: LocalDate

  def frequency: Int

  def period: Double = 1.0 / frequency

  def coupons: List[Scheduled[Double]]

  def price(pxDate: LocalDate, f: DiscountFunction): Double = {
    100.0 * (coupons.filter(_.date.isAfter(pxDate)).map(c => (c.value / (frequency * 100)) * f(c.date)).sum + (f(maturity)))
  }

  //Price on issue date, practically not covering short first coupon etc
  def price(pxDate: LocalDate, yld: Double): Double = {
    val d = 1.0 / (1.0 + yld / (frequency * 100))
    val (factor, sum) = coupons.foldLeft((d, 0.0))((tup: Pair[Double, Double], c: Scheduled[Double]) => {
      println(tup)
      (tup._1 * d, tup._2 + (c.value / (frequency * 100)) * tup._1)
    })
    100 * (sum + factor / d)
  }
}






