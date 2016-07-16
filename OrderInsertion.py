import csv
import time
import datetime
import random
from random import randrange
from datetime import timedelta

useridList = []
date_list = []
status_list=[]
status_list.append("CREATED")
status_list.append("PROGRESS")
status_list.append("SHIPPED")
status_list.append("COMPLETED")

date_list.append('2016-07-01')
date_list.append('2016-08-01')
date_list.append('2016-09-01')
date_list.append('2016-10-01')
date_list.append('2016-11-01')
date_list.append('2016-07-01')
date_list.append('2016-11-01')
date_list.append('2016-02-01')


with open('user.csv', 'rb') as csvfile:
	reader = csv.reader(csvfile, delimiter=',')
	flag = 0

	for row in reader:
		flag += 1
		if flag is 1:
			continue

		useridList.append(row[0])

with open('orders.csv', 'wb') as csvfile:
	writer = csv.writer(csvfile, delimiter=',')
	orderpresent = 58000

	writer.writerow(['Order_ID', 'OrderDate', 'ShipDate', 'Status', 'ShipID', 'User_ID', 'Amount', 'Deleted'])
	shipid = 123
	for i in range(1, 1000001):
		orderpresent = orderpresent + 1 
		orderid = orderpresent
		orderstatus=random.choice(status_list)
		orderdate=random.choice(date_list)
		ShipDate = random.choice(date_list)
		shipid += 1
		Deleted=0
		userId=random.choice(useridList)
		writer.writerow([orderid,orderdate,ShipDate,orderstatus,shipid,userId,0,Deleted])



