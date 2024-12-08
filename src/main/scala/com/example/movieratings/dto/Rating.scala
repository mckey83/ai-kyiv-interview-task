package com.example.movieratings.dto

import org.apache.commons.csv.CSVRecord

import scala.util.control.Exception.allCatch

case class Rating(value: Int)

object Rating {
  def apply(record: CSVRecord): Option[Rating] = allCatch.opt(record.get(1).toInt).map(Rating(_))
}

