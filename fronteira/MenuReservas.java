package fronteira;

import entidades.Reserva;
import entidades.Cliente;
import entidades.Espaco;
import entidades.EstacaoTrabalho;
import entidades.SalaPrivada;
import entidades.ServicoAdicional;
import entidades.CafePremium;
import entidades.Locker;
import entidades.Estacionamento;
import entidades.RecebimentoCorrespondencia;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


public class MenuReservas
{
  private Scanner scanner;
  private List<Reserva> reservas;
  private List<Cliente> clientes;
  private List<Espaco> espacos;

  public MenuReservas(Scanner scanner)
  {
    this.scanner = scanner;
    this.reservas = new ArrayList<>();
    this.clientes = new ArrayList<>();
    this.espacos = new ArrayList<>();
    carregarDadosTeste();
  }

  public void exibir()
  {
    boolean continuar = true;
    while (continuar)
    {
      limparTela();
      System.out.println("»»» KAFFEECOWORKHUB - GERENCIAR RESERVAS «««");
      System.out.println("1. Fazer Reserva");
      System.out.println("2. Listar Reservas");
      System.out.println("3. Buscar Reserva");
      System.out.println("4. Adicionar Serviço");
      System.out.println("0. Voltar");
      System.out.print("Escolha uma opção: ");
      
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao)
      {
        case 1:
          fazerReserva();
          break;
        case 2:
          listarReservas();
          break;
        case 3:
          buscarReserva();
          break;
        case 4:
          adicionarServico();
          break;
        case 0:
          continuar = false;
          break;
        default:
          System.out.println("Opção inválida!");
          pausar();
      }
    }
  }

  private void fazerReserva()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - FAZER RESERVA «««");
    
    listarClientesSimples();
    System.out.print("Insira o cliente: ");
    int indiceCliente = scanner.nextInt() - 1;
    scanner.nextLine();
    
    if (indiceCliente < 0 || indiceCliente >= clientes.size())
    {
      System.out.println("Cliente inválido!");
      pausar();
      return;
    }
    
    listarEspacosSimples();
    System.out.print("Insira o espaço: ");
    int indiceEspaco = scanner.nextInt() - 1;
    scanner.nextLine();
    
    if (indiceEspaco < 0 || indiceEspaco >= espacos.size())
    {
      System.out.println("Espaço inválido!");
      pausar();
      return;
    }
    System.out.print("Data: ");
    String data = scanner.nextLine();
    System.out.print("Hora início: ");
    String horaInicio = scanner.nextLine();
    System.out.print("Hora final: ");
    String horaFim = scanner.nextLine();

    try
    {
      LocalDate dataReserva = LocalDate.parse(data);
      LocalTime inicio = LocalTime.parse(horaInicio);
      LocalTime fim = LocalTime.parse(horaFim);
      
      int novoId = reservas.size() + 1;
      Reserva reserva = new Reserva(novoId, clientes.get(indiceCliente), espacos.get(indiceEspaco),
                                    dataReserva, inicio, fim);
      
      reservas.add(reserva);
      System.out.println("Reserva criada com sucesso! ID: " + novoId);
    }
    catch (Exception e)
    {
      System.out.println("Erro ao criar reserva: " + e.getMessage());
    }
    pausar();
  }

  private void listarReservas()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - LISTA DE RESERVAS «««");
    if (reservas.isEmpty())
    {
      System.out.println("Nenhuma reserva encontrada.");
    }
    else
    {
      for (Reserva reserva : reservas)
      {
        System.out.println("ID: " + reserva.getId());
        System.out.println("Cliente: " + reserva.getCliente().getNome());
        System.out.println("Espaço: " + reserva.getEspaco().getNome());
        System.out.println("Data: " + reserva.getDataReserva());
        System.out.println("Horário: " + reserva.getHoraInicio() + " às " + reserva.getHoraFim());
        System.out.println("Valor Total: R$ " + reserva.getValorTotal());
        System.out.println("Serviços: " + reserva.getServicosAdicionais().size());
        System.out.println("---");
      }
    }
    pausar();
  }

  private void buscarReserva()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - BUSCAR RESERVA «««");
    System.out.print("Digite o ID da reserva: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Reserva encontrada = null;
    for (Reserva reserva : reservas)
    {
      if (reserva.getId() == id)
      {
        encontrada = reserva;
        break;
      }
    }

    if (encontrada != null)
    {
      System.out.println("\nReserva encontrada:");
      System.out.println("ID: " + encontrada.getId());
      System.out.println("Cliente: " + encontrada.getCliente().getNome());
      System.out.println("Espaço: " + encontrada.getEspaco().getNome());
      System.out.println("Data: " + encontrada.getDataReserva());
      System.out.println("Horário: " + encontrada.getHoraInicio() + " às " + encontrada.getHoraFim());
      System.out.println("Valor Total: R$ " + encontrada.getValorTotal());
    }
    else
    {
      System.out.println("Reserva não encontrada!");
    }
    pausar();
  }

  private void adicionarServico()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - ADICIONAR SERVIÇO «««");
    System.out.print("ID da reserva: ");
    int id = scanner.nextInt();
    scanner.nextLine();

    Reserva reserva = null;
    for (Reserva r : reservas)
    {
      if (r.getId() == id)
      {
        reserva = r;
        break;
      }
    }

    if (reserva == null)
    {
      System.out.println("Reserva não encontrada!");
      pausar();
      return;
    }

    System.out.println("Serviços disponíveis:");
    System.out.println("1. Café Premium");
    System.out.println("2. Locker");
    System.out.println("3. Estacionamento");
    System.out.println("4. Recebimento de Correspondência");
    System.out.print("Insira o serviço: ");
    
    int opcaoServico = scanner.nextInt();
    scanner.nextLine();
    
    System.out.print("Quantidade: ");
    int quantidade = scanner.nextInt();
    scanner.nextLine();

    ServicoAdicional servico = null;
    switch (opcaoServico)
    {
      case 1:
        servico = new CafePremium(quantidade);
        break;
      case 2:
        servico = new Locker(quantidade);
        break;
      case 3:
        servico = new Estacionamento(quantidade);
        break;
      case 4:
        servico = new RecebimentoCorrespondencia(quantidade);
        break;
      default:
        System.out.println("Serviço inválido!");
        pausar();
        return;
    }

    reserva.adicionarServico(servico);
    System.out.println("Serviço adicionado com sucesso!");
    pausar();
  }

  private void listarClientesSimples()
  {
    System.out.println("Clientes disponíveis:");
    for (int i = 0; i < clientes.size(); i++)
    {
      System.out.println((i + 1) + ". " + clientes.get(i).getNome());
    }
  }

  private void listarEspacosSimples()
  {
    System.out.println("Espaços disponíveis:");
    for (int i = 0; i < espacos.size(); i++)
    {
      Espaco espaco = espacos.get(i);
      System.out.println((i + 1) + ". " + espaco.getTipo() + " - " + espaco.getNome());
    }
  }

  private void carregarDadosTeste()
  {
    clientes.add(new Cliente("12345678901", "João Silva", "joao@email.com", "11999887766"));
    clientes.add(new Cliente("98765432109", "Maria Santos", "maria@email.com", "11888776655"));
    
    espacos.add(new EstacaoTrabalho("E001", "Estação Alpha", 15.0, true));
    espacos.add(new SalaPrivada("S001", "Sala Beta", 45.0, true));
  }

  private void limparTela()
  {
    try
    {
      if (System.getProperty("os.name").contains("Windows"))
      {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      }
      else
      {
        new ProcessBuilder("clear").inheritIO().start().waitFor();
      }
    }
    catch (Exception e)
    {
      for (int i = 0; i < 50; i++)
      {
        System.out.println();
      }
    }
  }

  private void pausar()
  {
    System.out.println("Pressione Enter para continuar...");
    scanner.nextLine();
  }
}
