package entidades;


public abstract class Usuario
{
  protected String login;
  protected String senha;
  protected String nome;
  protected String email;

  public Usuario(String login, String senha, String nome, String email)
  {
    this.login = login;
    this.senha = senha;
    this.nome = nome;
    this.email = email;
  }

  public String getLogin()
  {
    return login;
  }

  public String getSenha()
  {
    return senha;
  }

  public String getNome()
  {
    return nome;
  }

  public String getEmail()
  {
    return email;
  }

  public void setNome(String nome)
  {
    this.nome = nome;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public abstract String getTipoUsuario();
}
