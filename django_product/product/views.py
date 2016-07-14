
from django.db.models import Count, Sum, FloatField
import datetime
from django.db.models import F, Q
from django.http import HttpResponse
from models import Orderdetails
from django.http import JsonResponse

# Create your views here.


def index(request):
    return HttpResponse("Hello, world!!!")

def dailysales(request):
    startDate = request.GET.get('startDate', "01/01/2000")
    endDate = request.GET.get('endDate', "01/01/9000")

    startDate = datetime.datetime.strptime(startDate, "%m/%d/%Y")
    endDate = datetime.datetime.strptime(endDate, "%m/%d/%Y")


    query = Orderdetails.objects.filter(order__orderdate__gte=startDate, order__orderdate__lte=endDate). \
        values('order__orderdate'). \
        annotate(date=F('order__orderdate'),
                 orders=Count('order__order_id'),
                 qty=Sum('quantity'),
                 sale_price=Sum(F('price') * F('quantity'), output_field = FloatField()),
                 buy_price=Sum(F('product__buyprice') * F('quantity'), output_field = FloatField()),
                 profit=(Sum(F('price') * F('quantity'), output_field = FloatField()) - Sum(F('product__buyprice') * F('quantity'), output_field = FloatField()))).\
                 order_by('-order__orderdate')

    resultsList = list(query)

    for i in resultsList:
        del i['order__orderdate']
        i['date'] = datetime.date.strftime(i['date'], "%m/%d/%Y")
        i['sale_price'] = round(i['sale_price'], 2)
        i['buy_price'] = round(i['buy_price'], 2)
        i['profit'] = round(i['profit'], 2)


    return JsonResponse({'data': resultsList})



