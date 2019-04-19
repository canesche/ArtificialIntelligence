from search.algorithms import State
import numpy as np
import random

class Map:
    """
    Classe para armazenar mapas no formato de grid. Os mapas foram disponibilizados pelo site movingai.org
    """
    def __init__(self, file_name):
        self.file_name = file_name        
        self.map_file = open(self.file_name)
        self.type_map = self.map_file.readline()
        self.height = int(self.map_file.readline().split(' ')[1])
        self.width = int(self.map_file.readline().split(' ')[1])
        
        State.map_width = self.width
        State.map_height = self.height
        
        self.read_map()
        self.convert_data()
        
        self.map_file.close()
        
    def read_map(self):
        """
        Função que lê o mapa e o armazena em formato de texto na memória do computador. 
        """
        line = self.map_file.readline()
        while 'map' not in line:
            line = self.map_file.readline()
        lines = self.map_file.readlines()

        self.data_str = []
        for line in lines:
            line_list = []
            line = line.replace('\n', '')
            for c in line:
                line_list.append(c)
            self.data_str.append(line_list)
        
    def convert_data(self):
        """
        Função que converte o mapa, inicialmente no formato de texto, para inteiros. Onde células 
        atravessáveis possuem o valor 1 e células não atravessáveis o valor 0. 
        
        De acordo com a documentação do movingai.org, esses são os códigos dos mapas utilizados:
        
        . - passable terrain
        G - passable terrain
        @ - out of bounds
        O - out of bounds
        T - trees (unpassable)
        S - swamp (passable from regular terrain)
        W - water (traversable, but not passable from terrain)
        """
        self.data_int = np.zeros((len(self.data_str), len(self.data_str[0])))

        for i in range(0, self.height):
            for j in range(0, self.width):
                if self.data_str[i][j] == '.' or self.data_str[i][j] == 'G':
                    self.data_int[i][j] = 0
                else:
                    self.data_int[i][j] = 1
        
    def plot(self, start, goal, OPEN, CLOSED, width=800, height=600):
        """
        Método para plotar o mapa para uma data lista ABERTA e FECHADA. A chamada desse método
        requer que a biblioteca tkinter esteja instalada no computador. A instalação de tal biblioteca
        é dependente do sistema operacional e ela não está na lista de bibliotecas do arquivo requirements.txt 
        """
        from tkinter import Tk, Canvas
        
        self.convert_data()
        for _, state in CLOSED.items():
            self.data_int[state.get_y()][state.get_x()] = 3
        for state in OPEN:
            self.data_int[state.get_y()][state.get_x()] = 2
        
        self.data_int[start.get_y()][start.get_x()] = 4
        self.data_int[goal.get_y()][goal.get_x()] = 5
        
        master = Tk()
        canvas = Canvas(master, bg="black", width=width, height=height)
        canvas.pack()
        h,l = self.data_int.shape
        x_size = width/l
        y_size = height/h
        for i in range(h):
            for j in range(l):
                if self.data_int[i,j] == 1:
                    canvas.create_rectangle(x_size*j , y_size*i, x_size + x_size*j, y_size + y_size*i, fill="gray", outline='gray')
                elif self.data_int[i,j] == 4:
                    canvas.create_rectangle(x_size*j , y_size*i, x_size + x_size*j, y_size + y_size*i, fill="green", outline='green')    
                elif self.data_int[i,j] == 5:
                    canvas.create_rectangle(x_size*j , y_size*i, x_size + x_size*j, y_size + y_size*i, fill="yellow", outline='yellow')
                elif self.data_int[i,j] == 2:
                    canvas.create_rectangle(x_size*j , y_size*i, x_size + x_size*j, y_size + y_size*i, fill="blue", outline='blue')
                elif self.data_int[i,j] == 3:
                    canvas.create_rectangle(x_size*j , y_size*i, x_size + x_size*j, y_size + y_size*i, fill="red", outline='red')

        master.mainloop()
        
        
    def random_state(self):
        """
        Gera um estado aleatório válido para o mapa.
        """
        x = random.randint(0, self.width - 1)
        y = random.randint(0, self.height - 1)
        while self.data_int[y][x] == 1:
            x = random.randint(0, self.width - 1)
            y = random.randint(0, self.height - 1)
        state = State(x, y)
        return state
    
    def is_valid_pair(self, x, y):
        """
        Verifica se um par x e y é válido para o mapa.
        """
        if x < 0 or y < 0:
            return False
        if x >= self.width or y >= self.height:
            return False
        if self.data_int[y][x] == 1:
            return False
        return True
    
    def cost(self, x, y):
        """
        Retorna o custo de um operador no mapa. 
        
        Movimentos diagonais custam 1.5; movimentos nas 4 direções cardeais custam 1.0
        """
        if x == 0 or y == 0:
            return 1
        else:
            return 1.5
    
    def successors(self, state):
        """
        Função de sucessores (transição). Recebe um estado como parâmetro e retorna uma lista com 
        os filhos do estado. 
        """
        children = []
        for i in range(-1, 2):
            for j in range(-1, 2):
                if i == 0 and j == 0:
                    continue
                if self.is_valid_pair(state.get_x() + i, state.get_y() + j):
                    s = State(state.get_x() + i, state.get_y() + j)
                    s.set_g(state.get_g() + self.cost(i, j))
                    children.append(s)
        return children