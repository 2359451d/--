#include<stdio.h>
#include<ctype.h>

int main(int argc, char const *argv[])
{
    char word[256];
    int wordCount=0;

    // while ((read = getchar())!=EOF)
    // {
    //     if (! isalpha(read))
    //     {
    //         wordCount++;        
    //     }
    // }
    while ((scanf("%s", word))!= -1)
    {
        wordCount++;
    }
    
    printf("Word count: %d", wordCount);
    return 0;
}
