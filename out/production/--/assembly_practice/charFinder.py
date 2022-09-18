def charFinder(user_input,ch):
    flag = -1
    for i in range(len(user_input)):
            if user_input[i]==ch:
                flag = i
                break
    return flag

if __name__ == "__main__":
    prompt = input("Please type a string:")
    while True:
        char = input("Please type a char for searching, '?' to exit:") # case sensetive
        if char=='?':
            break
        index = charFinder(prompt, char)
        if index!=-1:
            print("Success!Location:{0}".format(index+1))
        else:
            print("Fail!")
