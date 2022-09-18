#ifndef _ARRAY_H_
#define _ARRAY_H_

// 为类型取一个新名字
typedef struct
{
    int * array;//int* 指针
    int size;
} Array; // 也可以定义成*Array，缺点：其他函数中可能无法创建局部变量Array（因为是指针，会改变外部值）

Array array_create(int init_size);
void array_free(Array *a);
int array_size(const Array *a);
int* array_at(Array *a, int index);
void array_inflate(Array *a, int more_size);

#endif