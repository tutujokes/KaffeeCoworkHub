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
import controle.AdministradorSistema;
import excecoes.ClienteJaCadastradoException;
import excecoes.ClienteNaoEncontradoException;
import excecoes.HorarioConflituosoException;
import excecoes.EspacoIndisponivelException;
import excecoes.FalhaPersistenciaException;
import excecoes.ReservaNaoEncontradaException;
import excecoes.ServicoInvalidoException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class MenuReservas
{
  private Scanner scanner;
  private AdministradorSistema administrador;

  public MenuReservas(Scanner scanner)
  {
    this.scanner = scanner;
    this.administrador = new AdministradorSistema();
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
  }

  private void fazerReserva()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - FAZER RESERVA «««");
    System.out.println("Exemplo de Formato: 21/12/2024 às 17:14");
    System.out.println();
    
    System.out.print("CPF do Cliente: ");
    String cpf = scanner.nextLine();
    
    System.out.print("ID do Espaço: ");
    String idEspaco = scanner.nextLine();
    
    System.out.print("Data (DD/MM/AAAA): ");
    String dataStr = scanner.nextLine();
    LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    
    System.out.print("Hora início (HH:MM): ");
    String horaInicioStr = scanner.nextLine();
    LocalTime horaInicio = LocalTime.parse(horaInicioStr);
    
    System.out.print("Hora fim (HH:MM): ");
    String horaFimStr = scanner.nextLine();
    LocalTime horaFim = LocalTime.parse(horaFimStr);
    
    try
    {
      List<ServicoAdicional> servicos = new ArrayList<>();
      administrador.realizarReserva(cpf, idEspaco, data, horaInicio, horaFim, servicos);
      System.out.println("Reserva realizada com sucesso!");
    }
    catch (Exception e)
    {
      System.out.println("Erro: " + e.getMessage());
    }
    pausar();
  }

  private void listarReservas()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - LISTA DE RESERVAS «««");
    List<Reserva> reservas = administrador.getRepositorioReservas().listarTodos();
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

    Reserva encontrada = administrador.getRepositorioReservas().buscar(id);

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

    try
    {
      administrador.adicionarServicoReserva(id, servico);
      System.out.println("Serviço adicionado com sucesso!");
    }
    catch (Exception e)
    {
      System.out.println("Erro: " + e.getMessage());
    }
    pausar();
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
