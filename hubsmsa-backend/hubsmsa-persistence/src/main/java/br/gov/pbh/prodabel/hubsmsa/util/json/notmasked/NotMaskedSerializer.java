package br.gov.pbh.prodabel.hubsmsa.util.json.notmasked;

import java.io.IOException;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class NotMaskedSerializer extends StdSerializer<String> {

  /**
   * 
   */
  private static final long serialVersionUID = -809125322084787489L;
  
  protected NotMaskedSerializer() {
    super(String.class);
  }

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    
    String text = Optional.ofNullable(value).map(v -> {
      return v.replaceAll("\\D+", "");
    }).orElse(StringUtils.EMPTY);
    
    gen.writeString(text);
    
  }

}
