#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

void write_file(int countWord, char *output, char *file_name);
char *read_file(int countWord, char *clear_file);
void make_rand_key(int len, char *key);
void encrypt(char *clear_file, char *key_file, char *cipher_file );
void decrypt(char *key_file, char *cipher_file, char *message_file);


int main() {
	int i =0;
	
	
	
	do{
		int n;
		
		printf("choice 1 = encrypt\nchoice 2 = decrypt\nchoice 3 = exit\n");
		printf("Enter a choice: ");
		scanf(" %d", &n);
		
		switch(n) {
		case 1 :
		encrypt("clear.txt", "key.txt","cipher.txt");
			break;
		case 2:
		decrypt("key.txt", "cipher.txt", "message.txt");
			break;
		case 3:
			exit(1);
		default :
			printf("Please enter a valid choice.\n");
		}
	
	}while(i != 3);
	

}
	
char *read_file(int countWord, char *file_name) {
	// Open file
	FILE *file = fopen(file_name, "r");
	
	// Check that file opened
	if(file!= NULL) { 
		
		// If countWord is zero get length from file
		if(countWord==0) {
			while(getc(file) != EOF) 
					countWord++;
			rewind(file);
			}// end if
			
	
					
	// Allocate memory for countWord + 1 bytes	
	char *line = (char*) malloc(countWord); 
			
	// Read countWord bytes from file with for loop
	int i;
		for(i = 0; i < countWord; i++) 
				line[i] = getc(file);
		
		line[i] = '\0';
	
	// Close file	
		fclose(file);
	
	// Return line
		return line;
			
		
	
	}// end outer if
	else {
			printf("FILE NOT FOUND SORRY\n");
			exit(2);
		}// end else
	
}

void write_file(int countWord, char *output, char *file_name) {
	
	// Open file
	FILE *file = fopen(file_name, "w");
		// If countWord is not zero write countWord bytes to file with for loop
	if(countWord != 0) {
		for(int z = 0; z < countWord; z++) 
			putc(output[z], file);
			
	} // end if
	
	// Else write to file until null char in output
	else {
			int i = 0;
			while(output[i] = '\0' ) 
				putc(output[i++], file);
			
		}// end else
			
		// Close file_name
	fclose(file);
	
}

void make_rand_key(int len, char *key) {
	srand(time(NULL));
	
	for(int n = 0; n < len; n++) {
		int randomValue = (rand()%256);
		key[n] = (char)randomValue;
			if(key[n] == -1) 
				key[n] = -2;
			else if(key[n] == 0)
				key[n] = 1;
			
	}
	key[len] = '\0';
	return;
	
}


void encrypt(char *clear_file, char *key_file, char *cipher_file ) {
	
	
	char *input = read_file(0, clear_file);
	
	
	int len = strlen(input);

	char key[len+1];
	make_rand_key(len, key);
	
	write_file(len, key ,key_file);
	
	int i = 0;
	char output[len+1];
	
		for(i = 0; i < len; i++) 
			output[i] = input[i]^key[i];
		output[i] = '\0';
		
	write_file(len, output ,cipher_file);
		
}


void decrypt(char *key_file, char *cipher_file,char *message_file) {
	
	char *key = read_file(0, key_file);
	int len = strlen(key);
	char *cipher = read_file(len, cipher_file);
	int i = 0;
	
	
	char message[len +1];
		for(i = 0; i < len; i++) 
			message[i] = key[i]^cipher[i];
		message[i] = '\0';
		
	write_file(len, message, message_file);
	
}

