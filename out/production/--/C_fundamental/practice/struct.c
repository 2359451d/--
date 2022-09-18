#include <stdio.h>

struct date{
    int month;
    int day;
    int year;
};

struct date today;//today是date结构类型,里面有如上成员

struct {
    // [无名结构],date1,date2,里面都有以下成员
    int month;
    int day;
    int year;
} date1, date2;

struct toDays
{
    int month;
    int day;
    int year;
} date3,date4;//date3,4都是toDays结构类型,里面有如上成员

struct point
{
    int x,y,z;
};

int main(int argc, char const *argv[])
{
    
    //初始化1
    today.month=7;
    today.day=12;
    today.year=2020;
    printf("today=%lu/%lu/%lu\n", today.month,today.day,today.year);

    //初始化2
    date3 = {7,12,2020};
    printf("date3=%lu/%lu/%lu\n",date3.month,date3.day,date3.year);
    //初始化3
    date4 = {.month=7, .day=12, .year=2020};
    printf("date4=%lu/%lu/%lu\n",date4.month,date4.day,date4.year);

    //结构强转
    struct point now;
    now = (struct date){3,10,2020};
    printf("now=%lu/%lu/%lu\n",now.month,now.day,now.year);

    //结构类型互相赋值,只是获得另外一个结构的所有成员值
    //堆中地址不同
    return 0;
}
