import boto3
import json
import threading, time

import datetime
from sqlalchemy import *
import sqlalchemy
import sqlalchemy.ext.declarative
import sqlalchemy.orm.interfaces
import sqlalchemy.exc
import config

conncetion_string = "mysql+pymysql://" + config.USERNAME + ":" + config.PASSWORD + "@" + config.HOST + "/" + config.DATABASE
engine = create_engine(conncetion_string)
metadata = MetaData()
connection = engine.connect()

logInformation = Table('LogInformation', metadata, autoload=True, autoload_with=engine)


class SQSReveiveMessage:

    def receiveMessageFromSQS(self):
        # Get the service resource
        sqs = boto3.resource('sqs')

        # Get the queue. This returns an SQS.Queue instance
        queue = sqs.get_queue_by_name(QueueName='cnu2016_fkhan_assignment05')

        # You can now access identifiers and attributes
        print(queue.url)

        while True:
            for message in queue.receive_messages():

                body = json.loads(message.body)
                url = body['url']
                parameters = body['parameters']
                responseCode = body['responseCode']
                ipAddress = body['ipAddress']
                startTime = body['startTime']
                execTime = body['execTime']

                t = datetime.datetime.fromtimestamp(float(startTime) / 1000.)

                fmt = "%Y-%m-%d %H:%M:%S"
                startTime = t.strftime(fmt)

                parameters = parameters.encode('utf-8')

                ins = logInformation.insert().values(URL=url, PARAMETERS=parameters,
                                            RESPONSECODE=responseCode,
                                            IPADDRESS=ipAddress, STARTTIME=startTime, EXECTIME=execTime)
                res = connection.execute(ins)

                message.delete()
            time.sleep(1)


sqs = SQSReveiveMessage()
sqs.receiveMessageFromSQS()