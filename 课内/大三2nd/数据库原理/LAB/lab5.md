# ANY

![](/static/2021-03-30-15-23-28.png)
![](/static/2021-03-30-15-23-45.png)

```sql
SELECT DISTINCT d.dogid, d.name, a.place
FROM dog as d, attendance as a
WHERE d.dogid=a.dogid and a.place > ANY(
	SELECT a1.place
	FROM attendance as a1
)
```

# ALL

![](/static/2021-03-30-15-25-03.png)
![](/static/2021-03-30-15-24-55.png)

* rank 比所有的都低

# LIKE模糊查询

![](/static/2021-03-30-15-33-55.png)
![](/static/2021-03-30-15-34-06.png)

# GROUP BY

![](/static/2021-03-30-15-39-59.png)
![](/static/2021-03-30-15-39-46.png)

---

![](/static/2021-03-30-15-40-19.png)
![](/static/2021-03-30-15-43-35.png)

# 嵌套聚合查询 nested aggregation queries

显示狗舍名，有最多狗的，，
1. 假设只有一个狗舍的狗最多，，可以用LIMIT选出
2. 可能有多个狗舍的狗最多（一样多），需要嵌套聚合查询

---

嵌套聚合
![](/static/2021-03-30-16-13-48.png)
![](/static/2021-03-30-16-15-25.png)

LIMIT
![](/static/2021-03-30-16-16-12.png)