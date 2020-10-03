#include <stdio.h>
#include <string.h>

int main(void)
{
    char line[] = "Hello World";
    char line2[] = "Hello World2";
    // 获取字符串长度（不包括结束标志符）
    printf("strlen=%lu\n", strlen(line));
    // printf("sizeof=%lu\n", sizeof(line));

    // 比较字符串,返回差值
    printf("strcmp=%lu\n", strcmp(line,line2));
    return 0;

    // 拷贝字符串，malloc动态分配内存
    char *dst = (char *)malloc(strlen(line)+1);//相当于const char * dst, 指针存放内容不允许修改. strlen +1 正好存放最后'\0'
    strcpy(dst,line);
    free(dst);//释放内存

    // 字符串拼接:strcat(str1,str2_，拷贝的变种,str1必须有足够空间
    /* 
    安全版本：(多了参数size, 避免了越界问题)
    strncpy(...,size)
    strncat(...,size)
    strncmp(...,size)
    */

    // 查找字符串
    // char* strchr
    // char* strrchr: search from the right hand side

    // 字符串中找字符串
    // char* strstr(const char *s1, const char *s2)
    // char* strcasestr(const char *s1, const char *s2): ignore case sensitive
    
}