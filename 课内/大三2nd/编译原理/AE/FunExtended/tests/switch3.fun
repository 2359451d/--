# Test switch.
# 0<=n<=50, print 10
# 51<=n<=99, print 100
# n=100, print 666
# n=other, print 0

func int test (int n):
    int s = 0
    switch n:
        case 0..50:
            s = 10
        case 51..99:
            s = 100
        case 100:
            s = 666
        default:
            s = 0
    .
    return s .

proc main ():
# Read numbers ,switch
	int i = read()
	write(test(i))
.

