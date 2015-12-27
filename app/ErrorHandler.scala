import com.google.inject.Inject
import java.util.UUID
import models.errors.ModelNotFoundException
import models.exceptions.ModelFormatException
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.http.Status.{ NOT_FOUND, UNPROCESSABLE_ENTITY }
import play.api.i18n.{ MessagesApi, I18nSupport, Messages }
import play.api.libs.json.Json
import play.api.mvc.Results.{ InternalServerError, Status }
import play.api.mvc.{ RequestHeader, Result }
import scala.concurrent.Future
import scala.util.control.NonFatal

class ErrorHandler @Inject() (val messagesApi: MessagesApi) extends HttpErrorHandler with I18nSupport {

  // 4xx response
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val id = UUID.randomUUID
    Logger.error(s"$id - Client error occurred: $message. Returning $statusCode for ${request.uri}")

    Future.successful(Status(statusCode)(Json.obj(
      "error" -> Json.obj(
        "id" -> id,
        "statusCode" -> statusCode.toString,
        "message" -> message
      )
    )))
  }

  // 5xx response
  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    exception match {
      case ModelNotFoundException => onClientError(request, NOT_FOUND, Messages(s"exceptions.$NOT_FOUND"))
      case e: ModelFormatException => onClientError(request, UNPROCESSABLE_ENTITY, Messages(e.getI18nKey))
      case NonFatal(e) => {
        val id = UUID.randomUUID
        Logger.error(s"$id - Error while processing request. Returning 500 for ${request.uri}")

        Future.successful(InternalServerError(Json.obj(
          "error" -> Json.obj(
            "id" -> id,
            "statusCode" -> "500",
            "message" -> Messages("exceptions.500")
          )
        )))
      }
    }
  }

}
