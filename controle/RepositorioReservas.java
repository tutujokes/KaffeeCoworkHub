package controle;

import entidades.Reserva;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class RepositorioReservas
{
  private Map<Integer, Reserva> reservas;
  private int proximoId;

  public RepositorioReservas()
  {
    this.reservas = new HashMap<>();
    this.proximoId = 1;
  }

  public void inserir(Reserva reserva)
  {
    reservas.put(reserva.getId(), reserva);
  }

  public void remover(Integer id)
  {
    reservas.remove(id);
  }

  public Reserva buscar(Integer id)
  {
    return reservas.get(id);
  }

  public List<Reserva> listarTodos()
  {
    return new ArrayList<>(reservas.values());
  }

  public int obterProximoId()
  {
    return proximoId++;
  }

  public void salvarArquivo(String caminho) throws Exception
  {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho)))
    {
      out.writeObject(reservas);
      out.writeInt(proximoId);
    }
  }

  public void carregarArquivo(String caminho) throws Exception
  {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho)))
    {
      this.reservas = (Map<Integer, Reserva>) in.readObject();
      this.proximoId = in.readInt();
    }
  }
}
