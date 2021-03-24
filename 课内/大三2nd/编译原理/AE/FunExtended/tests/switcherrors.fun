# Test switch.

func int test (int n):
    int r = 0
    int s = 0

    switch n:
        case 1..3:
        case 1..5:   # Guard overlap conflicts with other guards before
        case 2..4:   # Guard overlap conflicts with other guards before
        case 2:     # duplicate guard 2 already exists
        case 3..1:  # Invalid guard range 3..1
        default:
    .

    # incompatible type
    switch n:
        case true:  # type is bool, should be int,
        case true:  # type is bool, should be int, Duplicate guard true already exists
        case false: # type is bool, should be int
        default:
    .

    write(s)
    return r .

proc main ():
# Read numbers ,switch
	int i = read()
	write(test(i))
.

