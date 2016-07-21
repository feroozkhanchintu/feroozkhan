FROM python:2.7
VOLUME /tmp
ADD . /
RUN apt-get -y update && pip install awscli && pip install -r requirements.txt
ADD . /
CMD aws s3 cp $CONFIG_URL/dbsettings.ini ECommerce/dbsettings.ini && python ECommerce/manage.py runserver 0.0.0.0:8080