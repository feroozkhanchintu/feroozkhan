# -*- coding: utf-8 -*-
# Generated by Django 1.9.7 on 2016-07-14 13:21
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='AuthGroup',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=80, unique=True)),
            ],
            options={
                'db_table': 'auth_group',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='AuthGroupPermissions',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('group', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthGroup')),
            ],
            options={
                'db_table': 'auth_group_permissions',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='AuthPermission',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=255)),
                ('codename', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'auth_permission',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='AuthUser',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('password', models.CharField(max_length=128)),
                ('last_login', models.DateTimeField(blank=True, null=True)),
                ('is_superuser', models.IntegerField()),
                ('username', models.CharField(max_length=30, unique=True)),
                ('first_name', models.CharField(max_length=30)),
                ('last_name', models.CharField(max_length=30)),
                ('email', models.CharField(max_length=254)),
                ('is_staff', models.IntegerField()),
                ('is_active', models.IntegerField()),
                ('date_joined', models.DateTimeField()),
            ],
            options={
                'db_table': 'auth_user',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='AuthUserGroups',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('group', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthGroup')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthUser')),
            ],
            options={
                'db_table': 'auth_user_groups',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='AuthUserUserPermissions',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('permission', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthPermission')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthUser')),
            ],
            options={
                'db_table': 'auth_user_user_permissions',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=45)),
                ('description', models.CharField(blank=True, max_length=1000, null=True)),
            ],
            options={
                'db_table': 'Category',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='DjangoAdminLog',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('action_time', models.DateTimeField()),
                ('object_id', models.TextField(blank=True, null=True)),
                ('object_repr', models.CharField(max_length=200)),
                ('action_flag', models.SmallIntegerField()),
                ('change_message', models.TextField()),
            ],
            options={
                'db_table': 'django_admin_log',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='DjangoContentType',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('app_label', models.CharField(max_length=100)),
                ('model', models.CharField(max_length=100)),
            ],
            options={
                'db_table': 'django_content_type',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='DjangoMigrations',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('app', models.CharField(max_length=255)),
                ('name', models.CharField(max_length=255)),
                ('applied', models.DateTimeField()),
            ],
            options={
                'db_table': 'django_migrations',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='DjangoSession',
            fields=[
                ('session_key', models.CharField(max_length=40, primary_key=True, serialize=False)),
                ('session_data', models.TextField()),
                ('expire_date', models.DateTimeField()),
            ],
            options={
                'db_table': 'django_session',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Feedback',
            fields=[
                ('id', models.AutoField(db_column='Id', primary_key=True, serialize=False)),
                ('user_id', models.IntegerField(blank=True, db_column='User_ID', null=True)),
                ('email_id', models.CharField(blank=True, db_column='Email_ID', max_length=45, null=True)),
                ('name', models.CharField(blank=True, db_column='Name', max_length=45, null=True)),
                ('phonenumber', models.CharField(blank=True, db_column='PhoneNumber', max_length=20, null=True)),
                ('description', models.CharField(db_column='Description', max_length=100)),
                ('feedbacktime', models.DateTimeField(blank=True, db_column='FeedbackTime', null=True)),
                ('updatetime', models.DateTimeField(blank=True, db_column='UpdateTime', null=True)),
            ],
            options={
                'db_table': 'Feedback',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Loginformation',
            fields=[
                ('id', models.AutoField(db_column='ID', primary_key=True, serialize=False)),
                ('url', models.CharField(blank=True, db_column='URL', max_length=100, null=True)),
                ('parameters', models.CharField(blank=True, db_column='PARAMETERS', max_length=1000, null=True)),
                ('responsecode', models.IntegerField(blank=True, db_column='RESPONSECODE', null=True)),
                ('ipaddress', models.CharField(blank=True, db_column='IPADDRESS', max_length=45, null=True)),
                ('starttime', models.DateTimeField(blank=True, db_column='STARTTIME', null=True)),
                ('exectime', models.IntegerField(blank=True, db_column='EXECTIME', null=True)),
                ('requesttype', models.CharField(blank=True, db_column='REQUESTTYPE', max_length=45, null=True)),
            ],
            options={
                'db_table': 'LogInformation',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Orderdetails',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('price', models.FloatField(db_column='Price')),
                ('quantity', models.IntegerField(blank=True, db_column='Quantity', null=True)),
                ('total', models.FloatField(blank=True, db_column='Total', null=True)),
            ],
            options={
                'db_table': 'OrderDetails',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Orders',
            fields=[
                ('order_id', models.AutoField(db_column='Order_ID', primary_key=True, serialize=False)),
                ('orderdate', models.DateField(blank=True, db_column='OrderDate', null=True)),
                ('shipdate', models.DateField(blank=True, db_column='ShipDate', null=True)),
                ('status', models.CharField(blank=True, db_column='Status', max_length=45, null=True)),
                ('shipid', models.CharField(blank=True, db_column='ShipID', max_length=45, null=True)),
                ('user_id', models.IntegerField(db_column='User_ID')),
                ('amount', models.FloatField(blank=True, db_column='Amount', null=True)),
                ('deleted', models.TextField(blank=True, db_column='Deleted', null=True)),
            ],
            options={
                'db_table': 'Orders',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Payment',
            fields=[
                ('payment_id', models.IntegerField(db_column='Payment_ID', primary_key=True, serialize=False)),
                ('type', models.CharField(db_column='Type', max_length=45)),
            ],
            options={
                'db_table': 'Payment',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Product',
            fields=[
                ('id', models.AutoField(db_column='Id', primary_key=True, serialize=False)),
                ('product_id', models.CharField(db_column='Product_ID', max_length=25, unique=True)),
                ('name', models.CharField(blank=True, db_column='Name', max_length=45, null=True)),
                ('description', models.CharField(blank=True, db_column='Description', max_length=750, null=True)),
                ('unitprice', models.FloatField(blank=True, db_column='UnitPrice', null=True)),
                ('buyprice', models.FloatField(blank=True, db_column='BuyPrice', null=True)),
                ('isavailable', models.TextField(blank=True, db_column='isAvailable', null=True)),
            ],
            options={
                'db_table': 'Product',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('user_id', models.AutoField(db_column='User_ID', primary_key=True, serialize=False)),
                ('customername', models.CharField(blank=True, db_column='CustomerName', max_length=45, null=True)),
                ('firstname', models.CharField(blank=True, db_column='FirstName', max_length=45, null=True)),
                ('lastname', models.CharField(blank=True, db_column='LastName', max_length=45, null=True)),
                ('email', models.CharField(blank=True, db_column='Email', max_length=45, null=True)),
                ('addressline1', models.CharField(blank=True, db_column='AddressLine1', max_length=45, null=True)),
                ('addressline2', models.CharField(blank=True, db_column='AddressLine2', max_length=45, null=True)),
                ('city', models.CharField(blank=True, db_column='City', max_length=45, null=True)),
                ('state', models.CharField(blank=True, db_column='State', max_length=45, null=True)),
                ('postalcode', models.CharField(blank=True, db_column='PostalCode', max_length=45, null=True)),
                ('country', models.CharField(blank=True, db_column='Country', max_length=45, null=True)),
                ('mobileno', models.CharField(blank=True, db_column='MobileNo', max_length=45, null=True)),
            ],
            options={
                'db_table': 'User',
                'managed': True,
            },
        ),
        migrations.CreateModel(
            name='Inventory',
            fields=[
                ('product', models.ForeignKey(db_column='Product_ID', on_delete=django.db.models.deletion.DO_NOTHING, primary_key=True, serialize=False, to='product.Product')),
                ('quantity', models.IntegerField(blank=True, db_column='Quantity', null=True)),
            ],
            options={
                'db_table': 'Inventory',
                'managed': True,
            },
        ),
        migrations.AddField(
            model_name='product',
            name='category',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.DO_NOTHING, to='product.Category'),
        ),
        migrations.AddField(
            model_name='orderdetails',
            name='order',
            field=models.ForeignKey(db_column='Order_ID', on_delete=django.db.models.deletion.DO_NOTHING, to='product.Orders'),
        ),
        migrations.AddField(
            model_name='orderdetails',
            name='product',
            field=models.ForeignKey(db_column='Product_ID', on_delete=django.db.models.deletion.DO_NOTHING, to='product.Product'),
        ),
        migrations.AlterUniqueTogether(
            name='djangocontenttype',
            unique_together=set([('app_label', 'model')]),
        ),
        migrations.AddField(
            model_name='djangoadminlog',
            name='content_type',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.DO_NOTHING, to='product.DjangoContentType'),
        ),
        migrations.AddField(
            model_name='djangoadminlog',
            name='user',
            field=models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthUser'),
        ),
        migrations.AddField(
            model_name='authpermission',
            name='content_type',
            field=models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.DjangoContentType'),
        ),
        migrations.AddField(
            model_name='authgrouppermissions',
            name='permission',
            field=models.ForeignKey(on_delete=django.db.models.deletion.DO_NOTHING, to='product.AuthPermission'),
        ),
        migrations.AlterUniqueTogether(
            name='orderdetails',
            unique_together=set([('order', 'product')]),
        ),
        migrations.AlterUniqueTogether(
            name='authuseruserpermissions',
            unique_together=set([('user', 'permission')]),
        ),
        migrations.AlterUniqueTogether(
            name='authusergroups',
            unique_together=set([('user', 'group')]),
        ),
        migrations.AlterUniqueTogether(
            name='authpermission',
            unique_together=set([('content_type', 'codename')]),
        ),
        migrations.AlterUniqueTogether(
            name='authgrouppermissions',
            unique_together=set([('group', 'permission')]),
        ),
    ]
