package com.example.api.controllers.v1

import akka.stream.Materializer
import com.example.api.models.exceptions.{ ModelFormatException, ModelNotFoundException }
import factories.PostFactory
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play.{ OneAppPerSuite, PlaySpec }
import play.api.http.MimeTypes.JSON
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test.{ FakeApplication, FakeRequest }
import scala.concurrent.ExecutionContext
import utils.{ DatabaseCleaner, TestDatabaseConfig }

class PostsControllerIntegrationSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterEach with ScalaFutures {
  implicit val ec = app.injector.instanceOf[ExecutionContext]
  implicit val mat = app.injector.instanceOf[Materializer]
  implicit override lazy val app = FakeApplication(additionalConfiguration = TestDatabaseConfig.config)
  val databaseCleaner = app.injector.instanceOf[DatabaseCleaner]
  val postFactory = app.injector.instanceOf[PostFactory]

  override def beforeEach(): Unit = {
    databaseCleaner.cleanDatabase
  }

  "#index" should {
    "return all posts" in {
      val post = postFactory.create()

      val response = route(app, FakeRequest(GET, "/v1/posts")).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe Json.obj(
        "posts" -> Json.arr(Json.obj(
          "id" -> post.id,
          "title" -> post.title,
          "body" -> post.body
        ))
      )
    }

    "not return deleted posts" in {
      postFactory.create(deleted = true)

      val response = route(app, FakeRequest(GET, "/v1/posts")).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe Json.obj(
        "posts" -> Json.arr()
      )
    }
  }

  "#create" should {
    "return the persisted post" in {
      val post = postFactory.build()
      val postJson = Json.obj(
        "post" -> Json.obj(
          "id" -> post.id,
          "title" -> post.title,
          "body" -> post.body
        )
      )

      val response = route(app, FakeRequest(POST, "/v1/posts").withJsonBody(postJson)).get

      status(response) mustBe CREATED
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe postJson
    }

    "return 422 with error message if the format is incorrect" in {
      val controller = app.injector.instanceOf[PostsController]
      val post = postFactory.build()
      val postJson = Json.obj(
        "post" -> Json.obj(
          "id" -> post.id,
          "title" -> "",
          "body" -> post.body
        )
      )

      val response = call(controller.create, FakeRequest(POST, "/v1/posts").withJsonBody(postJson))

      response.failed.futureValue mustBe a[ModelFormatException]
    }
  }

  "#show" should {
    "return the post if found" in {
      val post = postFactory.create()

      val response = route(app, FakeRequest(GET, s"/v1/posts/${post.id}")).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe Json.obj(
        "post" -> Json.obj(
          "id" -> post.id,
          "title" -> post.title,
          "body" -> post.body
        )
      )
    }

    "return 404 if not found" in {
      val controller = app.injector.instanceOf[PostsController]
      val post = postFactory.build()

      val response = call(controller.show(post.id), FakeRequest(GET, s"/v1/posts/${post.id}"))

      response.failed.futureValue must equal(ModelNotFoundException)
    }
  }

  "#update" should {
    "return the updated post if successful" in {
      val post = postFactory.create()
      val postJson = Json.obj(
        "post" -> Json.obj(
          "id" -> post.id,
          "title" -> "changed-post-title",
          "body" -> post.body
        )
      )

      val response = route(app, FakeRequest(PUT, s"/v1/posts/${post.id}").withJsonBody(postJson)).get

      status(response) mustBe OK
      contentType(response) mustBe Some(JSON)
      contentAsJson(response) mustBe postJson
    }

    "return 422 with error message if the format is incorrect" in {
      val controller = app.injector.instanceOf[PostsController]
      val post = postFactory.build()
      val postJson = Json.obj(
        "post" -> Json.obj(
          "id" -> post.id,
          "title" -> "",
          "body" -> post.body
        )
      )

      val response = call(controller.update(post.id), FakeRequest(POST, s"/v1/posts/${post.id}").withJsonBody(postJson))

      response.failed.futureValue mustBe a[ModelFormatException]
    }
  }

  "#destroy" should {
    "delete the post" in {
      val post = postFactory.create()

      val response = route(app, FakeRequest(DELETE, s"/v1/posts/${post.id}")).get

      status(response) mustBe NO_CONTENT
    }
  }

}
