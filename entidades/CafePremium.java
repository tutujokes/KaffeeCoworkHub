package entidades;

public class CafePremium implements ServicoAdicional
{
  private int quantidade;
  private double precoPorUnidade;
  
  public CafePremium(int quantidade)
  {
    this.quantidade = quantidade;
    this.precoPorUnidade = 8.50;
  }
  
  @Override
  public String getDescricao()
  {
    return "Café Premium - " + quantidade + " unidade(s)";
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
