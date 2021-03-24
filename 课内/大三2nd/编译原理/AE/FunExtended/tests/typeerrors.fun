# Test type checking.

int n = true  # error
bool c = 1  # error

func bool pos (int n):
	return n  # error, type is int, should be bool
.

proc main ():
	int i = 3
	bool b = true
	i = i+1
	i = b  # error, should be int
	i = b*2  # error, should be int
	b = i>0
	if b: write(i) .
	if i: write(i) .  # error, if i should be bool, not int
	b = pos(true)  # error, type is bool, should be int
	while pos(7): i = i+1 .
.

