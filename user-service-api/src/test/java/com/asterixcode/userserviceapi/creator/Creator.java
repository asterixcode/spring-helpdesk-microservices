package com.asterixcode.userserviceapi.creator;

import lombok.experimental.UtilityClass;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@UtilityClass
public class Creator {

  private static final PodamFactory factory = new PodamFactoryImpl();

  public static <T> T generateMock(final Class<T> clazz) {
    return factory.manufacturePojo(clazz);
  }
}
