package br.gov.pbh.prodabel.hubsmsa.util.json.date;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>Anotacao para formatar valores recebidos ou retornados ao front-end.</p>
 * 
 * Uso:<br>
 * 
 * <pre>
 *  public class Pessoa {
 *  
 *     {@code}@Temporal(format = "dd/MM/yyyy")
 *     private LocalDate dataNascimento;
 *     
 *     {@code}@Temporal(format = "dd/MM/yyyy HH:mm:ss")
 *     private LocalDateTime ultimaAtualizacao;
 *     
 *  }
 *  pessoa.setDataNascimento(LocalDate.now())
 *  pessoa.setUltimaAtualizacao(LocalDateTime.now())
 * </pre>
 * 
 * Resultado:<br>
 * 
 * <pre>
 *  {"dataNascimento":"10/08/2020","ultimaAtualizacao":"10/08/2020 10:20:42"}
 * </pre>
 * 
 * @author claudivan.moreira
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using=DateSerializer.class)
@JsonDeserialize(using=DateDeserializer.class)
public @interface Temporal {
  
  String format();

}
