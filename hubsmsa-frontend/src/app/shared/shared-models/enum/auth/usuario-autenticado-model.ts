import { PermissaoUsuarioEnum } from '@shared/models/enum/auth/permissao-usuario.enum';

export interface UsuarioAutenticadoModel {
  login: string;
  nome: string;
  permissoes: PermissaoUsuarioEnum[];
}
