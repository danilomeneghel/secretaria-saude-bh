package br.gov.pbh.prodabel.hubsmsa.util.json.notmasked;

import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class NotMaskedDeserializer extends StdDeserializer<String> {

  /**
   * 
   */
  private static final long serialVersionUID = 117604379006005386L;
  
  public NotMaskedDeserializer() {
    super(String.class);
  }
  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    
    String text = Optional.ofNullable(p.getValueAsString()).map(v -> {
      return v.replaceAll("\\D+", "");
    }).orElse(StringUtils.EMPTY);
    
    return text;
  }

}
