#include<stdio.h>
#include<string.h>
int main(){
	char str[100000], substr[100000];	
	int length, max;
	length = max = 0;
	scanf("%s%s", &str, &substr);		
	length = strlen(str);				
	for(int i=0; i<length; i++){
		if(str[i]>str[max])				
			max = i;					
	}
	for(int i=0; i<=max; i++) 
		printf("%c", str[i]);		
	printf("%s", substr);				
	for(int i=max+1; i<length; i++)
		printf("%c", str[i]);			
	printf("\n");	
	return 0;	
}