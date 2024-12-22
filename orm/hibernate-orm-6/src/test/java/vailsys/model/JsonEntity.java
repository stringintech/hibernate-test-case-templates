package vailsys.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "json_entity")
// This entity has a Map<String, String> as the field which holds the JSON data.
// When persisted, it will generate a single SQL insert statement.
public class JsonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JdbcTypeCode(SqlTypes.JSON)
  Map<String, String> json;

  public JsonEntity() { }

  public JsonEntity(Map<String, String> data) {
    json = data;
  }
}
