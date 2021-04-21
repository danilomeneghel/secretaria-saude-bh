package br.gov.pbh.prodabel.hubsmsa.persistence.audit;

import java.util.Calendar;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.hibernate.envers.RevisionListener;

/**
 * Listener Envers
 */
public class AuditListener implements RevisionListener {

	@Context
	private SecurityContext sc;
	 
	@Inject
	private UserRevisionService userRevisionService;
	
	private final String USER_DEFAULT = "hubsmsa".toUpperCase();
	
	@Override
	public void newRevision(Object objRevision) {
		
		Revision revision = (Revision) objRevision;
		revision.setMatricula(definirMatricula());
		revision.setDtRevisao(Calendar.getInstance().getTime());
	}
	
	private String definirMatricula() {
		AuditUser auditUser = userRevisionService.getAuditUser();
		return auditUser != null ? auditUser.getMatricula() != null ? auditUser.getMatricula() : auditUser.getNomeUsuario() : USER_DEFAULT;
	}

}
