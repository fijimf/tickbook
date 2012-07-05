package com.fijimf.util

import org.joda.time.LocalDate


trait DayCounter extends Function2[LocalDate,LocalDate, Double]

object ActualActual extends DayCounter{
  def apply(v1: LocalDate, v2: LocalDate) = 0.0
}
