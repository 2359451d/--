""" 

SIM card class 
* each object have =1 phone num.

Phone class that uses SIM card class

* each phone has >=1 SIMcard(maybe >=1 phone num.)

Person class

* name
* >=1 or 0 phone (>=1 simcard or 0 simcard)

dangling func() or class or methods

* given 2 phone num. return the names of the matched person (or same person)

"""

class Card:
    """ 
    Represents the SIM card objects:
    each simcard has 1 phone number
    """

    def __init__(self, phone_number):
        self.phone_number = phone_number

class Phone:
    """ 
    Represents the Phone objects:
    each phone has >=1 SIM cards (phone numbers)
    """

    def __init__(self, *cards, **phone_numbers):
        self.cards = cards
        self.phone_numbers = phone_numbers

class Person:
    """
    Represents the class for persons:
    a person has a name & might has >=1 phones or 0 phone
    """

    def __init__(self, name, *phones, **phone_numbers):
        self.name = name
        # might be the list
        self.phones = phones
        # might be the list
        self.phone_numbers = phone_numbers
    
def dangling(phone_number1,phone_number2):

    # given a pair of phone numbers,
    # return the name(s) of related person
    phone_numbers = [phone_number1, phone_number2]
    person_name = set()
    
    for each in phone_numbers:
        for person in phone_book:
            for phone,cards in person.phone_numbers.items():
                for each_num in cards.values():
                    if each_num == each:
                        person_name.add(person.name)

    if (len(person_name)==0):
        print("No results returned")
    else:
        print(person_name)            

if __name__ == "__main__":
    card1 = Card(123)
    card2 = Card(456)
    card3 = Card(789)
    card4 = Card(111)

    # phone1 = Phone(card1,card1.phone_number)
    phone2 = Phone(card2,card2=card2.phone_number)
    phone3 = Phone(card3,card3=card3.phone_number)
    phone4 = Phone(card1,card4,card1=card1.phone_number,card4=card4.phone_number)

    person1 = Person("a")
    person2 = Person("b",phone2,phone2=phone2.phone_numbers)
    person3 = Person("c",phone3,phone3=phone3.phone_numbers)
    person4 = Person("d",phone4, phone4=phone4.phone_numbers)

    phone_book = [person1,person2,person3,person4]
    
    
    dangling(123,456)


