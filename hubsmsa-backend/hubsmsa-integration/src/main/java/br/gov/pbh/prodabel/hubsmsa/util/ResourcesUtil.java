package br.gov.pbh.prodabel.hubsmsa.util;

import java.util.ResourceBundle;

public class ResourcesUtil {
    
	private ResourcesUtil() {
		
	}
	
    /**
     * Recuperar valor de arquivo de propriedades
     *
     * @param bundleName - Nome do arquivo que será carregado
     * @param name - Nome da propriedade no arquivo
     * 
     * @return String - Valor da propriedade
     */
    public static String getProperty(String bundleName, String name) {
    	
    	ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
    	
    	return bundle.getString(name);
    	
    }
}
