package controllers

import play.api.mvc.{ AnyContent, Action, Controller }

class HealthCheckController extends Controller {

  def index: Action[AnyContent] = Action {
    Ok("OK")
  }

}
