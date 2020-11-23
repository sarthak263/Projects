# Variable for compiler
CC=gcc
# Variable for compiler options
CFLAGS= -I.
# Variable for cleanup command
# to clean .o file
CLEANUP=rm -rf *o

# Entry point and exit point for call to "make".
# all is the target and compile is dependency.
# makeRanLib will be completed before all.
all: run
	
run: utility.o myShell.o	
	gcc utility.o myShell.o
utility.o: utility.c myShell.h
	gcc -c -Wall utility.c
myShell.o: myShell.c myShell.h
	gcc -c -Wall myShell.c
	
# Remove object
clean:
	$(CLEANUP)
