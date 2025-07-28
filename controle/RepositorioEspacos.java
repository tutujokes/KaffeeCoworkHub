package controle;

import entidades.Espaco;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class RepositorioEspacos
{
  private Map<String, Espaco> espacos;

  public RepositorioEspacos()
  {
    this.espacos = new HashMap<>();
  }

  public void inserir(Espaco espaco)
  {
    espacos.put(espaco.getId(), espaco);
  }

  public void remover(String id)
  {
    espacos.remove(id);
  }

  public Espaco buscar(String id)
  {
    return espacos.get(id);
  }

  public List<Espaco> listarTodos()
  {
    return new ArrayList<>(espacos.values());
  }

  public void salvarArquivo(String caminho) throws Exception
  {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho)))
    {
      out.writeObject(espacos);
    }
  }

  public void carregarArquivo(String caminho) throws Exception
  {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho)))
    {
      this.espacos = (Map<String, Espaco>) in.readObject();
    }
  }
}
