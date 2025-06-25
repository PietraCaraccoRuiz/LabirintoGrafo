# LabirintoGrafo

# Editor de Labirinto com BFS e DFS

Uma aplicação Java com interface gráfica (Swing) que permite criar labirintos interativamente e visualizar algoritmos de busca em ação.

## 📋 Descrição

Este projeto implementa um editor visual de labirintos onde o usuário pode:
- Desenhar labirintos personalizados clicando nas células
- Executar algoritmos de **Busca em Largura (BFS)** e **Busca em Profundidade (DFS)**
- Visualizar o caminho percorrido pelos algoritmos em tempo real

## 🎯 Funcionalidades

### Interface Gráfica
- ✅ Matriz interativa 12x7 (84 células)
- ✅ Alternância entre caminho livre (preto) e parede (branco) com clique
- ✅ Três botões principais: BFS, DFS e Reiniciar
- ✅ Legenda visual com cores explicativas
- ✅ Barra de status com feedback em tempo real

### Algoritmos Implementados
- 🔍 **BFS (Breadth-First Search)**: Busca em largura
- 🔍 **DFS (Depth-First Search)**: Busca em profundidade
- 📍 Movimento apenas horizontal e vertical (sem diagonais)
- 🎯 Busca do ponto origem (0,0) até destino (11,6)

### Recursos Visuais
- 🟩 **Verde**: Ponto de origem (0,0)
- 🟥 **Vermelho**: Ponto de destino (11,6)
- ⬛ **Preto**: Caminho livre
- ⬜ **Branco**: Parede
- 🟨 **Amarelo**: Células visitadas durante a busca

## 🚀 Como Executar

### Pré-requisitos
- Java 8 ou superior instalado
- Compilador Java (javac)

### Compilação e Execução
```bash
# Compilar o projeto
javac LabirintoEditor.java

# Executar a aplicação
java LabirintoEditor
```

## 🎮 Como Usar

### 1. Criando o Labirinto
1. **Clique nas células** para alternar entre caminho livre (preto) e parede (branco)
2. **Origem** (canto superior esquerdo) e **destino** (canto inferior direito) são fixos
3. Desenhe um caminho conectando origem ao destino

### 2. Executando Algoritmos
- **Buscar em Largura (BFS)**: Executa busca em largura
- **Buscar em Profundidade (DFS)**: Executa busca em profundidade
- **Reiniciar**: Limpa toda a matriz (todas as células viram paredes)

### 3. Interpretando Resultados
- **Células amarelas** mostram o caminho percorrido pelo algoritmo
- **Barra de status** informa se o caminho foi encontrado e quantas células foram visitadas
- **Popup de aviso** aparece quando não há caminho possível

## 🏗️ Estrutura do Código

### Classes Principais
```java
public class LabirintoEditor extends JFrame
```

### Componentes Principais
- `botoesCelulas[][]`: Matriz de botões da interface
- `matriz[][]`: Representação lógica do labirinto (0=parede, 1=caminho)
- `buscaLargura()`: Implementação do algoritmo BFS
- `buscaProfundidade()`: Implementação do algoritmo DFS

### Constantes Importantes
```java
private static final int LINHAS = 7;
private static final int COLUNAS = 12;
private static final int ORIGEM_X = 0, ORIGEM_Y = 0;
private static final int DESTINO_X = 11, DESTINO_Y = 6;
```

## 🎨 Exemplo de Uso

1. **Inicie a aplicação**
2. **Desenhe um labirinto simples**:
   ```
   ●→→→→→→→→→→→
   →                 
   →                 
   →→→→→→→→→→→→
                   →
                   →
                   ●
   ```
   (● = origem, ● = destino, → = caminho)

3. **Clique em "Buscar em Largura"**
4. **Observe as células amarelas** mostrando o caminho percorrido

## 🔧 Detalhes Técnicos

### Algoritmos de Busca
- **BFS**: Usa `Queue<Point>` para exploração nível por nível
- **DFS**: Usa `Stack<Point>` para exploração em profundidade
- **Ambos** usam `LinkedHashSet<Point>` para manter ordem de visitação

### Validações
- Verificação de limites da matriz
- Verificação se célula é caminho livre
- Prevenção de loops infinitos
- Tratamento de casos sem solução

### Movimentação
```java
// Direções: cima, baixo, esquerda, direita
int[] dx = {0, 0, -1, 1};
int[] dy = {-1, 1, 0, 0};
```

## 🐛 Tratamento de Erros

- **Caminho não encontrado**: Exibe popup de aviso
- **Células inválidas**: Verificação de limites automática
- **Interface responsiva**: Sem travamentos durante execução
- **Validação de entrada**: Prevenção de estados inválidos

## 🎓 Conceitos Demonstrados

- **Algoritmos de Grafos**: BFS e DFS
- **Interface Gráfica**: Java Swing
- **Estruturas de Dados**: Queue, Stack, LinkedHashSet
- **Programação Orientada a Objetos**: Encapsulamento e organização
- **Tratamento de Eventos**: ActionListener
- **Visualização de Algoritmos**: Representação gráfica em tempo real

**Desenvolvido como projeto acadêmico para demonstração de algoritmos de busca em grafos com interface gráfica.**
