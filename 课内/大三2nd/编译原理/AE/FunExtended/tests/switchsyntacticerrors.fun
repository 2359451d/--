# Test switch.

func int test (int n):
    int r = 0
    int s = 0

    switch n:
        case 1..3: # missing default
    .

    switch n:
        case true..false: # invalid guard range,should be int
        default:
    .


    write(s)
    return r .

proc main ():
# Read numbers ,switch
	int i = read()
	write(test(i))
.

