#include "myShell.h"


// this is environ function
void environ() {
	
	char cwd[1024];
	// gets the current directory
	getcwd(cwd,1024);
	printf("SHELL=/bin/bash\n");
	printf("TERM=xterm-256color\n");
	printf("PWD=%s\n",cwd);
	printf("USER=sarthak26\n");
	printf("NAME=LAPTOP-S8MRRVMF\n");
	char *s = getenv("PATH");
	printf("PATH=%s\n",s);
	printf("LANG=en_US.UTF-8\nSHLVL=1\nHOME=/home/sarthak26\n");
	printf("LOGNAME=sarthak26\nXDG_DATA_DIRS=/usr/local/share:/usr/share:/var/lib/snapd/desktop\n");
	printf("LESSOPEN=| /usr/bin/lesspipe\n");
	printf("LESSCLOSE=/usr/bin/lesspipe\n");
	printf("_=/usr/bin/env\n");
}

// sperated the words by spaces
void space(char *args, char **parsed) 
{
	int i;
	for(i = 0; i < 100; i++) 
	{
		parsed[i] = strsep(&args, " ");
		if(parsed[i] == NULL) {
			return;
			break;
		}
		if(strlen(parsed[i]) == 0) {
			i--;
		}		
	}
	
}

// executes when pipe is entered by user
void pipe_execute(char **parsed, char **parsedPipe) 
{
	int pipefd[2];
	pid_t p1, p2;
	
	if(pipe(pipefd) < 0) 
	{
		printf("no intialization\n");
		return;
	}
	p1 = fork();
	if(p1 < 0) {
		perror("error forking");
		return;
	}
	if(p1 ==0) 
	{
		// child process
		// process the file discrptores that are left of the pipe
		close(pipefd[0]);
		dup2(pipefd[1], STDOUT_FILENO);
		close(pipefd[1]);
		
		if(execvp(parsed[0], parsed) < 0) {
			printf("error executing\n");
			exit(0);
		}
	} 
	else {
		// parent process
		p2 = fork();
		
		if(p2 < 0) {
			printf("error forking\n");
			return;
		}
		// child process same thing but does the right of the pipe
		if(p2 ==0) {
			close(pipefd[1]);
			dup2(pipefd[0], STDIN_FILENO);
			close(pipefd[0]);
			if(execvp(parsedPipe[0], parsedPipe) < 0) {
				printf("error executing\n");
				exit(0);
			}	
		} else {
			// closes both fd 
			close(pipefd[0]);
			close(pipefd[1]);
			// wait for the child process to execute
			wait(NULL);
			wait(NULL);
		}
	}
	return;
}
//execute when bultin in command is not entered but simple command is entered
void No_Pipe_execute(char **parsed) 
{
	//printf("parsed is %s\n",parsed[0]);
	// creates two process
	pid_t pid = fork();
	int state = 0;
	int p = 0;
	// check if the last char is and & if it is puts a null character
	if(parsed[0][strlen(parsed[0])-1] == '&') {
		state = 1;
		parsed[0][strlen(parsed[0])-1] = '\0';
	}
	
	//error forking
	if(pid == -1) {
		perror("Forking failure in No pipe execution method\n");
	}else if (pid > 1) {// parent process
		if(state == 0)
			waitpid(pid,&p,WUNTRACED);
		return;
	}else {// child process
		if(execvp(parsed[0],parsed) < 0) {
			printf("Cannot execute\n");
		}
	}
	
}
// read to when user enters < and separates them
int ReadTo(char *args, char **parsed)
{
	int flag = 0;
	
	for(int i = 0; i < 2; i++) {
		parsed[i] = strsep(&args, "<");
		if(parsed[i] == NULL)
			break;
	}
	//no PIPE found
	if(parsed[1] == NULL){
		flag = 0;
	}else
		flag = 2;
	
	return flag;
}
// write or creates when users enters > and separates them
int WriteTo(char *args, char **parsed) 
{
	int flag = 0;
	for(int i = 0; i < 2; i++) {
		parsed[i] = strsep(&args, ">");
		if(parsed[i] == NULL)
			break;
	}
	//no PIPE found
	if(parsed[1] == NULL){

		flag = 0;
	}else
		flag = 1;
	
	return flag;
}
// function for implemetning > and <
void IO_Redirection(char **parsed,char **parsedArrow, int i) 
{	
	char *file = parsedArrow[0];
	int fd[2];
	int state = 0;
	pid_t pid = fork();
		if(pid == -1) {
			printf("Error forking in I/O redirection");
			exit(-1);
		}else if(pid == 0) {
			// if < this is read only
			if(i ==2) {
				fd[0] = open(file, O_RDONLY, 0600);
				if(fd[0] == -1) {
					printf("ERROR READING\n");
				}
				dup2(fd[0], STDIN_FILENO);
				close(fd[0]);
			}else if(i == 1) {
				// this write only
				fd[1] = open(file, O_WRONLY | O_CREAT | O_TRUNC , 0600);
				if(fd[1] == -1) {
					printf("ERROR WRITING2\n");
				}
				dup2(fd[1],1);
				close(fd[1]);
			}
			// runs the new program
			if(execvp(parsed[0], parsed) == -1) {
				printf("error executing\n");
				return;
			}
		}else {
			waitpid(pid, &state, WUNTRACED);
		}
		return;
}
// parsed the string that uses pipe
int parsePipe(char *args, char **parsed) 
{

	
	int flag = 0;
	for(int i = 0; i < 2; i++) {
		parsed[i] = strsep(&args, "|");
		if(parsed[i] == NULL)
			break;
	}
	//no PIPE found
	if(parsed[1] == NULL){

		flag = 0;
	}else
		flag = 1;
	
	return flag;
	
}
// makes the user input some string
void userInput(char *args) 
{

	//args = (char*)malloc((1024*sizeof(char)));
	fgets(args,1024,stdin);

	for(int i = 0; i < 1024; i++) {
		if(args[i] == '\n'){
			args[i] = '\0';
			break;
		}
		
	}
	
	
}

