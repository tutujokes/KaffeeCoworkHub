package fronteira;

import controle.AdministradorSistema;
import java.util.Scanner;

public class MenuRelatorios
{
  private Scanner scanner;
  private AdministradorSistema administrador;

  public MenuRelatorios(Scanner scanner, AdministradorSistema administrador)
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
      System.out.println("»»» KAFFEECOWORKHUB - RELATÓRIOS «««");
      System.out.println("1. Relatório de Reservas por Cliente");
      System.out.println("2. Relatório de Utilização de Espaços");
      System.out.println("3. Relatório de Faturamento");
      System.out.println("4. Relatório de Serviços Adicionais");
      System.out.println("0. Voltar");
      System.out.print("Escolha uma opção: ");
      
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao)
      {
        case 1:
          relatorioReservasCliente();
          break;
        case 2:
          administrador.gerarRelatorioUtilizacaoEspacos();
          pausar();
          break;
        case 3:
          administrador.gerarRelatorioFaturamento();
          pausar();
          break;
        case 4:
          administrador.gerarRelatorioServicosAdicionais();
          pausar();
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

  private void relatorioReservasCliente()
  {
    System.out.print("Digite o CPF do cliente: ");
    String cpf = scanner.nextLine();
    administrador.gerarRelatorioReservasCliente(cpf);
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
    System.out.println("Pressione enter para retornar ao menu anterior");
    scanner.nextLine();
  }
}
