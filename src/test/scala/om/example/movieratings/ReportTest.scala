package om.example.movieratings

import com.example.movieratings.ReportGenerator
import com.example.movieratings.ReportGenerator.{generateMovieReport, readRecords}
import com.example.movieratings.dto.{Movie, Report}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

import scala.collection.Seq

class ReportTest extends AnyFlatSpec with Matchers {

  "Report apply" should "one fields ordering" in {
    val reports = Seq(
      Report("title1", 2000, 1.0, 10),
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 3.0, 10),
    )
    reports.sorted should be (Seq(
      Report("title1", 2000, 1.0, 10),
      Report("title2", 2000, 3.0, 10),
      Report("title3", 2000, 5.0, 10),
    )
    )
  }

  "Report sort" should "one fields ordering" in {
    val reports = Seq(
      Report("title1", 2000, 1.0, 10),
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 3.0, 10),
    )
    reports.sorted should be (Seq(
      Report("title1", 2000, 1.0, 10),
      Report("title2", 2000, 3.0, 10),
      Report("title3", 2000, 5.0, 10),
    )
    )
  }

  "Report sort" should "one fields ordering reverse" in {
    val reports = Seq(
      Report("title1", 2000, 1.0, 10),
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 3.0, 10),
    )
    reports.sorted(Ordering[Report].reverse) should be (Seq(
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 3.0, 10),
      Report("title1", 2000, 1.0, 10),
    )
    )
  }

  "Report sort" should "two fields ordering" in {
    val reports = Seq(
      Report("title1", 2000, 5.0, 10),
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 5.0, 10),
    )
    reports.sorted should be (Seq(
      Report("title1", 2000, 5.0, 10),
      Report("title2", 2000, 5.0, 10),
      Report("title3", 2000, 5.0, 10),
    )
    )
  }

  "Report sort" should "two fields ordering reverse" in {
    val reports = Seq(
      Report("title1", 2000, 5.0, 10),
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 5.0, 10),
    )
    reports.sorted(Ordering[Report].reverse) should be (Seq(
      Report("title3", 2000, 5.0, 10),
      Report("title2", 2000, 5.0, 10),
      Report("title1", 2000, 5.0, 10),
    )
    )
  }


  "Report for 2 films" should "2 films" in {
    val generateFilmReportFn: Movie => List[Report] = generateMovieReport("./src/test/resources/training_set_test")(_)
    val actual = ReportGenerator.generateCSVReport("./src/test/resources/movie_titles_test.txt", generateFilmReportFn)
    actual shouldBe List(
      List("Dinosaur Planet", 1988, 3.770463651974814, 8735),
      List("Isle of Man TT 2004 Review", 1989, 3.603991054532943, 5813)
    )
  }

  "Report no movie titles file" should "be no errors" in {
    val generateFilmReportFn: Movie => List[Report] = generateMovieReport("./src/test/resources/training_set_test")(_)
    val actual = ReportGenerator.generateCSVReport("", generateFilmReportFn)
    actual shouldBe List()
  }

  "Report no training set" should "be no errors" in {
    val generateFilmReportFn: Movie => List[Report] = generateMovieReport("./src/test/resources/training_set_test_empty")(_)
    val actual = ReportGenerator.generateCSVReport("./src/test/resources/movie_titles_test.txt", generateFilmReportFn)
    actual shouldBe List()
  }

  "Report for 3 films one no match" should "2 films" in {
    val generateFilmReportFn: Movie => List[Report] = generateMovieReport("./src/test/resources/training_set_test")(_)
    val actual = ReportGenerator.generateCSVReport("./src/test/resources/movie_titles_test_one_no_match.txt", generateFilmReportFn)
    actual shouldBe List(
      List("Dinosaur Planet", 1988, 3.770463651974814, 8735),
      List("Isle of Man TT 2004 Review", 1989, 3.603991054532943, 5813)
    )
  }
}
