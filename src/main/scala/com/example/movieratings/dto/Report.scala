package com.example.movieratings.dto

case class Report(title: String, yearOfRelease: Int, averageRating: Double, numberOfReviews: Int) extends Ordered[Report] {
  def compare(that: Report): Int =
    averageRating compare that.averageRating match {
      case 0 => title compare that.title
      case c => c
    }
}

object Report {
  def apply(movie: Movie, averageRating: Double, numberOfReviews: Int): Report =
    Report(
      title = movie.title,
      yearOfRelease = movie.yearOfRelease,
      averageRating = averageRating,
      numberOfReviews = numberOfReviews
    )

  def asList(report: Report): List[Any] =
    List(report.title, report.yearOfRelease, report.averageRating, report.numberOfReviews)
}
