#include<stdio.h>
#include<string.h>

int readline(char line[], int max);
int writeline(const char line[]);

int main(int argc, char const *argv[])
{
    char input[256];
    // printf("please type sth: ");
    // scanf("%s", &input);
    readline(input,256);
    writeline(input);
    return 0;
}


int readline(char line[], int max){
    // read成功，返回缓冲区数组地址
    // read失败或EOF，返回NULL
    if (fgets(line, max, stdin) ==NULL)
        return 0;
    else
        return strlen(line);
}

int writeline(const char line[]){
    fputs(line, stdout);
    return strlen(line);
}
