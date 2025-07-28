package entidades;

public class Estacionamento implements ServicoAdicional
{
  private int duracaoHoras;
  private double precoPorHora;
  
  public Estacionamento(int duracaoHoras)
  {
    this.duracaoHoras = duracaoHoras;
    this.precoPorHora = 3.00;
  }
  
  @Override
  public String getDescricao()
  {
    return "Estacionamento - " + duracaoHoras + " hora(s)";
  }
  
  @Override
  public double getValorTotal()
  {
    return duracaoHoras * precoPorHora;
  }
  
  public int getDuracaoHoras()
  {
    return duracaoHoras;
  }
  
  public void setDuracaoHoras(int duracaoHoras)
  {
    this.duracaoHoras = duracaoHoras;
  }
}
