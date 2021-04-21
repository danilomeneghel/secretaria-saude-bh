package br.gov.pbh.prodabel.hubsmsa.util.json.date;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateSerializer extends StdSerializer<java.time.temporal.Temporal> implements ContextualSerializer {

  /**
   * 
   */
  private static final long serialVersionUID = -1537740990393303502L;
  private static final ZoneId timeZoneBr = ZoneId.of("America/Sao_Paulo");
  private Temporal temporalAnnotation;
  
  private DateSerializer() {
    super(java.time.temporal.Temporal.class);
  }

  @Override
  public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
      throws JsonMappingException {
    
    temporalAnnotation = Optional.ofNullable(property).map(beanProperty -> {
      return beanProperty.getAnnotation(Temporal.class);
    }).orElse(null);

    return this;
  }

  @Override
  public void serialize(java.time.temporal.Temporal value, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    
    String text = DateTimeFormatter
    .ofPattern(temporalAnnotation.format())
    .withZone(timeZoneBr)
    .format(value);
    
    gen.writeString(text);
    
  }

}
