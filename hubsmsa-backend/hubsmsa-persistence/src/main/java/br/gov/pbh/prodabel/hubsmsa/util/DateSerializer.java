package br.gov.pbh.prodabel.hubsmsa.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateSerializer extends StdSerializer<Date> {

  private static final long serialVersionUID = 2477078807920812239L;

  final SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");

  /**
   * Instantiates a new date serializer.
   */
  public DateSerializer() {
    super(Date.class);
  }

  @Override
  public void serialize(Date value, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    String data = dateFormater.format(value);
    generator.writeString(data);
  }
}
