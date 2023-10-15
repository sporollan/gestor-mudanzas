
flag=""

if [ "$1" = "-v" ]; then
	flag="-v"
fi

javac tests/*.java \
	tests/lineales/dinamicas/TestLista.java lineales/dinamicas/Lista.java lineales/dinamicas/Nodo.java \
	tests/conjuntistas/*.java \
	conjuntistas/*.java
$flag
java tests/Main $flag
