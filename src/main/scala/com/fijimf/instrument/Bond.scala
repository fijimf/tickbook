package com.fijimf.instrument

import org.joda.time.LocalDate
import com.fijimf.cashflow.DiscountFunction
import com.fijimf.util.{CalendarNY, Calendar}


case class Scheduled[T](date: LocalDate, value: T)

trait Bond {
  def issueDate: LocalDate

  def maturity: LocalDate

  def frequency: Int

  def period: Double = 1.0 / frequency

  def coupons: List[Scheduled[Double]]

  def price(f: DiscountFunction): Double = {
    100.0 * (coupons.map(c => (c.value / (frequency * 100)) * f(c.date)).sum + (f(maturity)))
  }

}

case class USTreasuryNote(cusip: String, issueDate: LocalDate, firstCouponDate: LocalDate, maturity: LocalDate, coupon: Double, origTerm: Int) extends Bond {
  val calendar =  CalendarNY
  val frequency = 2

  val coupons = TreasuryCouponScheduler(firstCouponDate, maturity)(calendar).map(d => Scheduled(d, coupon)).toList
}

object TreasuryCouponScheduler {
  def apply(fcd: LocalDate, mat: LocalDate)(implicit cal: Calendar) = {
    if (cal.isLastBusinessDayOfMonth(fcd) && cal.isLastBusinessDayOfMonth(mat)) {
      Stream.iterate(fcd)(d => cal.toEom(d.plusMonths(6))).takeWhile(d => !d.isAfter(mat))
    } else if (cal.isFirstBusinessDayOfMonth(fcd) && cal.isFirstBusinessDayOfMonth(mat)) {
      Stream.iterate(fcd)(d => cal.toSom(d.plusMonths(6))).takeWhile(d => !d.isAfter(mat))
    } else {
      val dom = math.min(fcd.getDayOfMonth, mat.getDayOfMonth)
      Stream.iterate(fcd)(d => cal.toBusinessDay(d.plusMonths(6).withDayOfMonth(dom))).takeWhile(d => !d.isAfter(mat))
    }
  }
}





