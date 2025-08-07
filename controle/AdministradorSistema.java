package controle;

import entidades.Cliente;
import entidades.Espaco;
import entidades.EstacaoTrabalho;
import entidades.SalaPrivada;
import entidades.SalaReuniao;
import entidades.Auditorio;
import entidades.Reserva;
import entidades.ServicoAdicional;
import excecoes.ClienteJaCadastradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.HorarioConflituosoException;
import excecoes.EspacoIndisponivelException;
import excecoes.FalhaPersistenciaException;
import excecoes.ReservaNaoEncontradaException;
import excecoes.ServicoInvalidoException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class AdministradorSistema
{
  private RepositorioClientes repositorioClientes;
  private RepositorioEspacos repositorioEspacos;
  private RepositorioReservas repositorioReservas;
  private int contadorReservas;

  public AdministradorSistema()
  {
    this.repositorioClientes = new RepositorioClientes();
    this.repositorioEspacos = new RepositorioEspacos();
    this.repositorioReservas = new RepositorioReservas();
    this.contadorReservas = 1;
    inicializarEspacos();
    
    // Carregar dados se existirem
    try
    {
      carregarDados();
    }
    catch (Exception e)
    {
      // Se não conseguir carregar, continua com dados vazios
      System.out.println("Iniciando com dados vazios.");
    }
  }

  private void inicializarEspacos()
  {
    SalaPrivada queen = new SalaPrivada("SP001", "Queen", 50.0, true);
    SalaPrivada king = new SalaPrivada("SP002", "King", 60.0, true);
    SalaPrivada valete = new SalaPrivada("SP003", "Valete", 45.0, true);

    SalaReuniao executiva = new SalaReuniao("SR001", "Executiva", 80.0, true);
    SalaReuniao presidencial = new SalaReuniao("SR002", "Presidencial", 120.0, true);

    repositorioEspacos.inserir(queen);
    repositorioEspacos.inserir(king);
    repositorioEspacos.inserir(valete);
    repositorioEspacos.inserir(executiva);
    repositorioEspacos.inserir(presidencial);
  }

  public void cadastrarCliente(String cpf, String nome, String email, String telefone) throws ClienteJaCadastradoException
  {
    if (repositorioClientes.buscar(cpf) != null)
    {
      throw new ClienteJaCadastradoException("Cliente com CPF " + cpf + " já está cadastrado!");
    }
    Cliente cliente = new Cliente(cpf, nome, email, telefone);
    repositorioClientes.inserir(cliente);
  }

  public void criarEspaco(String tipo, String id, String nome, double valorHora)
  {
    Espaco espaco = null;
    
    if (tipo.equals("EstacaoTrabalho"))
    {
      espaco = new EstacaoTrabalho(id, nome, valorHora, true);
    }
    else if (tipo.equals("SalaPrivada"))
    {
      espaco = new SalaPrivada(id, nome, valorHora, true);
    }
    else if (tipo.equals("SalaReuniao"))
    {
      espaco = new SalaReuniao(id, nome, valorHora, true);
    }
    else if (tipo.equals("Auditorio"))
    {
      espaco = new Auditorio(id, nome, valorHora, true);
    }
    
    if (espaco != null)
    {
      repositorioEspacos.inserir(espaco);
    }
  }

  public void fazerReserva(String cpfCliente, String idEspaco, LocalDateTime inicio, LocalDateTime fim) throws ClienteNaoEncontradoException, EspacoIndisponivelException, HorarioConflituosoException
  {
    Cliente cliente = repositorioClientes.buscar(cpfCliente);
    if (cliente == null)
    {
      throw new ClienteNaoEncontradoException("Cliente com CPF " + cpfCliente + " não encontrado!");
    }

    Espaco espaco = repositorioEspacos.buscar(idEspaco);
    if (espaco == null)
    {
      throw new EspacoIndisponivelException("Espaço com ID " + idEspaco + " não encontrado!");
    }

    if (!espaco.isDisponivel())
    {
      throw new EspacoIndisponivelException("Espaço " + espaco.getNome() + " não está disponível!");
    }

    LocalDate dataReserva = inicio.toLocalDate();
    LocalTime horaInicio = inicio.toLocalTime();
    LocalTime horaFim = fim.toLocalTime();
    
    if (evitarHorarioConflituoso(idEspaco, dataReserva, horaInicio, horaFim))
    {
      throw new HorarioConflituosoException("Conflito de horário! Já existe uma reserva para o espaço " + espaco.getNome() + " no período " + horaInicio + " às " + horaFim + " na data " + dataReserva);
    }

    int novoId = repositorioReservas.listarTodos().size() + 1;
    Reserva reserva = new Reserva(novoId, cliente, espaco, inicio.toLocalDate(), inicio.toLocalTime(), fim.toLocalTime());
    repositorioReservas.inserir(reserva);
  }

  public void realizarReserva(String cpfCliente, String idEspaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim, List<ServicoAdicional> servicos) throws ClienteNaoEncontradoException, EspacoIndisponivelException, HorarioConflituosoException
  {
    Cliente cliente = repositorioClientes.buscar(cpfCliente);
    if (cliente == null)
    {
      throw new ClienteNaoEncontradoException("Cliente com CPF " + cpfCliente + " não encontrado!");
    }
    
    Espaco espaco = repositorioEspacos.buscar(idEspaco);
    if (espaco == null)
    {
      throw new EspacoIndisponivelException("Espaço com ID " + idEspaco + " não encontrado!");
    }
    
    if (!espaco.isDisponivel())
    {
      throw new EspacoIndisponivelException("Espaço " + espaco.getNome() + " não está disponível!");
    }

    if (evitarHorarioConflituoso(idEspaco, dataReserva, horaInicio, horaFim))
    {
      throw new HorarioConflituosoException("Conflito de horário! Já existe uma reserva para o espaço " + espaco.getNome() + " no período " + horaInicio + " às " + horaFim + " na data " + dataReserva);
    }

    double valorTotal = calcularValorTotal(espaco, horaInicio, horaFim, servicos);
    int id = repositorioReservas.obterProximoId();
    Reserva reserva = new Reserva(id, cliente, espaco, dataReserva, horaInicio, horaFim);
    
    for (ServicoAdicional servico : servicos)
    {
      reserva.adicionarServico(servico);
    }
    
    repositorioReservas.inserir(reserva);
    espaco.setDisponivel(false);
  }

  public void cancelarReserva(String idReserva) throws ReservaNaoEncontradaException
  {
    int id = Integer.parseInt(idReserva);
    Reserva reserva = repositorioReservas.buscar(id);
    if (reserva == null)
    {
      throw new ReservaNaoEncontradaException("Reserva com ID " + id + " não encontrada!");
    }
    reserva.getEspaco().setDisponivel(true);
    repositorioReservas.remover(id);
  }

  public void cancelarReserva(int idReserva) throws ReservaNaoEncontradaException
  {
    Reserva reserva = repositorioReservas.buscar(idReserva);
    if (reserva == null)
    {
      throw new ReservaNaoEncontradaException("Reserva com ID " + idReserva + " não encontrada!");
    }
    reserva.getEspaco().setDisponivel(true);
    repositorioReservas.remover(idReserva);
  }

  public void verificarDisponibilidade(String idEspaco, LocalDate data, LocalTime hora)
  {
    Espaco espaco = repositorioEspacos.buscar(idEspaco);
    if (espaco != null)
    {
      System.out.println("Espaco " + espaco.getNome() + " - Disponivel: " + espaco.isDisponivel());
    }
  }

  public void adicionarServicoReserva(int idReserva, ServicoAdicional servico) throws ReservaNaoEncontradaException, ServicoInvalidoException
  {
    Reserva reserva = repositorioReservas.buscar(idReserva);
    if (reserva == null)
    {
      throw new ReservaNaoEncontradaException("Reserva com ID " + idReserva + " não encontrada!");
    }
    
    if (servico == null)
    {
      throw new ServicoInvalidoException("Serviço inválido!");
    }
    
    reserva.adicionarServico(servico);
  }

  private boolean evitarHorarioConflituoso(String idEspaco, LocalDate dataReserva, LocalTime horaInicio, LocalTime horaFim)
  {
    List<Reserva> reservasExistentes = repositorioReservas.listarTodos();
    
    for (Reserva reserva : reservasExistentes)
    {
      if (reserva.getEspaco().getId().equals(idEspaco) && reserva.getDataReserva().equals(dataReserva))
      {
        LocalTime inicioExistente = reserva.getHoraInicio();
        LocalTime fimExistente = reserva.getHoraFim();
        
        if (horaInicio.isBefore(fimExistente) && horaFim.isAfter(inicioExistente))
        {
          return true;
        }
      }
    }
    
    return false;
  }

  public double calcularValorTotal(Espaco espaco, LocalTime horaInicio, LocalTime horaFim, List<ServicoAdicional> servicos)
  {
    double horasDuracao = horaFim.getHour() - horaInicio.getHour();
    double valorEspaco = espaco.getValorHora() * horasDuracao;
    double valorServicos = 0.0;
    
    for (ServicoAdicional servico : servicos)
    {
      valorServicos += servico.getValorTotal();
    }
    
    return valorEspaco + valorServicos;
  }

  public void gerarRelatorioReservasCliente(String cpfCliente)
  {
    List<Reserva> reservas = repositorioReservas.listarTodos();
    System.out.println("Relatorio de Reservas - Cliente: " + cpfCliente);
    
    for (Reserva reserva : reservas)
    {
      if (reserva.getCliente().getCpf().equals(cpfCliente))
      {
        System.out.println("Reserva ID: " + reserva.getId() + " - Espaco: " + reserva.getEspaco().getNome());
      }
    }
  }

  public void gerarRelatorioUtilizacaoEspacos()
  {
    List<Espaco> espacos = repositorioEspacos.listarTodos();
    List<Reserva> reservas = repositorioReservas.listarTodos();
    System.out.println("Relatorio de Utilizacao de Espacos:");
    
    for (Espaco espaco : espacos)
    {
      int contador = 0;
      for (Reserva reserva : reservas)
      {
        if (reserva.getEspaco().getId().equals(espaco.getId()))
        {
          contador++;
        }
      }
      System.out.println("Espaco: " + espaco.getNome() + " - Reservas: " + contador);
    }
  }

  public void gerarRelatorioFaturamento()
  {
    List<Reserva> reservas = repositorioReservas.listarTodos();
    double totalFaturamento = 0.0;
    System.out.println("Relatorio de Faturamento:");
    
    for (Reserva reserva : reservas)
    {
      totalFaturamento += reserva.getValorTotal();
    }
    
    System.out.println("Total Faturado: R$ " + totalFaturamento);
  }

  public void gerarRelatorioServicosAdicionais()
  {
    List<Reserva> reservas = repositorioReservas.listarTodos();
    Map<String, Integer> contadorServicos = new HashMap<>();
    System.out.println("Relatorio de Servicos Adicionais:");
    
    for (Reserva reserva : reservas)
    {
      for (ServicoAdicional servico : reserva.getServicosAdicionais())
      {
        String descricao = servico.getDescricao();
        contadorServicos.put(descricao, contadorServicos.getOrDefault(descricao, 0) + 1);
      }
    }
    
    for (Map.Entry<String, Integer> entry : contadorServicos.entrySet())
    {
      System.out.println("Servico: " + entry.getKey() + " - Quantidade: " + entry.getValue());
    }
  }

  public void salvarDados() throws FalhaPersistenciaException
  {
    try
    {
      repositorioClientes.salvarArquivo("clientes.dat");
      repositorioEspacos.salvarArquivo("espacos.dat");
      repositorioReservas.salvarArquivo("reservas.dat");
    }
    catch (Exception e)
    {
      throw new FalhaPersistenciaException("Erro ao salvar dados: " + e.getMessage());
    }
  }

  public void carregarDados() throws FalhaPersistenciaException
  {
    try
    {
      repositorioClientes.carregarArquivo("clientes.dat");
      repositorioEspacos.carregarArquivo("espacos.dat");
      repositorioReservas.carregarArquivo("reservas.dat");
    }
    catch (Exception e)
    {
      throw new FalhaPersistenciaException("Erro ao carregar dados: " + e.getMessage());
    }
  }

  public RepositorioClientes getRepositorioClientes()
  {
    return repositorioClientes;
  }

  public RepositorioEspacos getRepositorioEspacos()
  {
    return repositorioEspacos;
  }

  public RepositorioReservas getRepositorioReservas()
  {
    return repositorioReservas;
  }
}