// uses my builtin commands
int no_PipeCommands(char *args, char **parsed) 
{
	int flag = 1;
	char *tmp = strdup(args);
	for(int i = 0; i < 100;i++) {
		parsed[i] = strsep(&args, " ");
		if(parsed[i] == NULL) 
			break;
		if(strlen(parsed[i]) == 0)
			i--;
	}
	//check if one of them is true and returns 1;
	if(strcmp(parsed[0], "help") == 0) {
		help();
	}else if(strcmp(parsed[0], "cd") == 0) {
		cd(parsed[1]);
	}else if(strcmp(parsed[0], "echo") == 0) {
		echo(tmp);
	}else if(strcmp(parsed[0], "dir") == 0){
		parsed[0] = "ls";
		No_Pipe_execute(parsed);
		//dir();
	}else if(strcmp(parsed[0], "quit") == 0) {
		quit();
	}else if(strcmp(parsed[0], "clr") == 0) {
		clr();
	}else if(strcmp(parsed[0], "pause") == 0){
		pause_P();
	}else if (strcmp(parsed[0], "environ") == 0) {
		environ();
	}else {
		//means none is true and returns 0
		flag = 0;
	}
	return flag;
}
// clears are the shell
void clr(char *args) 
{
	printf("\033[H\033[J");
}
// pauses and wait for the user to hit enter
void pause_P() 
{
	char word = getchar();
	while(word != '\n') {
		word = getchar();
	}
	
}
// prints out all the files in that directory
void dir() 
{
	DIR *dfd = NULL;
	//char *direc;
	struct dirent *dp;
	
	char curr_dir[1024];
	
	getcwd(curr_dir, 1024);
	
	
	if(curr_dir== NULL) {
		printf("Could not find the current directory\n");
	}
	
	dfd = opendir(curr_dir);
	
	if(dfd == NULL) {
		printf("Count not open this working directory\n");
	}
	int count = 0;
	printf(ANSI_COLOR_GREEN);
	for(count = 0; NULL != (dp = readdir(dfd));count++) {
		if(dp->d_name[0] != '.')
	
		printf("%s ", dp->d_name);
	}
	printf(ANSI_COLOR_RESET);
	
	printf("\n");
	
}
// prints out everything after echo
void echo(char *args)
{
	if(args == NULL) {
		printf("\n");
	}
	for(int i = 0; i < strlen(args); i++) {
		if(args[i] == '(' || args[i] == ')') {
			printf("-bash: syntax error near unexpected token %c `newline'\n", args[i]);
			
			}
	}
	puts(strchr(args, ' ') +1);
}
// used to change directory
void cd(char *argv) 
{
	if(argv == NULL) {
		chdir("/home/sarthak26");
		return;
	}
	
	char *direc;
	char cwd[1024];
	// gets the current directory
	getcwd(cwd,1024);

	//malloc the stored chars
	direc = malloc(sizeof(direc));
	// copies pwd to direc
	direc = strcpy(direc, cwd);
	
	// adds / to the for the current format
	
	direc = strcat(direc, "/");
	// // adds the input from the user to the current directory
	
	direc = strcat(direc, argv);
	
	// changes the current directory
	chdir(direc);
	
	if(chdir(direc) == -1) {
		printf("cannot find the file\n");
		//return -1;
	}
	//return 0;
}

void help() 
{
	puts("\n***WELCOME TO MY SHELL HELP*** 			Use the shell at your own risk..."
		"\nCopyright @ Sarthak Patel			List of Commands supported:"
		"\n>pwd [-LP]					>exit [n]"
		"\n>pipe handling [|]				>help [-dms] [pattern ...]"
		"\n>ls						>cd [-L|[-P [-e]] [-@]] [dir]"
        "\n>echo [-neE] [arg ...] 	                        I/O redirection '>' and '<'");
}

// uses to see the prompt
void prompt() 
{
	/* gets the users name and the current directory */
	char *user = getenv("USER");
	char cwd[1024];
	getcwd(cwd,sizeof(cwd));
	
	/* prints the exact prompt shown in ubuntu with color */
	printf(ANSI_COLOR_GREEN);
	printf("%s@LAPTOP-S8MRRVMF:", user);
	
	printf(ANSI_COLOR_BLUE);
	printf("%s", cwd);
	printf(ANSI_COLOR_RESET);
	printf("$ ");
	/* forces a write of all user-space buffered data for the given output or update and flushes it */
	fflush(stdout);
	
}
// quits my shell
void quit() 
{
	printf("Program will be quiting...\n");
	sleep(1);
	exit(0);
}