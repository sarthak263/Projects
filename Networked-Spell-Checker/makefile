# Variable for compiler
CC=gcc
# Variable for compiler options
CFLAGS=-g -Wall -std=gnu99 -pthread
# Variable for cleanup command
# to clean .o file
CLEANUP=rm -rf *o

# Entry point and exit point for call to "make".
# all is the target and compile is dependency.
# makeRanLib will be completed before all.
all:
	$(CC) $(CFLAGS) networked_spellChecker.c networked_Commands.c -o network
	
	
# Remove object
clean:
	$(CLEANUP)
