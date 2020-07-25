#include <iostream>
using namespace std;

int main() {
	int size = 10,a[10],temp;

	// read 10 numbers
	for (int i = 0; i < size;i++) {
		cin >> a[i];
	}

	// maintain head&tail pointers, scanning in 2 directions
	//partition the array into odd & even parts
	// swap if head>even, tail>odd
	int head = 0, tail = size - 1;
	while (head<tail) {
		bool headIsEven = a[head] % 2 == 0;
		bool tailIsOdd = a[tail] % 2 == 1;
		if (headIsEven && tailIsOdd) {
			temp = a[head];
			a[head] = a[tail];
			a[tail] = temp;
		}

		if (!headIsEven) head++;
		if (!tailIsOdd) tail--;
	}

	// normal bubble sorting: odd & even parts
	for (int i = 0; i < size; i++) {
		for (int j = 1; j < size - i;j++) {
			if (a[j]%2==0 && a[j-1]%2==0) {
				if (a[j-1]>a[j]) {
					temp = a[j - 1];
					a[j - 1] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	for (int i = 0; i < size; i++) {
		for (int j = 1; j < size - i; j++) {
			if (a[j] % 2 == 1 && a[j - 1] % 2 == 1) {
				if (a[j - 1] > a[j]) {
					temp = a[j - 1];
					a[j - 1] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	// print out
	for (int i = 0; i < size;i++) {
		cout << a[i] << ' ';
	}

	return 0;
}