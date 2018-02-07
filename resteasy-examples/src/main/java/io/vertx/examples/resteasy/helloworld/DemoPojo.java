package io.vertx.examples.resteasy.helloworld;

import java.io.Serializable;

/**
 * Created by numbcode@gmail.com on 2018/2/8.
 */
public class DemoPojo implements Serializable {
  private String name;
  private String age;

  public String getName() {
    return name;
  }

  public DemoPojo setName(String name) {
    this.name = name;
    return this;
  }

  public String getAge() {
    return age;
  }

  public DemoPojo setAge(String age) {
    this.age = age;
    return this;
  }
}
