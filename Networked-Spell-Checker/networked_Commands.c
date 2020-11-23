#include "networked_Commands.h"

int error(char *msg) 
{
	perror(msg);
	return -1;
	
}

char *read_file(char *filename) 
{
	printf("The Server is starting... \n");
	FILE *file = fopen(filename,"r");
	if(file == NULL) {
		error("ERROR reading from the file\n");
	}
	int count = 0;
	while(getc(file) != EOF)
		count++;
	
	rewind(file);
	//printf("count is %d\n\n\n",count);
	char *line = (char*)malloc(count);
	int i;
	
	for(i = 0;i < count;i++) {
		line[i] = getc(file);
	}
	line[i] = '\0';
	
	fclose(file);
	
	return line;
}

void write_toFile(txtQueue *log)
{
	char *letter;
	FILE *file = fopen("out.txt","w");
	if(file == NULL) {
		error("file cannot be written\n");
	}
	
	while(1) 
	{
		while(log->size !=0)
		{
			letter = pop(log);
			fputs(letter,file);
			fputs("\r\n",file);
			fflush(file);
		}
	
	}
	fclose(file);
	free(letter);
	
	
}
char *read_socket(int client_socket, char *word) 
{
	int n;
	if((n = read(client_socket, word, sizeof(word)*3)) < 0) {
		error("Error Reading");
	}
	if(n == 0) {
		close(client_socket);
		return NULL;
		
	}
	
		for(int i = 0; i < 1024; i++) {
			if(word[i] == '\n'){
				word[i] = '\0';
				break;
			}
		}
	
	return word;
}

int check_spell(char *word, char *dict) 
{
	int flag = 0;
		if(strstr(dict,word) != 0) {
			return 1;
		}else {
			return 0;
		}
	return flag;
}


int listen_sock(int port) 
{
	int server_socket;
	int opt = 1;
	server_socket = socket(AF_INET, SOCK_STREAM, 0);
	
	if(server_socket < 0) {
		error("Cannot use Socket\n");
		return -1;
	}
	if (setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR,(const void *)&opt , sizeof(opt)) < 0)
        return -1;
	
	
	struct sockaddr_in server_address;
	server_address.sin_family = AF_INET;
	server_address.sin_port = htons(port);
	server_address.sin_addr.s_addr = htonl(INADDR_ANY);
	
	if(bind(server_socket, (struct sockaddr*) &server_address, sizeof(server_address)) < 0) {
		error("ERROR BINDING\n");
		return -1;
	}
	
	if(listen(server_socket, 1) < 0) {
		error("CANNOT LISTEN\n");
		return -1;
	}
	//if returned 1 then listening was successful else not
	return server_socket;
	

	
}
int call_port(char **argc)
{
	int port;
	if(argc[1] == NULL) {
		port = DEFAULT_PORT;
	}else {
		port = atoi(argc[1]);
	}
	
	return port;
}



void init_queue(queue *q, int s)
{
	q->buf = malloc(sizeof(int*)*s);
    q->size = 0;
    q->front = 0;
    q->rear = s-1;
	q->max_size = s;
	
	pthread_mutex_init(&q->mutex, NULL);
	pthread_cond_init(&q->cv_empty , NULL);
	pthread_cond_init(&q->cv_full , NULL);
	
}
int dequeue(queue *q)
{
	int item;
	pthread_mutex_lock(&q->mutex);
	if(q->size == 0) 
	{
		pthread_cond_wait(&q->cv_empty, &q->mutex);
	}
	
	item = q->buf[q->front];
	q->front = (q->front +1) % (q->max_size);
	q->size--;
	
	pthread_cond_signal(&q->cv_full);
	pthread_mutex_unlock(&q->mutex);
	
	return item;

}
void enqueue(queue *q, int key)
{
	pthread_mutex_lock(&q->mutex);
	while(q->size == q->max_size){
		pthread_cond_wait(&q->cv_full , &q->mutex);
	}
	q->rear = (q->rear + 1) % (q->max_size);
	q->buf[q->rear] = key;
	q->size++;
	
	pthread_cond_signal(&q->cv_empty);
	pthread_mutex_unlock(&q->mutex);
}
void q_deinit(queue *q) 
{
	free(q->buf);
	
}

void init_txtQueue(txtQueue *q, int c) 
{
	q->buf = malloc(sizeof(char*)*c);
	q->size = 0;
	q->max_size = c;
	q->front = 0;
	q->rear = c - 1;
	pthread_mutex_init(&q->mutex, NULL);
	pthread_cond_init(&q->cv_empty , NULL);
	pthread_cond_init(&q->cv_full , NULL);
	
}

void push(txtQueue *q, char *word) 
{
	pthread_mutex_lock(&q->mutex);
	
	while(q->size == q->max_size) {
		pthread_cond_wait(&q->cv_full, &q->mutex);
	}
	q->rear = (q->rear +1) % (q->max_size);
	q->buf[q->rear] = word;
	q->size++;
	
	pthread_cond_signal(&q->cv_empty);
	pthread_mutex_unlock(&q->mutex);



}

char *pop(txtQueue *q) 
{
	char *item;
	
	if((item = malloc(strlen(q->buf[q->front])*sizeof(char*)+1)) == NULL) {
		error("Cannot allocate");
	}
	pthread_mutex_lock(&q->mutex);
	while(q->size == 0)
		pthread_cond_wait(&q->cv_empty,&q->mutex);
	
	item = q->buf[q->front];
	q->front = (q->front+1) % (q->max_size);
	q->size--;
	
	pthread_cond_signal(&q->cv_full);
	pthread_mutex_unlock(&q->mutex);

	return item;
}	






























