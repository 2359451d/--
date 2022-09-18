#include<stdio.h>
#include<string.h>

int main(int argc, char const *argv[])
{

    char input[256];
    char* input_p= input;

    printf("Please type: ");
    scanf("%[^\n]",&input);
    printf("%p %p\n",input,&input);

    printf("input length: %d\n", strlen(input_p));
    printf("%s\n", input_p);
    return 0;
}
