package com.example.api.controllers

import org.scalatestplus.play.{ OneAppPerSuite, PlaySpec }
import play.api.http.MimeTypes.TEXT
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HealthCheckIntegrationSpec extends PlaySpec with OneAppPerSuite {

  "#index" should {
    "return OK" in {
      val response = route(FakeRequest(GET, "/")).get

      status(response) mustBe OK
      contentType(response) mustBe Some(TEXT)
      contentAsString(response) mustBe "OK"
    }
  }

}
