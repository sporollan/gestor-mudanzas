
flag=""

if [ "$1" = "-v" ]; then
	flag="-v"
fi

find estructuras mudanzas tests | grep '.java'$ | xargs javac

java tests/Main $flag
