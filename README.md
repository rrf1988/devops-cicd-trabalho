# Pipeline CI/CD - MBA DevOps

Trabalho da disciplina de CI/CD do MBA. A ideia era montar um pipeline de
build e deploy de uma aplicacao, entao fizemos uma aplicacao simples em
Java/Spring Boot e um pipeline no GitHub Actions que faz tudo: roda os
testes, mede cobertura, analisa qualidade de codigo no SonarCloud, builda o
jar e sobe pra uma Web App na Azure, com um smoke test no final pra garantir
que subiu certo.

Grupo: Lucas Pedro de Lima, Pedro Henrique Rodrigues Ramos, Rafael Ricardo
Franca, Rayane Ferreira dos Santos, Veronica Guimaraes dos Santos.

## O que tem aqui

```
projeto-cicd/
├── pom.xml
├── src/main/java/.../DevopsCicdDemoApplication.java   -> app Spring Boot
├── src/main/java/.../HelloController.java             -> endpoints "/" e "/health"
├── src/main/java/.../MathService.java                  -> classe simples pra ter algo pra testar
├── src/test/java/.../DevopsCicdDemoApplicationTests.java
├── src/test/java/.../MathServiceTest.java              -> testes JUnit
└── .github/workflows/pipeline.yml                      -> o pipeline
```

## O pipeline

5 jobs, um depende do outro:

1. **unit-test** - roda `mvn test` e gera o relatorio de cobertura (JaCoCo).
2. **code-analysis** - manda pro SonarCloud analisar o codigo.
3. **build** - empacota o `.jar`.
4. **deploy-azure** - pega o jar e publica na Web App da Azure.
5. **smoke-test** - espera a aplicacao subir e bate no `/health` pra
   confirmar que o deploy funcionou de verdade.

Todo push na `main` dispara o pipeline sozinho.

## App no ar

`https://devops-cicd-trabalho01-a6cagwd2cddsbtg5.centralus-01.azurewebsites.net/`

## Umas observacoes

- Usamos so a branch `main` pra simplificar dentro do prazo. Da pra fazer um
  fluxo mais completo com `develop`/`release`/`main` depois, mas nao era
  obrigatorio pra essa entrega.
- O job do SonarCloud precisou ser configurado com JDK 21 mesmo o projeto
  compilando em Java 11 - o scanner exige uma versao mais nova de Java pra
  rodar, isso nao tem nada a ver com a versao que a aplicacao usa.
- A Web App da Azure usa "unique default hostname", entao a URL final vem
  com um sufixo aleatorio no dominio, nao e so `<nome>.azurewebsites.net`
  como a gente esperava no comeco.
