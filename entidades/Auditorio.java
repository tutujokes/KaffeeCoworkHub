package entidades;


public class Auditorio extends Espaco
{
  private int capacidadeMaxima;
  private boolean possuiSistemaSom;
  
  public Auditorio(String id, String nome, double valorHora, boolean disponivel)
  {
    super(id, nome, valorHora, disponivel);
    this.capacidadeMaxima = 20;
    this.possuiSistemaSom = true;
  }
  
  @Override
  public String getTipo()
  {
    return "Auditório";
  }
  
  @Override
  public String getDescricaoCompleta()
  {
    return getTipo() + " - " + nome + "\n" +
           "Valor por hora: R$ " + valorHora + "\n" +
           "Capacidade: " + capacidadeMaxima + " pessoas\n" +
           "Sistema de som: " + (possuiSistemaSom ? "SIM" : "NÃO") + "\n" +
           "Disponível: " + (disponivel ? "SIM" : "NÃO");
  }
  
  @Override
  public int getCapacidade()
  {
    return capacidadeMaxima;
  }
  
  public boolean isPossuiSistemaSom()
  {
    return possuiSistemaSom;
  }
  
  public void setPossuiSistemaSom(boolean possuiSistemaSom)
  {
    this.possuiSistemaSom = possuiSistemaSom;
  }
}
