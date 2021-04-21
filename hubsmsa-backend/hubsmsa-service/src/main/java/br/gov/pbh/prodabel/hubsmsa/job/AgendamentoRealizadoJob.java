package br.gov.pbh.prodabel.hubsmsa.job;

import java.util.logging.Logger;
import javax.ejb.EJB;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import br.gov.pbh.prodabel.hubsmsa.service.AgendamentoRealizadoService;

public class AgendamentoRealizadoJob implements Job {

  private Logger logger = Logger.getLogger(AgendamentoRealizadoJob.class.getName());

  @EJB
  AgendamentoRealizadoService service;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    service.processar("2020-11-25");

  }

}
