public abstract class Espaco
{
  protected String id;
  protected String nome;
  protected double valorHora;
  protected boolean disponivel;
  
  public Espaco(String id, String nome, double valorHora, boolean disponivel)
  {
    this.id = id;
    this.nome = nome;
    this.valorHora = valorHora;
    this.disponivel = disponivel;
  }
  
  public abstract String getTipo();
  public abstract String getDescricaoCompleta();
  public abstract int getCapacidade();
  
  public String getId()
  {
    return id;
  }
  
  public String getNome()
  {
    return nome;
  }
  
  public double getValorHora()
  {
    return valorHora;
  }
  
  public boolean isDisponivel()
  {
    return disponivel;
  }
  
  public void setDisponivel(boolean disponivel)
  {
    this.disponivel = disponivel;
  }
}
