#include "myShell.h"

int main(int argc, char *argv[]) 
{
	printf("WELCOME TO MY SHELL\n\n");
	printf("THIS IS WHERE YOU WILL USE MY SHELL\n\n\n\n");
	char args[1024];
	char *parsed[1024];
	char *redirectParsed[1024];
	char *redirectParsed2[1024];
	char *RredirectParsed[1024];
	char *RredirectParsed2[1024];
	char *strPipe[2];
	char *parsedPipe[1024];
	char *strRedirect[2];
	char *strRedirect2[2];
	// runs forver until user hits quit
	while(1) {
		int execute = 0;
		//prints the prompt
		prompt();
		//ask for the user to enter strings
		userInput(args);
		//uses the strings for many fucntions
		char *line = strdup(args);
		int check = 0;
		int piped = parsePipe(line, strPipe); 
		int WRITE = WriteTo(line, strRedirect);// echo hello > temp
		int READ = ReadTo(line,strRedirect2);
	//	printf("line is %s\n",line);
		//printf("Write To is %s\n",strRedirect[0]);// echo hello
		//printf("Write To is %s\n",strRedirect[1]);// temp
		//check if >or < is used
		if(WRITE == 1) {
			space(strRedirect[0],redirectParsed);
			space(strRedirect[1],redirectParsed2);
			IO_Redirection(redirectParsed,redirectParsed2, WRITE);
			execute = 1;
		}
		else if(READ == 2) {
			space(strRedirect2[0], RredirectParsed);
			space(strRedirect2[1], RredirectParsed2);
			IO_Redirection(RredirectParsed,RredirectParsed2, READ);
			execute = 1;
		}else {
			//check if pipe function is used
			if(piped) {
			space(strPipe[0], parsed);// cd new | lo ve = cd
			space(strPipe[1], parsedPipe);
			pipe_execute(parsed, parsedPipe);
			check = 1;
			execute = 1;
		}else if(!check) {
			// check if nothing is used then use my bultin
			 execute = no_PipeCommands(line,parsed);
		}
		if(!execute){
			// if my built is not used then use simple fucntions
			No_Pipe_execute(parsed);
			printf("\n");
		}
		
		}
	}
}