<html>
   <#include "../common/header.ftl">
<body>

<div id="wrapper" class="toggled">
<#--一边栏sidebar-->
           <#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目ID</th>
                            <th>名字</th>
                            <th>type</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                   <#list categoryList as cateogory>
                   <tr>
                       <td>${cateogory.categoryId}<br></td>
                       <td>${cateogory.categoryName}</td>
                       <td>${cateogory.categoryType}</td>
                       <td>${cateogory.createTime}</td>
                       <td>${cateogory.updateTime}</td>
                       <td>
                           <a href="/sell/seller/category/index?categoryId=${cateogory.categoryId}">修改</a>
                       </td>
                   </tr>
                   </#list >
                        </tbody>
                    </table>
                </div>



            </div>
        </div>
    </div>

</div>
</body>
</html>