#include<stdio.h>
#include<string.h>
#include<stdlib.h>

char* strcat2(char * str1, char * str2);

int main(int argc, char const *argv[])
{
    char str1[] = "Here ";
    char str2[] = "Hello World";
    char * str1_p = str1;
    char * str2_p = str2;
    char * result = strcat2(str1_p, str2_p);
    printf("%s\n", result);
    return 0;
}

char* strcat2(char * str1, char * str2){
    /* 
    拷贝变种，从str1末尾开始将str2拷贝完毕 */
    int len = strlen(str1) + strlen(str2) + 1;
    // printf("len=%lu\n",len);

    // 新指针分配足够空间，len1+len2+1
    char* extended = (char*) malloc( (len) * sizeof(char));

    char* start = extended;
    while (*str1)
    {
        *extended++ = *str1++;
    }
    
    while (*str2)
    {
        *extended++ = *str2++;
    }

    *extended = '\0';
    return start;

};