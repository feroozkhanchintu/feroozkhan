from rest_framework import serializers, status
from rest_framework.response import Response
from django.shortcuts import get_object_or_404


from models import *

class ProductSerializer(serializers.ModelSerializer):

    code = serializers.CharField(source='product_id')
    description = serializers.CharField()
    price = serializers.FloatField(source = 'buyprice')
    category = serializers.CharField(source='category.name')
    category_id = serializers.IntegerField(source='category.id', read_only=True)

    class Meta:
        model = Product
        field = ('id', 'code', 'description', 'price', 'category', 'category_id')
        exclude = ('product_id', 'buyprice', 'unitprice', 'isavailable', 'name')

    def create(self, validated_data):
        category = Category.objects.get_or_create(name = validated_data['category']['name'])

        validated_data['category_id'] = category[0].id;

        data = Product.objects.create(category = category[0], product_id=validated_data['product_id'],
                                        description = validated_data['description'], buyprice = validated_data['buyprice'])


        return data


    def update(self, instance, validated_data):
        if self.partial:
            if(validated_data.get('category') is not None):
                category = Category.objects.get_or_create(name = validated_data['category']['name'])
                instance.category = category[0]

            if(validated_data.get('product_id') is not None):
                instance.product_id = validated_data['product_id']
            if (validated_data.get('description') is not None):
                instance.description = validated_data['description']
            if (validated_data.get('buyprice') is not None):
                instance.buyprice = validated_data['buyprice']

            instance.save()
            return instance

        category = Category.objects.get_or_create(name=validated_data['category']['name'])
        instance.category = category[0]
        instance.product_id = validated_data.get('product_id', None)
        instance.description = validated_data.get('description', None)
        instance.buyprice = validated_data.get('buyprice', None)

        instance.save()
        return instance



class OrderSerializer(serializers.ModelSerializer):

    id = serializers.IntegerField(source='order_id', read_only=True)
    username = serializers.CharField(source='user.customername', required=False)
    address = serializers.CharField(source='user.addressline1', required=False)
    status = serializers.CharField()

    class Meta:
        model = Orders
        field = ('id', 'username', 'address', 'status')
        exclude = ('order_id', 'orderdate', 'shipdate', 'shipid', 'amount', 'deleted', 'user')

    def create(self, validated_data):
        if(validated_data.get('user') is not None):
            if 'customername' in validated_data['user'].keys():
                user = User.objects.get_or_create(customername=validated_data['user']['customername'])

            if 'addressline1' in validated_data['user'].keys():
                User.objects.filter(customername=validated_data['user']['customername']).update(
                    addressline1=validated_data['user']['addressline1'])
                user = User.objects.filter(customername=validated_data['user']['customername'])

            data = Orders.objects.create(user=user[0], status=validated_data['status'])

            return data

        else:
            data = Orders.objects.create(status=validated_data['status'])
            return data

    #def create(self, validated_data):
        # if 'userid' in validated_data.keys():
        #     if 'customername' in validated_data['userid'].keys():
        #         customername = validated_data['userid']['customername']
        #         user = Users.objects.get_or_create(
        #             customername=customername,
        #         )
        #     if 'addressline1' in validated_data['userid'].keys():
        #         addressline1 = validated_data['userid']['addressline1']
        #         Users.objects.filter(customername=validated_data['userid']['customername']).update(addressline1=addressline1)
        #         user = Users.objects.filter(customername=validated_data['userid']['customername'])
        #
        #     order = Orders.objects.create(
        #         orderdate=datetime.datetime.now().date(),
        #         status=validated_data['status'],
        #         userid=user[0],
        #         enabled=1

    def update(self, instance, validated_data):
        if self.partial:
            if 'user' in validated_data.keys():
                if 'customername' in validated_data['user'].keys():
                    user = User.objects.get_or_create(customername=validated_data['user']['customername'])

                    if 'addressline1' in validated_data['user'].keys():
                        User.objects.filter(customername=validated_data['user']['customername']).update(addressline1 = validated_data['user']['addressline1'])
                        user = User.objects.filter(customername=validated_data['user']['customername'])
                else:
                    User.objects.filter(customername=instance.user.customername).update(
                        addressline1=validated_data['user']['addressline1'])
                    user = User.objects.filter(customername=instance.user.customername)

                instance.user = user[0]

            instance.status = validated_data.get('status', instance.status)
            instance.deleted = False

            instance.save()
            return instance


        instance.user = None
        if 'user' in validated_data.keys():
            if 'customername' in validated_data['user'].keys():
                user = User.objects.get_or_create(customername=validated_data['user']['customername'])

                if 'addressline1' in validated_data['user'].keys():
                    User.objects.filter(customername=validated_data['user']['customername']).update(
                        addressline1=validated_data['user']['addressline1'])
                    user = User.objects.filter(customername=validated_data['user']['customername'])
                else :
                    User.objects.filter(customername=validated_data['user']['customername']).update(
                        addressline1=None)
                    user = User.objects.filter(customername=validated_data['user']['customername'])

                instance.user = user[0]

        instance.status = validated_data['status']
        instance.deleted = False

        instance.save()
        return instance


class OrderLineItemSerializer(serializers.ModelSerializer):

    id = serializers.IntegerField(read_only=True)
    order_id = serializers.IntegerField(source='order.order_id', read_only=True)
    product_id = serializers.IntegerField(source='product.id')
    price = serializers.FloatField()

    class Meta:
        model = Orderdetails
        field = ('id', 'product_id', 'order_id', 'price')
        exclude = ('order', 'quantity', 'total', 'product')


