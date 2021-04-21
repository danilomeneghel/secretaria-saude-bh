package br.gov.pbh.prodabel.hubsmsa.job;

import javax.ejb.EJB;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import br.gov.pbh.prodabel.hubsmsa.service.AtendimentoPacienteService;

public class ReenvioAtendimentoJob implements Job {

  @EJB
  private AtendimentoPacienteService service;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    service.reprocessarRegistroComFalha();

  }

}
