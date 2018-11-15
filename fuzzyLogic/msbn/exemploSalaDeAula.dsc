diagnostic network ""
node Arrombamento
{
	name: "Arrombamento";
	type: discrete[2] =
	{
		"~R",
		"R"
	};
	position: (15445, 11800);
}

node Terremoto
{
	name: "Terremoto";
	type: discrete[2] =
	{
		"T",
		"~T"
	};
	position: (19345, 11710);
}

node Alarme
{
	name: "Alarme";
	type: discrete[2] =
	{
		"A",
		"~A"
	};
	position: (17590, 13450);
}

node Jo_oLiga
{
	name: "JoãoLiga";
	type: discrete[2] =
	{
		"J",
		"~J"
	};
	position: (16090, 15415);
}

node MariaLiga
{
	name: "MariaLiga";
	type: discrete[2] =
	{
		"M",
		"~M"
	};
	position: (19120, 15370);
}



probability(Arrombamento)
{
	0.99, 0.01;
}

probability(Terremoto)
{
	0.94, 0.06;
}

probability(Alarme | Arrombamento, Terremoto)
{
	(0, 0): 0.29, 0.71;
	(0, 1): 0.001, 0.999;
	(1, 0): 0.95, 0.05;
	(1, 1): 0.94, 0.06;
}

probability(Jo_oLiga | Alarme)
{
	(0): 0.9, 0.1;
	(1): 0.05, 0.95;
}

probability(MariaLiga | Alarme)
{
	(0): 0.7, 0.3;
	(1): 0.01, 0.99;
}
