package om.example.movieratings

import com.example.movieratings.ReportGenerator.readRecords
import com.example.movieratings.dto.Movie
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper

class MovieTest extends AnyFlatSpec with Matchers {

  "Movie apply" should "be one correct film" in {
    val actual = readRecords("./src/test/resources/test_movie_records.txt")
      .map(_.flatMap(Movie(_)))
      .toList
      .flatten

    actual shouldBe Seq(Movie(17756,1988,"Dinosaur Planet"))
  }
}
