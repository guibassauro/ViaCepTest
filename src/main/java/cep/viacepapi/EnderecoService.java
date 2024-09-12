package cep.viacepapi;

import org.springframework.web.client.RestTemplate;

public class EnderecoService {

    public Endereco checaEnderecoViaCep(String cep) {

        char aSerDeletado = '-';

        cep = cep.replace(String.valueOf(aSerDeletado), "");
        
        if(cep.length() != 8){
            throw new IllegalArgumentException("CEP inválido.");
        }

        String url = String.format("https://viacep.com.br/ws/%s/json", cep);

        RestTemplate restTemplate = new RestTemplate();

        Endereco enderecoResponse = restTemplate.getForObject(url, Endereco.class);

        if(enderecoResponse.getCep() == null){
            throw new IllegalArgumentException("CEP inválido.");
        }

        Endereco endereco = new Endereco(
            enderecoResponse.getCep(),
            enderecoResponse.getLogradouro(),
            enderecoResponse.getBairro(),
            enderecoResponse.getLocalidade(),
            enderecoResponse.getUf()
        );

        return endereco;
    }
}
