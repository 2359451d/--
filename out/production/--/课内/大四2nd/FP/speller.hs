-- string data
comma = ", "
isFor = " is for "
_and = "and "

-- concate word element with corresponding information
_concat :: Bool -> [Char] -> [Char]
_concat isLast ele = if isLast
                    then _and++letter++isFor++ele
                    else letter++isFor++ele++comma
                    where letter=[head ele]

-- from right to left, if current element is the last one
-- set 'isLast' flag to true and call '_concat' function
call :: [Char] -> [Char] -> [[Char]] -> [Char]
call ele acc lst
  | ele == lastEle = (_concat True ele)++acc
  | otherwise = (_concat False ele)++acc
  where lastEle=last lst

-- output spelling info
speller :: [[Char]] -> [Char]
speller lst
  | let len=length lst in len==1 = [head (head lst)]++isFor++(head lst) 
  | null lst = ""
  | otherwise = foldr (\ele acc -> call ele acc lst) "" lst


---------------------------------------
-- version 2
-- generate letter with corresponding words
generatePair :: [[Char]] -> [([Char],[Char])]
generatePair lst = zip [[head x] | x <- lst] lst

con lst = map (\ele->(fst ele)++isFor++(snd ele)) (generatePair lst)

s lst
  | null lst = ""
  | let len=length lst in len==1 = [head (head lst)]++isFor++(head lst) 
  | otherwise = foldr (\ele acc->let lastEle=last (con lst) in if ele==lastEle then _and++ele else ele++comma++acc) "" (con lst)
