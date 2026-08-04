package br.com.rafaelfranca.devopscicddemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Pipeline CI/CD funcionando! Deploy realizado com sucesso - Grupo Lucas Pedro de Lima, Pedro Henrique Rodrigues Ramos, Rafael Ricardo Franca, Rayane Ferreira dos Santos, Veronica Guimaraes dos Santos - MBA DevOps";
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
