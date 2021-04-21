package br.gov.pbh.prodabel.hubsmsa.util.json.notmasked;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>Anotacao para remover formatacao de valores recebidos e retornados ao front-end.</p>
 * 
 * Uso:<br>
 * 
 * <pre>
 *  public class Empresa {
 *  
 *     {@code}@NotMasked
 *     private String cnpj;
 *     
 *     {@code}@NotMasked
 *     private String telefone;
 *  }
 *  empresa.setCnpj("18.236.120/0001-58")
 *  empresa.setTelefone("(83) 99951-8730")
 * </pre>
 * 
 * Resultado:<br>
 * 
 * <pre>
 *  {"cnpj":"18236120000158", "telefone": "83999518730"}
 * </pre>
 * @author claudivan.moreira
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using=NotMaskedSerializer.class)
@JsonDeserialize(using=NotMaskedDeserializer.class)
public @interface NotMasked {

}
