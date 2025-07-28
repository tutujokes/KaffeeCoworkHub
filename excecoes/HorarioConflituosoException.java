package excecoes;


public class HorarioConflituosoException extends Exception
{
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_RESET = "\u001B[0m";
  
  public HorarioConflituosoException(String mensagem)
  {
    super(ANSI_RED + mensagem + ANSI_RESET);
  }
}
