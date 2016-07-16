from django.conf.urls import url, include
from rest_framework import routers
from views import ProductViewSet, OrderViewSet, OrderLineItemViewSet, ProductSummary, OrderSummary

router = routers.DefaultRouter()
router.register(r'orders/(?P<order_id>[0-9]+)/orderlineitem', OrderLineItemViewSet)
router.register(r'products',ProductViewSet, base_name='ProductViewSet')
router.register(r'orders', OrderViewSet)

urlpatterns = [
    url(r'^product/summary/$', ProductSummary),
    url(r'^orders/summary/$', OrderSummary),
    url(r'^', include(router.urls)),
    url(r'^docs/', include('rest_framework_swagger.urls')),
]