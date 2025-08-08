package fronteira;

import entidades.Espaco;
import entidades.EstacaoTrabalho;
import entidades.SalaPrivada;
import entidades.SalaReuniao;
import entidades.Auditorio;
import controle.AdministradorSistema;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


public class MenuEspacos
{
  private Scanner scanner;
  private AdministradorSistema administrador;

  public MenuEspacos(Scanner scanner, AdministradorSistema administrador)
  {
    this.scanner = scanner;
    this.administrador = administrador;
  }

  public void exibir()
  {
    boolean continuar = true;
    while (continuar)
    {
      limparTela();
      System.out.println("»»» KAFFEECOWORKHUB - GERENCIAR ESPAÇOS «««");
      System.out.println("1. Cadastrar Espaço");
      System.out.println("2. Listar Espaços");
      System.out.println("3. Buscar Espaço");
      System.out.println("4. Verificar Disponibilidade");
      System.out.println("0. Voltar");
      System.out.print("Escolha uma opção: ");
      
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao)
      {
        case 1:
          cadastrarEspaco();
          break;
        case 2:
          listarEspacos();
          break;
        case 3:
          buscarEspaco();
          break;
        case 4:
          verificarDisponibilidade();
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

  private void cadastrarEspaco()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - CADASTRAR ESPAÇO «««");
    System.out.println("1. Estação de Trabalho");
    System.out.println("2. Sala Privada");
    System.out.println("3. Sala de Reunião");
    System.out.println("4. Auditório");
    System.out.print("Tipo de espaço: ");
    
    int tipo = scanner.nextInt();
    scanner.nextLine();
    
    System.out.print("ID: ");
    String id = scanner.nextLine();
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    System.out.print("Valor por hora: ");
    double valorHora = scanner.nextDouble();
    scanner.nextLine();

    String tipoEspaco = "";
    switch (tipo)
    {
      case 1:
        tipoEspaco = "EstacaoTrabalho";
        break;
      case 2:
        tipoEspaco = "SalaPrivada";
        break;
      case 3:
        tipoEspaco = "SalaReuniao";
        break;
      case 4:
        tipoEspaco = "Auditorio";
        break;
      default:
        System.out.println("Tipo inválido!");
        pausar();
        return;
    }

    administrador.criarEspaco(tipoEspaco, id, nome, valorHora);
    System.out.println("Espaço cadastrado com sucesso!");
    pausar();
  }

  private void listarEspacos()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - LISTA DE ESPAÇOS «««");
    List<Espaco> espacos = administrador.getRepositorioEspacos().listarTodos();
    if (espacos.isEmpty())
    {
      System.out.println("Nenhum espaço cadastrado.");
    }
    else
    {
      for (int i = 0; i < espacos.size(); i++)
      {
        Espaco espaco = espacos.get(i);
        System.out.println((i + 1) + ". " + espaco.getTipo() + " - " + espaco.getNome());
        System.out.println("   ID: " + espaco.getId());
        System.out.println("   Valor/hora: R$ " + espaco.getValorHora());
        System.out.println("   Capacidade: " + espaco.getCapacidade() + " pessoas");
        System.out.println("   Status: " + (espaco.isDisponivel() ? "Disponível" : "Ocupado"));
        System.out.println();
      }
    }
    pausar();
  }

  private void buscarEspaco()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - BUSCAR ESPAÇO «««");
    System.out.print("Digite o ID: ");
    String id = scanner.nextLine();

    Espaco encontrado = administrador.getRepositorioEspacos().buscar(id);

    if (encontrado != null)
    {
      System.out.println("\nEspaço encontrado:");
      System.out.println(encontrado.getDescricaoCompleta());
    }
    else
    {
      System.out.println("Espaço não encontrado!");
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

  private void verificarDisponibilidade()
  {
    System.out.println("»»» KAFFEECOWORKHUB - VERIFICAR DISPONIBILIDADE «««");
    System.out.println("Exemplo de Formato: 21/12/2024 às 17:14");
    System.out.println();
    
    System.out.print("ID do Espaço: ");
    String idEspaco = scanner.nextLine();
    
    System.out.print("Data: ");
    String dataStr = scanner.nextLine();
    
    System.out.print("Hora: ");
    String horaStr = scanner.nextLine();
    
    try
    {
      String[] dateParts = dataStr.split("/");
      LocalDate data = LocalDate.of(Integer.parseInt(dateParts[2]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[0]));
      LocalTime hora = LocalTime.parse(horaStr);
      administrador.verificarDisponibilidade(idEspaco, data, hora);
    }
    catch (Exception e)
    {
      System.out.println("Erro: " + e.getMessage());
    }
    pausar();
  }

  private void pausar()
  {
    System.out.println("Pressione enter para retornar ao menu anterior");
    scanner.nextLine();
  }
}
