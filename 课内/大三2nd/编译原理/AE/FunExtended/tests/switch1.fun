# Test switch.
# n=1, print -> 2 1
# n=2..4, print -> 3 0
# n=other, print -> 0 4

func int test (int n):
    int r = 0
    int s = 0
    switch n:
        case 1:
            r = 1
            s = 2
        case 2..4:
            s = 3
        default:
            r = 4
    .
    write(s)
    return r .

proc main ():
# Read numbers ,switch
	int i = read()
	write(test(i)) # write r
.

