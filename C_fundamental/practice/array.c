#include "array.h"

// 扩容因子
const int BLOCK_SIZE=20;


Array array_create(int int_size)
{
    Array a;
    a.size = int_size;
    a.array = (int*)malloc(sizeof(int)*a.size);//array pointer
    // a = {int_size, malloc(sizeof(int)*a.size)};
    // a = {.size=int_size};
    return a;
}

/* 
返回指针可能存在风险,并且复杂
*Array array_create(Array* a, int init_size)
{
    a==NULL?风险
    a already exist?需要free，风险

    a->size=init_size:
    a->array=...
    return a;
}
 */

void array_free(Array *a){
    free(a->array);
    a->array = NULL;
    a->size = 0;
}

//模拟封装，private
//保护内部细节
int array_size(const Array *a){
    return a->size;
}

//扩容
void array_inflate(Array *a, int more_size){
    int *p = (int*)malloc(sizeof(int)*(a->size+more_size));
    int i;
    for ( i = 0; i < a->size; i++)
    {
        p[i] = a->array[i];
    }
    free(a->array);
    a->array = p;
    a->size += more_size;
}

int* array_at(Array *a, int index){
    // index out of the bounds
    if (index>=a->size)
    {
        //扩容因子，提升效率?
        array_inflate(a, ((index/BLOCK_SIZE+1)*BLOCK_SIZE)-a->size);
    }
    return &(a->array[index]);
}

int array_get(const Array *a, int index){
    return a->array[index];
}

void array_set(Array *a, int index, int value){
    a->array[index] = value;
}



int main(int argc, char const *argv[])
{
    Array a = array_create(100);
    printf("%d\n", array_size(&a));
    
    *(array_at(&a, 0)) = 100;
    printf("return the value store in the 0 index: %d\n", *(array_at(&a, 0)));
    
    array_free(&a);
    return 0;
}
