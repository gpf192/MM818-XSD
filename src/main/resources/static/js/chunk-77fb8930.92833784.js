(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-77fb8930"],{"2c0b":function(t,a,e){"use strict";e.r(a);var s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"rule-wrapper"},[e("div",{staticClass:"rule-content"},[e("div",{staticClass:"ticket-content"},t._l(t.prizeList,function(a,s){return e("div",{key:s},[e("div",{staticClass:"ticket-content-item"},[e("div",{staticClass:"first-cloumn"},[e("p",{staticClass:"name"},[t._v("成功兑换"+t._s(a.awardNumber)+"个"+t._s(a.awardEntity.awardName))]),e("p",{staticClass:"common-text"},[t._v(t._s(a.recordTime))])]),t.getCarsType(a.awardEntity.index)?e("div",{staticClass:"second-cloumn"},[e("p",{staticClass:"common-text"},[t._v(t._s(t.getCardsName(a.awardNumber,a.awardEntity.index)))])]):e("div",{staticClass:"second-other-cloumn"},[e("p",{staticClass:"common-text"},[t._v("-"+t._s(a.awardNumber)+"鼠卡")]),e("p",{staticClass:"common-text"},[t._v("-"+t._s(a.awardNumber)+"牛卡")])]),e("div",{staticClass:"third-cloumn"},[e("p",{staticClass:"common-text"},[t._v("获得"+t._s(a.awardNumber)+"个")]),e("p",{staticClass:"common-text"},[t._v(t._s(a.awardEntity.awardNameAlias))])])])])}),0)]),t._m(0)])},n=[function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("span",{staticClass:"advice"},[t._v("投资有风险 入市需谨慎")])])}],i=(e("5fc6"),e("5af2")),r=e.n(i),c=(e("ceaa"),e("b2fb")),o=e.n(c),d=e("4be0"),l={name:"AwardRecord",components:{},data:function(){return{prizeList:[],prizesFlag:!1}},computed:{},created:function(){var t={back:!0,romoveMarginTop:!1,title:"奖品兑换记录"};this.$emit("routerChange",t),this.getMyAwards()},methods:{getMyAwards:function(){var t=this;o.a.open({text:"加载中...",spinnerType:"triple-bounce"}),Object(d["c"])().then(function(a){console.log(a),o.a.close(),0==a.data.resCode?a.data.result.length>0?(t.prizeList=a.data.result,t.prizesFlag=!0):t.prizesFlag=!1:r()({message:a.data.resMsg,position:"middle",duration:2e3})})},getCardsName:function(t,a){var e="";return 1==a?e="-"+t+"张牛卡":2==a?e="-"+2*t+"张鼠卡":4==a&&(e="-"+12*t+"张生肖卡"),e},getCarsType:function(t){var a=!0;return this.isSingle=3!=t,a}}},u=l,m=(e("410b"),e("2877")),p=Object(m["a"])(u,s,n,!1,null,"fefd9800",null);a["default"]=p.exports},"410b":function(t,a,e){"use strict";var s=e("81ff"),n=e.n(s);n.a},"81ff":function(t,a,e){}}]);
//# sourceMappingURL=chunk-77fb8930.92833784.js.map