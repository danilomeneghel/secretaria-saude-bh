package br.gov.pbh.prodabel.hubsmsa.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.apache.commons.lang3.StringUtils;
import br.gov.pbh.prodabel.hubsmsa.dto.EntityDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.EnversDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.ResponseDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.SelecaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.historico.HistoricoAlteracaoDetalheDTO;
import br.gov.pbh.prodabel.hubsmsa.dto.usuario.UsuarioDTO;
import br.gov.pbh.prodabel.hubsmsa.enumeration.MensagemEnum;
import br.gov.pbh.prodabel.hubsmsa.exception.DAOException;
import br.gov.pbh.prodabel.hubsmsa.exception.NegocioException;
import br.gov.pbh.prodabel.hubsmsa.exception.RegistroNaoEncontradoException;
import br.gov.pbh.prodabel.hubsmsa.persistence.audit.UserRevisionService;
import br.gov.pbh.prodabel.hubsmsa.persistence.dao.UsuarioDAO;
import br.gov.pbh.prodabel.hubsmsa.persistence.model.Usuario;
import br.gov.pbh.prodabel.hubsmsa.service.mapper.UsuarioMapper;
import br.gov.pbh.prodabel.hubsmsa.util.MensagemUtil;
import br.gov.pbh.prodabel.hubsmsa.util.ResponseUtil;

// TODO: Auto-generated Javadoc
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class UsuarioService extends GenericService<Long, Usuario> {

  @EJB
  private UsuarioDAO usuarioDAO;

  @EJB
  private UserRevisionService usuarioLogadoService;

  @EJB
  private LogAcessoUsuarioService logAcessoUsuarioService;

  /**
   * Verificar cadastro usuario.
   *
   * @param username the username
   * @return true, if successful
   */
  public boolean verificarCadastroUsuario(final String username) {
    try {
      final boolean usuarioExiste = this.usuarioDAO.verificarCadastroUsuario(username);
      if (usuarioExiste) {
        consultarUsuario(username);
      }
      return usuarioExiste;
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME027));
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar usuario.
   *
   * @param username the username
   * @return the usuario
   */
  public Usuario consultarUsuario(final String username) {
    try {
      final Usuario usuario = this.usuarioDAO.consultarPorLogin(username);
      registrarLogDeAcesso(usuario);
      return usuario;
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME027));
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Consultar usuario.
   *
   * @param username the username
   * @return the usuario
   */
  public Usuario consultarUsuario(final Long idUsuario) {
    try {
      return this.usuarioDAO.consultarPorId(idUsuario);
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }
  }

  /**
   * Registrar log de acesso.
   *
   * @param usuario the usuario
   */
  private void registrarLogDeAcesso(final Usuario usuario) {
    if (usuario != null) {
      this.logAcessoUsuarioService.registrarLog(usuario);
    }
  }

  /**
   * Cadastrar usuario.
   *
   * @param cadastrarUsuario the cadastrar usuario
   * @return the response DTO
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public ResponseDTO<EntityDTO> cadastrarUsuario(final UsuarioDTO cadastrarUsuario) {
    final Usuario usuario = UsuarioMapper.mapper(cadastrarUsuario);
    this.logAcessoUsuarioService.registrarLog(this.usuarioDAO.gravar(usuario));
    return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
        new EntityDTO(usuario.getId()));
  }

  /**
   * Checkin.
   *
   * @return the response DTO
   */
  public ResponseDTO<String> checkin() {

    try {
      final Boolean usuarioEstaCadastro = this.usuarioDAO
          .verificarCadastroUsuario(this.usuarioLogadoService.getAuditUser().getMatricula());
      if (Boolean.TRUE.equals(usuarioEstaCadastro)) {
        final Usuario usuario = this.usuarioDAO
            .consultarPorLogin(this.usuarioLogadoService.getAuditUser().getMatricula());

        this.logAcessoUsuarioService.registrarLog(usuario);
      } else {

        final Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(this.usuarioLogadoService.getAuditUser().getEmail());
        novoUsuario.setNome(this.usuarioLogadoService.getAuditUser().getNomeUsuario());
        novoUsuario.setLogin(this.usuarioLogadoService.getAuditUser().getMatricula());

        this.logAcessoUsuarioService.registrarLog(this.usuarioDAO.gravar(novoUsuario));
      }
      return ResponseUtil.montarRetorno(MensagemUtil.getMessage(MensagemEnum.MSG002),
          StringUtils.EMPTY);
    } catch (final RegistroNaoEncontradoException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME027));
    } catch (final Exception e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    }

  }

  /**
   * Consultar selecao usuario.
   *
   * @return the list
   */
  public List<SelecaoDTO> consultarSelecaoUsuario() {
    try {
      return this.usuarioDAO.consultarSelecaoUsuario();
    } catch (final DAOException e) {
      throw new NegocioException(MensagemUtil.getMessage(MensagemEnum.ME001));
    } catch (final RegistroNaoEncontradoException e) {
      return Collections.emptyList();
    }
  }

  /**
   * Inserir dados usuario.
   *
   * @param historico the historico
   * @param historicoDetalhe the historico detalhe
   */
  public void inserirDadosUsuario(List<HistoricoAlteracaoDTO> historico,
      HistoricoAlteracaoDetalheDTO historicoDetalhe) {
    Usuario usuario;
    try {
      Optional<HistoricoAlteracaoDTO> usuarioOpt = historico.stream().findFirst();

      if (usuarioOpt.isPresent()) {
        usuario = consultarUsuario(usuarioOpt.get().getUsuarioResponsavelAlteracao());
        historicoDetalhe.setEmail(usuario.getEmail());
        historicoDetalhe.setLogin(usuario.getLogin());
        historicoDetalhe.setNome(usuario.getNome());
      }
    } catch (NegocioException e) {
      throw e;
    }
  }

  /**
   * Adiciona o nome do usuario recuperado pelo login.
   *
   * @param enversDto the envers dto
   */
  public void adicionarNomeUsuario(List<EnversDTO> enversDto) {
    Set<String> logins = enversDto.stream().map(e -> e.getUser()).collect(Collectors.toSet());
    Map<String, String> nomeUsuario = new HashMap<>();
    for (String login : logins) {
      nomeUsuario.put(login, consultarUsuario(login).getNome());
    }

    for (EnversDTO dto : enversDto) {
      dto.setUser(nomeUsuario.get(dto.getUser()));
    }
  }

}
