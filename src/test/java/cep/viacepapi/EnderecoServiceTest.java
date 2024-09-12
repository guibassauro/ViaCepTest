package cep.viacepapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class EnderecoServiceTest {

    @Test
    public void testaCepValido(){
        EnderecoService service = new EnderecoService();
        
        Endereco endereco = service.checaEnderecoViaCep("90040-480");

        Assert.assertEquals(endereco.getCep(), "90040-480");
        Assert.assertEquals(endereco.getLogradouro(), "Parque Farroupilha");
        Assert.assertEquals(endereco.getBairro(), "Farroupilha");
        Assert.assertEquals(endereco.getLocalidade(), "Porto Alegre");
        Assert.assertEquals(endereco.getUf(), "RS");
    }

    @Test
    public void testaCepInvalido(){
        EnderecoService service = new EnderecoService();
        
        try {
            service.checaEnderecoViaCep("96822252");
            fail("Não deu exception");

        } catch(Exception e){
            assertEquals("CEP inválido.", e.getMessage());

        }

        
    }
}
