public class EstacaoTrabalho extends Espaco
{
  private int capacidadeMaxima;
  
  public EstacaoTrabalho(String id, String nome, double valorHora, boolean disponivel)
  {
    super(id, nome, valorHora, disponivel);
    this.capacidadeMaxima = 1;
  }
  
  @Override
  public String getTipo()
  {
    return "Estação de Trabalho";
  }
  
  @Override
  public String getDescricaoCompleta()
  {
    return getTipo() + " - " + nome + "\n" +
           "Valor por hora: R$ " + valorHora + "\n" +
           "Para uso individual\n" +
           "Disponível: " + (disponivel ? "SIM" : "NÃO");
  }
  
  @Override
  public int getCapacidade()
  {
    return capacidadeMaxima;
  }
}
