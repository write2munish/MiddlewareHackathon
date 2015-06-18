window.Modernizr=(function(z,c,g){var G="2.8.3",v={},x=true,J=c.documentElement,a="modernizr",F=c.createElement(a),B=F.style,I,b={}.toString,j=" -webkit- -moz- -o- -ms- ".split(" "),h="Webkit Moz O ms",C=h.split(" "),H=h.toLowerCase().split(" "),E={svg:"http://www.w3.org/2000/svg"},l={},o={},f={},e=[],k=e.slice,r,m=function(S,U,M,T){var L,R,O,P,K=c.createElement("div"),Q=c.body,N=Q||c.createElement("body");
if(parseInt(M,10)){while(M--){O=c.createElement("div");
O.id=T?T[M]:a+(M+1);
K.appendChild(O)
}}L=["&#173;",'<style id="s',a,'">',S,"</style>"].join("");
K.id=a;
(Q?K:N).innerHTML+=L;
N.appendChild(K);
if(!Q){N.style.background="";
N.style.overflow="hidden";
P=J.style.overflow;
J.style.overflow="hidden";
J.appendChild(N)
}R=U(K,S);
if(!Q){N.parentNode.removeChild(N);
J.style.overflow=P
}else{K.parentNode.removeChild(K)
}return !!R
},D=function(M){var L=z.matchMedia||z.msMatchMedia;
if(L){return L(M)&&L(M).matches||false
}var K;
m("@media "+M+" { #"+a+" { position: absolute; } }",function(N){K=(z.getComputedStyle?getComputedStyle(N,null):N.currentStyle)["position"]=="absolute"
});
return K
},i=({}).hasOwnProperty,w;
if(!q(i,"undefined")&&!q(i.call,"undefined")){w=function(K,L){return i.call(K,L)
}
}else{w=function(K,L){return((L in K)&&q(K.constructor.prototype[L],"undefined"))
}
}if(!Function.prototype.bind){Function.prototype.bind=function d(M){var N=this;
if(typeof N!="function"){throw new TypeError()
}var K=k.call(arguments,1),L=function(){if(this instanceof L){var Q=function(){};
Q.prototype=N.prototype;
var P=new Q();
var O=N.apply(P,K.concat(k.call(arguments)));
if(Object(O)===O){return O
}return P
}else{return N.apply(M,K.concat(k.call(arguments)))
}};
return L
}
}function A(K){B.cssText=K
}function t(L,K){return A(j.join(L+";")+(K||""))
}function q(L,K){return typeof L===K
}function s(L,K){return !!~(""+L).indexOf(K)
}function y(M,K){for(var L in M){var N=M[L];
if(!s(N,"-")&&B[N]!==g){return K=="pfx"?N:true
}}return false
}function p(L,O,N){for(var K in L){var M=O[L[K]];
if(M!==g){if(N===false){return L[K]
}if(q(M,"function")){return M.bind(N||O)
}return M
}}return false
}function n(O,K,N){var L=O.charAt(0).toUpperCase()+O.slice(1),M=(O+" "+C.join(L+" ")+L).split(" ");
if(q(K,"string")||q(K,"undefined")){return y(M,K)
}else{M=(O+" "+(H).join(L+" ")+L).split(" ");
return p(M,K,N)
}}l.canvas=function(){var K=c.createElement("canvas");
return !!(K.getContext&&K.getContext("2d"))
};
l.touch=function(){var K;
if(("ontouchstart" in z)||z.DocumentTouch&&c instanceof DocumentTouch){K=true
}else{m(["@media (",j.join("touch-enabled),("),a,")","{#modernizr{top:9px;position:absolute}}"].join(""),function(L){K=L.offsetTop===9
})
}return K
};
l.boxshadow=function(){return n("boxShadow")
};
l.csstransforms=function(){return !!n("transform")
};
l.csstransforms3d=function(){var K=!!n("perspective");
if(K&&"webkitPerspective" in J.style){m("@media (transform-3d),(-webkit-transform-3d){#modernizr{left:9px;position:absolute;height:3px;}}",function(L,M){K=L.offsetLeft===9&&L.offsetHeight===3
})
}return K
};
l.csstransitions=function(){return n("transition")
};
l.localstorage=function(){try{localStorage.setItem(a,a);
localStorage.removeItem(a);
return true
}catch(K){return false
}};
l.svg=function(){return !!c.createElementNS&&!!c.createElementNS(E.svg,"svg").createSVGRect
};
for(var u in l){if(w(l,u)){r=u.toLowerCase();
v[r]=l[u]();
e.push((v[r]?"":"no-")+r)
}}v.addTest=function(L,M){if(typeof L=="object"){for(var K in L){if(w(L,K)){v.addTest(K,L[K])
}}}else{L=L.toLowerCase();
if(v[L]!==g){return v
}M=typeof M=="function"?M():M;
if(typeof x!=="undefined"&&x){J.className+=" test-"+(M?"":"no-")+L
}v[L]=M
}return v
};
A("");
F=I=null;
v._version=G;
v._prefixes=j;
v._domPrefixes=H;
v._cssomPrefixes=C;
v.mq=D;
v.testProp=function(K){return y([K])
};
v.testAllProps=n;
v.testStyles=m;
J.className=J.className.replace(/(^|\s)no-js(\s|$)/,"$1$2")+(x?" test-js test-"+e.join(" test-"):"");
return v
})(this,this.document);
(function(p,t){var j="3.7.0";
var g=p.html5||{};
var k=/^<|^(?:button|map|select|textarea|object|iframe|option|optgroup)$/i;
var e=/^(?:a|b|code|div|fieldset|h1|h2|h3|h4|h5|h6|i|label|li|ol|p|q|span|strong|style|table|tbody|td|th|tr|ul)$/i;
var y;
var l="_html5shiv";
var c=0;
var v={};
var h;
(function(){try{var B=t.createElement("a");
B.innerHTML="<xyz></xyz>";
y=("hidden" in B);
h=B.childNodes.length==1||(function(){(t.createElement)("a");
var D=t.createDocumentFragment();
return(typeof D.cloneNode=="undefined"||typeof D.createDocumentFragment=="undefined"||typeof D.createElement=="undefined")
}())
}catch(C){y=true;
h=true
}}());
function i(B,D){var E=B.createElement("p"),C=B.getElementsByTagName("head")[0]||B.documentElement;
E.innerHTML="x<style>"+D+"</style>";
return C.insertBefore(E.lastChild,C.firstChild)
}function r(){var B=o.elements;
return typeof B=="string"?B.split(" "):B
}function x(B){var C=v[B[l]];
if(!C){C={};
c++;
B[l]=c;
v[c]=C
}return C
}function u(E,B,D){if(!B){B=t
}if(h){return B.createElement(E)
}if(!D){D=x(B)
}var C;
if(D.cache[E]){C=D.cache[E].cloneNode()
}else{if(e.test(E)){C=(D.cache[E]=D.createElem(E)).cloneNode()
}else{C=D.createElem(E)
}}return C.canHaveChildren&&!k.test(E)&&!C.tagUrn?D.frag.appendChild(C):C
}function z(D,F){if(!D){D=t
}if(h){return D.createDocumentFragment()
}F=F||x(D);
var G=F.frag.cloneNode(),E=0,C=r(),B=C.length;
for(;
E<B;
E++){G.createElement(C[E])
}return G
}function A(B,C){if(!C.cache){C.cache={};
C.createElem=B.createElement;
C.createFrag=B.createDocumentFragment;
C.frag=C.createFrag()
}B.createElement=function(D){if(!o.shivMethods){return C.createElem(D)
}return u(D,B,C)
};
B.createDocumentFragment=Function("h,f","return function(){var n=f.cloneNode(),c=n.createElement;h.shivMethods&&("+r().join().replace(/\w+/g,function(D){C.createElem(D);
C.frag.createElement(D);
return'c("'+D+'")'
})+");return n}")(o,C.frag)
}function d(B){if(!B){B=t
}var C=x(B);
if(o.shivCSS&&!y&&!C.hasCSS){C.hasCSS=!!i(B,"article,aside,dialog,figcaption,figure,footer,header,hgroup,main,nav,section{display:block}mark{background:#FF0;color:#000}template{display:none}")
}if(!h){A(B,C)
}return B
}var o={elements:g.elements||"abbr article aside audio bdi canvas data datalist details dialog figcaption figure footer header hgroup main mark meter nav output progress section summary template time video",version:j,shivCSS:(g.shivCSS!==false),supportsUnknownElements:h,shivMethods:(g.shivMethods!==false),type:"default",shivDocument:d,createElement:u,createDocumentFragment:z};
p.html5=o;
d(t);
var b=/^$|\b(?:all|print)\b/;
var m="html5shiv";
var s=!h&&(function(){var B=t.documentElement;
return !(typeof t.namespaces=="undefined"||typeof t.parentWindow=="undefined"||typeof B.applyElement=="undefined"||typeof B.removeNode=="undefined"||typeof p.attachEvent=="undefined")
}());
function f(F){var G,D=F.getElementsByTagName("*"),E=D.length,C=RegExp("^(?:"+r().join("|")+")$","i"),B=[];
while(E--){G=D[E];
if(C.test(G.nodeName)){B.push(G.applyElement(w(G)))
}}return B
}function w(D){var E,B=D.attributes,C=B.length,F=D.ownerDocument.createElement(m+":"+D.nodeName);
while(C--){E=B[C];
E.specified&&F.setAttribute(E.nodeName,E.nodeValue)
}F.style.cssText=D.style.cssText;
return F
}function a(E){var G,F=E.split("{"),C=F.length,B=RegExp("(^|[\\s,>+~])("+r().join("|")+")(?=[[\\s,>+~#.:]|$)","gi"),D="$1"+m+"\\:$2";
while(C--){G=F[C]=F[C].split("}");
G[G.length-1]=G[G.length-1].replace(B,D);
F[C]=G.join("}")
}return F.join("{")
}function q(C){var B=C.length;
while(B--){C[B].removeNode()
}}function n(B){var H,F,E=x(B),D=B.namespaces,G=B.parentWindow;
if(!s||B.printShived){return B
}if(typeof D[m]=="undefined"){D.add(m)
}function C(){clearTimeout(E._removeSheetTimer);
if(H){H.removeNode(true)
}H=null
}G.attachEvent("onbeforeprint",function(){C();
var I,M,K,O=B.styleSheets,L=[],J=O.length,N=Array(J);
while(J--){N[J]=O[J]
}while((K=N.pop())){if(!K.disabled&&b.test(K.media)){try{I=K.imports;
M=I.length
}catch(P){M=0
}for(J=0;
J<M;
J++){N.push(I[J])
}try{L.push(K.cssText)
}catch(P){}}}L=a(L.reverse().join(""));
F=f(B);
H=i(B,L)
});
G.attachEvent("onafterprint",function(){q(F);
clearTimeout(E._removeSheetTimer);
E._removeSheetTimer=setTimeout(C,500)
});
B.printShived=true;
return B
}o.type+=" print";
o.shivPrint=n;
n(t)
}(this,document));