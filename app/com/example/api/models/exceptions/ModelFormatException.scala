package com.example.api.models.exceptions

import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath

case class ModelFormatException(errors: Seq[(JsPath, Seq[ValidationError])]) extends RuntimeException {

  def getI18nKey: String = {
    val formattedPath = errors.head._1.toString().replaceAll("/", ".").replaceAll("\\(.+?\\)", "")
    camelToUnderscores(formattedPath + ".invalid").stripPrefix(".")
  }

  private def camelToUnderscores(name: String) = "[A-Z\\d]".r.replaceAllIn(name, { m =>
    "_" + m.group(0).toLowerCase
  })

}
