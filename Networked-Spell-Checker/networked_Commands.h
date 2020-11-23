#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <unistd.h>
#include <netinet/in.h>
#include <errno.h>
#include <arpa/inet.h>
#include <string.h>
#include <netdb.h>
#include <sys/wait.h>

#ifndef networked_Commands


#define DEFAULT_DICTIONARY "dictionary.txt"
#define DEFAULT_PORT 9000
#define LENGTH_OF_DIC 100000
#define NUM_THREADS 2
#define MAX_LEN 100

typedef struct {
	
	int *buf;
	int size;
	int front;
	int rear;
	int max_size;
	pthread_mutex_t mutex;
	pthread_cond_t cv_full;
	pthread_cond_t cv_empty;
}queue;


typedef struct {
	char **buf;
	int rear;
	int front;
	int size;
	int max_size;
	unsigned long tid[NUM_THREADS+1];
	pthread_mutex_t mutex;
	pthread_cond_t cv_full;
	pthread_cond_t cv_empty;
}txtQueue;

typedef struct {
	queue *q;
	txtQueue *text;
	char *dict;
}thread_argument;

char *pop(txtQueue *q);
void push(txtQueue *q, char *word); 
int error(char *msg);
char *read_file(char *filename);
int check_spell(char *word, char *dict);
int listen_sock(int port);
int call_port(char **argc);
void write_file(char *args, int p);
char *read_socket(int client_socket, char *word);
void *thread_execution(void *dict_file);
void init_queue(queue *q, int s);
void *put_inFile(void *args);
int dequeue(queue *q);
void enqueue(queue *q, int key);
void q_deinit(queue *q);
void init_txtQueue(txtQueue *q, int c);
void write_toFile(txtQueue *log);
#endif
