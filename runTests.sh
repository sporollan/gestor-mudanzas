
flag=""

if [ "$1" = "-v" ]; then
	flag="-v"
fi

javac tests/*.java \
	tests/estructuras/lineales/dinamicas/TestLista.java estructuras/lineales/dinamicas/Lista.java estructuras/lineales/dinamicas/Nodo.java \
	tests/estructuras/conjuntistas/*.java \
	estructuras/conjuntistas/*.java \
	estructuras/propositoEspecifico/*.java \
	tests/estructuras/propositoEspecifico/*.java

java tests/Main $flag
