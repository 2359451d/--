# from drawingpanel import *
# def Mandelbrot_Set(max_iter, c1, c2):#判断（c1，c2）是否在Mandelbrot集合里面
#     x = y = 0
#     for iter in range(max_iter):
#         x, y = x*x - y*y + c1, 2*x*y + c2
#         if x*x + y*y > 4:
#             return iter+1
#     return 0
# def point(p,x,y):
#     p.canvas.create_oval(x,y,x,y,fill='black')
# def point1(p,x,y):
#     p.canvas.create_oval(x,y,x,y,fill='red')
# def main():
#     width,height =900,600
#     panel = DrawingPanel(width,height,background = 'white')
#     for i in range(width):
#         for j in range(height):
#             c1 = -2+(i/width)*(1-(-2))
#             c2 = -1+(j/height)*(1-(-1))
#             judge = Mandelbrot_Set(10000,c1,c2)
#             if judge==0:
#                 point(panel,i,j)
#                 panel.sleep(0.1)
#     # for i in range(width):
#     #     for j in range(height):
#     #         c1 = -2+(i/width)*(1-(-2))
#     #         c2 = -1+(j/height)*(1-(-1))
#     #         judge = Mandelbrot_Set(10000,c1,c2)
#     #         if 1<=judge<1000:
#     #             point1(panel,i,j)
#     #             panel.sleep(0.1)

# main()
