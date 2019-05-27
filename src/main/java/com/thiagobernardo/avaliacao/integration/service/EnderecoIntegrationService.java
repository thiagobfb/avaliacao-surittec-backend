package com.thiagobernardo.avaliacao.integration.service;

import com.thiagobernardo.avaliacao.integration.dto.EnderecoIntegrationDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EnderecoIntegrationService {

    private static final String URI = "https://viacep.com.br/ws/";

    public EnderecoIntegrationDTO buscarEnderecoPorCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        EnderecoIntegrationDTO dto = restTemplate.getForObject(URI  + cep + "/json", EnderecoIntegrationDTO.class);

        return dto;
    }
}
