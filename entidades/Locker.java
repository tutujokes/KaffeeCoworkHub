package entidades;

public class Locker implements ServicoAdicional
{
  private int quantidade;
  private double precoPorUnidade;
  
  public Locker(int quantidade)
  {
    this.quantidade = quantidade;
    this.precoPorUnidade = 5.00;
  }
  
  @Override
  public String getDescricao()
  {
    return "Locker - " + quantidade + " unidade(s)";
  }
  
  @Override
  public double getValorTotal()
  {
    return quantidade * precoPorUnidade;
  }
  
  public int getQuantidade()
  {
    return quantidade;
  }
  
  public void setQuantidade(int quantidade)
  {
    this.quantidade = quantidade;
  }
}
