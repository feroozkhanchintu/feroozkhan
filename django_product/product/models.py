# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from __future__ import unicode_literals

from django.db import models


class Category(models.Model):
    name = models.CharField(max_length=45)
    description = models.CharField(max_length=1000, blank=True, null=True)

    def __unicode__(self):
        return self.name

    class Meta:
        managed = True
        db_table = 'Category'


class Feedback(models.Model):
    id = models.AutoField(db_column='Id', primary_key=True)  # Field name made lowercase.
    user_id = models.IntegerField(db_column='User_ID', blank=True, null=True)  # Field name made lowercase.
    email_id = models.CharField(db_column='Email_ID', max_length=45, blank=True, null=True)  # Field name made lowercase.
    name = models.CharField(db_column='Name', max_length=45, blank=True, null=True)  # Field name made lowercase.
    phonenumber = models.CharField(db_column='PhoneNumber', max_length=20, blank=True, null=True)  # Field name made lowercase.
    description = models.CharField(db_column='Description', max_length=100)  # Field name made lowercase.
    feedbacktime = models.DateTimeField(db_column='FeedbackTime', blank=True, null=True)  # Field name made lowercase.
    updatetime = models.DateTimeField(db_column='UpdateTime', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'Feedback'


class Inventory(models.Model):
    product = models.ForeignKey('Product', models.DO_NOTHING, db_column='Product_ID', primary_key=True)  # Field name made lowercase.
    quantity = models.IntegerField(db_column='Quantity', blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'Inventory'


class Loginformation(models.Model):
    id = models.AutoField(db_column='ID', primary_key=True)  # Field name made lowercase.
    url = models.CharField(db_column='URL', max_length=100, blank=True, null=True)  # Field name made lowercase.
    parameters = models.CharField(db_column='PARAMETERS', max_length=1000, blank=True, null=True)  # Field name made lowercase.
    responsecode = models.IntegerField(db_column='RESPONSECODE', blank=True, null=True)  # Field name made lowercase.
    ipaddress = models.CharField(db_column='IPADDRESS', max_length=45, blank=True, null=True)  # Field name made lowercase.
    starttime = models.DateTimeField(db_column='STARTTIME', blank=True, null=True)  # Field name made lowercase.
    exectime = models.IntegerField(db_column='EXECTIME', blank=True, null=True)  # Field name made lowercase.
    requesttype = models.CharField(db_column='REQUESTTYPE', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'LogInformation'


class Orderdetails(models.Model):
    order = models.ForeignKey('Orders', models.DO_NOTHING, db_column='Order_ID')  # Field name made lowercase.
    price = models.FloatField(db_column='Price')  # Field name made lowercase.
    quantity = models.IntegerField(db_column='Quantity', blank=True, null=True)  # Field name made lowercase.
    total = models.FloatField(db_column='Total', blank=True, null=True)  # Field name made lowercase.
    product = models.ForeignKey('Product', models.DO_NOTHING, db_column='Product_ID')  # Field name made lowercase.

    def __unicode__(self):
        return  self.order

    class Meta:
        managed = True
        db_table = 'OrderDetails'
        unique_together = (('order', 'product'),)


class Orders(models.Model):
    order_id = models.AutoField(db_column='Order_ID', primary_key=True)  # Field name made lowercase.
    orderdate = models.DateField(db_column='OrderDate', blank=True, null=True)  # Field name made lowercase.
    shipdate = models.DateField(db_column='ShipDate', blank=True, null=True)  # Field name made lowercase.
    status = models.CharField(db_column='Status', max_length=45, blank=True, null=True)  # Field name made lowercase.
    shipid = models.CharField(db_column='ShipID', max_length=45, blank=True, null=True)  # Field name made lowercase.
    user_id = models.IntegerField(db_column='User_ID')  # Field name made lowercase.
    amount = models.FloatField(db_column='Amount', blank=True, null=True)  # Field name made lowercase.
    deleted = models.TextField(db_column='Deleted', blank=True, null=True)  # Field name made lowercase. This field type is a guess.

    class Meta:
        managed = True
        db_table = 'Orders'


class Payment(models.Model):
    payment_id = models.IntegerField(db_column='Payment_ID', primary_key=True)  # Field name made lowercase.
    type = models.CharField(db_column='Type', max_length=45)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'Payment'


class Product(models.Model):
    id = models.AutoField(db_column='Id', primary_key=True)  # Field name made lowercase.
    product_id = models.CharField(db_column='Product_ID', unique=True, max_length=25)  # Field name made lowercase.
    name = models.CharField(db_column='Name', max_length=45, blank=True, null=True)  # Field name made lowercase.
    description = models.CharField(db_column='Description', max_length=750, blank=True, null=True)  # Field name made lowercase.
    unitprice = models.FloatField(db_column='UnitPrice', blank=True, null=True)  # Field name made lowercase.
    buyprice = models.FloatField(db_column='BuyPrice', blank=True, null=True)  # Field name made lowercase.
    isavailable = models.TextField(db_column='isAvailable', blank=True, null=True)  # Field name made lowercase. This field type is a guess.
    category = models.ForeignKey(Category, models.DO_NOTHING, blank=True, null=True)

    def __unicode__(self):
        return self.product_id

    class Meta:
        managed = True
        db_table = 'Product'


class User(models.Model):
    user_id = models.AutoField(db_column='User_ID', primary_key=True)  # Field name made lowercase.
    customername = models.CharField(db_column='CustomerName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    firstname = models.CharField(db_column='FirstName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    lastname = models.CharField(db_column='LastName', max_length=45, blank=True, null=True)  # Field name made lowercase.
    email = models.CharField(db_column='Email', max_length=45, blank=True, null=True)  # Field name made lowercase.
    addressline1 = models.CharField(db_column='AddressLine1', max_length=45, blank=True, null=True)  # Field name made lowercase.
    addressline2 = models.CharField(db_column='AddressLine2', max_length=45, blank=True, null=True)  # Field name made lowercase.
    city = models.CharField(db_column='City', max_length=45, blank=True, null=True)  # Field name made lowercase.
    state = models.CharField(db_column='State', max_length=45, blank=True, null=True)  # Field name made lowercase.
    postalcode = models.CharField(db_column='PostalCode', max_length=45, blank=True, null=True)  # Field name made lowercase.
    country = models.CharField(db_column='Country', max_length=45, blank=True, null=True)  # Field name made lowercase.
    mobileno = models.CharField(db_column='MobileNo', max_length=45, blank=True, null=True)  # Field name made lowercase.

    class Meta:
        managed = True
        db_table = 'User'


class AuthGroup(models.Model):
    name = models.CharField(unique=True, max_length=80)

    class Meta:
        managed = True
        db_table = 'auth_group'


class AuthGroupPermissions(models.Model):
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)
    permission = models.ForeignKey('AuthPermission', models.DO_NOTHING)

    class Meta:
        managed = True
        db_table = 'auth_group_permissions'
        unique_together = (('group', 'permission'),)


class AuthPermission(models.Model):
    name = models.CharField(max_length=255)
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING)
    codename = models.CharField(max_length=100)

    class Meta:
        managed = True
        db_table = 'auth_permission'
        unique_together = (('content_type', 'codename'),)


class AuthUser(models.Model):
    password = models.CharField(max_length=128)
    last_login = models.DateTimeField(blank=True, null=True)
    is_superuser = models.IntegerField()
    username = models.CharField(unique=True, max_length=30)
    first_name = models.CharField(max_length=30)
    last_name = models.CharField(max_length=30)
    email = models.CharField(max_length=254)
    is_staff = models.IntegerField()
    is_active = models.IntegerField()
    date_joined = models.DateTimeField()

    class Meta:
        managed = True
        db_table = 'auth_user'


class AuthUserGroups(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    group = models.ForeignKey(AuthGroup, models.DO_NOTHING)

    class Meta:
        managed = True
        db_table = 'auth_user_groups'
        unique_together = (('user', 'group'),)


class AuthUserUserPermissions(models.Model):
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)
    permission = models.ForeignKey(AuthPermission, models.DO_NOTHING)

    class Meta:
        managed = True
        db_table = 'auth_user_user_permissions'
        unique_together = (('user', 'permission'),)


class DjangoAdminLog(models.Model):
    action_time = models.DateTimeField()
    object_id = models.TextField(blank=True, null=True)
    object_repr = models.CharField(max_length=200)
    action_flag = models.SmallIntegerField()
    change_message = models.TextField()
    content_type = models.ForeignKey('DjangoContentType', models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey(AuthUser, models.DO_NOTHING)

    class Meta:
        managed = True
        db_table = 'django_admin_log'


class DjangoContentType(models.Model):
    app_label = models.CharField(max_length=100)
    model = models.CharField(max_length=100)

    class Meta:
        managed = True
        db_table = 'django_content_type'
        unique_together = (('app_label', 'model'),)


class DjangoMigrations(models.Model):
    app = models.CharField(max_length=255)
    name = models.CharField(max_length=255)
    applied = models.DateTimeField()

    class Meta:
        managed = True
        db_table = 'django_migrations'


class DjangoSession(models.Model):
    session_key = models.CharField(primary_key=True, max_length=40)
    session_data = models.TextField()
    expire_date = models.DateTimeField()

    class Meta:
        managed = True
        db_table = 'django_session'
