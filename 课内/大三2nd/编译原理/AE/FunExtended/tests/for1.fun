# TEST For
# consecutive Summation
# n=1, print 1
# n=2, print 3
# n=3, print 6
# n=4, print 10

func int sum (int n):  # Return sum(0...n+)
    int i = 0
	int f = 0
	for i=1 to n:
		f = f+i .
	return f .

proc main ():
# Read integers and write their factorials.
	int num = read()
    write(sum(num))
.


