/* 枚举
    用户定义的[数据类型]
    声明语法:
    enum type {name1,name2,name3,...};
    枚举类中每个name实质是int常量,可以直接IO
    类似java
    enum class_name {NAME1,NAME2,NAME3};
 */
#include <stdio.h>

enum color{RED,BLUE,ORANGE, COUNT};//last index可以作为个数,用于创建array等

int main(int argc, char const *argv[])
{
    enum color t =  RED;
    printf("RED=%lu\n", RED);
    return 0;
}
