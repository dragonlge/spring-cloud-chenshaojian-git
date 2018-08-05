
#Java二维码类库
##功能：
###二维码生成（编码）与读取（解码）类库。包括：
1．	普通二维码（黑白码）生成。

2．	带Logo的二维码生成。

3．	带动画Logo的二维码生成。

4．	彩色二维码（设置前景色和背景色）生成。

5．	可在边框上设置文字说明的二维码。

6．	以图形做背景生成特效二维码。

7．	对图形二维码进行解码，读取内容。
##用法：
将程序打包成Jar包，在项目中进行引用。
###1．	普通二维码生成
````
import com.code.QRCodeEncoder;

String imgPath = "C:/temp/1.png";
String content = "http://www.demo.com";
QRCodeEncoder encoder = new QRCodeEncoder();
encoder.encoderQRCode(
content, //内容
imgPath, //文件路径 
"png",   //文件类型
"UTF-8", //编码方式
4,      //大小
null,   //边框
null, //前景色
null, //背景色
6,    //图标比例
null, //图标路径 
null  //动画图标路径
);
````
###2．	带Logo的二维码生成
````

import com.code.QRCodeEncoder;

String imgPath = "C:/temp/1.png";
String logo = "C:/temp/logo.png";
String content = "http://www.demo.com";
QRCodeEncoder encoder = new QRCodeEncoder();
encoder.encoderQRCode(
content, //内容
imgPath, //文件路径 
"png",   //文件类型
"UTF-8", //编码方式
4,      //大小
null,   //边框
null, //前景色
null, //背景色
6,    //图标比例
logo, //图标路径 
null  //动画图标路径
);
````
###3．	彩色二维码生成
````

import com.code.QRCodeEncoder;

String imgPath = "C:/temp/1.png";
String content = "http://www.demo.com";
QRCodeEncoder encoder = new QRCodeEncoder();
encoder.encoderQRCode(
content, //内容
imgPath, //文件路径 
"png",   //文件类型
"UTF-8", //编码方式
4,      //大小
null,   //边框
Color.BLUE, //前景色
Color.WHITE, //背景色
6,    //图标比例
null, //图标路径 
null  //动画图标路径
);
````

###4．	边框加文字说明二维码生成
````

import com.code.QRCodeEncoder;

String imgPath = "C:/temp/1.png";
String content = "http://www.demo.com";
QRCodeEncoder encoder = new QRCodeEncoder();
encoder.encoderQRCode(
content, //内容
imgPath, //文件路径 
"png",   //文件类型
"UTF-8", //编码方式
4,      //大小
null,   //边框
Color.BLUE, //前景色
Color.WHITE, //背景色
6,    //图标比例
null, //图标路径 
"顶边文字说明", //顶边文字
"底边文字说明", //底边文字 
null  //定位点变色
);
````

###5．	以图形做背景的二维码生成
````

import com.code.QRCodeEncoder;

String imgPath = "C:/temp/1.png";
String content = "http://www.demo.com";
QRCodeEncoder encoder = new QRCodeEncoder();
encoder.encoderQRCode(
content, //内容
imgPath, //文件路径 
"png",   //文件类型
"UTF-8", //编码方式
4,      //大小
null, //前景色
null, //背景色
null,    //定位点变色
null, //背景图片
null, //检测规则
null, //检验选项
null  //圆角选项（1：圆角）
);
````

###6．	二维解码（读取）
````
import com.code.QRCodeDncoderHandler;

QRCodeDecoderHandler handler = new QRCodeDecoderHandler();
String imgPath = "C:/temp/1.png";
String decoderContent = handler.decoderQRCode(imgPath);
````