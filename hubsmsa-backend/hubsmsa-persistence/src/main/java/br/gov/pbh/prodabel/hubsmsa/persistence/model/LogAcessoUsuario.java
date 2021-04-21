package br.gov.pbh.prodabel.hubsmsa.persistence.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.envers.Audited;

@Entity
@SequenceGenerator(sequenceName = "sq_log_acesso_usuario", name = "sq_log_acesso_usuario", allocationSize = 1)
@Table(name = "log_acesso_usuario")
@Audited
public class LogAcessoUsuario extends EntidadeBase<Long> {
	private static final long serialVersionUID = 1887922655988681791L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_log_acesso_usuario")
	@Column(name = "id_log_acesso_usuario", nullable = false, unique = true)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", foreignKey = @ForeignKey(name = "fk_usuario_log_acesso_usuario"), nullable = false)
	private Usuario usuario;

	@Column(name = "data_acesso", nullable = false)
	private Timestamp dataAcesso;

	@Override
  public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Timestamp getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Timestamp dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
