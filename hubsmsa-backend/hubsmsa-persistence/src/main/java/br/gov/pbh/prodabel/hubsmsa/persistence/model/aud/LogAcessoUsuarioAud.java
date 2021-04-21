package br.gov.pbh.prodabel.hubsmsa.persistence.model.aud;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RevisionType;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.Revision;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.EntidadeBase;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Sistema;

@Entity
@Table(name = "de_para_aud")
public class LogAcessoUsuarioAud extends EntidadeBase<Long> {

	private static final long serialVersionUID = 8980467199708529279L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_log_acesso_usuario")
	@Column(name = "id_log_acesso_usuario", nullable = false, unique = true)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario_log_acesso_usuario"), nullable = false)
	private Sistema idUsuario;

	@NotAudited
	@Column(name = "data_atualizacao", nullable = false)
	private Timestamp dataAtualizacao;

	@JoinColumn(name = "id_revisao", foreignKey = @ForeignKey(name = "fk_revisao_de_para_aud"), nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Revision revisao;

	@Column(name = "rev_type")
	@Enumerated(EnumType.ORDINAL)
	private RevisionType revType;

	@Override
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sistema getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Sistema idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Timestamp getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Timestamp dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Revision getRevisao() {
		return revisao;
	}

	public void setRevisao(Revision revisao) {
		this.revisao = revisao;
	}

	public RevisionType getRevType() {
		return revType;
	}

	public void setRevType(RevisionType revType) {
		this.revType = revType;
	}

}
