import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt
import numpy as np

class PlotResults:
    """
    Classe para plotar os resultados, comparando métricas como número de nós expandidos,
    tempo de execução e custo da solução encontrada por dois algoritmos. 
    """
    def plot_results(self, data1, data2, label1, label2, filename):
        """
        Recebe como entrada dois conjuntos de dados na forma de listas (data1 e data2). 
        As listas podem armazenar informação como o número de nós expandidos por 2 algoritmos
        em um conjunto de problemas. A função assume que data1 e data2 são do mesmo tamanho. 
        
        label1 e label2 são strings com o rótulo do gráfico, para eixos x e y. 
        
        filename é o nome do arquivo no qual o gráfico será salvo. 
        """
        _, ax = plt.subplots()
        ax.scatter(data1, data2, s=100, c="g", alpha=0.5, cmap=plt.cm.coolwarm, zorder=10)
    
        lims = [
        np.min([ax.get_xlim(), ax.get_ylim()]),  # min of both axes
        np.max([ax.get_xlim(), ax.get_ylim()]),  # max of both axes
        ]
    
        ax.plot(lims, lims, 'k-', alpha=0.75, zorder=0)
        ax.set_aspect('equal')
        ax.set_xlim(lims)
        ax.set_ylim(lims)
        plt.xlabel(label1)
        plt.ylabel(label2)
        plt.savefig(filename)
        #plt.show()