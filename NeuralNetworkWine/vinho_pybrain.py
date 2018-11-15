# encoding:utf-8

'''
    Disciplina Inteligência Artificial I

Estudantes: Michael Canesche – 68064
	        Lucio Paulo Reis – 78379
	        Jean Martins Vieira de Góes - 85259

Professor Responsável: Alcione de Paiva Oliveira

	Instalação necessária (python 2.7)
	1) sudo apt install python-pip
	2) sudo pip install numpy scipy setuptools pybrain
'''

from pybrain.datasets import SupervisedDataSet
from pybrain.tools.shortcuts import buildNetwork
from pybrain.supervised import BackpropTrainer

# adiciona-se as amostras que consiste numa entrada e num objetivo

arq = open("vinho.csv","r") 

texto = arq.read()

listas = []; saidas = []
entradas = list()
texto = texto.split("\n")

for i in range(1,len(texto)):
	#listas.append(texto[i].split(";"))
	listas.append(texto[i].split(","))

for i in range(0,len(listas)-1):
	l = list()
	for j in range(len(listas[i])):
		if j < len(listas[i])-1:
			l.append(float(listas[i][j]))
		else:
			entradas.append(l)
			saidas.append(float(listas[i][j])) 
arq.close()

# cria-se um conjunto de dados (dataset) para treinamento
# são passadas as dimensões dos vetores de entrada e do objetivo
dataset = SupervisedDataSet(len(entradas[0]),1)

for i in range(len(entradas)):
	dataset.addSample(entradas[i],saidas[i])

'''
Agora iremos construir a rede utilizando a função buildNetwork
dataset.indim é o tamanho da camada de entrada
4 é a quantidade de camadas intermediárias
dataset.outdim é o tamanho da camada de saída
iremos utilizar o "bias" para permitir uma melhor adaptação por parte da rede neural
ao conhecimento à ela fornecido
'''
network = buildNetwork(dataset.indim, 4, dataset.outdim, bias=True)

'''
O procedimento que iremos utilizar para treinar a rede é o backpropagation.
É passada a rede, o conjunto de dados (dataset), "learningrate" é a taxa de aprendizado,
"momentum" tem por objetivo aumentar a velocidade de treinamento da rede neural e
diminuir o perigo da instabilidade.
'''
trainer = BackpropTrainer(network, dataset, learningrate=0.01, momentum=0.99)

# Logo em seguinda é feito de fato o treinamento da rede
for epoch in range(0, 200): # treina por 1000 épocas
    trainer.train()

'''
Outras formas de treinar:
    trainer.trainEpochs(1000)
    treinar até a convergência: trainer.trainUntilConvergence()
'''

# Agora iremos testar a rede com um conjunto de dados
test_data = SupervisedDataSet(len(entradas[0]),1)
for i in range(len(entradas)):
	test_data.addSample(entradas[i],[saidas[i]])

# verbose=True indica que deve ser impressas mensagens
trainer.testOnData(test_data, verbose=True)
