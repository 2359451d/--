# TEST For
# n=1, print 1
# n=2, print 2
# n=3, print 6
# n=4, print 24

func int fac (int n):  # Return n!
    int i = 0
	int f = 1
	for i=2 to n:
		f = f*i .
	return f .

proc main ():
# Read integers and write their factorials.
	int num = read()
    write(fac(num))
.
