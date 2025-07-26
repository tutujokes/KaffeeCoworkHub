package entidades;

public class SalaPrivada extends Espaco
{
  private int capacidadeMaxima;
  private boolean possuiProjetor;
  
  public SalaPrivada(String id, String nome, double valorHora, boolean disponivel)
  {
    super(id, nome, valorHora, disponivel);
    this.capacidadeMaxima = 4;
    this.possuiProjetor = true;
  }
  
  @Override
  public String getTipo()
  {
    return "Sala Privada";
  }
  
  @Override
  public String getDescricaoCompleta()
  {
    return getTipo() + " - " + nome + "\n" +
           "Valor por hora: R$ " + valorHora + "\n" +
           "Capacidade: " + capacidadeMaxima + " pessoas\n" +
           "Projetor: " + (possuiProjetor ? "SIM" : "NÃO") + "\n" +
           "Disponível: " + (disponivel ? "SIM" : "NÃO");
  }
  
  @Override
  public int getCapacidade()
  {
    return capacidadeMaxima;
  }
  
  public boolean isPossuiProjetor()
  {
    return possuiProjetor;
  }
  
  public void setPossuiProjetor(boolean possuiProjetor)
  {
    this.possuiProjetor = possuiProjetor;
  }
}
