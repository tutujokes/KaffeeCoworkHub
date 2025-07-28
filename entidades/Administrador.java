package entidades;


public class Administrador extends Usuario
{
  public Administrador(String login, String senha, String nome, String email)
  {
    super(login, senha, nome, email);
  }

  @Override
  public String getTipoUsuario()
  {
    return "ADMINISTRADOR";
  }
}
