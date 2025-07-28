package fronteira;

import entidades.Cliente;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class MenuClientes
{
  private Scanner scanner;
  private List<Cliente> clientes;

  public MenuClientes(Scanner scanner)
  {
    this.scanner = scanner;
    this.clientes = new ArrayList<>();
    carregarClientesTeste();
  }

  public void exibir()
  {
    boolean continuar = true;
    while (continuar)
    {
      limparTela();
      System.out.println("»»» KAFFEECOWORKHUB - GERENCIAR CLIENTES «««");
      System.out.println("1. Cadastrar Cliente");
      System.out.println("2. Listar Clientes");
      System.out.println("3. Buscar Cliente");
      System.out.println("0. Voltar");
      System.out.print("Escolha uma opção: ");
      
      int opcao = scanner.nextInt();
      scanner.nextLine();

      switch (opcao)
      {
        case 1:
          cadastrarCliente();
          break;
        case 2:
          listarClientes();
          break;
        case 3:
          buscarCliente();
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

  private void cadastrarCliente()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - CADASTRAR CLIENTE «««");
    System.out.print("CPF: ");
    String cpf = scanner.nextLine();
    System.out.print("Nome: ");
    String nome = scanner.nextLine();
    System.out.print("Email: ");
    String email = scanner.nextLine();
    System.out.print("Numero de telefone: ");
    String telefone = scanner.nextLine();

    Cliente cliente = new Cliente(cpf, nome, email, telefone);
    clientes.add(cliente);
    
    System.out.println("Cliente cadastrado com sucesso!");
    pausar();
  }

  private void listarClientes()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - LISTA DE CLIENTES «««");
    if (clientes.isEmpty())
    {
      System.out.println("Nenhum cliente cadastrado.");
    }
    else
    {
      for (int i = 0; i < clientes.size(); i++)
      {
        Cliente cliente = clientes.get(i);
        System.out.println((i + 1) + ". " + cliente.getNome());
        System.out.println("   CPF: " + cliente.getCpf());
        System.out.println("   Email: " + cliente.getEmail());
        System.out.println("   Telefone: " + cliente.getTelefone());
        System.out.println();
      }
    }
    pausar();
  }

  private void buscarCliente()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - BUSCAR CLIENTE «««");
    System.out.print("Digite o CPF: ");
    String cpf = scanner.nextLine();

    Cliente encontrado = null;
    for (Cliente cliente : clientes)
    {
      if (cliente.getCpf().equals(cpf))
      {
        encontrado = cliente;
        break;
      }
    }

    if (encontrado != null)
    {
      System.out.println("\nCliente encontrado:");
      System.out.println("Nome: " + encontrado.getNome());
      System.out.println("CPF: " + encontrado.getCpf());
      System.out.println("Email: " + encontrado.getEmail());
      System.out.println("Telefone: " + encontrado.getTelefone());
    }
    else
    {
      System.out.println("Cliente não encontrado!");
    }
    pausar();
  }

  private void carregarClientesTeste()
  {
    clientes.add(new Cliente("12345678901", "João Silva", "joao@email.com", "11999887766"));
    clientes.add(new Cliente("98765432109", "Maria Santos", "maria@email.com", "11888776655"));
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
