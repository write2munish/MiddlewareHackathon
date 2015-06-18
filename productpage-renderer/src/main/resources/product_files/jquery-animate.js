(function(Z,V,U){function s(r,k,q,A){if("d"!=q&&S(r)){var w=o.exec(k),y="auto"===r.css(q)?0:r.css(q),y="string"==typeof y?m(y):y;
"string"==typeof k&&m(k);
A=!0===A?0:y;
var x=r.is(":hidden"),z=r.translation();
"left"==q&&(A=parseInt(y,10)+z.x);
"right"==q&&(A=parseInt(y,10)+z.x);
"top"==q&&(A=parseInt(y,10)+z.y);
"bottom"==q&&(A=parseInt(y,10)+z.y);
w||"show"!=k?w||"hide"!=k||(A=0):(A=1,x&&r.css({display:l(r.context.tagName),opacity:0}));
return w?(r=parseFloat(w[2]),w[1]&&(r=("-="===w[1]?-1:1)*r+parseInt(A,10)),"%"==w[3]&&(r+="%"),r):A
}}function i(D,C,w,B,y,A,z,d){var r=D.data(T),r=r&&!W(r)?r:Z.extend(!0,{},h),F=y;
if(-1<Z.inArray(C,j)){var x=r.meta,q=m(D.css(C))||0,E=C+"_o",F=y-q;
x[C]=F;
x[E]="auto"==D.css(C)?0+F:q+F||0;
r.meta=x;
z&&0===F&&(F=0-x[E],x[C]=F,x[E]=0)
}return D.data(T,g(D,r,C,w,B,F,A,z,d))
}function g(H,G,E,F,B,D,C,x,z){var L=!1;
C=!0===C&&!0===x;
G=G||{};
G.original||(G.original={},L=!0);
G.properties=G.properties||{};
G.secondary=G.secondary||{};
x=G.meta;
for(var k=G.original,y=G.properties,K=G.secondary,A=Y.length-1;
0<=A;
A--){var J=Y[A]+"transition-property",I=Y[A]+"transition-duration",w=Y[A]+"transition-timing-function";
E=C?Y[A]+"transform":E;
L&&(k[J]=H.css(J)||"",k[I]=H.css(I)||"",k[w]=H.css(w)||"");
K[E]=C?!0===z||!0===X&&!1!==z&&v?"translate3d("+x.left+"px, "+x.top+"px, 0)":"translate("+x.left+"px,"+x.top+"px)":D;
y[J]=(y[J]?y[J]+",":"")+E;
y[I]=(y[I]?y[I]+",":"")+F+"ms";
y[w]=(y[w]?y[w]+",":"")+B
}return G
}function f(k){for(var d in k){if(!("width"!=d&&"height"!=d||"show"!=k[d]&&"hide"!=k[d]&&"toggle"!=k[d])){return !0
}}return !1
}function W(k){for(var d in k){return !1
}return !0
}function l(k){k=k.toUpperCase();
var d={LI:"list-item",TR:"table-row",TD:"table-cell",TH:"table-cell",CAPTION:"table-caption",COL:"table-column",COLGROUP:"table-column-group",TFOOT:"table-footer-group",THEAD:"table-header-group",TBODY:"table-row-group"};
return"string"==typeof d[k]?d[k]:"block"
}function m(d){return parseFloat(d.replace(d.match(/\D+$/),""))
}function S(k){var d=!0;
k.each(function(q,r){return d=d&&r.ownerDocument
});
return d
}function e(q,d,k){if(!S(k)){return !1
}var r=-1<Z.inArray(q,c);
"width"!=q&&"height"!=q&&"opacity"!=q||parseFloat(d)!==parseFloat(k.css(q))||(r=!1);
return r
}var c="top right bottom left opacity height width".split(" "),j=["top","right","bottom","left"],Y=["-webkit-","-moz-","-o-",""],b=["avoidTransforms","useTranslate3d","leaveTransforms"],o=/^([+-]=)?([\d+-.]+)(.*)$/,a=/([A-Z])/g,h={secondary:{},meta:{top:0,right:0,bottom:0,left:0}},T="jQe",u=null,p=!1,n=(document.body||document.documentElement).style,t=void 0!==n.WebkitTransition||void 0!==n.MozTransition||void 0!==n.OTransition||void 0!==n.transition,v="WebKitCSSMatrix" in window&&"m11" in new WebKitCSSMatrix,X=v;
Z.expr&&Z.expr.filters&&(u=Z.expr.filters.animated,Z.expr.filters.animated=function(d){return Z(d).data("events")&&Z(d).data("events")["webkitTransitionEnd oTransitionEnd transitionend"]?!0:u.call(this,d)
});
Z.extend({toggle3DByDefault:function(){return X=!X
},toggleDisabledByDefault:function(){return p=!p
},setDisabledByDefault:function(d){return p=d
}});
Z.fn.translation=function(){if(!this[0]){return null
}var q=window.getComputedStyle(this[0],null),k={x:0,y:0};
if(q){for(var r=Y.length-1;
0<=r;
r--){var w=q.getPropertyValue(Y[r]+"transform");
if(w&&/matrix/i.test(w)){q=w.replace(/^matrix\(/i,"").split(/, |\)$/g);
k={x:parseInt(q[4],10),y:parseInt(q[5],10)};
break
}}}return k
};
Z.fn.animate=function(q,d,k,z){q=q||{};
var r=!("undefined"!==typeof q.bottom||"undefined"!==typeof q.right),y=Z.speed(d,k,z),w=0,x=function(){w--;
0===w&&"function"===typeof y.complete&&y.complete.apply(this,arguments)
};
return !0===("undefined"!==typeof q.avoidCSSTransitions?q.avoidCSSTransitions:p)||!t||W(q)||f(q)||0>=y.duration||y.step?V.apply(this,arguments):this[!0===y.queue?"queue":"each"](function(){var E=Z(this),D=Z.extend({},y),B=function(M){var L=E.data(T)||{original:{}},K={};
if(2==M.eventPhase){if(!0!==q.leaveTransforms){for(M=Y.length-1;
0<=M;
M--){K[Y[M]+"transform"]=""
}if(r&&"undefined"!==typeof L.meta){M=0;
for(var J;
J=j[M];
++M){K[J]=L.meta[J+"_o"]+"px",Z(this).css(J,K[J])
}}}E.unbind("webkitTransitionEnd oTransitionEnd transitionend").css(L.original).css(K).data(T,null);
"hide"===q.opacity&&E.css({display:"none",opacity:""});
x.call(this)
}},A={bounce:"cubic-bezier(0.0, 0.35, .5, 1.3)",linear:"linear",swing:"ease-in-out",easeInQuad:"cubic-bezier(0.550, 0.085, 0.680, 0.530)",easeInCubic:"cubic-bezier(0.550, 0.055, 0.675, 0.190)",easeInQuart:"cubic-bezier(0.895, 0.030, 0.685, 0.220)",easeInQuint:"cubic-bezier(0.755, 0.050, 0.855, 0.060)",easeInSine:"cubic-bezier(0.470, 0.000, 0.745, 0.715)",easeInExpo:"cubic-bezier(0.950, 0.050, 0.795, 0.035)",easeInCirc:"cubic-bezier(0.600, 0.040, 0.980, 0.335)",easeInBack:"cubic-bezier(0.600, -0.280, 0.735, 0.045)",easeOutQuad:"cubic-bezier(0.250, 0.460, 0.450, 0.940)",easeOutCubic:"cubic-bezier(0.215, 0.610, 0.355, 1.000)",easeOutQuart:"cubic-bezier(0.165, 0.840, 0.440, 1.000)",easeOutQuint:"cubic-bezier(0.230, 1.000, 0.320, 1.000)",easeOutSine:"cubic-bezier(0.390, 0.575, 0.565, 1.000)",easeOutExpo:"cubic-bezier(0.190, 1.000, 0.220, 1.000)",easeOutCirc:"cubic-bezier(0.075, 0.820, 0.165, 1.000)",easeOutBack:"cubic-bezier(0.175, 0.885, 0.320, 1.275)",easeInOutQuad:"cubic-bezier(0.455, 0.030, 0.515, 0.955)",easeInOutCubic:"cubic-bezier(0.645, 0.045, 0.355, 1.000)",easeInOutQuart:"cubic-bezier(0.770, 0.000, 0.175, 1.000)",easeInOutQuint:"cubic-bezier(0.860, 0.000, 0.070, 1.000)",easeInOutSine:"cubic-bezier(0.445, 0.050, 0.550, 0.950)",easeInOutExpo:"cubic-bezier(1.000, 0.000, 0.000, 1.000)",easeInOutCirc:"cubic-bezier(0.785, 0.135, 0.150, 0.860)",easeInOutBack:"cubic-bezier(0.680, -0.550, 0.265, 1.550)"},I={},A=A[D.easing||"swing"]?A[D.easing||"swing"]:D.easing||"swing",C;
for(C in q){if(-1===Z.inArray(C,b)){var H=-1<Z.inArray(C,j),G=s(E,q[C],C,H&&!0!==q.avoidTransforms);
e(C,G,E)?i(E,C,D.duration,A,G,H&&!0!==q.avoidTransforms,r,q.useTranslate3d):I[C]=q[C]
}}E.unbind("webkitTransitionEnd oTransitionEnd transitionend");
C=E.data(T);
if(!C||W(C)||W(C.secondary)){D.queue=!1
}else{w++;
E.css(C.properties);
var F=C.secondary;
setTimeout(function(){E.bind("webkitTransitionEnd oTransitionEnd transitionend",B).css(F)
})
}W(I)||(w++,V.apply(E,[I,{duration:D.duration,easing:Z.easing[D.easing]?D.easing:Z.easing.swing?"swing":"linear",complete:x,queue:D.queue}]));
return !0
})
};
Z.fn.animate.defaults={};
Z.fn.stop=function(q,d,k){if(!t){return U.apply(this,[q,d])
}q&&this.queue([]);
this.each(function(){var A=Z(this),w=A.data(T);
if(w&&!W(w)){var z,x={};
if(d){if(x=w.secondary,!k&&void 0!==typeof w.meta.left_o||void 0!==typeof w.meta.top_o){for(x.left=void 0!==typeof w.meta.left_o?w.meta.left_o:"auto",x.top=void 0!==typeof w.meta.top_o?w.meta.top_o:"auto",z=Y.length-1;
0<=z;
z--){x[Y[z]+"transform"]=""
}}}else{if(!W(w.secondary)){var y=window.getComputedStyle(A[0],null);
if(y){for(var r in w.secondary){if(w.secondary.hasOwnProperty(r)&&(r=r.replace(a,"-$1").toLowerCase(),x[r]=y.getPropertyValue(r),!k&&/matrix/i.test(x[r]))){for(z=x[r].replace(/^matrix\(/i,"").split(/, |\)$/g),x.left=parseFloat(z[4])+parseFloat(A.css("left"))+"px"||"auto",x.top=parseFloat(z[5])+parseFloat(A.css("top"))+"px"||"auto",z=Y.length-1;
0<=z;
z--){x[Y[z]+"transform"]=""
}}}}}}A.unbind("webkitTransitionEnd oTransitionEnd transitionend");
A.css(w.original).css(x).data(T,null)
}else{U.apply(A,[q,d])
}});
return this
}
})(jQuery,jQuery.fn.animate,jQuery.fn.stop);