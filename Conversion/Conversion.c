/*

    Number converter

    Menu
    Convert between integer, binary, octal and hexadecimal

    This program should accept numeric values in hexadecimal,
    decimal, octal and binary formats as:

        Hex 0x0 to 0xFFFFFFFF
        Dec 0 to 4294967295
        Oct o0 to o37777777777
        Bin b0 to b11111111111111111111111111111111

    After a value is input the code in main will interpret the
    data types above an process the conversion to an unsigned
    int.  The unsigned int will be used to convert the input to
    strings containing hexadecimal, octal and binary strings.

*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h>


//int input_to_decimal(char *input);
unsigned int bin_to_uint(char *input);
unsigned int oct_to_uint(char *input);
unsigned int hex_to_uint(char *input);
unsigned int dec_to_uint(char *input);
void uint_to_hex(unsigned int n, char *output);
void uint_to_oct(unsigned int n, char *output);
void uint_to_bin(unsigned int n, char *output);


int error = 0;


int main(){

    char input[50];
    unsigned int n = 0;
    char output[50];

    // Write code here to test your functions
	
    // Uncomment code below when done

   printf("Enter a binary, octal, decimal or hexadecimal number\n");
    printf("convert > ");
    gets(input);

    // Detect input data type
    // Hexadecimal
    if(input[0] == '0' && input[1] == 'x'){
        n = hex_to_uint(input);
    }
    // Decimal
    else if(input[0] >= '0' && input[0] <= '9'){
        n = dec_to_uint(input);
    }
    // Octal
    else if(input[0] == 'o'){
        n = oct_to_uint(input);
    }
    // Binary
    else if(input[0] == 'b'){
        n = bin_to_uint(input);
    }
    // Unknown
    else{
        printf("ERROR: Unknown data type: %s\n", input);
    }

    // Print results
    printf("The decimal value of %s is %u\n", input, n);
    uint_to_hex(n, output);
    printf("The hexadecimal value of %s is %s\n", input, output);
    uint_to_oct(n, output);
    printf("The octal value of %s is %s\n", input, output);
    uint_to_bin(n, output);
    printf("The binary value of %s is %s\n", input, output);


    return 0;
}


/*
    This function converts the value part of the hex
    string to an unsigned integer value.  The first
    two chars are 0x, which tells that the string is
    in hex.  Start processing the value at index 2 until
    the null, calculating the int value as you would on
    paper.  Try on paper first.
*/
// Convert a hexadecimal char array to uint

unsigned int hex_to_uint(char *input){
    // Declare result and set to zero
    unsigned int res = 0;
    // Declare and set multiplier to 1
	int multi = 1;
	int len = strlen(input)-1;
	
		
			// Loop through value part of input string
			for(int p = len; p >=2; --p) {
				
				//printf("%c", input[p]);
				if(input[p] >= '0' && input[p] <= '9') {
				
				
					res = res + (input[p] - '0')*multi;
					
				} 
				// If between A and F add 10 to 15 to res with multiplier
				else if(input[p] >= 'A' && input[p] <= 'F') {
					res = res + (input[p] -'A' + 10)*multi;
        
				}
				else {
					printf("NOT A HEX");
					exit(1);
				
				// Advance multiplier to next position value
				
			
				}
				multi*=16;
			}
  
	return res;
}

/*
    Copy hex_to_uint() and modify for decimal input.
*/
// Convert a unsigned integer char array to uint
unsigned int dec_to_uint(char *input){
    // Declare result and set to zero
	
	 unsigned int res = 0;
	 int multi = 1;
	int len = strlen(input)-1;
	
		
			// Loop through value part of input string
			for(int p = len; p >=0; --p) {
				
				if(input[p] >= '0' && input[p] <= '9') {
				
				//printf("this is %c", input[p]);
					res = res + (input[p]-48)*multi;
					
				}			
				// Advance multiplier to next position value
				multi*=10;
			
			
			}
   
	
    return res;
}

