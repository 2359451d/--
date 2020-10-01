#include <stdio.h>

int main(void)
{
    // char month[][10] = {"January", "February", "March","Apiral", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    // // char *p = month;
    // printf("please type:");
    // int input;
    // scanf("%d", &input);
    // printf("%s",month[input-1]);
    char *a [ ] = {"China","French","America","German"};
    printf("%p==?%p\n",a,&a[0]);
    printf("%p %p %p %p\n",a[0],a[1],a[2],a[3]); 
    printf("%s %s %s %s\n",a[0],a[1],a[2],a[3]);
}
