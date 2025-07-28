package entidades;

public class RecebimentoCorrespondencia implements ServicoAdicional
{
  private int quantidade;
  private double precoPorUnidade;
  
  public RecebimentoCorrespondencia(int quantidade)
  {
    this.quantidade = quantidade;
    this.precoPorUnidade = 2.50;
  }
  
  @Override
  public String getDescricao()
  {
    return "Recebimento de Correspondência - " + quantidade + " item(s)";
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
