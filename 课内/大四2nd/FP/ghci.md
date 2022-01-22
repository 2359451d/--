# Content

* [Content](#content)
* [:type](#type)
* [:set +m](#set-m)
* [:load,:l](#loadl)

# :type

`:t`

`:type`

# :set +m

set multiple line statements

```haskell

-- do organise IO operations (impure func)
-- then define the impure func opeartions with name greet()

let greet() = do 
    planet <- getLine
    home <- getLine
    putStrLn ("greetings " ++ planet ++ "ling.")
    putStrLn ("I am from " ++ home ++ ".")
    putStrLn "Take me to your leader."

```

# :load,:l

![](/static/2022-01-20-06-07-48.png)

after load

`xxxx "Name"`