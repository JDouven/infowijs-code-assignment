package nl.infowijs.codeassignment.utils

import io.vertx.core.json.Json
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import io.vertx.json.schema.OutputUnit


object ResponseUtils {
  private const val CONTENT_TYPE_HEADER: String = "Content-Type"
  private const val APPLICATION_JSON: String = "application/json"

  fun buildOkResponse(rc: RoutingContext, data: Any) {
    val body = JsonObject().put("data", data)
    rc.response()
      .setStatusCode(200)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(body))
  }

  fun buildCreatedResponse(rc: RoutingContext, data: Any) {
    val body = JsonObject().put("data", data)
    rc.response()
      .setStatusCode(201)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(Json.encodePrettily(body))
  }

  fun buildErrorResponse(rc: RoutingContext, throwable: Throwable) {
    val (status, message) = when (throwable) {
      is IllegalArgumentException, is IllegalStateException, is NullPointerException -> {
        Pair(400, throwable.message)
      }

      is NoSuchElementException -> {
        Pair(404, throwable.message)
      }

      else -> {
        Pair(500, "Internal server error")
      }
    }
    rc.response()
      .setStatusCode(status)
      .setStatusMessage(message)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(JsonObject().put("error", message).encodePrettily())
  }

  fun buildErrorResponse(rc: RoutingContext, errors: List<OutputUnit>) {
    rc.response()
      .setStatusCode(400)
      .putHeader(CONTENT_TYPE_HEADER, APPLICATION_JSON)
      .end(JsonObject().put("validationErrors", errors.map { it.error }).encodePrettily())
  }
}
