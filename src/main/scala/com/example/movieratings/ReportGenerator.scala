package com.example.movieratings

import com.example.movieratings.CsvUtils.readFromFileAsList
import com.example.movieratings.dto.Report.asList
import com.example.movieratings.dto.{Movie, Rating, Report}
import org.apache.commons.csv.CSVRecord

import java.io.File
import java.nio.file.Paths
import scala.util.Try

object ReportGenerator {

  private val formatFn: Int => String = "%07d".format(_)

  def main(args: Array[String]): Unit = {
    val (movieDataSource, trainingSet, output) = getConfig(args)
    val reports = generateCSVReport(movieDataSource, generateMovieReport(trainingSet)(_))
    CsvUtils.writeToFile(reports, new File(output))
  }

  private def getConfig(args: Array[String]): (String, String, String) = {
    assert(args.length == 3, "please add cli parameters: /path/to/movie_titles.txt /path/to/training_set /path/to/output-report.csv")
    val movieDataSource = args.head
    val trainingSet = args(1)
    val output = args(2)
    (movieDataSource, trainingSet, output)
  }

  def generateCSVReport(movieDataSource: String, generateFilmReportFn: Movie => List[Report]): List[List[Any]] = {
    readRecords(movieDataSource)
      .toList
      .flatten
      .map(Movie(_))
      .flatMap(_.toList)
      .filter(movie => movie.yearOfRelease > 1970 && movie.yearOfRelease < 1990)
      .par
      .flatMap(generateFilmReportFn)
      .filter(_.numberOfReviews > 1000)
      .toList
      .sorted(Ordering[Report].reverse)
      .map(asList)
  }

  def generateMovieReport: String => Movie => List[Report] = { trainingSet =>
    mt =>
      val id = formatFn(mt.id)
      val movieFileName = s"$trainingSet/mv_$id.txt"
      readRecords(movieFileName)
        .toList
        .map(_.flatMap(Rating(_)))
        .filter(_.nonEmpty)
        .map { ratingRecords =>
          val numberOfReviews = ratingRecords.size
          val averageRating = ratingRecords.map(_.value).sum / numberOfReviews.toDouble
          Report(mt, averageRating, numberOfReviews)
        }
  }

  def readRecords(fileName: String): Option[List[CSVRecord]] = Try{readFromFileAsList(Paths.get(fileName).toFile)}.toOption
}
