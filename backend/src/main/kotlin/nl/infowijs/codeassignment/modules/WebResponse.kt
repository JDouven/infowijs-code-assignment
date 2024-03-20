package nl.infowijs.codeassignment.modules

import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext

class WebResponse(val routingContext: RoutingContext) {
  fun end(data: JsonArray) {
    val response = JsonObject().put("data", data)
    end(response.encode())
  }

  fun end(data: JsonObject) {
    val response = JsonObject().put("data", data)
    end(response.encode())
  }

  fun end(data: String) {
    routingContext
        .response()
        .putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
        .setStatusCode(200)
        .setStatusMessage("OK")
        .end(data)
  }
}
