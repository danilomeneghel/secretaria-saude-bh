package br.gov.pbh.prodabel.hubsmsa.persistence.audit;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * Entidade de revisão do Envers
 */
@Entity
@RevisionEntity(AuditListener.class)
@Table(name = "revisao", schema = "hubsmsa")
@SequenceGenerator(name = "sq_revisao", sequenceName = "sq_revisao", allocationSize = 1, initialValue = 1)
public class Revision implements Serializable{

	private static final long serialVersionUID = -3808318639743875988L;

	@Id
	@GeneratedValue(generator = "sq_revisao", strategy = GenerationType.SEQUENCE)
	@RevisionNumber
	@Column(name = "id_revisao", nullable = false, unique = true)
	private Long id;
	
	@RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_revisao", nullable = false)
	private Date dtRevisao;
	
	@Column(name = "usr_login", nullable = false, length = 10)
	private String matricula; /*Matrícula do usuário na Prodabel*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtRevisao() {
		return dtRevisao;
	}

	public void setDtRevisao(Date dtRevisao) {
		this.dtRevisao = dtRevisao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtRevisao == null) ? 0 : dtRevisao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Revision other = (Revision) obj;
		if (dtRevisao == null) {
			if (other.dtRevisao != null)
				return false;
		} else if (!dtRevisao.equals(other.dtRevisao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
	
}
