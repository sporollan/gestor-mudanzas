
flag=""

if [ "$1" = "-v" ]; then
	flag="-v"
fi

find estructuras tests | grep '.java'$ | xargs javac

java tests/Main $flag
