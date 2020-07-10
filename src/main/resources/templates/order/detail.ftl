<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

    <#--一边栏sidebar-->
        <#include "../common/nav.ftl">

    <#--主要内容conten-->
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-5 column">
                    <table class="table table-bordered table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>订单ID</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.getOrderId()}</td>
                            <td>${orderDTO.getOrderAmount()}</td>
                        </tr>

                        </tbody>
                    </table>
                </div>

                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>商品名称</th>
                            <th>价格</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                <#list orderDTO.getOrderDetailList() as orderdetail>
                <tr>
                    <th>${orderdetail.getProductId()}</th>
                    <th>${orderdetail.getProductName()}</th>
                    <th>${orderdetail.getProductPrice()}</th>
                    <th>${orderdetail.getProductQuantity()}</th>
                    <th>${orderdetail.getProductPrice() * orderdetail.getProductQuantity()}</th>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <a href="/sell/seller/order/finish?orderId=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-info">完成订单</a>
                    <a href="/sell/seller/order/cancel?orderID=${orderDTO.getOrderId()}" type="button" class="btn btn-default btn-danger">取消订单</a>
                </div>

            </div>
        </div>
    </div>

</div>

</body>
</html>