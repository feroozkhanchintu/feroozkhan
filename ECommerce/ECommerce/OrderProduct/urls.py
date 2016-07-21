from django.conf.urls import url, include
from rest_framework import routers
from views import ProductViewSet, OrderViewSet, OrderLineItemViewSet, ProductSummary, OrderSummary, HealthAPI

router = routers.DefaultRouter()
router.register(r'orders/(?P<order_id>[0-9]+)/orderlineitem', OrderLineItemViewSet)
router.register(r'product',ProductViewSet, base_name='ProductViewSet')
router.register(r'orders', OrderViewSet)

urlpatterns = [
    url(r'^product/summary/$', ProductSummary),
    url(r'^orders/summary/$', OrderSummary),
    url(r'^orders/summary/$', OrderSummary),
    url(r'^health', HealthAPI),
    url(r'^docs/', include('rest_framework_swagger.urls')),
]