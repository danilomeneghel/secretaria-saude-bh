package br.gov.pbh.prodabel.hubsmsa.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.LogContatoNotificadoDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.ContatoEmpresaServico;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogContatoNotificado;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.LogServico;

@Stateless
@LocalBean
public class LogContatoNotificadoService {

  @EJB
  private LogContatoNotificadoDAO logContatoNotificadoDAO;
  
  private LogContatoNotificado logContatoNotificado;
  
  

  /**
   * Gravar log contato notificado.
   *
   * @param logContatoNotificado the log contato notificado
   */
  public void gravarLogContatoNotificado(LogContatoNotificado logContatoNotificado) {
    logContatoNotificadoDAO.merge(logContatoNotificado);
  }
  
  
  public LogContatoNotificado gravarLogNotificadoSucesso(LogServico logServico,
	      ContatoEmpresaServico contato) {	    
	prepararLogNotificado(logServico, contato);
	logContatoNotificado.setNotificacaoSucesso("1");
	logContatoNotificado.setNotificacaoFalha("0");
    gravarLogContatoNotificado(logContatoNotificado);
	return logContatoNotificado;
  }
  
  public LogContatoNotificado gravarLogNotificadoFalha(LogServico logServico,
	      ContatoEmpresaServico contato) {	    
	prepararLogNotificado(logServico, contato);
	logContatoNotificado.setNotificacaoSucesso("0");
	logContatoNotificado.setNotificacaoFalha("1");
    gravarLogContatoNotificado(logContatoNotificado);
	return logContatoNotificado;
  }
  
  private void prepararLogNotificado(LogServico logServico,
	      ContatoEmpresaServico contato) {
	logContatoNotificado = new LogContatoNotificado();
	logContatoNotificado.setLogServico(logServico);
	logContatoNotificado.setContatoEmpresaServico(contato);
   }

}
