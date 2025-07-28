package entidades;

import java.time.LocalDate;

public class Cliente
{
  private String cpf;
  private String nome;
  private String email;
  private String telefone;
  private LocalDate dataCadastro;
  
  public Cliente(String cpf, String nome, String email, String telefone)
  {
    this.cpf = cpf;
    this.nome = nome;
    this.email = email;
    this.telefone = telefone;
    this.dataCadastro = LocalDate.now();
  }
  
  public String getCpf()
  {
    return cpf;
  }
  
  public String getNome()
  {
    return nome;
  }
  
  public String getEmail()
  {
    return email;
  }
  
  public String getTelefone()
  {
    return telefone;
  }
  
  public LocalDate getDataCadastro()
  {
    return dataCadastro;
  }
}
}
