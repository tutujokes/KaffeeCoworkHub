package controle;

import entidades.Cliente;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class RepositorioClientes
{
  private Map<String, Cliente> clientes;

  public RepositorioClientes()
  {
    this.clientes = new HashMap<>();
  }

  public void inserir(Cliente cliente)
  {
    clientes.put(cliente.getCpf(), cliente);
  }

  public void remover(String cpf)
  {
    clientes.remove(cpf);
  }

  public Cliente buscar(String cpf)
  {
    return clientes.get(cpf);
  }

  public List<Cliente> listarTodos()
  {
    return new ArrayList<>(clientes.values());
  }

  public void salvarArquivo(String caminho) throws Exception
  {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho)))
    {
      out.writeObject(clientes);
    }
  }

  public void carregarArquivo(String caminho) throws Exception
  {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho)))
    {
      this.clientes = (Map<String, Cliente>) in.readObject();
    }
  }
}
