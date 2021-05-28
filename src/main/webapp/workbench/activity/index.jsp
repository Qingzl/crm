<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" +
request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
<base href="<%=basePath%>"/>
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){
	    //刷新活动列表
		pageQuery(1,3);

        //设置日历控件
        $(".time").datetimepicker({
            minView: "month",
            language:  'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true,
            todayBtn: true,
            pickerPosition: "bottom-left"
        });

		//给创建按钮绑定事件
		$("#addBtn").click(function () {
			$.post(
					"activity/users",
					function (data) {
						var option = "";
						$.each(data,function (i,e) {
							option+="<option value='"+e.id+"' >"+e.loginAct+"</option>";
						})
						$("#addSelect").html(option);

                        var id = "${user.id}";
                        $("#addSelect").val(id);

                        $("#createActivityModal").modal("show");
					}
			)
		})

        //给名称表单框绑定一个聚焦事件
        $("#create-name").focus(function () {
            if ($("#name_font").text() != ""){
                $("#name_font").text("");
            }
        })

        //给保存按钮绑定事件
        $("#saveBtn").click(function () {
            var nameValue = $.trim($("#create-name").val());
            if (nameValue == ""){
                $("#name_font").text("请填写活动名称");
                return;
            }

            $.post(
                "activity/add",
                {
                    "owner":$("#addSelect").val(),
                    "name":nameValue,
                    "startDate":$("#create-startDate").val(),
                    "endDate":$("#create-endDate").val(),
                    "cost":$.trim($("#create-cost").val()),
                    "description":$.trim($("#create-description").val()),
                    "createBy":"${user.name}"
                },
                function (data) {
                    if(data.success == "true"){
                        alert("添加成功");

						pageQuery(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

                        $("#addForm")[0].reset();

                        $("#createActivityModal").modal("hide");

                    }else{
                        alert("添加失败");
                    }
                }
            )


        })

        //给查询按钮绑定事件
		$("#searchBtn").click(function () {
			$("#nameHidden").val($.trim($("#search_name").val()));
			$("#ownerHidden").val($.trim($("#search_owner").val()));
			$("#startDateHidden").val($("#search_startDate").val());
			$("#endDateHidden").val($("#search_endDate").val());

			pageQuery(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		})

        //给清空按钮绑定事件
		$("#resetBtn").click(function () {
			$("#searchForm")[0].reset();
		})

        //全选
        $("#firstCb").click(function () {
            $("input[name=cb]").prop("checked",this.checked);
        })

        $("#activityBody").on("click","input[name=cb]",function () {
            $("#firstCb").prop("checked",$("input[name=cb]").size() == $("input[name=cb]:checked").size());
        })

		//删除操作
        $("#deleteBtn").click(function () {
            var $cbs = $("input[name=cb]:checked");
            if ($cbs.size() == 0){
                alert("请选中删除项");
                return;
            }

            window.confirm("确认删除吗");
            var ids = "";
            $.each($cbs,function (i,e) {
                ids+="id=" + e.value;
                if ((i+1) != $cbs.size()){
                    ids+="&";
                }
            })

            $.post(
                "activity/delete",
                ids,
                function (data) {
					if (data.success == "true"){
						alert("删除成功");

						pageQuery(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
					}else{
						alert("删除失败");
					}
                }
            )
        })

		//给修改按钮绑定事件（跳转到修改模态窗口）
		$("#editBtn").click(function () {
			var $cbs = $("input[name=cb]:checked");
			if ($cbs.size() != 1){
				alert("请选择1处修改项");
				return;
			}

			$.get(
					"activity/edit",
					{
						"id":$cbs.val()
					},
					function (data) {
						$("#update-name").val(data.activity.name);
						$("#update-startDate").val(data.activity.startDate);
						$("#update-endDate").val(data.activity.endDate);
						$("#update-cost").val(data.activity.cost);
						$("#update-description").val(data.activity.description);

						$("#editHidden").val(data.activity.id);

						var option = "";
						$.each(data.users,function (i,e) {
							option+="<option value='"+e.id+"'>"+e.name+"</option>";
						})
						$("#update-owner").html(option);

						$("#update-owner").val(data.activity.owner);

						$("#editActivityModal").modal("show");
					}
			)
		})


		//修改页面的“更新”操作
		$("#updateBtn").click(function () {
			var nameValue = $.trim($("#update-name").val());
			if (nameValue == ""){
				$("#update_name_font").text("请填写活动名称");
				return;
			}

			$.post(
					"activity/update",
					{
						"id":$("#editHidden").val(),
						"owner":$("#update-owner").val(),
						"name":nameValue,
						"startDate":$("#update-startDate").val(),
						"endDate":$("#update-endDate").val(),
						"cost":$.trim($("#update-cost").val()),
						"description":$.trim($("#update-description").val()),
						"editBy":"${user.name}"
					},
					function (data) {
						if(data.success == true){
							alert("操作成功");

							pageQuery($("#activityPage").bs_pagination('getOption', 'currentPage'),$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

							$("#editActivityModal").modal("hide");

						}else{
							alert("操作失败");
						}
					}
			)

		})

	})

    //分页查询
	function pageQuery(pageno,pagesize) {
		var startDate= $("#startDateHidden").val();
		var endDate= $("#endDateHidden").val();
		if (startDate != "" && endDate != "" && (startDate > endDate)){
			alert("日期输入有误");
			return;
		}

		$.get(
				"activity/pageQuery",
				{
					"s_name":$("#nameHidden").val(),
					"s_owner":$("#ownerHidden").val(),
					"s_startDate":startDate,
					"s_endDate":endDate,
					"pageno":pageno,
					"pagesize":pagesize
				},
				function (data) {
					var activities = "";
					$.each(data.dataList,function (i,e) {
						activities+='<tr class="active">';
						activities+='<td><input type="checkbox" name="cb" value="'+e.id+'" /></td>';
						activities+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location=\'activity/detail?aid='+e.id+'&owner='+e.owner+'\';">'+e.name+'</a></td>';
						activities+='<td>'+e.owner+'</td>';
						activities+='<td>'+e.startDate+'</td>';
						activities+='<td>'+e.endDate+'</td>';
						activities+='</tr>';
					})
					$("#activityBody").html(activities);

					$("#firstCb").prop("checked",false);

					$("#activityPage").bs_pagination({
						currentPage: pageno, // 页码
						rowsPerPage: pagesize, // 每页显示的记录条数
						maxRowsPerPage: 20, // 每页最多显示的记录条数
						totalPages: data.totalpage, // 总页数
						totalRows: data.totalsize, // 总记录条数

						visiblePageLinks: 3, // 显示几个卡片

						showGoToPage: true,
						showRowsPerPage: true,
						showRowsInfo: true,
						showRowsDefaultInfo: true,

						onChangePage : function(event, data){
							pageQuery(data.currentPage , data.rowsPerPage);
						}
					});
				}
		)

	}



</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="addForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select id="addSelect" class="form-control" id="create-marketActivityOwner">

								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name"><font id="name_font" color="red"></font>
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveBtn" type="button" class="btn btn-primary">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input id="editHidden" type="hidden"/>
						<div class="form-group">
							<label for="update-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="update-owner">
								  <option>zhangsan</option>
								  <option>lisi</option>
								  <option>wangwu</option>
								</select>
							</div>
                            <label for="update-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="update-name" value="发传单"><font id="update_name_font" color="red"></font>
                            </div>
						</div>

						<div class="form-group">
							<label for="update-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="update-startDate" value="2020-10-10" readonly>
							</div>
							<label for="update-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="update-endDate" value="2020-10-20" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="update-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="update-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="update-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="update-description">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="updateBtn" type="button" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form id="searchForm" class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  <input id="nameHidden" type="hidden"/>
				  <input id="ownerHidden" type="hidden"/>
				  <input id="startDateHidden" type="hidden"/>
				  <input id="endDateHidden" type="hidden"/>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="search_name" class="form-control" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="search_owner" class="form-control" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input id="search_startDate" class="form-control time" type="text" readonly/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input id="search_endDate" class="form-control time" type="text" readonly/>
				    </div>
				  </div>
				  
				  <button id="searchBtn" type="button" class="btn btn-default">查询</button>
					&nbsp;&nbsp;&nbsp;
				  <button id="resetBtn" type="button" class="btn btn-default">清空</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button id="addBtn" type="button" class="btn btn-primary" ><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button id="editBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button id="deleteBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="firstCb" type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					</tbody>
				</table>
			</div>

			<div id="activityPage"></div>
			
		</div>
		
	</div>
</body>
</html>