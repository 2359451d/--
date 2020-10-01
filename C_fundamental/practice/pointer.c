#include <stdio.h>
#include <stdlib.h>

int main()
{
    void *p =0;
    int cnt = 0;
    while ((p=malloc(1024*1024*100))!=0)
    {
        // 分配成功
        cnt++;
    }
    printf("total allocate: %d00MB\n",cnt);
    
    return 0;
}