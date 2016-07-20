'use strict';

// Declare app level module which depends on views, and components
var app = angular.module('EcommerceApp', ['ngRoute', 'ngCookies']);
app.value('itemsoncart', { value : 0});

app.config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {
  //      $locationProvider.hashPrefix('!');
        $routeProvider.

        when('/showProduct/:id', {
            templateUrl: 'show-product.html',
            controller: 'getProductDetail'
        }).
        when('/showCart', {
            templateUrl: 'ViewCart.html',
            controller: 'ViewCartController'
        }).
        when('/checkout', {
            templateUrl: 'Checkout.html',
            controller: 'checkoutController'
        }).
        when('/thankyou', {
            templateUrl: 'ThankYou.html',
        }).
        when('/contactus', {
            templateUrl: 'ContactUs.html',
            controller: 'contactusController'
        }).
        when('/', {
            templateUrl: 'AllProducts.html',
            controller: 'getProductsController'
        }).

        otherwise({
            redirectTo: '/'
        });
    }]);


app.controller('getProductDetail', function($log, $routeParams, $http, $scope) {
    this.productId = $routeParams.id;
    console.log(this.productId);
    $http.get("http://127.0.0.1:8000/api/products/" + this.productId).then(function (response) {
        $scope.requestedProduct = response.data.data;
        console.log(response.data.data);

    });
});

app.controller('getProductsController', function($scope, $http, $cookies, $rootScope) {
    if($rootScope.itemsoncart === undefined)
        $rootScope.itemsoncart = 0;
  $http.get("http://127.0.0.1:8000/api/products/").then(function(response) {
    $scope.products = response.data.data;
      console.log(response.data.data);
  });
    $scope.setCategory = function (category){
        $scope.selectedCategory = category;
    }

    $http.get("http://127.0.0.1:8000/api/category/").then(function(response) {
        $scope.categories = response.data.data;
        console.log(response.data.data);

    });

    var data = JSON.stringify({status : "ONCART"});

    var config = {
        headers : {
            'Content-Type': 'application/json;'
        }
    }

    $http.post('http://127.0.0.1:8000/api/orders/', data, config)
        .success(function (data, status, headers, config) {
            $scope.orderId  = $cookies.get("orderId");
            if($scope.orderId === undefined)
                $cookies.put("orderId", data.data.id)
            console.log($scope.orderId)
        });

    $scope.addToCart = function (addToCartProduct, qty) {
        $rootScope.itemsoncart += 1;

        if(qty == undefined)
            qty = 1;

        $scope.cartProducts = $cookies.getObject("cartProducts5");
        if ($scope.cartProducts === undefined)
            $scope.cartProducts = [];
        window.alert("Item has been added to the cart");

        for (var i = 0; i < $scope.cartProducts.length; i++) {
            if (addToCartProduct.id == $scope.cartProducts[i].id) {
                $scope.cartProducts[i].quantity += qty;
                $cookies.putObject("cartProducts5", $scope.cartProducts);
                return;
            }
        }
        addToCartProduct.quantity = qty;
        $scope.cartProducts.push(addToCartProduct);
        $cookies.putObject("cartProducts5", $scope.cartProducts);
    }
});


app.controller('getCategoryController', function($scope, $http) {
    $http.get("http://127.0.0.1:8000/api/category/").then(function(response) {
        $scope.categories = response.data.data;
        console.log(response.data.data);

    });
});

app.controller('ViewCartController', function($scope, $http, $cookies, $rootScope) {
    $scope.showCartProducts = $cookies.getObject("cartProducts5");

    $scope.getTotal = function () {
        var total = 0;
        for(var i = 0; i < $scope.showCartProducts.length; i++){
            var product = $scope.showCartProducts[i];
            total += (product.price * product.quantity);
        }
        return total;

    }

    $scope.quantityChange = function (product, index) {
        $scope.showCartProducts[index].quantity = product.quantity;
        $cookies.putObject("cartProducts5", $scope.showCartProducts);
    }

    $scope.removeFromCart = function (index) {
        $rootScope.itemsoncart -= 1;
        $scope.showCartProducts.splice(index, 1);
        $cookies.putObject("cartProducts5", $scope.showCartProducts);

    }
});


app.controller('checkoutController', function($scope, $http, $cookies) {
    $scope.reviewProducts = $cookies.getObject("cartProducts5");

    $scope.sendDatatoDb = function(){
        for(var i = 0; i < $scope.reviewProducts.length; i++)
        {

            var data = JSON.stringify({product_id : $scope.reviewProducts[i].id,
                quantity: $scope.reviewProducts[i].quantity, price:$scope.reviewProducts[i].price});

            console.log(data);
            var config = {
                headers : {
                    'Content-Type': 'application/json;'
                }
            };

            $http.post('http://127.0.0.1:8000/api/orders/'+$cookies.get("orderId")+'/orderlineitem/', data, config);

        }

        var username = $scope.first_name + " " + $scope.last_name;
        var address = $scope.useraddress;

        data = JSON.stringify({username : username,
            address: address, status:"ORDERED"});

        $http.get('http://127.0.0.1:8000/api/checkInventory?orderId=' + $cookies.get("orderId"))
            .success(function(data) {
            })
            .error(function(data, status) {
                window.alert("Bad Request: Quantity not available");
                return;
            });

        $http.patch('http://127.0.0.1:8000/api/orders/'+$cookies.get("orderId") + '/', data, config);

        $cookies.remove("orderId");
        $cookies.remove("cartProducts5");
    }
});

app.controller('contactusController', function($scope, $http) {

    $scope.postFeedback = function () {
        var email = $scope.email;
        var name = $scope.fname + $scope.lname;
        var phone = $scope.phone;
        var desc = $scope.message;


        var data = JSON.stringify({email : email, name: name, phone : phone, description:desc});

        var config = {
            headers : {
                'Content-Type': 'application/json;'
            }
        }

        $http.post('http://127.0.0.1:8000/api/contactus/', data, config)
            .success(function (data, status, headers, config) {
                window.alert("Your feedback has been received");
            });
    }


});