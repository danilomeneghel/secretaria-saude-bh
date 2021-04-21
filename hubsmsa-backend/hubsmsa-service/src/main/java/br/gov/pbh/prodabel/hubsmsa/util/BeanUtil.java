package br.gov.pbh.prodabel.hubsmsa.util;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.NestedNullException;
/**
 * Classe utilitaria para extrair variaveis em objetos aninhados sem necessidade
 * de verificacao de Null
 * @author claudivan.moreira
 *
 */
public final class BeanUtil {
  
  private static final BeanUtilsBean BEAN_UTILS = BeanUtilsBean.getInstance();
  
  private BeanUtil() {
  }

  /**
   * Realiza a extracao do valor da variavel solicitada presente no bean.
   * @param bean Objeto para pesquisar a variavel
   * @param path Variavel para ser extraida do objeto. Ex: "nome", "pessoa.nome", "pessoa.endereco.rua"
   * @param defaultValue Valor a ser retornado caso algum objeto referenciado seja null.
   * @return O valor da variavel passada em <i>path</i> ou defaultValue caso algum objeto referenciado no <i>path</i> seja null.
   * Ex: se for solicitado o path "pessoa.endereco.rua" e o objeto endereco esteja null, o valor em <i>defaultValue</i> sera utilizado
   */
  public static String getString(Object bean, String path, String defaultValue) {
    try {
      return BEAN_UTILS.getNestedProperty(bean, path);
    } catch (NullPointerException | NestedNullException e) {
      return defaultValue;
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

}
