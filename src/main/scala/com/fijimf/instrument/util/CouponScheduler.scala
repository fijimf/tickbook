package com.fijimf.instrument.util

import org.joda.time.LocalDate

trait CouponScheduler {
   def couponDates:Seq[LocalDate]
}
