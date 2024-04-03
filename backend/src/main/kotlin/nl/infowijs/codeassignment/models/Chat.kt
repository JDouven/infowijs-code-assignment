package nl.infowijs.codeassignment.models

import com.fasterxml.jackson.annotation.JsonProperty
import io.vertx.json.schema.JsonSchema
import io.vertx.json.schema.common.dsl.Schemas.*
import io.vertx.json.schema.draft7.dsl.Keywords.maxLength
import io.vertx.sqlclient.templates.RowMapper

data class Chat(
  var id: Int,
  @JsonProperty("owner_id") var ownerId: Int,
  @JsonProperty("person_id") var personId: Int,
  val person: Person,
  val name: String
) {
  companion object {
    val SCHEMA: JsonSchema = JsonSchema.of(
      objectSchema()
        .requiredProperty("person_id", intSchema())
        .requiredProperty("name", stringSchema().with(maxLength(128)))
        .toJson()
    )
    val ROW_MAPPER = RowMapper { row ->
      Chat(
        id = row.getInteger("c_id"),
        name = row.getString("c_name"),
        ownerId = row.getInteger("owner_id"),
        personId = row.getInteger("person_id"),
        person = Person(
          id = row.getInteger("p_id"),
          name = row.getString("p_name"),
          avatar = row.getString("avatar"),
          email = try {
            row.getString("email")
          } catch (e: NoSuchElementException) {
            null
          },
          phone = try {
            row.getString("phone")
          } catch (e: NoSuchElementException) {
            null
          }
        )
      )
    }
  }
}
