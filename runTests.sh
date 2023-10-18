
flag=""

if [ "$1" = "-v" ]; then
	flag="-v"
fi

javac tests/*.java \
	tests/lineales/dinamicas/TestLista.java lib/lineales/dinamicas/Lista.java lib/lineales/dinamicas/Nodo.java \
	tests/conjuntistas/*.java \
	lib/conjuntistas/*.java
$flag
java tests/Main $flag
