#include<iostream>
using namespace std;
int main() {
	int salary;  
	double k;    
	while(cin >> salary >> k){
		int cost = 200;
		// int year = -1;
		int saving = 0;
        int i=1;
		for(i = 1;i < 20; i++){
            cost = cost * (1 + k/100);    
			saving = (i + 1) * salary; 
			if(saving >= cost){
                cout << i+1 << endl;
				// year = i + 1;
				break;
			}
		}
        if (i>=20){
            cout << "Impossible" << endl;
        }
	}
    return 0;
}