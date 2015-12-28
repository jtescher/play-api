package com.example.api.controllers

import play.api.mvc.{ Action, AnyContent, Controller }

class HealthCheckController extends Controller {

  def index: Action[AnyContent] = Action {
    Ok("OK")
  }

}
