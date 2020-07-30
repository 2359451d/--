#include<iostream>
using namespace std;
int main(){
    int a[5] = {34,2,7,9,5}, temp,min;
    // outer loop: find the right element for each position
    // inner loop: comparing the elements for each poistion, finding the smallest num
    for (int i=0; i<5;i++){
        min = i;
        for (int j=i;j<5;j++){
            if (a[j]<a[min]){
                min = j;
            }
        }
        // swap the min value
        temp = a[i];
        a[i] = a[min];
        a[min] = temp;
    }
    for (int i=0; i<5;i++){
        cout<<a[i]<<" ";
    }
    return 0 ;
}