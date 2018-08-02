<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>微信菜单设置</title>
    <link rel="stylesheet" href="${ctx}/operator/assets/wechatMenu/css/flat.css"/>
    <link rel="stylesheet" href="${ctx}/operator/assets/wechatMenu/css/style.css"/>
    <link rel="stylesheet" href="${ctx}/operator/assets/wechatMenu/js/bootstrap3-dialog3/bootstrap3-dialog.css">
    <%@ include file="global/mylink.jsp" %>

</head>
<body class="layer-body" style="margin-top: -63px;">
<div class="clearfix wrap" id="app" style="border:0px;">
    <div class="main" style="margin-top: -9px;">
        <div class="menu_preview_area" style="margin-left: 20px;">
            <div class="mobile_menu_preview">
                <div class="mobile_hd tc">微信菜单设置</div>
                <div class="mobile_bd">
                    <div class="pre_menu_list dd" id="nestable">
                        <ol class="dd-list" id="menuList" data-listidx="0">
                            <li class="pre_menu_item" v-bind:class="{selected:item == choseMenu,current:item == choseMenu&&selectFlag==1}" v-for="(item,index) in menu.button">
                                <a href="javascript:void(0);" class="pre_menu_link" draggable="true" @click="selectMenu(item)" >
                                    <i class="icon20_common sort_gray"></i><span class="js_l1Title">{{item.name}}</span>
                                </a>
                                <div class="sub_pre_menu_box dd-list">
                                    <ol class="sub_pre_menu_list" v-bind:class="getGroupClass(index)">
                                        <li class="dd-item jslevel2" v-bind:class="{current:sub_item == choseSubMenu}" @click="selectSubMenu(sub_item)" v-for="(sub_item,index) in item.sub_button">
                                            <a href="javascript:void(0);" class="jsSubView" draggable="false">
                                                <span class="sub_pre_menu_inner js_sub_pre_menu_inner">{{sub_item.name}}</span>
                                            </a>
                                        </li>
                                        <div class="js_addMenuBox">
                                            <a href="javascript:void(0);" class="jsSubView js_addL2Btn" @click="addSubMenu" title="最多添加5个子菜单" draggable="false">
                                                <span class="sub_pre_menu_inner js_sub_pre_menu_inner"><i class="fui-plus"></i></span>
                                            </a>
                                        </div>
                                    </ol>
                                </div>
                            </li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
        <div class="menu_form_area" style="margin-left: -69px;">
            <div id="js_rightBox" class="portable_editor to_left" style="display: block;">
                <div class="editor_inner">
                    <div class="menu_form_hd clearfix">
                        <h4 class="global_info fl"> 菜单信息 </h4>
                        <div class="global_extra fr">
                            <a @click="deleteMenu">删除菜单</a>
                        </div>
                    </div>
                    <div class="menu_form_bd" id="view">
                        <div class="form-horizontal">
                            <div class="col-md-8" style="margin-top: 10px;">
                                <div class="icheck-list">
                                    <label>
                                        <span class="text">菜单名称</span>
                                    </label>
                                    <label>
                                        <input type="text" class="form-control" v-model="modifyOjb.name" />
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-4"></div>
                        </div>
                        <div style="margin-top: 15px;" class="menu_content_container">
                            <div class="msg_sender" id="edit">
                                <div class="tab-container msg_tab">
                                    <ul class="nav nav-tabs clearfix">
                                        <li class="active">
                                            <a href="#setting" data-toggle="tab"><i class="fui-new"></i>菜单设置</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="setting">
                                            <select class="selectpicker" id="sel" v-model="selectMenuType">
                                                <option v-for="(item,index) in definitionMenu" v-bind:value="index">{{item.name}}</option>
                                            </select>
                                            <br>
                                            <br>
                                            <input type="text" class="form-control" v-show="selectMenuType==0||selectMenuType==1" v-bind:placeholder="inputPlaceholder" v-model="inputData" />
                                            <br>
                                            <button type="button" class="btn btn-primary margin-right-5" style="cursor: pointer;" @click="saveModify">
                                                保存修改
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <span class="editor_arrow_wrp"><i class="editor_arrow editor_arrow_in"></i></span>
            </div>
        </div>

    </div>
    <div class="col-md-12" style="margin-top: 10px;margin-bottom: 5px;">
        <div class="col-md-1"></div>
        <div class="col-md-4">
            <button type="button" class="btn btn-primary btn-botton" style="cursor: pointer;"
                    onclick="sortMenu(this)">排序
            </button>
            <button type="button" class="btn btn-primary btn-botton" style="cursor: pointer;"
                    @click="addMenu">增加菜单
            </button>
        </div>
        <div class="col-md-5">
            <button type="button" class="btn btn-primary btn-botton" style="cursor: pointer;"
                    @click="updateMenu()"
            >上传菜单
            </button>
            <button type="button" class="layui-btn layui-btn-normal" style="cursor: pointer;"
                    @click="pullMenu()"
            >上传并发布微信菜单
            </button>
        </div>
        <div class="col-md-2"></div>
    </div>
