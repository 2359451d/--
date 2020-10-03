/* 
自定义数据类型: typedef

 - 声明一个已有数据类型的新名字
 - 提高可读性
 - 新名字是某种数据类型的别名

 typedef ... othername：中间所有的东西都是一种数据类型
 */

#include <iostream>

typedef int Length;

typedef struct Date {
    int month;
    int day;
    int year;
} Time; // 原来类型 Date, 可以重载，Time类型就是Date类型

Date time = {1,2,3030};
Time time = {1,2,3030};

