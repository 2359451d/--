"""

cleaning robot. moves in a strange way.

* starting point (x, y)
* take a step (x, x+y) or (x+2y, y)

- given a starting point (x1,y1) check wether it is possible
to reach a specific point (x2, y2)

# BASE CASE: if (a, b) > (x2,y2) goes too far

"""

start_x, start_y = 2, 3
final_x, final_y = 20,4

result = 0

def canReach(start_x, start_y, final_x, final_y):
    # base case: robot goes too far

    if (start_x>final_x) or (start_y>final_y):
        return 0

    canReach(start_x, start_x + start_y, final_x, final_y)
    canReach(start_x + start_y + start_y, start_y, final_x, final_y)
    if start_x==final_x and start_y==final_y:
        global result
        result = 1

def getResult():
    canReach(start_x, start_y, final_x, final_y)
    return result

if __name__ == "__main__":
    if (getResult()):
        print("True")
    else:
        print("False")
