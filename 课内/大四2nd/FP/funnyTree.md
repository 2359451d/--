# Context

```haskell
-- 任意参数类型节点，任意数量分支子树
data FunnyTree a = FunnyNode a [FunnyTree a]


-- 计算总结点数
treeNodeCount :: FunnyTree a -> Int
-- count how many FunnyNode instances are in a FunnyTree

-- 计算树深度，root到最远叶节点，，最小为1(单root)
treeDepth :: FunnyTree a -> Int
-- what is the maximum depth of a tree, from the root node to the
-- furthest node with no children? Depth is a count of the number
-- of FunnyNode instances on a path from the root to a node with
-- no children. So the minimum depth of a FunnyTree will be 1.

-- 树所有节点值转换, a->b
treeMap :: (a->b) -> FunnyTree a -> FunnyTree b
-- map a function over the elements contained in FunnyNodes -
-- preserving tree structure but changing contained values

-- 折叠全树为单一值
-- foldr
treeFold :: (a->b->b) -> b -> FunnyTree a -> b
-- fold a function over a FunnyTree - reducing the Tree to a value.
-- This is similar to foldr for lists.
```

不要改变 `FT.hs`中函数签名，仅修改函数定义部分

只允许使用Prelude, Data.List库中函数

# stack测试

`cd FunnyTrees`

`stack init`

`stack test`

---

`runhaskell test/testfunnytrees.hs`