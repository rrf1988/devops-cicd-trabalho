# Pipeline CI/CD — MBA DevOps (entrega 10/08)

Aplicacao Java/Spring Boot de exemplo + pipeline no GitHub Actions cobrindo build,
testes unitarios com cobertura (JaCoCo), analise de qualidade (SonarCloud) e deploy
continuo em uma Azure Web App, com smoke test pos-deploy.

O que a disciplina pede (`CI-CD_ANOTACOES.docx`): **criar um pipeline de build e deploy
de uma aplicacao** e entregar, ate 10/08, o **link do repositorio** e um **print do
deploy funcionando**.

## Estrutura

```
projeto-cicd/
├── pom.xml
├── src/main/java/.../DevopsCicdDemoApplication.java   -> classe principal Spring Boot
├── src/main/java/.../HelloController.java             -> endpoints "/" e "/health"
├── src/main/java/.../MathService.java                  -> classe simples para ter algo testável
├── src/test/java/.../DevopsCicdDemoApplicationTests.java
├── src/test/java/.../MathServiceTest.java              -> testes unitarios (JUnit 5)
└── .github/workflows/pipeline.yml                      -> pipeline GitHub Actions
```

O pipeline (`pipeline.yml`) tem 5 jobs, cada um depende do anterior:

1. **unit-test** — roda `mvn test`, gera badge e relatorio de cobertura JaCoCo.
2. **code-analysis** — roda a analise SonarCloud (`sonar-maven-plugin`).
3. **build** — empacota o `.jar` e sobe como artifact.
4. **deploy-azure** — baixa o `.jar` e publica na Azure Web App.
5. **smoke-test** — espera a app subir e chama `/health` para confirmar que o deploy
   funcionou.

## Passo a passo para entregar

### 1. Criar o repositorio no GitHub

Como voce ja tem conta no GitHub:

1. Crie um repositorio novo (ex.: `devops-cicd-demo`), publico ou privado.
2. Copie todo o conteudo desta pasta (`projeto-cicd/`) para a raiz do repositorio.
3. `git init` (se necessario), `git add .`, `git commit -m "pipeline ci/cd"`, `git push`.

### 2. Criar conta e projeto no SonarCloud

1. Acesse https://sonarcloud.io/ e faça login com sua conta GitHub.
2. Crie uma organizacao (plano **Free**) — anote o nome dela (ex.: `rafaelfranca`).
3. Em **Analyze new project**, selecione o repositorio criado no passo 1.
4. Anote o **Project Key** gerado.
5. No SonarCloud, va em **My Account > Security**, gere um token chamado
   `SONAR_TOKEN` e copie o valor (so aparece uma vez).
6. No `pom.xml` do projeto, substitua:
   - `SEU_ORGANIZATION_SONARCLOUD` pelo nome da sua organizacao;
   - `SEU_PROJECT_KEY_SONARCLOUD` pelo Project Key.

### 3. Criar a Azure Web App

1. Acesse https://portal.azure.com/ (crie conta/free trial se ainda nao tiver).
2. Crie um **Grupo de recursos** (ex.: `rg-devops-cicd`).
3. Crie um **App Service (Web App)**:
   - Runtime stack: **Java 11**, com servidor embutido (nao precisa de Tomcat, pois o
     Spring Boot ja sobe seu proprio servidor — escolha "Java SE").
   - Anote o **nome da Web App** (ex.: `devops-cicd-demo-rafael`), ele forma a URL
     `https://<nome>.azurewebsites.net`.
4. Dentro da Web App, va em **Deployment Center** (ou "Obter o perfil de publicacao")
   e baixe o **Publish Profile** (arquivo `.PublishSettings`).

### 4. Configurar Secrets e Variables no GitHub

No repositorio, va em **Settings > Secrets and variables > Actions**:

- Aba **Secrets**, criar:
  - `SONAR_TOKEN` = token gerado no SonarCloud.
  - `AZURE_WEBAPP_PUBLISH_PROFILE` = conteudo do arquivo `.PublishSettings` baixado.
- Aba **Variables**, criar:
  - `AZURE_WEBAPP_NAME` = nome da Web App criada (ex.: `devops-cicd-demo-rafael`).

### 5. Rodar o pipeline

1. Va na aba **Actions** do repositorio.
2. Um push na branch `main` ja dispara o pipeline automaticamente. Se precisar,
   use "Re-run all jobs".
3. Acompanhe os 5 jobs rodando; todos devem ficar verdes.
4. Acesse `https://<nome-da-webapp>.azurewebsites.net/` no navegador e confirme a
   mensagem "Pipeline CI/CD funcionando! Deploy realizado com sucesso".

### 6. Print e entrega

1. Tire um print da pagina da aplicacao no ar (passo anterior) **e** um print da
   execucao do pipeline com todos os jobs verdes no GitHub Actions.
2. Entregue, conforme suas anotacoes: o link do repositorio GitHub + os prints,
   ate **10/08**.

## Observacoes

- O projeto usa GitHub Flow (branch `main` unica) para simplificar a entrega dentro
  do prazo. Os roteiros de aula tambem descrevem um fluxo mais completo com branchs
  `develop`/`release`/`main` e testes de smoke, integracao, funcionais e performance
  em cada uma — isso pode ser adicionado depois como evolucao, mas nao e obrigatorio
  para a entrega minima (build + deploy funcionando).
- Testado localmente apenas quanto a sintaxe (XML/YAML/Java) — a validacao completa
  (`mvn test`/`mvn package`) ocorre no proprio GitHub Actions, que tem acesso a
  internet para baixar as dependencias do Maven.
