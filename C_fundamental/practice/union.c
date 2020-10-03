/* 
    联合union：
    所有成员占用同一份空间
 */

#include<stdio.h>

union MyUnion
{
    int i;
    char c;
} u1, u2;

u1.i =4;
u2.c ='a';
u2.i =4;

