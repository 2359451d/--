# Example Program

## Guessing game

https://github.com/wimvanderbauwhede/HaskellMOOC/blob/master/Starman/starman.hs

```haskell
-- func check, return pair

check :: String -> String -> Char -> (Bool, String)
check word display c
  = (c `elem` word, [if x==c
          then c
          else y | (x,y) <- zip word display])
```

![](/static/2022-01-24-22-46-23.png)