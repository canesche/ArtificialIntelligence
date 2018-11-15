diagnostic network ""
node Estudou
{
	name: "estudou";
	type: discrete[2] =
	{
		"E",
		"~E"
	};
	position: (13210, 11140);
}

node QIElevado
{
	name: "QI elevado";
	type: discrete[2] =
	{
		"QI",
		"~QI"
	};
	position: (16000, 11185);
}

node RlzOsTrblhs
{
	name: "realizou os trabalhos";
	type: discrete[2] =
	{
		"R",
		"~R"
	};
	position: (18400, 11200);
}

node TirouBoaNota
{
	name: "tirou boa nota";
	type: discrete[2] =
	{
		"tirou",
		"nao tirou"
	};
	position: (15986, 12970);
}

node Aprovado
{
	name: "aprovado";
	type: discrete[2] =
	{
		"aprovado",
		"reprovado"
	};
	position: (16405, 14665);
}



probability(Estudou)
{
	0.9, 0.1;
}

probability(QIElevado)
{
	0.9, 0.1;
}

probability(RlzOsTrblhs)
{
	0.85, 0.15;
}

probability(TirouBoaNota | Estudou, QIElevado, RlzOsTrblhs)
{
	(0, 0, 0): 0.94, 0.06;
	(0, 0, 1): 0.76, 0.24;
	(0, 1, 0): 0.93, 0.07;
	(0, 1, 1): 0.21, 0.79;
	(1, 0, 0): 0.55, 0.45;
	(1, 0, 1): 0.58, 0.42;
	(1, 1, 0): 0.59, 0.41;
	(1, 1, 1): 0.01, 0.99;
}

probability(Aprovado | TirouBoaNota)
{
	(0): 0.99, 0.01;
	(1): 0.02, 0.98;
}
