import dateutil
from flask import Flask, jsonify
from flask_sqlalchemy import SQLAlchemy
import dateutil.parser as parser
from flask import request
import config
import datetime

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = config.SQLALCHEMY_DATABASE_URI
db = SQLAlchemy(app)


class LogInformation(db.Model):
    __tablename__ = 'LogInformation'
    ID = db.Column(db.Integer, primary_key=True)
    URL = db.Column(db.VARCHAR(80))
    PARAMETERS = db.Column(db.VARCHAR)
    RESPONSECODE = db.Column(db.Integer)
    IPADDRESS = db.Column(db.VARCHAR)
    STARTTIME = db.Column(db.DATETIME)
    EXECTIME = db.Column(db.Integer)
    REQUESTTYPE = db.Column(db.String)


    def __init__(self, id, url, parameters, responsecode, ipaddress, starttime, exectime, requesttype):
        self.ID = id
        self.URL = url
        self.PARAMETERS = parameters
        self.RESPONSECODE = responsecode
        self.IPADDRESS = ipaddress
        self.STARTTIME = starttime
        self.EXECTIME = exectime
        self.REQUESTTYPE = requesttype

    def to_JSON(self):
        timestamp = convertDateToIso(str(self.STARTTIME))
        timestamp = datetime.datetime.strptime(str(timestamp), '%Y-%m-%dT%H:%M:%S').strftime('%m/%d/%yT%H:%M:%S')
        return { "id" : self.ID, "timestamp" : timestamp,
                 "url" : self.URL ,
                 "parameters" : self.PARAMETERS, "response_code" : self.RESPONSECODE,
                 "ip_address" : self.IPADDRESS,
                 "request_duration_ms" : self.EXECTIME, "request_type" : self.REQUESTTYPE
             }


@app.route('/', methods=['GET'])
def HelloWorld():
    return jsonify("Hello World!!!")

def convertDateToIso(date):
    ret = parser.parse(date)
    return ret.isoformat()

@app.route("/api/auditLogs", methods=['GET'])
def AuditLogs():

    limit = min(int(request.args.get('limit', 10)), 10)
    offset = request.args.get('offset', 0)

    startTime = request.args.get('startTime', '2015-01-01 00:00:00')
    endTime = request.args.get('endTime','3000-12-10 00:00:00')

    startTime = dateutil.parser.parse(startTime)
    endTime = dateutil.parser.parse(endTime)

    # offset = offset * limit

    # return jsonify(data = [logs.to_JSON() for logs in LogInformation.query.order_by(LogInformation.STARTTIME.desc()).limit(limit).offset(offset).all()])

    return jsonify(data = [logs.to_JSON() for logs in LogInformation.query
                   .order_by(LogInformation.STARTTIME.desc())
                   .filter(LogInformation.STARTTIME.between(startTime, endTime))
                   .limit(limit).offset(offset).all()])



if __name__ == '__main__':
    app.run(debug=True, port = 8080)
    db.create_all()


