#include <stdio.h>
#include <string.h>
/*
 * count lines on stdin and print results on stdout
 * counts last line even if it is not terminated by a newline character
 */

int readline( char line[], int max);

int main()
{
	char input[256];
	int readCount;
	int lineCount=0;

	while ((readCount=readline(input, 256)) !=0){
			// when return 0, read fail or EOF
			// check the last element read, ?= /n
			if (input[readCount-1]=='\n'){
					// newline
					lineCount++;
			}
	}
	printf("line counts: %d\n", lineCount);
	return 0;
}

int readline(char line[], int max){
        if ((fgets(line, max, stdin)) !=NULL){
                return strlen(line);
        }else{
                return 0;
        }
}
