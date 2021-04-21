package br.gov.pbh.prodabel.hubsmsa.job.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public final class JobUtil {

  private static final Logger LOGGER = Logger.getLogger(JobUtil.class.getName());

  public static final String CRON_EXPRESSION_22H = "0 0 22 1/1 * ? *";
  public static final String CRON_EXPRESSION_1MINUTO = "0 * * ? * *";
  public static final String CRON_EXPRESSION_5MINUTOS = "0 0/5 * 1/1 * ? *";
  public static final String CRON_EXPRESSION_10MINUTOS = "0 0/10 * 1/1 * ? *";

  /**
   * Instantiates a new job util.
   */
  private JobUtil() {}

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
      // Armazena o agendamento
      scheduler.scheduleJob(entry.getKey(), entry.getValue());
      LOGGER.info("\n.....Adiciona Job.....\njob-name: " + entry.getKey().getKey().getName()
          + "\njob-trigger: " + df.format(entry.getValue().getNextFireTime())
          + "\n...............");
    }
  }

  public static Logger getLogger() {
    return LOGGER;
  }
}
