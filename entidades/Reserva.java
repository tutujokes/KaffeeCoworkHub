package entidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Reserva
{
  private int id;
  private Cliente cliente;
  private Espaco espaco;
  private LocalDate dataReserva;
  private LocalTime horaInicio;
  private LocalTime horaFim;
  private double valorTotal;
  private List<ServicoAdicional> servicosAdicionais;
  
  public Reserva(int id, Cliente cliente, Espaco espaco, LocalDate dataReserva, 
                 LocalTime horaInicio, LocalTime horaFim)
  {
    this.id = id;
    this.cliente = cliente;
    this.espaco = espaco;
    this.dataReserva = dataReserva;
    this.horaInicio = horaInicio;
    this.horaFim = horaFim;
    this.servicosAdicionais = new ArrayList<>();
    calcularValorTotal();
  }
  
  public void adicionarServico(ServicoAdicional servico)
  {
    servicosAdicionais.add(servico);
    calcularValorTotal();
  }
  
  private void calcularValorTotal()
  {
    int duracaoHoras = horaFim.getHour() - horaInicio.getHour();
    valorTotal = espaco.getValorHora() * duracaoHoras;
    
    for (ServicoAdicional servico : servicosAdicionais)
    {
      valorTotal += servico.getValorTotal();
    }
  }
  
  public int getId()
  {
    return id;
  }
  
  public Cliente getCliente()
  {
    return cliente;
  }
  
  public Espaco getEspaco()
  {
    return espaco;
  }
  
  public LocalDate getDataReserva()
  {
    return dataReserva;
  }
  
  public LocalTime getHoraInicio()
  {
    return horaInicio;
  }
  
  public LocalTime getHoraFim()
  {
    return horaFim;
  }
  
  public double getValorTotal()
  {
    return valorTotal;
  }
  
  public List<ServicoAdicional> getServicosAdicionais()
  {
    return servicosAdicionais;
  }
}
