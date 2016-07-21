from django.db.models import Count
from django.http import JsonResponse
from rest_framework import viewsets
from rest_framework import serializers, status
from rest_framework.response import Response
from django.db.models import F
from django.shortcuts import get_object_or_404

from . import serializers
from . import models


# Create your views here.
class MiddleWare(object):
    def process_response(self, request, response):
        if(response is not None):
            if(response._container is not None and response._container is not ['']):
                response._container = ['{"data":'+response._container[0]+'}']

        return response

class ProductViewSet(viewsets.ModelViewSet):
    serializer_class = serializers.ProductSerializer
    queryset = models.Product.objects.filter(isavailable=True)

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.isavailable = False
        instance.save()
        return Response(status=status.HTTP_204_NO_CONTENT)

class OrderViewSet(viewsets.ModelViewSet):
    serializer_class = serializers.OrderSerializer
    queryset = models.Orders.objects.filter(deleted=False)

    def destroy(self, request, *args, **kwargs):
        instance = self.get_object()
        instance.deleted = True
        instance.save()
        return Response(status=status.HTTP_204_NO_CONTENT)

class OrderLineItemViewSet(viewsets.ModelViewSet):
    serializer_class = serializers.OrderLineItemSerializer
    queryset = models.Orderdetails.objects.all()

    def list(self, request, *args, **kwargs):
        order_id = self.kwargs['order_id']

        resultQuerySet = models.Orderdetails.objects.filter(order__order_id=order_id)
        serializer = self.get_serializer(resultQuerySet, many=True)
        return Response(serializer.data)

    def retrieve(self, request, *args, **kwargs):
        order_id = self.kwargs['order_id']
        pk = self.kwargs['pk']

        resultQuerySet = models.Orderdetails.objects.filter(order__order_id=order_id, product__id = pk)
        serializer = self.get_serializer(resultQuerySet, many = True)

        return Response(serializer.data)

    def create(self, request, *args, **kwargs):

        orderLineItemSerializer = serializers.OrderLineItemSerializer(data=request.data)
        data = request.data
        if orderLineItemSerializer.is_valid():
            product = models.Product.objects.get(id=data['product_id'])
            order = models.Orders.objects.get(order_id=kwargs['order_id'])
            orderdetails = models.Orderdetails.objects.create(product=product, order=order, price=data['price'])
            serializer = self.get_serializer(orderdetails)
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        # data = request.data
        # product = models.Product.objects.get(id=data['product_id'])
        # order = models.Orders.objects.get(order_id=kwargs['order_id'])
        #
        #
        # data = {}
        # data['order'] = order
        # data['product'] = product
        # data['price'] = request.data.get('price')
        # orderLineItemSerializer = serializers.OrderLineItemSerializer(data=data)
        # order_product = models.Orderdetails.objects.create(product=product, order=order, price=data['price'])
        # return Response(order_product, status=status.HTTP_201_CREATED)


        # order_id = self.kwargs['order_id']
        # price = request.data.get('price')
        # product_id = request.data.get('product_id')
        # serializer = self.get_serializer(data=request.data)
        # serializer.is_valid(raise_exception=True)
        # order = get_object_or_404(models.Orders, order_id=order_id)
        # product = get_object_or_404(models.Product, id=product_id)
        #
        # data = models.Orderdetails(order=order, product=product, price=price)
        # data.save()
        # serializer = self.get_serializer(data, many = True)
        # return Response({data : serializer.data})

    #
    #     order = get_object_or_404(models.Orders, order_id=order_id)
    #     product = get_object_or_404(models.Product, id=request.get('product.id'))
    #
    #     data = Orderdetails.objects.create(order=order, product=product, price=validated_data['price'])
    #     return data
    #
    # serializer = self.get_serializer(data=request.data)
    # serializer.is_valid(raise_exception=True)
    # self.perform_create(serializer)
    # headers = self.get_success_headers(serializer.data)
    # return Response(serializer.data, status=status.HTTP_201_CREATED, headers=headers)


def ProductSummary(request):
    groupby = request.GET.get('group_by', None)
    productcode = request.GET.get('code', None)
    categoryname = request.GET.get('category__name', None)

    query = models.Product.objects.filter(isavailable=True)

    if productcode:
        query = query.filter(product_id=productcode)

    if categoryname:
        query = query.filter(category__name=categoryname)

    if groupby :
        query = query.values('category__id')
        query = query.annotate(count = Count('product_id'))
        query = query.annotate(category_id = F('category__id'))
        print  query
        resultSet = list(query)

        for i in resultSet:
             del i['category__id']

        return JsonResponse({'data': resultSet})

    else:
        query = query.aggregate(count = Count('product_id'))
        return JsonResponse({'data': query})


def OrderSummary(request):
    groupby = request.GET.get('group_by', None)
    productcode = request.GET.get('orderlineitem__product__code', None)
    categoryname = request.GET.get('orderlineitem__product__category__name', None)

    query = models.Orderdetails.objects.filter(order__deleted=False)

    if productcode:
        query = query.filter(product__product_id=productcode)

    if categoryname:
        query = query.filter(product__category__name=categoryname)

    if groupby:
        if 'product' in groupby:
            query = query.values('product__product_id')
            query = query.annotate(product_id=F('product__id'))
            query = query.annotate(count=Count('product__id'))
        else:
            query = query.values('product__category__name')
            query = query.annotate(category_id=F('product__category__id'))
            query = query.annotate(count=Count('product__category__id'))

        resultSet = list(query)

        return JsonResponse({'data': resultSet})

    else:
        query = query.aggregate(count=Count('product__product_id'))
        return JsonResponse({'data': query})


def HealthAPI(request):
    content = {'data' : 'HealthAPI'}
    return JsonResponse(content)