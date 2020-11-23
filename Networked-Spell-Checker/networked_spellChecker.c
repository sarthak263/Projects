#include "networked_Commands.h"

int main(int argv, char *argc[]) 
{	
	// initiating all the necessary variables
	int client_socket = 1;
	struct sockaddr_in client_address;
	pthread_t tid[NUM_THREADS+1];
	int port;
	thread_argument thread_args;
	char *dict_file;
	char *file_name;
	int server_socket;
	char client_name[MAX_LEN];
	char client_port[MAX_LEN];
	queue q;
	init_queue(&q, 3);
	txtQueue log;
	socklen_t client_size;
	init_txtQueue(&log, 1024);
	char server_message[13] = "Enter a word\n";
	
	
	if(argv > 2 ) {
		file_name = argc[2];
	}else {
		file_name = DEFAULT_DICTIONARY;
	}
	printf("file name is %s\n",file_name);
	
	dict_file  = read_file(file_name);
	//if null then printing out error
	if(dict_file == NULL) {
		error("Cannot load file\n");
	}
	// initializing the port
	port = call_port(argc);
	//listening from the socket
	server_socket = listen_sock(port);
	
	thread_args.q = &q;
	thread_args.dict = dict_file;
	thread_args.text = &log;
	
	int i;
	for(i = 0; i < NUM_THREADS ;i++) 
	{
		//creating thread equal to the NUM_THREADS
		if(pthread_create(&tid[i], NULL, thread_execution, &thread_args) != 0) 
			error("CANNOT CREATE THREAD");
		
		printf("Thread is created %lu\n",tid[i]);
	}
	
	if(pthread_create(&tid[i], NULL, put_inFile, &log) != 0) 
			error("CANNOT CREATE THREAD");
		
	i = 0;
	while(1) 
	{
		//
		client_size = sizeof(client_address);
		client_socket = accept(server_socket,(struct sockaddr*) &client_address
		,&client_size);
		
			
		//printf("Client %lu is connected\n",tid[i]);

		if(client_socket < 0) {
			error("Error accepting...\n");
		}
		
		if(getnameinfo((struct sockaddr*) &client_address, sizeof(client_address),
		 client_name, MAX_LEN, client_port, MAX_LEN, 0) != 0) {
			error("failed getting the name of the client");
		}
		printf("CLIENT %s connected\n", client_port);

		if(send(client_socket, server_message, sizeof(server_message), 0 )< 0) {
			error("error sending message\n");
		}
		
		enqueue(&q, client_socket);
		i++;

	}
	
	
	for(i = 0 ; i < NUM_THREADS ; i++){
		if(pthread_join(tid[i] , NULL) != 0){   //join threads
			error("Threads cannot be joined");
		}
	}
	free(dict_file);
	
		
}


void *thread_execution(void *args) 
{
	char word[1024];
	char *input;
	char result[1024];
	int client_socket;
	pthread_detach(pthread_self());
	thread_argument *argument = args;
	queue *queue = argument->q;
	char *dict_file = argument->dict;
	txtQueue *log = argument->text;
	int p;
	while(1) 
	{
		
		client_socket = dequeue(queue);
		while((input = read_socket(client_socket, word)) != NULL) {
			memset(result, 0, sizeof(result));
			if(strncmp(input, "^",1) == 0) {
				close(client_socket);
				break;
				
				
			}else {
				p = check_spell(input, dict_file);
				
				if(p==1) {
					strncpy(result,input,strlen(input)-1);
					strcat(result, " - found\n");
					printf("%s", result);
					write(client_socket,result, sizeof(result));
					push(log, result);
				}else {
					strncpy(result,input,strlen(input)-1);
					strcat(result, " - misspelled\n");
					printf("%s", result);
					write(client_socket,result, sizeof(result));
					push(log, result);
				}
				
				//write_file(input, p);
			}
		}
			
	}
}

void *put_inFile(void *args)
{
	txtQueue *log = args;
	write_toFile(log);
	
	return 0;
	
}

