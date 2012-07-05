package com.fijimf.cashflow

import org.joda.time.{LocalDate, ReadableInstant}

trait DiscountFunction extends PartialFunction[LocalDate, Double] {
}


