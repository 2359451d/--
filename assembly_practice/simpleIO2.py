"""
determine if the input character is capital
return boolean
"""
def isCapital(char):
    if 65<=ord(char)<=90:
        return True

"""
determine if the input character is lower
return boolean
"""
def isUncapitalized(char):
    if 97<=ord(char)<=122:
        return True

""" 
determine if the input character is numeric
return boolean
"""
def isNumeric(char):
    if 48<=ord(char)<=57:
        return True

""" 
main method
"""
if __name__ == "__main__":
    capitalList = ["Alpha","Bravo","China","Delta","Echo","Foxtrot","Golf","Hotel","India","Juliet","Kilo","Lima","Mary","November","Oscar","Paper","Quebec","Research","Sierra","Tango","Uniform","Victor","Whisky","X-ray","Yankee","Zulu"]
    numericList = ["zero","First","Second","Third","Fourth","Fifth","Sixth","Seventh","Eighth","Nineth"]
    lowerList = ["alpha","bravo","china","delta","echo","foxtrot","golf","hotel","india","juliet","kilo","lima","mary","november","oscar","paper","quebec","research","sierra","tango","uniform","victor","whisky","x-ray","yankee","zulu"]
    while True:
        prompt = input("Please type a character, '?' to exit:\n")
        if prompt == '?':
            break
        if isNumeric(prompt):
            # 48-57
            result = numericList[int(prompt)]
        elif isCapital(prompt):
            # 65-90
            result = capitalList[ord(prompt)-65]
        elif isUncapitalized(prompt):
            # 97-122
            result = lowerList[ord(prompt)-97]
        else:
            result = '*' # result = chr(42)
        print(result)

    # input_tuple = (1,2,3,4,5,6,7,8,9,0)
    # output_tuple = ("First","Second","Third","Fourth","Sixth","Seventh","Eighth","Ninth","zero")

    # data = zip(input_tuple,output_tuple)
    # data_dict = {}

    # for k,v in data:
    #     data_dict[k] = v

    # print(ord('a'))