# TEST For
# n=1, print 10
# n=2, print 20
# n=3, print 60
# n=4, print 240

func int time (int n):  # Return factorial(0...n) * 10, n included
    int i = 0
	int f = 10
	for i=1 to n:
		f = f*i .
	return f .

proc main ():
# Read integers and write their factorials.
	int num = read()
    write(time(num))
.


