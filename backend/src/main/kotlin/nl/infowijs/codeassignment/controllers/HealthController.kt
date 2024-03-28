package nl.infowijs.codeassignment.controllers

import io.vertx.ext.web.RoutingContext
import nl.infowijs.codeassignment.utils.ResponseUtils.buildOkResponse

class HealthController {
  companion object {
    fun healthCheck(routingContext: RoutingContext) {
      buildOkResponse(routingContext, "Healthy")
    }
  }
}
