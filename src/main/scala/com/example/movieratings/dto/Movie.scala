package com.example.movieratings.dto

import org.apache.commons.csv.CSVRecord

import scala.util.control.Exception._

case class Movie(id: Int, yearOfRelease: Int, title: String)

object Movie {

  def apply(record: CSVRecord): Option[Movie] =
    for {
      id <- allCatch.opt(record.get(0).toInt)
      yearOfRelease <- allCatch.opt(record.get(1).toInt)
      title <- allCatch.opt(record.get(2))
    } yield Movie(id, yearOfRelease, title)
}
