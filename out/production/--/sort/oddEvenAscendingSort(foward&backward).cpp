#include <iostream>
using namespace std;

int main()
{
	int a[10], temp;
	bool flag = true;
	int size = 10;
	for (int i = 0; i < 10; i++) {
		// read 10 int
		cin >> a[i];
	}

	//maintain 2 pointers, scan in one direction
	for (int i = 0; i < 10;i++) {
		for (int j = 1; j < 10 - i; j++) {
			bool leftIsEven = a[j - 1] % 2 == 0;
			bool rightIsEven = a[j] % 2 == 0;
			if ((leftIsEven && !(rightIsEven)) ||(leftIsEven == rightIsEven && a[j-1]> a[j])) {
				// swap if even num is before the odd
				// or swap if both are even && bigger than the next num
				temp = a[j];
					a[j] = a[j - 1];
					a[j - 1] = temp;
			}
		}
	}

	for (int i = 0; i < 10; i++) {
		cout << a[i] << " ";
	}
	return 0;
}