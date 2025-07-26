package entidades;

public class SalaReuniao extends Espaco
{
  private int capacidadeMaxima;
  private boolean possuiTeleconferencia;
  
  public SalaReuniao(String id, String nome, double valorHora, boolean disponivel)
  {
    super(id, nome, valorHora, disponivel);
    this.capacidadeMaxima = 8;
    this.possuiTeleconferencia = true;
  }
  
  @Override
  public String getTipo()
  {
    return "Sala de Reunião";
  }
  
  @Override
  public String getDescricaoCompleta()
  {
    return getTipo() + " - " + nome + "\n" +
           "Valor por hora: R$ " + valorHora + "\n" +
           "Capacidade: " + capacidadeMaxima + " pessoas\n" +
           "Sistema de teleconferência: " + (possuiTeleconferencia ? "SIM" : "NÃO") + "\n" +
           "Disponível: " + (disponivel ? "SIM" : "NÃO");
  }
  
  @Override
  public int getCapacidade()
  {
    return capacidadeMaxima;
  }
  
  public boolean isPossuiTeleconferencia()
  {
    return possuiTeleconferencia;
  }
  
  public void setPossuiTeleconferencia(boolean possuiTeleconferencia)
  {
    this.possuiTeleconferencia = possuiTeleconferencia;
  }
}
