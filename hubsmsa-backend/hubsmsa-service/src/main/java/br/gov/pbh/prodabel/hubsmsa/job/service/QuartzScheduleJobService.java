package br.gov.pbh.prodabel.hubsmsa.job.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import br.gov.pbh.prodabel.hubsmsa.job.AtendimentoPacienteJob;
import br.gov.pbh.prodabel.hubsmsa.job.ReenvioAtendimentoJob;
import br.gov.pbh.prodabel.hubsmsa.job.util.JobUtil;

// TODO: Auto-generated Javadoc
@Singleton
@Startup
public class QuartzScheduleJobService {

  private static final Logger LOGGER = Logger.getLogger(QuartzScheduleJobService.class.getName());

  @Inject
  private CDIJobFactory jobFactory;

  private Scheduler scheduler;

  /**
   * Post construct.
   */
  @PostConstruct
  public void postConstruct() {

    try {
      createScheduler();

      Entry<JobDetail, Trigger> jobBuscarAtendimentos =
          createJob("buscar-atendimentos", "sigrah", JobUtil.CRON_EXPRESSION_5MINUTOS,
              AtendimentoPacienteJob.class);
      addJobEntry(jobBuscarAtendimentos, this.scheduler);

      Entry<JobDetail, Trigger> jobReenvioAtendimento =
          createJob("reenvio-sigrah", "sigrah", JobUtil.CRON_EXPRESSION_10MINUTOS,
              ReenvioAtendimentoJob.class);
      addJobEntry(jobReenvioAtendimento, this.scheduler);

    } catch (SchedulerException | NumberFormatException e) {
      LOGGER.log(Level.SEVERE, "Erro ao criar quartz-schedules", e);
    }
  }

  /**
   * Adds the job entry.
   *
   * @param entry the entry
   * @param scheduler the scheduler
   * @throws SchedulerException the scheduler exception
   */
  public static void addJobEntry(Entry<JobDetail, Trigger> entry, Scheduler scheduler)
      throws SchedulerException {
    DateFormat df = new SimpleDateFormat("HH:mm:ss");

    if (scheduler.isStarted()) {
      if (scheduler.checkExists(entry.getKey().getKey())) {
        scheduler.deleteJob(entry.getKey().getKey());
      }
      // Armazena o agendamento
      scheduler.scheduleJob(entry.getKey(), entry.getValue());
      LOGGER.info("\n.... Adiciona Job ....\njob-name: " + entry.getKey().getKey().getName()
          + "\njob-trigger: " + df.format(entry.getValue().getNextFireTime())
          + System.lineSeparator());
    }
  }

  /**
   * Creates the job.
   *
   * @param name the name
   * @param group the group
   * @param cronSchedule the cron schedule
   * @param classJob the class job
   * @return the entry
   */
  public static Entry<JobDetail, Trigger> createJob(String name, String group, String cronSchedule,
      Class<? extends Job> classJob) {

    // Criar a chave para o job e amarra ela no grupo
    JobKey jobKey = new JobKey("job-" + name, group);

    // Constroi o job detail para o Job Teste
    JobDetail jobDetail = JobBuilder.newJob(classJob).withIdentity(jobKey).build();

    // especifica o periodo que o job ira rodar
    Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger-" + name, group)
        .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule)).build();

    return new AbstractMap.SimpleEntry<>(jobDetail, trigger);
  }

  /**
   * Creates the scheduler.
   *
   * @throws SchedulerException the scheduler exception
   */
  public void createScheduler() throws SchedulerException {
    Properties props = null;
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("quartz.properties")) {
      props = new Properties();
      props.load(input);
    } catch (IOException e) {
      throw new SchedulerException("Erro ao carregar arquivo quartz.properties");
    }

    // agenda o job
    scheduler = new StdSchedulerFactory(props).getScheduler();
    scheduler.setJobFactory(jobFactory);
    scheduler.start();
    LOGGER.info("\n.... Inicia Scheduler ....\nscheduler-id: " + scheduler.getSchedulerInstanceId()
        + "\nscheduler-name: " + scheduler.getSchedulerName() + System.lineSeparator());
    }


  /**
   * Creates the scheduler teste local.
   *
   * @throws SchedulerException the scheduler exception
   */
  public void createSchedulerTesteLocal() throws SchedulerException {
    Properties props = new Properties();
    props.put(StdSchedulerFactory.PROP_JOB_STORE_CLASS, "org.quartz.simpl.RAMJobStore");
    props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME,
        QuartzScheduleJobService.class.getName());
    props.put("org.quartz.threadPool.threadCount", "10");

    // schedule the job
    scheduler = new StdSchedulerFactory(props).getScheduler();
    scheduler.setJobFactory(jobFactory);
    scheduler.start();
    LOGGER.info("\n.... Inicia Scheduler ....\nscheduler-id: " + scheduler.getSchedulerInstanceId()
        + "\nscheduler-name: " + scheduler.getSchedulerName() + System.lineSeparator());
  }

  /**
   * Destroy.
   */
  @PreDestroy
  public void destroy() {
    try {
      scheduler.shutdown();
      LOGGER.info(
          "\n.... Shutdown Scheduler ....\nscheduler-id: " + scheduler.getSchedulerInstanceId()
              + "\nscheduler-name: " + scheduler.getSchedulerName());
    } catch (SchedulerException e) {
      LOGGER.log(Level.SEVERE, "Erro ao finalizar quartz-schedule", e);
    }
    }
}