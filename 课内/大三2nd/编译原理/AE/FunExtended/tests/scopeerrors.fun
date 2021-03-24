# Test scope checking.

int y = x  # error, x undeclared
int x = 1
bool x = true  # error, x is redeclared

proc main ():
	int n = 0
	int x = 0
	int n = 1  # error, redeclared
	x = x+y  # error, ?
	p()  # error, p is not a procedure, p is undeclared
.
