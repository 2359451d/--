input_tuple = (1,2,3,4,5,6,7,8,9,0)
output_tuple = ("First","Second","Third","Fourth","Sixth","Seventh","Eighth","Ninth","zero")

data = zip(input_tuple,output_tuple)
data_dict = {}

for k,v in data:
    data_dict[k] = v

boo = 1

while boo:
    user_input = input("please enter a value, '?' to quit:\n")
    if user_input == '?':
        break
    else:
        if user_input.isdigit() and int(user_input) in data_dict:
            print(data_dict[int(user_input)])
        elif user_input.isalpha() and user_input in data_dict:
            print(data_dict[user_input])
        else:
            print("*")