</div>
<!--大于50KB类包-->
<%--<script src="${ctx}/operator/assets/wechatMenu/js/flat/flat-ui.min.js"></script>--%>
<script src="${ctx}/operator/assets/wechatMenu/js/flat/application.js"></script>
<script src="${ctx}/operator/assets/wechatMenu/js/index.js"></script>
<script src="${ctx}/plugins/vue/vue.js"></script>
<script src="${ctx}/operator/assets/wechatMenu/js/jquery.dragsort-0.5.2.js"></script>
<script src="${ctx}/operator/assets/wechatMenu/js/bootstrap3-dialog3/bootstrap3-dialog.js"></script>
<script>
    var vm = new Vue({
        el:"#app",
        data:{
            menu:{},
            selectFlag:1,
            choseMenu:{},//当前选择的主菜单
            choseSubMenu:{},//当前选择的子菜单
            nowMenu:{},//当前选择的菜单
            modifyOjb:{},//当前修改的属性
            defaultMenu:{
                "type":"click",
                "name":"默认菜单",
                "key":"welcome"
            },
            definitionMenu: [//顺序不可随意更改
                {"name": "自定义key", "type": "click", "key": ""},
                {"name": "自定义页面", "type": "view"},
                {"name": "零售主页", "type": "view", "url": "##mainPage##"},
                {"name": "附近商店", "type": "view", "url": "##nearbyShop##"},
                {"name": "购买记录", "type": "view", "url": "##myOrder##"},
                {"name": "扫码进店", "type": "scancode_push","key":"scancode_push"},
                {"name": "实名认证", "type": "click","key":"real_name_auth"}
            ],
            selectMenuType:{}
        },
        methods:{
            getGroupClass:function (index) {
                return 'group_'+index;
            },
            selectMenu: function (item) {
                this.choseMenu = item;
                this.choseSubMenu = {};
                this.nowMenu = item;
                this.selectFlag = 1;
            },
            selectSubMenu:function (item) {
                this.selectFlag = 2;
                this.choseSubMenu = item;
                this.nowMenu = item;
            },
            addMenu: function () {
                if (this.menu['button']) {
                    if (this.menu.button.length >= 3) {
                        alert('最大3个菜单');//TODO
                        return;
                    }
                    this.menu['button'].push(JSON.parse(JSON.stringify(this.defaultMenu)));
                } else {
                    this.$set(this.menu, "button", [JSON.parse(JSON.stringify(this.defaultMenu))]);
                }
            },
            addSubMenu:function () {
                if (this.choseMenu['sub_button']) {
                    if(this.choseMenu.sub_button.length >= 5){
                        alert('最大5个子菜单');//TODO
                        return ;
                    }
                    this.choseMenu.sub_button.push(JSON.parse(JSON.stringify(this.defaultMenu)));
                } else {
                    this.$set(this.choseMenu,"sub_button",[JSON.parse(JSON.stringify(this.defaultMenu))]);
                }
            },
            deleteMenu:function () {
                if(this.selectFlag == 2){
                    var index = this.choseMenu.sub_button.indexOf(this.nowMenu);
                    if (index != -1) {
                        this.choseMenu.sub_button.splice(index, 1);
                        this.choseSubMenu={};
                        this.nowMenu={};
                        this.modifyOjb={};
                    }
                }else if(this.selectFlag == 1){
                    var index = this.menu.button.indexOf(this.nowMenu);
                    if (index != -1) {
                        this.menu.button.splice(index, 1);
                        this.choseMenu={};
                        this.nowMenu={};
                        this.modifyOjb={};
                    }
                }
            },
            saveModify:function () {
                if($.isEmptyObject(this.modifyOjb)){
                    alert("请选择菜单");//TODO
                    return ;
                }
                this.modifyOjb.name = this.modifyOjb.name.trim();
                if(this.modifyOjb.name.length == 0){
                    alert("输入菜单名称");//TODO
                    return ;
                }
                var newMenu;
                newMenu = JSON.parse(JSON.stringify(this.modifyOjb));
                if (this.selectMenuType == 1) {
                    newMenu.type = 'view';
                    newMenu.url = this.inputData;
                    delete newMenu['key'];
                } else if (this.selectMenuType == 0) {
                    newMenu.type = 'click';
                    newMenu.key = this.inputData;
                    delete newMenu['url'];
                } else {
                    newMenu = JSON.parse(JSON.stringify(this.definitionMenu[this.selectMenuType]));
                    newMenu.name = this.modifyOjb.name;
                }
                for (var k in this.nowMenu) {
                    if(k == 'sub_button'){
                        continue;
                    }
                    this.$delete(this.nowMenu, k);
                }
                for (var k in newMenu) {
                    this.nowMenu[k] = newMenu[k];
                }
            },
            getData:function () {
                var that = this;
                $.post('${ctx}/operator/wechatSetting/wechatMenu', {}, function (data) {
                    if (data.code == 0) {
                        if(data.data){
                            that.menu = JSON.parse(data.data);
                        }else{
                            that.menu = {};
                        }
                    }
                }, 'json');
            },
            updateMenu:function (callback) {
                var menu = JSON.parse(JSON.stringify(this.menu));
                if(!menu['button']){
                    menu['button'] = [];
                }
                var sortMenu = function (button,names) {
                    if(!button){
                        return ;
                    }
                    var newButton = [];
                    for (var i in names) {
                        for (var j in button) {
                            if (button[j].name == names[i]) {
                                newButton.push(button[j]);
                                continue;
                            }
                        }
                    }
                    for(var i in newButton){
                        button[i] = newButton[i];
                    }
                };
                //排序预处理
                //排序主菜单
                var names = [];
                $('.pre_menu_item .js_l1Title').each(function (i,n) {
                    names.push($(n).html());
                });
                sortMenu(menu.button,names);
                //排序子菜单
                for(var i in menu.button){
                    var subNames = [];
                    $('.group_'+i+' .js_sub_pre_menu_inner').each(function (i,n) {
                        subNames.push($(n).html());
                    });
                    if (subNames.length > 0) {
                        sortMenu(menu.button[i].sub_button,subNames);
                    }
                }
                //修改预处理
                for(var i in menu.button){
                    if (menu.button[i]['sub_button'] && menu.button[i]['sub_button'].length > 0) {//有子菜单删除其他的
                        for (var k in menu.button[i]) {
                            if (k != 'sub_button' && k != 'name') {
                                delete menu.button[i][k];
                            }
                        }
                    } else {//删除子菜单
                        delete menu.button[i]['sub_button'];
                    }
                }

                $.post('${ctx}/operator/wechatSetting/saveWechatMenu', {menu: JSON.stringify(menu)}, function (data) {
                    if (data.code == 0 && data.data) {
                        alert("上传成功");//todo 保存成功
                        if(callback){
                            callback();
                        }
                    }
                }, 'json');
            },
            pullMenu: function () {
                this.updateMenu(function () {
                    $.post('${ctx}/operator/wechatSetting/pullMenu', {}, function (data) {
                        if (data.code == 0) {
                            if (data.data) {
                                alert('推送菜单成功');
                            } else {
                                data.message;
                                alert('推送菜单失败');
                            }
                        } else {
                            alert('推送菜单失败');
                        }
                    }, 'json');
                });
            }
        },
        watch:{
            nowMenu: function () {
                //当前选择菜单
                this.modifyOjb = JSON.parse(JSON.stringify(this.nowMenu));//复制一个对象到修改界面
                var b = true;
                for (var index in this.definitionMenu) {
                    if(this.modifyOjb.type == 'view') {
                        if (this.modifyOjb.url == this.definitionMenu[index].url) {
                            this.selectMenuType = index;
                            b = false;
                            break;
                        }
                    } else if (this.modifyOjb.type == this.definitionMenu[index].type) {
                        if (this.modifyOjb.type == 'click') {
                            if(this.modifyOjb.key == this.definitionMenu[index].key) {
                                this.selectMenuType = index;
                                b = false;
                                break;
                            }
                        } else {
                            this.selectMenuType = index;
                            b = false;
                            break;
                        }
                    }
                }

                if(b){
                    this.selectMenuType = 1;
                }
            }
        },
        computed:{
            inputData: {
                get: function () {
                    if (this.modifyOjb.type == 'view') {
                        return this.modifyOjb.url;
                    } else if (this.modifyOjb.type == 'click') {
                        return this.modifyOjb.key;
                    } else {
                        return "";
                    }
                },
                set: function (newValue) {
                    if (this.selectMenuType != 5) {
                        if (this.selectMenuType == 1) {
                            this.modifyOjb['url'] = newValue;
                            delete this.modifyOjb['key'];
                        } else {
                            this.modifyOjb['key'] = newValue;
                            delete this.modifyOjb['url'];
                        }
                    }
                }
            },
            inputPlaceholder:function () {
                if (this.selectMenuType == 1) {
                    return '请输入页面地址';
                } else if (this.selectMenuType == 0) {
                    return '请输入响应key值';
                }
            }
        },
        mounted:function () {
            this.getData();
        },
        updated:function () {
            $('.selectpicker').selectpicker('refresh');
        }
    });

    /**是否在排序还是非排序状态**/
    var dragSort = false;
    /**排序**/
    function sortMenu(dom) {
        if (dragSort) {
            //拖拽
            $("#menuList").dragsort("destroy");
            $("#menuList .sub_pre_menu_list").dragsort("destroy");
            $(dom).html("排序");
            dragSort = false;
        } else {
            $("#menuList").dragsort({});
            $("#menuList .sub_pre_menu_list").each(function (i,n) {
                $(n).dragsort({});
            });
            $(dom).html("完成");
            dragSort = true;
        }
    }
</script>
</body>
</html>