package br.gov.pbh.prodabel.hubsmsa.util.json.masked;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>Anotacao para formatar valores retornados ao front-end.</p>
 * 
 * Uso:<br>
 * 
 * <pre>
 *  public class Empresa {
 *  
 *     {@code}@Masked(format = "##.###.###/####-##")
 *     private String cnpj;
 *     
 *     {@code}@Masked(format = "(##) #####-####")
 *     private String telefone;
 *  }
 *  empresa.setCnpj("18236120000158")
 *  empresa.setTelefone("83999518730")
 * </pre>
 * 
 * Resultado:<br>
 * 
 * <pre>
 *  {"cnpj":"18.236.120/0001-58", "telefone": "(83) 99951-8730"}
 * </pre>
 * @author claudivan.moreira
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using=MaskedSerializer.class)
public @interface Masked {
  
  String format();

}
