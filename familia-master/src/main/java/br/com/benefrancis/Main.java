package br.com.benefrancis;

import br.com.benefrancis.model.PessoaFisica;
import br.com.benefrancis.model.PessoaJuridica;
import br.com.benefrancis.model.Sexo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
        EntityManager manager = factory.createEntityManager();


        var bruno = new PessoaFisica();
        bruno.setCPF(geraCpf())
                .setSexo(Sexo.MASCULINO)
                .setNome("Bruno Sudré do Nascimento")
                .setNascimento(LocalDate.of(2000, 5, 15));

        var esposa = new PessoaFisica();
        esposa.setSexo(Sexo.FEMININO)
                .setCPF(geraCpf())
                .setNascimento(LocalDate.of(1975, 5, 28))
                .setNome("Laine");

        esposa.addFilho(bruno);

        var bene = new PessoaFisica();

        bene.setCPF(geraCpf())
                .setSexo(Sexo.MASCULINO)
                .setNome("Benefrancis do Nascimento")
                .setNascimento(LocalDate.of(1977, 03, 8));
        bene.addFilho(bruno);


        var holding = new PessoaJuridica();
        holding.setCNPJ(geraCNPJ())
                .setNascimento(LocalDate.now().minusYears(new Random().nextLong(99)))
                .setNome("Benezinho Holding");
        holding.addSocio(bene).addSocio(bruno).addSocio(esposa);


        // Metodo para salvar aqui:
        manager.getTransaction().begin();
        manager.persist(holding);
        manager.getTransaction().commit();
        //Métodos para consultar aqui:

        System.out.println(holding);
    }

    private static String geraCpf() {
        var sorteio = new Random();
        var digito = sorteio.nextLong(99);
        var numero = sorteio.nextLong(999999999);
        var cpf = String.valueOf(numero) + "-" + String.valueOf(digito);
        return cpf;
    }

    private static String geraCNPJ() {
        var sorteio = new Random();
        var digito = sorteio.nextLong(99);
        var numero = sorteio.nextLong(999999999);
        var cpf = String.valueOf(numero) + "/0001-" + String.valueOf(digito);
        return cpf;
    }


}