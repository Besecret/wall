(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-554a2eac"],{"31e5":function(t,e,a){},"43c0":function(t,e,a){"use strict";var s=a("31e5"),i=a.n(s);i.a},9406:function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"index_header"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:6}},[a("div",{staticClass:"grid-content"},[a("el-image",{staticStyle:{width:"52px",height:"64px"},attrs:{src:"https://i.loli.net/2019/10/08/dwNC82JeBHOoV1x.png"}}),t._v(" "),a("div",{staticClass:"grid-content-right"},[a("div",{staticClass:"grid-content-right-text"},[t._v("未审核信息用户")]),t._v(" "),a("div",{staticClass:"grid-content-right-number"},[t._v(t._s(t.userInfo.unCheckUse))])])],1)]),t._v(" "),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"grid-content"},[a("el-image",{staticStyle:{width:"52px",height:"64px"},attrs:{src:"https://i.loli.net/2019/10/08/AZbzfepGNahXrUm.png"}}),t._v(" "),a("div",{staticClass:"grid-content-right"},[a("div",{staticClass:"grid-content-right-text"},[t._v("近七天惠民卡使用次数")]),t._v(" "),a("div",{staticClass:"grid-content-right-number"},[t._v(t._s(t.userInfo.countRecord))])])],1)]),t._v(" "),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"grid-content"},[a("el-image",{staticStyle:{width:"52px",height:"64px"},attrs:{src:"https://i.loli.net/2019/10/08/NXViF8svEHgUP23.png"}}),t._v(" "),a("div",{staticClass:"grid-content-right"},[a("div",{staticClass:"grid-content-right-text"},[t._v("近七天惠民卡购买次数")]),t._v(" "),a("div",{staticClass:"grid-content-right-number"},[t._v(t._s(t.userInfo.countMemRecord))])])],1)]),t._v(" "),a("el-col",{attrs:{span:6}},[a("div",{staticClass:"grid-content"},[a("el-image",{staticStyle:{width:"52px",height:"64px"},attrs:{src:"https://i.loli.net/2019/10/08/JjU3H1MADLvtd7k.png"}}),t._v(" "),a("div",{staticClass:"grid-content-right"},[a("div",{staticClass:"grid-content-right-text"},[t._v("近七天惠民卡激活用户")]),t._v(" "),a("div",{staticClass:"grid-content-right-number"},[t._v(t._s(t.userInfo.countMemUseRecord))])])],1)])],1)],1),t._v(" "),a("div",[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12}},[a("div",[a("div",{staticClass:"table_title"},[t._v("新增用户")]),t._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticClass:"users_table",staticStyle:{width:"100%"}},[a("el-table",{attrs:{data:t.userInfo.countAddUser,size:"medium","header-cell-style":{background:"#f1f1f1"}}},[a("el-table-column",{attrs:{prop:"userrole",label:"用户类型"}}),t._v(" "),a("el-table-column",{attrs:{prop:"realname",label:"姓名"}}),t._v(" "),a("el-table-column",{attrs:{prop:"gender",label:"性别"}}),t._v(" "),a("el-table-column",{attrs:{prop:"usertel",label:"联系电话"}})],1)],1)])]),t._v(" "),a("el-col",{attrs:{span:12}},[a("div",[a("div",{staticClass:"table_title"},[t._v("操作记录")]),t._v(" "),a("div",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],staticClass:"users_table",staticStyle:{width:"100%"}},[a("el-table",{attrs:{data:t.userInfo.countAddRecord,size:"medium","header-cell-style":{background:"#f1f1f1"}}},[a("el-table-column",{attrs:{prop:"user.realname",label:"姓名"}}),t._v(" "),a("el-table-column",{attrs:{prop:"checkuser",label:"操作人"}}),t._v(" "),a("el-table-column",{attrs:{prop:"checktime",formatter:t.dateFormatter,label:"操作时间"}}),t._v(" "),a("el-table-column",{attrs:{prop:"status",label:"状态"}})],1)],1)])])],1)],1)])},i=[],l=a("ed08"),n=a("b775");function r(t){return Object(n["a"])({url:"/xianwall/index/dashBoard",method:"post",data:t})}var c={data:function(){return{listLoading:!1,userInfo:{}}},mounted:function(){this.getList()},methods:{dateFormatter:function(t,e){return Object(l["c"])(t.checktime)},getList:function(){var t=this;this.listLoading=!0,r().then((function(e){t.userInfo=e.data.data,t.listLoading=!1}))}}},o=c,d=(a("43c0"),a("2877")),v=Object(d["a"])(o,s,i,!1,null,"68327491",null);e["default"]=v.exports}}]);