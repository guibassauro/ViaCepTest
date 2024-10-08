package cep.viacepapi;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ViaCepTest {

    @Test
    @DisplayName("Testa a saída quando requisitamos um CEP válido")
    public void dadoCepValidoQuandoFizermosUmaRequisicaoEntaoDeveRetornar200(){
        when().
            get("https://viacep.com.br/ws/90040480/json").
                then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    body("cep", equalTo("90040-480")).
                    body("localidade", equalTo("Porto Alegre"));
    }

    @Test
    @DisplayName("Testa a saída com CEP inválido")
    public void testeDadoCepInvalidoQuandoFizermosUmaRequisicaoDeveRetornar400(){
        when()
            .get("https://viacep.com.br/ws/900404800/json")
                .then()
                    .statusCode(400);
    }

    @Test
    @DisplayName("Testa saída com CEP inexistente")
    public void testeDadoCepInexistenteQuandoFizermosUmaRequisicaoDeveRetornarErro(){
        when()
            .get("https://viacep.com.br/ws/12345678/json")
                .then()
                    .statusCode(200)
                    .body("erro", equalTo("true"));
    }

    @Test
    @DisplayName("Testa colocar caracteres no CEP")
    public void testeDadoCepComLetrasQuandoFizermosUmaRequisicaoDeveRetornarErro(){
        when()
            .get("https://viacep.com.br/ws/9004c48a/json")
                .then()
                    .statusCode(400);
    }

    @Test
    @DisplayName("Testa se o request de diferentes regiões está sendo feito corretamente")
    public void testeDadoDoisCepsDiferentesQuandoFizermosUmaRequisicaoDeveRetornarCorposDiferentes(){
        when().
            get("https://viacep.com.br/ws/90040480/json").
                then().
                    statusCode(200).
                    contentType(ContentType.JSON).
                    body("cep", equalTo("90040-480")).
                    body("localidade", equalTo("Porto Alegre"));

        when()
            .get("https://viacep.com.br/ws/01506-030/json")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("cep", equalTo("01506-030"))
                    .body("localidade", equalTo("São Paulo"));
    }

    @Test
    @DisplayName("Testa colocar '-' no Cep")
    public void testeDadoCepComBarraQuandoFizermosUmaRequisicaoDeveRetornar200(){
        when()
            .get("https://viacep.com.br/ws/90040-480/json")
                .then()
                    .statusCode(200)
                    .contentType(ContentType.JSON)
                    .body("cep", equalTo("90040-480"))
                    .body("localidade", equalTo("Porto Alegre"));
    }

    @Test
    @DisplayName("Testa colocar '-' no lugar errado")
    public void dadoCepComBarraErradaQuandoFizermosUmaRequisicaoDeveRetornar200(){
        when()
            .get("https://viacep.com.br/ws/9004-0480/json")
                .then()
                    .statusCode(400);
    }
    

}
