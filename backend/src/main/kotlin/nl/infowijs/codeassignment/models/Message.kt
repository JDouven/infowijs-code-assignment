package nl.infowijs.codeassignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.json.schema.JsonSchema
import io.vertx.json.schema.common.dsl.Schemas.*
import io.vertx.json.schema.draft7.dsl.Keywords.maxLength
import java.time.Instant

data class Message(
  var id: Int?,
  @JsonProperty("chat_id") var chatId: Int,
  val sender: String,
  val message: String,
  var datetime: Instant
) {
  companion object {
    val schema: JsonSchema = JsonSchema.of(
      objectSchema()
        .requiredProperty("chat_id", intSchema())
        .requiredProperty("sender", stringSchema().with(maxLength(128)))
        .requiredProperty("message", stringSchema().with(maxLength(512)))
        .toJson()
    )
  }
}
