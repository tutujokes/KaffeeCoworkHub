package controle;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class GeradorArquivoUsuarios
{
  public static void main(String[] args)
  {
    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("usuarios.dat")))
    {
      dos.writeUTF("admin");
      dos.writeUTF("1234");
      dos.writeUTF("Administrador Sistema");
      dos.writeUTF("admin@workhub.com");

      dos.writeUTF("saulo");
      dos.writeUTF("123");
      dos.writeUTF("Saulo Ferro");
      dos.writeUTF("saulo@workhub.com");

      dos.writeUTF("arthur");
      dos.writeUTF("234");
      dos.writeUTF("Arthur");
      dos.writeUTF("arthur@workhub.com");

      System.out.println("Arquivo usuarios.dat criado com sucesso!");
    }
    catch (IOException e)
    {
      System.err.println("Erro ao criar arquivo: " + e.getMessage());
    }
  }
}
