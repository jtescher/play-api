package com.example.api.controllers.utils

import com.example.api.models.exceptions.ModelFormatException
import play.api.libs.json._
import play.api.mvc.{ BodyParser, BodyParsers }
import scala.concurrent.ExecutionContext

trait ControllerConventions {

  protected def jsonModelParser[ModelClass](path: JsPath)(implicit reads: Reads[ModelClass], ec: ExecutionContext): BodyParser[ModelClass] = {
    BodyParsers.parse.json.map { jsValue =>
      jsValue.validate(path.json.pick[JsObject]).flatMap(_.validate[ModelClass]) match {
        case JsSuccess(model, _) => model
        case JsError(errors) => throw ModelFormatException(errors)
      }
    }
  }

}
