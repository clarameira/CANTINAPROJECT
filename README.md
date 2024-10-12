# CANTINAPROJECT: PedeAqui!

O **PedeAqui!** é um sistema de gerenciamento de operações em cantinas que fornece uma maneira intuitiva e eficiente para definir e gerenciar itens e processos adequados a uma cantina. Com esta ferramenta, usuários podem realizar pedidos de forma remota e administrar o estoque de itens de maneira simplificada.

## Funcionalidades

- **Cadastro de Usuários:** Permite que usuários se cadastrem e façam pedidos na cantina de forma remota.
- **Consulta de Itens:** Os usuários podem visualizar os itens disponíveis em estoque, facilitando a escolha de pedidos.
- **Gerenciamento de Cardápio:** O administrador da cantina tem a capacidade de alterar itens do cardápio e atualizar informações sobre o estoque.
- **Otimização de Processos:** O sistema reduz a necessidade de pedidos presenciais, agilizando o atendimento e a gestão do estoque.

## Requisitos do Sistema

- **Java JDK 8 ou superior**
- **Java Swing:** Para a construção da interface gráfica.
- **Banco de Dados:** PostgreSQL

## Técnicas de Teste

### Testes de Caixa Preta

Os testes de caixa preta focam na funcionalidade do sistema sem considerar a implementação interna. As principais técnicas utilizadas incluem:

- **Técnica de Equivalência de Particionamento:** Divide os dados de entrada em grupos que são tratados da mesma forma, garantindo que os testes cubram todas as variações relevantes.

- **Teste de Valor Limite:** Realiza testes em valores extremos e nas bordas das classes de equivalência para identificar erros em condições limites.

- **Teste de Tabela de Decisão:** Utiliza tabelas para representar combinações de entradas e as saídas esperadas, permitindo a cobertura de múltiplas condições de forma sistemática.

- **Grafo de Causa e Efeito:** Ilustra como diferentes entradas afetam o comportamento do sistema, podemos identificar e testar todos os caminhos possíveis de execução.

### Testes de Caixa Branca

## Integração com SonarCloud

A plataforma [SonarCloud](https://sonarcloud.io/) será utilizada para monitorar a qualidade do código, oferecendo uma análise abrangente sobre a cobertura de testes e a identificação de vulnerabilidades. Essa integração ajudará a manter as melhores práticas de codificação e a assegurar a manutenção contínua do sistema.