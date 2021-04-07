from tkinter import *
import tkinter.font as tkFont
parent = Tk()
parent.geometry("800x600")
fontStyle = tkFont.Font(family="Lucida Grande", size=20)
name = Label(parent,text = "Google", font=fontStyle).grid(row = 0, column = 0)
#T = Text(parent, height = 1, width = 1)
#T = tkinter.Text(parent, height=2, width=30)
e1 = Entry(parent, bd=6, font=10, fg="red").grid(row = 0, column = 1)
#password = Label(parent,text = "Password").grid(row = 1, column = 0)
#e2 = Entry(parent).grid(row = 1, column = 1)
submit = Button(parent, text = "Search", font=fontStyle).grid(row = 0, column = 4)
#T.pack()
parent.mainloop()

