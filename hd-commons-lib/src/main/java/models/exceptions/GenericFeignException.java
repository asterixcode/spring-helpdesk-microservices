package models.exceptions;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenericFeignException extends RuntimeException {
  private Integer status;
  private Map error;
}
