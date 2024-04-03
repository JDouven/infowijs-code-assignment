package nl.infowijs.codeassignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.json.schema.JsonSchema
import io.vertx.json.schema.common.dsl.Schemas.*
import io.vertx.json.schema.draft7.dsl.Keywords.maxLength
import io.vertx.sqlclient.templates.RowMapper
import java.time.Instant

data class Message(
  var id: Int?,
  @JsonProperty("chat_id") var chatId: Int,
  @JsonProperty("sender_id") val senderId: Int,
  val sender: Person,
  val message: String,
  var datetime: Instant
) {
  companion object {
    val SCHEMA: JsonSchema = JsonSchema.of(
      objectSchema()
        .requiredProperty("chat_id", intSchema())
        .requiredProperty("sender_id", stringSchema())
        .requiredProperty("message", stringSchema().with(maxLength(512)))
        .toJson()
    )
    val ROW_MAPPER = RowMapper { row ->
      Message(
        id = row.getInteger("m_id"),
        chatId = row.getInteger("chat_id"),
        message = row.getString("message"),
        datetime = Instant.parse(row.getString("datetime")),
        senderId = row.getInteger("sender_id"),
        sender = Person(
          id = row.getInteger("p_id"),
          name = row.getString("name"),
          avatar = row.getString("avatar"),
          email = row.getString("email"),
          phone = row.getString("phone"),
        )
      )
    }
  }
}
