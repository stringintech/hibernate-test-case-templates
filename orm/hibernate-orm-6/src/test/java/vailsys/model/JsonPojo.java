package vailsys.model;

import java.io.Serializable;

public class JsonPojo implements Serializable {

  String data;

  public JsonPojo(String data) {
    this.data = data;
  }

  // getter and setter are here to avoid Jackson "No serializer found" exception
  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
