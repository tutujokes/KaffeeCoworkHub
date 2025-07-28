package fronteira;

import entidades.Espaco;
import entidades.EstacaoTrabalho;
import entidades.SalaPrivada;
import entidades.SalaReuniao;
import entidades.Auditorio;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class MenuEspacos
{
  private Scanner scanner;
  private List<Espaco> espacos;

  public MenuEspacos(Scanner scanner)
  {
    this.scanner = scanner;
    this.espacos = new ArrayList<>();
    carregarEspacosTeste();
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

    Espaco espaco = null;
    switch (tipo)
    {
      case 1:
        espaco = new EstacaoTrabalho(id, nome, valorHora, true);
        break;
      case 2:
        espaco = new SalaPrivada(id, nome, valorHora, true);
        break;
      case 3:
        espaco = new SalaReuniao(id, nome, valorHora, true);
        break;
      case 4:
        espaco = new Auditorio(id, nome, valorHora, true);
        break;
      default:
        System.out.println("Tipo inválido!");
        pausar();
        return;
    }

    espacos.add(espaco);
    System.out.println("Espaço cadastrado com sucesso!");
    pausar();
  }

  private void listarEspacos()
  {
    limparTela();
    System.out.println("»»» KAFFEECOWORKHUB - LISTA DE ESPAÇOS «««");
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

    Espaco encontrado = null;
    for (Espaco espaco : espacos)
    {
      if (espaco.getId().equals(id))
      {
        encontrado = espaco;
        break;
      }
    }

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

  private void carregarEspacosTeste()
  {
    espacos.add(new EstacaoTrabalho("E001", "Estação Alpha", 15.0, true));
    espacos.add(new SalaPrivada("S001", "Sala Beta", 45.0, true));
    espacos.add(new SalaReuniao("R001", "Sala Gamma", 80.0, true));
    espacos.add(new Auditorio("A001", "Auditório Delta", 150.0, true));
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
