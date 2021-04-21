package br.gov.pbh.prodabel.hubsmsa.util.json.masked;

import java.io.IOException;
import java.util.Optional;
import javax.swing.text.MaskFormatter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MaskedSerializer extends StdSerializer<String> implements ContextualSerializer {

  /**
   * 
   */
  private static final long serialVersionUID = -809125322084787489L;
  private Masked maskedAnnotation;

  public MaskedSerializer() {
    super(String.class);
  }

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {

    try {
      MaskFormatter maskFormatter = new MaskFormatter(maskedAnnotation.format());
      maskFormatter.setAllowsInvalid(true);
      maskFormatter.setValueContainsLiteralCharacters(false);
      String text = maskFormatter.valueToString(value);
      gen.writeString(text);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {

    maskedAnnotation = Optional.ofNullable(property).map(v -> {
      return v.getAnnotation(Masked.class);
    }).orElse(null);

    return this;
  }

}
