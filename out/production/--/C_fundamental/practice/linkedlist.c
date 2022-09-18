#include "node.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct _list
{
    Node* head;
    Node* tail;
} List;

#define BOOL int
#define TRUE 1
#define FALSE 0

void add(List* pList, int value);
void print(List* pList);
BOOL get(List* pList, int value);
void delete(List* pList, int value);


int main(int argc, char const *argv[])
{
    List list;
    list.head=list.tail=NULL;
    int number;
    //decide the head pointer
    do
    {
        scanf("%d",&number);
        if (number!=-1)
        {
            add(&list, number);
        }
    } while (number!=-1);
    return 0;
}

void add(List* pList, int value){
    // new pointer add to the linked list
    Node *p = (Node*)malloc(sizeof(Node));
    p->value =value;
    p->next = NULL;//initial it is null

    Node *last = pList->head;// find the last pointer

    // find the tail
    if (last)//last !=NULL
    {
        while(last->next){
            last = last->next;
        }
        //circular attaching to insert the new node P
        last->next = p;
        pList->tail = p;
    }else{
        // if last==NULL, initialise it to the head pointer
        pList->head = p;
        pList->tail = p;
    }
}

void print(List *pList){
    Node* p;
    for (p = pList->head; p; p=p->next)
    {
        printf("%d\t",p->value);
    }
}

BOOL get(List* pList, int value){
    Node* p;
    BOOL result = FALSE;
    for (p = pList->head; p; p=p->next)
    {
        if (p->value==value)
        {
            result = TRUE;
        }
    }
    return result;
}

void delete(List* pList, int value){
    Node *p;
    Node *q;
    for (q=NULL,p=pList->head; p; q=p, p=p->next)
    {
        
        //if found
        if (p->value==value)
        {
            if (q)
            {
                // if current list has > 1 nodes
                q->next = p->next;
            }else
            {
                pList->head = p->next;
            }
            
            free(p);
            break;
        }
    }
}

void clear(List* pList){
    // delete all the nodes from the head
    Node* p;
    Node* q;
    for (p = pList->head; p; p=q)
    {
        q = p->next;
        free(p);
    }
}