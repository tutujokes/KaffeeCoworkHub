package excecoes;

public class ReservaNaoEncontradaException extends Exception
{
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_RESET = "\u001B[0m";
  
  public ReservaNaoEncontradaException(String mensagem)
  {
    super(ANSI_RED + mensagem + ANSI_RESET);
  }
}
