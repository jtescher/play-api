package com.example.api

import com.example.api.models.exceptions.{ ModelFormatException, ModelNotFoundException }
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{ OneAppPerSuite, PlaySpec }
import play.api.data.validation.ValidationError
import play.api.http.MimeTypes.JSON
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }
import play.api.libs.json.{ JsPath, KeyPathNode }
import play.api.test.FakeRequest
import play.api.test.Helpers._

class ErrorHandlerSpec extends PlaySpec with OneAppPerSuite with ScalaFutures with I18nSupport {
  implicit val messagesApi = app.injector.instanceOf[MessagesApi]
  val errorHandler = app.injector.instanceOf[ErrorHandler]

  "404 errors" should {
    "return json" in {
      val fakeRequest = FakeRequest(POST, "/v2/not-found")

      val res = errorHandler.onServerError(fakeRequest, ModelNotFoundException)

      (contentAsJson(res) \ "error" \ "message").as[String] mustBe Messages("exceptions.404")
      (contentAsJson(res) \ "error" \ "statusCode").as[String] mustBe "404"
      contentType(res) mustBe Some(JSON)
    }
  }

  "422 errors" should {
    "return json" in {
      val fakeRequest = FakeRequest(POST, "/v2/model-format")
      val ex = ModelFormatException(Seq((JsPath(List(KeyPathNode("modelName/attributeName"))), Seq(ValidationError("Invalid format")))))

      val res = errorHandler.onServerError(fakeRequest, ex)

      (contentAsJson(res) \ "error" \ "message").as[String] mustBe Messages("model_name.attribute_name.invalid")
      (contentAsJson(res) \ "error" \ "statusCode").as[String] mustBe "422"
      contentType(res) mustBe Some(JSON)
    }
  }

  "500 errors" should {
    "return json" in {
      val fakeRequest = FakeRequest(GET, "/v2/internal-server-error")
      val ex = new RuntimeException("Some exception message")

      val res = errorHandler.onServerError(fakeRequest, ex)

      (contentAsJson(res) \ "error" \ "message").as[String] must equal(Messages("exceptions.500"))
      (contentAsJson(res) \ "error" \ "statusCode").as[String] must equal("500")
      contentType(res) must equal(Some(JSON))
    }
  }

}
