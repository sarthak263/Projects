#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <string.h>
#include <dirent.h> 

#define ANSI_COLOR_GREEN   "\x1b[1;32m"
#define ANSI_COLOR_RESET   "\x1b[0m"
#define ANSI_COLOR_BLUE    "\x1b[1;34m"

#ifndef myShell
int WriteTo(char *args, char **parsed);
int ReadTo(char *args, char **parsed);
void IO_Redirection(char **parsed,char **filename, int i);
void prompt();
void cd(char *argv);
void help();
void echo(char *args);
void quit();
void userInput(char *args);
void dir();
int parsePipe(char *args, char **parsed);
int no_PipeCommands(char *args, char **parsed);
void clr();
void pause_P();
void No_Pipe_execute(char **parsed);
void space(char *args, char **parsed);
void pipe_execute(char **parsed, char **parsedPipe);
void environ();

#endif