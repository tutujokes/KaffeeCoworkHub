package controle;

import entidades.Usuario;
import entidades.Administrador;
import java.util.HashMap;
import java.util.Map;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class GerenciadorAutenticacao
{
  private Map<String, Usuario> usuarios;
  private Usuario usuarioLogado;

  public GerenciadorAutenticacao()
  {
    this.usuarios = new HashMap<>();
    carregarUsuariosDoArquivo();
  }

  private void carregarUsuariosDoArquivo()
  {
    try (DataInputStream dis = new DataInputStream(new FileInputStream("usuarios.dat")))
    {
      while (dis.available() > 0)
      {
        String login = dis.readUTF();
        String senha = dis.readUTF();
        String nome = dis.readUTF();
        String email = dis.readUTF();
        
        Administrador admin = new Administrador(login, senha, nome, email);
        usuarios.put(login, admin);
      }
      System.out.println("Usuários carregados do arquivo binário.");
    }
    catch (FileNotFoundException e)
    {
      System.out.println("Arquivo usuarios.dat não encontrado. Criando usuários padrão...");
      criarArquivoUsuarios();
      carregarUsuariosDoArquivo();
    }
    catch (IOException e)
    {
      System.err.println("Erro ao carregar usuários: " + e.getMessage());
      inicializarUsuariosPadrao();
    }
  }

  private void criarArquivoUsuarios()
  {
    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("usuarios.dat")))
    {
      dos.writeUTF("saulo");
      dos.writeUTF("123");
      dos.writeUTF("Saulo Ferro");
      dos.writeUTF("saulo@coworking.com");
      
      dos.writeUTF("artu");
      dos.writeUTF("234");
      dos.writeUTF("Arthur");
      dos.writeUTF("artu@coworking.com");
      
      System.out.println("Arquivo usuarios.dat criado automaticamente.");
    }
    catch (IOException e)
    {
      System.err.println("Erro ao criar arquivo: " + e.getMessage());
    }
  }

  private void inicializarUsuariosPadrao()
  {
    Administrador sauloFerro = new Administrador("saulo", "123", "Saulo Ferro", "saulo@coworking.com");
    usuarios.put("saulo", sauloFerro);
    
    Administrador arthur = new Administrador("artu", "234", "Arthur", "artu@coworking.com");
    usuarios.put("artu", arthur);
  }

  public boolean autenticar(String login, String senha)
  {
    Usuario usuario = usuarios.get(login);
    if (usuario != null && usuario.getSenha().equals(senha))
    {
      usuarioLogado = usuario;
      return true;
    }
    return false;
  }

  public void logout()
  {
    usuarioLogado = null;
  }

  public Usuario getUsuarioLogado()
  {
    return usuarioLogado;
  }

  public boolean isAdministrador()
  {
    return usuarioLogado != null && usuarioLogado instanceof Administrador;
  }

  public void adicionarUsuario(Usuario usuario)
  {
    usuarios.put(usuario.getLogin(), usuario);
  }
}
