import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;

import javax.swing.*;

public class LabirintoEditor extends JFrame {
    
    private static final int LINHAS = 7;
    private static final int COLUNAS = 12;
    private static final int ORIGEM_X = 0;
    private static final int ORIGEM_Y = 0;
    private static final int DESTINO_X = COLUNAS - 1;
    private static final int DESTINO_Y = LINHAS - 1;
    
    // Cores
    private static final Color COR_PAREDE = Color.WHITE;
    private static final Color COR_CAMINHO = Color.BLACK;
    private static final Color COR_VISITADO = Color.YELLOW;
    private static final Color COR_ORIGEM = Color.GREEN;
    private static final Color COR_DESTINO = Color.RED;
    
    private JButton[][] botoesCelulas;
    private int[][] matriz; // 0 = parede, 1 = caminho livr
    private JButton btnBFS, btnDFS, btnReiniciar;
    private JLabel lblStatus;
    
    public LabirintoEditor() {
        initializeComponents();
        setupLayout();
        reiniciarMatriz();
    }
    
    private void initializeComponents() {
        setTitle("Editor de Labirinto - BFS e DFS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Inicializar matriz e botões
        botoesCelulas = new JButton[LINHAS][COLUNAS];
        matriz = new int[LINHAS][COLUNAS];
        
        // Criar botões da matriz
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                botoesCelulas[i][j] = new JButton();
                botoesCelulas[i][j].setPreferredSize(new Dimension(40, 40));
                botoesCelulas[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                
                final int linha = i;
                final int coluna = j;
                
                botoesCelulas[i][j].addActionListener(e -> toggleCelula(linha, coluna));
            }
        }
        
        // Botões de controle
        btnBFS = new JButton("Buscar em Largura (BFS)");
        btnDFS = new JButton("Buscar em Profundidade (DFS)");
        btnReiniciar = new JButton("Reiniciar");
        
        btnBFS.addActionListener(e -> executarBFS());
        btnDFS.addActionListener(e -> executarDFS());
        btnReiniciar.addActionListener(e -> reiniciarMatriz());
        
        // Label de status
        lblStatus = new JLabel("Clique nas células para desenhar o labirinto");
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Painel da matriz
        JPanel painelMatriz = new JPanel(new GridLayout(LINHAS, COLUNAS, 1, 1));
        painelMatriz.setBorder(BorderFactory.createTitledBorder("Labirinto 12x7"));
        
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                painelMatriz.add(botoesCelulas[i][j]);
            }
        }
        
        // Painel de controles
        JPanel painelControles = new JPanel(new FlowLayout());
        painelControles.add(btnBFS);
        painelControles.add(btnDFS);
        painelControles.add(btnReiniciar);
        
        // Painel de legenda
        JPanel painelLegenda = new JPanel(new GridLayout(2, 3, 10, 5));
        painelLegenda.setBorder(BorderFactory.createTitledBorder("Legenda"));
        
        painelLegenda.add(criarLegendaItem("Parede", COR_PAREDE));
        painelLegenda.add(criarLegendaItem("Caminho", COR_CAMINHO));
        painelLegenda.add(criarLegendaItem("Visitado", COR_VISITADO));
        painelLegenda.add(criarLegendaItem("Origem", COR_ORIGEM));
        painelLegenda.add(criarLegendaItem("Destino", COR_DESTINO));
        
        // Layout principal
        add(painelMatriz, BorderLayout.CENTER);
        add(painelControles, BorderLayout.NORTH);
        add(painelLegenda, BorderLayout.SOUTH);
        add(lblStatus, BorderLayout.PAGE_END);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel criarLegendaItem(String texto, Color cor) {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel quadrado = new JLabel("  ");
        quadrado.setOpaque(true);
        quadrado.setBackground(cor);
        quadrado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        painel.add(quadrado);
        painel.add(new JLabel(texto));
        return painel;
    }
    
    private void toggleCelula(int linha, int coluna) {
        // Não permitir alterar origem e destino
        if ((linha == ORIGEM_Y && coluna == ORIGEM_X) || 
            (linha == DESTINO_Y && coluna == DESTINO_X)) {
            return;
        }
        
        // Alternar entre parede e caminho
        if (matriz[linha][coluna] == 0) {
            matriz[linha][coluna] = 1;
            botoesCelulas[linha][coluna].setBackground(COR_CAMINHO);
        } else {
            matriz[linha][coluna] = 0;
            botoesCelulas[linha][coluna].setBackground(COR_PAREDE);
        }
        
        limparVisualizacao();
    }
    
    private void reiniciarMatriz() {
        // Limpar matriz (todas as células como parede)
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                matriz[i][j] = 0;
                botoesCelulas[i][j].setBackground(COR_PAREDE);
            }
        }
        
        // Garantir que origem e destino sejam caminhos livres
        matriz[ORIGEM_Y][ORIGEM_X] = 1;
        matriz[DESTINO_Y][DESTINO_X] = 1;
        
        atualizarVisualizacao();
        lblStatus.setText("Matriz reiniciada. Desenhe seu labirinto!");
    }
    
    private void limparVisualizacao() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (matriz[i][j] == 1) {
                    botoesCelulas[i][j].setBackground(COR_CAMINHO);
                } else {
                    botoesCelulas[i][j].setBackground(COR_PAREDE);
                }
            }
        }
        atualizarVisualizacao();
    }
    
    private void atualizarVisualizacao() {
        // Marcar origem e destino
        botoesCelulas[ORIGEM_Y][ORIGEM_X].setBackground(COR_ORIGEM);
        botoesCelulas[DESTINO_Y][DESTINO_X].setBackground(COR_DESTINO);
    }
    
    private void executarBFS() {
        limparVisualizacao();
        
        LinkedHashSet<Point> caminho = buscaLargura();
        
        if (caminho.isEmpty() || !caminhoEncontraDestino(caminho)) {
            JOptionPane.showMessageDialog(this, "Caminho não encontrado!", 
                                        "Resultado BFS", JOptionPane.WARNING_MESSAGE);
            lblStatus.setText("BFS: Caminho não encontrado");
        } else {
            visualizarCaminho(caminho);
            lblStatus.setText("BFS: Caminho encontrado! Células visitadas: " + caminho.size());
        }
    }
    
    private void executarDFS() {
        limparVisualizacao();
        
        LinkedHashSet<Point> caminho = buscaProfundidade();
        
        if (caminho.isEmpty() || !caminhoEncontraDestino(caminho)) {
            JOptionPane.showMessageDialog(this, "Caminho não encontrado!", 
                                        "Resultado DFS", JOptionPane.WARNING_MESSAGE);
            lblStatus.setText("DFS: Caminho não encontrado");
        } else {
            visualizarCaminho(caminho);
            lblStatus.setText("DFS: Caminho encontrado! Células visitadas: " + caminho.size());
        }
    }
    
    private boolean caminhoEncontraDestino(LinkedHashSet<Point> caminho) {
        return caminho.contains(new Point(DESTINO_X, DESTINO_Y));
    }
    
    private void visualizarCaminho(LinkedHashSet<Point> caminho) {
        for (Point p : caminho) {
            if (!(p.x == ORIGEM_X && p.y == ORIGEM_Y) && 
                !(p.x == DESTINO_X && p.y == DESTINO_Y)) {
                botoesCelulas[p.y][p.x].setBackground(COR_VISITADO);
            }
        }
        atualizarVisualizacao();
    }
    
    // Adaptação do algoritmo BFS para matriz 2D
    private LinkedHashSet<Point> buscaLargura() {
        LinkedHashSet<Point> verticesVisitados = new LinkedHashSet<>();
        Queue<Point> filaPercurso = new LinkedList<>();
        
        Point origem = new Point(ORIGEM_X, ORIGEM_Y);
        verticesVisitados.add(origem);
        filaPercurso.add(origem);
        
        // Direções: cima, baixo, esquerda, direita
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        
        while (!filaPercurso.isEmpty()) {
            Point verticeAtual = filaPercurso.poll();
            
            // Se chegou ao destino, parar
            if (verticeAtual.x == DESTINO_X && verticeAtual.y == DESTINO_Y) {
                break;
            }
            
            // Explorar vizinhos
            for (int i = 0; i < 4; i++) {
                int novoX = verticeAtual.x + dx[i];
                int novoY = verticeAtual.y + dy[i];
                
                Point vizinho = new Point(novoX, novoY);
                
                if (isValido(novoX, novoY) && !verticesVisitados.contains(vizinho)) {
                    verticesVisitados.add(vizinho);
                    filaPercurso.add(vizinho);
                }
            }
        }
        
        return verticesVisitados;
    }
    
    // Adaptação do algoritmo DFS para matriz 2D
    private LinkedHashSet<Point> buscaProfundidade() {
        LinkedHashSet<Point> verticesVisitados = new LinkedHashSet<>();
        Stack<Point> pilhaPercurso = new Stack<>();
        
        Point origem = new Point(ORIGEM_X, ORIGEM_Y);
        pilhaPercurso.push(origem);
        
        // Direções: cima, baixo, esquerda, direita
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        
        while (!pilhaPercurso.isEmpty()) {
            Point verticeAtual = pilhaPercurso.pop();
            
            if (!verticesVisitados.contains(verticeAtual)) {
                verticesVisitados.add(verticeAtual);
                
                // Se chegou ao destino, continuar para mostrar o caminho completo
                if (verticeAtual.x == DESTINO_X && verticeAtual.y == DESTINO_Y) {
                    break;
                }
                
                // Explorar vizinhos (em ordem reversa para manter consistência com DFS)
                for (int i = 3; i >= 0; i--) {
                    int novoX = verticeAtual.x + dx[i];
                    int novoY = verticeAtual.y + dy[i];
                    
                    Point vizinho = new Point(novoX, novoY);
                    
                    if (isValido(novoX, novoY) && !verticesVisitados.contains(vizinho)) {
                        pilhaPercurso.push(vizinho);
                    }
                }
            }
        }
        
        return verticesVisitados;
    }
    
    private boolean isValido(int x, int y) {
        return x >= 0 && x < COLUNAS && y >= 0 && y < LINHAS && matriz[y][x] == 1;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LabirintoEditor().setVisible(true);
        });
    }
}