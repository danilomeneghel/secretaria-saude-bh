package br.gov.pbh.prodabel.hubsmsa.job.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

@ApplicationScoped
public class CDIJobFactory implements JobFactory {

	@Inject
	private BeanManager beanManager;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Job newJob(TriggerFiredBundle bundle, Scheduler arg1) throws SchedulerException{
		
		Class jobClazz = bundle.getJobDetail().getJobClass();
		Bean bean = beanManager.getBeans(jobClazz).iterator().next();
		CreationalContext ctx = beanManager.createCreationalContext(bean);
		return (Job) beanManager.getReference(bean, jobClazz, ctx);
	}
}