# Test switch.
# n>50, print 100
# n<=50, print 0

func int test (int n):
    int s = 0
    switch n>50:
        case true:
            s = 100
        default:
            s = 0
    .
    return s .

proc main ():
# Read numbers ,switch
	int i = read()
	write(test(i))
.

