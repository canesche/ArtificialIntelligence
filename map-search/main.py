from search.algorithms import Dijkstra, AStar, State
from search.map import Map
import time
import getopt
import sys

def main():
    """
    Função para testes dos algoritmos de Dijkstra e A*. Rodar o programa com a opção -h 
    para diferentes formas de execução. Se não for utilizar os gráficos, deve-se rodar com 
    
    -Para testes locais: 
        main.py
    """
    optlist, _ = getopt.getopt(sys.argv[1:], 'h:m:r:', ['plots', 'plotmaps'])

    test_instances = "test-instances/local_test.txt"
    plots = False
    plotmaps = False
    submit = False
    random_instances = False
    number_random_instances = -1
    for o, a in optlist:
        if o == "-h":
            print("Exemplos de uso:")
            print("Para imprimir gráficos comparativos, mapas e enviar resultado para servidor: main.py -m matricula --plots --maps")
            print("Para testar sem gráficos e mapas e sem enviar ao servidor: main.py -m matricula")
            exit()
        if o == "-r":
            random_instances = True
            number_random_instances = int(a)
        elif o in ("--plots"):
            plots = True
        elif o in ("--plotmaps"):
            plotmaps = True
    
    gridded_map = Map("dao-map/brc000d.map")
    dijkstra = Dijkstra(gridded_map)
    astar = AStar(gridded_map)
    
    nodes_expanded_dijkstra = []
    runtime_dijkstra = []
    solution_cost_dijkstra = []
    
    nodes_expanded_astar = []
    runtime_astar = []
    solution_cost_astar = []
    
    start_states = []
    goal_states = []
    solution_costs = []
    
    success = True
    
    if random_instances:
        print("Generating %d random instances." % number_random_instances)
        for _ in range(0, number_random_instances):
            start_states.append(gridded_map.random_state())
            goal_states.append(gridded_map.random_state())
    else:
        file = open(test_instances, "r")
        for instance_string in file:
            list_instance = instance_string.split(",")
            start_states.append(State(int(list_instance[0]), int(list_instance[1])))
            goal_states.append(State(int(list_instance[2]), int(list_instance[3])))
            
            if not submit:
                solution_costs.append(float(list_instance[4]))
        file.close()
        
    for i in range(0, len(start_states)):    
        start = start_states[i]
        goal = goal_states[i]
    
        beg = time.time()
        cost, expanded_diskstra = dijkstra.search(start, goal)
        end = time.time()
        time_dijkstra = end - beg
        nodes_expanded_dijkstra.append(expanded_diskstra)
        solution_cost_dijkstra.append(cost)
        runtime_dijkstra.append(time_dijkstra)
        
        if not submit and not random_instances:
            if cost != solution_costs[i]:
                print("Um erro foi encontrado nos testes do Dijkstra:")
                print("Estado inicial: ", start)
                print("Estado objetivo: ", goal)
                print("Custo de solução encontrada: ", cost)
                print("Custo de solução esperada: ", solution_costs[i])
                print()
                success = False

        beg = time.time()
        cost, expanded_astar = astar.search(start, goal)
        end = time.time()
        time_astar = end - beg
        nodes_expanded_astar.append(expanded_astar)
        solution_cost_astar.append(cost)
        runtime_astar.append(time_astar)
        
        if not submit and not random_instances:
            if cost != solution_costs[i]:
                print("Um erro foi encontrado nos testes do A*:")
                print("Estado inicial: ", start)
                print("Estado objetivo: ", goal)
                print("Custo de solução encontrada: ", cost)
                print("Custo de solução esperada: ", solution_costs[i])
                print()
                success = False
                
        if plotmaps:
            astar.plot_result()
            dijkstra.plot_result()
    
    if not submit and not random_instances:
        if not success:
            print("Erros foram encontrados durante os testes locais...")
        else:
            print("Sucesso! Sua implementação passou todos os testes locais.")
    
    if plots:
        from search.plot_results import PlotResults
        plotter = PlotResults()
        plotter.plot_results(runtime_astar, runtime_dijkstra, "Tempo de Execução (A*)", "Tempo de Execução (Dijkstra)", "tempo")
        plotter.plot_results(nodes_expanded_astar, nodes_expanded_dijkstra, "Nós Expandidos (A*)", "Nós Expandidos (Dijkstra)", "nos_expandidos")
        plotter.plot_results(solution_cost_astar, solution_cost_dijkstra, "Custo da Solução (A*)", "Custo da Solução (Dijkstra)", "custo_solucao")

if __name__ == "__main__":
    main()