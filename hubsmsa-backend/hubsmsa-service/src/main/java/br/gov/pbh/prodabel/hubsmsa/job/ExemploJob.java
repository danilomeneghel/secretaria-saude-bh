package br.gov.pbh.prodabel.hubsmsa.job;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExemploJob implements Job {

	private Logger logger = Logger.getLogger(ExemploJob.class.getName());
	
	//Aqui voce pode importar qualquer EJB através da anotação @EJB
	//@EJB
	//private ExemploService service;
	
	@Override
  public void execute(JobExecutionContext jExeCtx) throws JobExecutionException {
		try {
      System.out.println("Teste Job Exemplo...");
			
		} catch (Throwable e) {
			logger.log(Level.SEVERE,"Erro ao processar Job",e);
		}
	}	
}
