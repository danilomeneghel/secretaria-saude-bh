package br.gov.pbh.prodabel.hubsmsa.util.json.date;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateDeserializer extends StdDeserializer<java.time.temporal.Temporal>
    implements ContextualDeserializer {

  /**
   * 
   */
  private static final long serialVersionUID = 2683280863094394457L;
  private static final ZoneId timeZoneBr = ZoneId.of("America/Sao_Paulo");
  private Temporal temporalAnnotation;
  private Class<?> propertyType;

  private DateDeserializer() {
    super(java.time.temporal.Temporal.class);
  }
  
  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
      throws JsonMappingException {
    
    temporalAnnotation = Optional.ofNullable(property).map(beanProperty -> {
      propertyType = beanProperty.getType().getRawClass();
      return beanProperty.getAnnotation(Temporal.class);
    }).orElse(null);
    
    return this;
    
  }

  @Override
  public java.time.temporal.Temporal deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter
    .ofPattern(temporalAnnotation.format())
    .withZone(timeZoneBr);
    
    if (LocalDate.class.isAssignableFrom(propertyType)) {
      return LocalDate.parse(p.getValueAsString(), dateTimeFormatter);
    } else if (LocalDateTime.class.isAssignableFrom(propertyType)) {
      return LocalDateTime.parse(p.getValueAsString(), dateTimeFormatter);
    } else {
      throw new IllegalArgumentException("@Temporal can only be used for types LocalDate and LocalDateTime");
    }

  }

  
}
