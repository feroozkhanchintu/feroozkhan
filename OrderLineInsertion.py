import csv
import time
import datetime
import random
from random import randint
productid_list=[]
orderid_list=[]

productid_list.append(1)
productid_list.append(2)
productid_list.append(3)
productid_list.append(4)
productid_list.append(5)
productid_list.append(6)
productid_list.append(7)
productid_list.append(8)

with open('orders.csv', 'rb') as csvfile:
	reader = csv.reader(csvfile, delimiter=',')
	flag = 0
	for row in reader:
		flag = flag + 1
		if flag is 1:
			continue
		orderid_list.append(row[0])


with open('orderline.csv', 'wb') as csvfile:
	writer = csv.writer(csvfile, delimiter=',')
	id1=100
	maxprice=1000
	maxorderquantity=50
	writer.writerow(["id","Order_ID","Price","Quantity","Product_ID"])
	for iter in range(1  ,1000001):
		id=id1 + iter
		price=randint(1,maxprice)
		quantityordered=randint(1,maxorderquantity)
		orderid=random.choice(orderid_list)
		productid=random.choice(productid_list)
		writer.writerow([id,orderid,price,quantityordered,productid])


	


