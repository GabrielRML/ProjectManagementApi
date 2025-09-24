# Sistema de Gestão de Projetos

## Visão Geral

Este é um sistema de gestão de projetos desenvolvido em **Java 21** utilizando Spring Boot 3.5.5. O sistema permite gerenciar projetos, tarefas, sprints, usuários e timesheets, oferecendo funcionalidades completas para controle de tempo e recursos em projetos.

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.5** - Framework principal
- **PostgreSQL 17.6** - Banco de dados relacional
- **Redis** - Sistema de cache
- **Liquibase** - Controle de versionamento do banco de dados
- **Spring Security** - Autenticação e autorização
- **JWT** - Tokens de autenticação
- **MapStruct** - Mapeamento entre DTOs e entidades
- **Lombok** - Redução de código boilerplate
- **Swagger/OpenAPI 3** - Documentação da API

## Arquitetura do Sistema

### Banco de Dados (PostgreSQL 17.6)

O sistema utiliza PostgreSQL como banco de dados principal, com as seguintes configurações:
- Host: localhost:5432
- Database: project_management
- Controle de migrações via Liquibase

### Sistema de Cache (Redis)

Implementado para melhorar a performance do sistema:
- Host: localhost:6379
- TTL padrão: 10 minutos (600 segundos)
- Serialização JSON personalizada para suporte a tipos de data Java 8+

**⚠️ Status do Cache**: Atualmente, apenas o **ProjectController** possui implementação completa de cache. As demais entidades ainda não possuem cache implementado.

### Migrações com Liquibase

O sistema utiliza Liquibase para controle de versionamento do banco de dados, com os seguintes changelogs:
- `20250915000000-configure-uuid-generators.xml` - Configuração de geradores UUID
- `20250915000001-create-users-table.xml` - Tabela de usuários
- `20250915000002-create-project-table.xml` - Tabela de projetos
- `20250915000003-create-project_user-table.xml` - Relacionamento projeto-usuário
- `20250915000004-create-sprint-table.xml` - Tabela de sprints
- `20250915000005-create-task-table.xml` - Tabela de tarefas
- `20250915000006-create-task-user-table.xml` - Relacionamento task-usuário
- `20250915000007-create-timesheet-table.xml` - Tabela de controle de tempo
- `20250915000008-create-attachment-table.xml` - Tabela de anexos

## Modelo de Dados

### Entidades Principais

#### User
- **Campos**: id (UUID), name, email, password, role
- **Relacionamentos**: 
  - Um para muitos com ProjectUser
  - Muitos para muitos com Task
- **Funcionalidade**: Implementa UserDetails para autenticação Spring Security
- **Roles**: ADMIN, USER

#### Project
- **Campos**: id (UUID), name, description, plannedHours, initialDate, endDate, status, priority
- **Relacionamentos**: Um para muitos com ProjectUser
- **Enums**: ProgressStatus, PriorityStatus
- **Cache**: ✅ Implementado

#### ProjectUser
- **Campos**: id (UUID), allocationPercentage
- **Relacionamentos**: 
  - Muitos para um com Project
  - Muitos para um com User
- **Funcionalidade**: Controla a alocação de usuários em projetos

#### Sprint
- **Campos**: id (UUID), name, initialDate, endDate, sequence
- **Relacionamentos**: 
  - Muitos para um com Project
  - Um para muitos com Task

#### Task
- **Campos**: id (UUID), name, description, deadline, estimatedHours, sequence, status, priority
- **Relacionamentos**: 
  - Muitos para um com Sprint
  - Muitos para um com Task (rootTask - para subtarefas)
  - Muitos para muitos com User

#### Timesheet
- **Campos**: id (UUID), hoursWorked, date, description
- **Relacionamentos**: 
  - Muitos para um com User
  - Muitos para um com Task
- **Funcionalidade**: Controle de horas trabalhadas

#### Attachment
- **Campos**: id (UUID), fileName, fileType, filePath, relatedEntityId, relatedEntityType
- **Funcionalidade**: Sistema genérico de anexos para diferentes entidades
- **Enum**: AttachmentRelatedEntityType

## Configurações de Cache

### Configuração Redis (RedisConfig)

```java
@Configuration
@EnableCaching
public class RedisConfig {
    // Configurações de serialização JSON
    // TTL padrão: 10 minutos
    // Suporte a tipos de data Java 8+
}
```

### Implementação de Cache no ProjectService

O serviço de projetos utiliza as seguintes anotações de cache:

- **@Cacheable**: Para consultas (getAllProjects, getProjectById)
- **@CachePut**: Para atualizações (updateProject)
- **@CacheEvict**: Para remoção de cache (createProject, deleteProject)
- **@Caching**: Para operações múltiplas de cache

## Documentação da API (Swagger)

A documentação completa da API está disponível através do Swagger UI:

**URL de Acesso**: `http://localhost:8080/docs`

### Características da Documentação:
- Interface interativa para teste dos endpoints
- Especificação OpenAPI 3
- Documentação automática dos DTOs e modelos
- Exemplos de requisições e respostas

## Segurança

O sistema implementa autenticação JWT com Spring Security:
- Endpoints protegidos por roles (ADMIN/USER)
- Tokens JWT para autenticação stateless
- Configuração de CORS para integração frontend

## Estrutura do Projeto

```
src/main/java/com/gabriel_lima/project_management/
├── config/          # Configurações (Redis, Security, etc.)
├── controllers/     # Controladores REST
├── domain/
│   ├── entities/    # Entidades JPA
│   └── enums/       # Enumerações
├── dto/             # Data Transfer Objects
├── mapper/          # Mappers MapStruct
├── repositories/    # Repositórios JPA
└── services/        # Serviços de negócio
```

## Como Executar

### Pré-requisitos
- Java 21
- PostgreSQL 17.6
- Redis

### Configurações de Ambiente
1. Configure o banco PostgreSQL
2. Configure o Redis
3. Defina a variável de ambiente `JWT_SECRET` (opcional)

### Executando a Aplicação
```bash
./gradlew bootRun
```

### Acessando a Documentação
Após iniciar a aplicação, acesse: `http://localhost:8080/docs`

## Status de Desenvolvimento

### ✅ Implementado
- Todas as entidades e relacionamentos
- Autenticação JWT
- CRUD completo para projetos
- Cache implementado para projetos
- Documentação Swagger
- Migrações Liquibase

### 🚧 Em Desenvolvimento
- Cache para demais entidades (User, Task, Sprint, etc.)
- Funcionalidades avançadas de relatórios
- Upload de arquivos para attachments

### 📋 Roadmap
- Implementação de cache para todas as entidades
- Otimização de queries
- Testes unitários e integração
- Deploy em containers Docker

## Contato

Para dúvidas ou sugestões sobre o projeto, entre em contato com a equipe de desenvolvimento.
