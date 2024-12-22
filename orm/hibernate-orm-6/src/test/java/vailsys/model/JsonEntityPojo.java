package vailsys.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "json_entity_pojo")
// This entity has a POJO as the field which holds the JSON data.
// When persisted, it will generate a SQL insert followed by a SQL update statement.
public class JsonEntityPojo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  JsonPojo json;

  public JsonEntityPojo() { }

  public JsonEntityPojo(String data) {
    json = new JsonPojo(data);
  }
}
