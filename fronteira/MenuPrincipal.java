package fronteira;

import fronteira.MenuRelatorios;
import controle.AdministradorSistema;
import entidades.Administrador;
import java.util.Scanner;


public class MenuPrincipal
{
  private Scanner scanner;
  private Administrador adminLogado;
  private AdministradorSistema administradorSistema;

  public MenuPrincipal()
  {
    this.scanner = new Scanner(System.in);
    this.administradorSistema = new AdministradorSistema();
  }

  public void exibir()
  {
    if (realizarLogin())
    {
      exibirMenuPrincipal();
    }
    scanner.close();
  }

  private boolean realizarLogin()
  {
    System.out.println("»»» BEM-VINDO AO KAFFEECOWORKHUB «««");
    System.out.println("»»» LOGIN KAFFEECOWORKHUB «««");
    System.out.println("Faça login para continuar\n");
    
    System.out.print("Login: ");
    String login = scanner.nextLine();
    System.out.print("Senha: ");
    String senha = scanner.nextLine();

    if (login.equals("admin") && senha.equals("1234"))
    {
      adminLogado = new Administrador("admin", "1234", "Administrador Sistema", "admin@workhub.com");
      System.out.println("\nLogin realizado com sucesso!");
      System.out.println("Bem-vindo, " + adminLogado.getNome());
      return true;
    }
    else
    {
      System.out.println("Credenciais inválidas!");
      return false;
    }
  }

  private void exibirMenuPrincipal()
  {
    boolean continuar = true;
    while (continuar)
    {
      limparTela();
      System.out.println("»»» KAFFEECOWORKHUB - MENU PRINCIPAL «««");
      System.out.println("1. Gerenciar Clientes");
      System.out.println("2. Gerenciar Espaços");
      System.out.println("3. Gerenciar Reservas");
      System.out.println("4. Relatórios");
      System.out.println("0. Sair");
      System.out.print("Escolha uma opção: ");
      
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao)
      {
        case 1:
          new MenuClientes(scanner).exibir();
          break;
        case 2:
          new MenuEspacos(scanner).exibir();
          break;
        case 3:
          new MenuReservas(scanner).exibir();
          break;
        case 4:
          new MenuRelatorios(scanner, administradorSistema).exibir();
          break;
        case 0:
          System.out.println("Saindo do sistema...");
          continuar = false;
          break;
        default:
          System.out.println("Opção inválida!");
          pausar();
      }
    }
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
