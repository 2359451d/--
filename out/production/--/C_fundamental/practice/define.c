/* 
    定义宏
    预处理阶段：将所有头文件替换/添加成文本

    预先定义好的宏
    __LINE__ line number
    __FILE__ 
    __DATE__
    __TIME__
    __STDC__

 */
// const double PI = 3.1415926;
#include<stdio.h>
#define PI 3.1415926 //宏，预处理
#define PRT printf("%f\n",PI); \ 
            printf("%f\n", PI)// '\'代表该定义未结束, 注意想象一下【替换后分号结构】
#define _DEBUG //没有值的宏macros，用于条件编译，检查该宏是否被定义

//带参数的宏: 一切参数都要用到括号，不然实际值和预期值可能不同
#define cube(x) ((x)*(x)*(x))
#define time2(x) ((x)*2)

int main(int argc, char const *argv[])
{
    // printf("%f", PI);
    printf("cube(5):%d \n",cube(5));
    printf("time2(x)=%d \n", time2(2));
    PRT;
    printf("%s: %d\n", __FILE__,__LINE__);
    printf("%s: %s\n", __DATE__,__TIME__);
    return 0;
}