/*
    Copy dec_to_uint() and modify for octal input.
*/
// Convert a octal char array to uint
unsigned int oct_to_uint(char *input){
    // Declare result and set to zero
    unsigned int res = 0;
	int multi = 1;
	int len = strlen(input)-1;
	
		
			// Loop through value part of input string
			for(int p = len; p >=0; p--) {
				
				if(input[p] >= '0' && input[p] <= '7') {
				
				
					res = res + (input[p]-48)*multi;
					
				}			
				// Advance multiplier to next position value
				multi*=8;
			
			
			}
			
    return res;
}


/*
    Copy oct_to_uint() and modify for binary input.
*/
// Convert a binary char array to unsigned int
unsigned int bin_to_uint(char *input){
    // Declare result and set to zero
    unsigned int res = 0;
	
    int multi = 1;
	int len = strlen(input)-1;
	
		
			// Loop through value part of input string
			for(int p = len; p >=1; p--) {
				
				if(input[p] == '0' || input[p] == '1') {
				
				
					res = res + (input[p]-48)*multi;
					
				}			
				// Advance multiplier to next position value
				multi*=2;
			
			
			}
			
    return res;
}


/*
    This function converts from unsigned int to a hex
    char array.  Try this on paper before coding.
*/
// Convert a unsigned integer char array to hexadecimal
void uint_to_hex(unsigned int n, char *output){
    // Declare a uint for remainder
	unsigned int rem = 0;
	int quotient = n;
    // Declare an int for division
	int div = 16;
	int p = 0;
	 
    char buffer[100];
	
	
   
	//output[len] = '\0';
	// Use a loop to generate a hex string - string will be reverse
		while(n > 0 ) {
			
			quotient = quotient / div;
			rem = n % div;
			n = quotient;
			
		if(rem >= 0 && rem <= 9) {
			buffer[p] = rem+ '0';
		}
		if(rem >= 10 && rem <= 15 ) {
			buffer[p] = (char)(55 + rem);
		}
			p++;
		
			// Loop through value part of input string
			
		
		
	}
	
	buffer[p] = '\0';
	//printf("%s\n", buffer);
	strcpy(output, "0x");
	
	int i;
	int len = strlen(buffer);
	for(i = 0; i < len; i++) {
		output[i+2] = buffer[len-i-1];
		
		
	}
	output[i+2] = '\0';
	
    // Get last hex char

    // Put null at end of buffer
		
    // Copy 0x to output string

    // Copy chars from buffer in reverse order to output string

    return;
	

}

/*
    Copy uint_to_hex() and modify for octal
*/
// Convert a unsigned integer char array to octal
void uint_to_oct(unsigned int n, char *output){
	unsigned int rem = 0;
	int quotient = n;;
    // Declare an int for division
	int div = 8;
	int p = 0;
	 char buffer[100];
	
	
	while(n > 0 ) {
			
			quotient = quotient / div;
			rem = n % div;
			n = quotient;
			
		if(rem >= 0 && rem <= 7) {
			buffer[p] = (char)(rem + '0');
		}
		
			p++;
		

    
	}
	buffer[p] = '\0';
	
	
	strcpy(output, "o");
	
	int i;
	int len = strlen(buffer)-1;
	int length = len;
	for(i = 1; i <= length+1; i++) {
		output[i] = buffer[len];
		
		len--;
	}
	output[i+1] = '\0';
			return;
}


/*
    Copy uint_to_oct() and modify for binary
*/
// Convert a unsigned integer char array to binary
void uint_to_bin(unsigned int n, char *output){
	unsigned int rem = 0;
	int quotient = n;
    // Declare an int for division
	int div = 2;
	
	int p = 0;
	char buffer[100];
	
	while(n > 0 ) {
			
			quotient = quotient / div;
			rem = n % div;
			n = quotient;
			
		if(rem == 0 || rem == 1) {
			buffer[p] = (char)(rem + '0');
		}
		
			p++;

    
	}
	buffer[p] = '\0';
	//printf("%s\n", buffer);
	strcpy(output, "b");
	
	int i;
	int len = strlen(buffer);
	
	int length = len;
	for(i = 0; i < len; i++) {
		output[i+1] = buffer[len-i-1];
		
		
	}
	output[i+1] = '\0';
			return;
}

