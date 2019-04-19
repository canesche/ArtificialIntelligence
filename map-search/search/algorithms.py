#
#   Student: Michael Canesche
#   Student id: 68064
#   Prof.: Levi Lelis
#   Data: 06/04/2019
#

import heapq

class State:
    """
    Classe descrevendo estados no problema de busca em mapas. A classe contém duas variáveis estáticas:
    map_width e map_heiht, contendo a largura e altura do mapa, respectivamente. Embora as variáveis 
    sejam de características do mapa, e não do estado, elas são necessárias para calcular a hash perfeita
    dos estados. 
    
    Cada estado possui valores de x, y, g, h e o custo. O custo é utilizado como critério de ordenação da
    lista ABERTA nos algoritmos Dijkstra e A*. No caso do Dijkstra custo deve receber o valor g do nó; no
    caso do A*, custo recebe o valor f do nó. 
    """
    map_width = 0
    map_height = 0
    
    def __init__(self, x, y):
        """
        Construtor - requer como entrada os valores x e y do estado. Todas as outras variáveis são
        inicializadas com o valor de zero. 
        """
        self._x = x
        self._y = y
        self._g = 0
        self._h = 0
        self._cost = 0
        
    def __repr__(self):
        """
        Método chamado ao invocarmos o comando print parar um estado. Imprime [x, y], onde x e y
        são as coordenadas do estado no mapa. 
        """
        state_str = "[" + str(self._x) + ", " + str(self._y) + "]"
        return state_str
    
    def __lt__(self, other):
        """
        Função menor-que utilizada para ordenar estados no heap
        """
        return self._cost < other._cost
    
    def state_hash(self):
        """
        Dado um estado (x, y) a função retorna x*largura_do_mapa + y. Essa é uma função hash perfeita.
        Essa função é utilizada para implementar a estrutura FECHADO, com acesso à nós em O(1). 
        """
        return self._y * State.map_width + self._x
    
    def __eq__(self, other):
        """
        Função que verifica se um estado é igual a outro; utilizada pelas estruturas de dados utilizadas
        pelos algoritmos de busca. 
        """
        return self._x == other._x and self._y == other._y

    def get_x(self):
        """
        Retorna o valor de x de um estado
        """
        return self._x
    
    def get_y(self):
        """
        Retorna o valor de y de um estado
        """
        return self._y
    
    def get_g(self):
        """
        Retorna o valor g de um estado
        """
        return self._g
        
    def get_h(self):
        """
        Retorna o valor h de um estado
        """
        return self._h
    
    def get_cost(self):
        """
        Retorna o custo de um estado (g para Dijkstra e f para A*)
        """
        return self._cost
    
    def set_g(self, cost):
        """
        Seta o valor g de um estado
        """
        self._g = cost
    
    def set_h(self, h):
        """
        Seta o valor h de um estado
        """
        self._h = h
    
    def set_cost(self, cost):
        """
        Seta o custo de um estado (g para Dijkstra e f para A*)
        """
        self._cost = cost

class Search:
    """
    Superclasse do algoritmo de busca. Contém a lista ABERTA (OPEN) e FECHADA (CLOSED).
    
    A lista ABERTA está implementada como uma lista que pode ser utilizada com a biblioteca 
    heapq de python. Veja https://docs.python.org/3/library/heapq.html para mais detalhes.
    
    A lista fechada está implementada como um dicionário em que o valor hash dos estados deve
    ser utilizado como chave e o estado como valor. 
    """
    def __init__(self, gridded_map):
        self.map = gridded_map
        self.OPEN = []
        self.CLOSED = {}
    
    def search(self, start, goal):
        """
        Método a ser implementado nas classes Dijkstra e A*.
        """
        raise NotImplementedError()
        
    def plot_result(self):
        """
        Plota o resultado da busca do algoritmo, diferenciando as listas ABERTO, FECHADO,
        e os estados inicial e objetivo. Função para depurar o código. 
        """
        self.map.plot(self.start, self.goal, self.OPEN, self.CLOSED)
    
class Dijkstra(Search):
            
    def search(self, start, goal):
        """
        Algoritmo de Dijkstra: recebe como entrada um estado inicial e um objetivo. Retorna o custo do caminho 
        entre o inicial e objetivo, além do número de nós expandidos durante o processo 
        de busca. Caso uma solução não seja encontrada, retorna -1. 
        """        
        self.OPEN.clear()
        self.CLOSED.clear()
        nodes_expanded = 0

        self.start = start
        self.goal = goal
        
        heapq.heappush(self.OPEN, start)

        # loop while the heap is not empty
        while self.OPEN :
            no = heapq.heappop(self.OPEN)
            nodes_expanded += 1

            # verify if is solution
            if no == goal :
                return no.get_cost(), nodes_expanded
            
            # nodes from the neighborhood
            for c in self.map.successors(no) :

                c.set_cost(c.get_g())
                key = c.state_hash()

                # verify if key is inside of list CLOSED
                if key not in self.CLOSED :
                    heapq.heappush(self.OPEN, c)
                    self.CLOSED[key] = c
                # key in list CLOSED and verify if
                # the actual cost of a node is less than before
                elif self.CLOSED[key].get_g() > c.get_g() :
                    self.CLOSED[key].set_cost(c.get_cost())
                    self.CLOSED[key].set_g(c.get_g())
                    
        return -1, nodes_expanded
    
class AStar(Search):
    
    def h_value(self, state):
        """
        Função heurística de "octile". Para dois estados (x1, y1) e (x2, y2), retorna
        max(|x1 - x2|, |y1 - y2|) + 0.5 * min(|x1 - x2|, |y1 - y2|).
        """
        dist_x = abs(state.get_x() - self.goal.get_x()) 
        dist_y = abs(state.get_y() - self.goal.get_y())
        return max(dist_x, dist_y) + 0.5 * min(dist_x, dist_y)
            
    def search(self, start, goal):
        """
        Algoritmo A*: recebe como entrada um estado inicial e um objetivo. Retorna o custo do caminho 
        entre o inicial e objetivo, além do número de nós expandidos durante o processo 
        de busca. Caso uma solução não seja encontrada, retorna -1. 
        """
        self.start = start
        self.goal = goal
        
        self.OPEN.clear()
        self.CLOSED.clear()
        nodes_expanded = 0
        
        start.set_h(self.h_value(start))
        start.set_cost(self.h_value(start))
        
        heapq.heappush(self.OPEN, start)

        # loop while the heap is not empty
        while self.OPEN :
            no = heapq.heappop(self.OPEN)
            nodes_expanded += 1

            # verify if is solution
            if no == goal :
                return no.get_cost(), nodes_expanded
            
            # nodes from the neighborhood
            for c in self.map.successors(no) :

                key = c.state_hash()
                c.set_h(self.h_value(c))
                c.set_cost(c.get_g()+c.get_h())
                
                # verify if key is inside of list CLOSED
                if key not in self.CLOSED :
                    heapq.heappush(self.OPEN, c)
                    self.CLOSED[key] = c
                # key in list CLOSED and verify if
                # the actual cost of a node is less than before
                elif self.CLOSED[key].get_g() > c.get_g() :
                    self.CLOSED[key].set_cost(c.get_cost())
                    self.CLOSED[key].set_g(c.get_g())
                    # update heap
                    heapq.heapify(self.OPEN)

        return -1, nodes_expanded